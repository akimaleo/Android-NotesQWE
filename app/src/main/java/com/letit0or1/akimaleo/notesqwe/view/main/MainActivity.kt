package com.letit0or1.akimaleo.notesqwe.view.main

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.letit0or1.akimaleo.notesqwe.Note
import com.letit0or1.akimaleo.notesqwe.R
import com.letit0or1.akimaleo.notesqwe.util.FirebaseUtil
import com.letit0or1.akimaleo.notesqwe.util.webdata.SyncHandler
import com.letit0or1.akimaleo.notesqwe.util.webdata.SyncWorker
import com.letit0or1.akimaleo.notesqwe.view.CActivity
import com.letit0or1.akimaleo.notesqwe.view.authorization.AuthorizationActivity
import com.letit0or1.akimaleo.notesqwe.view.create.CreateNoteActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : CActivity() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: NotesRecyclerViewAdapter
    private lateinit var mLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRecyclerView = recycler_view
        mRecyclerView.setHasFixedSize(true)

        var count = if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 3
        mLayoutManager = GridLayoutManager(this, count)
        mRecyclerView.layoutManager = mLayoutManager as RecyclerView.LayoutManager?

        mAdapter = NotesRecyclerViewAdapter(ArrayList())
        mRecyclerView.setAdapter(mAdapter)

        login.setOnClickListener {
            val intent = Intent(this, AuthorizationActivity::class.java)
            startActivity(intent)
        }
        create.setOnClickListener {
            val intent = Intent(this, CreateNoteActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()

        fillData(SyncWorker.instance.getCacheData())
        if (FirebaseUtil.instance.firebaseAuth.currentUser != null)
            SyncWorker.instance.downloadData(object : SyncHandler {

                override fun success(list: ArrayList<Note>) {
                    fillData(list)
                }

                override fun error(exception: Exception) {
                    exception.printStackTrace()
                }
            })
    }

    public fun fillData(list: ArrayList<Note>) {
        mAdapter.mDataset.clear()
        mAdapter.mDataset.addAll(list)
        mAdapter.notifyDataSetChanged()
    }

    val REQ_CODE_CREATE = 0;
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQ_CODE_CREATE -> {
                    var uid = data?.extras?.getString("uid")
//                    SyncWorker.instance.putData()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)

    }
}
