package ru.practicum.event.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.category.Category;
import ru.practicum.event.dto.Location;
import ru.practicum.event.dto.State;
import ru.practicum.user.User;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "annotation", length = 2000, nullable = false)
    String annotation;

    @ManyToOne
    @JoinColumn(name = "categories_id", nullable = false)
    Category category;

    @Column(name = "created_on", nullable = false)
    LocalDateTime createdOn;

    @ManyToOne
    @JoinColumn(name = "initiator", nullable = false)
    User user;

    @Embedded
    Location location;

    @Column(name = "paid", nullable = false)
    Boolean paid;

    @Column(name = "participant_limit")
    Long participantLimit;

    @Column(name = "published_on")
    LocalDateTime publishedOn;

    @Column(name = "request_moderation")
    Boolean requestModeration;

    @Column(name = "description", length = 7000, nullable = false)
    String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", length = 20, nullable = false)
    State state;

    @Column(name = "title", length = 120, nullable = false)
    String title;

    @Column(name = "views")
    Long views;

    @Column(name = "event_date", nullable = false)
    LocalDateTime eventDate;
}