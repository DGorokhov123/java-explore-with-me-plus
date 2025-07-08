package ru.practicum;


import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface StatClient {

    void hit(EventHitDto eventHitDto);

    Collection<EventStatsResponseDto> stats(LocalDateTime start,
                                            LocalDateTime end,
                                            List<String> uris,
                                            Boolean unique);

}
