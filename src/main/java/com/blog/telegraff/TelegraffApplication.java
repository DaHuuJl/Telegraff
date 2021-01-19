package com.blog.telegraff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Класс, необходимый для запуска Spring приложения
 * @author Smirnov Danyil
 */
@SpringBootApplication
public class TelegraffApplication {

    /**
     * Главный метод, запускающий приложение
     * @param args - обязательные параметр
     */
    public static void main(String[] args) {
        SpringApplication.run(TelegraffApplication.class, args);
    }
}
