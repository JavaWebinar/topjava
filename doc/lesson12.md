# Стажировка <a href="https://github.com/JavaWebinar/topjava">Topjava</a>

## [Патчи занятия](https://drive.google.com/drive/u/1/folders/1sizknxR29Yu7XXjaVIBdS88ffXiVuqiB)

## Миграция на Spring Boot
За основу кода взят **[финальный код BootJava, ветка patched](https://github.com/JavaOPs/bootjava/tree/patched/src/main/java/ru/javaops/bootjava)**  
Вычекайте в отдельную папку (как отдельный проект) ветку `spring_boot` нашего проекта (так удобнее, не придется постоянно переключаться между ветками):
```
git clone --branch spring_boot --single-branch https://github.com/JavaWebinar/topjava.git topjava_boot
```  
Если будете его менять, [настройте `git remote`](https://javaops.ru/view/bootjava/lesson01#project)  
> Если захотите сами накатить патчи, сделайте ветку `spring_boot` от первого `init` и в корне **создайте каталог `src\test`**  

----

#### Apply 12_1_init_boot_java
- Для средних и больших проектов более удобна [организация пакетов по функционалу](https://stackoverflow.com/questions/6260302/548473): сначала крупными мазками функционал, внутри него слои.
  Финальный код Spring Boot 3.x (BootJava) разбил по функционалу на `app`, `common` и `user`.
  Процесс творческий, на одном из проектов я попробовал использовать [Spring Modulith](https://spring.io/projects/spring-modulith) и отказался от него, так как очень часто сущности делятся между разным функционалом и строгих границ не провести. "Суббота для человека, а не человек для субботы" в программировании звучит как: "Здравый смысл впереди строгой идеологии и архитектуры".  
  Именно поэтому еда, принадлежащая юзеру, попала в пакет `user`. Если будете соблюдать такую структуру в своем выпускном проекте - НЕ МЕЛЬЧИТЕ!
- Для получения авторизованного пользователя из `AuthUser` выделил отдельный `AuthUtil` класс.

#### Apply 12_2_migrate_spring_boot_4
Мигрировал проект на [Spring Boot 4](https://javaops.ru/view/bootjava4/lesson01)

#### Apply 12_3_add_calories_meals
Добавил из TopJava: 
- Еду, калории
- Таблицы назвал в единственном числе: `user_role, meal` (кроме `users`, _user_ зарезервированное слово)
- Общие вещи (пусть небольшие) вынес в сервис : `MealService`
- Проверку принадлежности еды делаю в `MealRepository.getBelonged`
- Вместо своих конверторов использую `@DateTimeFormat`
- Обратите внимание на `UserRepository.getWithMeals` - он [не работает с `@EntityGraph`. Зато работает с обычным `JOIN FETCH`](https://stackoverflow.com/a/46013654/548473) и `DISTINCT` больше не нужен:
  - [forget about DISTINCT](https://vladmihalcea.com/spring-6-migration/#Auto-deduplication)
  - [it is no longer necessary to use distinct in JPQL and HQL](https://docs.jboss.org/hibernate/orm/6.0/migration-guide/migration-guide.html#query-sqm-distinct)
- Мигрировал все тесты контроллеров. В выпускном проекте столько тестов необязательно! Достаточно нескольких, на основные юзкейсы.
- Кэширование в выпускном очень желательно. 7 раз подумайте, что будете кэшировать!  
- **Максимально просто, самые частые запросы, которые редко изменяются**.
- **Добавьте в свой выпускной OpenApi/Swagger - это будет большим плюсом и избавит от необходимости писать документацию**.

### За основу выпускного предлагаю взять этот код миграции, сделав свой выпускной МАКСИМАЛЬНО в этом стиле.
### Успехов с выпускным проектом и в карьере! 
