package com.letit0or1.akimaleo.notesqwe.util.webdata

import com.google.firebase.database.*
import com.letit0or1.akimaleo.notesqwe.Note
import com.letit0or1.akimaleo.notesqwe.util.FirebaseUtil
import com.letit0or1.akimaleo.notesqwe.util.database.NO2Notes


/**
 * Created by akimaleo on 22.08.17.
 */

class SyncWorker private constructor() {

    private object Holder {
        val INSTANCE = SyncWorker()
    }

    companion object {
        val instance: SyncWorker by lazy { Holder.INSTANCE }
        fun mergeNotes(first: ArrayList<Note>, second: ArrayList<Note>): ArrayList<Note> {
            val list: ArrayList<Note> = ArrayList()
            val hashMap: java.util.HashMap<String, Note> = java.util.HashMap();

            for (i in first) {

                hashMap.put(i.uid, i)
                var lastCount = hashMap.count()

                for (j in second) {

                    if (i.uid == j.uid) {
                        hashMap[i.uid] = if (i.editDate > j.editDate) i else j
                        continue
                    }

                    if (hashMap.containsKey(j.uid)) continue

                    hashMap.put(j.uid, j)
                }
            }
            return ArrayList<Note>(hashMap.values)
        }
    }

    fun isAuthorized(): Boolean = FirebaseUtil.instance.firebaseAuth.currentUser != null

    //PUT DATA TO FIREBASE AND CACHE
    fun putItem(note: Note) {
        if (isAuthorized()) {
            val datas = NO2Notes.instance.getAllNotes();
            datas.add(note)
            reference().setValue(datas)
        }
        NO2Notes.instance.save(note)
    }

    fun removeItem(uid: String) {

        if (uid.isEmpty())
            return

        //GET OLD DATA AND COLLECTION
        val note = NO2Notes.instance.getItem(uid)
        val list = NO2Notes.instance.getAllNotes()

        //REMOVE ITEM WITH UID
        list.remove(note)

        //CACHE NEW COLLECTION
        NO2Notes.instance.save(list)
        //PUSH TO WEB
        if (isAuthorized()) {
            reference().setValue(list)
        }
    }


    //DOWNLOAD, MERGE, CACHE AND PUSH TO WEB
    fun syncData() {
        if (isAuthorized()) {
            downloadData(object : SyncHandler {

                override fun success(list: ArrayList<Note>) {
                    val newList = mergeNotes(list, NO2Notes.instance.getAllNotes());
                    reference().setValue(newList)
                    NO2Notes.instance.clearDb()
                    if (newList.size > 0)
                        NO2Notes.instance.save(newList)
                }

                override fun error(exception: Exception) {
                    exception.printStackTrace()
                }
            })
            reference().push()
        }
    }

    //RETRIEVE DATA FROM WEB
    private fun downloadData(hanler: SyncHandler?) {
        reference().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val t = object : GenericTypeIndicator<ArrayList<Note>>() {
                }

                var value = dataSnapshot.getValue(t)
                if (value == null) {
                    value = ArrayList()
                }

                if (hanler != null)
                    hanler.success(value)

                NO2Notes.instance.clearDb()
                NO2Notes.instance.save(value)
            }

            override fun onCancelled(error: DatabaseError) {
                if (hanler != null)
                    error.toException()
                // Failed to read value
            }
        })
    }

    //GET FIREBASE REFERENCE
    private fun reference(): DatabaseReference {
        return FirebaseUtil.instance.firebaseDatabase.getReference(FirebaseUtil.instance.firebaseAuth.currentUser?.uid)
    }
}
