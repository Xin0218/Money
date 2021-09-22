package com.example.recordaccounts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentMoney extends DialogFragment implements View.OnClickListener {
    private final String TAG = "FragmentMoney";
    private ImageView icon;
    private Button bt_num000, bt_num00, bt_num01, bt_num02, bt_num03, bt_num04, bt_num05, bt_num06, bt_num07, bt_num08, bt_num09;
    private Button bt_ac, bt_ok;
    private EditText inputText;
    private TextView showAll, inputNumber;

    private HashMap<String, String> saveMap = new HashMap<String, String>();
    private ArrayList moneyList = new ArrayList();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.money_fragment, container, false);

        findView(view);
        checkIcon();
        return view;
    }

    private void findView(View v) {
        icon = v.findViewById(R.id.input_image);
        inputText = v.findViewById(R.id.input_text);
        inputNumber = v.findViewById(R.id.input_number);

        bt_num000 = v.findViewById(R.id.btn_000);
        bt_num00 = v.findViewById(R.id.btn_00);
        bt_num01 = v.findViewById(R.id.btn_01);
        bt_num02 = v.findViewById(R.id.btn_02);
        bt_num03 = v.findViewById(R.id.btn_03);
        bt_num04 = v.findViewById(R.id.btn_04);
        bt_num05 = v.findViewById(R.id.btn_05);
        bt_num06 = v.findViewById(R.id.btn_06);
        bt_num07 = v.findViewById(R.id.btn_07);
        bt_num08 = v.findViewById(R.id.btn_08);
        bt_num09 = v.findViewById(R.id.btn_09);

        /* ok = total
         * ac = clear*/
        bt_ac = v.findViewById(R.id.btn_ac);
        bt_ok = v.findViewById(R.id.btn_ok);

        bt_ac.setOnClickListener(this);
        bt_ok.setOnClickListener(this);
        bt_num000.setOnClickListener(this);
        bt_num00.setOnClickListener(this);
        bt_num01.setOnClickListener(this);
        bt_num02.setOnClickListener(this);
        bt_num03.setOnClickListener(this);
        bt_num04.setOnClickListener(this);
        bt_num05.setOnClickListener(this);
        bt_num06.setOnClickListener(this);
        bt_num07.setOnClickListener(this);
        bt_num08.setOnClickListener(this);
        bt_num09.setOnClickListener(this);

    }

    /**
     * 食    衣       住     行     育       樂
     * food  clothes  home   walk   study   game
     */
    private void checkIcon() {
        switch (MainActivity.GetInstance().getClickID()) {
            case "food":
                icon.setImageResource(R.drawable.eat);
                break;
            case "clothes":
                icon.setImageResource(R.drawable.clothes);
                break;
            case "home":
                icon.setImageResource(R.drawable.home);
                break;
            case "walk":
                icon.setImageResource(R.drawable.moto);
                break;
            case "study":
                icon.setImageResource(R.drawable.study);
                break;
            case "game":
                icon.setImageResource(R.drawable.game);
                break;
            case "salary":
                icon.setImageResource(R.drawable.salary);
                break;
            case "work":
                icon.setImageResource(R.drawable.work2);
                break;
            case "stock":
                icon.setImageResource(R.drawable.stock);
                break;
            case "bank":
                icon.setImageResource(R.drawable.back);
                break;
            case "shopping":
                icon.setImageResource(R.drawable.shopping);
                break;
            case "other":
                icon.setImageResource(R.drawable.other2);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        String currentText = inputNumber.getText().toString();

        switch (v.getId()) {
            case R.id.btn_000:
                inputNumber.setText(currentText + "00");
                break;
            case R.id.btn_00:
                inputNumber.setText(currentText + "0");
                break;
            case R.id.btn_01:
                inputNumber.setText(currentText + "1");
                break;
            case R.id.btn_02:
                inputNumber.setText(currentText + "2");
                break;
            case R.id.btn_03:
                inputNumber.setText(currentText + "3");
                break;
            case R.id.btn_04:
                inputNumber.setText(currentText + "4");
                break;
            case R.id.btn_05:
                inputNumber.setText(currentText + "5");
                break;
            case R.id.btn_06:
                inputNumber.setText(currentText + "6");
                break;
            case R.id.btn_07:
                inputNumber.setText(currentText + "7");
                break;
            case R.id.btn_08:
                inputNumber.setText(currentText + "8");
                break;
            case R.id.btn_09:
                inputNumber.setText(currentText + "9");
                break;
            case R.id.btn_ac:
                clear();
                MainActivity.GetInstance().mDBHelper.delete();
                break;
            case R.id.btn_ok:
                save(v);
                MainActivity.GetInstance().initFragmentChoose();
                break;
        }
    }

    public void clear() {
        if (!"".equals(inputNumber.getText())) {
            Log.v(TAG, "clear: ");
            inputNumber.setText("");
            inputText.setText("");
        }
    }

    public void save(View v) {
        Integer price = Integer.valueOf(inputNumber.getText().toString());
        String input = inputText.getText().toString();
        switch (MainActivity.GetInstance().getClickID()) {
            case "food":
                putData("food", price, input);
                break;
            case "clothes":
                putData("clothes", price, input);
                break;
            case "home":
                putData("home", price, input);
                break;
            case "walk":
                putData("walk", price, input);
                break;
            case "study":
                putData("study", price, input);
                break;
            case "salary":
                putData("salary", price, "領薪水囉");
                break;
            case "work":
                putData("work", price, "認真工作");
                break;
            case "stock":
                putData("stock", price, "當韭菜");
                break;
            case "bank":
                putData("bank", price, "借銀行錢");
                break;
            case "shopping":
                putData("shopping", price, "蝦皮99免運");
                break;
            case "other":
                putData("other", price, "其他收入");
                break;
        }
        moneyList.add(inputNumber.getText().toString());
    }

    public void putData(String key, Integer value, String value2) {
        MainActivity.GetInstance().mDBHelper.addData(key, value.toString());

        HashMap<String, List<String>> map = new HashMap<String, List<String>>(); //map + list
        List list = new ArrayList();
        list.add(value);
        list.add(value2);

        map.put(key, list);
        MainActivity.GetInstance().getMap().put(key, value);
        MainActivity.GetInstance().getArrayList().add(MainActivity.GetInstance().getMap());
    }

    public void showData() {
        Log.v(TAG, "showData: ");
        for (Map temp : MainActivity.GetInstance().getArrayList()) {
            String key = temp.keySet().toString();
            String value = temp.values().toString();
            saveMap.put(key, value);
            Log.v(TAG, "Ryan_showData KEY: " + temp.keySet() + " ,value: " + temp.values());
        }
    }

}
