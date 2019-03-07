package com.pujara.dhaval.spendsmart.welcome.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pujara.dhaval.spendsmart.welcome.presenter.welcome.IWelcomePresenter
import com.pujara.dhaval.spendsmart.welcome.presenter.welcome.WelcomePresenter
import com.pujara.dhaval.spendsmart.welcome.view.IWelcomeView
import com.pujara.dhaval.spendsmart.NavigationHost
import com.pujara.dhaval.spendsmart.R
import kotlinx.android.synthetic.main.welcome_fragment.view.*

class WelcomeFragment : Fragment(),IWelcomeView {
    override fun navigateToTermsFragment() {
        (activity as NavigationHost).navigateTo(TermsFragment(), true,true,R.anim.slide_in_bottom,R.anim.fade_out,R.anim.fade_in,R.anim.fade_out) // Navigate to the next Fragment
    }

    override fun navigateToPolicyFragment() {
        (activity as NavigationHost).navigateTo(PolicyFragment(), true,true,R.anim.slide_in_bottom,R.anim.fade_out,R.anim.fade_in,R.anim.fade_out) // Navigate to the next Fragment
    }

    override fun navigateToContactUsFragment() {
        (activity as NavigationHost).navigateTo(ContactUsFragment(), true,true,R.anim.slide_in_bottom,R.anim.fade_out,R.anim.fade_in,R.anim.fade_out) // Navigate to the next Fragment
    }

    override fun navigateToLoginFragment() {
        (activity as NavigationHost).navigateTo(LoginFragment(), true,true,R.anim.fade_in,R.anim.fade_out,R.anim.fade_in,R.anim.fade_out) // Navigate to the next Fragment
    }

    override fun navigateToSignUpFragment() {
        (activity as NavigationHost).navigateTo(SignUpFragment(), true, true,R.anim.fade_in,R.anim.fade_out,R.anim.fade_in,R.anim.fade_out) // Navigate to the next Fragment
    }

    private lateinit var welcomePresenter: IWelcomePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.welcome_fragment, container, false)
        welcomePresenter = WelcomePresenter(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(!isAdded){
            return
        }
        view.button_welcome_login.setOnClickListener{ welcomePresenter.onLoginButtonClicked()}

        view.button_welcome_signup.setOnClickListener{ welcomePresenter.onSignUpButtonClicked()}

        view.textView_welcome_terms.setOnClickListener{ welcomePresenter.onTermTextviewClicked()}

        view.textView_welcome_privacy.setOnClickListener { welcomePresenter.onPolicyTextviewClicked() }

        view.textView_welcome_contactus.setOnClickListener { welcomePresenter.onContactUsTextviewClicked() }
    }
}