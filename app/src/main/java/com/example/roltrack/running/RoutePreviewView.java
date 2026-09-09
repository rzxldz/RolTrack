package com.example.roltrack.running;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.roltrack.data.entity.RunPoint;

import java.util.ArrayList;
import java.util.List;

public class RoutePreviewView extends View {

    private final Paint routePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path routePath = new Path();
    private List<RunPoint> points = new ArrayList<>();

    public RoutePreviewView(Context context) {
        super(context);
        init();
    }

    public RoutePreviewView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoutePreviewView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        routePaint.setColor(Color.parseColor("#2E9E2E"));
        routePaint.setStyle(Paint.Style.STROKE);
        routePaint.setStrokeWidth(10f);
        routePaint.setStrokeCap(Paint.Cap.ROUND);
        routePaint.setStrokeJoin(Paint.Join.ROUND);
    }

    public void setPoints(List<RunPoint> points) {
        this.points = points == null ? new ArrayList<>() : points;
        buildPath();
        invalidate();
    }

    private void buildPath() {
        routePath.reset();

        if (points == null || points.size() < 2 || getWidth() == 0 || getHeight() == 0) {
            return;
        }

        double minLat = Double.MAX_VALUE, maxLat = -Double.MAX_VALUE;
        double minLng = Double.MAX_VALUE, maxLng = -Double.MAX_VALUE;

        for (RunPoint p : points) {
            if (p.getLatitude() < minLat) minLat = p.getLatitude();
            if (p.getLatitude() > maxLat) maxLat = p.getLatitude();
            if (p.getLongitude() < minLng) minLng = p.getLongitude();
            if (p.getLongitude() > maxLng) maxLng = p.getLongitude();
        }

        double latRange = maxLat - minLat;
        double lngRange = maxLng - minLng;

        if (latRange == 0) latRange = 0.0001;
        if (lngRange == 0) lngRange = 0.0001;

        float padding = 40f;
        float contentWidth = getWidth() - 2 * padding;
        float contentHeight = getHeight() - 2 * padding;

        for (int i = 0; i < points.size(); i++) {
            RunPoint p = points.get(i);

            float x = (float) (((p.getLongitude() - minLng) / lngRange) * contentWidth + padding);

            float y = (float) (((p.getLatitude() - minLat) / latRange) * contentHeight + padding);
            y = getHeight() - y; // invertir vertical

            if (i == 0) {
                routePath.moveTo(x, y);
            } else {
                routePath.lineTo(x, y);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        buildPath();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        canvas.drawPath(routePath, routePaint);
    }
}