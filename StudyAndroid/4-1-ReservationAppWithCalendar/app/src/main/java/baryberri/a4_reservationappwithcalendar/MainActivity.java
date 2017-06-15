package baryberri.a4_reservationappwithcalendar;

import android.graphics.Color;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Chronometer;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Chronometer chronometer;
    Button startReservationButton;
    RadioButton pickDateRadioButton;
    RadioButton pickTimeRadioButton;
    CalendarView calendar;
    TimePicker timePicker;
    Button endReservationButton;
    TextView pickedTimeView;

    // {month, day, year, hour, min}
    Integer[] pickedDate = {0, 0, 0, 0, 0};

    String pickedDateToString() {
        String month = String.format(Locale.US, "%02d", pickedDate[0]);
        String day = String.format(Locale.US, "%02d", pickedDate[1]);
        String year = String.format(Locale.US, "%04d", pickedDate[2]);
        String hour = String.format(Locale.US, "%02d", pickedDate[3]);
        String min = String.format(Locale.US, "%02d", pickedDate[4]);
        return month + " " + day + ", " + year + ", " + hour + " : " + min;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // finding view
        chronometer = (Chronometer)findViewById(R.id.chronometer1);
        startReservationButton = (Button)findViewById(R.id.startReservationButton);
        pickDateRadioButton = (RadioButton)findViewById(R.id.selectionCalendar);
        pickTimeRadioButton = (RadioButton)findViewById(R.id.selectionTime);
        calendar = (CalendarView)findViewById(R.id.pickDate);
        timePicker = (TimePicker)findViewById(R.id.pickTime);
        endReservationButton = (Button)findViewById(R.id.endReservation);
        pickedTimeView = (TextView)findViewById(R.id.pickedDateTimeText);
        pickedTimeView.setText(pickedDateToString());

        setTitle(R.string.app_title);

        calendar.setVisibility(View.INVISIBLE);
        timePicker.setVisibility(View.INVISIBLE);


        pickDateRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.setVisibility(View.VISIBLE);
                timePicker.setVisibility(View.INVISIBLE);
            }
        });

        pickTimeRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.setVisibility(View.INVISIBLE);
                timePicker.setVisibility(View.VISIBLE);
            }
        });

        startReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set the chronometer base, and make the chronometer run.
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                chronometer.setTextColor(Color.RED);
            }
        });

        endReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                chronometer.setTextColor(Color.BLUE);
            }
        });

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                pickedDate[0] = month;
                pickedDate[1] = dayOfMonth;
                pickedDate[2] = year;
                pickedTimeView.setText(pickedDateToString());
            }
        });

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                pickedDate[3] = hourOfDay;
                pickedDate[4] = minute;
                pickedTimeView.setText(pickedDateToString());
            }
        });
    }
}
