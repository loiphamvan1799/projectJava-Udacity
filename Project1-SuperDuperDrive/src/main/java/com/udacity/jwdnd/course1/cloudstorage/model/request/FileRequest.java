package com.udacity.jwdnd.course1.cloudstorage.model.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileRequest {
    private MultipartFile file;
}
