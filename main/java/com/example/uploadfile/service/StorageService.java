package com.example.uploadfile.service;


import com.example.uploadfile.model.ImageData;
import com.example.uploadfile.repository.StorageRepository;
import com.example.uploadfile.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class StorageService {


    @Autowired
private StorageRepository storageRepository;

    public  String uploadImage(MultipartFile file) throws IOException {

   ImageData imageData   =  storageRepository.save(ImageData.builder()

                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtil.compressImage(file.getBytes())).build());



   if(imageData!=null){

       return "file uploaded successfully :"+file.getOriginalFilename();
   }
   return null;
    }


    public byte[] downloadImage(String fileName){
        Optional<ImageData> dbImageData = storageRepository.findByName(fileName);


      byte[] images= ImageUtil.decompressImage(dbImageData.get().getImageData());

         return images;

    }
}
