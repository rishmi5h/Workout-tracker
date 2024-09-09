package com.rishmi5h.workoutTracker.service;

import com.rishmi5h.workoutTracker.dto.ExerciseDTO;
import com.rishmi5h.workoutTracker.dto.WorkoutDTO;
import com.rishmi5h.workoutTracker.entity.Exercise;
import com.rishmi5h.workoutTracker.entity.User;
import com.rishmi5h.workoutTracker.entity.Workout;
import com.rishmi5h.workoutTracker.repository.UserRepository;
import com.rishmi5h.workoutTracker.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public WorkoutDTO createWorkout(WorkoutDTO workoutDTO) {
        User user = getCurrentUser();
        Workout workout = mapToEntity(workoutDTO);
        workout.setUser(user);
        return mapToDTO(workoutRepository.save(workout));
    }

    public WorkoutDTO updateWorkout(Long id, WorkoutDTO workoutDTO) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Workout not found"));
        updateWorkoutFromDTO(workout, workoutDTO);
        return mapToDTO(workoutRepository.save(workout));
    }

    public void deleteWorkout(Long id) {
        workoutRepository.deleteById(id);
    }

    public List<WorkoutDTO> listWorkouts() {
        User user = getCurrentUser();
        return workoutRepository.findByUserOrderByScheduledDateTimeAsc(user).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public String generateReport() {
        User user = getCurrentUser();
        List<Workout> workouts = workoutRepository.findByUserAndIsCompletedTrue(user);
        
        StringBuilder report = new StringBuilder();
        report.append("Workout Report for ").append(user.getUsername()).append("\n\n");
        
        if (workouts.isEmpty()) {
            report.append("No completed workouts found.");
        } else {
            report.append("Total completed workouts: ").append(workouts.size()).append("\n\n");
            
            for (Workout workout : workouts) {
                report.append("Workout: ").append(workout.getWorkoutTitle()).append("\n");
                report.append("Date: ").append(workout.getScheduledDateTime()).append("\n");
                report.append("Type: ").append(workout.getWorkoutType()).append("\n");
                report.append("Duration: ").append(workout.getWorkoutDuration()).append("\n");
                report.append("Exercises:\n");
                
                for (Exercise exercise : workout.getExercises()) {
                    report.append("  - ").append(exercise.getName())
                          .append(": ").append(exercise.getSets())
                          .append(" sets, ").append(exercise.getReps())
                          .append(" reps, ").append(exercise.getWeight())
                          .append(" kg\n");
                }
                
                report.append("\n");
            }
        }
        
        return report.toString();
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No authenticated user found");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }

        throw new RuntimeException("Unexpected authentication principal type");
    }

    private Workout mapToEntity(WorkoutDTO dto) {
        Workout workout = new Workout();
        workout.setId(dto.getId());
        workout.setWorkoutTitle(dto.getWorkoutTitle());
        workout.setWorkoutNote(dto.getWorkoutNote());
        workout.setWorkoutType(dto.getWorkoutType());
        workout.setWorkoutCategory(dto.getWorkoutCategory());
        workout.setWorkoutDuration(dto.getWorkoutDuration());
        workout.setScheduledDateTime(dto.getScheduledDateTime());
        workout.setCompleted(dto.isCompleted());

        if (dto.getExercises() != null) {
            List<Exercise> exercises = dto.getExercises().stream()
                    .map(this::mapExerciseDtoToEntity)
                    .collect(Collectors.toList());
            workout.setExercises(exercises);
        }

        return workout;
    }

    private Exercise mapExerciseDtoToEntity(ExerciseDTO dto) {
        Exercise exercise = new Exercise();
        exercise.setId(dto.getId());
        exercise.setName(dto.getName());
        exercise.setSets(dto.getSets());
        exercise.setReps(dto.getReps());
        exercise.setWeight(dto.getWeight());
        return exercise;
    }

    private WorkoutDTO mapToDTO(Workout workout) {
        WorkoutDTO dto = new WorkoutDTO();
        dto.setId(workout.getId());
        dto.setWorkoutTitle(workout.getWorkoutTitle());
        dto.setWorkoutNote(workout.getWorkoutNote());
        dto.setWorkoutType(workout.getWorkoutType());
        dto.setWorkoutCategory(workout.getWorkoutCategory());
        dto.setWorkoutDuration(workout.getWorkoutDuration());
        dto.setScheduledDateTime(workout.getScheduledDateTime());
        dto.setCompleted(workout.isCompleted());

        if (workout.getExercises() != null) {
            List<ExerciseDTO> exerciseDTOs = workout.getExercises().stream()
                    .map(this::mapExerciseToDTO)
                    .collect(Collectors.toList());
            dto.setExercises(exerciseDTOs);
        }

        return dto;
    }

    private ExerciseDTO mapExerciseToDTO(Exercise exercise) {
        ExerciseDTO dto = new ExerciseDTO();
        dto.setId(exercise.getId());
        dto.setName(exercise.getName());
        dto.setSets(exercise.getSets());
        dto.setReps(exercise.getReps());
        dto.setWeight(exercise.getWeight());
        return dto;
    }

    private void updateWorkoutFromDTO(Workout workout, WorkoutDTO dto) {
        workout.setWorkoutTitle(dto.getWorkoutTitle());
        workout.setWorkoutNote(dto.getWorkoutNote());
        workout.setWorkoutType(dto.getWorkoutType());
        workout.setWorkoutCategory(dto.getWorkoutCategory());
        workout.setWorkoutDuration(dto.getWorkoutDuration());
        workout.setScheduledDateTime(dto.getScheduledDateTime());
        workout.setCompleted(dto.isCompleted());

        if (dto.getExercises() != null) {
            // Clear existing exercises
            workout.getExercises().clear();
            
            // Add new exercises
            List<Exercise> exercises = dto.getExercises().stream()
                    .map(this::mapExerciseDtoToEntity)
                    .collect(Collectors.toList());
            workout.getExercises().addAll(exercises);
            
            // Set the workout reference for each exercise
            exercises.forEach(exercise -> exercise.setWorkout(workout));
        }
    }
}
