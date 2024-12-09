package com.netcracker.bookservice.service;

import com.netcracker.bookservice.entity.Image;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public interface ImageService {

    Image uploadImage(MultipartFile image) throws IOException;

   InputStream serveImage(UUID imageId);
}
