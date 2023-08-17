package com.beetech.api_intern.features.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Order repository.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * Find all by user id list.
     *
     * @param userId the user id
     * @return the list
     */
    List<Order> findAllByUserId(Long userId);

    /**
     * Find by id and display id optional.
     *
     * @param id        the id
     * @param displayId the display id
     * @return the optional
     */
    Optional<Order> findByIdAndDisplayId(Long id, String displayId);

    /**
     * Find by user id and display id optional.
     *
     * @param userId    the user id
     * @param displayId the display id
     * @return the optional
     */
    Optional<Order> findByUserIdAndDisplayId(Long userId, String displayId);
}
