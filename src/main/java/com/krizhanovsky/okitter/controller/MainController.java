package com.krizhanovsky.okitter.controller;

import com.krizhanovsky.okitter.entity.Message;
import com.krizhanovsky.okitter.entity.User;
import com.krizhanovsky.okitter.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class MainController {
    @Autowired
    private MessageRepository repository;

    @Value("${upload.path}")
    public String uploadPath;

    @GetMapping("/")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/home")
    public String getAllMessages(
            @RequestParam(required = false,defaultValue = "") String tag,
            Model model
            ){
        Iterable<Message> messages = repository.findAll();
        if(tag!=null && !tag.isEmpty()){
            messages = repository.findByTag(tag);
        }
        else{
            messages = repository.findAll();
        }
        model.addAttribute("messages",messages);
        model.addAttribute("filter", tag);
        return "home";
    }

    @PostMapping("/home")
    public String addMessage(
            @AuthenticationPrincipal User user,
            @ModelAttribute("message") @Valid Message message,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes
    ) throws IOException {
        message.setAuthor(user);
        if(bindingResult.hasErrors()){
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            //model.mergeAttributes(errorsMap);
            //model.addAttribute("message",message);
            redirectAttributes.addFlashAttribute("message",message);
            if(errorsMap!=null && !errorsMap.isEmpty())
            {
                errorsMap.forEach(redirectAttributes::addFlashAttribute);
            }
            //redirectAttributes.addFlashAttribute(errorsMap);
        }else {
            if (!file.isEmpty()) {
                File uploadDir = new File(uploadPath);

                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();

                file.transferTo(new File(uploadPath + "/" + resultFilename));

                message.setFilename(resultFilename);
            }
            repository.save(message);
        }
        Iterable<Message> messages = repository.findAll();
        model.addAttribute("messages",messages);
        return "redirect:/home";
    }
}

