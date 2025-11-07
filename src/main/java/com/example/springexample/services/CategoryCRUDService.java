package com.example.springexample.services;
import com.example.springexample.dto.CategoryDto;
import com.example.springexample.entity.Category;
import com.example.springexample.exception.CategoryListEmptyException;
import com.example.springexample.exception.CategoryNotFoundException;
import com.example.springexample.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryCRUDService implements CRUDService<CategoryDto>{
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new CategoryNotFoundException(id));
        return mapToDto(category);
    }

    @Override
    public List<CategoryDto> getAll() {
        List<CategoryDto> categoryDtoList = categoryRepository.findAll().stream()
                .map(CategoryCRUDService::mapToDto)
                .toList();
        if (categoryDtoList.isEmpty()){
            throw new CategoryListEmptyException();
        }
        return categoryDtoList;
    }

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        Category category = mapToEntity(categoryDto);
        Category saved = categoryRepository.save(category);
        return mapToDto(saved);
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