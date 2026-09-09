

Markdown(1).md pegado
Archivo


roltrack.zip
Archivo zip


RolTrack(1).zip
Archivo zip

d807cbce-97b4-4cb3-8633-9517de83c922.png
36c76522-019d-4924-bf58-c4de1b3c0ba9.png
06872ad1-5a5c-4d30-8b35-ab0838113303.png
adc9c953-4a70-479e-8b9f-2c227388f394.png


Pasted text(20260909-174224).txt
Documento

Sí, eso va dentro de README.md, pero lo que acabas de pegar está incompleto: te quedaste en Room / SQLite.

En README.md debes pegar todo el contenido que te mandé, desde:

# RolTrack
hasta el final de:

## Autor
No pegues comandos de Git dentro del README.

Cuando termines de pegarlo y guardes con Ctrl + S, entonces en la Terminal ejecutas:

git add README.md
git commit -m "Add RolTrack documentation"
git push
Si quieres, te puedo mandar el README otra vez en un único bloque completo, sin ninguna explicación antes ni después, para que copies todo de una.


pues dame todo en codigo 

# RolTrack

Aplicación móvil Android orientada al seguimiento de hábitos, actividad física y progreso personal.

RolTrack centraliza en una sola aplicación el registro de hidratación, alimentación, sueño y ejercicio, además de incorporar herramientas para seguimiento físico, generación de rutinas y registro de carreras mediante GPS.

---

## Características principales

### Dashboard

El dashboard presenta un resumen diario del progreso del usuario en:

- Hidratación
- Alimentación
- Sueño
- Ejercicio

También genera un puntaje diario y muestra el nivel de cumplimiento de los hábitos registrados.

### Seguimiento de hábitos

RolTrack permite registrar:

- Consumo de agua
- Alimentos y calorías
- Horas de sueño
- Actividad física

Los registros se almacenan localmente mediante Room.

### Rutinas personalizadas

La aplicación incluye un generador de rutinas basado en:

- Objetivo
- Nivel
- Tipo de entrenamiento

Incluye opciones como pérdida de grasa, ganancia muscular, fuerza, condición física, gym y calistenia.

Las rutinas generadas pueden guardarse para consultarlas posteriormente.

### Planes alimenticios

RolTrack puede generar planes alimenticios con:

- Objetivo calórico
- Proteína
- Recomendaciones
- Comidas sugeridas

Los planes también pueden guardarse dentro de la aplicación.

### Progreso físico

El usuario puede registrar y consultar:

- Peso
- Historial de peso
- IMC
- Clasificación del IMC
- Recomendaciones
- Evolución gráfica

### Running con GPS

RolTrack permite registrar carreras utilizando la ubicación del dispositivo.

Durante una carrera se registran:

- Tiempo
- Distancia
- Ritmo
- Coordenadas GPS
- Recorrido

La ruta se visualiza mediante Google Maps.

Al finalizar una carrera, el usuario puede consultar su resumen e historial.

### Exportación de carreras

La aplicación permite generar una imagen PNG con:

- Recorrido
- Distancia
- Tiempo
- Ritmo

La imagen puede guardarse o compartirse desde el dispositivo.

---

## Arquitectura

RolTrack utiliza una arquitectura basada en separación de responsabilidades:

