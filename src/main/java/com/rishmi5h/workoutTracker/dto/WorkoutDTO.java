package com.rishmi5h.workoutTracker.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class WorkoutDTO {
    private Long id;
    private String workoutTitle;
    private String workoutNote;
    private String workoutType;
    private String workoutCategory;
    private String workoutDuration;
    private List<ExerciseDTO> exercises;
    private LocalDateTime scheduledDateTime;
    private boolean isCompleted;
}
