package com.id.userproductservice.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.id.userproductservice.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String username);

	@Query(value="SELECT * FROM users WHERE username = ?1", nativeQuery=true)
	User findOneByUsername(String username);

	@Query(value="SELECT * FROM users WHERE email = ?1", nativeQuery=true)
	User findOneByEmail(String email);

}
