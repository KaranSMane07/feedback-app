package com.example.feedback;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final UserRepo users;
    private final FeedbackRepo feedbacks;

    public ApiController(UserRepo users, FeedbackRepo feedbacks) {
        this.users = users;
        this.feedbacks = feedbacks;
    }

    // ---------- auth ----------
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User u) {
        if (u.getUsername() == null || u.getUsername().isBlank() || u.getPassword() == null || u.getPassword().isBlank())
            return ResponseEntity.badRequest().body(Map.of("error", "Username and password required"));
        if (users.findByUsername(u.getUsername()) != null)
            return ResponseEntity.badRequest().body(Map.of("error", "Username already taken"));
        u.setRole("USER");
        users.save(u);
        return ResponseEntity.ok(Map.of("message", "Registered"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        User u = users.findByUsername(body.get("username"));
        if (u == null || !u.getPassword().equals(body.get("password")))
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        return ResponseEntity.ok(Map.of("id", u.getId(), "username", u.getUsername(), "role", u.getRole()));
    }

    // ---------- user: own feedback ----------
    @GetMapping("/my-feedback/{userId}")
    public ResponseEntity<?> myFeedback(@PathVariable Long userId) {
        Feedback f = feedbacks.findByUserId(userId);
        return f == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(f);
    }

    // add new, or update if the user already has one
    @PutMapping("/my-feedback/{userId}")
    public Feedback saveMyFeedback(@PathVariable Long userId, @RequestBody Feedback body) {
        Feedback f = feedbacks.findByUserId(userId);
        if (f == null) {
            f = new Feedback();
            f.setUserId(userId);
        }
        f.setText(body.getText());
        f.setDate(LocalDate.now());
        return feedbacks.save(f);
    }

    // ---------- admin: CRUD ----------
    @GetMapping("/feedback")
    public List<Feedback> all() { return feedbacks.findAll(); }

    @GetMapping("/feedback/{id}")
    public ResponseEntity<Feedback> one(@PathVariable Long id) {
        return feedbacks.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/feedback")
    public Feedback create(@RequestBody Feedback f) { return feedbacks.save(f); }

    @PutMapping("/feedback/{id}")
    public ResponseEntity<Feedback> update(@PathVariable Long id, @RequestBody Feedback body) {
        return feedbacks.findById(id).map(f -> {
            f.setText(body.getText());
            return ResponseEntity.ok(feedbacks.save(f));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/feedback/{id}")
    public void delete(@PathVariable Long id) { feedbacks.deleteById(id); }
}
