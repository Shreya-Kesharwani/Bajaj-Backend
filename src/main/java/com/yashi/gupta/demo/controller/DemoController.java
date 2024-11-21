package com.yashi.gupta.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/api")
public class DemoController {

    // GET endpoint (operation code)
    @GetMapping("/operation")
    public ResponseEntity<Map<String, Object>> getOperationCode() {
        Map<String, Object> response = new HashMap<>();
        response.put("operation_code", "GET_SUCCESS");
        return ResponseEntity.ok(response);
    }

    // POST endpoint (process the data)
    @PostMapping("/process")
    public ResponseEntity<Map<String, Object>> processRequest(
            @RequestParam String userId,
            @RequestParam String email,
            @RequestParam String rollNumber,
            @RequestParam String inputString,
            @RequestParam(required = false) MultipartFile file) {

        Map<String, Object> response = new HashMap<>();
        response.put("status", "Success");
        response.put("userId", userId);
        response.put("email", email);
        response.put("rollNumber", rollNumber);

        // Process inputString
        List<Character> numbers = new ArrayList<>();
        List<Character> alphabets = new ArrayList<>();
        Character highestLowercase = null;
        boolean primeFound = false;

        for (char ch : inputString.toCharArray()) {
            if (Character.isDigit(ch)) {
                numbers.add(ch);
                if (isPrime(Character.getNumericValue(ch))) {
                    primeFound = true;
                }
            } else if (Character.isLetter(ch)) {
                alphabets.add(ch);
                if (Character.isLowerCase(ch) && 
                        (highestLowercase == null || ch > highestLowercase)) {
                    highestLowercase = ch;
                }
            }
        }

        response.put("numbers", numbers);
        response.put("alphabets", alphabets);
        response.put("highestLowercase", highestLowercase);
        response.put("primeFound", primeFound);

        // File validation
        Map<String, Object> fileDetails = new HashMap<>();
        if (file != null && !file.isEmpty()) {
            fileDetails.put("valid", true);
            fileDetails.put("mimeType", file.getContentType());
            fileDetails.put("sizeInKB", file.getSize() / 1024);
        } else {
            fileDetails.put("valid", false);
        }
        response.put("fileDetails", fileDetails);

        return ResponseEntity.ok(response);
    }

    // Helper method to check if a number is prime
    private boolean isPrime(int num) {
        if (num <= 1) return false;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }
}

