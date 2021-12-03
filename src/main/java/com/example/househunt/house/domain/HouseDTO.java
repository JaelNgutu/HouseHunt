package com.example.househunt.house.domain;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class HouseDTO {

    Long houseId;
    String title;
    String description;
    Integer bedrooms;
    String location;
    Boolean status;
    Instant createdDate;
    String categoryName;
}
