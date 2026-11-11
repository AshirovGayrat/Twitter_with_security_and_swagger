package uz.pdp.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
public class HomeController {


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/profile")
    public ResponseEntity<String> profile() {
        return ResponseEntity.ok("My Profile");
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-user")
    public ResponseEntity<String> deleteUser() {
        return ResponseEntity.ok("User deleted");
    }

}
