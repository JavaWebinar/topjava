package ru.javaops.topjava.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import ru.javaops.topjava.common.model.BaseEntity;
import ru.javaops.topjava.common.validation.NoHtml;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "meal", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "meal_unique_user_datetime_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"user"})
public class Meal extends BaseEntity {

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    @NoHtml
    private String description;

    @Column(name = "calories", nullable = false)
    @NotNull
    @Range(min = 10, max = 5000)
    private Integer calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    @Schema(hidden = true)
    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    @Schema(hidden = true)
    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }
}
