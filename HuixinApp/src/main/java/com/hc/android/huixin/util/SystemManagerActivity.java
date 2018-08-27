package com.hc.android.huixin.util;

import java.util.ArrayList;

/**
 *
 */

public class SystemManagerActivity {
    public static final ArrayList<PackageAndActivity> xiaomi_Battery = new ArrayList<PackageAndActivity>() {{
        add(new PackageAndActivity("com.miui.powerkeeper", "com.miui.powerkeeper.ui.HiddenAppsConfigActivity"));
        add(new PackageAndActivity("com.miui.powerkeeper", "com.miui.powerkeeper.ui.HiddenAppsContainerManagementActivity"));
    }};
    public static final ArrayList<PackageAndActivity> xiaomi_AutoStart = new ArrayList<PackageAndActivity>() {{
        add(new PackageAndActivity("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
    }};

    public static final ArrayList<PackageAndActivity> Letv_Battery = new ArrayList<PackageAndActivity>() {{
        add(new PackageAndActivity("com.miui.powerkeeper", "com.miui.powerkeeper.ui.HiddenAppsConfigActivity"));
    }};
    public static final ArrayList<PackageAndActivity> Letv_AutoStart = new ArrayList<PackageAndActivity>() {{
        add(new PackageAndActivity("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
        add(new PackageAndActivity("com.miui.powerkeeper", "com.miui.powerkeeper.ui.HiddenAppsContainerManagementActivity"));
    }};
    public static final ArrayList<PackageAndActivity> Huawei_Battery = new ArrayList<PackageAndActivity>() {{
        add(new PackageAndActivity("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity"));
    }};
    public static final ArrayList<PackageAndActivity> Huawei_AutoStart = new ArrayList<PackageAndActivity>() {{
        add(new PackageAndActivity("com.huawei.systemmanager", "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity"));
        add(new PackageAndActivity("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupAwakedAppListActivity"));
        add(new PackageAndActivity("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.bootstart.BootStartActivity"));
        add(new PackageAndActivity("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"));
    }};
    public static final ArrayList<PackageAndActivity> Coolpad_Battery = new ArrayList<PackageAndActivity>() {{
    }};
    public static final ArrayList<PackageAndActivity> Coolpad_AutoStart = new ArrayList<PackageAndActivity>() {{
        add(new PackageAndActivity("com.yulong.android.security", "com.yulong.android.seccenter.tabbarmain"));
    }};
    public static final ArrayList<PackageAndActivity> Lenovo_Battery = new ArrayList<PackageAndActivity>() {{
        add(new PackageAndActivity("com.lenovo.powersetting", "com.lenovo.powersetting.PowerSettingActivity"));
    }};
    public static final ArrayList<PackageAndActivity> Lenovo_AutoStart = new ArrayList<PackageAndActivity>() {{
        add(new PackageAndActivity("com.lenovo.security", "com.lenovo.security.homepage.HomePageActivity"));
    }};
    public static final ArrayList<PackageAndActivity> Meizu_Battery = new ArrayList<PackageAndActivity>() {{
        add(new PackageAndActivity("com.meizu.safe", "com.meizu.safe.powerui.PowerAppPermissionActivity"));
    }};
    public static final ArrayList<PackageAndActivity> Meizu_AutoStart = new ArrayList<PackageAndActivity>() {{
        add(new PackageAndActivity("com.meizu.safe", "com.meizu.safe.security.HomeActivity"));
        add(new PackageAndActivity("com.meizu.safe", "com.meizu.safe.permission.SmartBGActivity"));
        add(new PackageAndActivity("com.meizu.safe", "com.meizu.safe.permission.PermissionMainActivity"));
    }};
    public static final ArrayList<PackageAndActivity> Oppo_Battery = new ArrayList<PackageAndActivity>() {{
        add(new PackageAndActivity("com.coloros.oppoguardelf", "com.coloros.oppoguardelf.MonitoredPkgActivity"));
        add(new PackageAndActivity("com.coloros.oppoguardelf", "com.coloros.powermanager.fuelgaue.PowerUsageModelActivity"));
        add(new PackageAndActivity("com.coloros.oppoguardelf", "com.coloros.powermanager.fuelgaue.PowerConsumptionActivity"));
    }};
    public static final ArrayList<PackageAndActivity> Oppo_AutoStart = new ArrayList<PackageAndActivity>() {{
        add(new PackageAndActivity("com.coloros.safecenter", "com.coloros.safecenter.newrequest.SecurityScanActivity"));
        add(new PackageAndActivity("com.coloros.safecenter", "com.coloros.privacypermissionsentry.PermissionTopActivity"));
        add(new PackageAndActivity("com.coloros.safecenter", "com.coloros.safecenter.startupapp.StartupAppListActivity"));
        add(new PackageAndActivity("com.coloros.safecenter", "com.coloros.safecenter.permission.startupapp.StartupAppListActivity"));
        add(new PackageAndActivity("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity"));
        add(new PackageAndActivity("com.color.safecenter", "com.color.safecenter.permission.startup.StartupAppListActivity"));
    }};
    public static final ArrayList<PackageAndActivity> Samsung_Battery = new ArrayList<PackageAndActivity>() {{
        add(new PackageAndActivity("com.samsung.android.lool", "com.samsung.android.sm.ui.battery.BatteryActivity"));
        add(new PackageAndActivity("com.samsung.android.lool", "com.samsung.android.sm.ui.battery.AppSleepListActivity"));
        add(new PackageAndActivity("com.samsung.android.sm_cn", "com.samsung.android.sm.ui.battery.BatteryActivity"));
        add(new PackageAndActivity("com.samsung.android.sm_cn", "com.samsung.android.sm.ui.battery.AppSleepListActivity_dim"));
    }};
    public static final ArrayList<PackageAndActivity> Samsung_AutoStart = new ArrayList<PackageAndActivity>() {{
        add(new PackageAndActivity("com.samsung.android.sm_cn", "com.samsung.android.sm.ui.cstyleboard.SmartManagerDashBoardActivity"));
        add(new PackageAndActivity("com.samsung.android.sm_cn", "com.samsung.android.sm.ui.appmanagement.AppManagementActivity"));
        add(new PackageAndActivity("com.samsung.android.sm_cn", "com.samsung.android.sm.ui.ram.AutoRunActivity"));
        add(new PackageAndActivity("com.samsung.android.sm ", "com.samsung.android.sm.app.dashboard.SmartManagerDashBoardActivity"));
    }};
    public static final ArrayList<PackageAndActivity> Smartisan_Battery = new ArrayList<PackageAndActivity>() {{
        add(new PackageAndActivity("com.android.settings", "com.android.settings.fuelgauge.PowerUsageSummaryActivity"));
        add(new PackageAndActivity("com.android.settings", "com.android.settings.fuelgauge.appBatteryUseOptimization.BatteryUseOptimizationActivity"));
        add(new PackageAndActivity("com.android.settings","com.android.settings.fuelgauge.appBatteryUseOptimization.AppBatteryUseOptimizationActivit"));
    }};
    public static final ArrayList<PackageAndActivity> Smartisan_AutoStart = new ArrayList<PackageAndActivity>() {{
        add(new PackageAndActivity("com.smartisanos.security", "com.smartisanos.security.MainActivity"));
        add(new PackageAndActivity("com.smartisanos.security", "com.smartisanos.security.PackagesOverview"));
    }};
    public static final ArrayList<PackageAndActivity> Vivo_Battery = new ArrayList<PackageAndActivity>() {{
        add(new PackageAndActivity("com.vivo.abe", "com.vivo.applicationbehaviorengine.ui.ExcessivePowerManagerActivity"));
    }};
    public static final ArrayList<PackageAndActivity> Vivo_AutoStart = new ArrayList<PackageAndActivity>() {{
        add(new PackageAndActivity("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager"));
        add(new PackageAndActivity("com.iqoo.secure", "com.vivo.permissionmanager.activity.PurviewTabActivity"));
    }};
    public static final ArrayList<PackageAndActivity> Zte_Battery = new ArrayList<PackageAndActivity>() {{
    }};
    public static final ArrayList<PackageAndActivity> Zte_AutoStart = new ArrayList<PackageAndActivity>() {{
        add(new PackageAndActivity("com.zte.powersavemode", "com.zte.powersavemode.autorun.AppAutoRunManager"));
    }};
}
