package com.example.roltrack.running;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.*;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import com.example.roltrack.R;
import com.example.roltrack.data.entity.RunPoint;
import com.example.roltrack.data.entity.RunSession;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RunPngExporter {

    public static Bitmap buildInstagramBitmap(Context context, RunSession session, List<RunPoint> points) {

        int width = 1080;
        int height = 1920;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.BLACK);

        Bitmap logo = BitmapFactory.decodeResource(context.getResources(), R.drawable.roltrack_logo);
        if (logo != null) {
            Bitmap scaledLogo = Bitmap.createScaledBitmap(logo, 340, 340, false);
            canvas.drawBitmap(scaledLogo, width / 2f - 170, 80, null);
        }

        Paint text = new Paint(Paint.ANTI_ALIAS_FLAG);
        text.setColor(Color.WHITE);
        text.setTextAlign(Paint.Align.CENTER);

        Paint title = new Paint(Paint.ANTI_ALIAS_FLAG);
        title.setColor(Color.WHITE);
        title.setTextAlign(Paint.Align.CENTER);
        title.setFakeBoldText(true);
        title.setTextSize(70f);
        canvas.drawText("ROLTRACK", width / 2f, 390, title);

        text.setTextSize(120f);
        text.setFakeBoldText(true);
        canvas.drawText(
                String.format(Locale.getDefault(), "%.2f km", session.getDistanceMeters() / 1000f),
                width / 2f,
                650,
                text
        );

        text.setFakeBoldText(false);
        text.setTextSize(70f);
        canvas.drawText(formatTime(session.getDurationMillis()), width / 2f, 740, text);

        text.setTextSize(65f);
        canvas.drawText(formatPace(session.getAvgPaceSecPerKm()), width / 2f, 820, text);

        if (points != null && points.size() > 1) {

            double minLat = Double.MAX_VALUE, maxLat = -Double.MAX_VALUE;
            double minLng = Double.MAX_VALUE, maxLng = -Double.MAX_VALUE;

            for (RunPoint p : points) {
                minLat = Math.min(minLat, p.getLatitude());
                maxLat = Math.max(maxLat, p.getLatitude());
                minLng = Math.min(minLng, p.getLongitude());
                maxLng = Math.max(maxLng, p.getLongitude());
            }

            double latRange = maxLat - minLat;
            double lngRange = maxLng - minLng;
            if (latRange == 0) latRange = 0.0001;
            if (lngRange == 0) lngRange = 0.0001;

            float padding = 100f;
            float top = 900f;
            float bottom = 1500f;
            float widthMap = width - padding * 2;
            float heightMap = bottom - top;

            Path path = new Path();

            float startX = 0, startY = 0;
            float endX = 0, endY = 0;

            for (int i = 0; i < points.size(); i++) {
                RunPoint p = points.get(i);

                float x = (float) ((p.getLongitude() - minLng) / lngRange * widthMap + padding);
                float y = (float) ((p.getLatitude() - minLat) / latRange * heightMap + top);
                y = bottom - (y - top);

                if (i == 0) {
                    path.moveTo(x, y);
                    startX = x;
                    startY = y;
                } else {
                    path.lineTo(x, y);
                }

                if (i == points.size() - 1) {
                    endX = x;
                    endY = y;
                }
            }

            Paint glow = new Paint(Paint.ANTI_ALIAS_FLAG);
            glow.setStyle(Paint.Style.STROKE);
            glow.setStrokeWidth(70f);
            glow.setColor(Color.parseColor("#9BFF3F"));
            glow.setMaskFilter(new BlurMaskFilter(45, BlurMaskFilter.Blur.NORMAL));
            glow.setAlpha(120);
            glow.setStrokeCap(Paint.Cap.ROUND);
            glow.setStrokeJoin(Paint.Join.ROUND);

            Paint glow2 = new Paint(Paint.ANTI_ALIAS_FLAG);
            glow2.setStyle(Paint.Style.STROKE);
            glow2.setStrokeWidth(40f);
            glow2.setColor(Color.parseColor("#9BFF3F"));
            glow2.setMaskFilter(new BlurMaskFilter(30, BlurMaskFilter.Blur.NORMAL));
            glow2.setStrokeCap(Paint.Cap.ROUND);
            glow2.setStrokeJoin(Paint.Join.ROUND);

            Paint line = new Paint(Paint.ANTI_ALIAS_FLAG);
            line.setStyle(Paint.Style.STROKE);
            line.setStrokeWidth(14f);
            line.setColor(Color.parseColor("#C6FF3A"));
            line.setStrokeCap(Paint.Cap.ROUND);
            line.setStrokeJoin(Paint.Join.ROUND);

            canvas.drawPath(path, glow);
            canvas.drawPath(path, glow2);
            canvas.drawPath(path, line);

            Paint start = new Paint(Paint.ANTI_ALIAS_FLAG);
            start.setColor(Color.GREEN);

            Paint end = new Paint(Paint.ANTI_ALIAS_FLAG);
            end.setColor(Color.RED);

            canvas.drawCircle(startX, startY, 16, start);
            canvas.drawCircle(endX, endY, 16, end);

            text.setColor(Color.WHITE);
            text.setFakeBoldText(true);
            text.setTextSize(32f);
            text.setTextAlign(Paint.Align.LEFT);

            canvas.drawText("INICIO", startX + 40, startY - 10, text);
            canvas.drawText("FIN", endX + 40, endY - 10, text);
        }

        text.setTextAlign(Paint.Align.CENTER);
        text.setFakeBoldText(false);
        text.setTextSize(45f);
        text.setColor(Color.GRAY);
        canvas.drawText(formatDate(session.getDate()), width / 2f, 1800, text);

        return bitmap;
    }

    private static String formatTime(long millis) {
        long sec = millis / 1000;
        return String.format(Locale.getDefault(), "%02d:%02d", sec / 60, sec % 60);
    }

    private static String formatPace(float pace) {
        return String.format(Locale.getDefault(), "%.0f:%02d /km", pace / 60, (int) pace % 60);
    }

    private static String formatDate(String raw) {
        try {
            SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date d = in.parse(raw);
            return new SimpleDateFormat("d 'de' MMMM 'de' yyyy", new Locale("es", "MX")).format(d);
        } catch (Exception e) {
            return raw;
        }
    }

    public static Uri saveToGallery(Context context, Bitmap bitmap) throws Exception {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "roltrack_" + System.currentTimeMillis() + ".png");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/RolTrack");
        }

        ContentResolver resolver = context.getContentResolver();
        Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        if (uri == null) throw new Exception("No se pudo crear PNG");

        OutputStream out = resolver.openOutputStream(uri);
        if (out == null) throw new Exception("No se pudo abrir PNG");

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        out.flush();
        out.close();

        return uri;
    }

    public static Uri saveToCacheForShare(Context context, Bitmap bitmap) throws Exception {
        File file = new File(context.getCacheDir(), "roltrack.png");
        FileOutputStream stream = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        stream.flush();
        stream.close();

        return FileProvider.getUriForFile(
                context,
                context.getPackageName() + ".fileprovider",
                file
        );
    }
}