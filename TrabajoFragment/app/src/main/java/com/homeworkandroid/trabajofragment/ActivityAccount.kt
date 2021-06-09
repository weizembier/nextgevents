package com.homeworkandroid.trabajofragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.activity_user.*

class ActivityAccount : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val Req_Code: Int = 123


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        mAuth = FirebaseAuth.getInstance()
        singUpButton.setBackgroundColor(Color.rgb(76,97,1))
        setup()
    }

    private fun setup(){
        singUpButton.setOnClickListener{
            if(emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty() ){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
                )
                    .addOnCompleteListener{
                        if (it.isSuccessful){
                            showHome()
                        }else{
                            if(passwordEditText.text.toString().length<6)
                                showAlertPassword()
                            else
                                showAlert()
                        }
                    }
            }else{
                showAlertEmpy()
            }
        }

        loginButton.setOnClickListener{
            if(emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty() ){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
                )
                    .addOnCompleteListener{
                        if (it.isSuccessful){
                            showHome()
                        }else{
                            if(passwordEditText.text.toString().length<6)
                                showAlertPassword()
                            else
                                showAlert()
                        }
                    }
            }else{
                showAlertEmpy()
            }
        }

        loginGoogle.setOnClickListener{
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            // getting the value of gso inside the GoogleSigninClient
            mGoogleSignInClient = GoogleSignIn.getClient(this,gso)
            // initialize the firebaseAuth variable
            mAuth= FirebaseAuth.getInstance()
            val signInIntent: Intent =mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent,Req_Code)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==Req_Code){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            manejarSigIn(task)
        }
    }

    private fun manejarSigIn(task: Task<GoogleSignInAccount>?) {
        try {
            // Google Sign In was successful, authenticate with Firebase
            val account = task?.getResult(ApiException::class.java)!!
            firebaseAuthWithGoogle(account, account.idToken!!)



        }catch(e: ApiException){
            // Google Sign In failed, update UI appropriately
            Log.w("Error: ",e)
        }
    }



    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount, idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth?.signInWithCredential(credential)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    SavedPreference.setEmail(this,account.email.toString())
                    SavedPreference.setUsername(this,account.displayName.toString())
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("asdaas", "signInWithCredential:failure", task.exception)

                }
            }

    }
    override fun onStart() {
        super.onStart()
        if(GoogleSignIn.getLastSignedInAccount(this)!=null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }



    private fun showAlert(){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autentificando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

    private fun showAlertPassword(){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("La contraseña debe de tener minimo 6 caracteres")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

    private fun showAlertEmpy(){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("El campo de usuario, email y/o contraseña estan vacios")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

    private fun showHome(){
        Toast.makeText(this,"¡Bienvenido jugador!",Toast.LENGTH_LONG,).show()
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)

    }
}

object SavedPreference {

    const val EMAIL= "email"
    const val USERNAME="username"

    private  fun getSharedPreference(ctx: Context?): SharedPreferences? {
        return PreferenceManager.getDefaultSharedPreferences(ctx)
    }

    private fun  editor(context: Context, const:String, string: String){
        getSharedPreference(
            context
        )?.edit()?.putString(const,string)?.apply()
    }

    fun getEmail(context: Context)= getSharedPreference(
        context
    )?.getString(EMAIL,"")

    fun setEmail(context: Context, email: String){
        editor(
            context,
            EMAIL,
            email
        )
    }

    fun setUsername(context: Context, username:String){
        editor(
            context,
            USERNAME,
            username
        )
    }

    fun getUsername(context: Context) = getSharedPreference(
        context
    )?.getString(USERNAME,"")

}