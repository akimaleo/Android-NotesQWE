package com.letit0or1.akimaleo.notesqwe.data

import com.letit0or1.akimaleo.notesqwe.data.web.SyncWorker
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by kawa on 19.03.2018.
 */
@Singleton
class DataAccessPoint {

    @Inject
    lateinit var syncWorker: SyncWorker

}