package com.example.feedback;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepo extends JpaRepository<Feedback, Long> {
    Feedback findByUserId(Long userId);
}
