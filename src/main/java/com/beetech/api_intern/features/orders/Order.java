package com.beetech.api_intern.features.orders;

import com.beetech.api_intern.features.orders.orderdetail.OrderDetail;
import com.beetech.api_intern.features.orders.ordershippingdetail.OrderShippingDetail;
import com.beetech.api_intern.features.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "display_id")
    @Setter
    private String displayId;

    @Column(name = "status")
    private Integer status = 1;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @OneToOne(mappedBy = "order")
    @Setter
    private OrderShippingDetail orderShippingDetail;

    @Column(name = "total_price")
    @Setter
    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order")
    @ToString.Exclude
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime updated;

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
