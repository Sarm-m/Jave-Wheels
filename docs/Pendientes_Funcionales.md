# Revisión funcional de JaveWheels

La aplicación tiene navegación y datos locales. Este análisis compara el código actual con el reparto de pantallas indicado por el equipo.

## Parte de Sebas: pendientes

| Función | Estado actual | Falta |
| --- | --- | --- |
| Buscar Ahora / Programar | Formularios con origen, destino, fecha/hora y búsqueda durante una ventana de 60 minutos. | Conectar búsquedas a viajes reales cuando exista almacenamiento compartido. |
| Wheels disponibles y filtros | Filtran datos locales por ruta, fecha/hora, cercanía, cupos, aporte y Buseta. | Compatibilidad geográfica con puntos intermedios: hoy se comparan nombres de zonas y distancias demostrativas. |
| Solicitar cupo | Abre Mis viajes. | Crear una solicitud Pendiente para el Wheel elegido; evitar duplicados y mostrar confirmación. |
| Detalle de solicitud | El detalle actual muestra la reserva confirmada de ejemplo; Ver solicitud muestra un aviso. | Identificar cada reserva por ID y presentar su estado y sus datos. |
| Cancelar reserva | Muestra un aviso. | Confirmación y actualización del estado; coordinar disponibilidad con administración del conductor. |
| Compartir viaje | Muestra un aviso. | Abrir el selector de compartir de Android con la información de la reserva. |
| Historial | Resúmenes locales de pasajero y conductor disponibles. | Obtener los viajes que realmente finalizaron y reflejar cambios de estado. |
| Guardados | Cuatro rutas predefinidas rellenan la búsqueda. | Si el equipo incluye favoritos: agregar, editar, eliminar y conservar rutas del usuario. |

La foto del vehículo no forma parte del detalle actual. Para mostrarla será necesario recibir ese dato del módulo de vehículo. Calificaciones y aportes son informativos.

## Módulos asignados a otros integrantes

- Karol: ver viaje en vivo, publicar Wheel, administrar solicitudes/Wheels y viaje en curso. Los detalles locales del conductor ya se pueden consultar; publicar y administrar aún no tienen acciones reales.
- Diego: Mensajes, Chat, Perfil y registrar/editar vehículo. Mensajes está en construcción; Perfil y registro muestran/validan datos locales.
- Andrés: autenticación e Inicio en ambos roles. Existe validación local, sin autenticación real. Actualmente cambiar a Conductor exige registrar vehículo; la especificación propone pedirlo al publicar el primer Wheel. Esa regla requiere coordinación con Inicio y Publicar Wheel.

Enviar mensaje y ver viaje en vivo desde una reserva dependen de las rutas que implementen Diego y Karol. Los botones de integración de Sebas se conectarán a esos módulos.

## Integración del equipo

Faltan almacenamiento compartido, sincronización de solicitudes/cupos/estados, sesión real y notificaciones del sistema. No se incorporan a este cambio de pantallas. Los mapas son ilustrativos y no calculan rutas ni usan GPS.

## Alcance de Ahora / Programar

- Ahora toma la hora al confirmar la búsqueda; Programar exige una fecha/hora futura.
- Ambos buscan desde esa hora hasta 60 minutos después, incluidos los extremos; la ventana puede cruzar medianoche.
- El formulario guarda la búsqueda en Inicio solo al confirmar. Atrás cancela el borrador; al volver desde resultados se conserva el formulario.
- Los viajes demostrativos tienen fechas concretas: mañana y pasado mañana, más salidas próximas generadas al cargar los datos del proceso. Las salidas vencidas se excluyen; otras fechas pueden quedar sin resultados.
- Mi ubicación conserva el comportamiento local: busca entre los orígenes disponibles sin consultar ubicación del dispositivo.
