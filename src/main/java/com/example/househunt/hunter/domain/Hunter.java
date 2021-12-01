package com.example.househunt.hunter.domain;

import com.example.househunt.house.domain.House;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "hunter")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hunter {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "UUID")
    UUID hunterId;

    @Column
    String userName;

    @Column
    String email;

    @Column
    Instant dateCreated;

    @Column
    String password;

    @Column
    Boolean active;

    @OneToMany
    @Builder.Default
    List<House> house = new ArrayList<>();
}
