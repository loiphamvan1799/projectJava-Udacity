package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/home/credential")
@Controller
public class CredentialController {

    @Autowired
    private final CredentialService credentialService;

    @Autowired
    private final UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping
    public String insertOrUpdateCredential(Authentication authentication, Credential credential) {

        Integer userId = userService.getUserId(authentication).getUserId();
        if (null != credential.getCredentialId()) {
            credentialService.updateCredential(credential);
        } else {
            credentialService.addCredential(userId, credential);
        }
        return "redirect:/result?success";
    }

    @GetMapping("/delete")
    public String deleteCredential(@RequestParam("credentialId") Integer credentialId) {

        if (null != credentialId) {
            credentialService.deleteCredential(credentialId);
            return "redirect:/result?success";
        }
        return "redirect:/result?error";
    }
}
