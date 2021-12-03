package com.example.househunt.house.query;

import app.kyosk.libs.query.JsonQueryUtil;
import com.example.househunt.category.domain.Category_;
import com.example.househunt.house.domain.House;
import com.example.househunt.house.domain.House_;
import com.example.househunt.house.query.criteria.HouseCriteria;
import com.example.househunt.house.repository.HouseRepository;
import com.example.househunt.hunter.domain.Hunter_;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class HouseQueryService extends JsonQueryUtil<House> {

    final HouseRepository houseRepository;

    public List<House> findByCriteria(HouseCriteria houseCriteria) {
        log.debug("find by criteria : {}", houseCriteria);
        final Specification<House> specification = createSpecification(houseCriteria);
        return houseRepository.findAll(specification);
    }

    public Page<House> findByCriteria(HouseCriteria houseCriteria, Pageable page) {
        log.debug("find by criteria : {}", houseCriteria.toString());
        final Specification<House> specification = createSpecification(houseCriteria);
        return houseRepository.findAll(specification, page);
    }

    protected Specification<House> createSpecification(HouseCriteria houseCriteria) {
        Specification<House> specification = Specification.where(null);

        if (houseCriteria != null) {
            if (houseCriteria.getHouseId() != null) {
                specification = specification.and(buildSpecification(houseCriteria.getHouseId(), root -> root.get(House_.HOUSE_ID)));
            }

            if (houseCriteria.getTitle() != null) {
                specification = specification.and(buildSpecification(houseCriteria.getTitle(), root -> root.get(House_.TITLE)));
            }

            if (houseCriteria.getLocation() != null) {
                specification = specification.and(buildSpecification(houseCriteria.getLocation(), root -> root.get(House_.LOCATION)));
            }

            if (houseCriteria.getHouseStatus() != null) {
                specification = specification.and(buildSpecification(houseCriteria.getHouseStatus(), root -> root.get(House_.STATUS)));
            }

            if (houseCriteria.getUserName() != null) {
                specification =
                    specification.and(
                        buildSpecification(houseCriteria.getUserName(), root -> root.get(House_.HUNTER).get(Hunter_.USER_NAME))
                    );
            }

            if (houseCriteria.getHunterEmail() != null) {
                specification =
                    specification.and(
                        buildSpecification(houseCriteria.getHunterEmail(), root -> root.get(House_.HUNTER).get(Hunter_.EMAIL))
                    );
            }

            if (houseCriteria.getHunterStatus() != null) {
                specification =
                    specification.and(
                        buildSpecification(houseCriteria.getHunterStatus(), root -> root.get(House_.HUNTER).get(Hunter_.ACTIVE))
                    );
            }

            if (houseCriteria.getCategoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(houseCriteria.getCategoryId(), root -> root.get(House_.CATEGORY).get(Category_.ID))
                    );
            }

            if (houseCriteria.getCategoryName() != null) {
                specification =
                    specification.and(
                        buildSpecification(houseCriteria.getCategoryName(), root -> root.get(House_.CATEGORY).get(Category_.NAME))
                    );
            }
        }
        return specification;
    }
}
