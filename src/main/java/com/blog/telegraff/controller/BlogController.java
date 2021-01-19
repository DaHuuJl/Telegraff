package com.blog.telegraff.controller;

import com.blog.telegraff.data.model.Post;
import com.blog.telegraff.data.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Класс контроллера для отображения блога пользователя
 * @author Danyil Smirnov
 */
@Controller
public class BlogController {
    /** Сервис содержащий в себе бизнес логику для работы с сущность Post ({@link com.blog.telegraff.data.model.Post})*/
    @Autowired
    PostService postService;

    /**
     * Функция, обрабатывающая get запрос по адресу "/blog"
     * @param model параметр модели для работы с атрибутами
     * @return возвращает html страницу blog, если пользователь авторизирован или переадресацию по адресу "/login", если не авторизирован
     */
    @GetMapping("/blog")
    public String blog (Model model) {
        if (HomeController.getStatus())
            return "redirect:" + "/login";
        Iterable<Post> posts = postService.findAllByUserId(HomeController.getVerificationUser());
        model.addAttribute("posts", posts);
        return "blog";
    }
}
