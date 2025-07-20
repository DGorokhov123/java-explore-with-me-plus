package ru.practicum.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.event.dto.State;
import ru.practicum.event.model.Event;
import ru.practicum.request.ParticipationRequestStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByIdAndInitiator(Long eventId, Long initiatorId);

    List<Event> findByInitiator(Long initiatorId, org.springframework.data.domain.Pageable pageable);

    Optional<Event> findByIdAndState(Long id, State state);

    boolean existsByCategoryId(Long catId);


    Long countByEventIdAndStatus(Long eventId, ParticipationRequestStatus status);

    @Query("SELECT e FROM Event e " +
            "WHERE e.state = 'PUBLISHED' " +
            "AND (LOWER(e.annotation) LIKE LOWER(CONCAT('%', :text, '%')) OR LOWER(e.description) LIKE LOWER(CONCAT('%', :text, '%')) " +
            "AND (:categories IS NULL OR e.category.id IN :categories) " +
            "AND (:paid IS NULL OR e.paid = :paid) " +
            "AND (e.eventDate >= :rangeStart) " +
            "AND (:rangeEnd IS NULL OR e.eventDate <= :rangeEnd) " +
            "AND (:onlyAvailable = false OR e.participantLimit = 0 OR e.participantLimit > e.confirmedRequests)")
    Page<Event> findPublicEvents(
            String text,
            List<Long> categories,
            Boolean paid,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Boolean onlyAvailable,
            Pageable pageable);
}

