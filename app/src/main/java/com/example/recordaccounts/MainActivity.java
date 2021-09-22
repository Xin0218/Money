package com.example.recordaccounts;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "MainActivity";
    private Button showAllMoney; //顯示
    private TextView headerTitle; //header文字
    private ImageView back; //返回image
    private FrameLayout mainFragment, bottomFragment; //切換用Fragment
    private TextView expend, income; //支出、收入文字
    private Boolean isIncome = false, isExpend = true;
    public String clickID;
    private HashMap<String, Integer> map = new HashMap<String, Integer>();
    private ArrayList<HashMap> arrayList = new ArrayList<HashMap>();

    /*Google Ad*/
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    /*SQL*/
    private final String DB_NAME = "MyTest.db";
    private String TABLE_NAME = "MyTable";
    private final int DB_VERSION = 1;
    private SQLiteDatabase db;
    DBHelper mDBHelper;

    private FragmentChoose choose;
    private FragmentMoney money;

    public static MainActivity mainActivity = null;

    public static MainActivity GetInstance() {
        return mainActivity;
    }

    public MainActivity() {
        mainActivity = MainActivity.this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDBHelper = new DBHelper(this, DB_NAME, null, DB_VERSION);
        db = mDBHelper.getWritableDatabase();

        findView();
        initFragmentChoose();
        showAllMoney.setVisibility(View.VISIBLE);
        showAD();
    }

    private void findView() {
        Log.v(TAG, "findView: ");
        headerTitle = findViewById(R.id.header_text);
        back = findViewById(R.id.back);
        mainFragment = findViewById(R.id.main_fragment);
        expend = findViewById(R.id.text_expend);
        income = findViewById(R.id.text_income);
        showAllMoney = findViewById(R.id.show_total_money);

        showAllMoney.setOnClickListener(this);
        back.setOnClickListener(this);
        expend.setOnClickListener(this);
        income.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.v(TAG, "Click ID: " + v.getId());
        switch (v.getId()) {
            case R.id.back:
                Log.v(TAG, "onClick_back");
                initFragmentChoose();
                break;
            case R.id.text_income:
                expend.setBackground(null);
                income.setBackground(getDrawable(R.drawable.setbar_bg));
                isIncome = true;
                isExpend = false;
                initFragmentChoose();
                break;
            case R.id.text_expend:
                income.setBackground(null);
                expend.setBackground(getDrawable(R.drawable.setbar_bg));
                isExpend = true;
                isIncome = false;
                initFragmentChoose();
                break;
            case R.id.show_total_money:
                Intent intent = new Intent(this, TotalMoney.class);
                startActivity(intent);
                break;
        }
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (choose != null) {
            transaction.hide(choose);
        }
        if (money != null) {
            transaction.hide(money);
        }
    }

    public void initFragmentChoose() {
        Log.v(TAG, "initFragmentChoose: ");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (choose != null) {
            transaction.remove(choose);
        }
        choose = new FragmentChoose();
        transaction.add(R.id.main_fragment, choose);
        hideFragment(transaction);
        transaction.show(choose);
        transaction.commit();
    }

    public void initFragmentMoney() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mainActivity == null) {
            mainActivity = new MainActivity();
        }
        if (money != null) {
            transaction.remove(money);
        }
        money = new FragmentMoney();
        transaction.add(R.id.bottom_fragment, money);

        transaction.show(money);
        transaction.commit();
    }

    private void showAD(){
        MobileAds.initialize(this);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void setClickID(String clickID) {
        this.clickID = clickID;
    }

    public String getClickID() {
        return clickID;
    }

    public Boolean getIsIncome() {
        return isIncome;
    }

    public Boolean getIsExpend() {
        return isExpend;
    }

    public HashMap<String, Integer> getMap() {
        return this.map;
    }

    public ArrayList<HashMap> getArrayList() {
        return this.arrayList;
    }

    public DBHelper getmDBHelper() {
        return mDBHelper;
    }
}