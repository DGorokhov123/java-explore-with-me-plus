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
    @Column(name = "id")
    Long id;

    @Column(name = "annotation")
    String annotation;

    @ManyToOne
    @JoinColumn(name = "categories_id")
    Category category;


    @JoinColumn(name = "description")
    String description;


    @Column(name = "createdOn")
    LocalDateTime createdOn;

    @ManyToOne
    @JoinColumn(name = "initiator")
    User user;

    @Embedded
    Location location;

    @Column(name = "paid")
    Boolean paid;

    @Column(name = "participant_limit")
    Long participantLimit;

    @Column(name = "published_on")
    LocalDateTime publishedOn;

    @Column(name = "request_moderation")
    Boolean requestModeration;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    State state;

    @Column(name = "title")
    String title;

    @Column(name = "views")
    Long views;

    @Column(name = "event_date")
    LocalDateTime eventDate;

}
