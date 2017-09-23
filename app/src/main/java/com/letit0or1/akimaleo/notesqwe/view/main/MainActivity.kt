package com.letit0or1.akimaleo.notesqwe.view.main

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.letit0or1.akimaleo.notesqwe.Note
import com.letit0or1.akimaleo.notesqwe.R
import com.letit0or1.akimaleo.notesqwe.util.FirebaseUtil
import com.letit0or1.akimaleo.notesqwe.util.database.NO2Notes
import com.letit0or1.akimaleo.notesqwe.util.ottobus.BroadcastEvent
import com.letit0or1.akimaleo.notesqwe.util.ottobus.OttoSingle
import com.letit0or1.akimaleo.notesqwe.util.webdata.SyncWorker
import com.letit0or1.akimaleo.notesqwe.view.create.CreateNoteActivity
import com.letit0or1.akimaleo.notesqwe.view.create.OnItemClickListener
import com.letit0or1.akimaleo.notesqwe.view.user.authorization.AuthorizationActivity
import com.letit0or1.akimaleo.notesqwe.view.user.page.UserPageActivity
import com.letit0or1.akimaleo.notesqwe.view.view.CActivity
import com.squareup.otto.Subscribe
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : CActivity() {

    private val REQ_CODE_CREATE = 0;

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: NotesRecyclerViewAdapter
    private lateinit var mLayoutManager: StaggeredGridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRecyclerView = recycler_view
        mRecyclerView.setHasFixedSize(true)

        val count = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 3
        mLayoutManager = StaggeredGridLayoutManager(count, resources.configuration.orientation)
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
            val intent: Intent
            if (FirebaseUtil.instance.firebaseAuth.currentUser == null) {
                intent = Intent(this, AuthorizationActivity::class.java)
            } else {
                intent = Intent(this, UserPageActivity::class.java)
            }
            startActivity(intent)
        }
        create.setOnClickListener {
            val intent = Intent(this, CreateNoteActivity::class.java)
            startActivityForResult(intent, REQ_CODE_CREATE)
        }
    }

    override fun onStart() {
        super.onStart()
        recycler_view.scrollTo(0, 0)
        fillData(NO2Notes.instance.getAllNotes())
        SyncWorker.instance.syncData()
    }

    fun fillData(list: ArrayList<Note>) {
        Collections.sort(list)
        mAdapter.mDataset.clear()
        mAdapter.mDataset.addAll(list)
        mAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        OttoSingle.instance.bus.register(this)
    }

    override fun onPause() {
        super.onPause()
        OttoSingle.instance.bus.unregister(this)
    }

    @Subscribe
    fun answerAvailable(event: BroadcastEvent) {
        fillData(NO2Notes.instance.getAllNotes())
    }
}