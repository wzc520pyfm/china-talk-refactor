package com.baidu.duer.chinatalk_refactor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity {

    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed_7zip.apk";
    private Context mContext;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this.getApplicationContext();

        final RxPermissions rxPermissions = new RxPermissions(this); // where this is an Activity or Fragment instance
        // 动态请求文件权限--文件读写权限,麦克风权限
        rxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                         Manifest.permission.WRITE_EXTERNAL_STORAGE,
                         Manifest.permission.RECORD_AUDIO)
                .subscribe(granted -> {
                    if (granted) {
                        // All requested permissions are granted
                        Toast.makeText(mContext, "获取权限,可自由发挥", Toast.LENGTH_SHORT).show();
                    } else {
                        // At least one permission is denied
                        Toast.makeText(mContext, "权限被禁,发挥受限", Toast.LENGTH_SHORT).show();
                    }
                });

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    /**
     * 加载热补丁插件
     */
    public void loadPatch(View v) {
        TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), path);
    }

    /**
     * 查看补丁信息
     */
    public void showInfo(View v) {
        // add more Build Info
        final StringBuilder sb = new StringBuilder();
        Tinker tinker = Tinker.with(getApplicationContext());
        if (tinker.isTinkerLoaded()) {
            sb.append(String.format("[补丁已加载] \n"));
            sb.append(String.format("[基准包版本号] %s \n", BuildConfig.TINKER_ID));

            sb.append(String.format("[补丁号] %s \n", tinker.getTinkerLoadResultIfPresent().getPackageConfigByName(ShareConstants.TINKER_ID)));
            sb.append(String.format("[补丁版本] %s \n", tinker.getTinkerLoadResultIfPresent().getPackageConfigByName("patchVersion")));
            sb.append(String.format("[补丁占用空间] %s k \n", tinker.getTinkerRomSpace()));

        } else {
            sb.append(String.format("[补丁未加载] \n"));
            sb.append(String.format("[基准包版本号] %s \n", BuildConfig.TINKER_ID));

            sb.append(String.format("[TINKER_ID] %s \n", ShareTinkerInternals.getManifestTinkerID(getApplicationContext())));
        }

        // FIXME 显示sb信息
    }

    /**
     * 清除补包
     */
    public void cleanPatch(View v){
        Tinker.with(getApplicationContext()).cleanPatch();
    }
}