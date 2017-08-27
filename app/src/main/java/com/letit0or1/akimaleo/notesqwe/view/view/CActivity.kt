package com.letit0or1.akimaleo.notesqwe.view.view

import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by akimaleo on 23.08.17.
 */

open class CActivity : AppCompatActivity(), CView {

    private lateinit var loadingDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog = LoadingDialog(this)
//        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

    }

    override fun showLoading() {
        loadingDialog.show()
    }

    override fun hideLoading() {
        loadingDialog.dismiss()
    }
}
