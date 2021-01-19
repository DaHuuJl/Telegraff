package com.blog.telegraff.data.service;

import com.blog.telegraff.data.model.User;
import com.blog.telegraff.data.repository.UserRepository;
import com.blog.telegraff.util.Encryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Сервис для работы с таблицей сущности User ({@link com.blog.telegraff.data.model.User})
 * через репозиторий UserRepository ({@link com.blog.telegraff.data.repository.UserRepository})
 * @author Danyil Smirnov
 */
@Service
public class UserService {
    /** Репозиторий для работы с таблицей сущности User ({@link com.blog.telegraff.data.model.User}) базы данных  */
    @Autowired
    UserRepository userRepository;
    /** для хэширования паролей */
    Encryptor encryptor = new Encryptor();

    /**
     * Функция для сохранения нового пользователя
     * @param user пользователь
     * @param encrypt переменная определения нужно ли хэширование
     */
    public void save (User user, boolean encrypt) {
        if (encrypt)
            user.setPassword(encryptor.encryption(user.getPassword()));
        userRepository.save(user);
    }

    /**
     * Функция для проверки данных нового пользователя
     * @param username логин пользователя
     * @param email почта пользователя
     * @return true - если такой пользователь найден, false - если такой пользователь не найден
     */
    public boolean checkRegistration (String username, String email) {
        return userRepository.findByEmail(email) != null || userRepository.findByUsername(username) != null;
    }

    /**
     * Функция для проверки почты и пароля пользователя
     * @param email почта пользователя
     * @param password пароль пользователя
     * @return true - если такой пользователь, false -  если такой пользователь не найден
     */
    public boolean checkLogining (String email, String password) {
        try {
            return userRepository.findByEmail(email).getEmail().equals(email) &&
                    userRepository.findByEmail(email).getPassword().equals(encryptor.encryption(password));
        } catch (NullPointerException ex) {
            return false;
        }
    }

    /**
     * Функция для поиска пользователя по почте
     * @param email почта пользователя
     * @return - пользователь
     */
    public User findByEmail (String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Функция для поиска пользователя по логину
     * @param username логин пользователя
     * @return найденный пользователь
     */
    public User findByUsername (String username) {
        return userRepository.findByUsername(username);
    }

}
