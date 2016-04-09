package com.sun.bingo.framework.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.sun.bingo.framework.proxy.MessageProxy;
import com.sun.bingo.framework.proxy.ModelMap;
import com.sun.bingo.framework.proxy.common.IRefreshBack;
import com.sun.bingo.framework.proxy.helper.ActivityHelper;
import com.umeng.analytics.MobclickAgent;

import de.devland.esperandro.Esperandro;

public class BaseAsyncActivity<T extends BaseControl> extends AppCompatActivity implements IRefreshBack {

    protected T mControl;
    protected MessageProxy messageProxy;
    protected ModelMap mModel;
    private ActivityHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new ActivityHelper<T, BaseAsyncActivity>(this);
        mHelper.onCreate();
        initVar();
        MobclickAgent.openActivityDurationTrack(false);
    }

    public void initVar() {
        mModel = mHelper.getModelMap();
        messageProxy = mHelper.getMessageProxy();
        mControl = (T) mHelper.getControl();
    }

    @Override
    protected void onStart() {
        mHelper.onStart();
        super.onStart();
    }

    @Override
    protected void onResume() {
        mHelper.onResume();
        super.onResume();
        MobclickAgent.onPageStart("SplashScreen");
        MobclickAgent.onResume(this); //友盟统计
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mHelper.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHelper.onPause();
        MobclickAgent.onPageEnd("SplashScreen");
        MobclickAgent.onPause(this); //友盟统计
    }

    @Override
    protected void onStop() {
        mHelper.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mHelper.onDestroy();
        super.onDestroy();
    }

    protected boolean isPaused() {
        return mHelper.isPause();
    }

    protected <P> P getSharedPreferences(Class<P> spClass) {
        return Esperandro.getPreferences(spClass, this);
    }

    @Override
    public void onRefresh(int requestCode, Bundle bundle) {

    }

}
