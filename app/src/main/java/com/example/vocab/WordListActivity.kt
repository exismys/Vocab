package com.example.vocab

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton

class WordListActivity : AppCompatActivity() {
    private lateinit var rvWordList: RecyclerView
    private lateinit var adapter: WordListAdapter
    private lateinit var fabAddWord: FloatingActionButton
    private lateinit var fabPlay: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_list)

        rvWordList = findViewById(R.id.rv_wordList)
        adapter = WordListAdapter(intent.getStringArrayListExtra("wordlist")!!)
        rvWordList.adapter = adapter
        rvWordList.layoutManager = LinearLayoutManager(this)

        fabAddWord = findViewById(R.id.fab_addWord)
        fabPlay = findViewById(R.id.fab_play)

        val dialog = AlertDialog.Builder(this).create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = LayoutInflater.from(this).inflate(R.layout.add_word_dialog, null, false)
        dialog.setView(view)
        val etWord = view.findViewById<EditText>(R.id.et_word)
        val btnFetch = view.findViewById<Button>(R.id.btn_fetch)
        val btnAddWord = view.findViewById<Button>(R.id.btn_addWord)
        val btnCancelWord = view.findViewById<Button>(R.id.btn_cancelWord)
        val tvTemp = view.findViewById<TextView>(R.id.tv_temp)

        val queue = Volley.newRequestQueue(this)
        val url = "https://api.dictionaryapi.dev/api/v2/entries/en/"
        btnFetch.setOnClickListener {
            val word = etWord.text.toString()
            val stringRequest = StringRequest(Request.Method.GET, url + word, { response ->
                tvTemp.text = "Response is: $response"
                Toast.makeText(this, "haha", Toast.LENGTH_SHORT).show()
                },
                { tvTemp.text = "That didn't work!" })
            queue.add(stringRequest)
        }

        btnCancelWord.setOnClickListener {
            dialog.cancel()
        }

        fabAddWord.setOnClickListener {
            dialog.show()
        }

        fabPlay.setOnClickListener {

        }
    }
}