package com.letit0or1.akimaleo.notesqwe.view.view

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.letit0or1.akimaleo.notesqwe.presentation.common.App

/**
 * Created by akimaleo on 23.08.17.
 */

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity(), BaseView {

    private lateinit var loadingDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog = LoadingDialog(this)
    }

    override fun showLoading() {
        loadingDialog.show()
    }

    override fun hideLoading() {
        loadingDialog.dismiss()
    }
}
