package com.rishmi5h.workoutTracker.service;

import com.rishmi5h.workoutTracker.dto.WorkoutDTO;
import com.rishmi5h.workoutTracker.entity.User;
import com.rishmi5h.workoutTracker.entity.Workout;
import com.rishmi5h.workoutTracker.repository.UserRepository;
import com.rishmi5h.workoutTracker.repository.WorkoutRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkoutServiceTest {

    @Mock
    private WorkoutRepository workoutRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private WorkoutService workoutService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock SecurityContextHolder
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("testUser");

        User user = new User();
        user.setId(1);
        user.setUsername("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
    }

    @Test
    void createWorkout() {
        WorkoutDTO inputDto = new WorkoutDTO();
        inputDto.setWorkoutTitle("Test Workout");
        inputDto.setScheduledDateTime(LocalDateTime.now());

        Workout savedWorkout = new Workout();
        savedWorkout.setId(1L);
        savedWorkout.setWorkoutTitle("Test Workout");

        when(workoutRepository.save(any(Workout.class))).thenReturn(savedWorkout);

        WorkoutDTO result = workoutService.createWorkout(inputDto);

        assertNotNull(result);
        assertEquals("Test Workout", result.getWorkoutTitle());
        assertEquals(1L, result.getId());
        verify(workoutRepository, times(1)).save(any(Workout.class));
    }

    @Test
    void updateWorkout() {
        Long workoutId = 1L;
        WorkoutDTO inputDto = new WorkoutDTO();
        inputDto.setWorkoutTitle("Updated Workout");

        Workout existingWorkout = new Workout();
        existingWorkout.setId(workoutId);
        existingWorkout.setWorkoutTitle("Original Workout");

        when(workoutRepository.findById(workoutId)).thenReturn(Optional.of(existingWorkout));
        when(workoutRepository.save(any(Workout.class))).thenReturn(existingWorkout);

        WorkoutDTO result = workoutService.updateWorkout(workoutId, inputDto);

        assertNotNull(result);
        assertEquals("Updated Workout", result.getWorkoutTitle());
        verify(workoutRepository, times(1)).findById(workoutId);
        verify(workoutRepository, times(1)).save(any(Workout.class));
    }

    @Test
    void deleteWorkout() {
        Long workoutId = 1L;
        workoutService.deleteWorkout(workoutId);
        verify(workoutRepository, times(1)).deleteById(workoutId);
    }

    @Test
    void listWorkouts() {
        User user = new User();
        user.setId(1);
        user.setUsername("testUser");

        Workout workout1 = new Workout();
        workout1.setId(1L);
        workout1.setWorkoutTitle("Workout 1");

        Workout workout2 = new Workout();
        workout2.setId(2L);
        workout2.setWorkoutTitle("Workout 2");

        List<Workout> workouts = Arrays.asList(workout1, workout2);

        when(workoutRepository.findByUserOrderByScheduledDateTimeAsc(user)).thenReturn(workouts);

        List<WorkoutDTO> result = workoutService.listWorkouts();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Workout 1", result.get(0).getWorkoutTitle());
        assertEquals("Workout 2", result.get(1).getWorkoutTitle());
        verify(workoutRepository, times(1)).findByUserOrderByScheduledDateTimeAsc(any(User.class));
    }

    @Test
    void generateReport() {
        User user = new User();
        user.setId(1);
        user.setUsername("testUser");

        Workout workout = new Workout();
        workout.setId(1L);
        workout.setWorkoutTitle("Test Workout");
        workout.setWorkoutType("Strength");
        workout.setWorkoutDuration("60 minutes");
        workout.setScheduledDateTime(LocalDateTime.now());

        List<Workout> workouts = Arrays.asList(workout);

        when(workoutRepository.findByUserAndIsCompletedTrue(user)).thenReturn(workouts);

        String report = workoutService.generateReport();

        assertNotNull(report);
        assertTrue(report.contains("Workout Report for testUser"));
        assertTrue(report.contains("Test Workout"));
        assertTrue(report.contains("Strength"));
        assertTrue(report.contains("60 minutes"));
        verify(workoutRepository, times(1)).findByUserAndIsCompletedTrue(any(User.class));
    }
}
