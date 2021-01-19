package com.blog.telegraff.controller;

import com.blog.telegraff.data.model.Post;
import com.blog.telegraff.data.service.PostService;
import com.blog.telegraff.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

/**
 * Класс контроллера для работы с публикациями
 * @author Danyil Smirnov
 */
@Controller
public class PostController {
    /** Сервис содержащий в себе бизнес логику для работы с сущность Post ({@link com.blog.telegraff.data.model.Post})*/
    @Autowired
    PostService postService;
    /** Сервис содержащий в себе бизнес логику для работы с сущность User ({@link com.blog.telegraff.data.model.User})*/
    @Autowired
    UserService userService;

    /**
     * Функция, обрабатывающая get запрос по адресу "/post/{id}"
     * @param model параметр модели для работы с атрибутами
     * @param id идентификатор публикации
     * @return возвращает html страницу post, если пользователь авторизирован, или переадресацию по адресу "/login", если не авторизирован или переадресацию по адресу "/", если такой публикации не существует
     */
    @GetMapping("/post/{id}")
    public String postInfo (Model model, @PathVariable(value = "id") Long id) {
        if (HomeController.getStatus())
            return "redirect:" + "/login";
        try {
            Optional<Post> resultPost = postService.findById(id);
            ArrayList<Post> posts = new ArrayList<>();
            resultPost.ifPresent(posts::add);
            model.addAttribute("posts", posts);

        } catch (NullPointerException ex){
            return "redirect:/";
        }
        return "post";
    }

    /**
     * Функция, обрабатывающая get запрос по адресу "/post/create"
     * @return возвращает html страницу post-create, если пользователь авторизирован, или переадресацию по адресу "/login", если не авторизирован
     */
    @GetMapping("/post/create")
    public String create () {
        if (HomeController.getStatus())
            return "redirect:" + "/login";
        return "post-create";
    }

    /**
     * Функция, обрабатывающая post запрос по адресу "/post/create"
     * @param title название публикации
     * @param fullText - полный текст публикации
     * @return возвращает переадресацию по адресу "/blog"
     */
    @PostMapping("/post/create")
    public String createPost (@RequestParam String title, @RequestParam String fullText) {
        postService.save(new Post(title, fullText, 0, HomeController.getVerificationUser().getId()));
        return "redirect:" + "/blog";
    }

    /**
     * Функция, обрабатывающая get запрос по адресу "/post/{id}/edit"
     * @param model параметр модели для работы с атрибутами
     * @param id идентификатор публикации
     * @return возвращает html страницу post-edit, если пользователь авторизирован, или переадресацию по адресу "/login", если не авторизирован, или переадресацию по адресу "/", если публикация не принадлежит данному пользователю или её не существует
     */
    @GetMapping("/post/{id}/edit")
    public String postEdit (Model model, @PathVariable(value = "id") Long id) {
        if (HomeController.getStatus())
            return "redirect:" + "/login";
        if (!Objects.equals(HomeController.getVerificationUser().getId(), postService.findById(id).orElseThrow().getUserId()))
            return "redirect:/";
        try {
            Optional<Post> resultPost = postService.findById(id);
            ArrayList<Post> posts = new ArrayList<>();
            resultPost.ifPresent(posts::add);
            model.addAttribute("posts", posts);
        } catch (NullPointerException ex){
            return "redirect:/";
        }
        return "post-edit";
    }

    /**
     * Функция, обрабатывающая post запрос по адресу "/post/{id}/edit"
     * @param id идентификатор публикации
     * @param title название публикации
     * @param fullText - полный текст публикации
     * @return возвращает переадресацию по адресу "/post/{id}"
     */
    @PostMapping("/post/{id}/edit")
    public String postUpdate (@PathVariable(value = "id") Long id, @RequestParam String title, @RequestParam String fullText) {
        Post post = postService.findById(id).orElseThrow();
        post.setTitle(title);
        post.setFullText(fullText);
        postService.save(post);
        return "redirect:" + "/post/{id}";
    }

    /**
     * Функция, обрабатывающая post запрос по адресу "/post/{id}/remove"
     * @param id идентификатор публикации
     * @return возвращает переадресацию по адресу "/blog", если пользователь авторизирован, или переадресацию по адресу "/login", если пользователь не авторизирован, или или переадресацию по адресу "/", если публикация не принадлежит данному пользователю
     */
    @PostMapping("/post/{id}/remove")
    public String postDelete (@PathVariable(value = "id") Long id) {
        if (HomeController.getStatus())
            return "redirect:" + "/login";
        if (!Objects.equals(HomeController.getVerificationUser().getId(), postService.findById(id).orElseThrow().getUserId()))
            return "redirect:/";
        Post post = postService.findById(id).orElseThrow();
        postService.delete(post);
        return "redirect:" + "/blog";
    }
}
