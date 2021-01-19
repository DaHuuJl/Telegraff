package com.blog.telegraff.data.repository;

import com.blog.telegraff.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Репозиторий для работы с таблицей сущности User ({@link com.blog.telegraff.data.model.User}) базы данных
 * @author Danyil Smirnov
 */
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    User findByUsername(String username);
}
