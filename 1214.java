package com.example.myapplication;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private List<TestCase> caseList = new ArrayList<>();

    // fruitList用于存储数据
    private List<com.example.apple.listviewtest.Fruit> fruitList = new ArrayList<>();
    private SimpleAdapter adapter;
    final String TESTCASE_STATUS_PASS = "\u2705";//对号
    final String TESTCASE_STATUS_FAIL = "\u274C";//错号
    final String TESTCASE_STATUS_WAIT = "\u2754";//问号
    final String TESTCASE_STATUS_INIT = "INIT";//
    final String TESTCASE_STATUS_RUNNNING = "\u25B6";//播放
    List<String> status = new ArrayList<>();
    // String[] status = {TESTCASE_STATUS_WAIT, TESTCASE_STATUS_PASS, TESTCASE_STATUS_FAIL};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView1 = findViewById(R.id.lv_testcase);

        final int size = loadTestCaseJson();

        // final List<String> arrayList1 = new ArrayList<String>();
/*         for (int i = 0; i < size; i++) {
            arrayList1.add((caseList.get(i)).getName());
        }
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList1);
        listView1.setAdapter(adapter1);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

/*        final List<String> testcaseList = new ArrayList<String>();
        for (int i = 0; i < size; i++) {
            testcaseList.add(caseList.get(i).getName() + "\u2754");
        }
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, testcaseList);
        listView1.setAdapter(adapter2);
        testcaseList.set(2, caseList.get(2).getName() + "\u274C");//改变第二个测试用例的执行结果*/

/* 这是list全部加载的adapter
       String[] testcase_name = new String[size];
        for (int i = 0; i < size; i++) {
            testcase_name[i] = caseList.get(i).getName();
        }
        Map<String, Object> map = new HashMap<>();//存放testcase_name和testcase_status
        lists = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            map.put("testcase_name", testcase_name[i]);
            map.put("testcase_status", TESTCASE_STATUS_RUNNNING);
            lists.add(map);
        }
        adapter = new SimpleAdapter(MainActivity.this, lists, R.layout.item, new String[]{"testcase_name", "testcase_status"}, new int[]{R.id.testcase_name, R.id.testcase_status});
        listView1.setAdapter(adapter);*/
        for (int i = 0; i < size; i++) {
            status.add(TESTCASE_STATUS_PASS);
        }
        status.set(0, TESTCASE_STATUS_FAIL);
        updateStatusByTestcase(size, 1);//指的是第一个testcase fail了
    }

    private void updateStatusByTestcase(int size, int index) {
        if(index==0){
            for (int i=0;i<size;i++){
                status.set(i,TESTCASE_STATUS_WAIT);
            }
        }
        //status==0:pass
        //status==1:fail
        //status==2:running
        //status==else:error
        String[] testcase_name = new String[size];
        for (int i = 0; i < size; i++) {
            testcase_name[i] = caseList.get(i).getName();
        }
        List<Map<String, Object>> lists = new ArrayList<>();
        Map<String, Object> map;//存放testcase_name和testcase_status
        for (int i = 0; i < size; i++) {
            map = new HashMap<>();//存放testcase_name和testcase_status
            map.put("testcase_name", testcase_name[i]);
            map.put("testcase_status", status.get(i));
            lists.add(map);
            System.out.println("@@@@" + lists.toString());
        }
        ListView listView1 = findViewById(R.id.lv_testcase);
        SimpleAdapter adapter1 = new SimpleAdapter(MainActivity.this, lists, R.layout.item, new String[]{"testcase_name", "testcase_status"}, new int[]{R.id.testcase_name, R.id.testcase_status});
        listView1.setAdapter(adapter1);


    }
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
