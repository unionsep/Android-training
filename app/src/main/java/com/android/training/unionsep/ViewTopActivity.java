package com.android.training.unionsep;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewTopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_top);

        ListView lvMenu = (ListView) findViewById(R.id.top_Menu);
        List<Map<String, String>> menuList = new ArrayList<Map<String, String>>();

        Map<String, String> menu = new HashMap<>();
        menu.put("title", "Web View");
        menu.put("description", "お産合宿のWeb Viewのサンプル");
        menuList.add(menu);
        menu = new HashMap<>();
        menu.put("title", "Typical View");
        menu.put("description", "Androidの基本Viewのサンプル");
        menuList.add(menu);

        String[] from = {"title", "description"};
        int[] to = {android.R.id.text1, android.R.id.text2};
        SimpleAdapter adapter = new SimpleAdapter(ViewTopActivity.this, menuList, android.R.layout.simple_list_item_2, from, to);
        lvMenu.setAdapter(adapter);

        lvMenu.setOnItemClickListener(new ListItemClickListener());

    }

    private class ListItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Map<String, String> item = (Map<String, String>) parent.getItemAtPosition(position);
            String menuName = item.get("title");
            String menuPrice = item.get("description");

            Intent intent = null;
            if (menuName.equals("Typical View")) {
                intent = new Intent(ViewTopActivity.this, ViewTypicalActivity.class);
            } else {
                intent = new Intent(ViewTopActivity.this, MenuThanksActivity.class);
            }

            intent.putExtra("menuName", menuName);
            intent.putExtra("menuPrice", menuPrice);
            startActivity(intent);

        }

    }
}
