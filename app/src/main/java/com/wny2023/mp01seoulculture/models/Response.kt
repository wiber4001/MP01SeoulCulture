package com.wny2023.mp01seoulculture.models

data class Response constructor(var culturalEventInfo:ResultShell)

data class ResultShell (var list_total_count:Int, var RESULT:Result, var row:MutableList<Item>)

data class Result (var CODE:String, var MESSAGE:String)
