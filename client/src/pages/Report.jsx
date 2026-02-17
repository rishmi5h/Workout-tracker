import { useState, useEffect } from 'react';
import { getReport } from '../api/workouts';
import { Loader2, BarChart3, FileText } from 'lucide-react';

export default function Report() {
  const [report, setReport] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchReport = async () => {
      try {
        const res = await getReport();
        setReport(res.data);
      } catch {
        setReport('Failed to generate report.');
      } finally {
        setLoading(false);
      }
    };
    fetchReport();
  }, []);

  if (loading) {
    return (
      <div className="flex items-center justify-center py-20">
        <Loader2 className="w-6 h-6 text-emerald-400 animate-spin" />
      </div>
    );
  }

  return (
    <div>
      <div className="flex items-center gap-2 mb-6">
        <BarChart3 className="w-5 h-5 text-emerald-400" />
        <h1 className="text-xl font-bold text-zinc-100">Workout Report</h1>
      </div>

      {report ? (
        <div className="rounded-xl border border-zinc-800 bg-zinc-900/60 p-6">
          <div className="flex items-center gap-2 mb-4">
            <FileText className="w-4 h-4 text-zinc-500" />
            <h2 className="text-sm font-medium text-zinc-400">Completed Workouts Summary</h2>
          </div>
          <pre className="whitespace-pre-wrap text-sm text-zinc-300 font-mono leading-relaxed">
            {report}
          </pre>
        </div>
      ) : (
        <div className="text-center py-20">
          <div className="inline-flex items-center justify-center w-16 h-16 rounded-2xl bg-zinc-900 mb-4">
            <BarChart3 className="w-8 h-8 text-zinc-700" />
          </div>
          <h2 className="text-lg font-medium text-zinc-400">No completed workouts</h2>
          <p className="text-zinc-600 mt-1">Complete some workouts to see your report.</p>
        </div>
      )}
    </div>
  );
}
