package com.blog.telegraff.data.repository;

import com.blog.telegraff.data.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Репозиторий для работы с таблицей сущности Post ({@link com.blog.telegraff.data.model.Post}) базы данных
 * @author Danyil Smirnov
 */
@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    ArrayList<Post> findAllByUserId(Long userId);
}
