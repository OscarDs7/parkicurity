package com.example.parkicurity_system

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import kotlin.math.pow
import kotlin.math.sqrt

class EstadisticasFragment : Fragment(R.layout.fragment_estadisticas) {

    private lateinit var database: DatabaseReference
    //private lateinit var txtEstadisticas: TextView

    // TextViews Temperatura
    private lateinit var txtTempMedia: TextView
    private lateinit var txtTempMin: TextView
    private lateinit var txtTempMax: TextView
    private lateinit var txtTempVar: TextView
    private lateinit var txtTempStd: TextView

    // TextViews Humedad
    private lateinit var txtHumMedia: TextView
    private lateinit var txtHumMin: TextView
    private lateinit var txtHumMax: TextView
    private lateinit var txtHumVar: TextView
    private lateinit var txtHumStd: TextView

    // TextViews Distancia
    private lateinit var txtDistMedia: TextView
    private lateinit var txtDistMin: TextView
    private lateinit var txtDistMax: TextView
    private lateinit var txtDistVar: TextView
    private lateinit var txtDistStd: TextView

    // Textview de interpretación
    private lateinit var txtInterpretacion: TextView

    // Listas mutables de datos de los sensores
    private val distanciaData = mutableListOf<Float>()
    private val temperaturaData = mutableListOf<Float>()
    private val humedadData = mutableListOf<Float>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //txtEstadisticas = view.findViewById(R.id.txtEstadisticas)

        // ----------- < ASOCIAR TEXTVIEWS  > --------------
        txtTempMedia = view.findViewById(R.id.txtTempMedia)
        txtTempMin = view.findViewById(R.id.txtTempMin)
        txtTempMax = view.findViewById(R.id.txtTempMax)
        txtTempVar = view.findViewById(R.id.txtTempVar)
        txtTempStd = view.findViewById(R.id.txtTempStd)

        txtHumMedia = view.findViewById(R.id.txtHumMedia)
        txtHumMin = view.findViewById(R.id.txtHumMin)
        txtHumMax = view.findViewById(R.id.txtHumMax)
        txtHumVar = view.findViewById(R.id.txtHumVar)
        txtHumStd = view.findViewById(R.id.txtHumStd)

        txtDistMedia = view.findViewById(R.id.txtDistMedia)
        txtDistMin = view.findViewById(R.id.txtDistMin)
        txtDistMax = view.findViewById(R.id.txtDistMax)
        txtDistVar = view.findViewById(R.id.txtDistVar)
        txtDistStd = view.findViewById(R.id.txtDistStd)

        txtInterpretacion = view.findViewById(R.id.txtInterpretacion)

        database = FirebaseDatabase.getInstance()
            .getReference("Lecturas")
            .child("Sensores")

