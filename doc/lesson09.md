# Стажировка <a href="https://github.com/JavaWebinar/topjava">TopJava</a>

## <a href="https://drive.google.com/drive/folders/0B9Ye2auQ_NsFVWRGbEw1RjJrMjg">Материалы занятия</a>

- **Браузер кэширует javascript и css. Если изменения не работают, обновите приложение в браузере по Ctrl+F5**
- **При удалении файлов не забывайте делать clean: `mvn clean package`**

### ![correction](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Правки в проекте

#### Apply 9_0_fix.patch
- Старый стандарт HTML, HTML4, требовал наличия этого атрибута в теге <script>. Обычно он имел значение type="text/javascript". На текущий момент этого больше не требуется. Более того, в современном стандарте HTML смысл этого атрибута полностью изменился, теперь он может использоваться для JavaScript-модулей.
[Тег «script»](https://learn.javascript.ru/hello-world)
- Обновил заголовок `pom.xml` 

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Разбор домашнего задания HW8

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. [HW8 + Optional 2,3](https://drive.google.com/file/d/1ZxmXrhz3K4V-mLkOOrH-JVtl5x0KSpIj)

#### Apply 9_01_HW8.patch
- [Grid system](https://getbootstrap.com/docs/4.1/layout/grid/)
- [Difference among col-* in Bootstrap](https://stackoverflow.com/a/19865627/548473)
- [Bootstrap forms](https://getbootstrap.com/docs/4.1/components/forms/)

#### ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Вопрос:
> Можно ли было удаление делать без перезагрузки таблицы (удалением строки) и для редактирования брать данные со страницы, а не с сервера?

В многопользовательском приложении принято при изменении данных подтягивать все изменения с базы, иначе может быть несогласованность базы и UI (например когда пользователей редактируют несколько администраторов одновременно). Для еды доставать из базы данные при редактировании нет необходимости, но лучше делать все универсально. В таблице обычно представлены не все данные, которые можно редактировать. Дополнительная нагрузка на базу тут совсем небольшая. Для еды нам при каждом добавлении-удалении-редактировании еще необходимо пересчитывать превышение excess.

#### Apply 9_02_HW8_clear_filter.patch

###  ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2. [HW8 Optional: enable/disable](https://drive.google.com/file/d/1-2ekRtwd60Cbqq4LPEQ_MOsqWcR7d04a)

#### Apply 9_03_HW8_enable_disable.patch

Примечание: [в публичном API выполнять PATH с параметрами нельзя](https://stackoverflow.com/questions/64390768/can-i-use-query-parameters-with-http-patch-method).
But in a situation where your API is only used by front ends that you control (for example, only called via your java script client downloaded from your web servers), and if you don't need to use any intermediate components (like a web cache) in the middle, then you might get away with it (данные у нас не кешируются).  
Предлагайте красивые решения.

## Занятие 9:

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 3.  <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFYlRkc2NGRGVydk0">Spring Binding</a>
#### Apply 9_04_binding.patch

>  Перенес `ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY` в `ru.javawebinar.topjava.util.UserUtil`

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 4.  <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFd2ZvcS1pSjdMQlU">Реализация update</a>
#### Apply 9_05_update.patch
> - Сделал интерфейс `HasId` от которого наследуются `BaseTo` и `AbstractBaseEntity`
> - Сделал проверку `id` в `ValidationUtil` на основе `HasId`
> - Сделал в `ProfileRestController` обновление своего профиля через `UserTo` (нельзя изменять себе роли) и поправил тест

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 5.  <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFLXp5MTFDMEY5WFE">Spring Validation</a>
#### Apply 9_06_validation.patch
> - `responseJSON` не выводится в случае его отсутствия (например при попытке добавить пользователя с дублирующимся email)

-  <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#validation-beanvalidation">Spring Validation.</a>
-  <a href="http://beanvalidation.org/">Bean Validation</a>
-  <a href="https://spring.io/blog/2012/08/29/integrating-spring-mvc-with-jquery-for-validation-rules">Валидация формы по AJAX.</a>
-  <a href="http://stackoverflow.com/questions/14730329/jpa-2-0-exception-to-use-javax-validation-package-in-jpa-2-0#answer-17142416">JSR-303, 349</a>
- <a href="https://dzone.com/articles/spring-31-valid-requestbody">@Valid @RequestBody + Error handling</a>
- [Java Bean Validation Basics](https://www.baeldung.com/javax-validation)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 6.  <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFcW1qeTVFdS1BdHM">Перевод DataTables на Ajax</a>
#### Apply 9_07_datatable_via_ajax.patch
> - Перешли на [параметры Datatables в формате 1.10](https://datatables.net/upgrade/1.10-convert)
> - В `makeEditable()` больше нет манипуляций c DOM, которые требуются делать ПОСЛЕ отработки плагина `datatable`, поэтому нам не обязательно вызывать ее в коллбэке `initComplete`.   

-  [DataTables Ajax](https://datatables.net/manual/ajax)

#### ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Вопрос:
> Что за дополнительный параметр (который каждый раз инкрементируется) появляется при запросе datatables данных по ajax (например `http://localhost:8080/topjava/ajax/admin/users/?_=1496156621129`) ?

Это защита `datatables` от кэширования запроса браузером. При изменении js, css и других статический ресурсов, также полезно добавлять в запрос версию, чтобы данные не брались из кэша (особенно когда приложение уже вышло в продакшен). 

#### Apply 9_08_js_i18n.patch
> - Добавил [простую интернационализацию в JavaScript](https://stackoverflow.com/questions/6218970/resolving-springmessages-in-javascript-for-i18n-internationalization). 
>   - на стороне сервера формируется `i18n` JavaScript массив с значениями, который затем используется для интернационализации в браузере
>   - в модальном окне заголовок подменяется через `$('#modalTitle').html(..title)`

> Для тестирования локали 
> - [можно поменять `Accept-Language`](https://stackoverflow.com/questions/7769061/how-to-add-custom-accept-languages-to-chrome-for-pseudolocalization-testing). Для хрома в `chrome://settings/languages` перетащить нужную локаль наверх.
> - можно поставить [Locale Switcher](https://chrome.google.com/webstore/detail/locale-switcher/kngfjpghaokedippaapkfihdlmmlafcc) хром плагин 
   
- <a href="http://stackoverflow.com/a/6242840/548473">JavaScript internationalization</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 7.  <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFUmhUTms1WnhTeHc">Форма логина / логаут.</a>
#### Apply 9_09_min_form_login.patch

> Добавил функциональность logout

-  <a href="https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#ns-minimal">Минимальный form-login</a>
-  <a href="https://docs.spring.io/spring-security/site/migrate/current/3-to-4/html5/migrate-3-to-4-xml.html#m3to4-xmlnamespace-form-login">Migrating &lt;form-login&gt;</a>

#### ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Мои вопросы:
- Почему при логине как admin еда отдаются для user?
- Почему при логине как user не отображается список пользователей?
- Почему еда не редактируется?

> Подсказка: поглядите вкладку Network в браузере.
 
#### Apply 9_10_jsp_form_login.patch
> Рефакторинг
> - В `login.jsp` вместо атрибутов достаю параметры запроса (`param.error/message`).
> - Сделал i18n описания приложения
> - При нажатии кнопок `Зайти как ...` сделал вход в приложение 

-  <a href="https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#ns-form-and-basic">Собственный form-login</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 8. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFYTA4aVN4bWxzbEU">Реализация собственного провайдера авторизации.</a>
#### Apply 9_11_auth_via_user_service.patch
> В `UserService` добавил `@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)`, т.к. без этой аннотации для кэширования создается прокси над интерфейсом `UserDetailsService` (см. следующее видео по типам проксирования Spring). Можете проверить, что без этой аннотации приложение не поднимется. 
> `GrantedAuthority` это "разрешение" или "право". Если оно дается на основе роли, в Spring Security принято использовать префикс `ROLE_`. При этом сама роль не должна иметь префикс.
>  - [Role and GrantedAuthority](https://stackoverflow.com/a/19542316/548473)

-  <a href="https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#userdetailsservice-implementations">UserDetailsService Implementations</a>
- [serialVersionUID value](https://stackoverflow.com/a/605832/548473)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 9.  <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFT2Qya2V4N0kzWWM">Принцип работы Spring Security. Проксирование.</a>
-  <a href="https://ru.wikibooks.org/wiki/Spring_Security/Технический_обзор_Spring_Security">Технический обзор Spring Security</a>
-  <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#aop-proxying">Типы проксирования</a>
-  <a href="http://samolisov.blogspot.ru/2010/04/proxy-java.html">Dynamic Proxy API</a>
-  <a href="https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#filter-stack">Security фильтры</a>
-  [Основы работы с Spring Security от Eugene Suleimanov](https://www.youtube.com/watch?v=7uxROJ1nduk)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 10. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFU3hMR0o4eGNoUmc">Spring Security Test</a>
#### Apply 9_12_spring_security_test.patch

> - Cделал "честную" авторизацию в `RootControllerTest` (через `authentication` в утильном методе `TestUtil`)
> - Cделал `mockAuthorize` для `SpringMain`, в который не попадают фильтры

-  <a href="https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#test">Spring Security Test</a></h3>
-  <a href="https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#test-mockmvc">Интеграция со Spring MVC Test</a>
-  <a href="https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#testing-http-basic-authentication">HttpBasic авторизация</a>
-  <a href="http://habrahabr.ru/post/171911/">Тестирование контроллеров с помощью MockMvc (без spring-security-test)</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 11. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFUzNFanF6MGZGNHc">Cookie. Session.</a>
-  <a href="https://ru.wikipedia.org/wiki/HTTP_cookie">HTTP cookie</a></h3>
-  <a href="http://stackoverflow.com/questions/595872/under-what-conditions-is-a-jsessionid-created">Under what conditions is a JSESSIONID created?</a>
-  <a href="http://halyph.blogspot.ru/2014/08/how-to-disable-tomcat-session.html">Tomcat Session Serialization</a>

### Дополнительно: ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 12. [Новое в Spring 5. Миграция проекта](https://javaops.ru/view/resources/spring5)

## ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Ваши вопросы

>  В куки попадает обычная строка JSESSIONID. Куда сериализуется объект User?

Для хранения состояния сессии (например корзины покупателя) в Servlet API есть механизм хранения объектов сессии (грубо - мультимапмапа, которая достается из хранилища по ключу). При создании сессии на стороне сервера (через `request.getSession`) создается кука `JSESSIONID`, которая передается между клиентом и сервером в каждом запросе и является ключом в хранилище объектов сессий. См. <a href="http://javatutor.net/books/tiej/servlets#_Toc39472970">обработка сессий с помощью сервлетов</a>

> В `login.jsp` есть форма `<form:form action="spring_security_check" ..>` Где такой url используется?

Это стандартный url для авторизации в `spring-security`. Он его и обрабатывает.

> Если не пользовать js, а писать UI на JSP, сообщения между ui и сервером будут в формате json? Это же будет JSON API?

Есть данные, которые передаются между клиентом и сервером в формате json или get/post с параметрами, есть стили взаимодействия клиента и сервера (<a href="https://ru.wikipedia.org/wiki/REST">REST</a>, <a href="http://jsonapi.org/">JSON API</a>, <a href="https://ru.wikipedia.org/wiki/JSON-RPC">JSON-RPC</a>) и есть средства генерации HTML: JSP, Javascript фреймворк, Thymleaf и пр. Не надо эти вещи путать между собой. 

> По умолчанию спринг работает с `UserDetailsService`, который должен возвращать `UserDetails`. Но мы не хотим стандартные, мы хотим свои, поэтому просто наследуем наши `UserService` и `AuthorizedUser` от соответствующих интерфейсов и реализуем недостающие методы, которые spring security и будет использовать?

Да. Сервис аутентификации конфигурируется в `spring-security.xml` `<authentication-manager>` и должен реализовывать интерфейс `UserDetailsService`. В spring-security есть его стандартные реализации, которые использовались до нашей кастомной `UserService`, например `jdbc-user-service` использует реализацию `JdbcUserDetailsManager`

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Домашнее задание HW9

- 1: Реализовать для meal Binding/ Update/ Validation. Проверить работу при пустом значении `calories`.
- 2: Перевести `meals.jsp` на работу по ajax. Стиль строки таблицы сделать в зависимости от `excess`, время отображать без `T`. Добавить i18n.
- 3: Починить meals тесты, добавить тест на неавторизованный доступ

### Optional
- 4: Подключить datetime-picker к фильтрам и модальному окну добавления/редактирования еды
  - <a href="http://xdsoft.net/jqplugins/datetimepicker/">DateTimePicker jQuery plugin</a>
  - [jQuery: конверторы](https://jquery-docs.ru/jQuery.ajax/#using-converters)

- Попробуйте при запросах по REST оставить стандартный ISO формат (с разделителем `T`). То есть:
  - Отображение и редактирование еды на UI происходит без `T` (формат значений на UI можно увидеть во вкладке браузера Network)
  - Когда мы работаем по REST, в json и запросах формат даты ISO (с разделителем `T`)
  - Напомню, что параметры методов контроллера (в том числе собранные в объекты через Binding) парсятся конверторами спринга (`@DateTimeFormat`), а объекты json парсится Jackson и они никак не влияют друг на друга.

## ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Проверка в HW09
- 1: Проверьте, что при добавлении и редактировании пользователя и еды у вас корректно отображаются заголовки модального окна:
"Добавить/Редактировать еду пользователя"
- 2: Не дублируйте
```
<c:forEach var='key' ...
i18n['${key}'] = ...
```
- 3: Для подключения css и js datetimepicker-а посмотрите в его jar (или поищите в проекте по Ctrl+Shift+N: `datetimepicker`). 
При ошибке в datetimepicker javascript, обратите внимание на **имя javascript файла** в [DateTimePicker](https://xdsoft.net/jqplugins/datetimepicker/) "How do I use it?"
- 4: datetimepicker работает корректно в Хроме, если убрать в `type` в `<input type="date/time/datetime-local" ..`
- 5: Если появляются проблемы с JS типа `... is not defined` - обратите внимание на порядок загрузки скриптов и атрибут `defer`. Скрипты должны идти в нужном порядке. Если определяете скрипт прямо в jsp, он выполняется до `defer` скриптов.
- 6: Не дублируйте обработку ошибок в `BindingResult` в ajax контроллерах
- 7: Проверьте редактирование еды: открыть на редактирование и сохранить не должно приводить к ошибкам с форматом времени.
- 8: Проверьте в `RootController.meals()`, его нужно тоже поправить
- 9: При решении с jQuery конвертором: он создает поле `responseJSON`, если внутри не вылетает по эксепшену. См. также [hasOwnProperty()](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Global_Objects/Object/hasOwnProperty)  
