import { Outlet, NavLink, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { Dumbbell, BarChart3, LogOut } from 'lucide-react';

export default function Layout() {
  const { logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <div className="min-h-screen bg-zinc-950 text-zinc-100">
      <nav className="border-b border-zinc-800 bg-zinc-900/80 backdrop-blur-sm sticky top-0 z-50">
        <div className="max-w-6xl mx-auto px-4 sm:px-6">
          <div className="flex items-center justify-between h-16">
            <div className="flex items-center gap-2">
              <Dumbbell className="w-6 h-6 text-emerald-400" />
              <span className="text-lg font-bold tracking-tight">Workout Tracker</span>
            </div>
            <div className="flex items-center gap-1">
              <NavLink
                to="/"
                end
                className={({ isActive }) =>
                  `px-3 py-2 rounded-lg text-sm font-medium transition-colors ${
                    isActive ? 'bg-emerald-500/10 text-emerald-400' : 'text-zinc-400 hover:text-zinc-200'
                  }`
                }
              >
                <span className="flex items-center gap-1.5">
                  <Dumbbell className="w-4 h-4" />
                  Workouts
                </span>
              </NavLink>
              <NavLink
                to="/report"
                className={({ isActive }) =>
                  `px-3 py-2 rounded-lg text-sm font-medium transition-colors ${
                    isActive ? 'bg-emerald-500/10 text-emerald-400' : 'text-zinc-400 hover:text-zinc-200'
                  }`
                }
              >
                <span className="flex items-center gap-1.5">
                  <BarChart3 className="w-4 h-4" />
                  Report
                </span>
              </NavLink>
              <button
                onClick={handleLogout}
                className="ml-4 px-3 py-2 rounded-lg text-sm font-medium text-zinc-400 hover:text-red-400 transition-colors cursor-pointer"
              >
                <span className="flex items-center gap-1.5">
                  <LogOut className="w-4 h-4" />
                  Logout
                </span>
              </button>
            </div>
          </div>
        </div>
      </nav>
      <main className="max-w-6xl mx-auto px-4 sm:px-6 py-8">
        <Outlet />
      </main>
    </div>
  );
}
