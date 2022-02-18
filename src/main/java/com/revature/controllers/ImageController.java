package com.revature.controllers;

import com.revature.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
@CrossOrigin
public class ImageController {

    @Autowired
    private ImageService service;

    @PostMapping("/add")
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file) {
        return ResponseEntity.ok()
                .header("Content-type", "application/json")
                .body("\"" + service.uploadFile(file) + "\"");
    }
}
