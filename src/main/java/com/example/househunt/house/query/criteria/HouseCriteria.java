package com.example.househunt.house.query.criteria;

import lombok.Getter;
import lombok.Setter;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

// (houseId, title, location, status, hunterUsername, hunterEmail, hunterActiveStatus, categoryId, categoryName
@Getter
@Setter
public class HouseCriteria {

    LongFilter houseId;
    StringFilter title;
    StringFilter location;
    StringFilter userName;
    BooleanFilter houseStatus;
    StringFilter hunterEmail;
    BooleanFilter hunterStatus;
    LongFilter categoryId;
    StringFilter categoryName;
}
