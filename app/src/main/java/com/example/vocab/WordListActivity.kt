package com.example.vocab

import android.app.ActionBar.LayoutParams
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.marginTop
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vocab.models.VocabDatabase
import com.example.vocab.models.Word
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException

class WordListActivity : AppCompatActivity() {
    private lateinit var rvWordList: RecyclerView
    private lateinit var adapter: WordListAdapter
    private lateinit var fabAddWord: FloatingActionButton
    private lateinit var fabPlay: FloatingActionButton
    private lateinit var database: VocabDatabase
    private lateinit var wordList: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_list)

        database = Room.databaseBuilder(this, VocabDatabase::class.java, "VocabDB").allowMainThreadQueries().build()
        var listName = intent.getStringExtra("listName")!!
        rvWordList = findViewById(R.id.rv_wordList)
        fabAddWord = findViewById(R.id.fab_addWord)
        fabPlay = findViewById(R.id.fab_play)
        wordList = ArrayList()

        adapter = WordListAdapter(wordList, listName)

        rvWordList.layoutManager = LinearLayoutManager(this)
        rvWordList.adapter = adapter


        database.groupDAO().getGroupLive(listName).observe(this) {
            wordList.clear()
            wordList.addAll(it.words)
            adapter.notifyDataSetChanged()
        }

        // Creating dialog to fetch and add words
        val dialog = AlertDialog.Builder(this).create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = LayoutInflater.from(this).inflate(R.layout.add_word_dialog, null, false)
        dialog.setView(view)
        // Accessing views in dialog
        val etWord = view.findViewById<EditText>(R.id.et_word)
        val btnFetch = view.findViewById<Button>(R.id.btn_fetch)
        val btnAddWord = view.findViewById<Button>(R.id.btn_addWord)
        val btnCancelWord = view.findViewById<ImageButton>(R.id.ib_closeAddWordDialog)
        val tvTemp = view.findViewById<TextView>(R.id.tv_temp)
        val llFetchedOutput = view.findViewById<LinearLayout>(R.id.ll_fetchedOutput)

        btnAddWord.visibility = View.INVISIBLE
        var word = ""
        var wordDetailJson = ""

        val queue = Volley.newRequestQueue(this)
        val url = "https://api.dictionaryapi.dev/api/v2/entries/en/"

        btnFetch.setOnClickListener {
            word = etWord.text.toString()
            llFetchedOutput.removeAllViews()
            val stringRequest = StringRequest(Request.Method.GET, url + word, { response ->

                val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                params.topMargin = 10
                val params2 = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                params2.topMargin = 25
                val tfMon = ResourcesCompat.getFont(this, R.font.montserrat)
                val tfPac = ResourcesCompat.getFont(this, R.font.pacifico)

                val tvWord = TextView(this)
                tvWord.layoutParams = params
                tvWord.textSize = 40F
                tvWord.setTextColor(Color.argb(100, 253, 233, 233))
                tvWord.typeface = tfMon
                tvWord.text = "$word"
                llFetchedOutput.addView(tvWord)

                wordDetailJson = "["

                val meanings = JSONArray(response).getJSONObject(0).getJSONArray("meanings")
                for (i in 0 until meanings.length()) {

                    val pos = meanings.getJSONObject(i).getString("partOfSpeech")
                    val tvPos = TextView(this)
                    if (i != 0) tvPos.layoutParams = params2
                    tvPos.text = "$pos"
                    tvPos.textSize = 10F
                    tvPos.setTextColor(Color.argb(100, 253, 233, 233))
                    tvPos.typeface = tfMon
                    llFetchedOutput.addView(tvPos)
                    wordDetailJson += "{\"pos\":\"$pos\",\"meanings\":["

                    val definitions = meanings.getJSONObject(i).getJSONArray("definitions")
                    for (j in 0 until definitions.length()) {

                        val meaning = definitions.getJSONObject(j).getString("definition")
                        val tvMeaningHead = TextView(this)
                        tvMeaningHead.layoutParams = params
                        tvMeaningHead.textSize = 16F
                        tvMeaningHead.setTextColor(Color.argb(100, 253, 233, 233))
                        tvMeaningHead.typeface = tfPac
                        tvMeaningHead.text = "Meaning:"
                        llFetchedOutput.addView(tvMeaningHead)
                        val tvMeaning = TextView(this)
                        tvMeaning.textSize = 16F
                        tvMeaning.setTextColor(Color.argb(100, 253, 233, 233))
                        tvMeaning.typeface = tfMon
                        tvMeaning.text = meaning
                        llFetchedOutput.addView(tvMeaning)

                        val example = try {
                            definitions.getJSONObject(j).getString("example")
                        } catch (ex: JSONException) {
                            ""
                        }
                        wordDetailJson += "{\"meaning\":\"$meaning\",\"example\":\"$example\"}"
                        if (j != definitions.length() - 1) {
                            wordDetailJson += ","
                        }
                        if (example.isNotEmpty()) {
                            val tvExampleHead = TextView(this)
                            tvExampleHead.textSize = 16F
                            tvExampleHead.setTextColor(Color.argb(100, 253, 233, 233))
                            tvExampleHead.typeface = tfPac
                            tvExampleHead.text = "Example"
                            llFetchedOutput.addView(tvExampleHead)
                            val tvExample = TextView(this)
                            tvExample.textSize = 16F
                            tvExample.setTextColor(Color.argb(100, 253, 233, 233))
                            tvExample.typeface = tfMon
                            tvExample.text = "$example"
                            llFetchedOutput.addView(tvExample)
                        }
                    }
                    wordDetailJson += "]}"
                    if (i != meanings.length() - 1) {
                        wordDetailJson += ","
                    }
                }
                wordDetailJson += "]"
                btnAddWord.visibility = View.VISIBLE
                }, {
                tvTemp.text = "That didn't work!"
                })

            queue.add(stringRequest)
        }

        btnAddWord.setOnClickListener { _ ->
            var dbWord = database.wordDAO().getWord(word)
            var groupWords = database.groupDAO().getGroup(listName).words
            if (dbWord == null) {
                wordList.add(word)
                GlobalScope.launch {
                    database.wordDAO().insert(Word(word, wordDetailJson))
                    database.groupDAO().update(listName, wordList)
                }
                Toast.makeText(this, "$word added to the database and list $listName", Toast.LENGTH_LONG).show()
            } else if (!groupWords.contains(word)) {
                wordList.add(word)
                GlobalScope.launch {
                    database.groupDAO().update(listName, wordList)
                }
                Toast.makeText(this, "$word added to the current list $listName", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "$word is already saved!", Toast.LENGTH_LONG).show()
            }
            dialog.dismiss()
        }

        btnCancelWord.setOnClickListener {
            dialog.cancel()
        }

        fabAddWord.setOnClickListener {
            dialog.show()
        }

        fabPlay.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                it.putExtra("listName", listName)
                startActivity(it)
            }
        }
    }
}