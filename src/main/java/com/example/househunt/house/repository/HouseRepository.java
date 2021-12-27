package com.example.househunt.house.repository;

import com.example.househunt.category.domain.Category;
import com.example.househunt.house.domain.House;
import com.example.househunt.hunter.domain.Hunter;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends JpaRepository<House, Long>, JpaSpecificationExecutor<House> {
    List<House> findAllByCategory(Category category);

    List<House> findByHunter(Hunter hunter);
}
