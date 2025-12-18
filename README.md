# 🚗 Parkicurity System

**Parkicurity System** es una aplicación móvil Android orientada a la **monitoreo inteligente de estacionamientos** mediante sensores IoT y visualización de datos en tiempo real. El proyecto integra **Firebase Realtime Database**, análisis estadístico, visualización gráfica y una arquitectura moderna basada en **Fragments + ViewPager2 + BottomNavigation**.

El objetivo principal es **mejorar la seguridad, control y toma de decisiones** dentro de estacionamientos mediante información clara, confiable y visualmente profesional.

---

## 📱 Características principales

### 🔹 Monitoreo en tiempo real

* Lectura continua de sensores desde **Firebase Realtime Database**
* Sensores actualmente implementados:

  * 🌡️ Temperatura ambiente
  * 💧 Humedad relativa
  * 📏 Distancia (detección de objetos / ocupación)
    
* Recomendaciones inteligentes basadas en el entrenamiento de una red neuronal programada en Python y recibidas desde Firebase Realtime Database.

### 🔹 Dashboard profesional

* Interfaz tipo **dashboard oscuro/moderno**
* Tarjetas (MaterialCardView) con diseño limpio y profesional
* Datos organizados por sensor y métricas

### 🔹 Estadísticas avanzadas

Para cada sensor se calculan:

* Media
* Valor mínimo
* Valor máximo
* Varianza
* Desviación estándar

Además:

* Filtrado de **outliers (±3σ)**
* Interpretación automática del estado del sensor

### 🔹 Visualización gráfica

* Gráficas de línea con **MPAndroidChart**
* Puntos visibles por medición (círculos)
* Zoom, desplazamiento y actualización en tiempo real

### 🔹 Navegación fluida

* **BottomNavigationView**
* **ViewPager2** para navegación por swipe (izquierda / derecha)
* Sin recargas innecesarias de fragments

---

## 🧭 Estructura de navegación

| Pestaña      | Descripción                           |
| ------------ | ------------------------------------- |
| Sensores     | Valores actuales de cada sensor       |
| Gráficos     | Visualización histórica de datos      |
| Estadísticas | Análisis estadístico e interpretación |

---

## 🏗️ Arquitectura

* **Lenguaje:** Kotlin
* **Patrón:** UI basada en Fragments
* **Navegación:**

  * ViewPager2
  * BottomNavigationView
* **Base de datos:** Firebase Realtime Database
* **Visualización:** MPAndroidChart

```text
MainActivity
 ├── ViewPager2
 │    ├── SensoresFragment
 │    ├── GraficosFragment
 │    └── EstadisticasFragment
 └── BottomNavigationView
```

---

## 🔥 Firebase

Estructura de datos utilizada:

```json
Lecturas
 └── Sensores
      ├── Ambiente
      │    ├── temperatura_C
      │    └── humedad_%
      └── Estacionamiento
           └── distancia_cm
```

* Escucha en tiempo real con `ValueEventListener`
* Conversión segura de tipos (`Double`, `Int` → `Float`)

---

## 📊 Lógica de estadísticas

Cada sensor mantiene una ventana deslizante de hasta **200 datos**:

* Se eliminan datos antiguos
* Se filtran valores atípicos
* Se calculan métricas estadísticas
* Se generan interpretaciones automáticas

Ejemplo de interpretación:

* Temperatura muy alta → ⚠️ Riesgo de sobrecalentamiento
* Alta desviación estándar → ⚠️ Datos inestables

---

## 🎨 Diseño UI/UX

* Material Design 3
* Colores contrastados (modo oscuro)
* Íconos claros y consistentes
* Separación clara entre etiquetas y valores
* Datos alineados para mejor legibilidad

---

## 🧪 Estado del proyecto

* ✅ Arquitectura base implementada
* ✅ Firebase conectado
* ✅ Gráficas funcionales
* ✅ Estadísticas correctas
* 🔄 En expansión (alertas, históricos, IA)

---

## 🚀 Posibles mejoras futuras

* 🔔 Alertas push por valores críticos
* 📈 Exportación de datos
* ☁️ Backend propio (API REST)
* 👥 Gestión de usuarios y roles
  
---

## 🛠️ Tecnologías utilizadas

* Kotlin
* Android SDK
* Firebase Realtime Database
* MPAndroidChart
* Material Components

---

## 👨‍💻 Autor

**Oscar Eduardo Romero Escamilla**

Proyecto académico / profesional enfocado en IoT, análisis de datos y desarrollo móvil.

---

## 📄 Licencia

Este proyecto se encuentra bajo uso académico / experimental. Puede adaptarse o extenderse con fines educativos.
