package com.example.myapplication;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<TestCase> caseList = new ArrayList<>();

    // fruitList用于存储数据
    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView1 = findViewById(R.id.lv_testcase);
        int size = loadTestCaseJson();

        final List<String> arrayList1 = new ArrayList<String>();
        for (int i = 0; i < size; i++) {
            arrayList1.add((caseList.get(i)).getName());
        }
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList1);
        listView1.setAdapter(adapter1);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final List<String> arrayList2 = new ArrayList<String>();
        for (int i = 0; i < arrayList1.size(); i++) {
            arrayList2.add(arrayList1.get(i)+"\u2705");
        }
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList2);
        listView1.setAdapter(adapter2);

        // 先拿到数据并放在适配器上
/*        initFruits(); //初始化水果数据
        FruitAdapter adapter = new FruitAdapter(MainActivity.this, R.id.lv_testcase, fruitList);

        // 将适配器上的数据传递给listView
        ListView listView = findViewById(R.id.lv_testcase);
        listView.setAdapter(adapter);*/

        // 为ListView注册一个监听器，当用户点击了ListView中的任何一个子项时，就会回调onItemClick()方法
        // 在这个方法中可以通过position参数判断出用户点击的是那一个子项
/*        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fruit fruit = fruitList.get(position);
                Toast.makeText(MainActivity.this, fruit.getName(), Toast.LENGTH_SHORT).show();

            }
        });*/
    }


    private static String getJson(String fileName, Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        AssetManager assetManager = context.getAssets();
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(
                assetManager.open(fileName)))) {
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private int loadTestCaseJson() {
        int size = 0;

        String json = getJson("testcase.json", this);
        try {
            JSONArray jsonArray = new JSONArray(json);
            size = jsonArray.length();


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                TestCase testCase = new TestCase();
                testCase.setName(jsonObject.getString("name"));
                caseList.add(testCase);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return size;
    }
}
