package com.pujara.dhaval.spendsmart.welcome.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.pujara.dhaval.spendsmart.NavigationHost
import com.pujara.dhaval.spendsmart.R
import com.pujara.dhaval.spendsmart.welcome.presenter.welcomebottom.IWelcomeBottomPresenter
import com.pujara.dhaval.spendsmart.welcome.presenter.welcomebottom.WelcomeBottomPresenter
import com.pujara.dhaval.spendsmart.welcome.view.IWelcomeBottomView
import kotlinx.android.synthetic.main.policy_welcome_fragment.view.*
import kotlinx.android.synthetic.main.terms_welcome_fragment.view.*

class TermsFragment : Fragment(), IWelcomeBottomView {
    private var url : String? = null
    var progressBar : ProgressBar? = null

    override fun onFeedbackResult(result: Boolean) { }

    override fun displayError(error: String) { }

    override fun onBackNavigationClicked() {
        (activity as NavigationHost).backPressed()
    }

    private lateinit var welcomeBottomPresenter: IWelcomeBottomPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.terms_welcome_fragment, container, false);
        welcomeBottomPresenter = WelcomeBottomPresenter(this)
        progressBar = view.findViewById(R.id.policy_progressbar)
        url = getString(R.string.policy_url)
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(!isAdded){
            return
        }
        view.terms_webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                view.visibility =View.INVISIBLE
                progressBar?.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                view.visibility =View.VISIBLE
                progressBar?.visibility = View.INVISIBLE
            }

        }
        val settings = view.terms_webView.settings
        settings.domStorageEnabled = true
        view.terms_webView.loadUrl(url)
        view.app_bar_terms.setNavigationOnClickListener { welcomeBottomPresenter.onBackNavigationClicked() }
    }
}