        escucharDatos()
    }

    private fun escucharDatos() {
        database.addValueEventListener(object : ValueEventListener {

            @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
            override fun onDataChange(snapshot: DataSnapshot) {

                val temperatura = snapshot.child("Ambiente")
                    .child("temperatura_C")
                    .getValue(Double::class.java) ?: 0.0

                val humedad = snapshot.child("Ambiente")
                    .child("humedad_%")
                    .getValue(Int::class.java) ?: 0

                val distancia = snapshot.child("Estacionamiento")
                    .child("distancia_cm")
                    .getValue(Int::class.java) ?: 0

                temperaturaData += temperatura.toFloat()
                humedadData += humedad.toFloat()
                distanciaData += distancia.toFloat()

                if (temperaturaData.size > 200) temperaturaData.removeFirst()
                if (humedadData.size > 200) humedadData.removeFirst()
                if (distanciaData.size > 200) distanciaData.removeFirst()

                actualizarPantalla()
            }

            override fun onCancelled(error: DatabaseError) {
                txtInterpretacion.text = "❌ Error al obtener datos."
            }
        })
    }

    // ---------------------------------------------------------
    // PROCESAMIENTO Y ACTUALIZACIÓN DE UI
    // ---------------------------------------------------------
    @SuppressLint("SetTextI18n")
    private fun actualizarPantalla() {
        // Variables que recibirán datos de cada sensor en decimal
        val temp = filtrarOutliers(temperaturaData)
        val hum = filtrarOutliers(humedadData)
        val dist = filtrarOutliers(distanciaData)

        // ---- TEMPERATURA ----
        val statsTemp = calcularEstadisticas(temp)
        txtTempMedia.text = "Media: %.2f".format(statsTemp.media)
        txtTempMin.text = "Mínimo: %.2f".format(statsTemp.min)
        txtTempMax.text = "Máximo: %.2f".format(statsTemp.max)
        txtTempVar.text = "Varianza: %.2f".format(statsTemp.varianza)
        txtTempStd.text = "Desviación estándar: %.2f".format(statsTemp.std)

        // ---- HUMEDAD ----
        val statsHum = calcularEstadisticas(hum)
        txtHumMedia.text = "Media: %.2f".format(statsHum.media)
        txtHumMin.text = "Mínimo: %.2f".format(statsHum.min)
        txtHumMax.text = "Máximo: %.2f".format(statsHum.max)
        txtHumVar.text = "Varianza: %.2f".format(statsHum.varianza)
        txtHumStd.text = "Desviación estándar: %.2f".format(statsHum.std)

        // ---- DISTANCIA ----
        val statsDist = calcularEstadisticas(dist)
        txtDistMedia.text = "Media: %.2f".format(statsDist.media)
        txtDistMin.text = "Mínimo: %.2f".format(statsDist.min)
        txtDistMax.text = "Máximo: %.2f".format(statsDist.max)
        txtDistVar.text = "Varianza: %.2f".format(statsDist.varianza)
        txtDistStd.text = "Desviación estándar: %.2f".format(statsDist.std)

        // -- INTERPRETACIÓN DE LOS SENSORES --- //
        val interpretTemp = interpretarEstadisticas(filtrarOutliers(temperaturaData), "Temperatura")
        val interpretHum = interpretarEstadisticas(filtrarOutliers(humedadData), "Humedad")
        val interpretDist = interpretarEstadisticas(filtrarOutliers(distanciaData), "Distancia")

        txtInterpretacion.text = """
            🔍 Interpretación de sensores:

            🌡️ Temperatura:
            $interpretTemp

            💧 Humedad:
            $interpretHum

            📏 Distancia:
            $interpretDist
        """.trimIndent()

    } // fin-actualizarpantalla

    // ------------------------------- OBJETO DE ESTADÍSTICAS -------------------------------
    data class Stats(
        val media: Float,
        val min: Float,
        val max: Float,
        val varianza: Float,
        val std: Float
    )

    // ---------------------------------------------------------
    // ESTADÍSTICAS
    // ---------------------------------------------------------
    private fun calcularEstadisticas(data: List<Float>): Stats {
        if (data.isEmpty()) return Stats(0f, 0f, 0f, 0f, 0f)

        val media = data.average().toFloat()
        val min = data.minOrNull()!!
        val max = data.maxOrNull()!!
        val varianza = data.map { (it - media).pow(2) }.average().toFloat()
        val std = sqrt(varianza)

        return Stats(media, min, max, varianza, std)
    }

    // ---------------------------------------------------------
    // INTERPRETACIÓN
    // ---------------------------------------------------------
    private fun interpretarTemperatura(valor: Float) = when {
        valor < 10 -> "Muy baja: clima frío extremo."
        valor in 10f..20f -> "Temperatura normal."
        valor in 20f..30f -> "Temperatura moderada."
        valor > 30 -> "Muy alta: riesgo de sobrecalentamiento."
        else -> "Sin interpretación."
    }

    private fun interpretarHumedad(valor: Float) = when {
        valor < 20 -> "Humedad muy baja."
        valor in 20f..40f -> "Humedad normal."
        valor in 40f..60f -> "Humedad moderada."
        valor > 60 -> "Humedad alta."
        else -> "Sin interpretación."
    }

    private fun interpretarDistancia(valor: Float) = when (valor) {
        in 0f..10f -> "⚠️ Objeto extremadamente cerca."
        in 10.01f..29.99f -> "Objeto cercano."
        else -> "Distancia segura."
    }

    private fun interpretarEstadisticas(data: List<Float>, tipo: String): String {
        if (data.isEmpty()) return "No hay datos."

        val stats = calcularEstadisticas(data)

        val media = stats.media.toFloat()
        val std = stats.std.toFloat()

        return when (tipo) {
            "Temperatura" -> interpretarTemperatura(media) +
                    if (std > 3) " ⚠️ Alta fluctuación" else ""

            "Humedad" -> interpretarHumedad(media) +
                    if (std > 10) " ⚠️ Variación fuerte" else ""

            "Distancia" -> interpretarDistancia(media) +
                    if (std > 10) " ⚠️ Distancia inestable" else ""

            else -> "Sin interpretación."
        }
    }


    // ---------------------------------------------------------
    // OUTLIERS -> VALORES ATÍPICOS (FUERA DE RANGO)
    // ---------------------------------------------------------
    private fun filtrarOutliers(data: List<Float>): List<Float> {
        if (data.isEmpty()) return emptyList()

        val media = data.average()
        val desviacion = sqrt(data.map { (it - media).pow(2) }.average())

        return data.filter { it in (media - 3 * desviacion)..(media + 3 * desviacion) }
    }
}
