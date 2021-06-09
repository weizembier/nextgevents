package com.homeworkandroid.trabajofragment.events

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore


class RepositorioEvents {

    fun getEventsData(): LiveData<MutableList<Events>> {

        val mutableEvents = MutableLiveData<MutableList<Events>>()
        FirebaseFirestore.getInstance().collection("Events").get().addOnSuccessListener{ result ->
            var listEvents = mutableListOf<Events>()
            for(document in result){
                val imgUrl:String? = document.getString("imgUrl")
                val titulo: String? = document.getString("titulo")
                val descripcion:String? = document.getString("descripcion")
                val texto:String? = document.getString("texto")
                var urlStreaming:String? = document.getString("urlStreaming")
                if(urlStreaming == null) urlStreaming=""
                var coordenadas:String? = document.getString("coordenadas")
                if(coordenadas==null) coordenadas=""

                val evento = Events(imgUrl!!, titulo!!, descripcion!!,texto!!,urlStreaming!!,coordenadas!!)
                listEvents.add(evento)
            }
            mutableEvents.value = listEvents
        }
        return mutableEvents
    }
}