package com.letit0or1.akimaleo.notesqwe.firebase

import com.letit0or1.akimaleo.notesqwe.view.user.authorization.AuthorizationView
import org.slf4j.Logger

/**
 * Created by kawa on 18.03.2018.
 */
class UserViewImpl(var logger: Logger) : AuthorizationView {


    override fun showLoading() {
        logger.info("show loading animation")
    }

    override fun hideLoading() {
        logger.info("hide loading animation")
    }

    override fun reqRegister() {
        logger.warn("registration required")
    }

    override fun loginSuccess() {
        logger.info("login done")
    }

    override fun emailError(error: Int) {
        logger.error("email error")
    }

    override fun passwordError(error: Int) {
        logger.error("password error")
    }

    override fun invalidCredentials() {
        throw RuntimeException("invalidCredentials")
    }

    override fun successRestore() {
        logger.info("password restoring success")
    }

    override fun failureRestore() {
        throw RuntimeException("password restoring failure")
    }
}