import { useState } from 'react';
import { Plus, Trash2, Loader2, X } from 'lucide-react';

const WORKOUT_TYPES = ['Strength', 'Cardio', 'Flexibility', 'HIIT', 'Sports', 'Other'];
const WORKOUT_CATEGORIES = ['Upper Body', 'Lower Body', 'Full Body', 'Core', 'Back', 'Arms', 'Legs', 'Chest', 'Shoulders'];

const emptyExercise = { name: '', sets: '', reps: '', weight: '' };

export default function WorkoutForm({ onSubmit, onCancel, initial, loading }) {
  const [form, setForm] = useState(
    initial || {
      workoutTitle: '',
      workoutNote: '',
      workoutType: 'Strength',
      workoutCategory: 'Full Body',
      workoutDuration: '',
      scheduledDateTime: new Date().toISOString().slice(0, 16),
      isCompleted: false,
      exercises: [{ ...emptyExercise }],
    }
  );

  const updateField = (field, value) => setForm((f) => ({ ...f, [field]: value }));

  const updateExercise = (idx, field, value) => {
    setForm((f) => {
      const exercises = [...f.exercises];
      exercises[idx] = { ...exercises[idx], [field]: value };
      return { ...f, exercises };
    });
  };

  const addExercise = () => setForm((f) => ({ ...f, exercises: [...f.exercises, { ...emptyExercise }] }));

  const removeExercise = (idx) => {
    setForm((f) => ({
      ...f,
      exercises: f.exercises.filter((_, i) => i !== idx),
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const payload = {
      ...form,
      exercises: form.exercises
        .filter((ex) => ex.name.trim())
        .map((ex) => ({
          ...ex,
          sets: Number(ex.sets) || 0,
          reps: Number(ex.reps) || 0,
          weight: Number(ex.weight) || 0,
        })),
    };
    onSubmit(payload);
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-5">
      <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
        <div className="sm:col-span-2">
          <label className="block text-sm font-medium text-zinc-400 mb-1.5">Title</label>
          <input
            type="text"
            value={form.workoutTitle}
            onChange={(e) => updateField('workoutTitle', e.target.value)}
            required
            className="w-full px-3 py-2.5 rounded-lg bg-zinc-900 border border-zinc-800 text-zinc-100 placeholder-zinc-600 focus:outline-none focus:ring-2 focus:ring-emerald-500/40 focus:border-emerald-500/40 transition-colors"
            placeholder="e.g. Morning Push Day"
          />
        </div>

        <div>
          <label className="block text-sm font-medium text-zinc-400 mb-1.5">Type</label>
          <select
            value={form.workoutType}
            onChange={(e) => updateField('workoutType', e.target.value)}
            className="w-full px-3 py-2.5 rounded-lg bg-zinc-900 border border-zinc-800 text-zinc-100 focus:outline-none focus:ring-2 focus:ring-emerald-500/40 focus:border-emerald-500/40 transition-colors"
          >
            {WORKOUT_TYPES.map((t) => (
              <option key={t} value={t}>{t}</option>
            ))}
          </select>
        </div>

        <div>
          <label className="block text-sm font-medium text-zinc-400 mb-1.5">Category</label>
          <select
            value={form.workoutCategory}
            onChange={(e) => updateField('workoutCategory', e.target.value)}
            className="w-full px-3 py-2.5 rounded-lg bg-zinc-900 border border-zinc-800 text-zinc-100 focus:outline-none focus:ring-2 focus:ring-emerald-500/40 focus:border-emerald-500/40 transition-colors"
          >
            {WORKOUT_CATEGORIES.map((c) => (
              <option key={c} value={c}>{c}</option>
            ))}
          </select>
        </div>

        <div>
          <label className="block text-sm font-medium text-zinc-400 mb-1.5">Duration</label>
          <input
            type="text"
            value={form.workoutDuration}
            onChange={(e) => updateField('workoutDuration', e.target.value)}
            className="w-full px-3 py-2.5 rounded-lg bg-zinc-900 border border-zinc-800 text-zinc-100 placeholder-zinc-600 focus:outline-none focus:ring-2 focus:ring-emerald-500/40 focus:border-emerald-500/40 transition-colors"
            placeholder="e.g. 45 mins"
          />
        </div>

        <div>
          <label className="block text-sm font-medium text-zinc-400 mb-1.5">Scheduled Date & Time</label>
          <input
            type="datetime-local"
            value={form.scheduledDateTime}
            onChange={(e) => updateField('scheduledDateTime', e.target.value)}
            className="w-full px-3 py-2.5 rounded-lg bg-zinc-900 border border-zinc-800 text-zinc-100 focus:outline-none focus:ring-2 focus:ring-emerald-500/40 focus:border-emerald-500/40 transition-colors"
          />
        </div>

        <div className="sm:col-span-2">
          <label className="block text-sm font-medium text-zinc-400 mb-1.5">Notes</label>
          <textarea
            value={form.workoutNote}
            onChange={(e) => updateField('workoutNote', e.target.value)}
            rows={2}
            className="w-full px-3 py-2.5 rounded-lg bg-zinc-900 border border-zinc-800 text-zinc-100 placeholder-zinc-600 focus:outline-none focus:ring-2 focus:ring-emerald-500/40 focus:border-emerald-500/40 transition-colors resize-none"
            placeholder="Any notes for this workout..."
          />
        </div>

        <div className="sm:col-span-2 flex items-center gap-2">
          <input
            type="checkbox"
            id="isCompleted"
            checked={form.isCompleted}
            onChange={(e) => updateField('isCompleted', e.target.checked)}
            className="w-4 h-4 rounded bg-zinc-900 border-zinc-700 text-emerald-500 focus:ring-emerald-500/40 accent-emerald-500"
          />
          <label htmlFor="isCompleted" className="text-sm text-zinc-400">Mark as completed</label>
        </div>
      </div>

      <div>
        <div className="flex items-center justify-between mb-3">
          <h3 className="text-sm font-medium text-zinc-300">Exercises</h3>
          <button
            type="button"
            onClick={addExercise}
            className="text-xs text-emerald-400 hover:text-emerald-300 flex items-center gap-1 cursor-pointer"
          >
            <Plus className="w-3.5 h-3.5" /> Add exercise
          </button>
        </div>

        <div className="space-y-3">
          {form.exercises.map((ex, idx) => (
            <div key={idx} className="flex items-start gap-2 p-3 rounded-lg bg-zinc-900/50 border border-zinc-800/50">
              <div className="flex-1 grid grid-cols-2 sm:grid-cols-4 gap-2">
                <input
                  type="text"
                  value={ex.name}
                  onChange={(e) => updateExercise(idx, 'name', e.target.value)}
                  placeholder="Exercise name"
                  className="col-span-2 sm:col-span-1 px-2.5 py-2 rounded-md bg-zinc-800 border border-zinc-700 text-zinc-100 text-sm placeholder-zinc-600 focus:outline-none focus:ring-1 focus:ring-emerald-500/40 transition-colors"
                />
                <input
                  type="number"
                  value={ex.sets}
                  onChange={(e) => updateExercise(idx, 'sets', e.target.value)}
                  placeholder="Sets"
                  min="0"
                  className="px-2.5 py-2 rounded-md bg-zinc-800 border border-zinc-700 text-zinc-100 text-sm placeholder-zinc-600 focus:outline-none focus:ring-1 focus:ring-emerald-500/40 transition-colors"
                />
                <input
                  type="number"
                  value={ex.reps}
                  onChange={(e) => updateExercise(idx, 'reps', e.target.value)}
                  placeholder="Reps"
                  min="0"
                  className="px-2.5 py-2 rounded-md bg-zinc-800 border border-zinc-700 text-zinc-100 text-sm placeholder-zinc-600 focus:outline-none focus:ring-1 focus:ring-emerald-500/40 transition-colors"
                />
                <input
                  type="number"
                  value={ex.weight}
                  onChange={(e) => updateExercise(idx, 'weight', e.target.value)}
                  placeholder="Weight (kg)"
                  min="0"
                  step="0.5"
                  className="px-2.5 py-2 rounded-md bg-zinc-800 border border-zinc-700 text-zinc-100 text-sm placeholder-zinc-600 focus:outline-none focus:ring-1 focus:ring-emerald-500/40 transition-colors"
                />
              </div>
              {form.exercises.length > 1 && (
                <button
                  type="button"
                  onClick={() => removeExercise(idx)}
                  className="mt-1.5 p-1 text-zinc-600 hover:text-red-400 transition-colors cursor-pointer"
                >
                  <Trash2 className="w-4 h-4" />
                </button>
              )}
            </div>
          ))}
        </div>
      </div>

      <div className="flex items-center gap-3 pt-2">
        <button
          type="submit"
          disabled={loading}
          className="px-5 py-2.5 rounded-lg bg-emerald-600 hover:bg-emerald-500 text-white font-medium text-sm transition-colors disabled:opacity-50 disabled:cursor-not-allowed flex items-center gap-2 cursor-pointer"
        >
          {loading && <Loader2 className="w-4 h-4 animate-spin" />}
          {initial ? 'Update Workout' : 'Create Workout'}
        </button>
        <button
          type="button"
          onClick={onCancel}
          className="px-5 py-2.5 rounded-lg bg-zinc-800 hover:bg-zinc-700 text-zinc-300 font-medium text-sm transition-colors cursor-pointer"
        >
          Cancel
        </button>
      </div>
    </form>
  );
}
