package com.carrental.bookingservice.modules.inventory.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name="t_inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String carModel;

    @NotNull
    private Integer price;

    @NotNull
    private Date date;

    @NotNull
    private Integer numInStock;

    @NotNull
    private Date updatedAt;

    @PreUpdate
    @PrePersist
    public void updatedAt() {
        updatedAt = new Date();
    }
}
