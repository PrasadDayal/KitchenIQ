package com.KitchenIQ.repository;

import com.KitchenIQ.model.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

    //Get order by restaurant
    List<OrderHistory> findByRestaurantId(Long restaurantId);

    //Get orders by Food item
    List<OrderHistory> findByFoodItemId(Long foodItemId);

    //Get orders between dates(for prediction)
    List<OrderHistory> findByFoodItemIdAndOrderDateBetween(
            Long foodItemId,
            LocalDate startDate,
            LocalDate endDate
    );
}
