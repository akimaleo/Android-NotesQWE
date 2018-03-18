package com.letit0or1.akimaleo.notesqwe.firebase

import android.support.test.runner.AndroidJUnit4
import com.letit0or1.akimaleo.notesqwe.view.user.authorization.AuthorizationPresenter
import com.letit0or1.akimaleo.notesqwe.view.user.authorization.AuthorizationPresenterImpl
import com.letit0or1.akimaleo.notesqwe.view.user.authorization.AuthorizationView
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Created by kawa on 18.03.2018.
 */
@RunWith(AndroidJUnit4::class)
class FirebaseTest {

    lateinit var presenter: AuthorizationPresenter
    lateinit var logger: Logger
    lateinit var view: AuthorizationView

    val credentialEmail = "testEmail@gmail.com"
    val credentialPasword = "test_password"

    @Before
    fun init() {
        logger = LoggerFactory.getLogger("FirebaseTest")
        view = UserViewImpl(logger)
        presenter = AuthorizationPresenterImpl(view)
    }

    @Test
    fun register() {
        presenter.register(credentialEmail, credentialPasword)
    }

    @Test
    fun authorize() {
        presenter.login(credentialEmail, credentialPasword)
    }
}