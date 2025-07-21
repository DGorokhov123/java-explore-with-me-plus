package ru.practicum.comment;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.exception.NotFoundException;
import ru.practicum.user.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentAdminServiceImpl implements CommentAdminService {

    CommentRepository repository;
    UserRepository userRepository;

    @Override
    public void delete(Long comId) {
        log.info("admin delete - invoked");
        if (repository.existsById(comId)) {
            log.error("User with id = {} not exist", comId);
            throw new NotFoundException("Comment not found");
        }
        log.info("Result: comment with id = {} deleted", comId);
        repository.deleteById(comId);
    }

    @Override
    public List<CommentDto> search(String text) {
        log.info("admin search - invoked");
        List<Comment> list = repository.findAllByText(text);
        log.info("Result: list of comments size = {} ", list.size());
        return CommentMapper.toListCommentDto(list);
    }

    @Override
    public List<CommentDto> findAllById(Long userId) {
        log.info("admin findAllById - invoked");
        if (userRepository.existsById(userId)) {
            log.error("User with id = {} not exist", userId);
            throw new NotFoundException("User not found");
        }
        List<Comment> list = repository.findAllByAuthorId(userId);
        log.info("Result: list of comments size = {} ", list.size());
        return CommentMapper.toListCommentDto(list);
    }
}