package com.example.dateselect;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText dayEditText;
    private Button selectStartDateButton;
    private Button selectEndDateButton;
    private Button countDayButton;
    private TextView startDateTxt;
    private TextView endDateTxt;

    private Calendar startDate;
    private Calendar endDate;
    private Calendar currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dayEditText = findViewById(R.id.dayEditText);
        selectStartDateButton = findViewById(R.id.selectStartDateButton);
        selectEndDateButton = findViewById(R.id.selectEndDateButton);
        countDayButton = findViewById(R.id.countDayButton);

        startDateTxt = findViewById(R.id.startDateTxt);
        endDateTxt = findViewById(R.id.endDateTxt);

        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
        currentDate = Calendar.getInstance();

        selectStartDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(true);
            }
        });

        selectEndDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(false);
            }
        });

        countDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countSelectedDay();
            }
        });
    }

    private void showDatePickerDialog(final boolean isStartDate) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayOfMonth);

                if (isStartDate) {
                    startDate = selectedDate;
                    Toast.makeText(MainActivity.this, "Start date: " + getFormattedDate(startDate), Toast.LENGTH_SHORT).show();
                } else {
                    endDate = selectedDate;
                    Toast.makeText(MainActivity.this, "End date: " + getFormattedDate(endDate), Toast.LENGTH_SHORT).show();
                }
            }
        };

        new DatePickerDialog(
                this,
                dateSetListener,
                currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void countSelectedDay() {
        String selectedDay = dayEditText.getText().toString().trim();

        if (selectedDay.isEmpty()) {
            Toast.makeText(this, "Please enter a day", Toast.LENGTH_SHORT).show();
            return;
        }

        int totalOccurrences = 0;
        while (startDate.compareTo(endDate) <= 0) {
            if (getDayOfWeek(startDate).equalsIgnoreCase(selectedDay)) {
                totalOccurrences++;
            }
            startDate.add(Calendar.DAY_OF_MONTH, 1);
        }

        Toast.makeText(this, "Total occurrences of " + selectedDay + ": " + totalOccurrences, Toast.LENGTH_SHORT).show();
    }

    private String getDayOfWeek(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
        return simpleDateFormat.format(calendar.getTime());
    }

    private String getFormattedDate(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return simpleDateFormat.format(calendar.getTime());
    }
}