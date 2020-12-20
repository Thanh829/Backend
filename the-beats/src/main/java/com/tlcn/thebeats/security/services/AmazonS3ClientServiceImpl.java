package com.tlcn.thebeats.security.services;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.amazonaws.services.s3.model.S3Object;
@Component
public class AmazonS3ClientServiceImpl implements AmazonS3ClientService 
{
    private String awsS3AudioBucket;
    private String awsS3ImageBucket;
    private AmazonS3 amazonS3;
    private static final Logger logger = LoggerFactory.getLogger(AmazonS3ClientServiceImpl.class);

    @Autowired
    public AmazonS3ClientServiceImpl(Region awsRegion, AWSCredentialsProvider awsCredentialsProvider, String awsS3AudioBucket,
    		String awsS3ImageBucket) 
    {
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(awsRegion.getName()).build();
        this.awsS3AudioBucket = awsS3AudioBucket;
        this.awsS3ImageBucket= awsS3ImageBucket;
        
    }

    @Async
    public String uploadFileToS3Bucket(MultipartFile multipartFile, boolean enablePublicReadAccess) 
    {
        String fileName = multipartFile.getOriginalFilename();

        try {
            //creating the file in the server (temporarily)
            File file = new File(fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();

            PutObjectRequest putObjectRequest = new PutObjectRequest(this.awsS3AudioBucket, fileName, file);

            if (enablePublicReadAccess) {
                putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
            }
            this.amazonS3.putObject(putObjectRequest);
            
            //removing the file created in the server
            file.delete();
            return this.amazonS3.getUrl(this.awsS3AudioBucket, fileName).toString();
        } catch (IOException | AmazonServiceException ex) {
            logger.error("error [" + ex.getMessage() + "] occurred while uploading [" + fileName + "] ");
            return "error [" + ex.getMessage() + "] occurred while uploading [" + fileName + "] ";
        }
    }
    
    @Async
    public String uploadImageToS3Bucket(MultipartFile multipartFile, boolean enablePublicReadAccess) 
    {
        String fileName = multipartFile.getOriginalFilename();

        try {
            //creating the file in the server (temporarily)
            File file = new File(fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();

            PutObjectRequest putObjectRequest = new PutObjectRequest(this.awsS3ImageBucket, fileName, file);

            if (enablePublicReadAccess) {
                putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
            }
            this.amazonS3.putObject(putObjectRequest);
            
            //removing the file created in the server
            file.delete();
            return this.amazonS3.getUrl(this.awsS3ImageBucket, fileName).toString();
        } catch (IOException | AmazonServiceException ex) {
            logger.error("error [" + ex.getMessage() + "] occurred while uploading [" + fileName + "] ");
            return "error [" + ex.getMessage() + "] occurred while uploading [" + fileName + "] ";
        }
    }

    @Async
    public void deleteFileFromS3Bucket(String fileName) 
    {
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(awsS3AudioBucket, fileName));
        } catch (AmazonServiceException ex) {
            logger.error("error [" + ex.getMessage() + "] occurred while removing [" + fileName + "] ");
        }
    }
    @Override
    public ByteArrayOutputStream downloadFile(String keyName) {
      try {
              S3Object s3object = amazonS3.getObject(new GetObjectRequest(awsS3AudioBucket, keyName));
              
              InputStream is = s3object.getObjectContent();
              ByteArrayOutputStream baos = new ByteArrayOutputStream();
              int len;
              byte[] buffer = new byte[4096];
              while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                  baos.write(buffer, 0, len);
              }
              
              return baos;
      } catch (IOException ioe) {
        logger.error("IOException: " + ioe.getMessage());
          } catch (AmazonServiceException ase) {
            logger.info("sCaught an AmazonServiceException from GET requests, rejected reasons:");
        logger.info("Error Message:    " + ase.getMessage());
        logger.info("HTTP Status Code: " + ase.getStatusCode());
        logger.info("AWS Error Code:   " + ase.getErrorCode());
        logger.info("Error Type:       " + ase.getErrorType());
        logger.info("Request ID:       " + ase.getRequestId());
        throw ase;
          } catch (AmazonClientException ace) {
            logger.info("Caught an AmazonClientException: ");
              logger.info("Error Message: " + ace.getMessage());
              throw ace;
          }
      
      return null;
    }
}