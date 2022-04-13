package com.krizhanovsky.okitter.controller;

import com.krizhanovsky.okitter.entity.User;
import com.krizhanovsky.okitter.service.ReCaptchaRegisterService;
import com.krizhanovsky.okitter.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {

    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);

    private final ReCaptchaRegisterService reCaptchaRegisterService;

    /*@Autowired*/
    private final UserService userService;

    @Autowired
    public RegistrationController(ReCaptchaRegisterService reCaptchaRegisterService,UserService userService) {
        this.reCaptchaRegisterService = reCaptchaRegisterService;
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("password2") String confirmPass,
                            @RequestParam("g-recaptcha-response") String response,
                            @Valid User user,
                          BindingResult bindingResult,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        reCaptchaRegisterService.verify(response);

        boolean isConfirmEmpty = confirmPass.isEmpty();

        if(isConfirmEmpty){
            model.addAttribute("password2Error","Поле не может быть пустым");
        }
        if (isConfirmEmpty || bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            return "registration";
        }
        if (user.getPassword() != null && !user.getPassword().equals(confirmPass)) {
            model.addAttribute("passwordError", "Пароли не совпадают");
            model.addAttribute("password2Error", "Пароли не совпадают");
            return "registration";
        }
        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User exists!");
            return "registration";
        }
        redirectAttributes.addFlashAttribute("messageType","success");
        redirectAttributes.addFlashAttribute("message","Пользователь успешно зарегестрирован," +
                " на почту отправлен ключ активации");
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("messageType","success");
            model.addAttribute("message", "Пользователь успешно активирован!");
        } else {
            model.addAttribute("messageType","danger");
            model.addAttribute("message", "Код активации не найден!");
        }
        return "login";
    }
}
