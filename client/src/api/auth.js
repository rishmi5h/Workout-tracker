import api from './axios';

export const login = (username, password) =>
  api.post('/api/auth/login', { username, password });

export const register = (username, password) =>
  api.post('/api/auth/register', { username, password });
