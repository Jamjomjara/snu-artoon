package baryberri.a3_5_tablelayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText[] operands = new EditText[2];
    Button[] operations = new Button[4];
    Button[] numbers = new Button[10];
    TextView result = null;

    int[] operandIDs = {R.id.editText1, R.id.editText2};
    int[] operationIDs = {R.id.add, R.id.sub, R.id.mul, R.id.div};
    int[] numberIDs = {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                       R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9};

    String num1 = null;
    String num2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // This sets the app name.
        setTitle("Simple Button Calculator");

        // Finding views.
        for (int i = 0; i < operandIDs.length; i++) {
            operands[i] = (EditText)findViewById(operandIDs[i]);
        }

        for (int i = 0; i < operationIDs.length; i++) {
            operations[i] = (Button)findViewById(operationIDs[i]);
        }

        for (int i = 0; i < numberIDs.length; i++) {
            numbers[i] = (Button)findViewById(numberIDs[i]);
        }

        result = (TextView)findViewById(R.id.result);

        // Adding Listeners to operations.
        operations[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int operand1 = Integer.parseInt(operands[0].getText().toString());
                int operand2 = Integer.parseInt(operands[1].getText().toString());
                String operationResult = Integer.toString(operand1 + operand2);
                result.setText(operationResult);
            }
        });

        operations[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int operand1 = Integer.parseInt(operands[0].getText().toString());
                int operand2 = Integer.parseInt(operands[1].getText().toString());
                String operationResult = Integer.toString(operand1 - operand2);
                result.setText(operationResult);
            }
        });

        operations[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int operand1 = Integer.parseInt(operands[0].getText().toString());
                int operand2 = Integer.parseInt(operands[1].getText().toString());
                String operationResult = Integer.toString(operand1 * operand2);
                result.setText(operationResult);
            }
        });

        operations[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int operand1 = Integer.parseInt(operands[0].getText().toString());
                int operand2 = Integer.parseInt(operands[1].getText().toString());
                String operationResult = Integer.toString(operand1 / operand2);
                result.setText(operationResult);
            }
        });

        // Adding number buttons listener
        for (int i = 0; i < numbers.length; i++) {
            final int idx = i;
            numbers[idx].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (operands[0].isFocused()) {
                        num1 = operands[0].getText().toString();
                        num1 += numbers[idx].getText().toString();
                        operands[0].setText(num1);
                    } else if (operands[1].isFocused()){
                        num2 = operands[1].getText().toString();
                        num2 += numbers[idx].getText().toString();
                        operands[1].setText(num2);
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.selectEditText,
                                       Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
