package com.example.vocab

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class ViewPagerAdapter(private val images: List<Int>) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {

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

    inner class ViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager, parent, false)
        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.itemView.setBackgroundColor(colorList[Random.nextInt(7)])
    }

    override fun getItemCount(): Int {
        return images.size
    }
}