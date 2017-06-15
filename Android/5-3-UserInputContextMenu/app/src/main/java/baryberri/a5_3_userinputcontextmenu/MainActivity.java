package baryberri.a5_3_userinputcontextmenu;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView nameText;
    TextView emailText;

    Button button;

    EditText nameEditText;
    EditText emailEditText;

    TextView toastText;

    View dialogView;
    View toastView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameText = (TextView)findViewById(R.id.name);
        emailText = (TextView)findViewById(R.id.email);
        button = (Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView = View.inflate(MainActivity.this, R.layout.dialog1, null);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Input User Information")
                        .setView(dialogView)
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                nameEditText = (EditText)dialogView.findViewById(R.id.nameEditText);
                                emailEditText = (EditText)dialogView.findViewById(R.id.emailEditText);
                                nameText.setText(nameEditText.getText().toString());
                                emailText.setText(emailEditText.getText().toString());
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast toast = new Toast(MainActivity.this);
                                toastView = View.inflate(MainActivity.this, R.layout.toast, null);
                                toastText = (TextView)toastView.findViewById(R.id.toastText);
                                toastText.setText("Canceled");
                                toast.setView(toastView);
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        })
                        .show();
            }
        });
    }
}
