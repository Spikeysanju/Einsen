package dev.spikeysanju.einsen.view.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.spikeysanju.einsen.model.EmojiItem
import dev.spikeysanju.einsen.model.Task
import dev.spikeysanju.einsen.repository.MainRepository
import dev.spikeysanju.einsen.utils.EmojiViewState
import dev.spikeysanju.einsen.utils.SingleViewState
import dev.spikeysanju.einsen.utils.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: MainRepository) : ViewModel() {

    // Backing property to avoid state updates from other classes
    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
    private val _singleViewState = MutableStateFlow<SingleViewState>(SingleViewState.Loading)
    private val _emojiViewState = MutableStateFlow<EmojiViewState>(EmojiViewState.Loading)

    // The UI collects from this StateFlow to get its state update
    val feed = _viewState.asStateFlow()
    val singleTask = _singleViewState.asStateFlow()
    val emoji = _emojiViewState.asStateFlow()

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


    // get all list of emoji from JSON
    fun getEmoji(context: Context) = viewModelScope.launch {
        try {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val listType = Types.newParameterizedType(List::class.java, EmojiItem::class.java)
            val adapter: JsonAdapter<List<EmojiItem>> = moshi.adapter(listType)
            val myJson = context.assets.open("allEmoji.json").bufferedReader().use {
                it.readText()
            }

            val emojiList = adapter.fromJson(myJson)
            if (emojiList.isNullOrEmpty()) {
                _emojiViewState.value = EmojiViewState.Empty
            } else {
                _emojiViewState.value = EmojiViewState.Success(emojiItem = emojiList)
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
    fun deleteTaskByID(id: Long) = viewModelScope.launch {
        repo.delete(id)
    }

    // update source
    fun updateTask(task: Task) = viewModelScope.launch {
        repo.update(task)
    }

    // find task by id
    fun findTaskByID(id: Long) = viewModelScope.launch(Dispatchers.IO) {
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
}