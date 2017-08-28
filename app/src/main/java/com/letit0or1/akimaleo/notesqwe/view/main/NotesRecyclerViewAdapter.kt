package com.letit0or1.akimaleo.notesqwe.view.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.letit0or1.akimaleo.notesqwe.Note
import com.letit0or1.akimaleo.notesqwe.R
import com.letit0or1.akimaleo.notesqwe.view.create.OnItemClickListener
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by akimaleo on 16.08.17.
 */

class NotesRecyclerViewAdapter(val mDataset: ArrayList<Note>) : RecyclerView.Adapter<NotesRecyclerViewAdapter.ViewHolder>() {

    var onClickListener: OnItemClickListener
        set

    private val formaterDate: SimpleDateFormat

    init {
        onClickListener = object : OnItemClickListener {
            override fun onClick(view: View, o: Any) {
            }
        }
        formaterDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var label: TextView
        var text: TextView
        var date: TextView

        init {
            label = view.findViewById(R.id.label) as TextView
            text = view.findViewById(R.id.text) as TextView
            date = view.findViewById(R.id.time) as TextView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_notes, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = mDataset[position]

        val content = note.text.split(Regex("\n"), 0)
        holder.label.text = content[0]

        if (content.size > 1) {

            holder.text.text = ""
            for (i in 1 until content.size)
                holder.text.text = holder.text.text.toString() +
                        "\n" + content[i]
        } else {
            holder.text.visibility = View.GONE
        }

        holder.date.text = formaterDate.format(note.editDate)
        holder.itemView.setOnClickListener { v -> onClickListener.onClick(v!!, note) }
    }

    override fun getItemCount(): Int {
        return mDataset.size
    }
}
