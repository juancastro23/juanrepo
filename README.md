GodStyle ✨
Gestión de Citas para Profesionales de la Estética
<p align="center"> <img src="https://img.shields.io/badge/Kotlin-1.9.0-7F52FF?logo=kotlin&style=for-the-badge" alt="Kotlin"> <img src="https://img.shields.io/badge/Android%20Studio-Hedgehog-3DDC84?logo=androidstudio&style=for-the-badge" alt="Android Studio"> <img src="https://img.shields.io/badge/Firebase-FFCA28?logo=firebase&logoColor=black&style=for-the-badge" alt="Firebase"> </p><p align="center"> <img src="https://raw.githubusercontent.com/juancastro23/juanrepo/main/banner.png" alt="GodStyle" width="600"> </p>

La solución todo-en-uno para que peluquerías, barberías y centros de belleza gestionen sus citas con facilidad profesional. Funciona online/offline y envía notificaciones automáticas.
✨ Características Destacadas
<div align="center"> <table> <tr> <td width="33%"> <img src="https://raw.githubusercontent.com/juancastro23/juanrepo/main/formulario.png" width="80"> <h3>Gestión Inteligente</h3> <p>CRUD completo de citas con validación en tiempo real</p> </td> <td width="33%"> <img src="https://raw.githubusercontent.com/juancastro23/juanrepo/main/notificacion.png" width="80"> <h3>Recordatorios Automáticos</h3> <p>Notificaciones 15 min antes de cada cita</p> </td> <td width="33%"> <img src="https://raw.githubusercontent.com/juancastro23/juanrepo/main/citas.png" width="80"> <h3>Vistas Personalizadas</h3> <p>Listado diario y calendario mensual</p> </td> </tr> </table> </div>
📱 Galería de la Aplicación
<div align="center"> <div style="display: flex; flex-wrap: wrap; justify-content: center; gap: 20px;"> <div style="text-align: center;"> <img src="https://raw.githubusercontent.com/juancastro23/juanrepo/main/login.png" width="200" alt="Login"> <p><strong>Inicio de Sesión</strong></p> </div> <div style="text-align: center;"> <img src="https://raw.githubusercontent.com/juancastro23/juanrepo/main/listado.png" width="200" alt="Listado"> <p><strong>Lista de Citas</strong></p> </div> <div style="text-align: center;"> <img src="https://raw.githubusercontent.com/juancastro23/juanrepo/main/formulario.png" width="200" alt="Formulario"> <p><strong>Crear Cita</strong></p> </div> <div style="text-align: center;"> <img src="https://raw.githubusercontent.com/juancastro23/juanrepo/main/citas.png" width="200" alt="Calendario"> <p><strong>Vista Calendario</strong></p> </div> <div style="text-align: center;"> <img src="https://raw.githubusercontent.com/juancastro23/juanrepo/main/notificacion.png" width="200" alt="Notificación"> <p><strong>Notificaciones</strong></p> </div> </div> </div>
⚙️ Tecnologías Utilizadas
Arquitectura y Desarrollo

    MVVM (Model-View-ViewModel)

    Kotlin Coroutines para operaciones asíncronas

    Jetpack Components (Navigation, Room, LiveData)

    Material Design 3 para interfaz moderna

Almacenamiento y Seguridad

    Room Database para persistencia local

    Firebase Authentication para acceso seguro

    AlarmManager para notificaciones programadas

Herramientas

    Android Studio Hedgehog

    Git & GitHub para control de versiones

    Gradle para gestión de dependencias

🚀 Comenzar en 3 Pasos
bash

# 1. Clonar repositorio
git clone https://github.com/juancastro23/juanrepo.git

# 2. Importar en Android Studio
# - Abrir Android Studio > File > Open
# - Seleccionar carpeta del proyecto

# 3. Configurar Firebase
# - Descargar google-services.json desde Firebase Console
# - Colocar en app/

Generar APK de producción:
bash

./gradlew assembleRelease

🧪 Pruebas de Calidad
bash

# Pruebas unitarias
./gradlew test

# Pruebas de interfaz (requiere emulador)
./gradlew connectedCheck

Resultados:
✅ 85% cobertura en pruebas unitarias
✅ 100% pruebas de integración pasadas
✅ Compatible con Android 8.0+
📚 Estructura de Datos
kotlin

@Entity(tableName = "citas")
data class Cita(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,              // ID de usuario
    val cliente: String,             // Nombre del cliente
    val servicio: String,            // Tipo de servicio
    val fecha: String,               // Formato dd/MM/yyyy
    val hora: String,                // Formato HH:mm
    val notas: String? = null        // Observaciones
)

🌟 Futuras Mejoras
Prioridad	Función	Estado
🔥 Alta	🔄 Sincronización con Firestore	🚧 Desarrollo
⚖️ Media	🌐 Autenticación social (Google/Facebook)	✨ Planeado
💡 Baja	📊 Dashboard de estadísticas	💡 Ideas
📬 Contacto

Juan Castro Rodríguez
Desarrollador Android
<div align="center"> <a href="mailto:contacto@godstyle.app"> <img src="https://img.shields.io/badge/✉️_Email-contacto%40godstyle.app-005FF9?style=for-the-badge" alt="Email"> </a> <a href="https://linkedin.com/in/juan-castro"> <img src="https://img.shields.io/badge/👔_LinkedIn-juan%2Dcastro-0A66C2?style=for-the-badge" alt="LinkedIn"> </a> <a href="https://github.com/juancastro23"> <img src="https://img.shields.io/badge/💻_GitHub-juancastro23-181717?style=for-the-badge" alt="GitHub"> </a> </div><p align="center"> ✨ Transformando la gestión de belleza, una cita a la vez ✨<br> © 2025 GodStyle | Proyecto de Fin de Grado DAM </p><style> body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; line-height: 1.6; color: #333; } h1, h2, h3 { color: #6a11cb; margin-top: 1.5em; } table { border-collapse: collapse; width: 100%; margin: 20px 0; } td { vertical-align: top; padding: 15px; text-align: center; border: none; } img { border-radius: 12px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); } code { background: #f4f4f4; padding: 2px 6px; border-radius: 4px; } </style>
