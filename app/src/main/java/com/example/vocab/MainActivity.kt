package com.example.vocab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager = findViewById<ViewPager2>(R.id.vp_vocabList)

        val images = listOf(
            R.drawable.cat_island,
            R.drawable.desert_hand,
            R.drawable.giant_causeway,
            R.drawable.glass_beach,
            R.drawable.lake_hillier,
            R.drawable.socotra_island,
            R.drawable.tianzi_mountains
        )

        viewPager.adapter = ViewPagerAdapter(images)
        viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL

    }
}