package com.homeworkandroid.trabajofragment

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.homeworkandroid.trabajofragment.notice.NoticeViewModel
import kotlinx.android.synthetic.main.activity_load_user.*
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.activity_user.actualizar
import kotlinx.android.synthetic.main.activity_user.editTextBattlenet
import kotlinx.android.synthetic.main.activity_user.editTextOrigin
import kotlinx.android.synthetic.main.activity_user.editTextSteam
import kotlinx.android.synthetic.main.activity_user.imgUser
import kotlinx.android.synthetic.main.activity_user.nameAcount
import kotlinx.android.synthetic.main.fragment_notice.*
import kotlinx.android.synthetic.main.item_notice.view.*
import java.util.*


class UserActivity : AppCompatActivity() {

    val REQ_IMAGEN  = 123

    lateinit var storageReference: StorageReference


    val database = FirebaseDatabase.getInstance()
    private var mAuth: FirebaseAuth? = FirebaseAuth.getInstance()
    val myRef = database.getReference("users")
    var usersRef: DatabaseReference = myRef.child(
        mAuth!!.currentUser?.email.toString().replace(
            '.',
            '-'
        )
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        val storage= Firebase.storage
        storageReference=storage.reference

        cargarFoto()
        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.child("Nick").value!=null)
                    nameAcount.setText(dataSnapshot.child("Nick").value.toString())
                if(dataSnapshot.child("Steam").value!=null)
                   editTextSteam.setText(dataSnapshot.child("Steam").value.toString())
                if(dataSnapshot.child("Battlenet").value!=null)
                   editTextBattlenet.setText(dataSnapshot.child("Battlenet").value.toString())
                if(dataSnapshot.child("Origin").value!=null)
                    editTextOrigin.setText(dataSnapshot.child("Origin").value.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })


        cerrarSesion.setBackgroundColor(Color.RED)
        actualizar.setOnClickListener{
            usersRef.child("Nick").setValue(nameAcount.text.toString())
            usersRef.child("Steam").setValue(editTextSteam.text.toString())
            usersRef.child("Battlenet").setValue(editTextBattlenet.text.toString())
            usersRef.child("Origin").setValue(editTextOrigin.text.toString())
        }

        /* ---------  PERMISO SUBIR IMAGEN --------- */
        val listaOpcion = arrayOf("Memoria Del Telefono"/*,"Camara"*/)
        val builder = AlertDialog.Builder(this)

        builder.setTitle("De Donde Subir La Imagen")


        imgUser.setOnClickListener{
            builder.setItems(listaOpcion){ id, posicion ->

                when(posicion){
                    0 -> cambiarPerfil()
                }
            }
            builder.show()


        }
        /* ---------  ================ --------- */

        cerrarSesion.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val mainIntent = Intent(this, SplashScreen::class.java)
            startActivity(mainIntent)
        }

    }

     fun cambiarPerfil(){
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
         intent.addCategory(Intent.CATEGORY_OPENABLE)
         intent.type="*/*"
         startActivityForResult(intent, REQ_IMAGEN)


     }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(
            "onActiviti =====",
            requestCode.toString() + " - " + resultCode.toString() + " - " + data.toString()
        )
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==REQ_IMAGEN && resultCode==RESULT_OK){
            if(data!=null){
                val uri: Uri? =data.data // mnt/sdacard/images/image.jpg por ejemplo
         //       if(usersRef!="-1"){
                var imageRef = storageReference.child(
                    "user/" + mAuth!!.currentUser?.email.toString().replace(
                        '.',
                        '-'
                    ) + "/pc_master_race.png"
                )
                    subirImagen(imageRef, uri)
         //       }

            }
        }
    }

    private fun subirImagen(imageRef: StorageReference, ruta: Uri?) {
        if(ruta!=null){
            imageRef.putFile(ruta).addOnCompleteListener(){
                if(it.isSuccessful){
                    Toast.makeText(this, "Imagen perfil subida", Toast.LENGTH_LONG).show()
                    cargarFoto()
                }
                else{
                    Toast.makeText(this, "Error: ${it.exception.toString()}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun cargarFoto() {
        var imageRef = storageReference.child(
            "user/" + mAuth!!.currentUser?.email.toString().replace(
                '.',
                '-'
            ) + "/pc_master_race.png"
        )
        if (imageRef != null) {
            Glide.with(this).load(imageRef).circleCrop().placeholder(R.drawable.pc_master_race).into(imgUser)
        }
    }


}