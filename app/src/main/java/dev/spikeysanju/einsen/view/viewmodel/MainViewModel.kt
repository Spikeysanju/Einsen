package dev.spikeysanju.einsen.view.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.spikeysanju.einsen.model.emoji.EmojiItem
import dev.spikeysanju.einsen.model.task.Task
import dev.spikeysanju.einsen.repository.MainRepository
import dev.spikeysanju.einsen.utils.viewstate.CountViewState
import dev.spikeysanju.einsen.utils.viewstate.EmojiViewState
import dev.spikeysanju.einsen.utils.viewstate.SingleViewState
import dev.spikeysanju.einsen.utils.viewstate.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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
    private val _currentEmoji = MutableStateFlow<String>("😄")

    // The UI collects from this StateFlow to get its state update
    val feed = _viewState.asStateFlow()
    val singleTask = _singleViewState.asStateFlow()
    val emoji = _emojiViewState.asStateFlow()
    val countState = _countState.asStateFlow()
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
    fun getAllEmoji(context: Context) = viewModelScope.launch {
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

            val decodedEmoji = format.decodeFromString<List<EmojiItem>>(myJson)
            _emojiViewState.value = EmojiViewState.Success(decodedEmoji)
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

    // update source
    fun updateTask(task: Task) = viewModelScope.launch {
        repo.update(task)
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

    fun getTaskByPriorityCount(priority: String) = viewModelScope.launch(Dispatchers.IO) {
        repo.getTaskByPriorityCount(priority).distinctUntilChanged().collect {
            try {
                _countState.value = CountViewState.Success(it)
            } catch (e: Exception) {
                _countState.value = CountViewState.Error(e)
            }
        }
    }

    // get all emoji's from db
    fun getAllEmojis() = viewModelScope.launch(Dispatchers.IO) {
        repo.getAllEmojis().distinctUntilChanged().collect { result ->
            try {
                if (result.isNullOrEmpty()) {
                    _emojiViewState.value = EmojiViewState.Empty
                } else {
                    _emojiViewState.value = EmojiViewState.Success(result)
                }
            } catch (e: Exception) {
                _emojiViewState.value = EmojiViewState.Error(e)
            }
        }
    }

    fun insertAllEmojis(emojiItem: List<EmojiItem>) = viewModelScope.launch(Dispatchers.IO) {
        repo.insert(emojiItem)
    }


    fun getEmojis(): Flow<PagingData<EmojiItem>> {
        return Pager(PagingConfig(10)) {
            repo.getAllEmojiss
        }.flow.cachedIn(viewModelScope)
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
            _currentEmoji.value = "❌"
        }
    }
}
