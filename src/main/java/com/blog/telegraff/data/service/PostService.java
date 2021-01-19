package com.blog.telegraff.data.service;

import com.blog.telegraff.data.model.Post;
import com.blog.telegraff.data.model.User;
import com.blog.telegraff.data.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * Сервис для работы с таблицей сущности Post ({@link com.blog.telegraff.data.model.Post})
 * через репозиторий PostRepository ({@link com.blog.telegraff.data.repository.PostRepository})
 * @author Danyil Smirnov
 */
@Service
public class PostService {
    /** Репозиторий для работы с таблицей сущности Post ({@link com.blog.telegraff.data.model.Post}) базы данных */
    @Autowired
    PostRepository postRepository;

    /**
     * Функция для сохранения новой публикации
     * @param post публикация
     */
    public void save (Post post) {
        postRepository.save(post);
    }

    /**
     * Функция для поиска публикаций определенного пользователя
     * @param user пользователь
     * @return публикации
     */
    public ArrayList<Post> findAllByUserId (User user) {
        return postRepository.findAllByUserId(user.getId());
    }

    /**
     * Функция для поиска всех публикаций
     * @param user пользователь
     * @return публикации
     */
    public ArrayList<Post> findAll (User user) {
        return (ArrayList<Post>) postRepository.findAll();
    }

    /**
     * Функция для поиска публикации по идентификатору
     * @param id идентификатор публикации
     * @return публикация
     */
    public Optional<Post> findById (Long id) {
        return postRepository.findById(id);
    }

    /**
     * Функция для поиска публикаций которые не принадлежат определенному пользователю
     * @param userId идентификатор пользователя
     * @return публикации
     */
    public ArrayList<Post> findAllExceptByUserId (Long userId){
        ArrayList<Post> result = (ArrayList<Post>) postRepository.findAll();
        ArrayList<Post> posts = new ArrayList<>();
        for(Post post : result) {
            if(!post.getUserId().equals(userId)) {
                posts.add(post);
            }
        }
        posts.sort(Collections.reverseOrder());

        return posts;
    }

    /**
     * Функция для удаления публикации
     * @param post публикация
     */
    public void delete (Post post) {
        postRepository.delete(post);
    }
}
