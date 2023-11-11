package com.zootopia.userservice.repository;

import com.zootopia.userservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByKakaoID(Long kakaoId);

    boolean existsByMemberID(String memberID);

    Optional<User> findByMemberID(String memberID);
}