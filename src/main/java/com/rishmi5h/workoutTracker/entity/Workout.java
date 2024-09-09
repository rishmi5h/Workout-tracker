package com.rishmi5h.workoutTracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_workout")
public class Workout {

    @Id
    @GeneratedValue
    private Long id;
    private String workoutTitle;
    private String workoutNote;
    private String workoutType;
    private String workoutCategory;
    private String workoutDuration;
}
