Java Enterprise Online Project
===============================

Наиболее востребованные технологии /инструменты / фреймворки Java Enterprise:
Maven/ Spring/ Security/ JPA(Hibernate)/ REST(Jackson)/ Bootstrap(CSS)/ jQuery + plugins.

- [Вступительное занятие](https://github.com/JavaOPs/topjava)
- [Описание и план проекта](https://github.com/JavaOPs/topjava/blob/master/description.md)
- [Wiki](https://github.com/JavaOPs/topjava/wiki)
- [Wiki Git](https://github.com/JavaOPs/topjava/wiki/Git)
- [Wiki IDEA](https://github.com/JavaOPs/topjava/wiki/IDEA)
- [Демо разрабатываемого приложения](http://topjava.herokuapp.com/)

### Миграция TopJava на Spring-Boot

Финальный код проекта BootJava с миграцией на Spring Boot  
Вычекайте в отдельную папку (как отдельный проект) ветку `spring_boot` нашего проекта (так удобнее, не придется постоянно переключаться между ветками):
```
git clone --branch spring_boot --single-branch https://github.com/JavaWebinar/topjava.git topjava_boot
```  
-------------------------------------------------------------

- Stack: [JDK 21](http://jdk.java.net/17/), Spring Boot 3.4, Lombok, H2, Caffeine Cache, Swagger/OpenAPI 3.0
- Run: `mvn spring-boot:run` in root directory.
-----------------------------------------------------
[REST API documentation](http://localhost:8080/)  
Креденшелы:

```
Admin: admin@gmail.com / admin
User:  user@yandex.ru / password
Guest: guest@gmail.com / guest
```