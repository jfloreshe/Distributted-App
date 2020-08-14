# Restaurante "El Ganso"
## Introducción
El presente texto muestra la instalación en el sistema operativo Linux Ubuntu.
## Base de Datos Distribuida
### Software requerido
#### PostgreSQL v10 o superior
### Configurando postgres para la red compartida, fragmentación y replicación
Luego de instalar postgreSQL necesitamos configurar cada nodo para esto es recomendable tener la información de cada uno como se muestra en la siguiente imagen.
![alt text](https://github.com/jfloreshe/INTERDISCIPLINAR/blob/master/bd1.png)
Desde cada nodo tenemos que modificar 2 archivos con permisos de root.
Para ingresar al primero ponemos el siguiente comando.
#### sudo vim /etc/postgresql/12/main/pg_hba.conf
Puede usar su editor de texto preferido, una vez dentro tendremos algo similar a la imagen siguiente, se dará cuenta que no es igual a lo que usted tiene esto es porque la imágen muestra el archivo `pg_hba.conf` configurado totalmente.
![alt text](https://github.com/jfloreshe/INTERDISCIPLINAR/blob/master/bdconf1.png)
Deberá copiar en su file lo que se muestra en la imágen. 
Salimos y ahora ingresamos al segundo archivo con el siguiente comando.
#### sudo vim /etc/postgresql/12/main/postgresql.conf
Deberá buscar `wal_level` con su editor de texto este se encontrará comentado debe descomentarlo y darle el valor de `logical` como se muestra en la siguiente imágen.
![alt text](https://github.com/jfloreshe/INTERDISCIPLINAR/blob/master/bdconf2.png)
Recordar hacer esto en cada nodo.
Lo que hemos realizado configura el acceso desde un nodo a otro y el tipo de replicación que realizaremos en postgresql.
Como ya podemos acceder desde un nodo a otro podemos configurar lo restante desde un solo nodo.

Para esto primero debemos crear 3 bases de datos en cada nodo.
Accedemos a postgres
#### sudo su - postgres
luego de ingresar el password correcto ingresamos el siguiente comando.
#### psql -h \<ip nodo que deseamos acceder\> \[-p \<puerto del nodo\>\] -U \<usuario\>
Este comando nos permite ingresar al nodo que deamos, al puerto donde se esta ejecutando postgresql con un determinado usuario.
Recordando la primera imágen ingresaremos al primer nodo con los datos proporcionados.
![alt text](https://github.com/jfloreshe/INTERDISCIPLINAR/blob/master/bdconf3.png)
Una vez dentro creamos las bases de datos.
#### create database restaurantenodo\<numero de nodo actual\>;
#### create database restaurantenodo\<replica del nodo\>;
#### create database restaurante;
Una vez hecho esto nos aseguramos que hemos creado correctamente las bases de datos colocando
#### \\list
Como se muestra en la siguiente imágen.
![alt text](https://github.com/jfloreshe/INTERDISCIPLINAR/blob/master/bdconf4.png)
En `nodo1` hemos creado una base de datos para todo los datos que se guardarán en el nodo 1, otra base de datos para todos los datos que se replicarán del nodo4 y una última base de datos `restaurante` que manejará la concurrencia, restricciones, redirección correcta de los datos, integridad, triggers, funciones y otros dando la apariencia de una base de datos centralizada pero que en realidad está distribuida entre los 4 nodos.
Ahora este paso se repite para cada nodo, ingresaremos al nodo 3 y realizaremos lo mismo.
![alt text](https://github.com/jfloreshe/INTERDISCIPLINAR/blob/master/bdconf5.png)
Como se muestra en la figura primero hemos ingresado al nodo 3 usando
#### psql -h 25.29.134.33 -p 5432 -U postgres 
Una vez dentro hemos creado las tablas y usando `list` vemos que tenemos la base de datos `restaurantenodo3` que es donde guardaremos todos los datos del nodo 3, `restaurantenodo2` que es donde se guardará la réplica del nodo 2 y restaurante.
Entonces siguiendo esta lógica hacemos lo mismo para el nodo 2 y 4 siguiendo el concepto de la siguiente imágen.
![alt text](https://github.com/jfloreshe/INTERDISCIPLINAR/blob/master/bdconf6.png)
```
En el nodo 1 se crea restaurantenodo1, restaurantenodo4 y restaurante.
En el nodo 2 se crea restaurantenodo2, restaurantenodo1 y restaurante.
En el nodo 3 se crea restaurantenodo3, restaurantenodo2 y restaurante.
En el nodo 4 se crea restaurantenodo4, restaurantenodo3 y restaurante.
```
Ahora ejecutamos los scripts correspondientes para crear la fragmentación, replicación y todo le mencionado en la propuesta.
Para realizar una ejecución de script salimos por completo de postgres y volvemos a ingresar usando
#### sudo su - postgres
Una vez dentro ejecutamos el siguiente comando
#### psql -h \<ip nodo que deseamos acceder\> \[-p \<puerto del nodo\>\] -U \<usuario\> -d \<nombre de la base de datos\> -a -f \<el camino completo al archivo\>
Ejecutaremos lo anterior para el nodo 1
![alt text](https://github.com/jfloreshe/INTERDISCIPLINAR/blob/master/bdconf7.png)
Ahora que sabemos como ejecutar un script desde terminal.
Realizamos la ejecución de los scripts en el siguiente orden

#### 1. Ejecutar en cada nodo el script `nodo<numero de nodo actual>.sql` para la base de datos `restaurantenodo<numero de nodo>`
#### 2. Ejecutar en cada nodo el script `nodoreplica<numero de nodo replica>.sql` para la base de datos `restaurantenodo<numero de nodo replica>`
#### 3. Ejecutar en cada nodo el script `restaurantScript.sql` para la base de datos `restaurante`
#### 4. Ejecutar en cada nodo el script `masterForeignTables.sql` para la base de datos `restaurante`
#### 5. Ejecutar en cada nodo el script `trigger.sql` para la base de datos `restaurante`
#### 6. Ejecutar en cada nodo el script `CreateFunctions.sql` para la base de datos `restaurante`
#### 7. Ejecutar en un solo nodo el script `DatosIniciales.sql` para la base de datos `restaurante`
Con esto ya tendremos la base de datos funcional y lista para ser llamada desde nuestra interfaz de usuario.

## Frontend y Backend
La parte del frontend se encuentra dentro de la carpeta client y el backend se encuentra dentro de la carpeta api

### Software requerido
#### NodeJS v12.16.2
#### yarn v1.22.4

### Frontend
El frontend se ejecutará en un servidor de desarrollo, para hacer esto dirigirse a la carpeta client y ejecutar el comando
#### `yarn`
que servirá para que se descarguen todos los módulos y dependencias necesarias para la parte del frontend.

Luego para iniciar el servidor de desarrollo ejecutar 
#### `yarn start`

Runs the app in the development mode.<br />
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.
