package com.blog.telegraff.controller;

import com.blog.telegraff.data.model.Post;
import com.blog.telegraff.data.model.User;
import com.blog.telegraff.data.service.PostService;
import com.blog.telegraff.util.enums.VerificationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Класс контроллера для главной страницы
 * @author Danyil Smirnov
 */
@Controller
public class HomeController {
    /** Сервис содержащий в себе бизнес логику для работы с сущность Post ({@link com.blog.telegraff.data.model.Post})*/
    @Autowired
    PostService postService;
    /** Переменная, хранящая в себе статус авторизации пользователя */
    private static VerificationStatus verificationStatus = VerificationStatus.NOT_AUTHORIZED;
    /** Переменная, хранящая в себе вошедшего пользователя */
    private static User user;

    /**
     * Функция, обрабатывающая get запрос по адресу "/"
     * @param model параметр модели для работы с атрибутами
     * @return возвращает html страницу home, если пользователь авторизирован, или переадресацию по адресу "/login", если не авторизирован
     */
    @GetMapping("/")
    public String home(Model model){
        if (HomeController.getStatus())
            return "redirect:" + "/login";
        Iterable<Post> posts = postService.findAllExceptByUserId(HomeController.getVerificationUser().getId());
        model.addAttribute("posts" , posts);
        return "home";
    }

    /**
     * Метод для установки статуса пользователя {@link HomeController#verificationStatus}
     * @param verificationStatus статус пользователя
     */
    public static void setVerificationStatus (VerificationStatus verificationStatus) {
        HomeController.verificationStatus = verificationStatus;
    }

    /**
     * Метод для установки пользователя {@link HomeController#user}
     * @param user авторизированный пользователь
     */
    public static void setVerificationUser (User user) {
        HomeController.user = user;
    }

    /**
     * Метод возвращающий статус пользователя {@link HomeController#verificationStatus}
     * @return статус пользователя
     */
    public static boolean getStatus () {
        return HomeController.verificationStatus == VerificationStatus.NOT_AUTHORIZED;
    }

    /**
     * Метод возвращающий авторизированного пользователя {@link HomeController#user}
     * @return авторизированный пользователь
     */
    public static User getVerificationUser() {
        return HomeController.user;
    }
}
