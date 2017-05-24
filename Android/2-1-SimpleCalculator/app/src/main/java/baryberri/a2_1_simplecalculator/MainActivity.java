package baryberri.a2_1_simplecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Views
    EditText operand1;
    EditText operand2;

    Button addButton;
    Button subButton;
    Button mulButton;
    Button divButton;

    TextView answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        operand1 = (EditText)findViewById(R.id.operand1);
        operand2 = (EditText)findViewById(R.id.operand2);

        addButton = (Button)findViewById(R.id.addButton);
        subButton = (Button)findViewById(R.id.subButton);
        mulButton = (Button)findViewById(R.id.mulButton);
        divButton = (Button)findViewById(R.id.divButton);

        answer = (TextView)findViewById(R.id.answerText);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double op1 = Double.parseDouble(operand1.getText().toString());
                double op2 = Double.parseDouble(operand2.getText().toString());
                double res = op1 + op2;
                String toShow = "Result : " + res;
                answer.setText(toShow);
            }
        });

        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double op1 = Double.parseDouble(operand1.getText().toString());
                double op2 = Double.parseDouble(operand2.getText().toString());
                double res = op1 - op2;
                String toShow = "Result : " + res;
                answer.setText(toShow);
            }
        });

        mulButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double op1 = Double.parseDouble(operand1.getText().toString());
                double op2 = Double.parseDouble(operand2.getText().toString());
                double res = op1 * op2;
                String toShow = "Result : " + res;
                answer.setText(toShow);
            }
        });

        divButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double op1 = Double.parseDouble(operand1.getText().toString());
                double op2 = Double.parseDouble(operand2.getText().toString());
                double res = op1 / op2;
                String toShow = "Result : " + res;
                answer.setText(toShow);
            }
        });
    }
}
