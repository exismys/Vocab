package com.example.vocab

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.vocab.models.VocabDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WordListAdapter(private var wordList: MutableList<String>, private var listName: String) : RecyclerView.Adapter<WordListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView) {
        val tvWord: TextView = itemView.findViewById(R.id.tv_word)
        private val ibDelete: ImageButton = itemView.findViewById(R.id.ib_deleteWord)
        init {
            val database = Room.databaseBuilder(context, VocabDatabase::class.java, "VocabDB").build()
            // Dialog box setup to delete a word
            val view = LayoutInflater.from(context).inflate(R.layout.delete_word_dialog, null, false)
            val dialog = AlertDialog.Builder(context).create()
            val btnDeleteFromList = view.findViewById<Button>(R.id.btn_deleteFromList)
            val ivCancel = view.findViewById<ImageView>(R.id.iv_cancel)

            btnDeleteFromList.setOnClickListener {
                val word = wordList[adapterPosition]
                val index = wordList.indexOf(word)
                wordList.removeAt(index)
                GlobalScope.launch {
                    database.groupDAO().update(listName, wordList)
                }
                notifyItemRemoved(index)
                dialog.cancel()
            }
            val btnDeleteFromEveryWhere = view.findViewById<Button>(R.id.btn_deleteFromEverywhere)
            btnDeleteFromEveryWhere.setOnClickListener {
                val word = wordList[adapterPosition]
                val index = wordList.indexOf(word)
                wordList.removeAt(index)
                GlobalScope.launch {
                    database.wordDAO().deleteByWord(word)
                    database.groupDAO().update(listName, wordList)
                }
                notifyItemRemoved(index)
                dialog.cancel()
            }
            dialog.setView(view)
            ibDelete.setOnClickListener {
                dialog.show()
            }
            ivCancel.setOnClickListener {
                dialog.cancel()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_word_list, parent, false)
        return ViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvWord.text = wordList[position + 1]
    }

    override fun getItemCount(): Int {
        return wordList.size - 1
    }
}