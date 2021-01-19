package com.blog.telegraff.controller;

import com.blog.telegraff.data.model.Attendance;
import com.blog.telegraff.data.service.AttendanceService;
import com.blog.telegraff.data.service.UserService;
import com.blog.telegraff.util.enums.VerificationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Класс контроллера для авторизации пользователя
 * @author Danyil Smirnov
 */
@Controller
public class LoginController {
    /** Сервис содержащий в себе бизнес логику для работы с сущность User ({@link com.blog.telegraff.data.model.User})*/
    @Autowired
    UserService userService;
    /** Сервис содержащий в себе бизнес логику для работы с сущность Attendance ({@link com.blog.telegraff.data.model.Attendance})*/
    @Autowired
    AttendanceService attendanceService;

    /**
     * Функция, обрабатывающая get запрос по адресу "/login"
     * @return возвращает html страницу login
     */
    @GetMapping("/login")
    public String login () {
        return "login";
    }

    /**
     * Функция, обрабатывающая post запрос по адресу "/login"
     * @param email почта пользователя
     * @param pass пароль пользователя
     * @param model параметр модели для работы с атрибутами
     * @return возвращает html страницу login, если логин и пароль пользователя не совпадают, или переадресацию по адресу "/", если логин и пароль пользователя совпадают
     */
    @PostMapping("/login")
    public String logining(@RequestParam String email, @RequestParam String pass, Model model) {
        if(userService.checkLogining(email, pass)){
            HomeController.setVerificationStatus(VerificationStatus.AUTHORIZED);
            HomeController.setVerificationUser(userService.findByEmail(email));

            SimpleDateFormat formatForDate = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
            String date = formatForDate.format(new Date());

            attendanceService.save(new Attendance(date, HomeController.getVerificationUser().getId()));

            return "redirect:/";
        }
        model.addAttribute("error", "Email or password is not correct");
        return "login";
    }
}
