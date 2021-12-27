package com.example.househunt.hunter.domain;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HunterDTO {

    String email;
    String username;
    String password;
    Instant dateCreated;
    Boolean active;
}
