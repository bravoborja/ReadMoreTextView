package com.borjabravo.readmoretextviewsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.borjabravo.readmoretextview.ReadMoreTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ReadMoreTextView text1 = findViewById(R.id.text1);
        text1.setText(getString(R.string.lorem_ipsum));

        ReadMoreTextView text2 = findViewById(R.id.text2);
        text2.setText(getString(R.string.lorem_ipsum2));
        text2.setOnClickReadMore(new ReadMoreTextView.onClickReadMore() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Move to details of text",Toast.LENGTH_LONG).show();
            }
        });
        TextView text3 = findViewById(R.id.text3);
        text3.setText(getString(R.string.lorem_ipsum3));
        TextView text4 = findViewById(R.id.text4);
        text4.setText(getString(R.string.one_line_text));
    }
}
