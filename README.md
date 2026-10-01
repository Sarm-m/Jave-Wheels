# JaveWheels

## Descripción

JaveWheels es una aplicación Android de movilidad compartida para la comunidad javeriana. Una única cuenta permite usar el modo **Pasajero** para consultar recorridos y reservas, y el modo **Conductor** para acceder al panel de conducción después de registrar un vehículo.

Un **Wheel** representa un recorrido con origen, destino, horario, conductor, cupos y aporte. El **Modo Buseta** muestra un punto de recogida con distancia a pie y hora aproximada de paso. La navegación principal reúne Inicio, Mis viajes, Mensajes y Perfil.

La versión actual utiliza datos locales y estados visuales. Solicitar cupo abre Mis viajes; las reservas y los cupos mostrados permanecen como datos demostrativos, sin guardar solicitudes ni modificar disponibilidad.

## Tecnologías

- Kotlin y Android.
- Jetpack Compose y Material 3 para la interfaz.
- Navigation 3 para las rutas y los retrocesos.
- AndroidX ViewModel para los estados de acceso, vehículo e Inicio.
- Gradle Wrapper para la compilación.

## Navegación actual

```text
Inicio pasajero
  → Buscar Wheels
  → Wheels disponibles
  → Ver Wheel
  → Detalle del Wheel
  → Solicitar cupo
  → Mis viajes / Reservas
  → Ver reserva
  → Detalle de reserva
```

**Reservas** y **Mis Wheels** son dos pestañas internas de Mis viajes. Los detalles permiten volver a la lista correspondiente. Una sola barra inferior mantiene Inicio activo en el flujo de Wheels y Mis viajes activo en el flujo de reservas.

## Pantallas actuales

| Área | Pantallas y alcance |
| --- | --- |
| Bienvenida | Bienvenida y presentación inicial. |
| Acceso | Iniciar sesión, registro, recuperar contraseña, verificar código, restablecer contraseña y confirmación, con validaciones locales. |
| Principal | Inicio según el rol, Mis viajes con Reservas y Mis Wheels, y Perfil con datos locales y cierre de sesión. |
| Pasajero | Wheels disponibles, detalle del Wheel y detalle de reserva. |
| Conductor | Invitación a conducir, registro de vehículo y confirmación; Inicio muestra el próximo viaje. Publicar y administrar recorridos siguen como acciones visuales. |
| Mensajes | Pantalla de estado en construcción. |

## Capturas de la aplicación

Capturas de JaveWheels ejecutado en el emulador Android.

| Inicio pasajero | Wheels disponibles | Detalle del Wheel |
| --- | --- | --- |
| <img src="docs/images/inicio-pasajero.png" alt="Inicio pasajero en el emulador" width="240"> | <img src="docs/images/wheels-disponibles.png" alt="Wheels disponibles en el emulador" width="240"> | <img src="docs/images/detalle-wheel.png" alt="Detalle del Wheel en el emulador" width="240"> |

| Mis viajes / Reservas | Mis viajes / Mis Wheels | Detalle de reserva |
| --- | --- | --- |
| <img src="docs/images/mis-viajes-reservas.png" alt="Reservas en el emulador" width="240"> | <img src="docs/images/mis-viajes-wheels.png" alt="Mis Wheels en el emulador" width="240"> | <img src="docs/images/detalle-reserva.png" alt="Detalle de reserva en el emulador" width="240"> |

## Compilación y validación

El proyecto utiliza Android SDK 37, admite dispositivos desde Android API 24 y se compiló con JDK 25. Configura el SDK en Android Studio y ejecuta desde la raíz del repositorio:

```powershell
.\gradlew.bat :app:assembleDebug
```

El APK de depuración se genera en `app/build/outputs/apk/debug/app-debug.apk`.

La validación del 1 de octubre de 2026 terminó con **BUILD SUCCESSFUL**. Se comprobó en el emulador el flujo completo del pasajero, el cambio entre Reservas y Mis Wheels, los detalles, los retrocesos y la selección de la barra inferior. La ejecución de `:app:lintDebug` quedó sin resultado al detenerse durante el análisis de comentarios.

## Estado actual

- Navegación principal y cambio de rol dentro de una misma cuenta.
- Vistas de Wheels, conductor, vehículo, aporte, cupos y recogida.
- Detalle del Wheel seleccionado y navegación hacia Mis viajes.
- Reservas confirmadas y pendientes, historial de Wheels finalizados y detalle de reserva.
- Mapas ilustrativos locales y componentes visuales compartidos.
- Avisos breves para filtros, solicitudes pendientes, resúmenes, mensajería, compartir viaje y cancelar reserva. Estas acciones conservan los datos locales.

## Funcionalidades pendientes

- Backend y autenticación conectada.
- Persistencia de usuarios, vehículos, recorridos y reservas.
- Solicitud y gestión efectiva de cupos.
- Publicación y administración de recorridos del conductor.
- Filtros y búsqueda de recorridos.
- Mapas, ubicación y seguimiento en tiempo real.
- Mensajería completa y acciones efectivas de compartir y cancelar reservas.
