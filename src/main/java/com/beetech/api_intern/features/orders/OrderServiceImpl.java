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
import com.beetech.api_intern.features.orders.dto.CreateOrderRequest;
import com.beetech.api_intern.features.orders.dto.FindAllOrderRequest;
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
    public List<Order> findAllOrder(FindAllOrderRequest dto) {
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
        // create string build and assign orderId to value
        StringBuilder orderIdStr = new StringBuilder(String.valueOf(orderId));
        // get length of order id
        int orderIdLength = orderIdStr.length();

        // if length > 4, trim to 4 chars
        if (orderIdLength > 4) {
            orderIdStr = new StringBuilder(orderIdStr.substring(orderIdLength - 4));
        } else {
            // while length < 4, insert "0" before id
            while (orderIdStr.length() < 4) {
                orderIdStr.insert(0, "0");
            }
        }

        // return displayId
        return "B" + orderIdStr;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Order createOrder(CreateOrderRequest dto) {
        // get user authenticated
        User user = UserUtils.getUser();
        // only  normal user can order
        if (user.isAdmin()) {
            throw new BadRequestException("admin cannot order");
        }
        // find user cart by user and version no
        Cart cart = cartRepository.findByUserIdAndVersionNo(user.getId(), dto.getVersionNo())
                .orElseThrow(CartNotFoundException::getInstance);

        // create order from user
        Order order = Order.builder()
                .user(user)
                .orderDate(LocalDate.now())
                .build();
        orderRepository.save(order);

        // create order detail list from cart detail
        List<OrderDetail> orderDetails = cart.getCartDetails().stream().map(cartDetail -> OrderDetail.builder()
                .order(order)
                .product(cartDetail.getProduct())
                .quantity(cartDetail.getQuantity())
                .build()).toList();

        // save all order detail
        orderDetailRepository.saveAll(orderDetails);

        // find district by district's id and city's id
        District district = districtRepository.findByIdAndCityId(dto.getDistrictId(), dto.getCityId())
                // throw exception if district not found
                .orElseThrow(() -> new EntityNotFoundException("District not found!"));

        // create order shipping detail from request data
        OrderShippingDetail orderShippingDetail = OrderShippingDetail.builder()
                .order(order)
                .address(dto.getAddress())
                .city(district.getCity())
                .district(district)
                .phoneNumber(dto.getPhoneNumber())
                .build();

        // save order shipping detail to database
        orderShippingDetailRepository.save(orderShippingDetail);

        // calculate total price of order
        Double totalPrice = orderDetails.stream().mapToDouble(OrderDetail::getPrice).sum();
        order.setTotalPrice(totalPrice);
        order.setDisplayId(generateDisplayId(order.getId()));

        // save order to db
        orderRepository.save(order);

        return order;
    }
}