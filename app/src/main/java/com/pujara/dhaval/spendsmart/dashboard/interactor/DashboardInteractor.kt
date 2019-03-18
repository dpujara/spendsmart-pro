package com.pujara.dhaval.spendsmart.dashboard.interactor

import android.util.Log
import android.util.Log.d
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.pujara.dhaval.spendsmart.dashboard.model.FriendBalance
import com.pujara.dhaval.spendsmart.dashboard.view.IFriendListView
import com.pujara.dhaval.spendsmart.dashboard.view.IAddFriendView

class DashboardInteractor : IDashboardInteractor {

    override fun fetchFriendsFirebase(
        iFriendListView: IFriendListView?,
        user: String?
    ) {
        val friendList: MutableList<FriendBalance> = mutableListOf()

        val friendListReference =
            FirebaseDatabase.getInstance().reference.child("root").child("users").child(user.toString())
                .child("friends")

        val friendListListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val children = dataSnapshot.children
                friendList.clear()
                children.forEach {
                    val list = it.getValue(FriendBalance::class.java)
                    if (list != null) {
                        friendList.add(list)
                    }
                }
                iFriendListView?.setFriendList(friendList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("tagDash", "loadPost:onCancelled", databaseError.toException())
            }
        }
        friendListReference.addValueEventListener(friendListListener)
    }

    override fun AddFriendsFirebase(
        iAddFriendView: IAddFriendView,
        email: String,
        user: String?,
        name: String?
    ) {
        val singlefriendListReference =
            FirebaseDatabase.getInstance().reference.child("root").child("users").child(user.toString())
                .child("friends")

        val emailReference =
            FirebaseDatabase.getInstance().reference.child("root").child("users")

        var isPresent: Boolean = false
        val queryFriendList = singlefriendListReference.orderByChild("email").equalTo(email)

        val valueFriendEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val children = dataSnapshot.children
                println(children)
                d("tag", "1")
                children.forEach {
                    val list = it.getValue(FriendBalance::class.java)
                    if (list != null) {
                        if (list.email.toString() == email) {
                            isPresent = true
                            iAddFriendView.alreadyAdded(list.name, list.email)
                        }
                    }
                }
                if (!isPresent) {
                    val query = emailReference.orderByChild("email").equalTo(email)
                    val valueEventListener = object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            var userExists: Boolean = false
                            for (postSnapshot in dataSnapshot.children) {
                                userExists = true
                                iAddFriendView.added(email)
                                val userData = HashMap<String, Any>()
                                for (childPostSnapshot: DataSnapshot in postSnapshot.children) {
                                    if (childPostSnapshot.key == "email") {
                                        userData["email"] = childPostSnapshot.value.toString()
                                    }
                                    if (childPostSnapshot.key == "name") {
                                        userData["name"] = childPostSnapshot.value.toString()
                                    }
                                    if (childPostSnapshot.key == "userid") {
                                        userData["userid"] = childPostSnapshot.value.toString()
                                    }
                                }
                                singlefriendListReference.child(userData["userid"].toString()).setValue(userData)
                                val friendInsertRefernece =
                                    FirebaseDatabase.getInstance().reference.child("root").child("users")
                                        .child(userData["userid"].toString())
                                        .child("friends")


                                val friendData = HashMap<String, Any>()
                                friendData["userid"] = FirebaseAuth.getInstance().currentUser?.uid.toString()
                                friendData["email"] = FirebaseAuth.getInstance().currentUser?.email.toString()
                                if(name != null){
                                    friendData["name"] = name
                                }
                                friendInsertRefernece.push().setValue(friendData)
                            }
                            if (!userExists) {
                                iAddFriendView.error("User doesn't exists !!!")
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {}
                    }
                    query.addListenerForSingleValueEvent(valueEventListener)
                }
            }
        }
        queryFriendList.addListenerForSingleValueEvent(valueFriendEventListener)
    }
}