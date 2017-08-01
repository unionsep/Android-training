package com.websarva.wings.android.viewsample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuThanksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_thanks);

        Intent intent = getIntent();
        String menuName = intent.getStringExtra("menuName");
        String menuPrice = intent.getStringExtra("menuPrice");

        TextView tvTextName = (TextView) findViewById(R.id.tvMenuName);
        TextView tvTextPrice = (TextView) findViewById(R.id.tvMenuPrice);

        tvTextName.setText(menuName);
        tvTextPrice.setText(menuPrice);
    }

    public void onBackButtonClick(View view) {
        finish();
    }
}
