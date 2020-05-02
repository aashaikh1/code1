package com.arif;

import com.amazonaws.regions.Regions;
import com.amazonaws.HttpMethod;
import java.net.URL;
import javax.ejb.Stateless;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import javax.annotation.PostConstruct;
import com.arif.car.jpa.CarStock;
import com.arif.car.jpa.Photo;
import java.util.List;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import java.io.ByteArrayInputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;
import javax.ejb.EJB;

/**
 *
 * @author arif
 */
@Stateless(name = "AwsS3BucketRepo", mappedName = "AwsS3BucketRepo")
public class AwsS3BucketRepo implements AwsS3BucketRepoLocal {

    private AmazonS3 s3Client; 
    private Regions clientRegion = Regions.US_WEST_2;
    private String bucketName = "arif-cars";
    
    @PostConstruct
    private void init(){
        s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(clientRegion)
                .build();
    }
    
    public String getObjectPreSignedUrl(String objectKey){

        try {

            // Pre Signed URL expires in 1 hour.
            java.util.Date expiration = new java.util.Date();
            long expTimeMillis = expiration.getTime();
            expTimeMillis += 1000 * 60 * 60;
            expiration.setTime(expTimeMillis);

            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, objectKey)
                            .withMethod(HttpMethod.GET)
                            .withExpiration(expiration);
            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

            return url.toString();
        } catch (Exception e) {
            throw new RuntimeException("Problem in AwsS3BucketRepo.getObjectPreSignedUrl", e);
        } 
    }
    
    public void updateListWithObjectPreSignedUrl(List<CarStock> cars){
        if(cars == null){ return; }
        for(CarStock car: cars){
            if(car.getPhotos() != null && !car.getPhotos().isEmpty()){ 
                for(Photo photo:car.getPhotos()){
                    String preSignedUrl = getObjectPreSignedUrl(photo.getObjectKey());
                    photo.setAwsPreSignedUrl(preSignedUrl);
                }
            }
        }
    }
    
    public void updateWithObjectPreSignedUrl(CarStock car){
        if(car.getPhotos() != null && !car.getPhotos().isEmpty()){ 
            for(Photo photo:car.getPhotos()){
                String preSignedUrl = getObjectPreSignedUrl(photo.getObjectKey());
                photo.setAwsPreSignedUrl(preSignedUrl);
            }
        }
    }

    public List<String> putPhotos(List<byte[]> photos){
        List<String> photoObjKeys = new ArrayList();
        for(byte[] photo:photos){
            String photoObjKey = UUID.randomUUID().toString();
            putPhoto(photo, photoObjKey);
            photoObjKeys.add(photoObjKey);
        }
        return photoObjKeys;
    }
    
    public void putPhoto(byte[] photo, String photoObjKey) {
        try {
            ByteArrayInputStream photoBAIS = new ByteArrayInputStream(photo);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/png");
            s3Client.putObject(new PutObjectRequest(bucketName, photoObjKey, photoBAIS, metadata));
        } catch (SdkClientException e) {
            throw new RuntimeException("Problem in AwsS3BucketRepo.putPhoto", e);
        }
    }    
    
    public void deletePhoto(String photoObjKey) {
        try {
            s3Client.deleteObject(new DeleteObjectRequest(bucketName, photoObjKey));
        } catch (SdkClientException e) {
            throw new RuntimeException("Problem in AwsS3BucketRepo.deletePhoto", e);
        }
    }    
}
