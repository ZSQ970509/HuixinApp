package com.hc.android.huixin.util;

import java.util.ArrayList;

/**
 * 手机类型
 */

public enum MobileTypeEnum {
    XIAOMI(SystemManagerActivity.xiaomi_Battery, SystemManagerActivity.xiaomi_AutoStart),
    HUAWEI(SystemManagerActivity.Huawei_Battery, SystemManagerActivity.Huawei_AutoStart),
    COOLPAD(SystemManagerActivity.Coolpad_Battery, SystemManagerActivity.Coolpad_AutoStart),
    LENOVO(SystemManagerActivity.Lenovo_Battery, SystemManagerActivity.Lenovo_AutoStart),
    MEIZU(SystemManagerActivity.Meizu_Battery, SystemManagerActivity.Meizu_AutoStart),
    OPPO(SystemManagerActivity.Oppo_Battery, SystemManagerActivity.Oppo_AutoStart),
    SAMSUNG(SystemManagerActivity.Samsung_Battery, SystemManagerActivity.Samsung_AutoStart),
    SMARTISAN(SystemManagerActivity.Smartisan_Battery, SystemManagerActivity.Smartisan_AutoStart),
    VIVO(SystemManagerActivity.Vivo_Battery, SystemManagerActivity.Vivo_AutoStart),
    ZTE(SystemManagerActivity.Zte_Battery, SystemManagerActivity.Zte_AutoStart),
    LETV(SystemManagerActivity.Letv_Battery, SystemManagerActivity.Letv_AutoStart);

    private ArrayList<PackageAndActivity> mBattery;
    private ArrayList<PackageAndActivity> mAutoStarty;

    MobileTypeEnum(ArrayList<PackageAndActivity> battery, ArrayList<PackageAndActivity> autoStarty) {
        mBattery = battery;
        mAutoStarty = autoStarty;
    }

    public ArrayList<PackageAndActivity> getmBattery() {
        return mBattery;
    }

    public ArrayList<PackageAndActivity> getmAutoStarty() {
        return mAutoStarty;
    }
}
