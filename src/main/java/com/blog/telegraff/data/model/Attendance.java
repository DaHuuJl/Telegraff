package com.blog.telegraff.data.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Класс, описывающий сущность посещений (Attendance) из базы данных
 * Имплементирует интерфейс Comparable, для определения поля сортировки
 * @author Danyil Smirnov
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Attendance implements Comparable<Attendance>{
    /** Поле идентификатора посещений, которое
     * является первичным ключом с автоматической генерацией значений */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /** Поле даты посещения */
    private String date;
    /** Поле идентификатора пользователя */
    private Long userId;

    /**
     * Конструктор с параметрами для создания посещения
     * @param date дата посещения
     * @param userId идентификатор пользователя
     */
    public Attendance(String date, Long userId) {
        this.date = date;
        this.userId = userId;
    }

    /**
     * Функция, который сравнивает текущий объект с объектом переданным в качестве параметра по полю {@link Attendance#id}
     * @param attendance объект для сравнения
     * @return возвращает отрицательное число - если значение поля id больше, чем id объекта, который передаётся в параметре положительное - если наоборот и 0 - если равны
     */
    @Override
    public int compareTo(Attendance attendance) {
        return this.id.compareTo(attendance.id);
    }
}
