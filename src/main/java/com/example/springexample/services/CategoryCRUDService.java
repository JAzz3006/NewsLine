package com.example.springexample.services;
import com.example.springexample.dto.CategoryDto;
import com.example.springexample.entity.Category;
import com.example.springexample.exception.CategoryNotFoundException;
import com.example.springexample.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryCRUDService implements CRUDService<CategoryDto>{
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow();
        return mapToDto(category);
    }

    @Override
    public List<CategoryDto> getAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryCRUDService::mapToDto)
                .toList();
    }

    @Override
    public void create(CategoryDto categoryDto) {
        Category category = mapToEntity(categoryDto);
        categoryRepository.save(category);
    }

    @Override
    public void update(CategoryDto categoryDto) {
        Long id = categoryDto.getId();
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        categoryRepository.deleteById(id);
    }

    public static Category mapToEntity (CategoryDto categoryDto){
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setTitle(categoryDto.getTitle());
        category.setNewsList(categoryDto.getNewsDtoList().stream()
                .map(NewsCRUDService::mapToEntity)
                .toList());
        return category;
    }

    public static CategoryDto mapToDto(Category category){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setTitle(category.getTitle());
        categoryDto.setNewsDtoList(category.getNewsList().stream()
                .map(NewsCRUDService::mapToDto)
                .toList());
        return categoryDto;
    }
}