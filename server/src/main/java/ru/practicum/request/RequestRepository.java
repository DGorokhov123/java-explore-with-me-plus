package ru.practicum.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    boolean existsByRequesterIdAndEventId(Long userId, Long eventId);

    long countByEventIdAndStatus(Long eventId, ParticipationRequestStatus status);

    List<Request> findByRequesterId(Long userId);

    List<Request> findByEventId(Long eventId);

    @Modifying
    @Query("UPDATE Request r SET r.status = :status WHERE r.id IN :ids")
    void updateStatusByIds(
            @Param("ids") List<Long> ids,
            @Param("status") ParticipationRequestStatus status
    );

    @Modifying
    @Query("""
            UPDATE Request r
            SET r.status = 'REJECTED'
            WHERE r.event.id = :eventId
            AND r.status = 'PENDING'
            """)
    void setStatusToRejectForAllPending(
            @Param("eventId") Long eventId
    );

}
