package com.pujara.dhaval.spendsmart.welcome.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pujara.dhaval.spendsmart.NavigationHost
import com.pujara.dhaval.spendsmart.welcome.presenter.signup.ISignupPresenter
import com.pujara.dhaval.spendsmart.welcome.presenter.signup.SignupPresenter
import com.pujara.dhaval.spendsmart.welcome.view.ISignupView
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.signup_fragment.*
import kotlinx.android.synthetic.main.signup_fragment.view.*
import android.text.Editable
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.pujara.dhaval.spendsmart.R

class SignUpFragment : Fragment(),ISignupView {

    private lateinit var signupPresenter: ISignupPresenter

    override fun emptyEdittext(editText: EditText) {
        editText.text = null
    }

    override fun setEditTextDrawable(drawable: Int,editText: EditText) {
        editText.setCompoundDrawablesWithIntrinsicBounds(0,0,drawable,0)
    }
    override fun showProgress() {

    }

    override fun hideProgress() {

    }

    override fun enableInput() {
        setInputs(true)
    }

    override fun disableInput() {
        setInputs(false)
    }

    override fun onSignUpResult(message: String) {
        setInputs(true)
        hideProgress()
        fragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        (activity as NavigationHost).navigateTo(SliderFragment(),false,true,R.anim.slide_in_bottom, R.anim.fade_out,R.anim.fade_in,R.anim.fade_out)
    }

    override fun navigateToWelcomeFragment() {
        (activity as NavigationHost).backPressed()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.signup_fragment, container, false)
        signupPresenter = SignupPresenter(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(!isAdded){
            return
        }
        view.back_button_signup.setOnClickListener {
            hideSoftKeyboard(view)
            signupPresenter.onBackButtonClicked()
        }

        view.done_button_signup.setOnClickListener {
            showProgress()
            signupPresenter.registerNewUser(signup_edittext_name.text.toString(), signup_edittext_email.text.toString(),signup_edittext_password.text.toString())
        }

        view.signup_edittext_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged( s: CharSequence, start: Int, before: Int, count: Int) {
                signupPresenter.displayEdittextDrawable(s,signup_edittext_email)
            }
        })

        view.signup_edittext_name.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                signupPresenter.displayEdittextDrawable(s,signup_edittext_name)
            }
        })

        view.signup_edittext_name.setOnTouchListener(object : View.OnTouchListener{
            @SuppressLint("ClickableViewAccessibility")
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                remoDrawable(signup_edittext_name,event)
                return false
            }
        })

        view.signup_edittext_email.setOnTouchListener(object : View.OnTouchListener{
            @SuppressLint("ClickableViewAccessibility")
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                remoDrawable(signup_edittext_email,event)
                return false
            }
        })
    }

    private fun hideSoftKeyboard(view: View) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun remoDrawable(edittext: TextInputEditText,event: MotionEvent?) {
        val drawableRight = 2
        if(event?.action == MotionEvent.ACTION_DOWN && edittext.text.toString().isNotEmpty()) {
            if(event.rawX >= (edittext.right - edittext.compoundDrawables[drawableRight].bounds.width())) {
                edittext.requestFocus()
                signupPresenter.emptyNameEdittext(edittext)
            }
        }
    }

    private fun setInputs(boolean: Boolean) {
        signup_edittext_email.isEnabled = boolean
        signup_edittext_name.isEnabled = boolean
        signup_edittext_password.isEnabled = boolean
    }
}