package ru.practicum.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.EventHitDto;
import ru.practicum.model.Stat;

@Mapper(componentModel = "spring")
public interface StatMapper {

    StatMapper INSTANCE = Mappers.getMapper(StatMapper.class);

    //    @Mapping(source = "app", target = "app")
//    @Mapping(source = "uri", target = "uri")
//    @Mapping(source = "ip", target = "ip")
//    @Mapping(source = "timestamp", target = "timestamp")
    Stat toStat(EventHitDto statDto);

    //    @Mapping(source = "app", target = "app")
//    @Mapping(source = "uri", target = "uri")
//    @Mapping(source = "ip", target = "ip")
//    @Mapping(source = "timestamp", target = "timestamp")
    EventHitDto toEventHitDto(Stat stat);

}
