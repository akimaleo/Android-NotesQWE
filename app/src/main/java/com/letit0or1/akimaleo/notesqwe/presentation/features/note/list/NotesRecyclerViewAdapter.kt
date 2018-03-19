package com.letit0or1.akimaleo.notesqwe.view.main

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.letit0or1.akimaleo.notesqwe.util.database.Note
import com.letit0or1.akimaleo.notesqwe.R
import com.letit0or1.akimaleo.notesqwe.view.create.OnItemClickListener
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by akimaleo on 16.08.17.
 */

class NotesRecyclerViewAdapter(val mDataSet: ArrayList<Note>) : RecyclerView.Adapter<NotesRecyclerViewAdapter.ViewHolder>() {

    var onClickListener: OnItemClickListener = object : OnItemClickListener {
        override fun onClick(view: View, o: Any) {
        }
    }

    private val formatterDate: SimpleDateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var label: TextView = view.findViewById(R.id.label) as TextView
        var text: TextView = view.findViewById(R.id.text) as TextView
        var date: TextView = view.findViewById(R.id.time) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_notes, parent, false)
        return ViewHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = mDataSet[position]

        val content = note.text.split(Regex("\n"), 0)
        holder.label.text = content[0]

        if (content.size > 1) {
            holder.text.visibility = View.VISIBLE

            holder.text.text = ""
            for (i in 1 until content.size) {
                if (i == content.size - 1) {
                    holder.text.text = "${holder.text.text} ${content[i]}"
                    break
                }
                holder.text.text = "${holder.text.text} ${content[i]}\n"
            }
        } else {
            holder.text.visibility = View.GONE
        }

        holder.date.text = formatterDate.format(note.editDate)
        holder.itemView.setOnClickListener { v -> onClickListener.onClick(v!!, note) }
    }

    override fun getItemCount() = mDataSet.size

}
