package com.example.househunt.category.mapper;

import com.example.househunt.category.domain.Category;
import com.example.househunt.category.domain.CategoryDTO;
import com.example.househunt.house.domain.House;
import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "numberOfHouses", expression = "java(mapPosts(category.getHouses()))")
    CategoryDTO mapCategoryToDto(Category category);

    default Integer mapPosts(List<House> numberOfHouses) {
        return numberOfHouses.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "houses", ignore = true)
    Category mapDtoToCategory(CategoryDTO categoryDto);
}
