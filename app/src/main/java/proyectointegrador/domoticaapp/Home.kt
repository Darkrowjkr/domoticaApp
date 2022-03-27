package proyectointegrador.domoticaapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Home : AppCompatActivity() {
    private lateinit var txtUser: TextView
    private lateinit var btnLogout : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        txtUser = findViewById(R.id.txtUserHome)
        btnLogout = findViewById(R.id.btnLogout)


        btnLogout.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
    }



}