package com.example.roltrack.running;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.roltrack.R;
import com.example.roltrack.data.entity.RunPoint;
import com.example.roltrack.data.entity.RunSession;
import com.example.roltrack.viewmodel.RunViewModel;
import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class RunSummaryFragment extends Fragment {

    private static final String ARG_DISTANCE = "arg_distance";
    private static final String ARG_DURATION = "arg_duration";
    private static final String ARG_PACE = "arg_pace";

    private TextView tvSummaryDistance;
    private TextView tvSummaryTime;
    private TextView tvSummaryPace;
    private MaterialButton btnExportPng;
    private MaterialButton btnSharePng;
    private ImageView ivSummaryLogo;
    private RoutePreviewView routePreviewView;

    private RunViewModel runViewModel;
    private List<RunPoint> currentPoints = new ArrayList<>();

    private float distanceMeters = 0f;
    private long durationMillis = 0L;
    private float paceSecPerKm = 0f;

    public RunSummaryFragment() {
        super(R.layout.fragment_run_summary);
    }

    public static RunSummaryFragment newInstance(float distanceMeters, long durationMillis, float paceSecPerKm) {
        RunSummaryFragment fragment = new RunSummaryFragment();
        Bundle args = new Bundle();
        args.putFloat(ARG_DISTANCE, distanceMeters);
        args.putLong(ARG_DURATION, durationMillis);
        args.putFloat(ARG_PACE, paceSecPerKm);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvSummaryDistance = view.findViewById(R.id.tvSummaryDistance);
        tvSummaryTime = view.findViewById(R.id.tvSummaryTime);
        tvSummaryPace = view.findViewById(R.id.tvSummaryPace);
        btnExportPng = view.findViewById(R.id.btnExportPng);
        routePreviewView = view.findViewById(R.id.routePreviewView);
        ivSummaryLogo = view.findViewById(R.id.ivSummaryLogo);
        btnSharePng = view.findViewById(R.id.btnSharePng);

        runViewModel = new ViewModelProvider(this).get(RunViewModel.class);

        Bundle args = getArguments();
        distanceMeters = args != null ? args.getFloat(ARG_DISTANCE, 0f) : 0f;
        durationMillis = args != null ? args.getLong(ARG_DURATION, 0L) : 0L;
        paceSecPerKm = args != null ? args.getFloat(ARG_PACE, 0f) : 0f;

        tvSummaryDistance.setText(RunStatsUtils.formatDistanceKm(distanceMeters));
        tvSummaryTime.setText(RunStatsUtils.formatDuration(durationMillis));
        tvSummaryPace.setText(RunStatsUtils.formatPace(paceSecPerKm));

        btnExportPng.setOnClickListener(v -> exportInstagramPng());
        btnSharePng.setOnClickListener(v -> shareInstagramPng());

        loadLatestRunPoints();
    }

    private void loadLatestRunPoints() {
        Executors.newSingleThreadExecutor().execute(() -> {
            RunSession latest = runViewModel.getLatestSessionSync();
            List<RunPoint> points = new ArrayList<>();

            if (latest != null) {
                points = runViewModel.getPointsForSession(latest.getId());
            }

            List<RunPoint> finalPoints = points;
            new Handler(Looper.getMainLooper()).post(() -> {
                currentPoints = finalPoints;
                routePreviewView.setPoints(currentPoints);
            });
        });
    }

    private void exportInstagramPng() {
        try {
            Bitmap bitmap = buildInstagramBitmap();
            saveBitmapToGallery(bitmap);
            Toast.makeText(requireContext(), "PNG guardado en Galería", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Error al guardar PNG", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareInstagramPng() {
        try {
            Bitmap bitmap = buildInstagramBitmap();
            Uri uri = saveBitmapToCache(bitmap);

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/png");
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivity(Intent.createChooser(shareIntent, "Compartir resumen"));
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Error al compartir imagen", Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap buildInstagramBitmap() {
        int width = 1080;
        int height = 1920;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.BLACK);

        Paint titlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        titlePaint.setColor(Color.WHITE);
        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setFakeBoldText(true);

        Paint bodyPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bodyPaint.setColor(Color.WHITE);
        bodyPaint.setTextAlign(Paint.Align.CENTER);

        Paint mutedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mutedPaint.setColor(Color.parseColor("#B7BDC7"));
        mutedPaint.setTextAlign(Paint.Align.CENTER);

        Paint glowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        glowPaint.setColor(Color.parseColor("#9BFF3F"));
        glowPaint.setStyle(Paint.Style.STROKE);
        glowPaint.setStrokeWidth(30f);
        glowPaint.setStrokeCap(Paint.Cap.ROUND);
        glowPaint.setStrokeJoin(Paint.Join.ROUND);
        glowPaint.setMaskFilter(new BlurMaskFilter(26f, BlurMaskFilter.Blur.NORMAL));

        Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.parseColor("#BFFF2B"));
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(12f);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setStrokeJoin(Paint.Join.ROUND);

        drawLogoImage(canvas, width);

        titlePaint.setTextSize(84f);
        canvas.drawText("ROLTRACK", width / 2f, 400f, titlePaint);

        titlePaint.setTextSize(110f);
        canvas.drawText(String.format(Locale.getDefault(), "%.2f km", distanceMeters / 1000f), width / 2f, 610f, titlePaint);

        bodyPaint.setTextSize(64f);
        canvas.drawText(RunStatsUtils.formatDuration(durationMillis), width / 2f, 730f, bodyPaint);

        bodyPaint.setTextSize(58f);
        canvas.drawText(RunStatsUtils.formatPace(paceSecPerKm), width / 2f, 830f, bodyPaint);

        RoutePathData data = buildRoutePathForInstagram(width, height);
        if (data != null) {
            canvas.drawPath(data.path, glowPaint);
            canvas.drawPath(data.path, linePaint);

            Paint startGlow = new Paint(Paint.ANTI_ALIAS_FLAG);
            startGlow.setColor(Color.parseColor("#69F05A"));
            startGlow.setMaskFilter(new BlurMaskFilter(22f, BlurMaskFilter.Blur.NORMAL));

            Paint endGlow = new Paint(Paint.ANTI_ALIAS_FLAG);
            endGlow.setColor(Color.parseColor("#FF5D5D"));
            endGlow.setMaskFilter(new BlurMaskFilter(22f, BlurMaskFilter.Blur.NORMAL));

            Paint startPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            startPaint.setColor(Color.parseColor("#69F05A"));

            Paint endPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            endPaint.setColor(Color.parseColor("#FF5D5D"));

            Paint markerTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            markerTextPaint.setColor(Color.WHITE);
            markerTextPaint.setTextAlign(Paint.Align.CENTER);
            markerTextPaint.setTextSize(34f);
            markerTextPaint.setFakeBoldText(true);

            canvas.drawCircle(data.startX, data.startY, 24f, startGlow);
            canvas.drawCircle(data.endX, data.endY, 24f, endGlow);

            canvas.drawCircle(data.startX, data.startY, 15f, startPaint);
            canvas.drawCircle(data.endX, data.endY, 15f, endPaint);

            canvas.drawText("INICIO", data.startX + 55f, data.startY - 20f, markerTextPaint);
            canvas.drawText("FIN", data.endX, data.endY - 28f, markerTextPaint);
        }

        mutedPaint.setTextSize(42f);
        canvas.drawText(getPrettyDate(), width / 2f, 1760f, mutedPaint);

        return bitmap;
    }

    private void drawLogoImage(Canvas canvas, int width) {
        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.roltrack_logo);
        if (logo == null) return;

        int targetWidth = 260;
        int targetHeight = (int) (((float) logo.getHeight() / logo.getWidth()) * targetWidth);

        Bitmap scaled = Bitmap.createScaledBitmap(logo, targetWidth, targetHeight, true);
        float left = (width - targetWidth) / 2f;
        float top = 110f;

        canvas.drawBitmap(scaled, left, top, null);
    }

    private RoutePathData buildRoutePathForInstagram(int width, int height) {
        if (currentPoints == null || currentPoints.size() < 2) {
            return null;
        }

        double minLat = Double.MAX_VALUE;
        double maxLat = -Double.MAX_VALUE;
        double minLng = Double.MAX_VALUE;
        double maxLng = -Double.MAX_VALUE;

        for (RunPoint point : currentPoints) {
            minLat = Math.min(minLat, point.getLatitude());
            maxLat = Math.max(maxLat, point.getLatitude());
            minLng = Math.min(minLng, point.getLongitude());
            maxLng = Math.max(maxLng, point.getLongitude());
        }

        double latRange = maxLat - minLat;
        double lngRange = maxLng - minLng;

        if (latRange == 0) latRange = 0.0001;
        if (lngRange == 0) lngRange = 0.0001;

        float left = 90f;
        float top = 980f;
        float right = width - 90f;
        float bottom = 1520f;

        float contentWidth = right - left;
        float contentHeight = bottom - top;

        Path path = new Path();
        float startX = 0f;
        float startY = 0f;
        float endX = 0f;
        float endY = 0f;

        for (int i = 0; i < currentPoints.size(); i++) {
            RunPoint p = currentPoints.get(i);

            float x = (float) (((p.getLongitude() - minLng) / lngRange) * contentWidth + left);
            float y = (float) (((p.getLatitude() - minLat) / latRange) * contentHeight + top);
            y = bottom - (y - top);

            if (i == 0) {
                path.moveTo(x, y);
                startX = x;
                startY = y;
            } else {
                path.lineTo(x, y);
            }

            if (i == currentPoints.size() - 1) {
                endX = x;
                endY = y;
            }
        }

        return new RoutePathData(path, startX, startY, endX, endY);
    }

    private String getPrettyDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("d 'de' MMMM 'de' yyyy", new Locale("es", "MX"));
        return sdf.format(new Date());
    }

    private void saveBitmapToGallery(Bitmap bitmap) throws Exception {
        String fileName = "roltrack_run_" + System.currentTimeMillis() + ".png";

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/RolTrack");
            values.put(MediaStore.Images.Media.IS_PENDING, 1);
        }

        Uri uri = requireContext().getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
        );

        if (uri == null) {
            throw new Exception("No se pudo crear el archivo PNG");
        }

        OutputStream out = requireContext().getContentResolver().openOutputStream(uri);
        if (out == null) {
            throw new Exception("No se pudo abrir el archivo PNG");
        }

        boolean success = bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        out.flush();
        out.close();

        if (!success) {
            throw new Exception("No se pudo comprimir la imagen como PNG");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentValues update = new ContentValues();
            update.put(MediaStore.Images.Media.IS_PENDING, 0);
            requireContext().getContentResolver().update(uri, update, null, null);
        }
    }

    private Uri saveBitmapToCache(Bitmap bitmap) throws Exception {
        File imagesFolder = new File(requireContext().getCacheDir(), "shared_images");
        if (!imagesFolder.exists()) {
            imagesFolder.mkdirs();
        }

        File file = new File(imagesFolder, "roltrack_share.png");
        FileOutputStream stream = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        stream.flush();
        stream.close();

        return FileProvider.getUriForFile(
                requireContext(),
                requireContext().getPackageName() + ".fileprovider",
                file
        );
    }

    private static class RoutePathData {
        final Path path;
        final float startX;
        final float startY;
        final float endX;
        final float endY;

        RoutePathData(Path path, float startX, float startY, float endX, float endY) {
            this.path = path;
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
        }
    }
}