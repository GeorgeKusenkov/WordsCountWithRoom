package com.example.myapplication.ui.main

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val ONE = 1

class MainViewModel(private val wordDao: WordDao, private val context: Context) : ViewModel() {
    val allWords = this.wordDao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    val sortedWords = this.wordDao.getSorted()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    fun onAddButton(text: String) {
        val regex = Regex("""[a-zA-Z-]+""")
        val list = allWords.value
        val wordInDataBase = list.any {
            it.text == text
        }

        if (text.matches(regex)) {
            if (allWords.value.isEmpty()) {
                addWord(text)
            } else {
                if (wordInDataBase) {
                    incrementCount(list, text)
                } else {
                    addWord(text)
                }
            }
        } else {
            Toast.makeText(context, "Try again! Only symbols and hyphen", Toast.LENGTH_SHORT).show()
        }
    }

    fun onDeleteBtn() {
        viewModelScope.launch {
            wordDao.delete()
        }
    }

    private fun incrementCount(
        list: List<Dictionary>,
        text: String
    ) {
        list.forEach { word ->
            if (word.text.contains(text)) {
                viewModelScope.launch {
                    wordDao.updateCount(text)
                }
            }
        }
    }

    private fun addWord(text: String) {
        viewModelScope.launch {
            wordDao.insert(
                Dictionary(
                    text = text,
                    count = ONE
                )
            )
        }
    }
}