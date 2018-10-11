package tw.com.team13.maps;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import tw.com.team13.firebaselogin.R;

public class MapsFragment extends Fragment implements OnMapReadyCallback,LocationListener {
    private GoogleMap mMap; //宣告 google map 物件
    float zoom;
    Spinner spnGeoPoint,spnMapType;
    String[] Names= new String[] {"ubike","中式餐厅","西式餐厅"};
    private LocationManager locMgr;
    String bestProv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_maps, container, false);

        spnGeoPoint= view.findViewById(R.id.spnGeoPoint);
        // 建立 ArrayAdapter
        ArrayAdapter<String> adapterspnGeoPoint=new ArrayAdapter<String>
                (getActivity(),android.R.layout.simple_spinner_item,Names);


        // 設定 Spinner 顯示的格式
        adapterspnGeoPoint.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        // 設定 Spinner 的資料來源
        spnGeoPoint.setAdapter(adapterspnGeoPoint);





        // 利用 getSupportFragmentManager() 方法取得管理器
        assert getFragmentManager() != null;
        SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map);
        // 以非同步方式取得 GoogleMap 物件
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // 取得 GoogleMap 物件
        mMap = googleMap;
        LatLng Taipei101 = new LatLng(25.033611, 121.565000); // 台北 101
        zoom = 17;
        mMap.addMarker(new MarkerOptions().position(Taipei101).title("台北 101"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Taipei101, zoom));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);       // 一般地圖

        // 檢查授權
        requestPermission();
    }

    // 檢查授權
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {  // Androis 6.0 以上
            // 判斷是否已取得授權
            int hasPermission = ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {  // 未取得授權
                ActivityCompat.requestPermissions(getActivity(),new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return;
            }
        }
        // 如果裝置版本是 Androis 6.0 以下，
        // 或是裝置版本是6.0（包含）以上，使用者已經授權
        setMyLocation(); //  顯示定位圖層
    }

    // 使用者完成授權的選擇以後，會呼叫 onRequestPermissionsResult 方法
    //     第一個參數：請求授權代碼
    //     第二個參數：請求的授權名稱
    //     第三個參數：使用者選擇授權的結果
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) { //按 允許 鈕
                setMyLocation(); //  顯示定位圖層
            }else{  //按 拒絕 鈕
                Toast.makeText(getActivity(), "未取得授權！", Toast.LENGTH_SHORT).show();
                getActivity().finish();  // 結束應用程式
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //  顯示定位圖層
    private void setMyLocation() throws SecurityException {
        mMap.setMyLocationEnabled(true); // 顯示定位圖層
    }

    @Override
    public void onLocationChanged(Location location) {
        // 取得地圖座標值:緯度,經度
        String x="緯=" + Double.toString(location.getLatitude());
        String y="經=" + Double.toString(location.getLongitude());
        LatLng Point = new LatLng(location.getLatitude(), location.getLongitude());
        zoom=17; //設定放大倍率1(地球)-21(街景)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Point, zoom));
        Toast.makeText(getActivity(), x + "\n" + y, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        // 取得定位服務
        locMgr = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        // 取得最佳定位
        Criteria criteria = new Criteria();
        bestProv = locMgr.getBestProvider(criteria, true);

        // 如果GPS或網路定位開啟，更新位置
        if (locMgr.isProviderEnabled(LocationManager.GPS_PROVIDER) || locMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            //  確認 ACCESS_FINE_LOCATION 權限是否授權
            if (ActivityCompat.checkSelfPermission(getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locMgr.requestLocationUpdates(bestProv, 1000, 1, this);
            }
        } else {
            Toast.makeText(getActivity(), "請開啟定位服務", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //  確認 ACCESS_FINE_LOCATION 權限是否授權
        if (ActivityCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locMgr.removeUpdates(this);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Criteria criteria = new Criteria();
        bestProv = locMgr.getBestProvider(criteria, true);
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
}
