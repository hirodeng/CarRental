package com.carrental.bookingservice.modules.order.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="t_order")
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private String carModel;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

    @NotNull
    private Integer status; // 50 - canceled, 100 - new, 120 - paid, 200 - completed

    @NotNull
    private Integer amount;

    private Date createdAt;

    private Date updatedAt;

    public boolean orderCanPay() {
        return status != null && status == 100;
    }

    public void payOrder() {
        status = Status.PAID.value;
    }

    public boolean orderCanCancel() {
        return status == Status.NEW.value || status == Status.PAID.value;
    }

    public void cancelOrder() {
        status = Status.CANCELED.value;
    }

    public boolean orderCanComplete() {
        return status == Status.PAID.value;
    }

    public void completeOrder() {
        status = Status.COMPLETE.value;
    }

    public static enum Status {
        CANCELED(50),
        NEW(100),
        PAID(120),
        COMPLETE(200)

        ;

        public Integer value;

        private Status(Integer value) {
            this.value = value;
        }
    }

    @PrePersist
    void createdAt() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    void updatedAt() {
        updatedAt = new Date();
    }
}
