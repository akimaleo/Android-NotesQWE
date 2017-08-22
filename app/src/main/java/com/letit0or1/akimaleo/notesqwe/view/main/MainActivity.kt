package com.letit0or1.akimaleo.notesqwe.view.main

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.letit0or1.akimaleo.notesqwe.Note
import com.letit0or1.akimaleo.notesqwe.R
import com.letit0or1.akimaleo.notesqwe.util.webdata.SyncHandler
import com.letit0or1.akimaleo.notesqwe.util.webdata.SyncWorker
import com.letit0or1.akimaleo.notesqwe.view.authorization.AuthorizationActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


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
            SyncWorker().downloadData(object : SyncHandler {
                override fun success(list: ArrayList<Note>) {
                    Log.i("SUCCESS GET", list.size.toString())
                }

                override fun error(exception: Exception) {
                    exception.printStackTrace()
                }
            })
        }

        write_time.setOnClickListener {
            //            val database = FirebaseDatabase.getInstance()
//            val myRef = database.getReference(FirebaseUtil.instance.firebaseAuth.currentUser?.email?.replace('.', '-', true))
//            myRef.setValue(format.format(Calendar.getInstance().time))
//            SyncWorker().putData(listOf({ Note() }))
            var w = ArrayList<Note>()
            w.add(Note("label", "zxc", "ehjghj", Calendar.getInstance().time))
            w.add(Note("laqfqfwbel", "213", "ghj", Calendar.getInstance().time))
            w.add(Note("''''l", "6666", "124124", Calendar.getInstance().time))
            SyncWorker().putData(w)
        }

        login.setOnClickListener {
            val intent = Intent(this, AuthorizationActivity::class.java)
            startActivity(intent)
        }

    }
}
