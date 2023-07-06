package com.example.forum.controller;

import com.example.forum.model.ArchiveItem;
import com.example.forum.model.Message;
import com.example.forum.model.Role;
import com.example.forum.model.User;
import com.example.forum.service.AdminService;
import com.example.forum.service.ArchiveItemService;
import com.example.forum.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@PreAuthorize("hasAuthority('ADMIN')")
@Controller
public class AdminController {
    @Autowired
    AdminService adminService;
    @Autowired
    MessageService messageService;
    @Autowired
    ArchiveItemService archiveItemService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("adminPage")
    public String function1 (@AuthenticationPrincipal User user, Model model) {
        List<User> list = adminService.findAll();
        list.remove(user);
        model.addAttribute("users", list);
        return "adminPanel";
    }
    @GetMapping("/ban/{id}")
    public String function2 (@PathVariable("id") Long a) {
        User user = adminService.findById(a);
        if (user.isActive()) {
            user.setActive(false);
        }
        else {
            user.setActive(true);
        }
        adminService.save(user);

        return "redirect:/adminPage";
    }
    @GetMapping("/edit/{id}")
    public String function3 (@PathVariable("id") String a, Model model) {
        User user = adminService.findById(a);
        model.addAttribute("name", user.getUsername());
        model.addAttribute("psw", user.getPassword());
        model.addAttribute("role", user.getList());
        model.addAttribute("id", a);
        return "updateUser";
    }
    @PostMapping("updateUser")
    public String function4 (@RequestParam(name = "user", required = false) String us, @RequestParam(name = "admin", required = false) String admin  //required=false не обязательный параметр
            , @RequestParam(name = "name", required = false) String name
            , @RequestParam(name = "psw", required = false) String psw
            , @RequestParam(name = "id", required = false) String id
            , Model model) {
        User user = adminService.findById(id);
        List<Role> list = new ArrayList<>();
        if (us!=null) {
            list.add(Role.USER);
        }
        if (admin!=null) {
            list.add(Role.ADMIN);
        }
        if (list.size()==0) {
            list.addAll(user.getList());
        }
        user.setUsername(name);
        user.setPassword(passwordEncoder.encode(psw));
        user.setList(list);
        adminService.save(user);
        System.out.println(us);
        System.out.println(admin);
        return "redirect:/adminPage";
    }
    @GetMapping("/correctNews")
    public String getMessageForAdmin (Model model) {
        List<Message> listAll= archiveItemService.finalList();
        model.addAttribute("messages", listAll);
        return "correctNews";
    }
    @GetMapping("/delete/{id}")
    public String sendToArchive (@PathVariable ("id") Long a, Model model) {
        Message message = messageService.findById(a);
        ArchiveItem archiveItem = new ArchiveItem();
        archiveItem.setMessage(message);
        archiveItem.setDate(new Date());
        if (archiveItemService.existByMessage(message)) {
            return "redirect:/correctNews";
        }
        archiveItemService.save(archiveItem);
        return "redirect:/correctNews";
    }
    @GetMapping ("/archiveItem")
    public String getArchive (Model model) {
        List<ArchiveItem> list = archiveItemService.findAll();
        model.addAttribute("archives", list);
        return "archiveItem";
    }
    @GetMapping ("/restore/{id}")
    public String delFromArchive (@PathVariable ("id") Long a, Model model) {
        archiveItemService.deleteById(a);
        return "redirect:/archiveItem";
    }
}
