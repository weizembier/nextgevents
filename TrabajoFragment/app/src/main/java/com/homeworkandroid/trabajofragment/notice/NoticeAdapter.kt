package com.homeworkandroid.trabajofragment.notice

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.homeworkandroid.trabajofragment.DetailsFragmentActivity
import com.homeworkandroid.trabajofragment.R
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse
import kotlinx.android.synthetic.main.item_notice.view.*

class NoticeAdapter(val context: FragmentNotice):RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder>() {

    private var noticeList = mutableListOf<Notice>()

    fun setListNotice(notice: MutableList<Notice>){
        noticeList = notice
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val v = inflate.inflate(R.layout.item_notice, parent, false)
        return NoticeViewHolder(v)
    }

    override fun getItemCount():Int{
        return noticeList.size
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        val notice = noticeList[position]
        holder.bindView(notice)
    }


    inner class NoticeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindView(notice: Notice){
            Glide.with(context).load(notice.imgUrl).into(itemView.imgNotice)
            if(notice.titulo.length<=40)
                itemView.tituloNotice.text= notice.titulo
            else{
                itemView.tituloNotice.text= notice.titulo.substring(0,40)+" ..."
            }
            if(notice.descripcion.length<=120)
                itemView.descNotice.text = notice.descripcion
            else{
                itemView.descNotice.text= notice.descripcion.substring(0,120)+" ..."
            }



            itemView.setOnClickListener{
                val intent =  Intent(itemView.context, DetailsFragmentActivity::class.java)
                intent.putExtra("tituloString", notice.titulo)
                intent.putExtra("descripcionString", notice.descripcion)
                intent.putExtra("imageView", notice.imgUrl)
                intent.putExtra("textoString", notice.texto)
                intent.putExtra("urlStreaming", notice.urlStreaming)
                intent.putExtra("coordenadas", notice.coordenadas)

                itemView.context.startActivity(intent)
            }


        }
    }


}