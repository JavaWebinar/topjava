# Стажировка <a href="https://github.com/JavaWebinar/topjava">Topjava</a>

## <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFSzlObk8tbHdtcXc">Материалы занятия</a>

### Все материалы проекта (в том числе и будущие обновления) останутся доступны без органичения по времени в [Google Drive](https://drive.google.com/drive/u/0/folders/0B9Ye2auQ_NsFflp6ZHBLSFI2OGVEZ2NQU0pzZkx4SnFmOWlzX0lzcDFjSi1SRk5OdzBYYkU)

### ![correction](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Правки в проекте

#### Apply 11_0_fix.patch
> - Обнулил отображение хэша пароля в профиле
> - В `login.jsp` код приходит параметром. Если такого не существует, вместо эксепшена выводим по умолчанию "". 

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Разбор домашнего задания HW10

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFX2V5eHRsa09IWHc">HW10</a>
#### Apply 11_01_HW10_fix_encoding.patch
> - Выставлять кодировку `-Dfile.encoding=UTF-8` лучше в _Help menu -> Edit Custom VM Options_
> - Советы по дополнительным настройкам: 
>   - [Customize IntelliJ IDEA Memory](http://tomaszdziurko.com/2015/11/1-and-the-only-one-to-customize-intellij-idea-memory-settings)
>   - [Increase IDE memory limit in IntelliJ IDEA](https://stackoverflow.com/questions/13578062/how-to-increase-ide-memory-limit-in-intellij-idea-on-mac)
>   - [Slow startup time on Windows](https://youtrack.jetbrains.com/issue/IDEA-211178#focus=streamItem-27-3412218.0-0)

#### Apply 11_02_HW10_validation.patch
> - В [соответствии со спицификацией](http://stackoverflow.com/a/22358422/548473) для поменял `HTTP 400` (ошибка в структуре сообщения) на `HTTP 422` (ошибка в содержании)
> - Сделал тесты и проверку типа ошибки [через jsonPath](https://www.petrikainulainen.net/programming/spring-framework/integration-testing-of-spring-mvc-applications-write-clean-assertions-with-jsonpath/)

#### Apply 11_03_HW10_duplicate_email.patch
> - сделал код(ключ) i18n константой (`EXCEPTION_DUPLICATE_EMAIL`)
> - в `GlobalExceptionHandler` добавил логирование и общий код вынес в `ValidationUtil`

#### Apply 11_04_HW10_duplicate_datetime.patch
> - Реализовать обработку дублирования `user.email` и `meal.dateTime` можно по разному
>   - через [поиск в сообщении `DataIntegrityViolationException` имени DB constrains](https://stackoverflow.com/a/42422568/548473)
>   - через [Controller Based Exception Handling](https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc#controller-based-exception-handling)

Первый самый простой и расширяемый (хотя зависить от базы), выбрал его. Для работы с HSQLDB сделал `toLowerCase`.

> - Для работы с i18n использую `MessageSourceAccessor`.
>   - [get error text from BindingResult](https://stackoverflow.com/questions/2751603/548473)
> - Добавил тесты на дублирование. Отключил транзакционность в тестах на дублирование через `@Transactional(propagation = Propagation.NEVER)`.
>   - [Решение проблемы с транзакционными тестами](https://stackoverflow.com/a/46415060/548473)

#### Apply 11_05_HW10_binder_validation.patch
> - Добавил корректный способ проверки email своим валидатором: проверка делается в контроллерах, а не при сохранении.
>   - [Spring MVC: валидатор и @InitBinder](https://coderlessons.com/articles/java/spring-mvc-validator-i-initbinder)

> Проблема: при REST update нам могут приходить `User/UserTo` без `id` (он корректируется уже после валидации через `ValidationUtil.assureIdConsistent`)
> Для UI мы в профиль добавили скрытый `id`, для REST проблема осталась.
> Для проверки сообщений в тестах в `ExceptionInfoHandler#bindValidationError` сделал `messageSourceAccessor::getMessage` и из сообщений ошибок ушли поля. Починим в патче `11_08_i18n`

#### Apply 11_06_HW10_manual_binder_validation.patch
- Для этих случаев я сделал тесты - можете проверить, что без правок они не пройдут.
- Правки заключаются в том, что я для REST update случаев убрал `@Valid` и делаю валидацию вручную в `AbstractUserController.validateBeforeUpdate`.
- При этом добавил в spring конфигурацию [стандартный валидатор по умолчанию](https://stackoverflow.com/a/23615478/548473)
- Теперь я могу выбирать, делать проверки автоматически (через `@Valid`) или вручную

###  ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFYms4YUxEMHdxZHM">HW10 Optional: change locale</a>
#### Apply 11_07_HW10_change_locale.patch
- Добавил локализацию календаря `$.datetimepicker.setLocale(localeCode)`
- Вместо смены локали в `lang.jsp` через javascript сделал `href=${requestScope['javax.servlet.forward.request_uri']}?lang=..`
- Добавил [Collapsing The Navigation Bar](https://www.w3schools.com/bootstrap4/bootstrap_navbar.asp)

## Заключительное 11-е занятие

### Локализация:
#### Apply 11_08_i18n.patch
 - Добавил [локализацию Search в datatable](https://datatables.net/reference/option/language)
 - Сделал локализацию ошибок валидации:
    - [MessageSourceAccessor](https://stackoverflow.com/a/20550627/548473)
 - Добавил локализацию `ErrorInfo.type` (код локализации в `ErrorType` и поле `ErrorInfo.typeMessage`)
 - В выводе AJAX ошибки вывожу `errorInfo.typeMessage`
 - [Увеличил ширину высплывающего noty](https://stackoverflow.com/a/53855189/548473) 
 
### [Обработка ошибок 404 (NotFound)](https://stackoverflow.com/questions/18322279/spring-mvc-spring-security-and-error-handling)
#### Apply 11_09_404.patch

### Доступ к AuthorizedUser
#### Apply 11_10_auth_user.patch
- [Автоподстановка в контроллерах](https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#mvc-authentication-principal)
  - не стал делать автоподстановку по всем контроллерам (в абстрактных контроллерах проще работать с `SecurityUtil`, чем получать его через `@AuthenticationPrincipal` и передавать параметром)
- [В JSP: the authentication Tag](https://docs.spring.io/spring-security/site/docs/current/reference/html/taglibs.html#the-authentication-tag)
  - авторизованный пользователь доступен в JSP через tag `authentication`, интерсептор становится не нужным
  
### Вынес статус ошибки в параметры `ErrorType.status`
- Возвращение статуса идет через `ResponseEntity<ErrorInfo>` и `ModelAndView.setStatus`
#### Apply 11_11_error_status.patch

### Swagger2
#### Apply 11_12_swagger2.patch
- [Setting Up Swagger 2 with a Spring REST API](https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api)
- [Swagger 2 Configuration With Spring (XML)](https://medium.com/@andreymamontov/swagger-2-configuration-with-spring-xml-3cd643a12425)
- [Hiding Endpoints From Swagger Documentation](https://www.baeldung.com/spring-swagger-hiding-endpoints)
> В версиях выше 2.10 и 3.0 появились проблемы с маппингом. Вариант документации c OpenAPI 3.0 смотрите в [Spring Boot курсе](https://javaops.ru/view/bootjava)

### Ограничение модификации пользователей
#### Apply 11_13_restrict_modification.patch
 - В `UserService` добавил защиту от изменения `Admin/User` для профиля `HEROKU` (в `UserService` заинжектил `Environment` и сделал проверку на наличие профиля `HEROKU`)
 - В выпускном проекте (если только не выставляете в облако для показа) это НЕ требуется.   
 - Чтобы тесты были рабочими, ввел профиль `HEROKU`, работающий так же, как и `POSTGRES`.
 - Добавил универсальный `ApplicationException` для работы с ошибками с поддержкой i18n в приложении (от него отнаследовал `UpdateRestrictionException`)

> Для тестирования с профилем heroku добавьте в VM options: `-Dspring.profiles.active="datajpa,heroku"`

###  ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png)  <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFZkpVM19QWFBOQ2c">3. Деплой приложения в Heroku.</a>
#### Apply 11_14_heroku.patch

`hr.bat` запускает внутри 2 процесса - maven (`mvn` и `java`). Проверьте из консоли, что они будут работать (прописаны в системную переменную `Path`). Если запускаетесь из под IDEA и меняете `Path`, не забывайте перегрузиться.
```
mvn -version 
java --version
```


> - Добавил зависимости `postgres` в профиль мавена `heroku`
> - [Поменял настройки `dataSource` для профиля `heroku`](http://stackoverflow.com/questions/10684244/dbcp-validationquery-for-different-databases). 
При опускании/поднятии приложения в heroku.com портятся коннекты в пуле и необходимо их валидировать. 
> - В tomcat 9 в `META-INF\services\javax.cache.spi.CachingProvider` находится реализация провайдера Redis JCache, которая конфликтует с нашеим ehcache провайдером: `ehcache-3.6.1.jar!\META-INF\services\javax.cache.spi.CachingProvider`. 
 [Решается заменой зависимости на `webapp-runner-main`](https://github.com/jsimone/webapp-runner#excluding-memcached-and-redis-libraries)

### Приложение деплоится в ROOT: [http://localhost:8080](http://localhost:8080)

- [Деплой Java Spring приложения в PaaS-платформу Heroku](http://habrahabr.ru/post/265591)
```
Config Vars
  ERROR_PAGE_URL=...
  TOPJAVA_ROOT=/app

Datasources advanced
    ssl=true
    sslmode=require
    sslfactory=org.postgresql.ssl.NonValidatingFactory
```    

-  Ресурсы:
   -  <a href="https://www.heroku.com/">PaaS-платформа Heroku</a></h3>
   -  Конфигурирование приложения для запуска через <a href="https://devcenter.heroku.com/articles/java-webapp-runner">Tomcat-based Java Web</a>
   -  Конфигурирование <a href="https://devcenter.heroku.com/articles/connecting-to-relational-databases-on-heroku-with-java#using-the-database_url-in-spring-with-xml-configuration">DataSource profile для Heroku</a>
   -  <a href="http://www.paasify.it/filter">Find your Platform as a Service</a>
   -  <a href="https://devcenter.heroku.com/articles/getting-started-with-java#set-up">Getting Started with Java on Heroku</a>
   -  <a href="https://devcenter.heroku.com/articles/keys">Managing Your SSH Keys</a>
   -  <a href="https://devcenter.heroku.com/articles/getting-started-with-spring-mvc-hibernate#deploy-your-application-to-heroku">Deploy your application to Heroku</a>
   -  <a href="http://www.ibm.com/developerworks/ru/library/j-javadev2-21/">Развертывание приложений Java с помощью PaaS от Heroku</a>
   -  <a href="http://www.infoq.com/articles/paas_comparison">A Java Developer’s Guide to PaaS</a>
   -  <a href="https://dzone.com/articles/simple-paas-comparison-guide">A Simple PaaS Comparison Guide (With the Java Dev in Mind)</a>
   -  <a href="http://www.ibm.com/developerworks/library/j-paasshootout/">Java PaaS shootout</a>
   - [Deploying Java Applications with the Heroku Maven Plugin](https://devcenter.heroku.com/articles/deploying-java-applications-with-the-heroku-maven-plugin)


###  ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png)  <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFQVc2WUdCR0xvLWM">4. Собеседование. Разработка ПО</a>
- [Темы/ресурсы тестового собеседования](http://javaops.ru/interview/test.html)
- [Составление резюме, подготовка к интервью, поиск работы](https://github.com/JavaOPs/topjava/blob/master/cv.md)
- [Слайды](https://docs.google.com/presentation/d/18o__IGRqYadi4jx2wX2rX6AChHh-KrxktD8xI7bS33k), [Книги](http://javaops.ru/view/books)
- [Jenkins/Hudson: что такое и для чего он нужен](https://habrahabr.ru/post/334730/)

###  ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png)  [Вебинар: Составление резюме и поиск работы в IT](https://www.facebook.com/watch/live/?v=2789025168007756)
###  ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png)  <a href="https://drive.google.com/open?id=1QtHfavgIeLEnKA2Yt58XzKOouiLhg6qX">Разбор типовых собеседований (необработанный вебинар)</a>
###  ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png)  <a href="http://javaops.ru/view/resources/fromStudyToJob">Вебинар выпускников</a>

### Защита от XSS (Cross Site Scripting)
> **Попробуйте ввести в любое текстовое поле редактирования `<script>alert('XSS')</script>` и сохранить.**

- <a href="https://forum.antichat.ru/threads/20140/">XSS для новичков</a>
- <a href="https://habrahabr.ru/post/66057/">XSS глазами злоумышленника</a>

#### Опциональное Домашнее Задание: реализовать защиту XSS.

> - `password` проверять не надо, т.к. он не выводится в html, а [email надо](https://stackoverflow.com/questions/17480809)
> - Сделать общий интерфейс валидации `View.Web` и `@Validated(View.Web.class)` вместо `@Valid` для проверки содержимого только на входе UI/REST. При сохранении в базу проверка на безопасный html контент не делается.
>   - [Validation groups in Spring MVC](https://blog.codeleak.pl/2014/08/validation-groups-in-spring-mvc.html)

 [`@SafeHtml` удалили из hibernate validator](https://hibernate.org/validator/releases/), нужно делать самим. Как вариант [Sanitizing User Input](https://thoughtfulsoftware.wordpress.com/2013/05/26/sanitizing-user-input-part-ii-validation-with-spring-rest/) или [jsoup - Sanitize HTML](https://www.tutorialspoint.com/jsoup/jsoup_sanitize_html.htm)

-----------------------

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png)  Основное Домашнее Задание:
### **Задеплоить свое приложение в Heroku** 
   - [Маленькие хитрости с Heroku - активность 24/7](https://javarush.ru/groups/posts/1987-malenjhkie-khitrosti-s-heroku)
   
### **Пройдите основы Spring Boot по курсу [BootJava](https://javaops.ru/view/bootjava)**
- **Занятие по миграция на BootJava будет на следующей неделе, может получится раньше следующего четверга**

### **[Выполнить выпускной проект](https://github.com/JavaWebinar/topjava/blob/doc/doc/graduation.md)**
   - Сроки сдачи указан в выпускном.
   - Если есть проверка или Диплом, после выполнения выпускного [заполни форму проверки](https://docs.google.com/forms/d/1G8cSGBfXIy9bNECo6L-tkxWQYWeVhfzR7te4b-Jwn-Q) 
   - Если проверки или Диплома нет, заполнять не нужно. 
   -  **Возможно доплатить за ревью отдельно из JavaOPs профиля, как за тестовое собеседование: 2450р**

### **Сделать / обновить резюме (отдать на ревью в канал #hw11 группы slack)**
- **Вставь ссылку на свой сертификат [из личного профиля](http://javaops.ru/auth/profile#finished), немного досрочно:)**
   - [Загрузка сайта на GitHub. Бесплатный хостинг и домен.](https://vk.com/video-58538268_456239051?list=661b165047264e7952)
   - [CSS theme for hosting your personal site, blog, or portfolio](https://mademistakes.com/work/minimal-mistakes-jekyll-theme/)

#### ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Замечания по резюме:
   -  **если нет опыта в IT, обязательно вставь [участие в стажировке Topjava](https://github.com/JavaOPs/topjava/blob/master/cv.md#Позиционирование-проекта-topjava). Весь не-IT опыт можно кратко.**
   -  варианты размещения: Pdf в любом облаке, [Google Doc](https://docs.google.com/), LinkedIn, HH,  [еще варианты и рекомендации](https://github.com/JavaOPs/topjava/blob/master/cv.md#составление-резюме)  
Хорошо, если будет в html или pdf формате (например в https://pages.github.com/). [Например так](https://gkislin.github.io/), [на github](https://github.com/gkislin/gkislin.github.io/blob/master/index.html). Возраст и день рождения писать не обязательно
   -  [все упоминания Junior убрать!!](https://vk.com/javawebinar?w=wall-58538268_1589)
   -  линки делай кликабельными (если формат поддерживает)
   -  всю выгодную для себя информацию (и важную для HR) распологайте вверху. Название секций в резюме и их порядок относительно стандартный и важный
   - **Резюме на hh или других ресурсах ДОЛЖНО БЫТЬ ОТКРЫТО ДЛЯ ПРОСМОТРА и иметь телефон для связи**
   - Заполните контакты `skype/telegram/whatsapp`, HR ими пользуется! Почта как контакт очень медленная, телефон может быть не всегда удобен. Вообще `skype/telegram` для программиста - **Must have**.
   - **Добавьте в резюме ссылки на свои проекты в `GitHub` и на задеплоенные в `Heroku`. Не забудьте про выпускной!**.
   - Диплом РФ от Виакадемии о [профессиональной переподготовке](https://ru.wikipedia.org/wiki/Профессиональная_переподготовка) приравнивается ко второму высшему образованию.  В резюме, полагаю, можно указать в высшем образовании
   - Заполнить в [своем профиле Java Online Projects](http://javaops.ru/auth/profileER) ссылку на резюме и информацию по поиску работы (если конечно актуально): резюме, флаги рассматриваю работу, готов к релокации и информация для HR.
   - **Рассылку обновления базы соискателей по HR буду рассылать после 20.05, можно не спешить**
   - По [стажировка с последующим трудоустройством в Москве/ Санкт-Петербурге](http://javaops.ru/view/topjava#internship) детали будут в январе 2021.

### **После ревью резюме - опубликовать на ресурсах IT вакансий**
 - [Основные сайты поиска работы](https://github.com/JavaOPs/topjava/blob/master/cv.md#основные-сайты-поиска-работы)

### **Получить первое открытое занятие МНОГОПОТОЧНОСТЬ и пройти эту важную тему в [проекте Masterjava](http://javaops.ru/view/masterjava)**
   - Обучение на MasterJava идет в индивидуальном режиме без проверки ДЗ: старт в любой момент, время прохождение ничем не ограничено
   - Проект, патчи, группа Slack, занятия и видео, разбор ДЗ анологичны проекту Topjava. 

-------------------------

#### Возможные доработки приложения:
-  Разделить `Meal.dateTime` на `date` и `time` и выполнять запрос целиком в SQL
-  Для редактирования паролей сделать отдельный интерфейс с запросом старого пароля и кнопку сброса пароля для администратора.
-  Добавление и удаление ролей для пользователей в админке.
-  Перевести UI на Angular / <a href="https://vaadin.com/elements">Vaadin elements</a> /GWT /GXT /Vaadin / ZK/ [Ваш любимый фреймворк]..
-  Перевести шаблоны с JSP на <a href="http://www.thymeleaf.org/doc/articles/petclinic.html">Thymeleaf</a>
-  Сделать авторизацию в приложение по OAuth 2.0 (<a href="http://projects.spring.io/spring-security-oauth/">Spring Security OAuth</a>,
<a href="https://vk.com/dev/auth_mobile">VK auth</a>, <a href="https://developer.github.com/v3/oauth/">github oauth</a>, ...)
-  Сделать отображение еды постранично, с поиском и сортировкой на стороне сервера.
-  Перевод проекта на https
-  Сделать desktop/mobile приложение, работающее по REST с нашим приложением.
-  <a href="http://spring.io/blog/2012/08/29/integrating-spring-mvc-with-jquery-for-validation-rules/">Показ ошибок в модальном окне редактирования таблицы так же, как и в JSP профиля</a>
-  <a href="http://www.mkyong.com/spring-security/spring-security-limit-login-attempts-example">Limit login attempts example</a>
-  Сделать авторизацию REST по <a href="https://en.wikipedia.org/wiki/JSON_Web_Token">JWT</a>

#### Доработки участников прошлых выпусков:
- [Авторизация в приложение по OAuth2 через GitHub](http://rblik-topjava.herokuapp.com)
  - [GitHub, ветка oauth](https://github.com/rblik/topjava/tree/oauth)
- [Авторизация в приложение по OAuth2 через GitHub/Facebook/Google](http://tj9.herokuapp.com)
  - [GitHub](https://github.com/jacksn/topjava)
- [Angular 2 UI](https://topjava-angular2.herokuapp.com)
  - [tutorial по доработке](https://github.com/Gwulior/article/blob/master/article.md)
  - [ветка angular2 в гитхабе](https://github.com/12ozCode/topjava08-to-angular2/tree/angular2)
- [Отдельный фронтэнд на Angular 2, который работает по REST с авторизацией по JWT](https://topjava6-frontend.herokuapp.com)
  - [ветка development фронтэнда](https://github.com/evgeniycheban/topjava-frontend/tree/development)
  - [ветка development бэкэнда](https://github.com/evgeniycheban/topjava/tree/development)
  - в <a href="https://en.wikipedia.org/wiki/JSON_Web_Token">JWT токенен</a> приложение topjava передает email, name и роль admin как boolean true/false,
на клиенте он декодируется и из него получается auth-user, с которым уже работает фронтэнд

#### Жду твою доработку из списка!

### Ресурсы по Проекту
-  <a href="https://webformyself.com/urok-1-frejmvork-bootstrap-4-chto-takoe-bootstrap-otlichiya-bootstrap-3-ot-bootstrap-4/">Уроки Bootstrap 4</a>
-  <a herf="http://www.tutorialspoint.com/spring/index.htm">Spring at tutorialspoint</a>
-  <a href="http://www.codejava.net/frameworks/spring">Articles in Spring</a>
-  <a href="http://www.baeldung.com/learn-spring">Learn Spring on Baeldung</a>
-  <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/html/index.html">Spring Framework
            Reference Documentation</a>
-  <a href="http://hibernate.org/orm/documentation">Hibernate Documentation</a>
-  <a href="http://java-course.ru/student/book2/">Java Course (книга 2)</a>
-  <a href="http://design-pattern.ru/">Справочник «Паттерны проектирования»</a>
-  <a href="http://martinfowler.com/eaaCatalog/">Catalog of Patterns of Enterprise Application Architecture</a>
