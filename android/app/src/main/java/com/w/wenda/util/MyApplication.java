package com.w.wenda.util;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.w.wenda.R;
import com.w.wenda.greendao.DaoMaster;
import com.w.wenda.greendao.DaoSession;
import com.w.wenda.pojo.User;

import org.greenrobot.greendao.database.Database;

import java.io.File;

public class MyApplication extends Application {
    public final static String DATABASE_NAME = "message_record";
    public static DaoSession daoSession;
    public static Context applicationContext;
    public User u;

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }

    private static MyApplication app;

    public static MyApplication getApp() {
        if (app == null) {
            app = new MyApplication();
        }
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher).cacheInMemory(true)
                .cacheOnDisc(true).build();
        File cacheDir = StorageUtils.getOwnCacheDirectory(this,
                "imageloader/Cache");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext()).defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);

        setupDataBase();//初始化greendao的sqlite数据库
    }

    void setupDataBase(){
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(applicationContext,DATABASE_NAME);
        Database db = openHelper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession(){
        return daoSession;
    }

}
