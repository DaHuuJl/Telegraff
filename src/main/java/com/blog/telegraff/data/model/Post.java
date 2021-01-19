package com.blog.telegraff.data.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

/**
 * Класс, описывающий сущность публикации (Post) из базы данных
 * Имплементирует интерфейс Comparable, для определения поля сортировки
 * @author Danyil Smirnov
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Post implements Comparable<Post>{
    /** Поле идентификатора публикации, которое
     * является первичным ключом с автоматической генерацией значений */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /** Поле заглавия публикации */
    private String title;
    /** Поле текст публикации */
    @Column(length = 50000)
    private String fullText;
    /** Поле количества просмотров */
    @Column(length = 300)
    private Integer views;
    /** Поле идентификатора создателя публикации */
    private Long userId;
    /** Поле названия изображения */
    private String image;

    /**
     * Конструктор с параметрами для создания новой публикации
     * @param title заглавие публикации
     * @param fullText текст публикации
     * @param views количество просмотров публикации
     * @param userId идентификатор создателя публикации
     * @param image название изображения
     */
    public Post(String title, String fullText, int views, Long userId, String image) {
        this.title = title;
        this.fullText = fullText;
        this.views = views;
        this.userId = userId;
        this.image = image;
    }

    /**
     * Конструктор с параметрами для создания новой публикации
     * @param title заглавие публикации
     * @param fullText текст публикации
     * @param views количество просмотров публикации
     * @param userId идентификатор создателя публикации
     */
    public Post(String title, String fullText, int views, Long userId) {
        this.title = title;
        this.fullText = fullText;
        this.views = views;
        this.userId = userId;
    }

    /**
     * Функция, который сравнивает текущий объект с объектом переданным в качестве параметра по полю {@link Post#views}
     * @param post объект для сравнения
     * @return возвращает отрицательное число - если значение поля id больше, чем id объекта, который передаётся в параметре положительное - если наоборот и 0 - если равны
     */
    @Override
    public int compareTo(Post post) {
        return this.views.compareTo(post.views);
    }
}
