package com.wny2023.mp01seoulculture.models

import java.io.Serializable

data class Review(
    var id:String,
    var img01:String?,
    var img02:String?,
    var img03:String?,
    var img04:String?,
    var reviewLong:String?,
    var reviewPlace: String,
    var reviewEquip: String,
    var reviewContent: String
):Serializable
