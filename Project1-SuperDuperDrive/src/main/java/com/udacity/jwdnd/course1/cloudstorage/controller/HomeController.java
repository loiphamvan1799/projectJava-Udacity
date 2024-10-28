package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @Autowired
    private final FileService fileService;

    @Autowired
    private final NoteService noteService;

    @Autowired
    private final UserService userService;

    @Autowired
    private final CredentialService credentialService;

    @Autowired
    private final EncryptionService encryptionService;

    public HomeController(FileService fileService, NoteService noteService,
                          UserService userService, CredentialService credentialService, EncryptionService encryptionService) {
        this.fileService = fileService;
        this.noteService = noteService;
        this.userService = userService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/home")
    public String getHomePage(Authentication authentication, @ModelAttribute File file,
                              @ModelAttribute Note note, @ModelAttribute Credential credential,
                              Model model) {
        User user = userService.getUserId(authentication);
        model.addAttribute("allFiles", this.fileService.getAllFileByUser(user.getUserId()));
        model.addAttribute("allNotes", this.noteService.getAllNoteByUser(user.getUserId()));
        model.addAttribute("allCredential", this.credentialService.getCredential(user.getUserId()));
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }

    @GetMapping("/result")
    public String result(){
        return "result";
    }
}
