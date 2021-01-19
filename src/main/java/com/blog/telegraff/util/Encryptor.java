package com.blog.telegraff.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** Класс для хэширования паролей
 * @author Danyil Smirnov
 */
public class Encryptor {
    /** Класс для получения хэш-суммы текста */
    private MessageDigest digest;

    /**
     * Конструктор без параметров, котрой задаёт алгоритм хеширования
     */
    public Encryptor() {
        try {
            this.digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * Хеширование пароля
     * @param password - пароль пользователя
     * @return - хэш пароля
     */
    public String encryption(String password) {
        byte[] bytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes)
            builder.append(String.format("%02X", b));
        return builder.toString();
    }
}
