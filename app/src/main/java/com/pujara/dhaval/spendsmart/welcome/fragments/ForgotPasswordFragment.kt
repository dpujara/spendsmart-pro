package com.pujara.dhaval.spendsmart.welcome.fragments

import android.os.Bundle
import android.support.design.widget.Snackbar

import android.support.v4.app.Fragment
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pujara.dhaval.spendsmart.NavigationHost
import com.pujara.dhaval.spendsmart.R
import com.pujara.dhaval.spendsmart.welcome.presenter.forgotpassword.Forgotpasswordpresenter
import com.pujara.dhaval.spendsmart.welcome.presenter.forgotpassword.IForgotpasswordPresenter
import com.pujara.dhaval.spendsmart.welcome.view.IForgotpasswordView
import kotlinx.android.synthetic.main.forgot_password_fragment.view.*

class ForgotPasswordFragment : Fragment(), IForgotpasswordView {
    override fun onBackNavigationClicked() {
        (activity as NavigationHost).backPressed()
    }

    private lateinit var forgotpasswordpresenter: IForgotpasswordPresenter
    var snackbar : Snackbar? = null

    override fun onPasswordSuccess(message: String) {
        snackbar  = view?.let { Snackbar.make(it,message, Snackbar.LENGTH_LONG) }
        snackbar?.show()
        view?.edittext_username_forgot_password?.text = null
    }

    override fun onPasswordFailure(message: String) {
        snackbar  = view?.let { Snackbar.make(it,message, Snackbar.LENGTH_LONG) }
        snackbar?.show()
    }

    override fun onForgotResult(message: String) {
        snackbar  = view?.let { Snackbar.make(it,message, Snackbar.LENGTH_LONG) }
        snackbar?.show()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.forgot_password_fragment, container, false)
        forgotpasswordpresenter = Forgotpasswordpresenter(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(!isAdded){
            return
        }
        view.reset_password_button_login.setOnClickListener { forgotpasswordpresenter.onResetButtonClick(view.edittext_username_forgot_password.text.toString()) }

        view.app_bar_forgot.setNavigationOnClickListener { forgotpasswordpresenter.onBackNavigationClicked() }
    }
}