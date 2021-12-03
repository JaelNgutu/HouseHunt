package com.example.househunt.house.services;

import com.example.househunt.category.domain.Category;
import com.example.househunt.category.repository.CategoryRepository;
import com.example.househunt.exceptions.CategoryNotFoundException;
import com.example.househunt.house.domain.House;
import com.example.househunt.house.domain.HouseDTO;
import com.example.househunt.house.repository.HouseRepository;
import com.example.househunt.hunter.repository.HunterRepository;
import com.example.househunt.hunter.service.HunterService;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Builder
public class HouseService {

    private final HouseRepository houseRepository;
    private final CategoryRepository categoryRepository;
    private final HunterRepository hunterRepository;
    private final HunterService hunterService;

    public List<House> getAllHouses() {
        return houseRepository.findAll();
    }

    public House findHouseById(Long id) {
        return houseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("House with id {} not found"));
    }

    //start from here
    House createHouseFromDTO(HouseDTO houseDTO) {
        return House
            .builder()
            .title(houseDTO.getTitle())
            .description(houseDTO.getDescription())
            .bedrooms(houseDTO.getBedrooms())
            .location(houseDTO.getLocation())
            .status(false)
            .createdDate(Instant.now())
            .category(getCategory(houseDTO))
            .hunter(hunterService.getCurrentUser())
            .build();
    }

    public House save(House house) {
        return houseRepository.save(house);
    }

    public House createNewHouseAndSave(HouseDTO houseDTO) {
        var newHouse = createHouseFromDTO(houseDTO);
        newHouse = save(newHouse);

        return newHouse;
    }

    public Category getCategory(HouseDTO houseDTO) {
        Category category = categoryRepository
            .findByName(houseDTO.getCategoryName())
            .orElseThrow(() -> new CategoryNotFoundException(houseDTO.getCategoryName()));
        return category;
    }

    public House updateHouse(Long id, HouseDTO houseDTO) {
        Optional<House> houseOptional = houseRepository.findById(id);
        if (houseOptional.isPresent()) {
            House house = houseOptional.get();
            house.setTitle(houseDTO.getTitle());
            house.setDescription(houseDTO.getDescription());
            house.setBedrooms(houseDTO.getBedrooms());
            house.setHunter(hunterService.getCurrentUser());
            house.setCategory(getCategory(houseDTO));
            house.setLocation(houseDTO.getLocation());

            houseRepository.save(house);
        }

        return null;
    }
}
