package com.rishmi5h.workoutTracker.controller;

import com.rishmi5h.workoutTracker.dto.WorkoutDTO;
import com.rishmi5h.workoutTracker.service.WorkoutService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkoutControllerTest {

    @Mock
    private WorkoutService workoutService;

    @InjectMocks
    private WorkoutController workoutController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createWorkout() {
        WorkoutDTO inputDto = new WorkoutDTO();
        WorkoutDTO outputDto = new WorkoutDTO();
        outputDto.setId(1L);

        when(workoutService.createWorkout(inputDto)).thenReturn(outputDto);

        ResponseEntity<WorkoutDTO> response = workoutController.createWorkout(inputDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(outputDto, response.getBody());
    }

    @Test
    void updateWorkout() {
        Long workoutId = 1L;
        WorkoutDTO inputDto = new WorkoutDTO();
        WorkoutDTO outputDto = new WorkoutDTO();
        outputDto.setId(workoutId);

        when(workoutService.updateWorkout(workoutId, inputDto)).thenReturn(outputDto);

        ResponseEntity<WorkoutDTO> response = workoutController.updateWorkout(workoutId, inputDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(outputDto, response.getBody());
    }

    @Test
    void deleteWorkout() {
        Long workoutId = 1L;

        ResponseEntity<Void> response = workoutController.deleteWorkout(workoutId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(workoutService).deleteWorkout(workoutId);
    }

    @Test
    void listWorkouts() {
        List<WorkoutDTO> workouts = Arrays.asList(new WorkoutDTO(), new WorkoutDTO());

        when(workoutService.listWorkouts()).thenReturn(workouts);

        ResponseEntity<List<WorkoutDTO>> response = workoutController.listWorkouts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(workouts, response.getBody());
    }

    @Test
    void generateReport() {
        String report = "Workout Report";

        when(workoutService.generateReport()).thenReturn(report);

        ResponseEntity<String> response = workoutController.generateReport();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(report, response.getBody());
    }
}
