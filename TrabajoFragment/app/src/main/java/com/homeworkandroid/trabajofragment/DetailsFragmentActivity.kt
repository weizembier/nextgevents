package com.homeworkandroid.trabajofragment

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_details_fragment.*
import java.io.IOException
import java.lang.NullPointerException
import java.security.AccessController.getContext


class DetailsFragmentActivity : AppCompatActivity(), OnMapReadyCallback {

    private val database = FirebaseFirestore.getInstance()
    var inscripcionValor =""
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_fragment)

        webViewNotice.visibility = View.GONE

        titulo.text = getIntent().getStringExtra("tituloString")
        descripcion.text = getIntent().getStringExtra("descripcionString")
        val imageViewString = getIntent().getStringExtra("imageView")
        Glide.with(this).load(imageViewString).into(this.imageView)

        val flagWebView = getIntent().getStringExtra("textoString")?.substring(0,4)
        if(!flagWebView.equals("http")){
            texto.text = getIntent().getStringExtra("textoString")
        }else{
            texto.text = ""
            webViewNotice.visibility = View.VISIBLE
            webViewNotice.webChromeClient = object : WebChromeClient(){}
            webViewNotice.webViewClient = object  : WebViewClient(){}
            webViewNotice.settings.javaScriptEnabled = true
            webViewNotice.loadUrl(getIntent().getStringExtra("textoString").toString())
        }


        if (getIntent().getStringExtra("coordenadas") != "") {
            val mapFragment = supportFragmentManager
                    .findFragmentById(R.id.mapView) as SupportMapFragment
            mapFragment.getMapAsync(this)
        } else {
            localizacionString.visibility = View.GONE
            mapView.view?.visibility = View.GONE
        }

        if (getIntent().getStringExtra("urlStreaming") != "") {
            urlStreaming.text = getIntent().getStringExtra("urlStreaming")
        } else {
            urlStreaming.visibility = View.GONE
            urlStreamingString.visibility = View.GONE
        }


        mAuth = FirebaseAuth.getInstance()
        var user = mAuth!!.currentUser?.email




        if(getIntent().getStringExtra("urlStreaming")!="" || getIntent().getStringExtra("coordenadas") != ""){


            try {

                 database.collection(titulo.text.toString()).document(user.toString()).get().addOnSuccessListener {
                    flag.setText( it.get("inscripcion") as String?)
                     saveFlag(flag.text.toString())
                }

            } catch (e: UninitializedPropertyAccessException) {
              //  inscripcionValor = "false"
                Log.d("UNITIALIZED ====>", e.toString())
            }catch (e: IOException){
                //inscripcionValor = "false"
                Log.d("IOE ====>", e.toString())
            }


    /*         Log.d("==== if true", inscripcionValor)
            if(inscripcionValor !="true"){
                inscribirse.setBackgroundColor(ContextCompat.getColor(this, R.color.colorRed))
                inscribirse.setText(R.string.borrame)
            }
            if(inscripcionValor =="false") {
                inscribirse.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
                inscribirse.setText(R.string.inscribeme)
            }*/





                inscribirse.setOnClickListener {
                    if(inscripcionValor =="false") {
                        Log.d("==== click false: ", inscripcionValor)
                        database.collection(titulo.text.toString()).document(user.toString()).set(hashMapOf("inscripcion" to "true"))
                        inscribirse.setBackgroundColor(ContextCompat.getColor(this, R.color.colorRed))
                        inscribirse.setText(R.string.borrame)
                        inscripcionValor = "true"
                        Log.d("Insc Valor =========>", inscripcionValor)
                    }else{
                        Log.d("======= click true",inscripcionValor )
                        //  database.collection(titulo.text.toString()).document(user.toString()).delete()
                        database.collection(titulo.text.toString()).document(user.toString()).set(hashMapOf("inscripcion" to "false"))
                        inscribirse.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
                        inscribirse.setText(R.string.inscribeme)
                        inscripcionValor = "false"
                    }
                }
        }else{
            inscribirse.visibility = View.GONE
            webViewNotice.visibility = View.VISIBLE

        }
        flag.visibility = View.GONE
    }

     override fun onMapReady(googleMap: GoogleMap) {
         if(getIntent().getStringExtra("urlStreaming")!="") {
             val coordenadasLocal = getIntent().getStringExtra("coordenadas")
             val coordenadasArray: List<String>? = coordenadasLocal?.split(",")
             coordenadasArray?.get(0)?.replace("[", "")
             coordenadasArray?.get(1)?.replace("]", "")
             var latitud = coordenadasArray?.get(0)?.toDouble()
             var longitud = coordenadasArray?.get(1)?.toDouble()

             if (latitud == null) latitud = 0.0
             if (longitud == null) longitud = 0.0

             val mapPositionEvent = LatLng(latitud, longitud)
             googleMap.addMarker(
                     MarkerOptions()
                             .position(mapPositionEvent)
                             .title(titulo.text.toString())
             )
             googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapPositionEvent, 15f))
             googleMap.animateCamera(CameraUpdateFactory.zoomTo(15f), 2000, null)
             googleMap.setMinZoomPreference(10.0f)
             googleMap.setMaxZoomPreference(25.0f)
         }
    }

    fun saveFlag(aux : String){
        Log.d("==== save log:", aux)
        inscripcionValor= aux
        Log.d("===== inscrip flg", inscripcionValor)
        Log.d("==== if true", inscripcionValor)
        if(inscripcionValor =="true"){
            inscribirse.setBackgroundColor(ContextCompat.getColor(this, R.color.colorRed))
            inscribirse.setText(R.string.borrame)
        }
    }

}