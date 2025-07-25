package ru.practicum.category;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CategoryMapper {

    public CategoryDto toCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public Category toCategories(CategoryDto categoryDto) {
        return Category.builder()
                .name(categoryDto.getName())
                .build();
    }

    public List<CategoryDto> toListCategoriesDto(List<Category> list) {
        return list.stream().map(CategoryMapper::toCategoryDto).collect(Collectors.toList());
    }
}
