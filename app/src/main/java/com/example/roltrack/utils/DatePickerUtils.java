package com.example.roltrack.utils;

import android.app.DatePickerDialog;
import android.content.Context;

import java.util.Calendar;
import java.util.Locale;

public class DatePickerUtils {

    public interface OnDateSelectedListener {
        void onDateSelected(String formattedDate);
    }

    public static void showDatePicker(Context context, OnDateSelectedListener listener) {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(
                context,
                (view, year, month, dayOfMonth) -> {
                    String formattedDate = String.format(
                            Locale.getDefault(),
                            "%04d-%02d-%02d",
                            year,
                            month + 1,
                            dayOfMonth
                    );
                    listener.onDateSelected(formattedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        dialog.show();
    }
}