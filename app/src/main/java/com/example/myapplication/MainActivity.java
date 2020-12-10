package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // fruitList用于存储数据
    private List<com.example.apple.listviewtest.Fruit> fruitList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 先拿到数据并放在适配器上
        initFruits(); //初始化水果数据
        FruitAdapter adapter=new FruitAdapter(MainActivity.this,R.layout.list_view,fruitList);

        // 将适配器上的数据传递给listView
        ListView listView=findViewById(R.id.lv_testcase);
        listView.setAdapter(adapter);

        // 为ListView注册一个监听器，当用户点击了ListView中的任何一个子项时，就会回调onItemClick()方法
        // 在这个方法中可以通过position参数判断出用户点击的是那一个子项
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                com.example.apple.listviewtest.Fruit fruit=fruitList.get(position);
                Toast.makeText(MainActivity.this,fruit.getName(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 初始化数据
    private void initFruits(){
        for(int i=0;i<10;i++){
            com.example.apple.listviewtest.Fruit a=new com.example.apple.listviewtest.Fruit("a",R.drawable.ic_launcher_background);
            fruitList.add(a);
            com.example.apple.listviewtest.Fruit b=new com.example.apple.listviewtest.Fruit("B",R.drawable.ic_launcher_background);
            fruitList.add(b);
            com.example.apple.listviewtest.Fruit c=new com.example.apple.listviewtest.Fruit("C",R.drawable.ic_launcher_background);
            fruitList.add(c);
            com.example.apple.listviewtest.Fruit d=new com.example.apple.listviewtest.Fruit("D",R.drawable.ic_launcher_background);
            fruitList.add(d);
        }
    }
}
