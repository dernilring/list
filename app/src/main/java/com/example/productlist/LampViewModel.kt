package com.example.productlist

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class LampViewModel : ViewModel() {
    val lampList = mutableStateListOf(
        Lamp( title = "lamp1", price = 100.0, photoId = R.drawable.lamp1),
        Lamp(title ="lamp2", price = 99.9, photoId =  R.drawable.lamp2, ),
        Lamp(title = "lamp3", price =79.9, photoId = R.drawable.lamp3 ),
        )
    fun addLamp(){
        lampList.add( Lamp( title = "lamp1", price = 100.0, photoId = R.drawable.lamp1))
    }
    fun removeLamp(lamp: Lamp){ lampList.remove(lamp)}
    fun toggleFavorite(lamp: Lamp) {
        val index = lampList.indexOfFirst { it.id == lamp.id }
        if (index != -1) {
            lampList[index] = lampList[index].copy(isInCard = !lampList[index].isInCard)
        }
    }
}