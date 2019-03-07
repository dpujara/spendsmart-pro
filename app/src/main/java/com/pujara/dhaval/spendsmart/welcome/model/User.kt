package com.pujara.dhaval.spendsmart.welcome.model

import android.text.TextUtils
import android.util.Patterns

class User(override var email: String, override val password: String) : IUser {
    override var name: String = ""
    override val isUsernameEmpty: Boolean
        get() = TextUtils.isEmpty(email)
    override val isUsernameValid: Boolean
        get() = !Patterns.EMAIL_ADDRESS.matcher(email).matches()
    override val isPasswordLengthValid: Boolean
        get() =  password.length < 6
    override val isNameEmpty : Boolean
        get() = TextUtils.isEmpty(name)
}