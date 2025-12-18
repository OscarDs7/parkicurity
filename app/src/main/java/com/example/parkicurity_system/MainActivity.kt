package com.example.parkicurity_system

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Instanciamiento y vinculación a los componentes UI
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)

        // Configurar adapter del ViewPager
        viewPager.adapter = ViewPagerAdapter(this)

        // Cambiar pestaña al tocar el BottomNavigation
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_sensores -> viewPager.currentItem = 0
                R.id.nav_graficos -> viewPager.currentItem = 1
                R.id.nav_estadisticas -> viewPager.currentItem = 2
            }
            true
        }

        // Cambiar item del BottomNavigation al deslizar
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> bottomNav.selectedItemId = R.id.nav_sensores
                    1 -> bottomNav.selectedItemId = R.id.nav_graficos
                    2 -> bottomNav.selectedItemId = R.id.nav_estadisticas
                }
            }
        })
    }
}
