package com.example.vocab

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vocab.models.Word
import org.json.JSONArray
import kotlin.random.Random

class ViewPagerAdapter(private val words: MutableList<Word>) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {

    private val colorList = listOf(
        Color.argb(100, 214, 145, 145),
        Color.argb(100, 195, 83, 121),
        Color.argb(100, 149, 128, 152),
        Color.argb(100, 207, 117, 220),
        Color.argb(100, 171, 5, 195),
        Color.argb(100, 166, 160, 102),
        Color.argb(100, 136, 68, 68),
        Color.argb(100, 98, 133, 161),
    )

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
        holder.itemView.setBackgroundColor(colorList[Random.nextInt(7)])
        val word = words[position]
        holder.tvWord.text = word.word
        val jsonArray = JSONArray(word.wordDetailJson)
        holder.tvPos.text = jsonArray.getJSONObject(0).getString("pos")
        holder.tvMeaning.text = jsonArray.getJSONObject(0).getJSONArray("meanings").getJSONObject(0).getString("meaning")

    }

    override fun getItemCount(): Int {
        return words.size
    }
}