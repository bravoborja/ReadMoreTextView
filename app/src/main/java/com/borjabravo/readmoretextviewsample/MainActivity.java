package com.borjabravo.readmoretextviewsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView text1 = (TextView) findViewById(R.id.text1);
        text1.setText(getString(R.string.lorem_ipsum));
        TextView text2 = (TextView) findViewById(R.id.text2);
        text2.setText(getString(R.string.lorem_ipsum2));
        TextView text3 = (TextView) findViewById(R.id.text3);
        text3.setText(getString(R.string.lorem_ipsum3));
    }
}
