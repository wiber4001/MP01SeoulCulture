package com.wny2023.mp01seoulculture.models

import android.net.Uri
import java.io.Serializable

data class Review(
    var id:String,
    var reviewImgs:MutableList<Uri>,
    var reviewTitle:String?,
    var reviewLong:String?,
    var reviewPlace: String,
    var reviewEquip: String,
    var reviewContent: String
):Serializable
