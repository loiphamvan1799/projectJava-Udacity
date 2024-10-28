package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/home/file")
public class FileController {

    @Autowired
    private final FileService fileService;

    @Autowired
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping
    public String insertFile(Authentication authentication,@RequestParam("fileUpload") MultipartFile multipartFile,
                             RedirectAttributes redirectAttributes) throws IOException {

        String uploadError = null;
        if (multipartFile.isEmpty()) {
            uploadError = "Please select a non-empty file.";
        }

        String fileName = multipartFile.getOriginalFilename();
        Integer userId = userService.getUserId(authentication).getUserId();
        List<File> filesOfUser = fileService.getAllFileByUser(userId);

        for (File file : filesOfUser) {
            if (file.getFileName().equals(fileName)) {
                uploadError = "The file already exists";
                break;
            }
        }

        if (uploadError != null) {
            redirectAttributes.addFlashAttribute("error", uploadError);
            return "redirect:/result?error";
        }

        fileService.insertFiles(multipartFile, userId);
        return "redirect:/result?success";
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<ByteArrayResource> dowloadFile(@PathVariable Integer fileId) {
        File file = fileService.getFileByUser(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ file.getFileName()+"\"")
                .body(new ByteArrayResource(file.getFileData()));
    }

    @GetMapping("/delete")
    public String deleteFile(@RequestParam Integer fileId, RedirectAttributes redirectAttributes) {

        String strError = null;
        int numError = fileService.deleteFile(fileId);
        if (numError == 0) {
            strError = "Have Error when delete file";
        }

        if(strError != null) {
            redirectAttributes.addAttribute("error", strError);
            return "redirect:/result?error";
        }

        return "redirect:/result?success";
    }
}
