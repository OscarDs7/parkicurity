🚗 Parkicurity System

Parkicurity System is an Android mobile application focused on intelligent parking monitoring using IoT sensors and real-time data visualization. The project integrates Firebase Realtime Database, statistical analysis, graphical visualization, and a modern architecture based on Fragments + ViewPager2 + BottomNavigation.

The main goal is to improve security, control, and decision-making in parking facilities through clear, reliable, and professionally presented information.

📱 Main Features
🔹 Real-time Monitoring

Continuous sensor data reading from Firebase Realtime Database

Currently implemented sensors:

🌡️ Ambient temperature

💧 Relative humidity

📏 Distance (object detection / parking occupancy)

Intelligent recommendations based on a neural network trained in Python, whose results are retrieved from Firebase Realtime Database.

🔹 Professional Dashboard

Dark / modern dashboard-style interface

Clean and professional MaterialCardView cards

Data organized by sensor and metrics

🔹 Advanced Statistics

For each sensor, the following metrics are calculated:

Mean

Minimum value

Maximum value

Variance

Standard deviation

Additionally:

Outlier filtering (±3σ)

Automatic sensor state interpretation

🔹 Data Visualization

Line charts using MPAndroidChart

Visible data points (circles) for each measurement

Zoom, scrolling, and real-time updates

🔹 Smooth Navigation

BottomNavigationView

ViewPager2 for swipe navigation (left / right)

No unnecessary fragment reloads

🧭 Navigation Structure
Tab	Description
Sensors	Current values of each sensor
Charts	Historical data visualization
Statistics	Statistical analysis and interpretation
🏗️ Architecture

Language: Kotlin

Pattern: Fragment-based UI

Navigation:

ViewPager2

BottomNavigationView

Database: Firebase Realtime Database

Charts: MPAndroidChart

MainActivity
 ├── ViewPager2
 │    ├── SensoresFragment
 │    ├── GraficosFragment
 │    └── EstadisticasFragment
 └── BottomNavigationView

🔥 Firebase

Data structure used:

Lecturas
 └── Sensores
      ├── Ambiente
      │    ├── temperatura_C
      │    └── humedad_%
      └── Estacionamiento
           └── distancia_cm


Real-time listening using ValueEventListener

Safe type conversion (Double, Int → Float)

📊 Statistical Logic

Each sensor maintains a sliding window of up to 200 data points:

Old data is removed

Outliers are filtered

Statistical metrics are calculated

Automatic interpretations are generated

Interpretation examples:

Very high temperature → ⚠️ Overheating risk

High standard deviation → ⚠️ Unstable data

🎨 UI/UX Design

Material Design 3

High-contrast colors (dark mode)

Clear and consistent icons

Clear separation between labels and values

Aligned data for better readability

🧪 Project Status

✅ Base architecture implemented

✅ Firebase connected

✅ Functional charts

✅ Correct statistical calculations

🔄 Under expansion (alerts, history, AI)

🚀 Possible Future Improvements

🔔 Push notifications for critical values

📈 Data export

☁️ Custom backend (REST API)

👥 User and role management

🛠️ Technologies Used

Kotlin

Android SDK

Firebase Realtime Database

MPAndroidChart

Material Components

👨‍💻 Author

Oscar Eduardo Romero Escamilla

Academic / professional project focused on IoT, data analysis, and mobile development.

📄 License

This project is intended for academic / experimental use. It may be adapted or extended for educational purposes.
