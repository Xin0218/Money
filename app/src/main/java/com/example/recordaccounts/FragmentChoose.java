package com.example.recordaccounts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentChoose extends Fragment {
    private final String TAG = "FragmentChoose";
    private GridView grid;
    //支出icon
    private String[] text = {"食", "衣", "住", "行", "育", "樂"};
    private int[] imageId = {R.drawable.eat, R.drawable.clothes, R.drawable.home, R.drawable.moto, R.drawable.study, R.drawable.game};
    //收入icon
    private String[] IncomeText = {"薪水", "副業", "股票", "利息", "網拍", "其他"};
    private int[] IncomeImageId = {R.drawable.salary, R.drawable.work2, R.drawable.stock, R.drawable.bank, R.drawable.shopping, R.drawable.other2};

    ArrayList<Map<String, Object>> items = new ArrayList<Map<String, Object>>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.main_fragment, container, false);

        findView(view);
        setGrid();

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v(TAG, "點選: " + position);

                clickImage(position);
            }
        });
        return view;
    }

    private void findView(View v) {
        grid = (GridView) v.findViewById(R.id.grid_view);
        grid.setNumColumns(3);
    }

    public void setGrid() {
        for (int i = 0; i < imageId.length; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            if (MainActivity.GetInstance().getIsExpend()) {
                item.put("image", imageId[i]);
                item.put("text", text[i]);
            } else if (MainActivity.GetInstance().getIsIncome()) {
                item.put("image", IncomeImageId[i]);
                item.put("text", IncomeText[i]);
            }
            items.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(this.getContext(), items, R.layout.grid_item, new String[]{"image", "text"}, new int[]{R.id.grid_image, R.id.grid_text});
        grid.setAdapter(adapter);
    }

    private void clickImage(int id) {
        /*食 衣 住 行 育 樂*/
        /*薪水 副業 股票 利息 網拍 其他*/
        switch (id) {
            case 0:
                if (MainActivity.GetInstance().getIsExpend()){
                    MainActivity.GetInstance().setClickID("food");
                }else {
                    MainActivity.GetInstance().setClickID("salary");
                }
                break;
            case 1:
                if (MainActivity.GetInstance().getIsExpend()){
                    MainActivity.GetInstance().setClickID("clothes");
                }else {
                    MainActivity.GetInstance().setClickID("work");
                }
                break;
            case 2:
                if (MainActivity.GetInstance().getIsExpend()){
                    MainActivity.GetInstance().setClickID("home");
                }else {
                    MainActivity.GetInstance().setClickID("stock");
                }
                break;
            case 3:
                if (MainActivity.GetInstance().getIsExpend()){
                    MainActivity.GetInstance().setClickID("walk");
                }else {
                    MainActivity.GetInstance().setClickID("bank");
                }
                break;
            case 4:
                if (MainActivity.GetInstance().getIsExpend()){
                    MainActivity.GetInstance().setClickID("study");
                }else {
                    MainActivity.GetInstance().setClickID("shopping");
                }
                break;
            case 5:
                if (MainActivity.GetInstance().getIsExpend()){
                    MainActivity.GetInstance().setClickID("game");
                }else {
                    MainActivity.GetInstance().setClickID("other");
                }
                break;
        }

        MainActivity.GetInstance().initFragmentMoney();
    }
}

