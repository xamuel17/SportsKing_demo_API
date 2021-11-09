package com.sport.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sport.models.User;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	 @Query(value = "SELECT is_verified from users u where u.email =:email ", nativeQuery = true)
	Boolean findWhereUserActive(@Param("email")String email);

	Optional<User> findByEmail(String email);


	Optional<User> findByPhone(String phone);

	 @Query(value = "SELECT * from users u where u.id =:userId ", nativeQuery = true)
	User findByUserId(@Param("userId") Long userId);

	 
	 @Query(value = "SELECT * from users u where u.verification_code =:code ", nativeQuery = true)
	User findByVerificationCode(String code);

	 
	 @Query(value = "SELECT * from users u where u.verification_code =:code ", nativeQuery = true)
	Boolean activateAccount(String code);

	 
	 @Query(value = "SELECT * from users u where u.username =:uname ", nativeQuery = true)
	User findByUname(String uname);
	
	

	   
}