package com.example.househunt.house.domain;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import com.example.househunt.category.domain.Category;
import com.example.househunt.hunter.domain.Hunter;
import java.time.Instant;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "house")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class House {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long houseId;

    @Column
    String title;

    @Column
    String description;

    @Column
    Integer bedrooms;

    @Column
    String location;

    @Column
    Boolean status;

    @Column
    Integer feedbackCount = 0;

    @Column
    Instant createdDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "hunterId", referencedColumnName = "hunterId")
    private Hunter hunter;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "categoryId", referencedColumnName = "id")
    private Category category;
}
