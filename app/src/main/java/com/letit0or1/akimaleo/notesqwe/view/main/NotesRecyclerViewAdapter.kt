package com.letit0or1.akimaleo.notesqwe.view.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.letit0or1.akimaleo.notesqwe.Note
import com.letit0or1.akimaleo.notesqwe.R
import java.util.*

/**
 * Created by akimaleo on 16.08.17.
 */

class NotesRecyclerViewAdapter(private val mDataset: ArrayList<Note>) : RecyclerView.Adapter<NotesRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var label: TextView
        var text: TextView
        var date: TextView

        init {
            label = view.findViewById(R.id.text) as TextView
            text = view.findViewById(R.id.text) as TextView
            date = view.findViewById(R.id.text) as TextView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_notes, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text = mDataset[0].text

    }

    override fun getItemCount(): Int {
        return mDataset.size
    }
}
