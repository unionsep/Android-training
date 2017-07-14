package com.websarva.wings.android.viewsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ViewSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sample);

        HelloListener listener = new HelloListener();

        Button btClick = (Button) findViewById(R.id.btClick);
        btClick.setOnClickListener(listener);

        Button btClear = (Button) findViewById(R.id.btClear);
        btClear.setOnClickListener(listener);
    }

    private class HelloListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            EditText input = (EditText) findViewById(R.id.etName);
            TextView output = (TextView) findViewById(R.id.tvOutput);

            int id = view.getId();
            switch (id) {
                case R.id.btClick:
                    String inputStr = input.getText().toString();
                    output.setText(inputStr + "さん、こんにちは！");
                    break;
                case R.id.btClear:
                    input.setText("");
                    output.setText("");
                    break;
            }
        }
    }
}
