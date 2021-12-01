package com.example.househunt.category.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {

    private String name;
    private Integer numberOfHouses;
}
