package br.com.pettz.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.pettz.models.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);
    
    boolean existsByEmail(String email);

    @Modifying
	@Query("UPDATE User u SET u.enabled = false WHERE u.email = :email")
	void disableUser(@Param("email") String email);
}