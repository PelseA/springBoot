package ru.pelse.myjira.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pelse.myjira.entity.Activity;
import ru.pelse.myjira.entity.Image;
import ru.pelse.myjira.repository.ImageRepository;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    public Image saveImage(String filename, Activity activity) {
        Image image = new Image(filename, activity);
        return imageRepository.save(image);
    }
}
