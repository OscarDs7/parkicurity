# 🚗 Parkicurity System

**Parkicurity System** is an Android mobile application focused on **intelligent parking monitoring** using IoT sensors and real-time data visualization. The project integrates **Firebase Realtime Database**, statistical analysis, graphical visualization, and a modern architecture based on **Fragments + ViewPager2 + BottomNavigation**.

The main goal is to **improve security, control, and decision-making** in parking facilities through clear, reliable, and professionally presented information.

---

## 📱 Main Features

### 🔹 Real-time Monitoring

- Continuous sensor data reading from **Firebase Realtime Database**
- Currently implemented sensors:
  - 🌡️ Ambient temperature  
  - 💧 Relative humidity  
  - 📏 Distance (object detection / parking occupancy)
- Real-time updates using `ValueEventListener`
- Safe data type conversion (`Int`, `Double` → `Float`)

### 🔹 AI-Based Recommendations System

The application consumes **AI-generated recommendations** stored in Firebase.  
These recommendations are produced by a **neural network trained in Python** and sent to Firebase for real-time consumption by the mobile app.

#### Firebase Recommendations Structure

```json
Recomendaciones
 ├── distancia: 83
 ├── estado: 0
 ├── humedad: 55
 ├── mensaje: "✓ Everything is under safe conditions"
 ├── nivel_intrusion: 0
 ├── temperatura: 28
 └── timestamp: 1764717220

### 🔹 Professional Dashboard

- **Dark / modern dashboard-style interface**
- Clean and professional **MaterialCardView** cards
- Data organized by sensor and metrics

### 🔹 Advanced Statistics

For each sensor, the following metrics are calculated:

- Mean
- Minimum value
- Maximum value
- Variance
- Standard deviation

Additionally:

- **Outlier filtering (±3σ)**
- Automatic sensor state interpretation

### 🔹 Data Visualization

- Line charts using **MPAndroidChart**
- Visible data points (circles) for each measurement
- Zoom, scrolling, and real-time updates

### 🔹 Smooth Navigation

- **BottomNavigationView**
- **ViewPager2** for swipe navigation (left / right)
- No unnecessary fragment reloads

---

## 🧭 Navigation Structure

| Tab        | Description                          |
|------------|--------------------------------------|
| Sensors    | Current values of each sensor        |
| Charts     | Historical data visualization        |
| Statistics | Statistical analysis and interpretation |

---

## 🏗️ Architecture

- **Language:** Kotlin  
- **Pattern:** Fragment-based UI  
- **Navigation:**
  - ViewPager2  
  - BottomNavigationView  
- **Database:** Firebase Realtime Database  
- **Charts:** MPAndroidChart  

```text
MainActivity
 ├── ViewPager2
 │    ├── SensoresFragment
 │    ├── GraficosFragment
 │    └── EstadisticasFragment
 └── BottomNavigationView
