package com.homeworkandroid.trabajofragment.notice

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.homeworkandroid.trabajofragment.events.Events
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse
import kotlinx.android.synthetic.main.item_notice.view.*

class RepositorioNotice {

    fun getNoticeData():LiveData<MutableList<Notice>>{

        val mutableNotice = MutableLiveData<MutableList<Notice>>()
        FirebaseFirestore.getInstance().collection("Notice").get().addOnSuccessListener{ result ->
            var listNotice = mutableListOf<Notice>()
            for(document in result){
                val imgUrl:String? = document.getString("imgUrl")
                val titulo: String? = document.getString("titulo")
                val descripcion:String? = document.getString("descripcion")
                val texto:String? = document.getString("texto")
                var urlStreaming:String? = ""
           //     if(urlStreaming == null) urlStreaming=""
                var coordenadas:String? = ""
            //    if(coordenadas==null) coordenadas=""

                val noticia = Notice(imgUrl!!, titulo!!, descripcion!!,texto!!, urlStreaming!!,coordenadas!!)
                listNotice.add(noticia)
            }
            mutableNotice.value = listNotice
        }

        var newsApiClient = NewsApiClient("8f87981641304c29be3022eacb10d6d9")


        newsApiClient.getTopHeadlines(
            TopHeadlinesRequest.Builder()
                .language("es")
                .q("game")
                .build(),
            object : NewsApiClient.ArticlesResponseCallback {
                override fun onSuccess(response: ArticleResponse) {
                    var listNotice = mutableListOf<Notice>()
                    for(document in response.articles){
                        val imgUrl:String? =document.urlToImage          //document.getString("imgUrl")
                        val titulo: String? =document.title            //document.getString("titulo")
                        val descripcion:String? =document.description           //document.getString("descripcion")
                        val texto:String? = document.url
                        var urlStreaming:String? = ""
                        var coordenadas:String? = ""
                        val noticia = Notice(imgUrl!!, titulo!!, descripcion!!,texto!!, urlStreaming!!,coordenadas!!)
                        listNotice.add(noticia)
                    }
                    mutableNotice.value = listNotice


                }

                override fun onFailure(throwable: Throwable) {
                    println(throwable.message)
                }
            }
        )

        return mutableNotice
    }




}