```text
Fragments / Activities
        ↓
ViewModels
        ↓
Repositories
        ↓
DAOs
        ↓
Room / SQLite
Esto permite separar la interfaz, la lógica de presentación y el acceso a datos.

Tecnologías
Java

Android SDK

Android Studio

Room

SQLite

Google Maps SDK

Google Play Services Location

MPAndroidChart

ViewModel

LiveData

Material Design

Gradle Kotlin DSL

Git

GitHub

Persistencia de datos
La información se almacena localmente utilizando Room sobre SQLite.

La aplicación maneja registros relacionados con:

Perfil

Hidratación

Alimentación

Sueño

Ejercicio

Peso

Rutinas

Planes alimenticios

Sesiones de carrera

Puntos GPS

Esto permite que las funciones principales de RolTrack puedan utilizarse sin depender constantemente de una conexión a internet.

Estructura del proyecto
app/src/main/java/com/example/roltrack/

├── data/
│   ├── dao/
│   ├── db/
│   ├── entity/
│   └── repository/
│
├── mealplan/
├── running/
│
├── ui/
│   ├── dashboard/
│   ├── exercise/
│   ├── habits/
│   ├── meal/
│   ├── onboarding/
│   ├── profile/
│   ├── progress/
│   ├── sleep/
│   ├── stats/
│   ├── water/
│   └── workout/
│
├── utils/
├── viewmodel/
├── worker/
└── workout/
Configuración
Para utilizar Google Maps se necesita una API key de Google Maps Platform.

La clave no se almacena directamente en el repositorio.

Debe agregarse en:

local.properties
con:

MAPS_API_KEY=YOUR_GOOGLE_MAPS_API_KEY
local.properties se encuentra excluido mediante .gitignore.

Ejecución
Clonar el repositorio.

git clone https://github.com/rzxldz/RolTrack.git
Abrir el proyecto en Android Studio.

Agregar la API key de Google Maps en local.properties.

Sincronizar las dependencias de Gradle.

Ejecutar la aplicación en un emulador o dispositivo Android.

Estado del proyecto
RolTrack cuenta actualmente con módulos funcionales para:

Seguimiento de hábitos

Hidratación

Alimentación

Sueño

Ejercicio

Progreso físico

Generación de rutinas

Generación de planes alimenticios

Registro de carreras mediante GPS

Historial de carreras

Exportación de resultados

Algunas funcionalidades continúan en desarrollo y pueden ampliarse en futuras versiones.

Próximas mejoras
Integración completa de estadísticas semanales

Sistema de recordatorios configurable

Mejoras en las pruebas automatizadas

Migraciones de base de datos

Mejoras de accesibilidad

Sincronización opcional en la nube

Autor
Jorge Emmanuel Roldán Márquez

Estudiante de Ingeniería en Tecnologías de Cómputo y Telecomunicaciones
Universidad Iberoamericana Ciudad de México

GitHub: rzxldz

Portfolio: jorge-portfolio-mu.vercel.app

LinkedIn: 


pero no me das solo un archivo

Sí, justo. Aquí tienes un solo archivo listo para usar:


Solo mételo en la raíz de tu proyecto RolTrack, al mismo nivel que app/, gradle/, build.gradle.kts, etc. 



Biblioteca
/
README.md


RolTrack
Aplicación móvil Android orientada al seguimiento de hábitos, actividad física y progreso personal.

RolTrack centraliza en una sola aplicación el registro de hidratación, alimentación, sueño y ejercicio, además de incorporar herramientas para seguimiento físico, generación de rutinas y registro de carreras mediante GPS.

Características principales
Dashboard
El dashboard presenta un resumen diario del progreso del usuario en:

Hidratación

Alimentación

Sueño

Ejercicio

También genera un puntaje diario y muestra el nivel de cumplimiento de los hábitos registrados.

Seguimiento de hábitos
RolTrack permite registrar:

Consumo de agua

Alimentos y calorías

Horas de sueño

Actividad física

Los registros se almacenan localmente mediante Room.

Rutinas personalizadas
La aplicación incluye un generador de rutinas basado en:

Objetivo

Nivel

Tipo de entrenamiento

Incluye opciones como pérdida de grasa, ganancia muscular, fuerza, condición física, gym y calistenia.

Las rutinas generadas pueden guardarse para consultarlas posteriormente.

Planes alimenticios
RolTrack puede generar planes alimenticios con:

Objetivo calórico

Proteína

Recomendaciones

Comidas sugeridas

Los planes también pueden guardarse dentro de la aplicación.

Progreso físico
El usuario puede registrar y consultar:

Peso

Historial de peso

IMC

Clasificación del IMC

Recomendaciones

Evolución gráfica

Running con GPS
RolTrack permite registrar carreras utilizando la ubicación del dispositivo.

Durante una carrera se registran:

Tiempo

Distancia

Ritmo

Coordenadas GPS

Recorrido

La ruta se visualiza mediante Google Maps.

Al finalizar una carrera, el usuario puede consultar su resumen e historial.

Exportación de carreras
La aplicación permite generar una imagen PNG con:

Recorrido

Distancia

Tiempo

Ritmo

La imagen puede guardarse o compartirse desde el dispositivo.

Arquitectura
RolTrack utiliza una arquitectura basada en separación de responsabilidades:

Fragments / Activities
        ↓
ViewModels
        ↓
Repositories
        ↓
DAOs
        ↓
Room / SQLite
Esto permite separar la interfaz, la lógica de presentación y el acceso a datos.

Tecnologías
Java

Android SDK

Android Studio

Room

SQLite

Google Maps SDK

Google Play Services Location

MPAndroidChart

ViewModel

LiveData

Material Design

Gradle Kotlin DSL

Git

GitHub

Persistencia de datos
La información se almacena localmente utilizando Room sobre SQLite.

La aplicación maneja registros relacionados con:

Perfil

Hidratación

Alimentación

Sueño

Ejercicio

Peso

Rutinas

Planes alimenticios

Sesiones de carrera

Puntos GPS

Esto permite que las funciones principales de RolTrack puedan utilizarse sin depender constantemente de una conexión a internet.

Estructura del proyecto
app/src/main/java/com/example/roltrack/

├── data/
│   ├── dao/
│   ├── db/
│   ├── entity/
│   └── repository/
│
├── mealplan/
├── running/
│
├── ui/
│   ├── dashboard/
│   ├── exercise/
│   ├── habits/
│   ├── meal/
│   ├── onboarding/
│   ├── profile/
│   ├── progress/
│   ├── sleep/
│   ├── stats/
│   ├── water/
│   └── workout/
│
├── utils/
├── viewmodel/
├── worker/
└── workout/
Configuración
Para utilizar Google Maps se necesita una API key de Google Maps Platform.

La clave no se almacena directamente en el repositorio.

Debe agregarse en:

local.properties
con:

MAPS_API_KEY=YOUR_GOOGLE_MAPS_API_KEY
local.properties se encuentra excluido mediante .gitignore.

Ejecución
Clonar el repositorio.

git clone https://github.com/rzxldz/RolTrack.git
Abrir el proyecto en Android Studio.

Agregar la API key de Google Maps en local.properties.

Sincronizar las dependencias de Gradle.

Ejecutar la aplicación en un emulador o dispositivo Android.

Estado del proyecto
RolTrack cuenta actualmente con módulos funcionales para:

Seguimiento de hábitos

Hidratación

Alimentación

Sueño

Ejercicio

Progreso físico

Generación de rutinas

Generación de planes alimenticios

Registro de carreras mediante GPS

Historial de carreras

Exportación de resultados

Algunas funcionalidades continúan en desarrollo y pueden ampliarse en futuras versiones.

Próximas mejoras
Integración completa de estadísticas semanales

Sistema de recordatorios configurable

Mejoras en las pruebas automatizadas

Migraciones de base de datos

Mejoras de accesibilidad

Sincronización opcional en la nube

Autor
Jorge Emmanuel Roldán Márquez

Estudiante de Ingeniería en Tecnologías de Cómputo y Telecomunicaciones
Universidad Iberoamericana Ciudad de México

GitHub: rzxldz

Portfolio: jorge-portfolio-mu.vercel.app

LinkedIn: Jorge Emmanuel Roldán Márquez

