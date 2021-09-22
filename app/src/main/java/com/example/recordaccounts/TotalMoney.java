package com.example.recordaccounts;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class TotalMoney extends Activity {

    private TextView totalContext;
    private String TAG = "TotalMoney";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.v(TAG,"onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.total_money);
        findView();
        DataValue();
    }

    public void findView() {
        totalContext = findViewById(R.id.total_content);
        totalContext.setMovementMethod(ScrollingMovementMethod.getInstance());
    }


    public void DataValue() {
        ArrayList<HashMap<String, String>> List = new ArrayList<>();
        List = MainActivity.GetInstance().mDBHelper2.showAll();
        Log.v(TAG, "DataValue List: " + List.size());
        Integer totoal = 0;
        for (int i = 0; i < List.size(); i++) {
            totalContext.setText(totalContext.getText() + "\n" + "key: " + List.get(i).get("type") + " value: " + List.get(i).get("value") + "describe: " + List.get(i).get("describe"));
            totoal += Integer.parseInt(List.get(i).get("value"));
        }
        Log.v(TAG, "DataValue totalMoney: " + totoal);
    }

}
