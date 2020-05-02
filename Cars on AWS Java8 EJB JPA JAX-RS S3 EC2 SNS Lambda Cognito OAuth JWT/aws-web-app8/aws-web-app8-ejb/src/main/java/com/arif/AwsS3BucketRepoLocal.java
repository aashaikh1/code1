package com.arif;

import com.arif.car.jpa.CarStock;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author arif
 */
@Local
public interface AwsS3BucketRepoLocal {
    public String getObjectPreSignedUrl(String objectKey);
    public void updateListWithObjectPreSignedUrl(List<CarStock> cars);
    public void updateWithObjectPreSignedUrl(CarStock car);
    public List<String> putPhotos(List<byte[]> photos);
    public void putPhoto(byte[] photo, String photoObjKey);
    public void deletePhoto(String photoObjKey);
}
