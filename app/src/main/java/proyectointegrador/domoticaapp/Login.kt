package proyectointegrador.domoticaapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject

class Login : AppCompatActivity() {

    private lateinit var btnLogin : Button
    private lateinit var btnRegistrar : Button
    private lateinit var txtPassword: TextInputEditText
    private lateinit var txtUser: TextInputEditText
    private lateinit var loginLayout : LinearLayout
    var sesion: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin = findViewById(R.id.btn_Login)
        btnRegistrar = findViewById(R.id.btn_NewUser)
        txtUser = findViewById(R.id.user_Login)
        txtPassword = findViewById(R.id.password_Login)
        loginLayout = findViewById(R.id.loginLayout)

        sesion = getSharedPreferences("sesion", 0)

        btnLogin.setOnClickListener(View.OnClickListener { login() })
        btnRegistrar.setOnClickListener(View.OnClickListener { registro() })
    }

    private fun login() {
        val url = Uri.parse(Config.URL + "login.php")
            .buildUpon()
            .appendQueryParameter("user", txtUser?.text.toString())
            .appendQueryParameter("pass", txtPassword?.text.toString())
            .build().toString()

        val peticion = JsonObjectRequest(Request.Method.GET, url, null,
            {response -> respuesta(response) },
            { error ->
                Toast.makeText(this, "Error: "+error.message,Toast.LENGTH_SHORT).show()
            })
        MySingleton.getInstance(applicationContext).addToRequestQueue(peticion)
    }

    private fun respuesta(response: JSONObject?) {
        try {
            if (response!!.getString("login") == "Y"){
                val jwt = response.getString("token")
                with (sesion!!.edit()) {
                    putString("user", txtUser?.text.toString())
                    putString("token", jwt)
                    apply()
                }
                startActivity(Intent(this,Home::class.java))
            } else {
                Toast.makeText(this, "Error de usuario o contraseña", Toast.LENGTH_SHORT).show()
            }
        }catch (e:Exception){}
    }

    private fun registro() {
        startActivity(Intent(this, Registro::class.java))
    }

}