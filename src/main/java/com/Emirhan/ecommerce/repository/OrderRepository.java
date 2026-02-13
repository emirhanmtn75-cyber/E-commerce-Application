package com.Emirhan.ecommerce.repository;
import com.Emirhan.ecommerce.entity.Order;
import com.Emirhan.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

}
