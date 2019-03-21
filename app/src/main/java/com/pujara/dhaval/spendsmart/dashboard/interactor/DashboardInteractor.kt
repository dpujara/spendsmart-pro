package com.pujara.dhaval.spendsmart.dashboard.interactor

import android.annotation.SuppressLint
import android.util.Log
import android.util.Log.d
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.pujara.dhaval.spendsmart.dashboard.model.FriendBalance
import com.pujara.dhaval.spendsmart.dashboard.model.PersonalExpenseData
import com.pujara.dhaval.spendsmart.dashboard.view.*
import java.text.SimpleDateFormat

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
                                if (name != null) {
                                    friendData["name"] = name
                                }
                                friendInsertRefernece.child(friendData["userid"].toString()).setValue(friendData)
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

    @SuppressLint("SimpleDateFormat")
    override fun AddPersonalExpenseFirebase(
        iAddExpenseView: IAddExpenseView,
        descr: String,
        amountExpense: String,
        date: String,
        expense: String,
        user: String?
    ) {
        val expenseData = HashMap<String, Any>()
        expenseData["description"] = descr
        expenseData["amount"] = amountExpense
        expenseData["expense"] = expense

        expenseData["day"] = date.split("/")[1]
        expenseData["month"] = date.split("/")[0]
        expenseData["year"] = date.split("/")[2]
        val format = SimpleDateFormat("mm/dd/yyyy")
        val date1 = format.parse(date)
        expenseData["date"] = date1.time
        val singleExpenseAddRef =
            FirebaseDatabase.getInstance().reference.child("root").child("users").child(user.toString())
                .child("personalexpenses").push()
        val mUniqueId = singleExpenseAddRef.key
        expenseData["uniqueKey"] = mUniqueId.toString()

        singleExpenseAddRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                iAddExpenseView.expenseAdded("Added Successfully !!!")
            }

            override fun onCancelled(p0: DatabaseError) {}
        })
        singleExpenseAddRef.setValue(expenseData)

        val activityExpenseAddRef =
            FirebaseDatabase.getInstance().reference.child("root").child("users").child(user.toString())
                .child("activity")
        var activity = "You"
        val activityData = HashMap<String, Any>()
        if (expense == "expense") {
            activity += "Spent $amountExpense @ $descr"
            activityData["expense"] = activity
        } else {
            activity += "Earned $amountExpense @ $descr"
            activityData["income"] = activity
        }
        activityData["type"] = "personal"
        activityData["day"] = date.split("/")[1]
        activityData["month"] = date.split("/")[0]
        activityData["year"] = date.split("/")[2]
        val mGroupId = activityExpenseAddRef.push().key
        activityData["uniqueId"] = mGroupId.toString()
        activityExpenseAddRef.child(mUniqueId.toString()).setValue(activityData)
    }

    override fun fetchPersonalExpenseFirebase(iPersonalExpenseView: IPersonalExpenseView, user: String?) {
        val expenseList: ArrayList<PersonalExpenseData> = ArrayList()
        val expenseListReference =
            FirebaseDatabase.getInstance().reference.child("root").child("users").child(user.toString())
                .child("personalexpenses")

        val expenseListListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val children = dataSnapshot.children
                expenseList.clear()
                children.forEach {
                    val list = it.getValue(PersonalExpenseData::class.java)
                    if (list != null) {
                        expenseList.add(list)
                    }
                }

                iPersonalExpenseView.setExpenseList(expenseList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("tagDash", "loadPost:onCancelled", databaseError.toException())
            }
        }
        expenseListReference.addValueEventListener(expenseListListener)
    }

    override fun UpdatePersonalExpenseFirebase(
        iAddExpenseView: IAddExpenseView,
        uniqueKey: String,
        descr: String,
        amount: String,
        date: String,
        expense: String,
        user: String?
    ) {
        val expenseData = HashMap<String, Any>()
        expenseData["description"] = descr
        expenseData["amount"] = amount
        expenseData["expense"] = expense
        expenseData["day"] = date.split("/")[1]
        expenseData["month"] = date.split("/")[0]
        expenseData["year"] = date.split("/")[2]
        val format = SimpleDateFormat("mm/dd/yyyy")
        val date1 = format.parse(date)
        expenseData["date"] = date1.time
        expenseData["uniqueKey"] = uniqueKey
        d("uniqiei", uniqueKey)
        FirebaseDatabase.getInstance().reference.child("root").child("users").child(user.toString())
            .child("personalexpenses").child(uniqueKey).setValue(expenseData)
        iAddExpenseView.updatedData("Updated Data")
    }

    override fun deletePersonalExpenseFirebase(iAddExpenseView: IAddExpenseView, uniqueKey: String, user: String?) {
        FirebaseDatabase.getInstance().reference.child("root").child("users").child(user.toString())
            .child("personalexpenses").child(uniqueKey).removeValue()
        iAddExpenseView.updatedData("Deleted Data")
    }

    override fun fetchFriendsFirebase(iCreateGroupView: ICreateGroupView, user: String?) {
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
                iCreateGroupView.setFriendList(friendList)
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        }
        friendListReference.addValueEventListener(friendListListener)
    }
}