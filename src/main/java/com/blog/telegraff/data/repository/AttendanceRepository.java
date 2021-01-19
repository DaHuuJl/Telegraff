package com.blog.telegraff.data.repository;

import com.blog.telegraff.data.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Репозиторий для работы с таблицей сущности Attendance ({@link com.blog.telegraff.data.model.Attendance}) базы данных
 * @author Danyil Smirnov
 */
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    ArrayList<Attendance> findByUserId(Long userId);

}
