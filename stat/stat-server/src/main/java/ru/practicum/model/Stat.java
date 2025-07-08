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

    @Column(name = "app")
    String app;

    @Column(name = "ip")
    String ip;

    @Column(name = "time_stamp")
    LocalDateTime timestamp;

    @Column(name = "uri")
    String uri;
}