# Prueba-Tecnica-ML
 
## Requerimientos técnicos para construir el proyecto:

Debe disponer de un IDE para correr el proyecto, se recomienda el uso de Android Studio. Para la 
instalación del mismo debe contar como mínimo con
    - Microsoft Windows 7/8/10 (32-64 bits).
    - 8 GB de RAM recomendados (2 GB mínimo).
    - 4 GB de espacio en disco duro (2 GB mínimo).
    - Pantalla con resolución mínima de 1280×800 píxeles.
    - Procesador Intel.
    - Java 8
    - JDK 8
Luego de tenerlo utilice git para clonar el repositorio del proyecto, corriendo el siguiente comando en
consola:

    git clone https://github.com/Bryanalmo/Prueba-Tecnica-ML.git

Luego de esto deberá abrir el proyecto en el IDE, antes de correrlo por primera vez asegurese de sincronizar 
las dependencias de gradle y hacer el build del proyecto.

## Arquitectura 

Dentro de la arquitectura de la app se utilizó clean architecture, con el patrón MVVM (Model View ViewModel) estableciendo las siguientes capas:

  - app: Esta es la capa con la cual interactua el usuario, esta es la encargada de todo lo 
    relacionado con el UI, incluyendo vistas (activities, fragments, etc)y otro componentes como ViewModels, Adaptadores, 
    etc. Esta capa incluirá a las demás, haciendo uso de sus clases e implementaciones.

  - domain: Es la capa en donde se encuentran la logica de negocio de la aplicación, en esta se incluyen los casos
    de uso (Interactors), modelos y definición de las llamadas a los servicios.

  - data: En esta capa se definen las fuentes de datos de la aplicación, esta implementará las interfaces de la capa 
    domain
    
En cada una de las capas mencionadas se realizaron las pruebas unitarias necesarias para garantizar el funcionamiento de la aplicación

## Flujo dentro de la aplicación 

En la primera pantalla se muestran una lista con todas las categorías disponibles
![image](https://user-images.githubusercontent.com/40524653/113515134-41d33500-9538-11eb-8668-85148ff79dab.png) with <img src="https://user-images.githubusercontent.com/40524653/113515134-41d33500-9538-11eb-8668-85148ff79dab.png" width="100" height="100">

Al seleccionar el buscador en la parte superior se mostrará la pantalla de busqueda en la cual, el usuario prodrá ver su historial de busqueda
así como escribir el producto que desea buscar
![Diagram](https://user-images.githubusercontent.com/40524653/113490892-a1770500-9492-11eb-843d-91e4d022f14a.png)


Luego de presionar el botón para buscar en el teclado si se encuentran resultados el usuario verá la lista de items, de lo contrario verá un mensaje 
indicando que debe intentar de nuevo 
![Diagram](https://user-images.githubusercontent.com/40524653/113490979-2530f180-9493-11eb-911b-7b1a958adf7a.png)

Al seleccionar un item de la lista el usuarios verá el detalle del producto en el cual también encontrará información relacionada con el vendedor
![Diagram](https://user-images.githubusercontent.com/40524653/113491052-848f0180-9493-11eb-8f23-e1745a3a7074.png)
![Diagram](https://user-images.githubusercontent.com/40524653/113491055-8b1d7900-9493-11eb-8884-33f9332da714.png)
![Diagram](https://user-images.githubusercontent.com/40524653/113491062-907ac380-9493-11eb-919a-b9aee3b2f886.png)

## Conceptos y librerías utilizadas

  - Clean Architecture
  - MVVM 
  - Injección de dependencias con Koin
  - Glide para cargar imagenes
  - Navigation Component
  - Retrofit2 para llamadas al API
  - Room como base de datos local para guardar las busquedas recientes
  - Coroutines
  - Paginación para cargar items progresivamente 
  - Shimmer Layout (loaders)
  - Pruebas unitarias



 
