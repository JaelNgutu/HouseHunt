package com.example.househunt.house.domain;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import app.kyosk.libs.audit.AbstractEntity;
import com.example.househunt.category.domain.Category;
import com.example.househunt.hunter.domain.Hunter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.Instant;
import java.util.UUID;
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
    @JoinColumn(name = "hunterId", referencedColumnName = "hunterId", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    Hunter hunter;

    @Column(insertable = false, updatable = false, name = "hunterId")
    UUID hunterId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "categoryId", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    Category category;

    @Column(insertable = false, updatable = false, name = "categoryId")
    Long categoryId;

    public void setHunter(Hunter hunter) {
        this.hunter = hunter;
        if (hunter != null) {
            this.hunterId = hunter.getHunterId();
        }
    }

    public void setCategory(Category category) {
        this.category = category;
        if (category != null) {
            this.categoryId = category.getId();
        }
    }
}
