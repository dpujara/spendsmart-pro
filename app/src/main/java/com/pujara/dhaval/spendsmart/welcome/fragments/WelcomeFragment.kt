package com.pujara.dhaval.spendsmart.welcome.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.FrameLayout
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
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
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private var progressBarHolder: FrameLayout? = null
    private var inAnimation: AlphaAnimation? = null
    private var outAnimation: AlphaAnimation? = null
    private var progress: ProgressDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.welcome_fragment, container, false)
        welcomePresenter = WelcomePresenter(this)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("445238201115-3tfbgrr1ds9mj2i16t94l12n6t3gg0ul.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = activity?.let { GoogleSignIn.getClient(it, gso) }!!
        auth = FirebaseAuth.getInstance()
        progressBarHolder =  view.findViewById(R.id.progressBarHolder)
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

        view.button_welcom_google.setOnClickListener { signIn()}
    }

    private fun signIn() {
        showProgressbar()
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
                hideProgressbar()
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
                hideProgressbar()
            }
        }
    }

    private fun showProgressbar(){
        inAnimation = AlphaAnimation(0f, 1f)
        inAnimation?.duration = 200
        progressBarHolder?.animation = inAnimation
        progressBarHolder?.visibility = View.VISIBLE
        progress = ProgressDialog.show(activity,"",
            getString(R.string.loading_dialog), false)
    }

    private fun hideProgressbar(){
        outAnimation = AlphaAnimation(1f, 0f)
        outAnimation?.duration = 200
        progressBarHolder?.animation = outAnimation
        progressBarHolder?.visibility = View.GONE
        progress?.dismiss()
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    Log.d("user",user?.uid.toString())
                    revokeAccess()
                } else {
                    hideProgressbar()
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun revokeAccess() {
        auth.signOut()
        googleSignInClient.revokeAccess().addOnCompleteListener { hideProgressbar() }
    }
    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
}