package com.blog.telegraff.data.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Класс, описывающий сущность пользователя (User) из базы данных
 * @author Danyil Smirnov
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity(name = "usr")
public class User {
    /** Поле идентификатора пользователя, которое
     * является первичным ключом с автоматической генерацией значений */
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;
    /** Поле логина пользователя */
    private String username;
    /** Поле пароля пользователя */
    private String password;
    /** Поле почты пользователя */
    private String email;
    /** Поле имени пользователя */
    private String name;
    /** Поле фамилии пользователя */
    private String surname;
    /** Поле пути к изображению пользователя */
    private String image;

    /**
     * Конструктор с параметрами для создания нового пользователя
     * @param username логин пользователя
     * @param password пароль пользователя
     * @param email почта пользователя
     * @param name имя пользователя
     * @param surname фамилия пользователя
     */
    public User(String username, String password, String email, String name, String surname) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.surname = surname;
    }
}
