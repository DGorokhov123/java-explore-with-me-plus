package ru.practicum.category;

import org.springframework.transaction.annotation.Transactional;

public interface CategoryAdminService {
    CategoryDto createCategory(CategoryDto requestCategory);

    void deleteCategory(Long catId);

    CategoryDto updateCategory(Long catId, CategoryDto categoryDto);
}
