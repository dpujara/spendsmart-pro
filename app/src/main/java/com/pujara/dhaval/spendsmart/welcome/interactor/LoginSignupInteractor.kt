package com.pujara.dhaval.spendsmart.welcome.interactor

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.pujara.dhaval.spendsmart.welcome.event.Event
import com.pujara.dhaval.spendsmart.welcome.view.IForgotpasswordView
import com.pujara.dhaval.spendsmart.welcome.view.ILoginView
import com.pujara.dhaval.spendsmart.welcome.view.ISignupView
import com.pujara.dhaval.spendsmart.welcome.view.IWelcomeBottomView

class LoginSignupInteractor : ILoginSignupInteractor {

    private val TAG = LoginSignupInteractor::class.java.name
    private var mFirebaseAuth = FirebaseAuth.getInstance()
    private var event = Event()
    private lateinit var database: DatabaseReference

    override fun forgotPassword(email: String, iForgotpasswordView: IForgotpasswordView) {
        mFirebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    event.onForgotPasswordSuccess("Email sent !!!", iForgotpasswordView)
                    Log.d(TAG, "Email sent.")
                } else {
                    event.onForgotPasswordFailure("Something went wrong!!", iForgotpasswordView)
                    Log.d(TAG, "Email was not sent.")
                }
            }
    }

    override fun doSignup(email: String, password: String, name: String, iSignupView: ISignupView) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    val user: String? = mFirebaseAuth.currentUser?.uid
                    database = FirebaseDatabase.getInstance().reference
                    val userData = HashMap<String, Any>()
                    userData["email"] = email
                    userData["password"] = password
                    userData["name"] = name
                    userData["userid"] = mFirebaseAuth.uid.toString()

                    if (user != null) {
                        database.child("root").child("users").child(user).setValue(userData)
                        event.onSignUpSuccess(user, iSignupView)
                    }
                } else {
                    event.onSignUpFailure(task.exception, iSignupView)
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                }
            }
    }

    override fun doSignIn(
        email: String,
        password: String,
        iLoginView: ILoginView
    ) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    val user: FirebaseUser? = mFirebaseAuth.currentUser
                    event.onSignInSuccess(user, iLoginView)
                } else {
                    Log.d(TAG, "signInWithEmail:failure")
                    val errorMessage = task.exception?.message
                    event.onSignInFailure(iLoginView, errorMessage)
                }
            }
    }

    override fun submitFeedback(
        email: String,
        subject: String,
        description: String,
        iWelcomeBottomView1: IWelcomeBottomView
    ) {
        val feedback = HashMap<String, Any>()
        feedback["email"] = email
        feedback["description"] = description
        feedback["subject"] = subject
        feedback["responded"] = "No"
        database = FirebaseDatabase.getInstance().reference
        database.child("root").child("feedback").push().setValue(feedback).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                event.onFeedbackResult(iWelcomeBottomView1, true)
            } else {
                event.onFeedbackResult(iWelcomeBottomView1, false)
            }
        }
    }
}