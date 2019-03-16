package com.pujara.dhaval.spendsmart.welcome.model

interface IUser {
    val email:String
    val password:String
    val name:String
    val isPasswordLengthValid: Boolean
    val isUsernameEmpty: Boolean
    val isUsernameValid : Boolean
    val isNameEmpty:Boolean
    val isStringEmpty: Boolean
    var stringValidation: String
}