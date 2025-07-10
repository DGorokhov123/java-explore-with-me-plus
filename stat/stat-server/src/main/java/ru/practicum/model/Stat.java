package ru.practicum.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "stat")
public class Stat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long statId;

    @Column(name = "app", nullable = false, length = 50)
    String app;

    @Column(name = "ip", nullable = false, length = 15)
    String ip;

    @Column(name = "time_stamp", nullable = false)
    LocalDateTime timestamp;

    @Column(name = "uri", nullable = false, length = 50)
    String uri;
}