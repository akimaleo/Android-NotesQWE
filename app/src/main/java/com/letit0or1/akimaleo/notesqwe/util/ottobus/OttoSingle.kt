package com.letit0or1.akimaleo.notesqwe.util.ottobus

import com.squareup.otto.Bus

/**
 * Created by akimaleo on 28.08.17.
 */

internal class OttoSingle private constructor() {

    val bus: Bus
        get

    init {
        bus = Bus()
    }

    companion object {
        val instance = OttoSingle()
    }
}
