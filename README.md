GodStyle - Gestión de Citas para Profesionales de la Estética
<p align="center"> <img src="https://img.shields.io/badge/Kotlin-1.9.0-blue?logo=kotlin&style=for-the-badge" alt="Kotlin Version"> <img src="https://img.shields.io/badge/Android%20Studio-Hedgehog-green?logo=androidstudio&style=for-the-badge" alt="Android Studio"> <img src="https://img.shields.io/badge/min%20SDK-API%2026%20(Android%208.0)-orange?logo=android&style=for-the-badge" alt="Min SDK"> <img src="https://img.shields.io/github/license/juancastro23/juanrepo?style=for-the-badge" alt="License"> </p><p align="center"> <img src="https://raw.githubusercontent.com/juancastro23/juanrepo/main/banner.png" alt="GodStyle Banner" width="600"> </p>

GodStyle es una aplicación móvil diseñada para profesionales del sector estético (peluquerías, barberías, centros de belleza) que permite gestionar citas, clientes y servicios de forma eficiente, con soporte offline y notificaciones inteligentes.
🔍 Características Principales

    ✅ Gestión completa de citas (crear, leer, actualizar, eliminar)

    ⏰ Notificaciones automáticas 15 minutos antes de cada cita

    📴 Funcionalidad offline con sincronización automática

    🔐 Autenticación segura con Firebase

    📅 Calendario integrado para visualización de citas

    🎨 Interfaz intuitiva con navegación sencilla

    📱 Soporte multi-dispositivo con almacenamiento en la nube

⚙️ Tecnologías Utilizadas
Categoría	Tecnologías
Lenguaje	Kotlin
Arquitectura	MVVM (Model-View-ViewModel)
Persistencia	Room Database
Autenticación	Firebase Authentication
Notificaciones	AlarmManager + BroadcastReceiver
Navegación	Jetpack Navigation Component
UI	XML + Material Design Components
Control Versión	Git + GitHub
📸 Capturas de Pantalla
<div align="center"> <table> <tr> <td align="center"><b>Inicio de Sesión</b></td> <td align="center"><b>Lista de Citas</b></td> <td align="center"><b>Calendario</b></td> </tr> <tr> <td><img src="https://raw.githubusercontent.com/juancastro23/juanrepo/main/login.png" width="200"></td> <td><img src="https://raw.githubusercontent.com/juancastro23/juanrepo/main/listado.png" width="200"></td> <td><img src="https://raw.githubusercontent.com/juancastro23/juanrepo/main/citas.png" width="200"></td> </tr> <tr> <td align="center"><b>Crear Cita</b></td> <td align="center"><b>Notificación</b></td> <td align="center"><b>Detalles</b></td> </tr> <tr> <td><img src="https://raw.githubusercontent.com/juancastro23/juanrepo/main/formulario.png" width="200"></td> <td><img src="https://raw.githubusercontent.com/juancastro23/juanrepo/main/notificacion.png" width="200"></td> <td><img src="https://raw.githubusercontent.com/juancastro23/juanrepo/main/citas.png" width="200"></td> </tr> </table> </div>
🚀 Instalación y Configuración
Requisitos Previos

    Android Studio Hedgehog o superior

    Dispositivo Android con API 26 (Android 8.0 Oreo) o superior

    Cuenta de desarrollador de Firebase

Pasos para Ejecutar el Proyecto

    Clonar el repositorio:


    

    git clone https://github.com/juancastro23/juanrepo.git
    cd GodStyle
    
Configurar Firebase:


        Crear un proyecto en Firebase Console

        Descargar el archivo google-services.json

        Colocarlo en el directorio app/

Abrir el proyecto en Android Studio:

        Seleccionar File > Open y navegar a la carpeta del proyecto

        Sincronizar dependencias con Gradle

Ejecutar en emulador o dispositivo físico:

        Seleccionar dispositivo en Android Studio

        Presionar el botón Run (▶️)

Generar APK para Producción

./gradlew assembleRelease

El APK se generará en app/build/outputs/apk/release/

🧪 Pruebas

    Pruebas Unitarias


    ./gradlew test

Pruebas de Integración (Espresso)

Ejecutar un emulador o conectar dispositivo

Ejecutar:


    ./gradlew connectedCheck

📚 Documentación Técnica
Diagrama de Arquitectura
<p align="center"> <img src="https://raw.githubusercontent.com/juancastro23/juanrepo/main/architecture.png" alt="Arquitectura MVVM" width="600"> </p>
Estructura de la Base de Datos
kotlin

@Entity(tableName = "citas")
data class Cita(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val cliente: String,
    val servicio: String,
    val fecha: String,  // Formato dd/MM/yyyy
    val hora: String,   // Formato HH:mm
    val notas: String? = null
)

Endpoints API
Método	Ruta	Descripción
GET	/citas?uid={uid}	Obtener citas de usuario
POST	/citas	Crear nueva cita
PUT	/citas/{id}	Actualizar cita existente
DELETE	/citas/{id}	Eliminar cita

Ejemplo de solicitud POST:
json

{
  "userId": "abc123",
  "cliente": "Ana López",
  "servicio": "Tinte",
  "fecha": "01/06/2025",
  "hora": "10:30",
  "notas": "Almendra"
}

🌟 Próximas Características

    🔄 Sincronización bidireccional con Cloud Firestore

    🌐 Autenticación con Google y Facebook

    📊 Dashboard analítico con estadísticas

    🌍 Soporte multi-idioma (internacionalización)

    💬 Sistema de recordatorios SMS

🤝 Contribución

¡Las contribuciones son bienvenidas! Sigue estos pasos:

    Haz un fork del proyecto

    Crea una rama para tu feature (git checkout -b feature/AmazingFeature)

    Haz commit de tus cambios (git commit -m 'Add some AmazingFeature')

    Haz push a la rama (git push origin feature/AmazingFeature)

    Abre un Pull Request


✉️ Contacto

Juan Castro Rodríguez 
    Email jcastrro02@gmail.com
    LinkedIn https://www.linkedin.com/in/juan-castro-rodr%C3%ADguez-15a6862b0/
    GitHub https://github.com/juancastro23/juanrepo


<p align="center"> ✨ <strong>GodStyle</strong> - Transformando la gestión de citas para profesionales de la estética © 2025 ✨ </p> ```
