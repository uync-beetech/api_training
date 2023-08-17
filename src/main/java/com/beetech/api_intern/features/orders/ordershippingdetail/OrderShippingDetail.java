package com.beetech.api_intern.features.orders.ordershippingdetail;

import com.beetech.api_intern.features.city.City;
import com.beetech.api_intern.features.district.District;
import com.beetech.api_intern.features.orders.Order;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_shipping_detail")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderShippingDetail implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    private Order order;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "address")
    private String address;

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime updated;
}
