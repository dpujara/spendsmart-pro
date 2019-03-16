package com.pujara.dhaval.spendsmart.welcome.Interactor

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.pujara.dhaval.spendsmart.welcome.event.Event
import com.pujara.dhaval.spendsmart.welcome.model.User
import com.pujara.dhaval.spendsmart.welcome.view.IForgotpasswordView
import com.pujara.dhaval.spendsmart.welcome.view.ILoginView
import com.pujara.dhaval.spendsmart.welcome.view.ISignupView

class LoginSignupInteractor : ILoginSignupInteractor{
    private val TAG = LoginSignupInteractor::class.java.getName()
    private var mFirebaseAuth = FirebaseAuth.getInstance()
    private var event = Event()
    private lateinit var database: DatabaseReference

    override fun forgotPassword(email: String,iForgotpasswordView: IForgotpasswordView) {
        mFirebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    event.onForgotPasswordSuccess("Email sent !!!",iForgotpasswordView)
                    Log.d(TAG, "Email sent.")
                }else{
                    event.onForgotPasswordFailure("Something went wrong!!",iForgotpasswordView)
                    Log.d(TAG, "Email was not sent.")
                }
            }
    }

    override fun doSignup(
        email: String,
        password: String,
        name: String,
        iSignupView: ISignupView
    ) {

        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user : String? = mFirebaseAuth.getCurrentUser()?.uid
                    database = FirebaseDatabase.getInstance().reference
                    val userData = User(email,password)
                    userData.name = name
                    if (user != null) {
                        database.child("root").child("users").child(user).setValue(userData)
                        event.onSignUpSuccess(user,iSignupView)
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                }
            }
    }

    override fun doSignIn(
        email: String,
        password: String,
        iLoginView: ILoginView
    ) {
        mFirebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    Log.d(TAG, "signInWithEmail:success")
                    val user :FirebaseUser? = mFirebaseAuth.getCurrentUser()
                    event.onSignInSuccess(user,iLoginView)
                }
                else{
                    Log.d(TAG, "signInWithEmail:failure")
                    val errorMessage = task.exception?.message
                    event.onSignInFailure(iLoginView,errorMessage)
                }
            }
    }
}