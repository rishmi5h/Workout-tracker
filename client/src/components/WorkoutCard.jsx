import { Pencil, Trash2, Clock, Dumbbell, CheckCircle2, Circle } from 'lucide-react';

export default function WorkoutCard({ workout, onEdit, onDelete }) {
  const date = new Date(workout.scheduledDateTime);
  const formatted = date.toLocaleDateString('en-US', {
    weekday: 'short',
    month: 'short',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
  });

  return (
    <div className="rounded-xl border border-zinc-800 bg-zinc-900/60 p-5 hover:border-zinc-700 transition-colors">
      <div className="flex items-start justify-between gap-3">
        <div className="flex-1 min-w-0">
          <div className="flex items-center gap-2 mb-1">
            {workout.isCompleted ? (
              <CheckCircle2 className="w-4 h-4 text-emerald-400 shrink-0" />
            ) : (
              <Circle className="w-4 h-4 text-zinc-600 shrink-0" />
            )}
            <h3 className="text-base font-semibold text-zinc-100 truncate">{workout.workoutTitle}</h3>
          </div>
          <div className="flex flex-wrap items-center gap-2 mt-2">
            <span className="inline-flex items-center px-2 py-0.5 rounded-md bg-emerald-500/10 text-emerald-400 text-xs font-medium">
              {workout.workoutType}
            </span>
            <span className="inline-flex items-center px-2 py-0.5 rounded-md bg-zinc-800 text-zinc-400 text-xs font-medium">
              {workout.workoutCategory}
            </span>
            {workout.workoutDuration && (
              <span className="inline-flex items-center gap-1 text-xs text-zinc-500">
                <Clock className="w-3 h-3" /> {workout.workoutDuration}
              </span>
            )}
          </div>
        </div>
        <div className="flex items-center gap-1 shrink-0">
          <button
            onClick={() => onEdit(workout)}
            className="p-2 rounded-lg text-zinc-500 hover:text-zinc-200 hover:bg-zinc-800 transition-colors cursor-pointer"
          >
            <Pencil className="w-4 h-4" />
          </button>
          <button
            onClick={() => onDelete(workout.id)}
            className="p-2 rounded-lg text-zinc-500 hover:text-red-400 hover:bg-zinc-800 transition-colors cursor-pointer"
          >
            <Trash2 className="w-4 h-4" />
          </button>
        </div>
      </div>

      {workout.workoutNote && (
        <p className="text-sm text-zinc-500 mt-3 line-clamp-2">{workout.workoutNote}</p>
      )}

      {workout.exercises?.length > 0 && (
        <div className="mt-4 space-y-1.5">
          {workout.exercises.map((ex, i) => (
            <div key={ex.id || i} className="flex items-center gap-2 text-sm">
              <Dumbbell className="w-3.5 h-3.5 text-zinc-600 shrink-0" />
              <span className="text-zinc-300">{ex.name}</span>
              <span className="text-zinc-600">-</span>
              <span className="text-zinc-500">
                {ex.sets}x{ex.reps} @ {ex.weight}kg
              </span>
            </div>
          ))}
        </div>
      )}

      <div className="mt-4 pt-3 border-t border-zinc-800/60">
        <span className="text-xs text-zinc-600">{formatted}</span>
      </div>
    </div>
  );
}
