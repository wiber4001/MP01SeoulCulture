package com.wny2023.mp01seoulculture.models

import java.io.Serializable

data class Member(var id:String, var pass:String, var email:String, var imgUrl:String, var path:String):Serializable
