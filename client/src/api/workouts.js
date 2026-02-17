import api from './axios';

export const getWorkouts = () => api.get('/api/workouts');

export const createWorkout = (workout) => api.post('/api/workouts', workout);

export const updateWorkout = (id, workout) => api.put(`/api/workouts/${id}`, workout);

export const deleteWorkout = (id) => api.delete(`/api/workouts/${id}`);

export const getReport = () => api.get('/api/workouts/report');
