package com.example.househunt.house.rest;

import static org.springframework.http.ResponseEntity.status;

import com.example.househunt.house.domain.House;
import com.example.househunt.house.domain.HouseDTO;
import com.example.househunt.house.services.HouseService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/houses")
public class HouseResource {

    private final HouseService houseService;

    @PostMapping("/add")
    public ResponseEntity<House> createHouseFromDTO(@RequestBody HouseDTO houseDTO) {
        var newHouse = houseService.createNewHouseAndSave(houseDTO);
        return ResponseEntity.ok(newHouse);
    }

    @GetMapping
    public ResponseEntity<List<House>> getAllHouses() {
        return status(HttpStatus.OK).body(houseService.getAllHouses());
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
    /*  @GetMapping("by-category/{id}")
    public ResponseEntity<List<HouseResponse>> getHousesByCategory(Long id) {
        return status(HttpStatus.OK).body(houseService.getHousesByCategory(id));
    }

    @GetMapping("by-user/{name}")
    public ResponseEntity<List<HouseResponse>> getHouseByUsername(@PathVariable String name) {
        return status(HttpStatus.OK).body(houseService.getHouseByUsername(name));
    }*/

}
