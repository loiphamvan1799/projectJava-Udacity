package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class FileService {

    @Autowired
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<File> getAllFileByUser(Integer userId) {
        return fileMapper.getAllFileByUser(userId);
    }

    public File getFileByUser(Integer fileId) {
        return fileMapper.getFileByUser(fileId);
    }

    public void insertFiles(MultipartFile multipartFile, Integer userId) throws IOException {
        InputStream fis = multipartFile.getInputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = fis.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        byte[] fileData = buffer.toByteArray();

        String fileName = multipartFile.getOriginalFilename();
        String contentType = multipartFile.getContentType();
        String fileSize = String.valueOf(multipartFile.getSize());
        fileMapper.insert(new File(0, fileName, contentType, fileSize, userId, fileData));
    }

    public int deleteFile(Integer fileId) {

        File fileDelete = getFileByUser(fileId);
        if (fileDelete != null) {
            fileMapper.delete(fileId);
            return 1;
        }

        return 0;
    }
}
