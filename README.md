# Restaurante "El Ganso"
## Introducción
El presente texto muestra la instalación en el sistema operativo Linux Ubuntu.
## Base de Datos Distribuida
### Software requerido
#### PostgreSQL v10 o superior
### Configurando postgres para la red compartida, fragmentación y replicación
Luego de instalar postgreSQL necesitamos configurar cada nodo para esto es recomendable tener la información de cada uno como se muestra en la siguiente imagen.
![alt text](https://bd1.png)
Desde cada nodo tenemos que modificar 2 archivos con permisos de root.
Para ingresar al primero ponemos el siguiente comando.
#### sudo vim /etc/postgresql/12/main/pg_hba.conf
Puede usar su editor de texto preferido, una vez dentro tendremos algo similar a la imagen siguiente, se dará cuenta que no es igual a lo que usted tiene esto es porque la imágen muestra el archivo `pg_hba.conf` configurado totalmente.
![alt text](https://bdconf1.png)
Deberá copiar en su file lo que se muestra en la imágen. 
Salimos y ahora ingresamos al segundo archivo con el siguiente comando.
#### sudo vim /etc/postgresql/12/main/postgresql.conf
Deberá buscar `wal_level` con su editor de texto este se encontrará comentado debe descomentarlo y darle el valor de `logical` como se muestra en la siguiente imágen.
![alt text](https://bdconf2.png)
Recordar hacer esto en cada nodo.
Lo que hemos realizado configura el acceso desde un nodo a otro y el tipo de replicación que realizaremos en postgresql.
Como ya podemos acceder desde un nodo a otro podemos configurar lo restante desde un solo nodo.

Para esto primero debemos crear 3 bases de datos en cada nodo.
Accedemos a postgres
#### sudo su - postgres
luego de ingresar el password correcto ingresamos el siguiente comando
#### psql -h <ip nodo que deseamos acceder> [-p <puerto del nodo>] -U <usuario>


Con esto podemos acceder desde cualquier nodo a otro nodo.
Ahora ingresamos al siguiente archivo con el siguiente comando.
#### sudo vim /etc/postgresql/12/main/postgresql.conf

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
