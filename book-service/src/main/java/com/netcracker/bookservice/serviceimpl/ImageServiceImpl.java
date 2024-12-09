package com.netcracker.bookservice.serviceimpl;

import com.netcracker.bookservice.entity.Book;
import com.netcracker.bookservice.entity.Category;
import com.netcracker.bookservice.entity.Image;
import com.netcracker.bookservice.exceptions.ResourceNotFoundException;
import com.netcracker.bookservice.repository.BookRepository;
import com.netcracker.bookservice.repository.CategoryRepository;
import com.netcracker.bookservice.repository.ImageRepository;
import com.netcracker.bookservice.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Override
    public Image uploadImage(MultipartFile image) throws IOException {
        Image image1=new Image();
        image1.setImageData(image.getBytes());
        return imageRepository.save(image1);
    }

    @Override
    public InputStream serveImage(UUID imageId) {
        Image image=imageRepository.findById(imageId).orElseThrow(()->new ResourceNotFoundException("Image","Id",imageId));
        return new ByteArrayInputStream(image.getImageData());
    }


}
