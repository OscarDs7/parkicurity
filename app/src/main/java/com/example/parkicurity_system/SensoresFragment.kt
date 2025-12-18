package com.example.parkicurity_system

import android.annotation.SuppressLint
import android.content.Context.VIBRATOR_SERVICE
import android.graphics.Color
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.google.firebase.database.*

class SensoresFragment : Fragment(R.layout.fragment_sensores) {

    private lateinit var database: DatabaseReference
    private lateinit var vibrator: Vibrator

    // Instancias a componentes UI
    private lateinit var layoutMain: ConstraintLayout
    private lateinit var txtMensaje: TextView
    private lateinit var txtDistancia: TextView
    private lateinit var txtTemperatura: TextView
    private lateinit var txtHumedad: TextView
    private lateinit var txtIntrusion: TextView
    private lateinit var txtEstado: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener vibrador
        vibrator = requireContext().getSystemService(VIBRATOR_SERVICE) as Vibrator

        // Inicializar UI correctamente usando view.*
        inicializarUI(view)

        // Vinculación a BD Firebase
        database = FirebaseDatabase.getInstance().getReference("Recomendaciones")
        database.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                // Extracción de valores de los sensores
                val distancia = snapshot.child("distancia").getValue(Double::class.java) ?: 0.0
                val temperatura = snapshot.child("temperatura").getValue(Double::class.java) ?: 0.0
                val humedad = snapshot.child("humedad").getValue(Double::class.java) ?: 0.0
                val intru = snapshot.child("nivel_intrusion").getValue(Int::class.java) ?: 0
                val estado = snapshot.child("estado").getValue(Int::class.java) ?: -1
                val mensaje = snapshot.child("mensaje").getValue(String::class.java) ?: "Sin mensaje"
                // Escritura de valores en los textview
                txtDistancia.text = "📏 Distancia: $distancia cm"
                txtTemperatura.text = "🌡️ Temperatura: $temperatura °C"
                txtHumedad.text = "💧 Humedad: $humedad %"
                txtIntrusion.text = "🚨 Nivel de intrusión: $intru"
                txtEstado.text = "Estado: $estado"
                txtMensaje.text = mensaje

                evaluarAlertas(estado, mensaje)
            }

            override fun onCancelled(error: DatabaseError) {
                txtMensaje.text = "❌ Error: ${error.message}"
            }
        })
    }

    private fun inicializarUI(view: View) {
        layoutMain = view.findViewById(R.id.main)
        txtMensaje = view.findViewById(R.id.txtMensaje)
        txtDistancia = view.findViewById(R.id.txtDistancia)
        txtTemperatura = view.findViewById(R.id.txtTemperatura)
        txtHumedad = view.findViewById(R.id.txtHumedad)
        txtIntrusion = view.findViewById(R.id.txtIntrusion)
        txtEstado = view.findViewById(R.id.txtEstado)
    }

    // ALERTAS
    private fun activarAlerta(mensaje: String, colorFondo: Int, vibrar: Boolean = false) {
        txtMensaje.text = mensaje
        layoutMain.setBackgroundColor(colorFondo)
        if (vibrar) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    500,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        }
    }

    private fun evaluarAlertas(claseIA: Int, mensajeIA: String) {
        when (claseIA) {
            4 -> activarAlerta(mensajeIA, Color.parseColor("#FFAB91"), vibrar = true)
            3, 2 -> activarAlerta(mensajeIA, Color.parseColor("#FFCDD2"), vibrar = true)
            1 -> activarAlerta(mensajeIA, Color.parseColor("#FFF59D"))
            0 -> activarAlerta(mensajeIA, Color.parseColor("#C8E6C9"))
            else -> activarAlerta(
                "⚪ Sin alerta: esperando predicción válida...",
                Color.parseColor("#FAFAFA")
            )
        }
    }
}
