package com.blog.telegraff.controller;

import com.blog.telegraff.data.model.Attendance;
import com.blog.telegraff.data.model.User;
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
 * Класс контроллера для регистрации пользователя
 * @author Danyil Smirnov
 */
@Controller
public class RegController {
    @Autowired
    UserService userService;
    @Autowired
    AttendanceService attendanceService;

    /**
     * Функция, обрабатывающая get запрос по адресу "/reg"
     * @return возвращает html страницу reg
     */
    @GetMapping("/reg")
    public String reg () {
        return "reg";
    }

    /**
     * Функция, обрабатывающая post запрос по адресу "/reg"
     * @param name имя пользователя
     * @param surname фамилия пользователя
     * @param username логин пользователя
     * @param email почта пользователя
     * @param pass пароль пользователя
     * @param model параметр модели для работы с атрибутами
     * @return возвращает переадресацию по адресу "/", если поля заполнены верно, или html reg страницу, если поля заполнены не верно
     */
    @PostMapping("/reg")
    public String registration (@RequestParam String name, @RequestParam String surname, @RequestParam String email,
                                @RequestParam String username, @RequestParam String pass, Model model) {
        if (username.length() < 5) {
            model.addAttribute("error", "login must contain at least 5 characters!");
            return "reg";
        }
        if (pass.length() < 6) {
            model.addAttribute("error", "password must contain at least 6 characters!");
            return "reg";
        }
        if(!userService.checkRegistration(username, email)) {
            userService.save(new User(username, pass, email, name, surname), true);
            HomeController.setVerificationStatus(VerificationStatus.AUTHORIZED);
            HomeController.setVerificationUser(userService.findByEmail(email));

            SimpleDateFormat formatForDate = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
            String date = formatForDate.format(new Date());

            attendanceService.save(new Attendance(date, HomeController.getVerificationUser().getId()));

            return "redirect:/";
        }
        model.addAttribute("error", "Email or login already in use");
        return "reg";
    }
}
