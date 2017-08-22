package com.letit0or1.akimaleo.notesqwe.view.main

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.letit0or1.akimaleo.notesqwe.R
import com.letit0or1.akimaleo.notesqwe.util.webdata.SyncWorker
import com.letit0or1.akimaleo.notesqwe.util.UserDataUtil
import com.letit0or1.akimaleo.notesqwe.view.authorization.AuthorizationActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: RecyclerView.Adapter<*>
    private lateinit var mLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRecyclerView = recycler_view
        mRecyclerView.setHasFixedSize(true);

        var count = if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 3
        mLayoutManager = GridLayoutManager(this, count)
        mRecyclerView.layoutManager = mLayoutManager

        mAdapter = NotesRecyclerViewAdapter(ArrayList())
        mRecyclerView.setAdapter(mAdapter);

        get_data.setOnClickListener {
            SyncWorker().downloadData()
        }

        write_time.setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            var format = SimpleDateFormat("dd.MM.yyy HH:mm:ss")
            val myRef = database.getReference(UserDataUtil.instance.firebaseAuth.currentUser?.email?.replace('.', '-', true))
            myRef.setValue(format.format(Calendar.getInstance().time))

//            SyncWorker().putData(listOf({ Note() }))
        }

        login.setOnClickListener {
            val intent = Intent(this, AuthorizationActivity::class.java)
            startActivity(intent)
        }

    }
}
