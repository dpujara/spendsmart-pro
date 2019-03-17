package com.pujara.dhaval.spendsmart.welcome.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.pujara.dhaval.spendsmart.NavigationHost
import com.pujara.dhaval.spendsmart.R
import com.pujara.dhaval.spendsmart.welcome.presenter.welcomebottom.IWelcomeBottomPresenter
import com.pujara.dhaval.spendsmart.welcome.presenter.welcomebottom.WelcomeBottomPresenter
import com.pujara.dhaval.spendsmart.welcome.view.IWelcomeBottomView
import kotlinx.android.synthetic.main.policy_welcome_fragment.view.*
import android.os.Handler

class PolicyFragment : Fragment(), IWelcomeBottomView {
    private var url : String? = null
    private var progress: ProgressDialog? = null
    override fun onFeedbackResult(result: Boolean) {}

    override fun displayError(error: String) { }

    override fun onBackNavigationClicked() {
        (activity as NavigationHost).backPressed()
    }
    private lateinit var welcomeBottomPresenter: IWelcomeBottomPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.policy_welcome_fragment, container, false)
        welcomeBottomPresenter = WelcomeBottomPresenter(this)
        progress = ProgressDialog.show(activity, "",
            getString(R.string.loading_dialog), true)
        url = getString(R.string.policy_url)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(!isAdded){
            return
        }
        view.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                progress?.dismiss();
            }
        }
        val settings = view.webView.settings
        settings.domStorageEnabled = true
        val handler = Handler()
        handler.postDelayed(Runnable {
            view.webView.loadUrl(url)
        }, 1000)
        view.app_bar_policy.setNavigationOnClickListener { welcomeBottomPresenter.onBackNavigationClicked() }
    }
}