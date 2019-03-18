package com.pujara.dhaval.spendsmart.dashboard.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.util.Log.d
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pujara.dhaval.spendsmart.R
import com.pujara.dhaval.spendsmart.dashboard.presenter.dialog.AddFriendPresenter
import com.pujara.dhaval.spendsmart.dashboard.presenter.dialog.IAddFriendPresenter
import com.pujara.dhaval.spendsmart.dashboard.view.IAddFriendView
import kotlinx.android.synthetic.main.full_screen_dialog_friend.view.*

class FullscreenDialog : DialogFragment(), IAddFriendView {
    override fun error(error: String) {
        displayMessage(error)
    }

    override fun added(email: String) {
        view?.let { hideSoftKeyboard(it) }
        view?.edittext_username_fullscreen_dialog?.text = null
        displayMessage("$email added successfully")
    }

    var snackbar: Snackbar? = null
    private var mFirebaseAuth = FirebaseAuth.getInstance()
    val user: String? = mFirebaseAuth.currentUser?.uid
    val userEmail : String = mFirebaseAuth.currentUser?.email.toString()


    private lateinit var addFriendPresenter: IAddFriendPresenter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.full_screen_dialog_friend, container, false)
        addFriendPresenter = AddFriendPresenter(this)

        rootView.button_close.setOnClickListener {
            view?.let { hideSoftKeyboard(it) }
            fragmentManager?.popBackStack()
        }
        return rootView
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!isAdded) {
            return
        }

        view.button_action.setOnClickListener {
            val email = view.edittext_username_fullscreen_dialog.text.toString()

            if (email.equals("")) {
                displayMessage("Email can not be  empty !!!")
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                displayMessage("Invalid Email id !!!")
            } else if(userEmail == email){
                displayMessage("Email id can't be same as current email id !!!")
            }
            else {
                val emailReference =
                    FirebaseDatabase.getInstance().reference.child("root").child("users")
                val query = emailReference.orderByChild("email").equalTo(FirebaseAuth.getInstance().currentUser?.email.toString())
                val valueEventListener = object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {}
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (postSnapshot in dataSnapshot.children) {
                            for (childPostSnapshot: DataSnapshot in postSnapshot.children) {
                                if (childPostSnapshot.key == "name") {
                                    val name = childPostSnapshot.value.toString()
                                    d("cvhgdcv",name)
                                    addFriendPresenter.addFriend(email,user,name)
                                }
                            }
                        }
                    }
                }
                query.addListenerForSingleValueEvent(valueEventListener)
            }
        }
    }

    fun displayMessage(message: String) {
        snackbar = view?.let { Snackbar.make(it, message, Snackbar.LENGTH_LONG) }
        snackbar?.show()
    }

    override fun alreadyAdded(name: String?, email: String?) {
        displayMessage("$email is already added as $name.")
    }

    private fun hideSoftKeyboard(view: View) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}