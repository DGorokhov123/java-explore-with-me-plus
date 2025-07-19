package ru.practicum.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.event.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByIdAndInitiator(Long eventId, Long initiatorId);

    List<Event> findByInitiator(Long initiatorId, org.springframework.data.domain.Pageable pageable);

    boolean existsByCategoryId(Long catId);

}
