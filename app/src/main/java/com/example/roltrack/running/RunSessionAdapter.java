package com.example.roltrack.running;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roltrack.R;
import com.example.roltrack.data.entity.RunPoint;
import com.example.roltrack.data.entity.RunSession;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class RunSessionAdapter extends RecyclerView.Adapter<RunSessionAdapter.RunSessionViewHolder> {

    public interface RunActionListener {
        List<RunPoint> getPointsForSession(int sessionId);
    }

    private List<RunSession> sessions = new ArrayList<>();
    private RunActionListener listener;

    public RunSessionAdapter(RunActionListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RunSessionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_run_session, parent, false);
        return new RunSessionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RunSessionViewHolder holder, int position) {
        RunSession session = sessions.get(position);
        Context context = holder.itemView.getContext();

        holder.tvRunSessionDate.setText(session.getDate());
        holder.tvRunSessionDistance.setText(RunStatsUtils.formatDistanceKm(session.getDistanceMeters()));
        holder.tvRunSessionDuration.setText(RunStatsUtils.formatDuration(session.getDurationMillis()));
        holder.tvRunSessionPace.setText(RunStatsUtils.formatPace(session.getAvgPaceSecPerKm()));

        // 🔽 DESCARGAR PNG
        holder.btnDownloadRunPng.setOnClickListener(v -> {
            Executors.newSingleThreadExecutor().execute(() -> {
                try {
                    List<RunPoint> points = listener.getPointsForSession(session.getId());
                    Bitmap bitmap = RunPngExporter.buildInstagramBitmap(context, session, points);
                    RunPngExporter.saveToGallery(context, bitmap);

                    ((Activity) context).runOnUiThread(() ->
                            Toast.makeText(context, "PNG guardado en Galería", Toast.LENGTH_LONG).show()
                    );

                } catch (Exception e) {
                    ((Activity) context).runOnUiThread(() ->
                            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show()
                    );
                }
            });
        });

        // 🔽 COMPARTIR PNG
        holder.btnShareRunPng.setOnClickListener(v -> {
            Executors.newSingleThreadExecutor().execute(() -> {
                try {
                    List<RunPoint> points = listener.getPointsForSession(session.getId());
                    Bitmap bitmap = RunPngExporter.buildInstagramBitmap(context, session, points);
                    Uri uri = RunPngExporter.saveToCacheForShare(context, bitmap);

                    ((Activity) context).runOnUiThread(() -> {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("image/png");
                        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                        context.startActivity(Intent.createChooser(shareIntent, "Compartir carrera"));
                    });

                } catch (Exception e) {
                    ((Activity) context).runOnUiThread(() ->
                            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show()
                    );
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return sessions == null ? 0 : sessions.size();
    }

    public void setSessions(List<RunSession> sessions) {
        this.sessions = sessions == null ? new ArrayList<>() : sessions;
        notifyDataSetChanged();
    }

    static class RunSessionViewHolder extends RecyclerView.ViewHolder {
        TextView tvRunSessionDate;
        TextView tvRunSessionDistance;
        TextView tvRunSessionDuration;
        TextView tvRunSessionPace;
        MaterialButton btnDownloadRunPng;
        MaterialButton btnShareRunPng;

        public RunSessionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRunSessionDate = itemView.findViewById(R.id.tvRunSessionDate);
            tvRunSessionDistance = itemView.findViewById(R.id.tvRunSessionDistance);
            tvRunSessionDuration = itemView.findViewById(R.id.tvRunSessionDuration);
            tvRunSessionPace = itemView.findViewById(R.id.tvRunSessionPace);
            btnDownloadRunPng = itemView.findViewById(R.id.btnDownloadRunPng);
            btnShareRunPng = itemView.findViewById(R.id.btnShareRunPng);
        }
    }
}