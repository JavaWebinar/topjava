package ru.javaops.topjava.user.web;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava.app.AuthUser;
import ru.javaops.topjava.user.model.Meal;
import ru.javaops.topjava.user.repository.MealRepository;
import ru.javaops.topjava.user.service.MealService;
import ru.javaops.topjava.user.to.MealTo;
import ru.javaops.topjava.user.util.MealsUtil;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javaops.topjava.common.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.topjava.common.validation.ValidationUtil.checkIsNew;
import static ru.javaops.topjava.user.util.DateTimeUtil.atStartOfDayOrMin;
import static ru.javaops.topjava.user.util.DateTimeUtil.atStartOfNextDayOrMax;

@RestController
@RequestMapping(value = MealController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class MealController {
    static final String REST_URL = "/api/profile/meals";

    private final MealRepository repository;
    private final MealService service;

    @GetMapping("/{id}")
    public ResponseEntity<Meal> get(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("get meal {} for user {}", id, authUser.id());
        return ResponseEntity.of(repository.get(authUser.id(), id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("delete {} for user {}", id, authUser.id());
        Meal meal = repository.getBelonged(authUser.id(), id);
        repository.delete(meal);
    }

    @GetMapping
    public List<MealTo> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("getAll for user {}", authUser.id());
        return MealsUtil.getTos(repository.getAll(authUser.id()), authUser.getUser().getCaloriesPerDay());
    }


    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody Meal meal, @PathVariable int id) {
        int userId = authUser.id();
        log.info("update {} for user {}", meal, userId);
        assureIdConsistent(meal, id);
        repository.getBelonged(userId, id);
        service.save(userId, meal);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody Meal meal) {
        int userId = authUser.id();
        log.info("create {} for user {}", meal, userId);
        checkIsNew(meal);
        Meal created = service.save(userId, meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }


    @GetMapping("/filter")
    public List<MealTo> getBetween(@AuthenticationPrincipal AuthUser authUser,
                                   @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                   @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) @Schema(type = "LocalTime") LocalTime startTime,
                                   @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                   @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) @Schema(type = "LocalTime") LocalTime endTime) {

        int userId = authUser.id();
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);
        List<Meal> mealsDateFiltered = repository.getBetweenHalfOpen(userId, atStartOfDayOrMin(startDate), atStartOfNextDayOrMax(endDate));
        return MealsUtil.getFilteredTos(mealsDateFiltered, authUser.getUser().getCaloriesPerDay(), startTime, endTime);
    }
}