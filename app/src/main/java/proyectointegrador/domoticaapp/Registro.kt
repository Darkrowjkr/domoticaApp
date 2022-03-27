package proyectointegrador.domoticaapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject

class Registro : AppCompatActivity() {

    private lateinit var txtPassword: TextInputEditText
    private lateinit var txtUser: TextInputEditText
    private lateinit var txtName: TextInputEditText
    private lateinit var btnRegistro : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        txtUser = findViewById(R.id.user_Registro)
        txtPassword = findViewById(R.id.password_Registro)
        txtName = findViewById(R.id.name_Registro)
        btnRegistro = findViewById(R.id.btn_Registro)

        btnRegistro.setOnClickListener(View.OnClickListener { registrar() })
    }

    private fun registrar() {
        val url = Uri.parse(Config.URL + "createuser.php")
            .buildUpon().build().toString()
        val peticion = object : StringRequest(
            Request.Method.POST, url,
            {response -> agregarRespuesta(response)},
            {error -> Toast.makeText(this@Registro, "Error de conexion", Toast.LENGTH_SHORT).show()}
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = java.util.HashMap()
                params["user"] = txtUser!!.text.toString()
                params["pass"] = txtPassword!!.text.toString()
                params["nombre"] = txtName!!.text.toString()
                params["rol"] = "U"
                return params
            }
        }
        MySingleton.getInstance(applicationContext).addToRequestQueue(peticion)
    }

    private fun agregarRespuesta(response: String?) {
        try {
            val r = JSONObject(response)
            if (r.getString("add").compareTo("Y") == 0) {
                Log.d("DEPURAR", "Ingreso al if de add")
                Toast.makeText(this@Registro, "Registrado correctamente", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, Login::class.java))
            } else {
                Toast.makeText(this@Registro, "Error no se pudo registrar", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {}
    }
}