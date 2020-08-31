package com.bravo.league.repositories;

import com.bravo.league.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.profiles WHERE u.id = :id")
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);
}
