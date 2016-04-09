package com.netease.hzinstitute.myemmagee.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hzhuangkeqing on 2015/6/25 0025.
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String App_SQL = "create table App ("
            + "id integer primary key autoincrement,"
            + "app text, "//短名
            + "package text, "//长名
            + "version text, "//应用版本versionName
            + "icon text, "//图标url
            + "minVer integer, "//最低版本
            + "tarVer integer, "//目标版本
            + "manualCount integer, "//手动测试次数
            + "monkeyCount integer, "//monkey测试次数
            + "isUpload integer)";//是否上传icon0否1是
    private static final String Test_SQL = "create table Test ("
            + "id integer primary key autoincrement, "
            + "uploadId text, "//网上的testId
            + "num text, "//该次测试包含多少detail
            + "app text, "//短名
            + "package text, "//长名
            + "date text, "//测试日期
            + "isLog integer, "//是否记录日志 若记录路径为R.string.log_save_path+uploadId
            + "isDeleted integer, "//是否删除
            + "type integer)";//0manual1monkey
    private static final String Monkey_SQL = "create table Monkey ("
            + "id integer primary key autoincrement, "
            + "testId text, "//测试id
            + "date text, "//测试日期
            + "ip text, "//ip地址
            + "seed text, "//种子
            + "package text, "//长名
            + "timeout text, "//延时
            + "sum text,"//总数
            + "filename text)";//文件名
    private static final String Detail_SQL = "create table Detail ("
            + "id integer primary key autoincrement, "
            + "testId text, "
            + "DateTime text, "
            + "TopActivity text, "
            + "HeapData text, "
            + "pMemory text, "
            + "percent text, "
            + "fMemory text, "
            + "processCpuRatio text, "
            + "totalCpuBuffer text, "
            + "trafValue text, "
            + "totalBatt text, "
            + "currentBatt text, "
            + "temperature text, "
            + "voltage text, "
            + "screenshot text)";

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(App_SQL);
        db.execSQL(Test_SQL);
        db.execSQL(Detail_SQL);
        db.execSQL(Monkey_SQL);
        //Toast.makeText(mContext,"Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //SQLite数据库更新
        /*switch(oldVersion){
            case 1:
                db.execSQL(Detail_SQL);
            default:
        }*/
    }
}
