package com.beetech.api_intern.features.orders;

import com.beetech.api_intern.common.exceptions.BadRequestException;
import com.beetech.api_intern.common.exceptions.EntityNotFoundException;
import com.beetech.api_intern.common.utils.UserUtils;
import com.beetech.api_intern.features.carts.Cart;
import com.beetech.api_intern.features.carts.CartRepository;
import com.beetech.api_intern.features.carts.exceptions.CartNotFoundException;
import com.beetech.api_intern.features.city.CityRepository;
import com.beetech.api_intern.features.district.District;
import com.beetech.api_intern.features.district.DistrictRepository;
import com.beetech.api_intern.features.orders.dto.CreateOrderDto;
import com.beetech.api_intern.features.orders.dto.FindAllOrderDto;
import com.beetech.api_intern.features.orders.dto.UpdateOrderDto;
import com.beetech.api_intern.features.orders.exceptions.OrderNotFoundException;
import com.beetech.api_intern.features.orders.orderdetail.OrderDetail;
import com.beetech.api_intern.features.orders.orderdetail.OrderDetailRepository;
import com.beetech.api_intern.features.orders.ordershippingdetail.OrderShippingDetail;
import com.beetech.api_intern.features.orders.ordershippingdetail.OrderShippingDetailRepository;
import com.beetech.api_intern.features.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final OrderShippingDetailRepository orderShippingDetailRepository;

    @Override
    public List<Order> findAllOrder(FindAllOrderDto dto) {
        User user = UserUtils.getUser();
        List<Order> orders;
        if (user.isAdmin()) {
            orders = orderRepository.findAll();
        } else {
            orders = orderRepository.findAllByUserId(user.getId());
        }
        return orders;
    }

    private String generateDisplayId(Long orderId) {
        StringBuilder orderIdStr = new StringBuilder(String.valueOf(orderId));
        int orderIdLength = orderIdStr.length();

        if (orderIdLength > 4) {
            orderIdStr = new StringBuilder(orderIdStr.substring(orderIdLength - 4));
        } else {
            while (orderIdStr.length() < 4) {
                orderIdStr.insert(0, "0");
            }
        }

        return "B" + orderIdStr;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Order createOrder(CreateOrderDto dto) {
        User user = UserUtils.getUser();
        if (user.isAdmin()) {
            throw new BadRequestException("admin cannot order");
        }
        Cart cart = cartRepository.findByUserIdAndVersionNo(user.getId(), dto.getVersionNo())
                .orElseThrow(CartNotFoundException::getInstance);

        Order order = Order.builder()
                .user(user)
                .orderDate(LocalDate.now())
                .build();
        orderRepository.save(order);

        List<OrderDetail> orderDetails = cart.getCartDetails().stream().map(cartDetail -> OrderDetail.builder()
                .order(order)
                .product(cartDetail.getProduct())
                .quantity(cartDetail.getQuantity())
                .build()).toList();

        orderDetailRepository.saveAll(orderDetails);

        District district = districtRepository.findByIdAndCityId(dto.getDistrictId(), dto.getCityId())
                .orElseThrow(() -> new EntityNotFoundException("District not found!"));

        OrderShippingDetail orderShippingDetail = OrderShippingDetail.builder()
                .order(order)
                .address(dto.getAddress())
                .city(district.getCity())
                .district(district)
                .phoneNumber(dto.getPhoneNumber())
                .build();

        orderShippingDetailRepository.save(orderShippingDetail);

        Double totalPrice = orderDetails.stream().mapToDouble(OrderDetail::getPrice).sum();
        order.setTotalPrice(totalPrice);
        order.setDisplayId(generateDisplayId(order.getId()));

        orderRepository.save(order);

        return order;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateOrder(UpdateOrderDto dto) {
        User user = UserUtils.getUser();
        Order order;
        if (user.isAdmin()) {
            if (dto.getId() == null) {
                throw new BadRequestException("id must be not null");
            }
            order = orderRepository.findByIdAndDisplayId(dto.getId(), dto.getDisplayId())
                    .orElseThrow(OrderNotFoundException::getInstance);
        } else {
            order = orderRepository.findByUserIdAndDisplayId(user.getId(), dto.getDisplayId())
                    .orElseThrow(OrderNotFoundException::getInstance);
        }
        order.setStatus(dto.getStatus());
        orderRepository.save(order);
    }
}
