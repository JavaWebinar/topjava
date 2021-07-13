# Стажировка <a href="https://github.com/JavaWebinar/topjava">Topjava</a>

## <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFfkpMd2UyWjBsc2JsSE4tRDFkU3BvMktFQkhUN1J6VExxSUUzOHlSR0RhNm8">Материалы занятия</a>

- **Браузер кэширует javascript и css. Если изменения не работают, обновите приложение в браузере (в хроме `Ctrl+F5`)**
- **При удалении файлов не забывайте делать clean: `mvn clean`**

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Разбор домашнего задания HW7

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. [HW7](https://drive.google.com/file/d/1h6wg2V9yZoNX7fA7mNA7w7Kxp8IACsIJ)

#### Apply 8_01_HW07_controller_test.patch

- [Persistent classes implementing equals and hashcode](https://docs.jboss.org/hibernate/orm/4.3/manual/en-US/html_single/#persistent-classes-equalshashcode): переопределять `equals()/hashCode()`
  необходимо, если
    - использовать Entity в `Set` (рекомендовано для many ассоциаций), либо как ключи в `HashMap`
    - использовать _reattachment of detached instances_ (те манипулировать одним Entity в нескольких транзакциях/сессиях).
- Оптимально использовать уникальные неизменяемые бизнес поля, но обычно таких нет, и, чаще всего, используются PK с ограничением, что он может быть `null` у новых объектов и нельзя объекты сравнивать
  через `equals` в бизнес-логике (например тестах).
- [Equals() and hashcode() when using JPA and Hibernate](https://stackoverflow.com/questions/1638723)

------------------------

#### Apply 8_02_HW07_rest_controller.patch

- В `MealTo` вместо изменяемых полей и конструктора без параметров сделал [`@ConstructorProperties`](https://www.logicbig.com/tutorials/misc/jackson/constructor-properties.html). `Immutable` классы
  всегда предпочтительнее для данных.
- [Паттерн стратегия](https://refactoring.guru/ru/design-patterns/strategy).

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFLXZ3OHdac18yZlk">HW7 Optional</a>

#### Apply 8_03_HW07_formatters.patch

> - Перенес форматтеры в подпакет `web`, т.к. они используются Spring MVC
> - Заменил `@RequestParam(required = false)` на `@RequestParam @Nullable`

#### Apply 8_04_HW07_soapui_curl.patch

> Добавил примеры запросов curl в `config/curl.md`

- <a href="http://rus-linux.net/lib.php?name=/MyLDP/internet/curlrus.html">Написание HTTP-запросов с помощью Curl</a> (для Windows можно использовать Git Bash)
- В IDEA появился отличный инструмент тестирования запросов. Для конвертации
  в [`Tools->HTTP Client->Test RESTful Web Service`](https://www.jetbrains.com/help/idea/http-client-in-product-code-editor.html) скопируйте curl без флага `-s`

###  ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 3. [HW7 Optional: getWithMeals + тесты](https://drive.google.com/file/d/13cjenXzWDr52HTTzleomOd-yjPAEAbOA)
#### Apply 8_05_HW07_with_meals.patch
#### Apply 8_06_HW07_test_with_meals.patch
> Изменения в AssertJ: `ignoringAllOverriddenEquals` для рекурсивных сравнений не нужен. См. [overridden equals used before 3.17.0](https://assertj.github.io/doc/#assertj-core-recursive-comparison-ignoring-equals)

## Занятие 8:

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 4.  <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFUmVsM3V6djMzYmc">WebJars. jQuery and JavaScript frameworks</a>

#### Apply 8_07_webjars.patch

> - Обновил jQuery до 3.x, Bootstrap до 4.x
>   - <a href="https://tproger.ru/translations/new-features-of-jquery-3/">Новое в jQuery 3</a>
> - УБРАЛ из проекта <a href="http://dandelion.github.io">Dandelion обертку к datatables</a>: 
>   - не встречал нигде, кроме Spring Pet Clinic;
>   - поддержка работы с datatables через Dandelion оказалось гораздо более трудоемкое, чем работа с плагином напрямую.
> - Исключил ненужные зависимости

- Подключение веб ресурсов. <a href="http://www.webjars.org/">WebJars</a>.
- <a href="http://www.jamesward.com/2012/04/25/introducing-webjars-web-libraries-as-managed-dependencies">Introducing WebJars</a>
- <a href="https://ru.wikipedia.org/wiki/Document_Object_Model">Document Object Model (DOM)</a>
- <a href="https://css-tricks.com/dom/">What is the DOM?</a>
- <a href="https://ru.wikipedia.org/wiki/JQuery">jQuery</a>
- <a href="http://stackoverflow.com/questions/7062775/is-jquery-a-javascript-library-or-framework">Is jquery a javascript library or framework</a>
- <a href="https://www.datatables.net/">DataTables</a>
- <a href="http://www.sitepoint.com/working-jquery-datatables/">Working with jQuery DataTables</a>

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 5. [Bootstrap](https://drive.google.com/file/d/1RHtzw8OQt6guCu6xe3apT7F9EfiX96tr)

#### Apply 8_08_bootstrap4.patch

> - [WIKI Bootstrap](https://ru.wikipedia.org/wiki/Bootstrap_(фреймворк))
> - Добавил <a href="https://www.w3schools.com/icons/fontawesome_icons_intro.asp">Font Awesome</a>
>   - [Map glyphicon icons to font-awesome](https://gist.github.com/blowsie/15f8fe303383e361958bd53ecb7294f9)
> - В `headTag.jsp` в ссылку на `style.css` добавил `?v=2`. Стили изменились, изменяя версию в параметре мы заставляем браузер не брать их из кэша, а загружать заново. 

- [Bootstrap](https://getbootstrap.com/)
    - [Bootstrap Examples](https://getbootstrap.com/docs/4.1/examples/)
    - [Navbar](https://getbootstrap.com/docs/4.1/components/navbar/)
    - [Spacing](https://getbootstrap.com/docs/4.1/utilities/spacing/)
    - [Forms](https://getbootstrap.com/docs/4.1/components/forms/)
    - [Sticky footer](https://getbootstrap.com/docs/4.1/examples/sticky-footer/)
- [Документация Bootstrap на русском](https://bootstrap-4.ru/)
- Дополнительно
    - <a href="http://www.tutorialrepublic.com/twitter-bootstrap-tutorial/">Twitter Bootstrap Tutorial</a>
    - <a href="https://www.youtube.com/playlist?list=PLVfMKQXDAhGUxJ4prQSC2K13-YlYj8LgB">Видео уроки Bootstrap 4</a>
    - [Bootstrap верстка современного сайта за 45 минут](https://www.youtube.com/watch?v=46q2eB7xvXA)

## ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Ваши вопросы

> А где реально этот путь "classpath:/META-INF/resources/webjars"?

Внутри подключаемых webjars ресурсы лежат по пути `/META-INF/resources/webjars/...` Не поленитесь посмотреть на них через `Ctrl+Shift+N`. Все подключаемые jar попадают в classpath и ресурсы доступны
по этому пути.

> У меня webjars зависимость лежит внутри ".m2\repository\org\webjars\". С чем это может быть связано?

Maven скачивает все депенденси в local repository, который по умолчанию находится в `~/.m2`. Каталог по умолчанию можно переопределить в `APACHE-MAVEN-HOME\conf\settings.xml`,
элемент `localRepository`.

> WEBJARS лежат вообще в другом месте WEB-INF\lib*. Биндим mapping="/webjars/*" на реальное положение jar в ware, откуда spring знает где искать наш jquery ?

В war в `WEB-INF/lib/*` лежат все jar, которые попадают к classpath. Spring при обращении по url `/webjars/` ищет по пути
биндинга `<mvc:resources mapping="/webjars/ " location="classpath:/META-INF/resources/webjars/"/>`
по всему classpath (то же самое как распаковать все jar в один каталог) в `META-INF/resources/webjars/`. В этом месте во всех jar, которые мы подключили из webjars лежат наши ресурсы.

> Оптимально ли делать доступ к статическим ресурсам (css, js, html) через webjars ?

На продакшене под нагрузкой статические ресурсы лучше всего держать не в war, а снаружи. Доступ к ним делается либо
через <a href="https://www.techsupper.com/2017/05/serve-static-resources-from-external-folder-outside-webapps-tomcat.html">конфигурирование Tomcat</a>.  
Но чаще всего для доступа к статике ставят прокси, например <a href="https://nginx.org/ru/">Nginx</a>

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 6. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFcGs4b1IyWWF2S2c">AJAX. Datatables. jQuery</a>

JSP полезны, если надо с сервера отдать статический html с серверной логикой (условия, циклы), сформированный на основе модели. Для динамической отрисовки таблицы мы будем использовать REST и JSON на
9м уроке (работа с datatables через Ajax).

По дебагу JavaScript из IDEA проверьте:

- в IDEA плагин `JavaScript Debugger`
- [Chrome extension is not required for debugging since 2017.3](https://intellij-support.jetbrains.com/hc/en-us/community/posts/360010507240-where-is-JETBRAINS-IDE-SUPPORT-chrome-extension-it-cant-be-found-anywhere-now-on-the-internet)
- при проблемах с портами [удалите в настройках IDEA файлы `~\AppData\Roaming\JetBrains\IntelliJIdea2020.x\options\web-browsers.xml` и `other.xml`](https://intellij-support.jetbrains.com/hc/en-us/community/posts/360009567459-Webstorm-2020-2-1-Remote-Debugging-do-not-work)

#### Apply 8_09_ajax_datatables.patch

> - Переименовал js скрипты по [javascript filename naming convention](https://stackoverflow.com/questions/7273316/what-is-the-javascript-filename-naming-convention)
> - `reset()` не чистит скрытые (hidden) поля формы. Сделал очистку полей через `form.find(":input").val("")`
> - Поменял форматирование модального окна: [Botstrap4 Modal](https://getbootstrap.com/docs/4.1/components/modal/)
> - URL с  `/ajax/admin/users` поменял на `/admin/users`

JavaScript

- <a href="https://ru.wikipedia.org/wiki/AJAX">AJAX</a>
- <a href="https://learn.javascript.ru/introduction-browser-events">Введение в браузерные события</a>
- [Скрипты: async, defer](https://learn.javascript.ru/script-async-defer)
- [With defer, in the head](https://flaviocopes.com/javascript-async-defer/#with-defer-in-the-head)
- <a href="http://stackoverflow.com/questions/436411/where-should-i-put-script-tags-in-html-markup/24070373#24070373">JavaScript loading modern approach</a>

jQuery

- <a href="http://ruseller.com/jquery.php?id=124">Событие $(document).ready</a>.
- <a href="http://anton.shevchuk.name/jquery/">jQuery для всех</a>.
- <a href="http://anton.shevchuk.name/javascript/jquery-for-beginners-ajax/">jQuery для начинающих. AJAX</a>.
- <a href="http://anton.shevchuk.name/javascript/jquery-for-beginners-selectors/">jQuery для начинающих. Селекторы</a>.
- [jQuery task from freecodecamp](https://www.freecodecamp.org/map-aside#nested-collapsejQuery)
- <a href="http://api.jquery.com/">jQuery API</a>

DataTables/Bootstrap

- <a href="http://datatables.net/reference/api/">DataTables API</a>
- <a href="http://bootstrap-ru.com/203/javascript.php">Javascript плагины для Bootstrap</a>
- <a href="https://datatables.net/examples/styling/bootstrap4">DataTables/Bootstrap 4 integration</a>
- <a href="https://datatables.net/upgrade/1.10-convert">Converting parameter names for 1.10</a>
- <a href="http://stackoverflow.com/questions/25207147/datatable-vs-datatable-why-is-there-a-difference-and-how-do-i-make-them-w">dataTable() vs. DataTable()</a>

## ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Ваши вопросы

> Что делает код?

```
$('.delete').click(function () {
        deleteRow($(this).attr("id"));
    });
```

На все элементы DOM с классом `delete` вешается обработчик события `click` который вызывает функцию `deleteRow`. Классы в html разделяются через пробел.
См. [селекторы в jQuery](http://anton.shevchuk.name/javascript/jquery-for-beginners-selectors/)

> Как в таблицу `<table id="datatable">` из JSP вставляются дополнительные `div`, поле для поиска, стрелочки для сортировки и т.д. (видны в браузере через `Inspect (Ctrl+Shift+I)` в хроме) ?

JSP отдает html на клиента, в браузере исполняется скрипт `$("#datatable").DataTable(..)`, который модифицирует элемент таблицы и вставляет туда (в элементы DOM html документа) все табличные плюшки.

> Как выполняется сценарий по запросу `http://localhost:8080/topjava/users` ?

**Обязательно смотрите в браузере вкладку Networks**

1. `RootController` принимает GET запрос, достает из репозитория юзеров и отдает их `users.jsp` для формирования таблицы. На следующем занятии мы поменяем сценарий - данные для таблицы для
   первоначальной отрисовки будут доставаться по ajax.
2. Сформированный `users.jsp` отдается в браузер клиента.
3. Страница и js скрипты, подключенные в `users.jsp`, загружаются в браузер.
4. После загрузки страницы по событию `onload` вызывается `$(function ()` из `topjava.users.js`. В ней компонент `#datatable` делается табличкой `DataTable` и вызывается `makeEditable()`
   из `topjava.common.js`.
5. В `makeEditable()` вешается `click` на элементы с классом `.delete` и глобально настраиваются вызовы `ajax`

Все. Приложение ждет дальнейших запросов от клиента.

> тянет ли Bootstrap за собой jQuery?

Bootstrap css это стили (форматирование), Bootstrap js зависит от jQuery: http://stackoverflow.com/questions/14608681/can-i-use-twitter-bootstrap-without-jquery#answer-14608772

#### Apply 8_10_refactor_js.patch
>  - Сделал [объект `ctx` (контекст) для глобальных переменных](https://stackoverflow.com/a/5064235/548473)
>  - `datatableApi` присваиваю в функции `makeEditable`, куда ее передаю параметром  
>  - Вынес переменную `form = $('#detailsForm')` (инициализирую только 1-н раз)
>  - При удалении строки добавил [подтверждение confirm](https://stackoverflow.com/questions/10462839/how-to-display-a-confirmation-dialog-when-clicking-an-a-link)
>  - [В `jquery.ajax` заменил depricated `success` на `done()`](http://api.jquery.com/jquery.ajax/#jqXHR)
>  - В HTML атрибут id у каждого элемента документа должен быть уникален, а у нас в `users.jsp` дублировались `id`. Переместил атрибут `id` в тэг `<tr>` и в `makeEditable` достаю его через селектор jQuery `closest('tr')`.
 
## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 7. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFMTVWaXdWRUZsUEE"> Notifications</a>

#### Apply 8_11_notification.patch

> - Сделал [защиту от кэширование ajax запросов в IE](https://stackoverflow.com/a/4303862/548473)
> - Обновил API Noty (3.x), добавил в сообщения font-awesome
> - [Tomcat 8.5.x перестал отдавать в заголовке `statusText`](http://tomcat.apache.org/tomcat-8.5-doc/changelog.html). Отображаем просто `status`
>   - RFC 7230 states that clients should ignore reason phrases in HTTP/1.1 response messages. Since the reason phrase is optional, Tomcat no longer sends it (statusText).

- <a href="http://ruseller.com/jquery.php?id=2">Обработка ajaxError</a>.
- <a href="http://ned.im/noty/">jQuery notification</a>

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 8. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFRVkzcFMwc0hrYmM">Добавление Spring Security</a>

> - Правка к видео: путь в intercept-url должен быть полный: `pattern="/rest/admin/**"`
> - В Spring Security 4.x по умолчанию включен csrf (защита от <a href="https://ru.wikipedia.org/wiki/Межсайтовая_подделка_запроса">межсайтовой подделки запроса</a>). Выключил, включим на 10-м занятии.
> - В Spring Security 5.x по умолчанию пароль кодируется. Выключил, включим на 10-м занятии.

- [Adding a Password Encoder](https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#ns-password-encoder)

#### Apply 8_12_add_security.patch

- <a href="http://projects.spring.io/spring-security/">Spring Security</a>
- <a href="https://ru.wikipedia.org/wiki/Протокол_AAA">Протокол AAA</a>
- <a href="https://ru.wikipedia.org/wiki/Аутентификация_в_Интернете">Методы аутентификации</a>.
- <a href="https://en.wikipedia.org/wiki/Basic_access_authentication">Basic access authentication</a>
- <a href="http://articles.javatalks.ru/articles/17">Использование ThreadLocal</a>
- <a href="http://www.baeldung.com/security-spring">Security with Spring</a>
- [Decode/Encode Base64 online](http://decodebase64.com/)

Вместо

`curl -v -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=' http://localhost:8080/topjava/rest/profile/meals`

лучше использовать эквивалентный

`curl -v --user user@yandex.ru:password http://localhost:8080/topjava/rest/profile/meals`

## ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Ваши вопросы

> Как можно в браузере сбросить введенный пароль базовой авторизации?

Проще всего делать новый запрос в новой анонимной вкладке (`Ctrl+Shift+N` в Chrome)

> Как по REST определяется залогиненный юзер? Аутентификация происходит при каждом запросе?

<a href="http://stackoverflow.com/questions/319530/restful-authentication">Способы RESTful Authentication</a>. Мы будем использовать 2:
- Basic Authentication для REST контроллеров с аутентификацией при каждом запросе
- coockie + http session для UI контроллеров на следующем уроке

> Почему при выполнении тестов `AdminRestControllerTest` не задействуется Spring Security?
 
Для этого в `MockMvc` надо явно добавлять security filter. Будем делать на следующем уроке.

> Почему `@RequestParam` не работает в PUT и DELETE запросах?

По спецификации Servlet API параметры в теле для PUT, DELETE, TRACE методах не обрабатываются (только в url). Те. можно:
- использовать POST
- передавать параметры в url
- использовать `HttpPutFormContentFilter` фильтр
- настроить Tomcat в обход спецификации.
См. <a href="http://stackoverflow.com/a/14568899/548473">Handle request parameters for an HTTP PUT method</a>

> Данные между браузером и ajax гоняются в виде json? Почему в `AdminAjaxController`  у методов delete и createOrUpdate нет в аннотациях параметра `consumes = MediaType.APPLICATION_JSON_VALUE` ?

Посмотреть на данные между приложением и браузером можно (и нужно!) в браузере (вкладка Network в Инструментах разработчика, F12 в Хроме). Зависит от того, как их отправляем из браузера и из
приложения. Данные формы обычно передаются просто параметрами. `APPLICATION_JSON_VALUE` в контроллере нужно, только если параметры отдаются/принимаются в формате JSON.

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Домашнее задание HW08

- 1: Перевести `meals` на `datatables` (изменить `meals.jsp`, вместо `JspMealController` будет `MealUIController`).
    - 1.1 Реализовать добавление записи еды через модальное окно Bootstrap и удаление еды по ajax (БЕЗ редактирования).
    - 1.2 При вставке данных по AJAX пропадает все JSP форматирование, чинить перерисовку НЕ надо. Следующий урок- будем делать datatable по AJAX и форматирование на стороне клиента.

### Optional.

- 2: Перевести работу фильтра на AJAX. Попробуйте после модификации таблицы (например добавлении записи) обновлять ее также с учетом фильтра.
    - [Руководство по выбору между GET и POST](https://handynotes.ru/2009/08/get-versus-pos.html)
- 3: Сделать кнопку сброса фильтра.
- 4: Реализовать enable/disable юзера
    - 4.1 Через checkbox в `users.jsp` с сохранением в DB. Неактивных пользователей выделить css стилем. Проверьте, как у вас первоначально (или по F5) отображаются неактивные пользователи (если
      меняете css при enable/disable)
    - 4.2 Добавить метод `enable` в `AdminRestController` и протестировать его в `AdminRestControllerTest` и в сервисах

---------------------

## ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Подсказки по HW08

- 1: enable/disable можно реализовать как на уровне репозитория, так и на уровне сервиса через несколько sql, которые должны быть в одной транзакции (`@Transactional`)
- 2: в `topjava.common.js` следует вынести только общие скрипты (cкрипты еды размещайте в  `topjava.meals.js`, пользователей в `topjava.users.js`)
- 3: если в контроллер приходит `null`, проверте в `Network` вкладке браузера в каком формате приходят данные и в каком формате в контроллере вы их принимаете (`consumes`)
- 4: при реализации `enable/disable` лучше явно указывать нужное состояние, чем переключать на противоположное. Если параллельно вам кто-то изменит состояние, то будет несоответствие UI и DB
