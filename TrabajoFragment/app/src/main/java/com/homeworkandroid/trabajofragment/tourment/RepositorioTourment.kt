package com.homeworkandroid.trabajofragment.tourment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.homeworkandroid.trabajofragment.events.Events

class RepositorioTourment {
    fun getTourmentData(): LiveData<MutableList<Tourment>> {

        val mutableTourment = MutableLiveData<MutableList<Tourment>>()
        FirebaseFirestore.getInstance().collection("Tourment").get().addOnSuccessListener{ result ->
            var listTourment = mutableListOf<Tourment>()
            for(document in result){
                val imgUrl:String? = document.getString("imgUrl")
                val titulo: String? = document.getString("titulo")
                val descripcion:String? = document.getString("descripcion")
                val texto:String? = document.getString("texto")
                var urlStreaming:String? = document.getString("urlStreaming")
                if(urlStreaming == null) urlStreaming=""
                var coordenadas:String? = document.getString("coordenadas")
                if(coordenadas==null) coordenadas=""

                val torneo = Tourment(imgUrl!!, titulo!!, descripcion!!,texto!!,urlStreaming!!,coordenadas!!)
                listTourment.add(torneo)
            }
            mutableTourment.value = listTourment
        }
        return mutableTourment
    }
}