package com.pujara.dhaval.spendsmart.welcome.fragments

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import com.google.firebase.auth.FirebaseUser
import com.pujara.dhaval.spendsmart.NavigationHost
import com.pujara.dhaval.spendsmart.R
import com.pujara.dhaval.spendsmart.welcome.presenter.login.ILoginPresenter
import com.pujara.dhaval.spendsmart.welcome.presenter.login.LoginPresenter
import com.pujara.dhaval.spendsmart.welcome.view.ILoginView
import kotlinx.android.synthetic.main.login_fragment.*


class LoginFragment : Fragment(), ILoginView {

    private lateinit var loginPresenter: ILoginPresenter
    private var progressBar: ProgressBar? = null
    var snackbar: Snackbar? = null

    override fun navigateToForgotFragment() {
        (activity as NavigationHost).navigateTo(
            ForgotPasswordFragment(),
            true,
            true,
            R.anim.slide_in_bottom,
            R.anim.fade_out,
            R.anim.fade_in,
            R.anim.fade_out
        ) // Navigate to the next Fragment
    }

    override fun onSignInSuccess(user: FirebaseUser?) {
        fragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        (activity as NavigationHost).navigateTo(
            SliderFragment(),
            false,
            true,
            R.anim.slide_in_bottom,
            R.anim.fade_out,
            0,
            0
        )
    }

    override fun onSignInFailure(exception: String?) {
        snackbar = view?.let { Snackbar.make(it, "Invalid username/password !!!", Snackbar.LENGTH_LONG) }
        snackbar?.show()
        hideProgress()
    }

    override fun showProgress() {
        progressBar?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar?.visibility = View.INVISIBLE
    }

    override fun disableInput() {
        setInputs(true)
    }

    override fun enableInput() {
        setInputs(false)
    }

    override fun onLoginSuccess() {
        snackbar = view?.let { Snackbar.make(it, "Successfully Logged In!!!", Snackbar.LENGTH_LONG) }
        snackbar?.show()
    }

    override fun navigateToWelcomeFragment() {
        (activity as NavigationHost).backPressed()
    }

    override fun onLoginResult(message: String) {
        val snackbar: Snackbar? = view?.let { Snackbar.make(it, message, Snackbar.LENGTH_LONG) }
        snackbar?.show()
        hideProgress()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.login_fragment, container, false)
        loginPresenter = LoginPresenter(this)
        progressBar = view.findViewById(R.id.progress_appbar_login)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back_button_login.setOnClickListener {
            hideSoftKeyboard(view)
            loginPresenter.onBackButtonClicked()
        }
        done_button_login.setOnClickListener {
            hideSoftKeyboard(view)
            loginPresenter.onLogin(edittext_username_login.text.toString(), edittext_password_login.text.toString())
        }
        textViewForgotPassword.setOnClickListener { loginPresenter.onForgotPasswordClicked() }
    }

    private fun setInputs(boolean: Boolean) {
        edittext_username_login.isEnabled = boolean
        edittext_password_login.isEnabled = boolean
    }

    private fun hideSoftKeyboard(view: View) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}