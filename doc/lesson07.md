# Стажировка <a href="https://github.com/JavaOps/topjava">Topjava</a>

## <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFfjVnUVhINEg0d09Nb3JsY2ZZZmpsSWp3bzdHMkpKMmtPTlpjckxyVzg0SWc">Материалы занятия</a>

### ![correction](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Правки в проекте

#### Apply 7_0_fix.patch

- ключи мапы в xml конфигурации можно писать без кавычек (`systemEnvironment[TOPJAVA_ROOT]`)
- мелкие правки

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Разбор домашнего задания HW6

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. <a href="https://drive.google.com/open?id=1luHTJOXQW-BWyueqsfzzAeiP8lUTZkje">HW6</a>

#### Apply 7_01_HW6_fix_tests.patch

> Инжекшен в `AbstractUserServiceTest.jpaUtil` сделал [`@Lazy`: не иннициализировать бин до первого использования](https://www.logicbig.com/tutorials/spring-framework/spring-core/lazy-at-injection-point.html).

#### Apply 7_02_HW6_meals.patch

> сделал фильтацию еды через `get`: операция идемпотентная, можно делать в браузере обновление по F5

#### Внимание: чиним пути в следующем патче

При переходе на AJAX `JspMealController` удалим за ненадобностью, возвращение всей еды `meals()` останется в `RootController`.

#### Apply 7_03_HW6_fix_relative_url_utf8.patch

- <a href="http://stackoverflow.com/questions/4764405/how-to-use-relative-paths-without-including-the-context-root-name">Relative paths in JSP</a>
- <a href="http://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/mvc.html#mvc-redirecting-redirect-prefix">Spring redirect: prefix</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFaXViWkkwYkF0eW8">HW6 Optional</a>

#### Apply 7_04_HW6_optional_add_role.patch

> - Для доставания ролей у нас дублируется `fetch = EAGER` и `LEFT JOIN FETCH u.roles` (можно делать что-то одно). Запросы выполняются по-разному: проверьте.

`JdbcUserServiceTest` отвалились. Будем чинить в `7_06_HW6_optional_jdbc.patch`

#### Apply 7_05_fix_hint_graph.patch

- В `JpaUserRepositoryImpl.getByEmail` DISTINCT попадает в запрос, хотя он там не нужен. Это просто указание Hibernate не дублировать данные. Для оптимизации можно указать Hibernate делать запрос без
  distinct: [15.16.2. Using DISTINCT with entity queries](https://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#hql-distinct)
    - Бага [HINT_PASS_DISTINCT_THROUGH does not work if 'hibernate.use_sql_comments=true'](https://hibernate.atlassian.net/browse/HHH-13280). При `hibernate.use_sql_comments=false` все работает- в
      SELECT нет DISTINCT.

- Тест `DataJpaUserServiceTest.getWithMeals()` не работает для admin (у админа 2 роли, и еда при JOIN дублируется). `DISTINCT` при нескольких JOIN не помогает, оставил в графе только `meals`.

#### Apply 7_06_HW6_optional_jdbc.patch

> - в `JdbcUserRepositoryImpl.getAll()` собираю роли из `ResultSet` напрямую в `map`
> - в `insertRoles` поменял метод `batchUpdate` и сделал проверку на empty
> - в `setRoles` достаю роли через `queryForList`

Еще интересные JDBC реализации:

- в `getAll()/ get()/ getByEmail()` делать запросы с `LEFT JOIN` и сделать реализацию `ResultSetExtractor`
- подключить зависимость `spring-data-jdbc-core`. Там есть готовый `OneToManyResultSetExtractor`. Можно посмотреть, как он реализован.
- реализация, зависимая от БД (для postgres): доставать агрегированные роли и делать им `split(",")`:

```
SELECT u.*, string_agg(r.role, ',') AS roles
FROM users u
  JOIN user_roles r ON u.id=r.user_id
GROUP BY u.id
```

### Валидация для `JdbcUserRepository` через Bean Validation API

#### Apply 7_07_HW6_optional_jdbc_validation.patch

- [Валидация данных при помощи Bean Validation API](https://alexkosarev.name/2018/07/30/bean-validation-api/). Т.к. `Validator` thread-safe (из Javadoc), его лучше создать один раз и переиспользовать
- Проверку `@NotNull Meal.user` пока закомментировал. Починим в 10-м занятии через `@JsonView`.

### Отключение кэша в тестах:

#### Apply 7_08_HW06_optional2_disable_tests_cache.patch

> - Удалил очистку кэша и `JpaUtil`
> - Переопределил в тестах `spring-cache.xml`: для отключения кэша Hibernate 2-го уровня `noOpCacheManager` недостаточно, требуется выставить `hibernate.cache.use_second_level_cache=false`. Кроме стандартного использования файла пропертей можно задать их прямо в конфигурации (через автодополнение в xml можно смотреть все варианты)
    >

- [Example of PropertyOverrideConfigurer](https://www.concretepage.com/spring/example_propertyoverrideconfigurer_spring)

> - [Spring util schema](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#xsd-schemas-util)

## Занятие 7:

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 3. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFQXhBN1pqa3FyOUE">Тестирование Spring MVC</a>

#### Apply 7_09_controller_test.patch

> - в `MockMvc` добавился `CharacterEncodingFilter`
> - добавил `AllActiveProfileResolver`
> - сделал вспомогательный метод `AbstractControllerTest.perform()`

- <a href="https://github.com/hamcrest/hamcrest-junit">Hamcrest</a>
- <a href="http://www.petrikainulainen.net/programming/spring-framework/unit-testing-of-spring-mvc-controllers-normal-controllers/">Unit Testing of Spring MVC Controllers</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png)  4. [Миграция на JUnit 5](https://drive.google.com/open?id=16wi0AJLelso-dPuDj6xaGL7yJPmiO71e)

#### Apply 7_10_JUnit5.patch

> - [No need `junit-platform-surefire-provider` dependency in `maven-surefire-plugin`](https://junit.org/junit5/docs/current/user-guide/#running-tests-build-maven)
> - [Наконец пофиксили баг с `@SpringJUnitConfig`](https://youtrack.jetbrains.com/issue/IDEA-166549)

- [JUnit 5 homepage](https://junit.org/junit5)
- [Overview](https://junit.org/junit5/docs/snapshot/user-guide/#overview)
- [10 интересных нововведений](https://habr.com/post/337700)
- Дополнительно:
    - [Extension Model](https://junit.org/junit5/docs/current/user-guide/#extensions)
    - [A Guide to JUnit 5](http://www.baeldung.com/junit-5)
    - [Migrating from JUnit 4](http://www.baeldung.com/junit-5-migration)
    - [Before and After Test Execution Callbacks](https://junit.org/junit5/docs/snapshot/user-guide/#extensions-lifecycle-callbacks-before-after-execution)
    - [Conditional Test Execution](https://junit.org/junit5/docs/snapshot/user-guide/#writing-tests-conditional-execution)
    - [Third party Extensions](https://github.com/junit-team/junit5/wiki/Third-party-Extensions)
    - [Реализация assertThat](https://stackoverflow.com/questions/43280250)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 5. [Принципы REST. REST контроллеры](https://drive.google.com/open?id=1e4ySjV15ZbswqzL29UkRSdGb4lcxXFm1)

#### Apply 7_11_rest_controller.patch

- <a href="http://spring-projects.ru/understanding/rest/">Понимание REST</a>
- <a href="https://ru.wikipedia.org/wiki/JSON">JSON (JavaScript Object Notation)</a>
- [15 тривиальных фактов о правильной работе с протоколом HTTP](https://habrahabr.ru/company/yandex/blog/265569/)
- [10 Best Practices for Better RESTful](https://medium.com/@mwaysolutions/10-best-practices-for-better-restful-api-cbe81b06f291)
- [Best practices for rest nested resources](https://stackoverflow.com/questions/20951419/what-are-best-practices-for-rest-nested-resources)
- <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-requestmapping">Request mapping</a>
- [Лучшие практики разработки REST API: правила 1-7,15-17](https://tproger.ru/translations/luchshie-praktiki-razrabotki-rest-api-20-sovetov/)  
- Дополнительно:
    - [Подборка практик REST](https://gist.github.com/Londeren/838c8a223b92aa4017d3734d663a0ba3)
    - <a href="http://www.infoq.com/articles/springmvc_jsx-rs">JAX-RS vs Spring MVC</a>
    - <a href="http://habrahabr.ru/post/144011/">RESTful API для сервера – делаем правильно (Часть 1)</a>
    - <a href="http://habrahabr.ru/post/144259/">RESTful API для сервера – делаем правильно (Часть 2)</a>
    - <a href="https://www.youtube.com/watch?v=Q84xT4Zd7vs&list=PLoij6udfBncivGZAwS2yQaFGWz4O7oH48">И. Головач. RestAPI</a>
    - [value/name в аннотациях @PathVariable и @RequestParam](https://habr.com/ru/post/440214/)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 6. [Тестирование REST контроллеров. Jackson.](https://drive.google.com/open?id=1aZm2qoMh4yL_-i3HhRoyZFjRAQx-15lO)

#### Apply 7_12_rest_test_jackson.patch

- [Jackson databind github](https://github.com/FasterXML/jackson-databind)
- [Jackson Annotation Examples](https://www.baeldung.com/jackson-annotations)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 7. [Кастомизация Jackson Object Mapper](https://drive.google.com/open?id=1CM6y1JhKG_yeLQE_iCDONnI7Agi4pBks)

#### Apply 7_13_jackson_object_mapper.patch

- Сериализация hibernate lazy-loading с помощью <a href="https://github.com/FasterXML/jackson-datatype-hibernate">jackson-datatype-hibernate</a>
- <a href="https://geowarin.github.io/jsr310-dates-with-jackson.html">Handle Java 8 dates with Jackson</a>
- Дополнительно:
    - <a href="https://www.sghill.net/how-do-i-write-a-jackson-json-serializer-deserializer.html">Jackson JSON Serializer & Deserializer</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 8. [Тестирование REST контроллеров через JSONassert и Матчеры](https://drive.google.com/open?id=1oa3e0_tG57E71g6PW7_tfb3B61Qldctl)

#### Apply 7_14_json_assert_tests.patch

- [JSONassert](https://github.com/skyscreamer/JSONassert)
- [Java Code Examples for ObjectMapper](https://www.programcreek.com/java-api-examples/index.php?api=com.fasterxml.jackson.databind.ObjectMapper)

#### Apply 7_15_tests_refactoring.patch
- [Java @SafeVarargs Annotation](https://www.baeldung.com/java-safevarargs)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 9. [Тестирование через SoapUi. UTF-8](https://drive.google.com/open?id=0B9Ye2auQ_NsFVXNmOUdBbUxxWVU)

#### Apply 7_16_soapui_utf8_converter.patch

- Инструменты тестирования REST:
    - <a href="https://www.soapui.org/downloads/soapui/">SoapUi</a>
    - <a href="http://rus-linux.net/lib.php?name=/MyLDP/internet/curlrus.html">Написание HTTP-запросов с помощью Curl</a>.  
      Для Windows 7 можно использовать Git Bash, с Windows 10 v1803 можно прямо из консоли. Возможны проблемы с UTF-8:
        - [CURL doesn't encode UTF-8](https://stackoverflow.com/a/41384903/548473)
        - [Нстройка кодировки в Windows](https://support.socialkit.ru/ru/knowledge-bases/4/articles/11110-preduprezhdenie-obnaruzhenyi-problemyi-svyazannyie-s-raspoznavaniem-russkih-simvolov)
    - **[IDEA: Tools->HTTP Client->...](https://www.jetbrains.com/help/idea/rest-client-tool-window.html)**
    - <a href="https://www.getpostman.com/">Postman</a>
    - [Insomnia REST client](https://insomnia.rest/)

**Импортировать проект в SoapUi из `config\Topjava-soapui-project.xml`. Response смотреть в формате JSON.**

> Проверка UTF-8: <a href="http://localhost:8080/topjava/rest/profile/text">http://localhost:8080/topjava/rest/profile/text</a>

[ResponseBody and UTF-8](http://web.archive.org/web/20190102203042/http://forum.spring.io/forum/spring-projects/web/74209-responsebody-and-utf-8)

## ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Ваши вопросы

> Зачем у нас и UIController'ы, и RestController'ы? То есть в общем случае backend-разработчику недостаточно предоставить REST-api и RestController?

В общем случае нужны и те и другие. REST обычно используют для отдельного UI например на React или Angular или для интеграции / мобильного приложения. У нас REST контроллеры используются только для
тестирования. UI мы используем для нашего приложения на JSP шаблонах. Таких сайтов без богатой UI логики тоже немало. Например https://javaops.ru/ :)  
Разница в запросах:

- для UI используются только GET и POST
- при создании-обновлении в UI мы принимаем данные из формы `application/x-www-form-urlencoded` (посмотрите вкладку `Network`, не в формате JSON)
- для REST запросы GET, POST, PUT, DELETE, PATCH и возвращают только данные (обычно JSON)

и в способе авторизации:

- для RESТ у нас будет базовая авторизация
- UI через cookies

Также часто бывают смешанные сайты - где есть и отдельное JS приложение и шаблоны.

> При выполнении тестов через MockMvc никаких изменений на базе не видно, почему оно не сохраняет?

`AbstractControllerTest` аннотируется `@Transactional` - это означает, что тесты идут в транзакции, и после каждого теста JUnit делает rollback базы.

> Что получается в результате выполнения запроса `SELECT DISTINCT(u) FROM User u LEFT JOIN FETCH u.roles ORDER BY u.name, u.email`? В чем разница в SQL без `DISTINCT`.

Запросы SQL можно посмотреть в логах. Т.е. `DISTINCT` в `JPQL` влияет на то, как Hibernate обрабатывает дублирующиеся записи (с `DISTINCT` их исключает). Результат можно посмотреть в тестах или
приложении, поставив брекпойнт. По поводу `SQL DISTINCT` не стесняйтесь пользоваться google, например, [оператор SQL DISTINCT](http://2sql.ru/novosti/sql-distinct/)

> В чем заключается расширение функциональности hamcrest в нашем тесте, что нам пришлось его отдельно от JUnit прописывать?

hamcrest-all используется в проверках `RootControllerTest`: `org.hamcrest.Matchers.*`

> Jackson мы просто подключаем в помнике, и Spring будет с ним работать без любых других настроек?

Да, Spring смотрит в classpath и если видит там Jackson, то подключает интеграцию с ним.

> Где-то слышал, что любой ресурс по REST должен однозначно идентифицироваться через url без параметров. Правильно ли задавать URL для фильтрации в виде `http://localhost/topjava/rest/meals/filter/{startDate}/{startTime}/{endDate}/{endTime}` ?

Так делают, только при отношении <a href="https://ru.wikipedia.org/wiki/Диаграмма_классов#.D0.90.D0.B3.D1.80.D0.B5.D0.B3.D0.B0.D1.86.D0.B8.D1.8F">агрегация</a>, например, если давать админу право
смотреть еду любого юзера, URL мог бы быть похож на `http://localhost/topjava/rest/users/{userId}/meals/{mealId}` (не рекомендуется, см ссылку ниже). В случае критериев поиска или страничных данных
они передаются как параметр. Смотри также:

- [15 тривиальных фактов о правильной работе с протоколом HTTP](https://habrahabr.ru/company/yandex/blog/265569/)
- <a href="https://medium.com/@mwaysolutions/10-best-practices-for-better-restful-api-cbe81b06f291">10 Best Practices for Better RESTful
- [REST resource hierarchy (если кратко: не рекомендуется)](https://stackoverflow.com/questions/15259843/how-to-structure-rest-resource-hierarchy)

> Что означает конструкция в `JsonUtil`: `reader.<T>readValues(json)`;

См. <a href="https://docs.oracle.com/javase/tutorial/java/generics/methods.html">Generic Methods</a>. Когда компилятор не может вывести тип, можно его уточнить при вызове generic метода. Неважно,
static или нет.

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Домашнее задание HW07

- 1: Добавить тесты контроллеров:
    - 1.1 `RootControllerTest.getMeals` для `meals.jsp`
    - 1.2 Сделать `ResourceControllerTest` для `style.css` (проверить `status` и `ContentType`)
- 2: Реализовать `MealRestController` и протестировать его через `MealRestControllerTest`
    - 2.1 следите, чтобы url в тестах совпадал с параметрами в методе контроллера. Можно добавить логирование `<logger name="org.springframework.web" level="debug"/>` для проверки маршрутизации.
    - 2.2 в параметрах `getBetween` принимать `LocalDateTime` (конвертировать через <a href="http://blog.codeleak.pl/2014/06/spring-4-datetimeformat-with-java-8.html">@DateTimeFormat with Java 8
      Date-Time API</a>), пока без проверки на `null` (используя `toLocalDate()/toLocalTime()`, см. Optional п.3). В тестах передавать в формате `ISO_LOCAL_DATE_TIME` (
      например `'2011-12-03T10:15:30'`).

### Optional

- 3: Переделать `MealRestController.getBetween` на параметры `LocalDate/LocalTime` c раздельной фильтрацией по времени/дате, работающий при `null` значениях (см. демо и `JspMealController.getBetween`)
  . Заменить `@DateTimeFormat` на свои LocalDate/LocalTime конверторы или форматтеры.
    - <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#core-convert">Spring Type Conversion</a>
    - <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#format">Spring Field Formatting</a>
    - <a href="http://stackoverflow.com/questions/13048368/difference-between-spring-mvc-formatters-and-converters">Difference between Spring MVC formatters and converters</a>
- 4: Протестировать `MealRestController` (SoapUi, curl, IDEA Test RESTful Web Service, Postman). Запросы `curl` занести в отдельный `md` файл (или `README.md`)
- 5: Добавить в `AdminRestController` и `ProfileRestController` методы получения пользователя вместе с едой (`getWithMeals`, `/with-meals`).
    - [Jackson – Bidirectional Relationships](https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion)

### Optional 2
- 6: Сделать тесты на методы контроллеров `getWithMeals()` (п.5) 

**На следующем занятии используется JavaScript/jQuery. Если у вас там пробелы, <a href="https://github.com/JavaOPs/topjava#html-javascript-css">пройдите его основы</a>**
---------------------

## ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Типичные ошибки и подсказки по реализации

- 1: Ошибка в тесте _Invalid read array from JSON_ обычно расшифровывается немного ниже: читайте внимательно.
- 2: <a href="https://urvanov.ru/2016/12/03/jackson-и-неизменяемые-объекты/">Jackson и неизменяемые объекты</a> (для сериализации MealTo) 
- 3: Если у meal, приходящий в контроллер, поля `null`, проверьте `@RequestBody` перед параметром (данные приходят в формате JSON)
- 4: При проблемах с собственным форматтером убедитесь, что в конфигурации `<mvc:annotation-driven...` не дублируется
- 5: **Проверьте выполение ВСЕХ тестов через maven**. В случае проблем проверьте, что не портите константу из `MealTestData`
- 6: `@Autowired` в тестах нужно делать в том месте, где класс будет использоваться. Общий принцип: не размазывать код по классам, объявление переменных держать как можно ближе к ее использованию,
  группировать (не смешивать) код с разной функциональностью.
- 7: Попробуйте в `RootControllerTest.getMeals` сделать сравнение через `model().attribute("meals", expectedValue)`. Учтите, что вывод результатов через `toString` к сравнению отношения не имеет
