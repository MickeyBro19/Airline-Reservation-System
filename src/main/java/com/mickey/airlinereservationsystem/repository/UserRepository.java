package com.mickey.airlinereservationsystem.repository;

import com.mickey.airlinereservationsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
