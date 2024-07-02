package com.vti.vivuxe.repository;

import com.vti.vivuxe.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByUsername(String username);
	boolean existsByDriverLicense(String driverLicense);
	boolean existsByEmail(String email);
	boolean existsByPhone(String phone);
	boolean existsByUserId(Long id);
}
