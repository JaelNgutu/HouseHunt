package com.example.househunt.category.service;

import static java.util.stream.Collectors.toList;

import com.example.househunt.category.domain.Category;
import com.example.househunt.category.domain.CategoryDTO;
import com.example.househunt.category.mapper.CategoryMapper;
import com.example.househunt.category.repository.CategoryRepository;
import com.example.househunt.exceptions.HouseHuntException;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Builder
public class CategoryService {

    final CategoryRepository categoryRepository;
    final CategoryMapper categoryMapper;

    /*  Category createCategoryFromDTO(CategoryDTO categoryDTO){
        return Category.builder()
                .name(categoryDTO.getName())
                .build();
    }*/

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    //save new user and send verification email
    public Category createNewCategoryAndSave(CategoryDTO categoryDTO) {
        var newCategory = categoryMapper.mapDtoToCategory(categoryDTO);
        newCategory = save(newCategory);

        return newCategory;
    }

    public List<CategoryDTO> getAll() {
        return categoryRepository.findAll().stream().map(categoryMapper::mapCategoryToDto).collect(toList());
    }

    public CategoryDTO getCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new HouseHuntException("No category found with ID - " + id));
        return categoryMapper.mapCategoryToDto(category);
    }
}
