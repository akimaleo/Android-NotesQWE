package com.letit0or1.akimaleo.notesqwe.data.web

import com.google.firebase.database.*
import com.letit0or1.akimaleo.notesqwe.data.cache.Note
import com.letit0or1.akimaleo.notesqwe.util.FirebaseUtil
import io.reactivex.Observable

/**
 * Created by akimaleo on 22.08.17.
 */

class SyncWorkerImpl : SyncWorker {

    override fun add(note: Note) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addAll(notes: List<Note>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteItem(note: Note) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAll(): Observable<List<Note>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun isAuthorized(): Boolean = FirebaseUtil.instance.firebaseAuth.currentUser != null

    /**
     * GET FIREBASE LIST REFERENCE
     */
    private fun reference(): DatabaseReference =
            FirebaseUtil.instance.firebaseDatabase.getReference(FirebaseUtil.instance.firebaseAuth.currentUser?.uid).child("list")
}
