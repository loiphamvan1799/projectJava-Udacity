package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fileId;

    private String fileName;

    private String contentType;

    private String fileSize;

    private Integer userId;

    private byte[] fileData;
}
