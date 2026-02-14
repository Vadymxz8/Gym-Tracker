package com.vadim.tkach.gym_tracker.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }
}

//@RestController
//public class HealthController {
//
//    @GetMapping("/api/health")
//    public ResponseEntity<String> health(@RequestParam(required = false) Integer number) {
//       if(number == null){
//           return new ResponseEntity<>("You must enter a numbe", HttpStatus.BAD_REQUEST);
//       }
//
//        if (number == 1) {
//            String message = "server is up and running";
//            return new ResponseEntity<>(message, HttpStatus.OK);
//        } else {
//            String message = "Ups!";
//            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
//        }
//    }
//
//
//    @GetMapping("/health/{id}")
//    public ResponseEntity<String> health(@PathVariable String id){
//        return new ResponseEntity<>("Gesundheit: " + id, HttpStatus.OK);
//    }
//}


