package com.example.vocab

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vocab.models.Word
import org.json.JSONArray
import org.json.JSONException
import kotlin.random.Random

class ViewPagerAdapter(private val words: MutableList<Word>) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvWord: TextView = itemView.findViewById(R.id.tv_word)
        val tvPos: TextView = itemView.findViewById(R.id.tv_pos)
        val tvMeaning: TextView = itemView.findViewById(R.id.tv_meaning)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager, parent, false)
        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val word = words[position]
        holder.tvWord.text = word.word
        try {
            val jsonArray = JSONArray(word.wordDetailJson)
            holder.tvPos.text = jsonArray.getJSONObject(0).getString("pos")
            holder.tvMeaning.text =
                jsonArray.getJSONObject(0).getJSONArray("meanings").getJSONObject(0)
                    .getString("meaning")
        } catch (ex: JSONException) {
            holder.tvMeaning.text = "Sorry! Could not get the word because of parsing issue!"
        }

    }

    override fun getItemCount(): Int {
        return words.size
    }
}