package cn.zzu.takeout.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

import cn.zzu.takeout.utils.MyApplication;

/**
 * Created by Teacher on 2016/9/2.
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASENAME = "itheima.db";
    private static final int DATABASEVERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }

    /**
     * 单例处* 双重家攀岩:提高效率
     * 如果方法上家加锁,,每次调用都需要排队
     */

    private static DBHelper instance;

    public static DBHelper getInstance() {
        if (instance == null) {//第一层校验:提高效率
            synchronized (DBHelper.class) {
                if (instance == null) {//第二层校验/.防止对象的多次创建
                    return new DBHelper(MyApplication.getmContext());

                }
            }
        }
        return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        /*try {
            TableUtils.createTable(connectionSource, UserBean.class);
            TableUtils.createTable(connectionSource, AddressBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
