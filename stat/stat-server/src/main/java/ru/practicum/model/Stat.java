package ru.practicum.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;

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
    long statId;

    @Column(name = "app", nullable = false)
    String app;

    @Column(name = "ip", nullable = false)
    String ip;

    @Column(name = "time_stamp", nullable = false)
    LocalDateTime timestamp;

    @Column(name = "uri", nullable = false)
    String uri;
}