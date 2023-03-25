Минипроект по Spring MVC

Tomcat - 9.0.x
Spring 5

Для запуска Docker Compose - в свойствах application.properties
использовать параметр: spring.datasource.url=jdbc:p6spy:mysql://db:3306/todo,
а для запуска из tomcat spring.datasource.url=jdbc:p6spy:mysql://localhost:3306/todo
или свое имя хоста