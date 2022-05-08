package com.baidu.duer.chinatalk_refactor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.chenenyu.router.annotation.Route;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.qmuiteam.qmui.skin.QMUISkinHelper;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.skin.QMUISkinValueBuilder;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 不要在主线程中访问网络
 */
@Route("home")
public class MainActivity extends AppCompatActivity {

    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed_7zip.apk";
    private Context mContext;
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    private QMUIPopup languagePopup;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

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
                R.id.navigation_home, R.id.navigation_exam, R.id.navigation_my)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        // 使用NoActionBar主题就不能使用这条代码(这条代码的作用是根据fragment的title更新页面中间的文本显示, actionBar都没了哪来的title?)
        // NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        ButterKnife.bind(this);
        QMUIStatusBarHelper.translucent(this);
        initTopBar();
        // 导航切换监听
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId() == R.id.navigation_home) {
                    resetTopBarTitle(getString(R.string.title_home));
                } else if(destination.getId() == R.id.navigation_exam) {
                    resetTopBarTitle(getString(R.string.title_exam));
                } else if(destination.getId() == R.id.navigation_my) {
                    resetTopBarTitle(getString(R.string.title_my));
                }
            }
        });
        // 底部导航栏与fragment绑定
        NavigationUI.setupWithNavController(navView, navController);
    }

    /**
     * 初始化状态栏
     */
    private void initTopBar() {
        // mTopBar.setTitleGravity(Gravity.LEFT); // 左对齐
        mTopBar.setTitle(R.string.title_home);
        mTopBar.setBorderColor(getColor(R.color.color_theme_blue));
        mTopBar.addRightTextButton("切换语言", QMUIViewHelper.generateViewId())
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showSelectLanguage(v);
                    }
                });
    }

    /**
     * 重设设置状态栏title
     */
    private void resetTopBarTitle(String title) {
        mTopBar.setTitle(title);
    }

    /**
     * 显示切换语言列表
     */
    void showSelectLanguage(View v) {
        String[] listItems = getResources().getStringArray(R.array.languages);
        List<String> data = new ArrayList<>();

        Collections.addAll(data, listItems);

        ArrayAdapter adapter = new ArrayAdapter<>(mContext, R.layout.simple_list_item, data);
        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(mContext, listItems[i], Toast.LENGTH_SHORT).show();
                if (languagePopup != null) {
                    languagePopup.dismiss();
                }
            }
        };
        languagePopup = QMUIPopups.listPopup(mContext,
                QMUIDisplayHelper.dp2px(mContext, 250),
                QMUIDisplayHelper.dp2px(mContext, 300),
                adapter,
                onItemClickListener)
                .animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER)
                .preferredDirection(QMUIPopup.DIRECTION_TOP)
                .shadow(true)
                .offsetYIfTop(QMUIDisplayHelper.dp2px(mContext, 5))
                .skinManager(QMUISkinManager.defaultInstance(mContext))
                .onDismiss(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        Toast.makeText(mContext, "onDismiss", Toast.LENGTH_SHORT).show();
                    }
                })
                .show(v);
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