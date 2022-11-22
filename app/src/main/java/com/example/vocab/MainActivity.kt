package com.example.vocab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import androidx.viewpager2.widget.ViewPager2
import com.example.vocab.models.VocabDatabase
import com.example.vocab.models.Word

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: ViewPagerAdapter
    private lateinit var list: MutableList<Word>
    private lateinit var database: VocabDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = Room.databaseBuilder(this, VocabDatabase::class.java, "VocabDB").allowMainThreadQueries().build()

        list = ArrayList()
        adapter = ViewPagerAdapter(list)
        viewPager2 = findViewById(R.id.vp_vocabList)
        viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL
        viewPager2.adapter = adapter


        val listName: String? = intent.getStringExtra("listName")
        if (listName == null) {
            database.wordDAO().getAllWords().observe(this) {
                list.clear()
                list.addAll(it)
                adapter.notifyDataSetChanged()
            }
        } else {
            var groupWords = database.groupDAO().getGroup(listName).words
            database.wordDAO().getAllWords(groupWords).observe(this) {
                list.clear()
                list.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }
    }
}