package com.letit0or1.akimaleo.notesqwe.view.user.authorization

import android.support.annotation.StringRes
import com.letit0or1.akimaleo.notesqwe.view.view.BaseView

/**
 * Created by akimaleo on 23.08.17.
 */

interface AuthorizationView : BaseView {
    fun reqRegister()
    fun loginSuccess()

    fun emailError(@StringRes error: Int)

    fun passwordError(@StringRes error: Int)

    fun invalidCredentials()

    fun successRestore()
    fun failureRestore()

}
