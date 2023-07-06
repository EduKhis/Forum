package com.example.forum.controller;

import com.example.forum.model.ArchiveItem;
import com.example.forum.model.Message;
import com.example.forum.model.Teg;
import com.example.forum.model.User;
import com.example.forum.repository.MessageRepository;
import com.example.forum.repository.UserRepository;
import com.example.forum.service.ArchiveItemService;
import com.example.forum.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.*;


@Controller
public class MainController {
    @Autowired
    MessageService messageService;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    ArchiveItemService archiveItemService;
    @Value("${upload.path}")
    private String file;
    @GetMapping("newForum")
    public String function1 (Model model, @AuthenticationPrincipal User user, @RequestParam (name = "sort", required = false) String a) {
        model.addAttribute("name", user.getUsername());
        model.addAttribute("options", Teg.values());
        List<Message> listAll= archiveItemService.finalList();
        if (a!=null) {
            listAll = archiveItemService.sortList(a, listAll);
        }
        model.addAttribute("messages", listAll);
        model.addAttribute("response","Default");

        return "forumm";
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/createMessage")
    public String function2 (@AuthenticationPrincipal User user, @RequestParam(name = "teg") String teg, @RequestParam(name = "description") String desc,
                             @RequestParam(name = "filename") MultipartFile multipartFile, Model model) throws IOException {
        model.addAttribute("name", user.getUsername());
        model.addAttribute("options", Teg.values());
        List<Message> list= messageService.findAll();
        model.addAttribute("messages", list);
        Message message = new Message();
        message.setTeg(Teg.valueOf(teg));
        if (!desc.isEmpty()) {
            message.setDescription(desc);
            model.addAttribute("response","OK");
        }
        else {
            model.addAttribute("response","");
            return "forumm";
        }
        message.setDate(new Date());
        message.setUser(user);

        if (!(Objects.requireNonNull(multipartFile.getOriginalFilename()).isEmpty())) {
            String orgName = UUID.randomUUID().toString().substring(10)+ "." + multipartFile.getOriginalFilename();
            String filePath = file +"\\"+ orgName;
            System.out.println(filePath);
            File dest = new File(filePath);
            multipartFile.transferTo(dest);
            message.setFilename(orgName);
        }
        else {
            message.setFilename("ccc.PNG");
        }
        messageService.save(message);
        return "redirect:/newForum";
    }
    @PostMapping("/filter")
    public String function3 (@AuthenticationPrincipal User user, @RequestParam(name = "teg") String teg, @RequestParam(name = "first" , required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date first
            , @RequestParam(name = "last", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date last,  Model model) throws IOException {

        model.addAttribute("name", user.getUsername());
        model.addAttribute("options", Teg.values());
        List<Message> list = new ArrayList<>();
        if (last==null && first==null && teg.equals("")) {
            list=messageService.findAll();
        }
        else if (last==null && first==null) {
            list = messageRepository.findByTeg(Teg.valueOf(teg));
        }
        else if (teg.equals("")) {
            if (last==null) {
                list = messageRepository.findByDateAfter(first);
            }
            else if (first==null) {
                list = messageRepository.findByDateBefore(last);
            }
            else {
                list = messageRepository.findByDateBetween(first, last);
            }
        }
        else if(last==null){
            list = messageRepository.findByTegAndDateAfter(Teg.valueOf(teg), first);
        }
        else if(first==null){
            list = messageRepository.findByTegAndDateBefore(Teg.valueOf(teg), last);
        }
        else {
            list = messageRepository.findByTegAndDateBetween(Teg.valueOf(teg), first, last);
        }
        model.addAttribute("messages", list);
        model.addAttribute("response","Default");
        return "forumm";

    }

}
