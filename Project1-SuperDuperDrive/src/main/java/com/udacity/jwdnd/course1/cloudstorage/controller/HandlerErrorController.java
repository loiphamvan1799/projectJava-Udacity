package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@ControllerAdvice
public class HandlerErrorController implements ErrorController {

    @Override
    @RequestMapping("/error")
    public String getErrorPath() {
        return "error";
    }

    @ExceptionHandler(FileSizeLimitExceededException.class)
    public String handleMaxSizeException(FileSizeLimitExceededException exc, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("uploadError", "Tệp tải lên quá lớn!");
        return "redirect:/result?error";
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(MaxUploadSizeExceededException exc, RedirectAttributes redirectAttributes) {

        String uploadError = "Error when upload file! The file too large! Please try other";
        redirectAttributes.addFlashAttribute("error", uploadError);
        return "redirect:/result?error";
    }
}
