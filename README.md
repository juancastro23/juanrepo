GodStyle - Gestión de Citas para Profesionales de la Estética

Kotlin Version
Android Studio
Min SDK

GodStyle es una aplicación móvil diseñada para profesionales del sector estético (peluquerías, barberías, centros de belleza) que permite gestionar citas, clientes y servicios de forma eficiente, con soporte offline y notificaciones inteligentes.

GodStyle Banner
🔍 Características Principales

    Gestión completa de citas (crear, leer, actualizar, eliminar)

    Notificaciones automáticas 15 minutos antes de cada cita

    Funcionalidad offline con sincronización automática

    Autenticación segura con Firebase

    Calendario integrado para visualización de citas

    Interfaz intuitiva con navegación sencilla

    Soporte multi-dispositivo con almacenamiento en la nube

⚙️ Tecnologías Utilizadas

    Lenguaje: Kotlin

    Arquitectura: MVVM (Model-View-ViewModel)

    Persistencia local: Room Database

    Autenticación: Firebase Authentication

    Notificaciones: AlarmManager + BroadcastReceiver

    Navegación: Jetpack Navigation Component

    UI: XML + Material Design Components

    Control de versiones: Git + GitHub

📸 Capturas de Pantalla
Inicio de Sesión	Lista de Citas	Calendario
Login	Citas	Calendario
Crear Cita	Notificación	Perfil
Crear Cita	Notificación	Perfil
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
bash

./gradlew assembleRelease

El APK se generará en app/build/outputs/apk/release/
🧪 Pruebas
Pruebas Unitarias
bash

./gradlew test

Pruebas de Integración (Espresso)

    Ejecutar un emulador o conectar dispositivo

    Ejecutar:

bash

./gradlew connectedCheck

📚 Documentación Técnica
Diagrama de Arquitectura

Arquitectura
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

    Sincronización bidireccional con Cloud Firestore

    Autenticación con Google y Facebook

    Dashboard analítico con estadísticas

    Soporte multi-idioma (internacionalización)

    Sistema de recordatorios SMS

🤝 Contribución

¡Las contribuciones son bienvenidas! Por favor siga estos pasos:

    Hacer un fork del proyecto

    Crear una rama para su feature (git checkout -b feature/AmazingFeature)

    Hacer commit de sus cambios (git commit -m 'Add some AmazingFeature')

    Hacer push a la rama (git push origin feature/AmazingFeature)

    Abrir un Pull Request

📄 Licencia

Este proyecto está bajo la licencia MIT. Consulte el archivo LICENSE para más detalles.
✉️ Contacto

Juan Castro Rodríguez
Email: tu-email@ejemplo.com
LinkedIn: Juan Castro
GitHub: juancastro23

GodStyle - Transformando la gestión de citas para profesionales de la estética © 2025
