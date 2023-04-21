package com.wny2023.mp01seoulculture.models

data class NaverBlogReviewResponse constructor(var total:Int, var display:Int,var items:MutableList<ReviewItem> )

//아이템 1개의 데이터 클래스
data class  ReviewItem ( //길어서 줄바꿈
    var title:String,
    var link:String,
    var description:String,
    var bloggername:String,
    var bloggerlink:String,
    var postdate:String,
)
