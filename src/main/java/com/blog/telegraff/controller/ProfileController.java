package com.blog.telegraff.controller;

import com.blog.telegraff.data.model.Attendance;
import com.blog.telegraff.data.model.User;
import com.blog.telegraff.data.service.AttendanceService;
import com.blog.telegraff.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Класс контроллера для работы с профилем пользователя
 * @author Danyil Smirnov
 */
@Controller
public class ProfileController {
    /** Сервис содержащий в себе бизнес логику для работы с сущность Attendance ({@link com.blog.telegraff.data.model.Attendance})*/
    @Autowired
    AttendanceService attendanceService;
    /** Сервис содержащий в себе бизнес логику для работы с сущность User ({@link com.blog.telegraff.data.model.User})*/
    @Autowired
    UserService userService;

    /**
     * Функция, обрабатывающая get запрос по адресу "/profile"
     * @param model параметр модели для работы с атрибутами
     * @return возвращает html страницу profile, если пользователь авторизирован, или переадресацию по адресу "/login", если не авторизирован
     */
    @GetMapping("/profile")
    public String profile (Model model) {
        if (HomeController.getStatus())
            return "redirect:" + "/login";
        User user = HomeController.getVerificationUser();
        model.addAttribute("user", user);

        String imagePath;
        if(!isEmpty(user.getImage())){
            imagePath = "images\\profile\\users\\" + user.getImage();
        } else
            imagePath = "images\\profile\\img.png";
        model.addAttribute("imagePath", imagePath);
        Iterable<Attendance> attendances = attendanceService.findByUserId(user.getId());
        model.addAttribute("attendances", attendances);
        model.addAttribute("size", attendanceService.findByUserId(user.getId()).size());
        return "profile";
    }

    /**
     * Функция, обрабатывающая get запрос по адресу "/profile/edit"
     * @param model параметр модели для работы с атрибутами
     * @return возвращает html страницу profile-edit, если пользователь авторизирован, или переадресацию по адресу "/login", если не авторизирован
     */
    @GetMapping("/profile/edit")
    public String profileEdit (Model model) {
        if (HomeController.getStatus())
            return "redirect:" + "/login";
        User user = HomeController.getVerificationUser();
        model.addAttribute("user", user);
        String imagePath;
        if(!isEmpty(user.getImage())){
            imagePath = "images\\profile\\users\\" + user.getImage();
        } else
            imagePath = "images\\profile\\img.png";
        model.addAttribute("imagePath", imagePath);
        return "profile-edit";
    }

    /**
     * Функция, обрабатывающая post запрос по адресу "/profile/edit"
     * @param name имя пользователя
     * @param surname фамилия пользователя
     * @param username логин пользователя
     * @param email почта пользователя
     * @param password пароль пользователя
     * @param file аватар (картинка) пользователя
     * @param redirectAttributes атрибут для взаимодействия с моделью страницы переадресации
     * @return возвращает переадресацию по адресу "/profile-edit", если поля заполнены не верно, или переадресацию по адресу "/profile" если поля заполнены верно
     */
    @PostMapping("/profile/edit")
    public String profileEditSave (@RequestParam String name, @RequestParam String surname,
                                   @RequestParam String username, @RequestParam String email,
                                   @RequestParam String password, @RequestBody MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        if (HomeController.getStatus())
            return "redirect:" + "/login";
        User user = HomeController.getVerificationUser();

        if (!isEmpty(name))
            user.setName(name);

        if (!isEmpty(surname))
            user.setSurname(surname);

        if (!isEmpty(username))
            if (!username.equals(user.getUsername()))
                if(username.length() <5) {
                    redirectAttributes.addAttribute("error", "Логин слишком короткий");
                    return "profile-edit";
                } else
                    try {
                        if (userService.findByUsername(username).getUsername().equals(username)) {
                            redirectAttributes.addAttribute("error", "Такой логин уже используется");
                            return "profile-edit";
                        } else
                            user.setUsername(username);
                    } catch (NullPointerException ex) {
                        user.setUsername(username);
                    }

        if (!isEmpty(email))
            if (!email.equals(user.getEmail()))
                try {
                    if(userService.findByEmail(email).getEmail().equals(email)) {
                        redirectAttributes.addAttribute("error", "Такая почта уже задействована");
                        return "profile-edit";
                    } else
                        user.setEmail(email);
                } catch (NullPointerException ex) {
                    user.setEmail(email);
                }
        boolean encrypt = false;
        if (!isEmpty(password))
            if(password.length() < 6){
                redirectAttributes.addAttribute("error", "Пароль слишком короткий");
                return "profile-edit";
            } else {
                user.setPassword(password);
                encrypt = true;
            }
        if (!file.isEmpty()) {
            //путь к проекту
            String dir = System.getProperty("user.dir");
            //путь к папке
            String saveLocation = dir + "\\src\\main\\resources\\static\\images\\profile\\users\\";
            String fileName = file.getOriginalFilename();
            File pathFile = new File(saveLocation);
            if (!pathFile.exists()) {
                pathFile.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + fileName;
            System.out.println(saveLocation);
            pathFile = new File(saveLocation + resultFileName);
            try {
                file.transferTo(pathFile);
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("error", "Произошла ошибка при добавлении фотографии.");
                return "redirect:/profile";
            }
            user.setImage(resultFileName);
        }
        userService.save(user, encrypt);
        System.out.println(user.toString());
        return "redirect:" + "/profile";
    }

    /**
     * Функция, которая проверяет заполнена ли строка
     * @param str строка
     * @return true - если строка заполнена, false - если не заполнена или равна null
     */
    private boolean isEmpty (String str) {
        return str == null || str.equals("");
    }

}
