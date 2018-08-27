package com.hc.android.huixin;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hc.android.huixin.util.MobileInfoUtils;
import com.hc.android.huixin.util.PreferenceUtil;

public class PermissionsSettingsActivity extends Activity implements View.OnClickListener{
	private TextView tv_location;
	private Context context;
    private ImageButton regulatory_back;
    private Button btnBattery,btnAutoStart;
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_permissions_settings);
        btnBattery = (Button) findViewById(R.id.btnBattery);
        btnAutoStart = (Button) findViewById(R.id.btnAutoStarty);
        regulatory_back = (ImageButton) findViewById(R.id.regulatory_back);
        btnBattery.setOnClickListener(this);
        btnAutoStart.setOnClickListener(this);
        regulatory_back.setOnClickListener(this);
       /* LocationManager locationManager;
        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager)getSystemService(serviceName); // 查找到服务信息
        //locationManager.setTestProviderEnabled("gps", true);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, mLocationListener01);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, mLocationListener01)*/;
    }
/* 通过location获取当前设备的具体位置
   *
           * @param location
   * @param rowId
   * @param params
   * @param rowId
   */
    private Location updateToNewLocation(Location location) {
        System.out.println("--------zhixing--2--------");
        String latLongString;
        double lat = 0;
        double lng=0;

        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            latLongString = "纬度:" + lat + "\n经度:" + lng;
            System.out.println("经度："+lng+"纬度："+lat);
        } else {
            latLongString = "无法获取地理信息，请稍后...";
        }
        if(lat!=0){
            System.out.println("--------反馈信息----------"+ String.valueOf(lat));
        }

        Toast.makeText(getApplicationContext(), latLongString, Toast.LENGTH_SHORT).show();

        return location;

    }

    // 设置监听器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米
    public final LocationListener mLocationListener01 = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            updateToNewLocation(location);
        }


        @Override
        public void onProviderDisabled(String provider) {
            updateToNewLocation(null);
        }

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBattery:
                PreferenceUtil.saveIsBattery(true);
                MobileInfoUtils.jumpBattery(this);
                break;
            case R.id.btnAutoStarty:
                PreferenceUtil.saveIsAutoStart(true);
                MobileInfoUtils.jumpAutoStarty(this);
                break;
            case R.id.regulatory_back:
                finish();
                break;
            default:
                break;
        }
    }
}
