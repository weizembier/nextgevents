package com.homeworkandroid.trabajofragment.tourment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.homeworkandroid.trabajofragment.DetailsFragmentActivity
import com.homeworkandroid.trabajofragment.R
import kotlinx.android.synthetic.main.item_tourment.view.*

class TourmentAdapter(val context: FragmentTourment): RecyclerView.Adapter<TourmentAdapter.TourmentViewHolder>() {

    private var tourmentList = mutableListOf<Tourment>()

    fun setListTourment(tourment: MutableList<Tourment>){
        tourmentList = tourment
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourmentViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val v = inflate.inflate(R.layout.item_tourment, parent, false)
        return TourmentViewHolder(v)

    }

    override fun getItemCount():Int{
        return tourmentList.size
    }

    override fun onBindViewHolder(holder: TourmentViewHolder, position: Int) {
        val events = tourmentList[position]

        holder.bindView(events)

    }


    inner class TourmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindView(tourment: Tourment){
            Glide.with(context).load(tourment.imgUrl).into(itemView.imgTourment)
            itemView.tituloTourment.text= tourment.titulo
            itemView.descTourment.text = tourment.descripcion

            itemView.setOnClickListener{
                val intent =  Intent(itemView.context, DetailsFragmentActivity::class.java)
                intent.putExtra("tituloString", tourment.titulo)
                intent.putExtra("descripcionString", tourment.descripcion)
                intent.putExtra("imageView", tourment.imgUrl)
                intent.putExtra("textoString", tourment.texto)
                intent.putExtra("urlStreaming", tourment.urlStreaming)
                intent.putExtra("coordenadas", tourment.coordenadas)

                itemView.context.startActivity(intent)
            }
        }
    }
}