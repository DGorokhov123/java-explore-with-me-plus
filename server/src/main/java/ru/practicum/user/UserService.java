package ru.practicum.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<UserDto> findByIdListWithOffsetAndLimit(List<Long> idList, Integer from, Integer size) {
        return null;
    }

    @Transactional(readOnly = false)
    public UserDto create(NewUserRequestDto newUserRequestDto) {
        return null;
    }

    @Transactional(readOnly = false)
    public void delete(Long userId) {
        // 111
    }

}
