package com.pujara.dhaval.spendsmart.welcome.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pujara.dhaval.spendsmart.NavigationHost
import com.pujara.dhaval.spendsmart.R
import com.pujara.dhaval.spendsmart.welcome.presenter.welcomebottom.IWelcomeBottomPresenter
import com.pujara.dhaval.spendsmart.welcome.presenter.welcomebottom.WelcomeBottomPresenter
import com.pujara.dhaval.spendsmart.welcome.view.IWelcomeBottomView
import kotlinx.android.synthetic.main.terms_welcome_fragment.view.*

class TermsFragment : Fragment(), IWelcomeBottomView {
    override fun onBackNavigationClicked() {
        (activity as NavigationHost).backPressed()
    }

    private lateinit var welcomeBottomPresenter: IWelcomeBottomPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.terms_welcome_fragment, container, false);
        welcomeBottomPresenter = WelcomeBottomPresenter(this)
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(!isAdded){
            return
        }
        view.app_bar_terms.setNavigationOnClickListener { welcomeBottomPresenter.onBackNavigationClicked() }
    }
}