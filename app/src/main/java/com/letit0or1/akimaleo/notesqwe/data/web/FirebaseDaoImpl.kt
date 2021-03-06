package com.letit0or1.akimaleo.notesqwe.data.web

import com.google.firebase.database.*
import com.letit0or1.akimaleo.notesqwe.data.cache.Note
import com.letit0or1.akimaleo.notesqwe.util.FirebaseUtil
import io.reactivex.Observable

/**
 * Created by akimaleo on 22.08.17.
 * @see FirebaseDao
 */

class FirebaseDaoImpl(var userId: String) : FirebaseDao {

    override fun add(note: Note) {
        if (isAuthorized) {
            val listRef = reference.push()
            listRef.setValue(note)
        }
    }

    override fun setAll(notes: List<Note>) {
        if (isAuthorized) {
            reference.setValue(notes)
        }
    }

    override fun deleteItem(note: Note) {
        if (isAuthorized) {
            reference.child(note.uid).removeValue()
        }
    }

    override fun getAll(): Observable<List<Note>> {
        var observable: Observable<List<Note>> = Observable.create<List<Note>> { }
        if (isAuthorized) {
            observable = Observable.create<List<Note>> {
                reference.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val t = object : GenericTypeIndicator<ArrayList<Note>>() {
                        }
                        var value = dataSnapshot.getValue(t)
                        if (value == null) {
                            value = ArrayList()
                        }
                        it.onNext(value)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        it.onError(error.toException())
                    }
                })
            }
        }
        return observable
    }

    /**
     * Check if user is authorized
     * also if synchronization is enabled
     */
    private val isAuthorized: Boolean get() = FirebaseUtil.instance.firebaseAuth.currentUser != null

    /**
     * GET FIREBASE LIST REFERENCE
     */
    private val reference: DatabaseReference get() = FirebaseUtil.instance.firebaseDatabase.getReference(userId).child("list")

    companion object {
        fun mergeNotes(first: ArrayList<Note>, second: ArrayList<Note>): ArrayList<Note> {
            val hashMap: java.util.HashMap<String, Note> = java.util.HashMap()

            for (i in first) {
                hashMap[i.uid] = i
                for (j in second) {

                    if (i.uid == j.uid) {
                        hashMap[i.uid] = if (i.editDate > j.editDate) i else j
                        continue
                    }

                    if (hashMap.containsKey(j.uid)) continue

                    hashMap.put(j.uid, j)
                }
            }
            return ArrayList(hashMap.values)
        }
    }
}
