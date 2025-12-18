package com.example.parkicurity_system

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class GraficosFragment : Fragment(R.layout.fragment_graficos) {

    private lateinit var database: DatabaseReference

    private lateinit var chartDistance: LineChart
    private lateinit var chartTemperature: LineChart
    private lateinit var chartHumidity: LineChart

    private val distanciaData = mutableListOf<Float>()
    private val temperaturaData = mutableListOf<Float>()
    private val humedadData = mutableListOf<Float>()
    private val maxDatos = 200

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inicializarUI(view)
        inicializarGraficos()

        // Ruta correcta
        database = FirebaseDatabase.getInstance()
            .getReference("Lecturas")
            .child("Sensores")

        // Listener en AMBOS nodos
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val temperatura = snapshot.child("Ambiente/temperatura_C")
                    .getValue(Double::class.java) ?: 0.0

                val humedad = snapshot.child("Ambiente/humedad_%")
                    .getValue(Double::class.java) ?: 0.0

                val distancia = snapshot.child("Estacionamiento/distancia_cm")
                    .getValue(Double::class.java) ?: 0.0

                actualizarDatos(
                    distancia.toFloat(),
                    temperatura.toFloat(),
                    humedad.toFloat()
                )
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun inicializarUI(view: View) {
        chartDistance = view.findViewById(R.id.chartDistance)
        chartTemperature = view.findViewById(R.id.chartTemperature)
        chartHumidity = view.findViewById(R.id.chartHumidity)
    }

    private fun inicializarGraficos() {
        listOf(chartDistance, chartTemperature, chartHumidity).forEach { chart ->
            chart.axisRight.isEnabled = false
            chart.setTouchEnabled(true)
            chart.setPinchZoom(true)
            chart.legend.form = Legend.LegendForm.LINE
        }
    }

    private fun actualizarDatos(dist: Float, temp: Float, hum: Float) {
        agregarDato(distanciaData, dist)
        agregarDato(temperaturaData, temp)
        agregarDato(humedadData, hum)

        actualizarGrafico(chartDistance, distanciaData, "Distancia (cm)")
        actualizarGrafico(chartTemperature, temperaturaData, "Temperatura (°C)")
        actualizarGrafico(chartHumidity, humedadData, "Humedad (%)")
    }

    private fun agregarDato(lista: MutableList<Float>, valor: Float) {
        lista.add(valor)
        if (lista.size > maxDatos) lista.removeAt(0)
    }

    private fun actualizarGrafico(chart: LineChart, lista: List<Float>, label: String) {
        val entries = lista.mapIndexed { index, value -> Entry(index.toFloat(), value) }

        val dataSet = LineDataSet(entries, label).apply {
            lineWidth = 2f // grosor de la línea del trazado de cada punto
            // Color de la línea del gráfico
            color = Color.BLUE
            valueTextColor = Color.BLACK
            // Círculito por cada dato recibido del sensor
            setDrawCircles(true) // habilitar el circulo de cada dato
            circleRadius = 4f
            setCircleColor(Color.WHITE)
            circleHoleColor = Color.BLUE
        }

        chart.data = LineData(dataSet)
        chart.invalidate()
    }
}
