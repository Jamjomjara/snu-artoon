package baryberri.a6_1_simplediarybyfilemanagement;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    View calendarPickView;
    DatePicker datePicker;

    Button datePickButton;
    TextView selectedDateTextView;
    EditText diaryContentEditText;
    Button saveButton;

    // month, day, year
    Integer[] selectedDate = {0, 0, 0};

    String selectedDateToString() {
        return String.format(Locale.US, "%02d %02d, %04d",
                selectedDate[0], selectedDate[1], selectedDate[2]);
    }

    String getFileName() {
        return String.format(Locale.US, "%02d_%02d_%04d",
                selectedDate[0], selectedDate[1], selectedDate[2]);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datePickButton = (Button) findViewById(R.id.pickDateButton);
        selectedDateTextView = (TextView) findViewById(R.id.selectedDateTextView);
        diaryContentEditText = (EditText) findViewById(R.id.diaryContentEditText);
        saveButton = (Button) findViewById(R.id.saveButton);

        // setting the selectedDate to current time.
        Calendar calendar = Calendar.getInstance();
        selectedDate[0] = calendar.get(Calendar.MONTH);
        selectedDate[1] = calendar.get(Calendar.DAY_OF_MONTH);
        selectedDate[2] = calendar.get(Calendar.YEAR);
        selectedDateTextView.setText(selectedDateToString());

        datePickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarPickView = View.inflate(MainActivity.this, R.layout.date_picker, null);
                datePicker = (DatePicker) calendarPickView.findViewById(R.id.datePicker);

                datePicker.init(selectedDate[2], selectedDate[0], selectedDate[1], new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        selectedDate[0] = monthOfYear;
                        selectedDate[1] = dayOfMonth;
                        selectedDate[2] = year;
                        selectedDateTextView.setText(selectedDateToString());

                        String fileName = getFileName();
                        String content = readDiary(fileName);
                        diaryContentEditText.setText(content);
                    }
                });

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.selectDate)
                        .setView(calendarPickView)
                        .setPositiveButton("OK", null)
                        .setNegativeButton("CANCEL", null)
                        .show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = getFileName();
                String contentToSave = diaryContentEditText.getText().toString();
                saveDiary(contentToSave, fileName);
            }
        });
    }


    // We can read and write to the file into data/file directory
    // by using FileInputStream and FileOutputStream given by android.
    String readDiary(String fileName) {
        String content;
        FileInputStream is;
        try {
            is = openFileInput(fileName);
            byte[] text = new byte[500];
            is.read(text);
            is.close();
            content = (new String(text)).trim();
        }
        catch (IOException e) {
            content = null;
        }
        return content;
    }

    void saveDiary(String content, String fileName) {
        FileOutputStream os;
        try {
            os = openFileOutput(fileName, MODE_PRIVATE);
            os.write(content.getBytes());
            os.close();
            Toast.makeText(MainActivity.this, "Successfully Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(MainActivity.this,
                    "An error occurred while saving, try again", Toast.LENGTH_SHORT).show();
        }
    }
}
