package com.example.coolweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.coolweather.model.City;
import com.example.coolweather.model.Country;
import com.example.coolweather.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xxz on 2016/9/18.
 */
public class CoolWeatherDB {
    public static final String DB_NAME = "cool_weather";
    public static final int VERSION = 1;
    private static CoolWeatherDB coolWeatherDB;
    private SQLiteDatabase db;
   /**
    * 构造函数私有化
   */
    private CoolWeatherDB(Context context){
        CoolWeatherOpenHelper dbHelper=new CoolWeatherOpenHelper(context,DB_NAME,null,VERSION);
        db = dbHelper.getWritableDatabase();
    }
    /**
     * 获取coolWeatherDB实例
     */
    public synchronized static CoolWeatherDB getInstance(Context context){
        if(coolWeatherDB == null){
            coolWeatherDB = new CoolWeatherDB(context);
        }
        return coolWeatherDB;
    }
    /**
     * 将province实例存储到数据库
     */
    public void saveProvince(Province province){
        if(province != null){
            ContentValues values = new ContentValues();
            values.put("province_name",province.getProvinceName());
            values.put("province_code",province.getProvinceCode());
            db.insert("Province",null,values);
        }
    }
    /**
     * 从数据库读取全国所有的省份信息
     */
    public List<Province> loadProvince(){
       List<Province> list = new ArrayList<Province>();
        Cursor cursor = db.query("Province",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);

            }while(cursor.moveToNext());
        }
        if(cursor != null){
            cursor.close();
        }
        return list;
    }
    /**
     * 将city实例存储到数据库
     */
    public void saveCity(City city){
        if(city != null){
            ContentValues values = new ContentValues();
            values.put("city_name",city.getCityName());
            values.put("city_code",city.getCityCode());
            values.put("province_id",city.getProvinceId());
            db.insert("City",null,values);
        }
    }
    /**
     * 从数据库读取某省内所有的城市信息
     */
    public List<City> loadCities(int provinceId){
        List<City> list=new ArrayList<City>();
        Cursor cursor = db.query("City",null,"province_id = ?",new String[] {String.valueOf(provinceId)},null,null,null);
        if (cursor.moveToFirst()){
            do{
                City city = new City();
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(provinceId);
                list.add(city);

            }while(cursor.moveToNext());
        }if(cursor != null){
            cursor.close();
        }
        return list;
    }
    /**
     * 将country实例存储到数据库
     */
    public void saveCountry(Country country){
        ContentValues values = new ContentValues();
        values.put("country_name",country.getCountryName());
        values.put("country_code",country.getCountryCode());
        values.put("city_id",country.getCityId());
        db.insert("Country",null,values);
    }
    /**
     * 从数据库读取某城市内所有的县信息
     */
   public  List<Country> loadCountries(int cityId){
       List<Country> list = new ArrayList<Country>();
       Cursor cursor=db.query("Country",null,"city_id = ?",new String[] {String.valueOf(cityId)},null,null,null);
       if(cursor.moveToFirst()){
           do{
               Country country = new Country();
               country.setId(cursor.getInt(cursor.getColumnIndex("id")));
               country.setCountryName(cursor.getString(cursor.getColumnIndex("country_name")));
               country.setCountryCode(cursor.getString(cursor.getColumnIndex("country_code")));
               country.setCityId(cityId);
               list.add(country);

           }while(cursor.moveToNext());
       }
       if(cursor != null){
           cursor.close();
       }
       return  list;

   }

}
