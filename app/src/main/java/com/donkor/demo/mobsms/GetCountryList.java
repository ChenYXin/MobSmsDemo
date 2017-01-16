package com.donkor.demo.mobsms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import cn.smssdk.SMSSDK;

public class GetCountryList {

    private HashMap<Character, ArrayList<String[]>> first;
    private ArrayList<String[]> second;
//    String countyList="\n";
    private ArrayList<Country> dataList;
    // 汉字转换成拼音的类
    private CharacterParser characterParser;

    @SuppressWarnings("rawtypes")
    public ArrayList<Country> getCountry(HashMap<Character, ArrayList<String[]>> list){
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        dataList=new ArrayList<>();
        first = SMSSDK.getGroupedCountryList();
        Set set=first.keySet();
        for(Object obj : set){
//            System.out.println("第一层"+first.get(obj));
            second=first.get(obj);
//            System.out.println("第二层"+second);
            for(int i = 0;i<second.toArray().length;i++){
                Country country=new Country();
                String[] thirst =second.get(i);
                country.setCountry(thirst[0]);
                country.setCountryCode("+"+thirst[1]);

                //汉字转换成拼音
                String pinyin = characterParser.getSelling(thirst[0]);
                String sortString = pinyin.substring(0, 1).toUpperCase();

                // 正则表达式，判断首字母是否是英文字母
                if (sortString.matches("[A-Z]")) {
                    country.setSortLetters(sortString.toUpperCase().toUpperCase());
                } else {
                    country.setSortLetters("#");
                }

                dataList.add(country);
//                System.out.println("第三层"+thirst);
//                System.out.println("实际数据"+thirst[0]+"国家"+thirst[1]);
//                String str=thirst[0]+"----------"+thirst[1]+"区域号\n";
//                countyList = countyList+str;
            }
        }
        return dataList;
    }
}
