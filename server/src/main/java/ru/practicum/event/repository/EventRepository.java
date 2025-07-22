package ru.practicum.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.event.dto.State;
import ru.practicum.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    Optional<Event> findByIdAndInitiatorId(Long eventId, Long initiatorId);

    List<Event> findByInitiatorId(Long initiatorId, Pageable pageable);

    Optional<Event> findByIdAndState(Long id, State state);

    boolean existsByCategoryId(Long catId);

    // это публичный эндпоинт, соответственно в выдаче должны быть только опубликованные события
    // текстовый поиск (по аннотации и подробному описанию) должен быть без учета регистра букв
    @Query("""
            SELECT e, count(r) c FROM Event e
            LEFT JOIN Request r ON r.event = e AND r.status = 'CONFIRMED'
            WHERE e.state = 'PUBLISHED'
            AND ( :text IS NULL OR e.annotation ILIKE CONCAT('%', :text, '%') OR e.description ILIKE CONCAT('%', :text, '%') )
            AND ( :categories IS NULL OR e.category.id IN :categories )
            AND ( :paid IS NULL OR e.paid = :paid )
            AND ( e.eventDate >= :rangeStart )
            AND ( e.eventDate <= :rangeEnd )
            GROUP BY e
            HAVING ( :onlyAvailable = false OR e.participantLimit = 0 OR e.participantLimit > count(r) )
            """)
    Page<Object[]> findPublicEvents(
            String text,
            List<Long> categories,
            Boolean paid,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Boolean onlyAvailable,
            Pageable pageable
    );


//    @Query("""
//            SELECT e FROM Event e
//            WHERE ( :users IS NULL OR e.initiator.id IN :users )
//            AND ( :states IS NULL OR e.state IN :states )
//            AND ( :categories IS NULL OR e.category.id IN :categories )
//            AND ( e.eventDate >= :rangeStart )
//            AND ( e.eventDate <= :rangeEnd )
//            """)
//    Page<Event> findEventsByAdminParams(
//            @Param("users") List<Long> users,
//            @Param("states") List<State> states,
//            @Param("categories") List<Long> categories,
//            @Param("rangeStart") LocalDateTime rangeStart,
//            @Param("rangeEnd") LocalDateTime rangeEnd,
//            Pageable pageable
//    );

}

