package com.example.househunt.house.rest;

import static org.springframework.http.ResponseEntity.status;

import com.example.househunt.house.domain.House;
import com.example.househunt.house.domain.HouseDTO;
import com.example.househunt.house.query.HouseQueryService;
import com.example.househunt.house.query.criteria.HouseCriteria;
import com.example.househunt.house.services.HouseService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

@AllArgsConstructor
@RestController
@RequestMapping("/api/houses")
public class HouseResource {

    final HouseService houseService;
    final HouseQueryService houseQueryService;

    @PostMapping("/add")
    public ResponseEntity<House> createHouseFromDTO(@RequestBody HouseDTO houseDTO) {
        var newHouse = houseService.createNewHouseAndSave(houseDTO);
        return ResponseEntity.ok(newHouse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<House> getHouseById(@PathVariable Long id) {
        return status(HttpStatus.OK).body(houseService.findHouseById(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<House> updateHouse(@PathVariable Long id, @RequestBody HouseDTO houseDTO) {
        return status(HttpStatus.OK).body(houseService.updateHouse(id, houseDTO));
    }

    @GetMapping("")
    public ResponseEntity<List<House>> getHouses(HouseCriteria houseCriteria) {
        List<House> houses = houseQueryService.findByCriteria(houseCriteria);
        return ResponseEntity.ok().body(houses);
    }
}
