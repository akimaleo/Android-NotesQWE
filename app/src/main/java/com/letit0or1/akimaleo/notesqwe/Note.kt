package com.letit0or1.akimaleo.notesqwe

import org.dizitart.no2.objects.Id
import java.io.Serializable
import java.util.*

/**
 * Created by akimaleo on 16.08.17.
 */

data class Note(@Id var label: String, var text: String, var date: String, var editTime: Date) : Serializable