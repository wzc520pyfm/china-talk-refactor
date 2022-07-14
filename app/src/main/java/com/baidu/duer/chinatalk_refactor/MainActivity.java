package com.baidu.duer.chinatalk_refactor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.ImageAssetDelegate;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieImageAsset;
import com.baidu.duer.chinatalk_refactor.base.BaseActivity;
import com.baidu.duer.chinatalk_refactor.base.BaseData;
import com.baidu.duer.chinatalk_refactor.utils.LanguageUtil;
import com.baidu.duer.chinatalk_refactor.utils.SharedUtil;
import com.chenenyu.router.Router;
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
import androidx.annotation.RawRes;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 不要在主线程中访问网络
 */
@Route("home")
public class MainActivity extends BaseActivity {

    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.nav_view)
    BottomNavigationView navView;
    @BindString(R.string.changeLang)
    String changeLangText;
    @BindArray(R.array.languages)
    String [] languages;
    @BindArray(R.array.lang)
    String [] lang;
    private QMUIPopup languagePopup;
    private static boolean ISFIRST = true;
    /**
     * 记录上一次被激活的navItem
     */
    private Integer actived = 0;
    private SharedUtil sharedUtil = SharedUtil.getInstance();
    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed_7zip.apk";
    private Context mContext;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        ButterKnife.bind(this);

        if (ISFIRST) { // 执行一次语言初始化
            ISFIRST = false;
            resetLanguage(sharedUtil.readShared("language", "en"));
        }

        final RxPermissions rxPermissions = new RxPermissions(this); // where this is an Activity or Fragment instance
        // 动态请求文件权限--文件读写权限,麦克风权限
        rxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO)
                .subscribe(granted -> {
                    if (granted) {
                        // All requested permissions are granted
                        Toast.makeText(this, "获取权限,可自由发挥", Toast.LENGTH_SHORT).show();
                    } else {
                        // At least one permission is denied
                        Toast.makeText(this, "权限被禁,发挥受限", Toast.LENGTH_SHORT).show();
                    }
                });
        QMUIStatusBarHelper.translucent(this);
        if(sharedUtil.readShared("token", "").equals("")) {
            Router.build("login").go(this);
        }
        initTopBar();
        initNavBar();
    }
    /**
     * 初始化NavBar
     */
    private void initNavBar() {
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_exam, R.id.navigation_my)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        // 导航切换监听
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId() == R.id.navigation_home) {
                    resetTopBarTitle(getString(R.string.title_home));
                    setNavItem(0);
                    actived = 0;
                } else if(destination.getId() == R.id.navigation_exam) {
                    resetTopBarTitle(getString(R.string.title_exam));
                    setNavItem(1);
                    actived = 1;
                } else if(destination.getId() == R.id.navigation_my) {
                    resetTopBarTitle(getString(R.string.title_my));
                    setNavItem(2);
                    actived = 2;
                }
            }
        });
        // 默认选中第一项
        navView.setSelectedItemId(0);
        setSilence(1);
        setSilence(2);
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
        mTopBar.addRightTextButton(changeLangText, QMUIViewHelper.generateViewId())
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
     * 设置navItem为激活状态
     */
    private void setNavItem(Integer index) {
        playLottie(index, BaseData.getNavItemsActiveLottieAnimation()[index], 1);
        if(!actived.equals(index)) {
            setSilence(actived);
        }
    }
    /**
     * 设置navItem为沉默状态
     */
    private void setSilence(Integer index) {
        playLottie(index, BaseData.getNavItemsSilenceLottieAnimation()[index], 1, 90, 90);
    }

    /**
     * 显示切换语言列表
     */
    void showSelectLanguage(View v) {
        List<String> data = new ArrayList<>();
        Collections.addAll(data, languages);

        ArrayAdapter adapter = new ArrayAdapter<>(mContext, R.layout.simple_list_item, data);
        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(mContext, languages[i], Toast.LENGTH_SHORT).show();
                resetLanguage(lang[i]);
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
     * 播放Lottie动画
     */
    private void playLottie(Integer index, @RawRes int resId, Integer speed) {
        LottieDrawable lottieDrawable = new LottieDrawable();
        LottieComposition.Factory.fromRawFile(mContext,resId,composition -> {
            lottieDrawable.setComposition(composition);
        });
        navView.getMenu().getItem(index).setIcon(lottieDrawable);
        lottieDrawable.setSpeed(speed);
        lottieDrawable.start();
    }
    private void playLottie(Integer index, @RawRes int resId, Integer speed, Integer min, Integer max) {
        LottieDrawable lottieDrawable = new LottieDrawable();
        LottieComposition.Factory.fromRawFile(mContext,resId,composition -> {
            lottieDrawable.setComposition(composition);
        });
        navView.getMenu().getItem(index).setIcon(lottieDrawable);
        lottieDrawable.setSpeed(speed);
        lottieDrawable.setMinAndMaxFrame(min, max);
        lottieDrawable.start();
    }

    /**
     * 保存设置的语言
     */
    private void resetLanguage(String language){
        //设置的语言、重启的类一般为应用主入口（微信也是到首页）
        LanguageUtil.changeAppLanguage(this, language, MainActivity.class);
        //保存设置的语言
        sharedUtil.writeShared("language", language);
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