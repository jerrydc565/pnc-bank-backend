package com.signup.fnc_bank.controller;




import com.signup.fnc_bank.dto.*;
import com.signup.fnc_bank.model.User;  // ✅ Your User model
import com.signup.fnc_bank.repository.UserRepository;
import com.signup.fnc_bank.service.UserService;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")  // Allow React app
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest request) {

        System.out.println("=" +
                "" +
                "========= SIGNUP REQUEST RECEIVED ==========");
        System.out.println("Email: " + request.getEmail());

        SignupResponse response = userService.registerUser(request);

        System.out.println("Response: " + response.getMessage());
        System.out.println("===========================================");

        return ResponseEntity.ok(response);
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        System.out.println("========== LOGIN REQUEST RECEIVED ==========");
        System.out.println("Email: " + request.getEmail());

        LoginResponse response = userService.loginUser(request);

        System.out.println("Response: " + response.getMessage());
        System.out.println("===========================================");

        return ResponseEntity.ok(response);
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<ForgotPasswordResponse> forgotPassword(@RequestBody ForgotPasswordRequest request) {

        System.out.println("========== FORGOT PASSWORD REQUEST RECEIVED ==========");
        System.out.println("Email: " + request.getEmail());

        ForgotPasswordResponse response = userService.forgotPassword(request);

        System.out.println("Response: " + response.getMessage());
        System.out.println("===================================================");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/account/lookup/{accountNumber}")
    public ResponseEntity<?> lookupAccount(@PathVariable String accountNumber) {
        // Call your banking API or database here
        // Return account holder information
        return ResponseEntity.ok(Map.of(
                "success", true,
                "accountHolder", "First Last Name"
        ));
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable String email) {

        System.out.println("========== GET USER PROFILE REQUEST ==========");
        System.out.println("Email: " + email);

        UserProfileResponse response = userService.getUserProfile(email);

        System.out.println("Response: " + response.getMessage());
        System.out.println("=============================================");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/user/{userId}/account")
    public ResponseEntity<?> getUserAccount(@PathVariable Integer userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Map<String, Object> response = new HashMap<>();
            response.put("accountNumber", user.getAccountNumber());
            response.put("balance", user.getBalance());
            response.put("firstName", user.getFirstName());
            response.put("secondName", user.getSecondName());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // Test endpoint
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Spring Boot backend is working!");
    }
}