import { useState, useEffect, useCallback } from 'react';
import { getWorkouts, createWorkout, updateWorkout, deleteWorkout } from '../api/workouts';
import WorkoutCard from '../components/WorkoutCard';
import WorkoutForm from '../components/WorkoutForm';
import { Plus, Loader2, Dumbbell } from 'lucide-react';

export default function Dashboard() {
  const [workouts, setWorkouts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [formLoading, setFormLoading] = useState(false);
  const [showForm, setShowForm] = useState(false);
  const [editing, setEditing] = useState(null);

  const fetchWorkouts = useCallback(async () => {
    try {
      const res = await getWorkouts();
      setWorkouts(res.data);
    } catch {
      // handled by interceptor
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchWorkouts();
  }, [fetchWorkouts]);

  const handleCreate = async (data) => {
    setFormLoading(true);
    try {
      await createWorkout(data);
      setShowForm(false);
      fetchWorkouts();
    } catch {
      // handled by interceptor
    } finally {
      setFormLoading(false);
    }
  };

  const handleUpdate = async (data) => {
    setFormLoading(true);
    try {
      await updateWorkout(editing.id, data);
      setEditing(null);
      setShowForm(false);
      fetchWorkouts();
    } catch {
      // handled by interceptor
    } finally {
      setFormLoading(false);
    }
  };

  const handleDelete = async (id) => {
    try {
      await deleteWorkout(id);
      setWorkouts((prev) => prev.filter((w) => w.id !== id));
    } catch {
      // handled by interceptor
    }
  };

  const handleEdit = (workout) => {
    setEditing(workout);
    setShowForm(true);
  };

  const handleCancel = () => {
    setShowForm(false);
    setEditing(null);
  };

  const prepareFormData = (workout) => ({
    ...workout,
    scheduledDateTime: workout.scheduledDateTime
      ? new Date(workout.scheduledDateTime).toISOString().slice(0, 16)
      : new Date().toISOString().slice(0, 16),
    exercises: workout.exercises?.length
      ? workout.exercises.map((ex) => ({
          ...ex,
          sets: String(ex.sets),
          reps: String(ex.reps),
          weight: String(ex.weight),
        }))
      : [{ name: '', sets: '', reps: '', weight: '' }],
  });

  if (loading) {
    return (
      <div className="flex items-center justify-center py-20">
        <Loader2 className="w-6 h-6 text-emerald-400 animate-spin" />
      </div>
    );
  }

  return (
    <div>
      <div className="flex items-center justify-between mb-6">
        <h1 className="text-xl font-bold text-zinc-100">Your Workouts</h1>
        {!showForm && (
          <button
            onClick={() => { setEditing(null); setShowForm(true); }}
            className="px-4 py-2 rounded-lg bg-emerald-600 hover:bg-emerald-500 text-white text-sm font-medium transition-colors flex items-center gap-1.5 cursor-pointer"
          >
            <Plus className="w-4 h-4" /> New Workout
          </button>
        )}
      </div>

      {showForm && (
        <div className="mb-8 p-6 rounded-xl border border-zinc-800 bg-zinc-900/40">
          <h2 className="text-lg font-semibold text-zinc-100 mb-4">
            {editing ? 'Edit Workout' : 'New Workout'}
          </h2>
          <WorkoutForm
            onSubmit={editing ? handleUpdate : handleCreate}
            onCancel={handleCancel}
            initial={editing ? prepareFormData(editing) : undefined}
            loading={formLoading}
          />
        </div>
      )}

      {workouts.length === 0 ? (
        <div className="text-center py-20">
          <div className="inline-flex items-center justify-center w-16 h-16 rounded-2xl bg-zinc-900 mb-4">
            <Dumbbell className="w-8 h-8 text-zinc-700" />
          </div>
          <h2 className="text-lg font-medium text-zinc-400">No workouts yet</h2>
          <p className="text-zinc-600 mt-1">Create your first workout to get started.</p>
        </div>
      ) : (
        <div className="grid gap-4 sm:grid-cols-2">
          {workouts.map((w) => (
            <WorkoutCard key={w.id} workout={w} onEdit={handleEdit} onDelete={handleDelete} />
          ))}
        </div>
      )}
    </div>
  );
}
