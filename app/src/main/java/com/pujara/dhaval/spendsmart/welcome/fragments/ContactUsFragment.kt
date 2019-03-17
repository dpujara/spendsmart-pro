package com.pujara.dhaval.spendsmart.welcome.fragments

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import com.pujara.dhaval.spendsmart.NavigationHost
import com.pujara.dhaval.spendsmart.R
import com.pujara.dhaval.spendsmart.welcome.presenter.welcomebottom.IWelcomeBottomPresenter
import com.pujara.dhaval.spendsmart.welcome.presenter.welcomebottom.WelcomeBottomPresenter
import com.pujara.dhaval.spendsmart.welcome.view.IWelcomeBottomView
import kotlinx.android.synthetic.main.contactus_welcome_fragment.view.*
import android.widget.FrameLayout
import android.view.animation.AlphaAnimation


class ContactUsFragment : Fragment(),IWelcomeBottomView {
    private lateinit var welcomeBottomPresenter: IWelcomeBottomPresenter
    private var snackbar : Snackbar? = null
    private var progressBarHolder: FrameLayout? = null
    private var inAnimation: AlphaAnimation? = null
    private var outAnimation: AlphaAnimation? = null
    private var progressBar : ProgressBar? = null

    override fun onFeedbackResult(result: Boolean) {
        hideProgressbar()
        if(result){
            emptyEditText()
            clearFocus()
            view?.let { hideSoftKeyboard(it)}

            displayError(getString(R.string.feedback_successfully_message))
        }else{
            displayError(getString(R.string.something_went_wrong))
        }
    }

    private fun emptyEditText() {
        view?.edittext_username_contact_us?.text = null
        view?.edittext_subject_contact_us?.text = null
        view?.edittext_subject_feedback?.text = null
    }

    private fun clearFocus() {
        view?.edittext_username_contact_us?.clearFocus()
        view?.edittext_subject_feedback?.clearFocus()
        view?.edittext_subject_contact_us?.clearFocus()
    }

    override fun displayError(error: String) {
        hideProgressbar()
        snackbar  = view?.let { Snackbar.make(it,error, Snackbar.LENGTH_LONG) }
        snackbar?.show()
    }

    override fun onBackNavigationClicked() {
        (activity as NavigationHost).backPressed()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.contactus_welcome_fragment, container, false)
        welcomeBottomPresenter = WelcomeBottomPresenter(this)
        progressBarHolder =  view.findViewById(R.id.progressBarHolder)
        progressBar = view.findViewById(R.id.progress_appbar_contactus)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(!isAdded){
            return
        }
        view.app_bar_contact.setNavigationOnClickListener { welcomeBottomPresenter.onBackNavigationClicked() }

        view.send_feedback_button_contact_us.setOnClickListener {
            showProgressbar()
            welcomeBottomPresenter.onSendFeedbackClicked(
                view.edittext_username_contact_us.text.toString(),
                view.edittext_subject_contact_us.text.toString(),
                view.edittext_subject_feedback.text.toString())
        }
    }

    private fun showProgressbar(){
        inAnimation = AlphaAnimation(0f, 1f)
        inAnimation?.duration = 200
        progressBarHolder?.animation = inAnimation
        progressBarHolder?.visibility = View.VISIBLE
        progressBar?.visibility = View.VISIBLE
    }

    private fun hideProgressbar(){
        outAnimation = AlphaAnimation(1f, 0f)
        outAnimation?.duration = 200
        progressBarHolder?.animation = outAnimation
        progressBarHolder?.visibility = View.GONE
        progressBar?.visibility = View.GONE
    }

    private fun hideSoftKeyboard(view: View) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}