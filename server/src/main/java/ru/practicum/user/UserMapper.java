package ru.practicum.user;

public class UserMapper {

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public static User toEntity(NewUserRequestDto dto) {
        return User.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .build();
    }

    public static UserShortDto toUserShortDto(User owner) {
        return UserShortDto.builder()
                .id(owner.getId())
                .name(owner.getName())
                .build();
    }
}
