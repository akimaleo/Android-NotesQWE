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

    //PUT DATA TO FIREBASE AND CACHE
    fun putData(list: ArrayList<Note>) {
        reference().setValue(list)
        cacheData(list)
    }

    //CACHE DATA TO NO2Notes
    private fun cacheData(list: ArrayList<Note>) {
        NO2Notes.instance.clearDb()
        if (list.size > 0)
            NO2Notes.instance.save(list)
    }

    //FORCE PUSH DATA TO WEB
    public fun syncData() {
        if (FirebaseUtil.instance.firebaseAuth.currentUser != null) {
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

    private fun mergeNotes(first: ArrayList<Note>, second: ArrayList<Note>): ArrayList<Note> {
        val list: ArrayList<Note> = ArrayList()
        val hashMap: HashMap<String, Note> = HashMap();

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
        return list
    }


    //RETRIEVE DATA FROM WEB
    public fun downloadData(hanler: SyncHandler?) {
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
                cacheData(value)
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
