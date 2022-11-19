package com.example.vocab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WordListActivity : AppCompatActivity() {
    private lateinit var rvWordList: RecyclerView
    private lateinit var adapter: WordListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_list)

        rvWordList = findViewById(R.id.rv_wordList)
        adapter = WordListAdapter(intent.getStringArrayListExtra("wordlist")!!)
        rvWordList.adapter = adapter
        rvWordList.layoutManager = LinearLayoutManager(this)
    }
}