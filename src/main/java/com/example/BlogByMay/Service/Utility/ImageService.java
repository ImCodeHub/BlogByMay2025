package com.example.BlogByMay.Service.Utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

@Service
public class ImageService {
    @Value("${file.upload.path}")
    private String uploadDir;  //it will have this path: /src/main/resources/profile-images

    public String saveImage(MultipartFile imageFile) throws IOException {
        if(imageFile.isEmpty()){
            throw new IOException("Empty file can not save");
        }
        //create unique name of the image file.
        String imageFileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();

        //creating path to save image
        Path directoryPath = Paths.get(uploadDir, imageFileName);

        // make sure that directory is existed.
        Files.createDirectories(directoryPath.getParent());

        //save the image in the directory.
        Files.copy(imageFile.getInputStream(),directoryPath, StandardCopyOption.REPLACE_EXISTING);

        //return the image name.
        return imageFileName;
    }

    //create image compress method



    //java 8 BASE64 encoder.
    public String getEncodedImage(String imageFileName) throws IOException{
        Path imagePath = Paths.get(uploadDir).resolve(imageFileName); // /src/main/resources/profile-images/imageFileName

        //read the image and change in bytes
        byte[] imageBytes = Files.readAllBytes(imagePath);

        //encode the bytes and return string
        String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
        //return the encodedImage String.
        return encodedImage;
    }


}
