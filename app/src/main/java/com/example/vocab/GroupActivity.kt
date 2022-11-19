package com.example.vocab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vocab.models.Group
import com.google.android.material.floatingactionbutton.FloatingActionButton

class GroupActivity : AppCompatActivity() {
    private lateinit var fabPlayAll: FloatingActionButton
    private lateinit var rvListName: RecyclerView
    private lateinit var adapter: GroupAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        fabPlayAll = findViewById(R.id.fab_play)
        rvListName = findViewById(R.id.rv_wordList)
        adapter = GroupAdapter(listOf(Group("Book 1", arrayListOf("")), Group("Book 2", arrayListOf(""))))
        rvListName.adapter = adapter
        rvListName.layoutManager = LinearLayoutManager(this)
        fabPlayAll.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}