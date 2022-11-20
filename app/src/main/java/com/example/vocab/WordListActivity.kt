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
import org.json.JSONArray
import org.json.JSONException

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

        var word = ""
        var pos = ""
        var meaning = ""
        var wordDetailJson = "["
        var example = ""
        var temp = ""

        val queue = Volley.newRequestQueue(this)
        val url = "https://api.dictionaryapi.dev/api/v2/entries/en/"
        btnFetch.setOnClickListener {
            word = etWord.text.toString()
            val stringRequest = StringRequest(Request.Method.GET, url + word, { response ->
                temp = "Word: $word\n\n"

                val meanings = JSONArray(response).getJSONObject(0).getJSONArray("meanings")
                for (i in 0 until meanings.length()) {
                    pos = meanings.getJSONObject(i).getString("partOfSpeech")
                    temp += "\nPart of Speech: $pos\n"
                    wordDetailJson += "{\"pos\":\"$pos\",\"meanings\":["
                    val definitions = meanings.getJSONObject(i).getJSONArray("definitions")
                    for (j in 0 until definitions.length()) {
                        meaning = definitions.getJSONObject(j).getString("definition")
                        temp += "Meaning: $meaning\n"
                        example = try {
                            definitions.getJSONObject(j).getString("example")
                        } catch (ex: JSONException) {
                            ""
                        }
                        wordDetailJson += "{\"meaning\":\"$meaning\",\"example\":\"$example\"}"
                        if (j != definitions.length() - 1) {
                            wordDetailJson += ","
                        }
                        if (example.isNotEmpty()) {
                            temp += "Example: $example\n"
                        }

                    }
                    wordDetailJson += "]}"
                    if (i != meanings.length() - 1) {
                        wordDetailJson += ","
                    }
                }
                wordDetailJson += "]"

                var parsed = ""
                val jsonArray1 = JSONArray(wordDetailJson)
                for (i in 0 until jsonArray1.length()) {
                    val pos1 = jsonArray1.getJSONObject(i).getString("pos")
                    parsed += "Part of Speech: $pos1\n\n\n"
                    val meanings1 = jsonArray1.getJSONObject(i).getJSONArray("meanings")
                    for (j in 0 until meanings1.length()) {
                        parsed += "Meaning: ${meanings1.getJSONObject(j).getString("meaning")}\nExample: ${meanings1.getJSONObject(j).getString("example")}\n\n"
                    }
                    parsed += "\n"
                }
                tvTemp.text = parsed
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