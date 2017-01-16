package com.donkor.demo.mobsms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import cn.smssdk.SMSSDK;

/**
 * 国家代码
 * Created by donkor
 */
public class CountryCodeActivity extends Activity {

    private SideBar sideBar;
    private TextView dialog;
    private ListView sortListView;
    private CountryAdapter countryAdapter;
    private Button btnBack;
    private ArrayList<Country> countryList;
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private CountryPinyinComparator pinyinComparator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_country_code);

        SMSSDK.initSDK(this, "167cf953112d0", "538346cc0f97e65a042adfec13badc72");
        pinyinComparator = new CountryPinyinComparator();

        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);
        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        btnBack = (Button) findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountryCodeActivity.this.finish();
            }
        });

        GetCountryList list = new GetCountryList();
        countryList = list.getCountry(SMSSDK.getGroupedCountryList());
//        Log.e("asd", "zone.size(): " + zone.size());
        // 根据a-z进行排序源数据
        Collections.sort(countryList, pinyinComparator);
//        adapter = new SortAdapter(getActivity(), SourceDateList);

        countryAdapter = new CountryAdapter(CountryCodeActivity.this, countryList);
        sortListView.setAdapter(countryAdapter);

//        设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = countryAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }
            }
        });

        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(CountryCodeActivity.this, MainActivity.class);
                Bundle b = new Bundle();
                String country = countryList.get(position).getCountry();
                String countryCode = countryList.get(position).getCountryCode().replace("+","");
                b.putString("country", country);
                b.putString("countryCode", countryCode);
                mIntent.putExtras(b);
                CountryCodeActivity.this.setResult(1, mIntent);
                CountryCodeActivity.this.finish();
            }
        });
    }


}