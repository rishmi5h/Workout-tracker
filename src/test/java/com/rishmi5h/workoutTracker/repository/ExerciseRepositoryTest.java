package com.rishmi5h.workoutTracker.repository;

import com.rishmi5h.workoutTracker.entity.Exercise;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ExerciseRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Test
    void saveExercise() {
        Exercise exercise = new Exercise();
        exercise.setName("Push-ups");
        exercise.setSets(3);
        exercise.setReps(10);
        exercise.setWeight(0);

        Exercise savedExercise = exerciseRepository.save(exercise);

        assertNotNull(savedExercise.getId());
        assertEquals("Push-ups", savedExercise.getName());
        assertEquals(3, savedExercise.getSets());
        assertEquals(10, savedExercise.getReps());
        assertEquals(0, savedExercise.getWeight());
    }

    @Test
    void findExerciseById() {
        Exercise exercise = new Exercise();
        exercise.setName("Squats");
        exercise.setSets(4);
        exercise.setReps(12);
        exercise.setWeight(50);

        Long id = entityManager.persistAndGetId(exercise, Long.class);

        Exercise foundExercise = exerciseRepository.findById(id).orElse(null);

        assertNotNull(foundExercise);
        assertEquals("Squats", foundExercise.getName());
        assertEquals(4, foundExercise.getSets());
        assertEquals(12, foundExercise.getReps());
        assertEquals(50, foundExercise.getWeight());
    }

}
