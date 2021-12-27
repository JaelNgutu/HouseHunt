package com.example.househunt.hunter.repository;

import com.example.househunt.hunter.domain.Hunter;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HunterRepository extends JpaRepository<Hunter, UUID> {
    Optional<Hunter> findByUserName(String username);
}
