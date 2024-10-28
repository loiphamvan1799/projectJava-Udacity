package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    @Autowired
    private final CredentialMapper credentialMapper;

    @Autowired
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getCredential(Integer userid){
        return credentialMapper.getCredential(userid);
    }

    public void addCredential(Integer userId, Credential credential){

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        Credential newCredential = new Credential();
        newCredential.setUrl(credential.getUrl());
        newCredential.setUsername(credential.getUsername());
        newCredential.setKey(encodedKey);
        newCredential.setPassword(encryptedPassword);
        newCredential.setUserId(userId);
        credentialMapper.insertCredentilas(newCredential);
    }

    public void updateCredential(Credential credential) {

        Credential credentialUpdate = credentialMapper.getCredentialById(credential.getCredentialId());
        credentialUpdate.setUrl(credential.getUrl());
        credentialUpdate.setUsername(credential.getUsername());
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), credentialUpdate.getKey());
        credentialUpdate.setPassword(encryptedPassword);
        credentialMapper.updateCredential(credentialUpdate);
    }

    public void deleteCredential(Integer credentialId) {
        credentialMapper.deleteCredential(credentialId);
    }

}
