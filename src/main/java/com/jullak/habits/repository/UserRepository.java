package com.jullak.habits.repository;

import com.jullak.habits.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsByNickname(String nickname);

    Optional<User> findByNickname(String nickname);

    Optional<User> findByNicknameAndPassword(String nickname, String password);
}
