package com.example.vocab

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.vocab.models.Group
import com.example.vocab.models.VocabDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GroupActivity : AppCompatActivity() {
    private lateinit var fabPlayAll: FloatingActionButton
    private lateinit var fabAddGroup: FloatingActionButton
    private lateinit var rvListName: RecyclerView
    private lateinit var adapter: GroupAdapter
    private lateinit var database: VocabDatabase
    private lateinit var list: MutableList<Group>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        database = Room.databaseBuilder(applicationContext, VocabDatabase::class.java, "VocabDB").build()
        fabPlayAll = findViewById(R.id.fab_play)
        fabAddGroup = findViewById(R.id.fab_addWord)
        rvListName = findViewById(R.id.rv_wordList)
        list = ArrayList()
        adapter = GroupAdapter(list)
        rvListName.layoutManager = LinearLayoutManager(this)
        rvListName.adapter = adapter

        // Getting all the groups to show on page from database
        database.groupDAO().getAllGroups().observe(this) {
            list.clear()
            list.addAll(it)
            adapter.notifyDataSetChanged()
        }

        fabPlayAll.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }

        // Creating dialog box to add word groups
        val dialog = AlertDialog.Builder(this).create()
        val view = LayoutInflater.from(this).inflate(R.layout.add_group_dialog, null, false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setView(view)
        val ivCancel = view.findViewById<ImageButton>(R.id.ib_closeAddGroupDialog)
        ivCancel.setOnClickListener {
            dialog.cancel()
        }
        val btnAdd = view.findViewById<Button>(R.id.btn_groupDialogAdd)
        btnAdd.setOnClickListener {
            val name = view.findViewById<EditText>(R.id.et_groupDialogName).text.toString()
            GlobalScope.launch {
                database.groupDAO().insert(Group(name, ArrayList()))
            }
            dialog.cancel()
            Toast.makeText(this, "Added $name", Toast.LENGTH_SHORT).show()
        }

        fabAddGroup.setOnClickListener {
            dialog.show()
        }
    }
}