package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Data;

import javax.persistence.*;

@Data
public class Credential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer credentialId;

    private String url;

    private String username;

    private String key;

    private String password;

    private Integer userId;
}
