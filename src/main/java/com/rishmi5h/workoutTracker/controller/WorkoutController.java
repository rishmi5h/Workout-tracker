package com.rishmi5h.workoutTracker.controller;


import com.rishmi5h.workoutTracker.dto.WorkoutDTO;
import com.rishmi5h.workoutTracker.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @PostMapping
    public ResponseEntity<WorkoutDTO> createWorkout(@RequestBody WorkoutDTO workoutDTO) {
        return ResponseEntity.ok(workoutService.createWorkout(workoutDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutDTO> updateWorkout(@PathVariable Long id, @RequestBody WorkoutDTO workoutDTO) {
        return ResponseEntity.ok(workoutService.updateWorkout(id, workoutDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<WorkoutDTO>> listWorkouts() {
        return ResponseEntity.ok(workoutService.listWorkouts());
    }

    @GetMapping("/report")
    public ResponseEntity<String> generateReport() {
        return ResponseEntity.ok(workoutService.generateReport());
    }
}
