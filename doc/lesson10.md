# Стажировкаа <a href="https://github.com/JavaWebinar/topjava">TopJava</a>

## [Материалы занятия](https://drive.google.com/open?id=0B9Ye2auQ_NsFfk43cG91Yk9pM3JxUHVhNFVVdHlxSlJtZm5oY3A4YXRtNk1KWEZxRlFNeW8)

### ![correction](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Правки в проекте

#### Apply 10_0_fix.patch
> Небольшие исправления

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Разбор домашнего задания HW9

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFZnQ2dDZsT0dvYjQ">HW9</a>
#### Apply 10_01_HW9_binding_ajax.patch

Datatables перевели на ajax (`"ajax": {"url": ajaxUrl, ..`), те при отрисовке таблица сама по этому url запрашивает данные. Поэтому в методе `RootController.meals()` нам нужно только возвратить view "meals" (`meals.jsp`) которому уже не нужны данные в атрибутах.

> - JavaScript `i18n[]` локализацию перенес в `i18n.jsp` и передаю туда `page` как параметр
>   - [JSP include action with parameter example](https://beginnersbook.com/2013/12/jsp-include-with-parameter-example)
> - Вынес общий код в `ValidationUtil.getErrorResponse()` и сделал обработку через `stream()`
> - Вместо контекста передаю в `makeEditable` опции `DataTable`. Переменную `ctx` теперь можно создавать не дожидаясь загрузки страницы.
> - Вынес создание `DataTable` в `topjava.common.js`. В параметр конфигурации добавляю общие опции используя [jQuery.extend()](https://api.jquery.com/jquery.extend/#jQuery-extend-deep-target-object1-objectN)

#### Apply 10_02_HW9_test.patch

#### Apply 10_03_HW9_datetimepicker.patch
> - Вынес форматирование даты в `topjava.common.formatDate()` 
> - Изменил формат ввода dateTime в форме без 'T': при биндинге значений к полям формы в `topjava.common.updateRow()` для поля `dateTime` вызываю `formatDate()`.  REST интерфейс по прежнему работает в стандарте ISO-8601
> - В новой версии `datetimepicker` работает ограничение выбора времени `startTime/endTime`
> - Добавил `autocomplete="off"` для выключения автоподстановки (у некоторых участников мешает вводу, у меня не воспроизводится)

- <a href="http://xdsoft.net/jqplugins/datetimepicker/">DateTimePicker jQuery plugin</a>
- <a href="https://github.com/xdan/datetimepicker/issues/216">Datetimepicker and ISO-8601</a>

## Занятие 10:

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2. [Дополнительно: кастомизация JSON (@JsonView) и валидации (groups)](https://drive.google.com/file/d/0B9Ye2auQ_NsFRTFsTjVHR2dXczA)
**ВНИМАНИЕ! Патчи `10_04_opt` и `10_05_opt` - не обязательные! Если будете делать- то в отдельной ветке (у меня `opt_view_group`). Это варианты решений, которые не идут в `master`.**
-  [Что возвращать: Entity или DTOs](https://stackoverflow.com/a/21569720/548473)

#### Apply 10_04_opt_json_view.patch
- [Jackson Annotation ](http://www.baeldung.com/jackson-annotations)
  - [Jackson JSON Views](http://www.baeldung.com/jackson-json-view-annotation)
- [@JsonView: фильтруем JSON](https://habrahabr.ru/post/307392/)
- [Jackson set default view](https://stackoverflow.com/questions/22875642/548473)

#### Apply 10_05_opt_validated_groups.patch
- [Validation Group in SpringMVC](https://narmo7.wordpress.com/2014/04/26/how-to-set-up-validation-group-in-springmvc/)
- [@Validated and Default group](http://web.archive.org/web/20170826153901/http://forum.spring.io/forum/spring-projects/web/117289-validated-s-given-groups-should-consider-default-group-or-not)

> **Починил тесты JDBC, добавив при проверке группы валидации.**

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 3. [Рефакторинг: jQuery конверторы и группы валидации по умолчанию](https://drive.google.com/file/d/1tOMOdmaP5OQ7iynwC77bdXSs-13Ommax)
**ВНИМАНИЕ! далее патчи идут после `10_03_HW9_datetimepicker` в ветке `master`**
#### Apply 10_04_jquery_converters.patch
- [jQuery: типы данных](https://jquery-docs.ru/jQuery.ajax/#data-types)
- [jQuery: конверторы](https://jquery-docs.ru/jQuery.ajax/#using-converters)

> В конвертере добавил дополнительную проверки `typeof json === 'object'` и `hasOwnProperty('dateTime')`, т.к. [при ексепшене не переопределяется `jqXHR.responseJSON`](https://stackoverflow.com/questions/48229776/548473)

#### Apply 10_05_persist_validate_group.patch
- [Default Hibernate validation](https://stackoverflow.com/a/16930663/548473). Т.к.  `Persist` наследуется от `javax.validation.groups.Default`, при сохранении учитываются все непомеченные аннотации валидации (`Default`) + помеченные `Persist`.

> ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png)
При `NotNull(groups = View.Persist.class)` валидация происходит непосредственно перед созданием или редактированием на уровне репозиториев? А если бы мы установили без группы то проверка была сразу после получения с UI?

Если `View` никаких нет (что равнозначно `javax.validation.groups.Default`), то бины, в которых есть аннотации валидации, проверяют Spring в контроллерах (если перед параметром стоит `@Valid`) и Hibernate перед сохранением и обновлением. Через `View` мы можем управлять валидацией. В нашем случае мы включили в `spring-db.xml` указание Hibernate валидировать поля с `View.Persist`. Так как `View.Persist` наследуется от `Default`, то Hibernate будет также валидировать и поля, на которых нет View.

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 4. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFTVZyQnBlYUtkNms">Spring Security Taglib. Method Security Expressions.</a>
#### Apply 10_06_secure_tag_annotation.patch
> - Сделал общий `bodyHeader.jsp` с разделением `isAnonymous/isAuthenticated`
> - В `login.jsp` кнопки входа отображаю только для `isAnonymous`       

-  <a href="https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#declaring-the-taglib">Spring Security Taglib.</a>
-  <a href="https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#jc-method">Method Security</a> и <a href="https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#method-security-expressions">Method Security Expressions</a>.

#### ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Вопрос:
> У нас в `sprng-mvc` подключается `spring-app`, в который подключается `spring-security`. Т.о. `spring-mvc`  в себе содержит код `spring-app` и `spring-security`. Почему тогда аннотация `<security:global-method-security...` из `spring-security` не видна для контроллера из `spring-mvc`?

1. Подключаем один контекст внутрь другого: это про import а не про наследование (тоже самое что скопипастить сюда xml из подключаемого import файла). Наследование означает, что поиск бина, если он не найден, происходит в родителе.
2. При создании контекста аннотация `<security:global-method-security...` говорит про то, что все бины текущего контекста, у которых есть аннотации авторизации (`@PreAuthorize/ @Secured/ ..`) будут проксироваться с проверкой авторизации. Контексты родителей и детей создаются сами по себе и аннотации проксирования у них собственные.

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 5.  <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFc1JMTE4xVG0zN0U">Interceptors. Редактирование профиля. JSP tag files.</a>
#### Apply 10_07_interceptor.patch
-  <a href="http://www.mkyong.com/spring-mvc/spring-mvc-handler-interceptors-example/">Spring interceptors</a>.

#### Apply 10_08_profile_jsptag.patch
**Глюк Хрома - у меня поле `email` у User показывается неверно (как для admin). В другом браузере, анонимном окне и коде страницы (Ctrl+U) все ок. Я решил локально обновлением Хрома. Еще решения:**
 - [В своем браузере](https://www.howtogeek.com/425270/how-to-disable-form-autofill-in-google-chrome)
 - [Chrome ignores autocomplete=“off”](https://stackoverflow.com/questions/12374442/548473)
 - [Disabling Chrome Autofill](https://stackoverflow.com/questions/15738259/548473)

> - Создал отдельный `ProfileUIController` для операций с профилем (вместо `RootController`)
> - Упростил: в `inputField.tag`: передаю для label код локализации и убрал ветвление
> - В профиль добавил кнопку "Cancel"

- [Bootstrap 4 form validation example](http://nicesnippets.com/snippet/bootstrap-4-form-validation-with-form-all-input-example)
- <a href="http://www.techrepublic.com/article/an-introduction-to-jsp-20s-tag-files/">Делаем jsp tag для ввода поля формы</a>.
- <a href="http://www.codejava.net/frameworks/spring/spring-mvc-form-validation-example-with-bean-validation-api">Spring MVC Form Validation</a>
- <a href="http://www.mkyong.com/spring-mvc/spring-mvc-form-check-if-a-field-has-an-error/">Spring MVC Form: check if a field has an error</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 6. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFNWpUNktMeGJURmM">Форма регистрации.</a>
#### Apply 10_09_registration.patch

> - Добавил локализацию
> - При регистрации передаю `username` в `login.jsp` как параметр и делаю `setCredentials` (пароль вводится скрыто, поэтому его не передаю из соображений безопасности). Т.к `setCredentials` требует jQuery, убрал для него `defer`
> - Поменял путь регистрации с `/registered` на `/profile/registered` (в `ProfileUIController` вместо `RootController`)
> - Добавил регистрацию пользователя по REST: `ProfileRestController.register`, тест и пример `curl`. Обратите внимание на хедер `Location`, он отличается от `AdminRestController.createWithLocation`

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 7. <a href="https://drive.google.com/file/d/0B9Ye2auQ_NsFZ19lU29VVDRfNXM">Обработка исключений в Spring.</a>
                                                                                                                                
#### Apply 10_10_not_found_422.patch
> [Поменял код 404 (URL not found) на 422 (Unprocessable Entity)](http://stackoverflow.com/a/22358422/548473)

-  <a href="http://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc#using-http-status-codes">Используем HTTP status code</a>

#### Apply 10_11_global_exception.patch
> - Перед  отображением exception предварительно делаю `ValidationUtil.getRootCause`
> - Добавил локализацию
> - Добавил общий статус `500` в ответ и отображение (на следующем уроке будем его менять, в зависимости от типа ошибки) 

-  <a href="http://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc#global-exception-handling">Global Exception Handling</a>

#### Apply 10_12_controller_advice_exception.patch
**Отображение валидации для формы еды и юзера пропало (`TODO` в коде для HW10). Можно посмотреть сериализацию в `ErrorInfo` при попытке добавить юзера с дублирующимся email.**

> - Добавил в `ErrorInfo` тип ошибки `ErrorType`
> - Добавил обработку неверного запроса (`IllegalRequestDataException`)
> - `@ResponseBody` над методами `ExceptionInfoHandler` заменил на `@RestControllerAdvice`
> - В `ExceptionInfoHandler` удалил `@Order` над методами и добавил над классом:
```
  Methods are matched by closest exception in
  *  @see  org.springframework.web.method.annotation.ExceptionHandlerMethodResolver#getMappedMethod
  *  164: Collections.sort(matches, new ExceptionDepthComparator(exceptionType))
```
> - Добавил в `curl.md` пример с возвращением `ErrorInfo`. Локализация ошибок будет на последнем занятии

- <a href="http://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc#errors-and-rest">Сериализация Exception в JSON</a>
- <a href="http://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc#controller-based-exception-handling">Exception Handling на уровне контроллера</a>
- <a href="https://www.javacodegeeks.com/2013/11/controlleradvice-improvements-in-spring-4.html">@ControllerAdvice improvements in Spring 4</a>
- <a href="https://dzone.com/articles/spring-31-valid-requestbody">@Valid @RequestBody + Error handling</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 8. <a href="https://drive.google.com/file/d/1XZXvOThinzPw4EhigAUdo8-MWT_g8wOt">Encoding password. Json READ/WRITE access</a>
#### Apply 10_13_password_encoding.patch
> - Добавил утильный метод `UserService.prepareAndSave`
> - Для InMemory тестов добавил `passwordEncoder` в  `inmemory.xml`

- <a href="https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#core-services-password-encoding">Password Encoding</a>

#### Apply 10_14_read_write_access.patch
> - Добавил методы сериализации в json `JsonUtil.writeAdditionProps` с дополнительными полями и `UserTestData.jsonWithPassword` для того, 
чтобы передавать в контроллер пользователя с паролем (который теперь не сериализуется)   

### В реальном приложении для управления паролем необходим отдельный UI интерфейс с подтверждением старого пароля - одна из ваших доработок проекта по окончанию стажировки

- [@JsonProperty READ_ONLY / WRITE_ONLY](https://stackoverflow.com/a/12505165/548473)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 9. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFNDlPZGdUNThzNUU">Межсайтовая подделка запроса (CSRF).</a>
#### Apply 10_15_csrf.patch
> Убрал `form:form` из ajax запросов: там csrf работает через header. Проверьте во вкладке браузера `Network`.

**Поломалась UTF-8 кодировка в редактировании профиля и регистрациию (если по умолчанию не UTF-8). В Optional HW10 нужно будет починить.**
 
-  <a class="anchor" id="csrf"></a><a href="https://ru.wikipedia.org/wiki/Межсайтовая_подделка_запроса">Межсайтовая подделка запроса (CSRF)</a>
-  <a href="https://docs.spring.io/spring-security/site/docs/current/reference/html/features.html#csrf">Using Spring Security CSRF Protection</a>
-  <a href="https://docs.spring.io/spring-security/site/docs/current/reference/html/features.html#csrf-when-json">Ajax and JSON Requests</a>
-  <a href="http://blog.jdriven.com/2014/10/stateless-spring-security-part-1-stateless-csrf-protection/">Stateless CSRF protection</a>
-  Ресурсы:
    -  <a href="http://habrahabr.ru/post/264641/">Spring Security 4 + CSRF</a>
    -  <a href="http://stackoverflow.com/questions/11008469/are-json-web-services-vulnerable-to-csrf-attacks">Are JSON web services vulnerable to CSRF attacks</a>
    -  <a href="https://ru.wikipedia.org/wiki/Правило_ограничения_домена">Правило ограничения домена (SOP)</a>
    -  <a href="https://ru.wikipedia.org/wiki/Cross-origin_resource_sharing">Cross-origin resource sharing (CORS)</a>

## ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Ваши вопросы

> В чем отличие между аннотоацией `@PreAuthorize("hasRole('ADMIN')")` и конфигурацией в jsp: `<sec:authorize access="isAuthenticated()">`, `<sec:authorize access="hasRole('ADMIN')">` ?

Анотация `@PreAuthorize` обрабатывается Spring анологично `@Transactional`, `@Cacheable` - класс проксируется и до-после вызова метода добавляется функциональность.
В данном случае перед вызовом метода проверяются роль залогиненного юзера. JSTL тэг `authorize` выполняет проверку условия в залогиненном юзере внутри jsp.

> Еще раз: почему не нужен csrf для REST и нельзя подделать JSON запрос с вредоносного сайта?

Попробуйте выполнить ajax запрос из вашего приложения c url, у которого домен отличный от вашего (например 'http://topjava.herokuapp.com/meals/ajax/admin/users/{id}').
В консоли браузера будет `XMLHttpRequest cannot load`... - <a href="https://developer.chrome.com/extensions/xhr">нарушение same origin policy</a>. 
Формам же разрешается делать submit (через `action=..`) на другой домен, но невозможно cделать `Content-Type`, отличный от <a href="http://htmlbook.ru/html/form/enctype">стндартных enctype</a> и методов <a href="http://htmlbook.ru/html/form/method">кроме get и post</a>. Таким образом `consumes = MediaType.APPLICATION_JSON_VALUE` в POST защищает приложение от CSRF.

> Почему использован `BCryptPasswordEncoder`а не `hash(password+salt)`?

[`BCryptPasswordEncoder` automatically generates a salt and concatenates it with the hash value in a single String](http://stackoverflow.com/a/8528804/548473).

> Когда запускается в `GlobalExceptionHandler` метод `defaultErrorHandler`? Когда как в него исключение попадает? Как выбирается, кто обрабатывает исключения: `ExceptionInfoHandler` или `GlobalExceptionHandler`?

 `GlobalExceptionHandler` попадает в контекст спринг (через `@ControllerAdvice` его находят в пакете `web`). Далее спринг перехватывает исключения и отправляет в подходящий по исключению метод `GlobalExceptionHandler`. `ExceptionInfoHandler` помечен `@RestControllerAdvice(annotations = RestController.class)`, он обрабатывает только ошибки из всех контроллеров с аннотацией `RestController`.
 
 > Откуда берутся в валидации сообщения на русском "должно быть между 10 и 10000"?
 
 Локализация встроена в Hibernate Validation. Смотрите `Ctrl+Shift+N` и `ValidationMessages_ru.properties`.

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Домашнее задание HW10
- 1: Сделать валидацию в `AdminUIController/MealUIController` через `ExceptionInfoHandler`. Вернуть клиенту `ErrorInfo` и статус `HttpStatus.UNPROCESSABLE_ENTITY` (тип методов контроллеров сделать `void`). Ошибки валидации отобразить на клиенте красиво (так, как это сделано в [demo](http://topjava.herokuapp.com), без локализации полей)
- 2: Сделать валидацию принимаемых json объектов в REST контроллерах через `ExceptionInfoHandler`. Добавить в Rest контроллеры тест для невалидных данных.
  - <a href="https://dzone.com/articles/spring-31-valid-requestbody">@Valid @RequestBody + Error handling</a>
- 3: Сделать обработку ошибки при дублирования email (вывод сообщения "User with this email already exists") для: 
  - 3.1 регистрации / редактирования профиля пользователя
  - 3.2 добавления / редактирования пользователя в таблице
  - 3.3 REST контроллеров  
  Варианты выполнения:
    - через `catch DataIntegrityViolationException`
    - обработку ошибок в  `@ExceptionHandler`(https://stackoverflow.com/a/42422568/548473)
    - более сложная реализация - [собственный валидатор](https://coderlessons.com/articles/java/spring-mvc-validator-i-initbinder)  
  - Опционально - [сделать локализацию выводимой ошибки](https://www.logicbig.com/tutorials/spring-framework/spring-core/message-sources.html)

### Optional
- 4: Сделать обработку ошибки при дублирования dateTime еды. Сделать тесты на дублирование email и dateTime.
  - [Тесты на DB exception c @Transactional](http://stackoverflow.com/questions/37406714/548473)
  - [Сheck String in response body with mockMvc](https://stackoverflow.com/questions/18336277/548473)
- 5: Сделать в приложении выбор локали (см. http://topjava.herokuapp.com/)
  - [Internationalization](https://terasolunaorg.github.io/guideline/5.0.x/en/ArchitectureInDetail/Internationalization.html)
  -  <a href="http://www.mkyong.com/spring-mvc/spring-mvc-internationalization-example">Spring MVC internationalization sample</a>
  -  <a href="https://www.concretepage.com/spring-4/spring-mvc-internationalization-localization">Spring 4 MVC Internationalization</a>
- 6: Починить UTF-8 в редактировании профиля и регистрации (если кодировка по умолчанию у вас не UTF-8). Подумайте, почему кодировка поломалась.
  
-------

## ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Типичные ошибки и подсказки по реализации
- 1: `ErrorInfo` просто бин для передачи информации на клиента. Кода возврата и ответ настраиваются в `ExceptionInfoHandler`.
- 2: Не дублируйте обработку ошибок `BindingResult`: `result.getFieldErrors()..` 
- 3: Можно не создавать собственные эксепшены, а в `ExceptionInfoHandler` ловить стандартные 
- 4: в `MethodArgumentNotValidException` также есть `e.getBindingResult()`, его можно обрабатывать по аналогии с `BindException`
- 5: Не дублируйте код переключения локали на странице логина и в приложении
- 6: При проблемах с валидацией `Meals` в `MealRestController`, посмотрите на валидацию в `MealUIController.updateOrCreate`
- 7: Импорт класса `java.net.BindException` вместо нужного `javax.validation.BindException`
