package com.letit0or1.akimaleo.notesqwe.view.view

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.letit0or1.akimaleo.notesqwe.R

/**
 * Created by akimaleo on 24.08.17.
 */

class LoadingDialog : ProgressDialog {

    constructor(context: Context) : super(context) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_dialog)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.setCancelable(false)
    }
}
