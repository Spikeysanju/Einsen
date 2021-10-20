/*
 *
 *  * Copyright 2021 Spikey Sanju
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     https://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */

package dev.spikeysanju.einsen.view.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.spikeysanju.einsen.model.emoji.EmojiItem
import dev.spikeysanju.einsen.model.task.Task
import dev.spikeysanju.einsen.repository.MainRepository
import dev.spikeysanju.einsen.utils.viewstate.CountViewState
import dev.spikeysanju.einsen.utils.viewstate.EmojiViewState
import dev.spikeysanju.einsen.utils.viewstate.SingleViewState
import dev.spikeysanju.einsen.utils.viewstate.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: MainRepository) : ViewModel() {

    // Backing property to avoid state updates from other classes
    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
    private val _singleViewState = MutableStateFlow<SingleViewState>(SingleViewState.Loading)
    private val _emojiViewState = MutableStateFlow<EmojiViewState>(EmojiViewState.Loading)
    private val _countState = MutableStateFlow<CountViewState>(CountViewState.Loading)
    private val _currentEmoji = MutableStateFlow("")

    // The UI collects from this StateFlow to get its state update
    val feed = _viewState.asStateFlow()
    val singleTask = _singleViewState.asStateFlow()
    val emoji = _emojiViewState.asStateFlow()
    val currentEmoji = _currentEmoji.asStateFlow()

    // get all task
    fun getAllTask() = viewModelScope.launch(Dispatchers.IO) {
        repo.getAllTask().distinctUntilChanged().collect { result ->
            try {
                if (result.isNullOrEmpty()) {
                    _viewState.value = ViewState.Empty
                } else {
                    _viewState.value = ViewState.Success(result)
                }
            } catch (e: Exception) {
                _viewState.value = ViewState.Error(e)
            }
        }
    }

    // get all list of emoji from Json
    fun getAllEmoji(context: Context, searchQuery: String) = viewModelScope.launch {
        try {
            // read JSON file
            val myJson = context.assets.open("emoji.json").bufferedReader().use {
                it.readText()
            }

            // format JSON
            val format = Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            }

            // decode emoji list from json
            val decodedEmoji = format.decodeFromString<List<EmojiItem>>(myJson).distinct()

            // filter the emoji based on Aliases
            val filteredEmojiAliases = decodedEmoji.filter { emojiAliases ->
                emojiAliases.aliases.any {
                    it.contains(searchQuery, ignoreCase = true)
                } || emojiAliases.category.contains(searchQuery, ignoreCase = true)
            }.distinct()

            if (searchQuery.isNullOrEmpty()) {
                _emojiViewState.value = EmojiViewState.Success(decodedEmoji)
            } else {
                if (filteredEmojiAliases.isNullOrEmpty()) {
                    _emojiViewState.value = EmojiViewState.Empty
                } else {
                    _emojiViewState.value = EmojiViewState.Success(filteredEmojiAliases)
                }
            }
        } catch (e: Exception) {
            _emojiViewState.value = EmojiViewState.Error(exception = e)
        }
    }

    // insert source
    fun insertTask(task: Task) = viewModelScope.launch {
        repo.insert(task)
    }

    // delete source
    fun deleteTaskByID(id: Int) = viewModelScope.launch {
        repo.delete(id)
    }

    // update status
    fun updateStatus(id: Int, isCompleted: Boolean) = viewModelScope.launch {
        repo.updateStatus(id, isCompleted)
    }

    // find task by id
    fun findTaskByID(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repo.find(id).distinctUntilChanged().collect { result ->
            try {
                if (result.title.isEmpty()) {
                    _singleViewState.value = SingleViewState.Empty
                } else {
                    _singleViewState.value = SingleViewState.Success(result)
                }
            } catch (e: Exception) {
                _viewState.value = ViewState.Error(e)
            }
        }
    }

    // get all task
    fun getTaskByPriority(priority: String) = viewModelScope.launch(Dispatchers.IO) {
        repo.getTaskByPriority(priority).distinctUntilChanged().collect { result ->
            try {
                if (result.isNullOrEmpty()) {
                    _viewState.value = ViewState.Empty
                } else {
                    _viewState.value = ViewState.Success(result)
                }
            } catch (e: Exception) {
                _viewState.value = ViewState.Error(e)
            }
        }
    }

    // update status
    fun currentEmoji(emoji: String) = viewModelScope.launch {
        try {
            if (emoji.isEmpty()) {
                _currentEmoji.value = "Select an emoji"
            } else {
                _currentEmoji.value = emoji
            }
        } catch (e: Exception) {
            _currentEmoji.value = "⚠️"
        }
    }
}
