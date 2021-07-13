# Стажировкаа <a href="https://github.com/JavaWebinar/topjava">TopJava</a>

## [Патчи](https://drive.google.com/drive/u/1/folders/1ZsPX879m6x4Va0Wy3D1EQIBsnZUOOvao)

### ![correction](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Правки в проекте

#### Apply 12_0_fix_valid_update

- Добавил `@Valid` на update. Тесты ломаются, тк. не вызывается `validateBeforeUpdate`

#### Apply 12_1_workaround_unique_email_validation

- Вернулся к автоматической валидации (убрал валидацию вручную в `validateBeforeUpdate`).
- В `UniqueMailValidator.validate` сделал обходное решение с использованием заинжекченного `HttpServletRequest`

#### Apply 12_2_XSS

- Все классы валидации разместил в `ru.javawebinar.topjava.util.validation`
- Сделал собственную аннотацию `@NoHtml` на основе [Sanitizing User Input](https://thoughtfulsoftware.wordpress.com/2013/05/26/sanitizing-user-input-part-ii-validation-with-spring-rest/)
  и [jsoup - Sanitize HTML](https://www.tutorialspoint.com/jsoup/jsoup_sanitize_html.htm)

## Spring Boot + Миграция

### 1. Пройдите основы Spring Boot: [BootJava](https://javaops.ru/view/bootjava)

### 2. Миграция REST части проекта `Calories Management` на Spring Boot

Вычекайте в отдельную папку (как отдельный проект) ветку `spring-boot`:

```
git clone --branch spring-boot --single-branch https://github.com/JavaWebinar/topjava.git topjava_boot
```  

Если будете его менять, [настройте `git remote`](https://javaops.ru/view/bootjava/lesson01#project)

- Проект сделал с минимальным количеством кода (как тестовое задание или ТЗ на выпускной проект): убрал слой сервисов, профили, группы, локализацию, весь UI.
- База создается автогенераций по модели (для тестового задания и базы в памяти - лучший вариант).
- При запросе сущности с неверным `id` вместо `NotFoundException` возвращаю `notFound()`, см. реализацию `ResponseEntity.of`
- Сделал `BaseRepository` - сюда можно размещать [общие методы репозиториев](https://stackoverflow.com/questions/42781264/multiple-base-repositories-in-spring-data-jpa)
- Вместо своих конверторов использую `@DateTimeFormat`
- Мигрировал все тесты контроллеров. В тестовом проекте столько тестов **НЕ ТРЕБУЕТСЯ**, достаточно нескольких на основные юзкейсы.
- [REST API documentation](http://localhost:8080/swagger-ui.html) сделал на основе [OpenAPI 3.0](https://javaops.ru/view/bootjava/lesson06#openapi). **Добавьте в выпускной проект - это будет большим
  плюсом и избавит от необходимости писать документацию**. Не забудьте ссылку на нее в `readme.md`!
- Кэширование желательно в выпускном. 7 раз подумайте, что будете кэшировать! **Максимально просто, самые частые запросы, которые редко изменяются**.
