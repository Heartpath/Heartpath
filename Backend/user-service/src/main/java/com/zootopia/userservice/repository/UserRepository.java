package com.zootopia.userservice.repository;

import com.zootopia.userservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, String> {

    @Transactional(readOnly = true)
    Optional<User> findByKakaoID(Long kakaoId);

    @Transactional(readOnly = true)
    boolean existsByMemberID(String memberID);

    @Transactional(readOnly = true)
    Optional<User> findByMemberID(String memberID);
}
