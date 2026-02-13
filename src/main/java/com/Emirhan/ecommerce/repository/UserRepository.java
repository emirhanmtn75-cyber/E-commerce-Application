package com.Emirhan.ecommerce.repository;

import com.Emirhan.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

     User findByEmail(String email);
}
