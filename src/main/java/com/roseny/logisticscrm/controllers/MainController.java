package com.roseny.logisticscrm.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MainController {

    @GetMapping("/catalog")
    public ResponseEntity<?> getProducts(@RequestParam(required = false) Long category_id) {
        return ResponseEntity.ok("Some returned");
    }

}
