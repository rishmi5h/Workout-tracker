package com.rishmi5h.workoutTracker.repository;

import com.rishmi5h.workoutTracker.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}
