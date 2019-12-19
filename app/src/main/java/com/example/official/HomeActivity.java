package com.example.official;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    Button button;
    Button organiser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        button = (Button)findViewById(R.id.volunteer);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),organiser.class);
                intent.putExtra(Constants.INTENT_ORG_ID_NAME,-1);
                intent.putExtra(Constants.INTENT_ORG_ACCESS_NAME,0);
                startActivity(intent);
            }
        });
        organiser = (Button)findViewById(R.id.organiser);
        organiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),login.class));
            }
        });
    }

}
