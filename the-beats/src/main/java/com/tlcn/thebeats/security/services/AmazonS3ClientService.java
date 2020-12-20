package com.tlcn.thebeats.security.services;

import java.io.ByteArrayOutputStream;

import org.springframework.web.multipart.MultipartFile;

public interface AmazonS3ClientService
{
    String uploadFileToS3Bucket(MultipartFile multipartFile, boolean enablePublicReadAccess);
    String uploadImageToS3Bucket(MultipartFile multipartFile, boolean enablePublicReadAccess);
    public ByteArrayOutputStream downloadFile(String keyName);
    void deleteFileFromS3Bucket(String fileName);
}