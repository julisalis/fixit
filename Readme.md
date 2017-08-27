# FixIt

<img src="https://github.com/julisalis/fixit/blob/master/src/main/resources/static/images/logo.png" width="100">

## Desarrollo:

Java8, Spring Boot, Maven, MySql, Hibernate

###### IntelliJ Idea
- https://www.jetbrains.com/idea/
- Hacerse cuenta con mail de UTN, despues hay que pedir una licencia estudiantil que aparece en los detalles de la cuenta.

###### Java 8
- http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
- Instalar
- Mi equipo -> configuracion avanzada del sistema -> variables de entorno
- - Agregar variable JAVA_HOME que apunte a la carpeta donde esta el jdk instalado
- - Agregar una nueva entrada (NO borrar todo lo que ya hay) en la variable path que apunte a %JAVA_HOME%\bin
  
###### Maven
- https://maven.apache.org/download.cgi (Bin / Link)
- Descomprimir (Haganlo en alguna carpeta Maven en C:)
- Mi equipo -> configuracion avanzada del sistema -> variables de entorno
- - Agregar variable MAVEN_HOME que apunte a la carpeta donde esta maven instalado
- - Agregar una nueva entrada (NO borrar todo lo que ya hay) en la variable path que apunte a %MAVEN_HOME%\bin
  
###### MySql
- https://dev.mysql.com/downloads/windows/installer/5.7.html (bajan el primero de los dos que aparecen con download)
- Instalar. Dar todo ok, cuando pide password, poner root.
- Ya instalado, abrir cmd, ir a C:\Program Files\MySQL\MySQL Server 5.7\bin y poner: mysql -u root -p
- - Va a pedir contraseña, poner root. Ahi entra a la consola de MySQL.
- - poner: create user 'springuser'@'localhost' identified by 'spring'; (Le creamos un user a la app)
- - poner: CREATE DATABASE fixit; (crean la base)
- - poner: grant all on fixit.* to 'springuser'@'localhost'; (Le dan permisos al usuario creado)

###### Github con IntelliJ
- https://www.jetbrains.com/help/idea/2017.1/registering-github-account-in-intellij-idea.html

###### Tutorial Spring Boot
- https://springframework.guru/spring-boot-web-application-part-1-spring-initializr/

###### Jquery validator
-https://jqueryvalidation.org/documentation/

