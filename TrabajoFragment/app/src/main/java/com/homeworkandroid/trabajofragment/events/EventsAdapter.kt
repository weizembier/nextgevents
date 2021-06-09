package com.homeworkandroid.trabajofragment.events

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.homeworkandroid.trabajofragment.DetailsFragmentActivity
import com.homeworkandroid.trabajofragment.MainActivity
import com.homeworkandroid.trabajofragment.R
import kotlinx.android.synthetic.main.item_events.view.*


class EventsAdapter(val context: FragmentEvents): RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {

    private var eventsList = mutableListOf<Events>()

    fun setListEvents(events: MutableList<Events>){
        eventsList = events
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val v = inflate.inflate(R.layout.item_events, parent, false)
        return EventsViewHolder(v)

    }

    override fun getItemCount():Int{
        return eventsList.size
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        val events = eventsList[position]

        holder.bindView(events)

    }


    inner class EventsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindView(events: Events){
            Glide.with(context).load(events.imgUrl).into(itemView.imgEvents)
            itemView.tituloEvents.text= events.titulo
            itemView.descEvents.text = events.descripcion

            itemView.setOnClickListener{
                val intent =  Intent(itemView.context, DetailsFragmentActivity::class.java)
                intent.putExtra("tituloString", events.titulo)
                intent.putExtra("descripcionString", events.descripcion)
                intent.putExtra("imageView", events.imgUrl)
                intent.putExtra("textoString", events.texto)
                intent.putExtra("urlStreaming", events.urlStreaming)
                intent.putExtra("coordenadas", events.coordenadas)

                itemView.context.startActivity(intent)
            }
        }
    }
}