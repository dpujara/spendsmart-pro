package com.pujara.dhaval.spendsmart.dashboard.model

import android.os.Parcelable
import java.io.Serializable

data class PersonalExpenseData(
    var year: String? = "",
    var month: String? = "",
    val amount: String? ="",
    val day : String? = "",
    val description : String? = "",
    val expense : String? = "",
    var uniqueKey : String? = "",
    var date : Long? = 0L
) : Serializable