package baryberri.a2_2_selectedimageviewer;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // View
    Switch startSwitch;

    TextView selectExplanationText;
    RadioGroup selectImageGroup;
    RadioButton joyRadio;
    RadioButton bookRadio;
    RadioButton laptopRadio;

    ImageView imageView;

    Button quitButton;
    Button restartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // linking views
        startSwitch = (Switch)findViewById(R.id.startSwitch);

        selectExplanationText = (TextView)findViewById(R.id.selectPictureText);
        selectImageGroup = (RadioGroup)findViewById(R.id.imageSelectionGroup);
        joyRadio = (RadioButton)findViewById(R.id.joyRadioButton);
        bookRadio = (RadioButton)findViewById(R.id.bookRadioButton);
        laptopRadio = (RadioButton)findViewById(R.id.laptopRadioButton);

        imageView = (ImageView)findViewById(R.id.imageView);

        quitButton = (Button)findViewById(R.id.quitButton);
        restartButton = (Button)findViewById(R.id.restartButton);


        // 1. When the switch is on, make all other views visible.
        startSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                selectImageGroup.clearCheck();
                if (isChecked) {
                    selectExplanationText.setVisibility(View.VISIBLE);
                    selectImageGroup.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                } else {
                    selectExplanationText.setVisibility(View.INVISIBLE);
                    selectImageGroup.setVisibility(View.INVISIBLE);
                    imageView.setVisibility(View.INVISIBLE);
                }
            }
        });


        // 2. When the radioButton selection is changed, change the image source.
        selectImageGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.joyRadioButton:
                        imageView.setImageResource(R.drawable.joy);
                        break;

                    case R.id.bookRadioButton:
                        imageView.setImageResource(R.drawable.book);
                        break;

                    case R.id.laptopRadioButton:
                        imageView.setImageResource(R.drawable.laptop);
                        break;

                    default:
                        imageView.setImageDrawable(null);
                        break;
                }
            }
        });


        // 3. When Quit button is called, quit the app execution.
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // 4. when restart button is called, reset the state.
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageDrawable(null);
                startSwitch.setChecked(false);
            }
        });
    }
}
