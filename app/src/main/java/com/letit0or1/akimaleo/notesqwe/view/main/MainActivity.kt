package com.letit0or1.akimaleo.notesqwe.view.main

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.letit0or1.akimaleo.notesqwe.Note
import com.letit0or1.akimaleo.notesqwe.R
import com.letit0or1.akimaleo.notesqwe.util.database.NO2Notes
import com.letit0or1.akimaleo.notesqwe.util.webdata.SyncWorker
import com.letit0or1.akimaleo.notesqwe.view.authorization.AuthorizationActivity
import com.letit0or1.akimaleo.notesqwe.view.create.CreateNoteActivity
import com.letit0or1.akimaleo.notesqwe.view.create.OnItemClickListener
import com.letit0or1.akimaleo.notesqwe.view.view.CActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : CActivity() {

    val REQ_CODE_CREATE = 0;

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: NotesRecyclerViewAdapter
    private lateinit var mLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRecyclerView = recycler_view
        mRecyclerView.setHasFixedSize(true)

        val count = if (resources.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 3
        mLayoutManager = GridLayoutManager(this, count)
        mRecyclerView.layoutManager = mLayoutManager as RecyclerView.LayoutManager?

        mAdapter = NotesRecyclerViewAdapter(ArrayList())
        mRecyclerView.setAdapter(mAdapter)


        mAdapter.onClickListener = object : OnItemClickListener {
            override fun onClick(view: View, o: Any) {
                val intent = Intent(this@MainActivity, CreateNoteActivity::class.java)
                intent.putExtra("uid", (o as Note).uid)
                startActivityForResult(intent, REQ_CODE_CREATE)
            }
        }
        login.setOnClickListener {
            val intent = Intent(this, AuthorizationActivity::class.java)
            startActivity(intent)
        }
        create.setOnClickListener {
            val intent = Intent(this, CreateNoteActivity::class.java)
            startActivityForResult(intent, REQ_CODE_CREATE)
        }
    }

    override fun onStart() {
        super.onStart()
        fillData(NO2Notes.instance.getAllNotes())
        SyncWorker.instance.syncData()
    }

    fun fillData(list: ArrayList<Note>) {
        mAdapter.mDataset.clear()
        mAdapter.mDataset.addAll(list)
        mAdapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQ_CODE_CREATE -> {
                    var uid = data?.extras?.getString("uid")

                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
