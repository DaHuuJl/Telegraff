package com.blog.telegraff.data.service;

import com.blog.telegraff.data.model.Attendance;
import com.blog.telegraff.data.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Сервис для работы с таблицей сущности Attendance ({@link com.blog.telegraff.data.model.Attendance})
 * через репозиторий AttendanceRepository ({@link com.blog.telegraff.data.repository.AttendanceRepository})
 * @author Danyil Smirnov
 */
@Service
public class AttendanceService {
    /** Репозиторий для работы с таблицей сущности Attendance ({@link com.blog.telegraff.data.model.Attendance}) базы данных */
    @Autowired
    AttendanceRepository attendanceRepository;

    /**
     * Функция для поиска посещений по идентификатору пользователя
     * @param userId идентификатор пользователя
     * @return список посещений
     */
    public ArrayList<Attendance> findByUserId (Long userId) {
        ArrayList<Attendance> attendances = attendanceRepository.findByUserId(userId);
        attendances.sort(Collections.reverseOrder());
        return attendances;
    }

    /**
     * Функция для сохранения нового посещения
     * @param attendance посещение
     */
    public void save (Attendance attendance) {
        attendanceRepository.save(attendance);
    }
}
