package com.example.vocab

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.vocab.models.Group
import com.example.vocab.models.VocabDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.ArrayList

class GroupAdapter(var list: MutableList<Group>) : RecyclerView.Adapter<GroupAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View, var context: Context) : RecyclerView.ViewHolder(itemView) {
        init {

            itemView.setOnClickListener {
                Intent(context, WordListActivity::class.java).also {
                    it.putExtra("listName", list[adapterPosition].listName)
                    context.startActivity(it)
                }
            }

            val database = Room.databaseBuilder(context, VocabDatabase::class.java, "VocabDB").build()

            // Setting up dialog box to update word group names
            val dialog = AlertDialog.Builder(context).create()
            val view = LayoutInflater.from(context).inflate(R.layout.add_group_dialog, null, false)
            dialog.setView(view)
            val ivCancel = view.findViewById<ImageView>(R.id.iv_groupDialogCancel)
            ivCancel.setOnClickListener {
                dialog.cancel()
            }
            val btnUpdate = view.findViewById<Button>(R.id.btn_groupDialogAdd)
            btnUpdate.text = "Update"
            btnUpdate.setOnClickListener {
                val name = view.findViewById<EditText>(R.id.et_groupDialogName).text.toString()
                GlobalScope.launch {
                    database.groupDAO().update(list[adapterPosition].listName, name)
                }
                dialog.cancel()
                list[adapterPosition].listName = name
                notifyItemChanged(adapterPosition)
            }

            itemView.findViewById<ImageButton>(R.id.ib_updateGroupName).setOnClickListener {
                dialog.show()
            }
            itemView.findViewById<ImageButton>(R.id.ib_deleteListName).setOnClickListener {
                GlobalScope.launch {
                    database.groupDAO().delete(list[adapterPosition])
                }
                Toast.makeText(context, "$adapterPosition removed", Toast.LENGTH_SHORT).show()
                list.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
                notifyItemRangeChanged(adapterPosition, 1)
            }
        }
        val tvGroupName: TextView = itemView.findViewById(R.id.tv_groupName)
        val wordPreview: List<TextView> = listOf(
            itemView.findViewById(R.id.tv_wordPreview1),
            itemView.findViewById(R.id.tv_wordPreview2),
            itemView.findViewById(R.id.tv_wordPreview3)
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_group, parent, false)
        return ViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val group = list[position]
        holder.tvGroupName.text = group.listName
        val words = group.words
        for (i in 1 until words.size) {
            holder.wordPreview[i - 1].text = words[i]
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}