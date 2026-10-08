package com.toolbox.lanzador

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val minecraftPackage = "com.mojang.minecraftpe"

    private lateinit var estado: TextView
    private lateinit var version: TextView
    private lateinit var botonJugar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        estado = findViewById(R.id.estado)
        version = findViewById(R.id.version)
        botonJugar = findViewById(R.id.botonJugar)

        botonJugar.setOnClickListener { abrirMinecraft() }
    }

    override fun onResume() {
        super.onResume()
        actualizarEstado()
    }

    private fun actualizarEstado() {
        try {
            val info = packageManager.getPackageInfo(minecraftPackage, 0)
            estado.setText(R.string.estado_instalado)
            version.text = getString(R.string.version_texto, info.versionName ?: "?")
            botonJugar.isEnabled = true
        } catch (e: PackageManager.NameNotFoundException) {
            estado.setText(R.string.estado_no_instalado)
            version.text = ""
            botonJugar.isEnabled = false
        }
    }

    private fun abrirMinecraft() {
        val intent = packageManager.getLaunchIntentForPackage(minecraftPackage)
        if (intent != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, R.string.no_se_pudo_abrir, Toast.LENGTH_SHORT).show()
        }
    }
}
