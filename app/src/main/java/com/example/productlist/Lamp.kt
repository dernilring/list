package com.example.productlist

import java.util.UUID

data class Lamp(
    val id : String = UUID.randomUUID().toString(),
    val title : String,
    val price : Double,
    val photoId : Int,
    val isInCard : Boolean = false
)
