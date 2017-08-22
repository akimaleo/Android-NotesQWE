package com.letit0or1.akimaleo.notesqwe.util.webdata

import com.google.firebase.database.*
import com.letit0or1.akimaleo.notesqwe.Note
import com.letit0or1.akimaleo.notesqwe.util.FirebaseUtil
import com.letit0or1.akimaleo.notesqwe.util.database.NO2
import org.dizitart.no2.objects.ObjectRepository
import org.dizitart.no2.objects.filters.ObjectFilters


/**
 * Created by akimaleo on 22.08.17.
 */

class SyncWorker private constructor() {

    private object Holder {
        val INSTANCE = SyncWorker()
    }

    companion object {
        val instance: SyncWorker by lazy { Holder.INSTANCE }
    }
    
    //PUT DATA TO FIREBASE AND CACHE
    fun putData(list: ArrayList<Note>) {
        reference().setValue(list)
        cacheData(list)
    }

    //CACHE DATA TO NO2
    private fun cacheData(list: ArrayList<Note>) {
        val repository: ObjectRepository<Note> = NO2.instance.db.getRepository(Note::class.java)
        repository.remove(ObjectFilters.ALL)
        repository.insert(list.toArray(Array<Note>(list.size, { i -> list.get(i) })))
    }

    //FORCE PUSH DATA TO WEB
    public fun uploadData() {
        reference().push()
    }

    //RETRIEVE DATA FROM WEB
    public fun downloadData(hanler: SyncHandler?) {
        reference().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val t = object : GenericTypeIndicator<ArrayList<Note>>() {
                }

                val value = dataSnapshot.getValue(t)

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

    //RETRIEVE DATA FROM CACHE
    public fun getCacheData(): ArrayList<Note> {
        val repository: ObjectRepository<Note> = NO2.instance.db.getRepository(Note::class.java)
        val dd = ArrayList<Note>()
        repository.find().forEach {
            dd.add(it)
        }
        return dd
    }

    //GET FIREBASE REFERENCE
    private fun reference(): DatabaseReference {
        return FirebaseUtil.instance.firebaseDatabase.getReference(FirebaseUtil.instance.firebaseAuth.currentUser?.uid)
    }
}
