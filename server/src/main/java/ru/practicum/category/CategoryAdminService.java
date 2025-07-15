package ru.practicum.category;

import org.springframework.transaction.annotation.Transactional;

public interface CategoryAdminService {
    @Transactional
    CategoryDto createCategory(CategoryDto requestCategory);

    @Transactional
    void deleteCategory(Long catId);

    @Transactional
    CategoryDto updateCategory(Long catId, CategoryDto categoryDto);
}
