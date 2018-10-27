package com.a1anwang.mybledemo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.NumberKeyListener;
import android.view.View;
import android.widget.EditText;

import com.a1anwang.utils.Conversion;
import com.a1anwang.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.edit_data)
    EditText editData;
    private String TAG = "MainActivity";
    BluetoothAdapter bluetoothAdapter;
    Handler handler = new Handler();
    BluetoothLeAdvertiser mBluetoothLeAdvertiser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);

        bluetoothAdapter = bluetoothManager.getAdapter();

        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
        }


        setUpViews();
    }

    private void setUpViews() {
        editData.setKeyListener(new NumberKeyListener() {
            @Override
            protected char[] getAcceptedChars() {
                char[] numberChars = {'A', 'B', 'C', 'D', 'E', 'F', 'a', 'b', 'c', 'd', 'e', 'f', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

                return numberChars;
            }

            @Override
            public int getInputType() {
                return InputType.TYPE_CLASS_TEXT;
            }
        });
    }

    public void startAction(View v) {
        if (!bluetoothAdapter.isEnabled()) {
            ToastUtils.showToast(this, "请打开蓝牙开关", 2000);
            return;
        }
        if (!bluetoothAdapter.isMultipleAdvertisementSupported()) {
            ToastUtils.showToast(this, "当前手机不支持BLE Advertise", 2000);
            return;
        }
        mBluetoothLeAdvertiser = bluetoothAdapter.getBluetoothLeAdvertiser();
        if (mBluetoothLeAdvertiser == null) {
            ToastUtils.showToast(this, "当前手机不支持BLE Advertise", 2000);
            return;
        }

        String str = editData.getText().toString();

        if (str == null || str.length() < 1) {
            ToastUtils.showToast(this, "请输入要广播的数据", 2000);
            return;
        }
        AdvertiseSettings advertiseSettings = createAdvSettings(false, 0);

        if (advertiseSettings == null) {
            ToastUtils.showToast(this, "当前手机不支持BLE Advertise", 2000);
            return;
        }
        final byte[] broadcastData = Conversion.hexStringToBytes(str);
        mBluetoothLeAdvertiser.stopAdvertising(mAdvertiseCallback);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                String str = Conversion.Bytes2HexString(broadcastData);
                LogUtils.e(TAG, "发送的广播数据:" + str);
                mBluetoothLeAdvertiser.startAdvertising(createAdvSettings(true, 0), createAdvertiseData(broadcastData), mAdvertiseCallback);

            }
        }, 1000);

    }

    public void stopAction(View v) {
        mBluetoothLeAdvertiser.stopAdvertising(mAdvertiseCallback);
    }

    public AdvertiseSettings createAdvSettings(boolean connectable, int timeoutMillis) {
        AdvertiseSettings.Builder mSettingsbuilder = new AdvertiseSettings.Builder();
        mSettingsbuilder.setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY);
        mSettingsbuilder.setConnectable(connectable);
        mSettingsbuilder.setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH);
        mSettingsbuilder.setTimeout(0);
        AdvertiseSettings mAdvertiseSettings = mSettingsbuilder.build();
        return mAdvertiseSettings;
    }

    public AdvertiseData createAdvertiseData(byte[] data) {
        AdvertiseData.Builder mDataBuilder = new AdvertiseData.Builder();

        mDataBuilder.addServiceUuid(ParcelUuid.fromString("0000ffe1-0000-1000-8000-00805f9b34fb"));
        mDataBuilder.addServiceData(ParcelUuid.fromString("0000ae8f-0000-1000-8000-00805f9b34fb"), new byte[]{0x64, 0x12});
        mDataBuilder.addManufacturerData(0x01AC, data);
        AdvertiseData mAdvertiseData = mDataBuilder.build();
        return mAdvertiseData;
    }

    private AdvertiseCallback mAdvertiseCallback = new AdvertiseCallback() {
        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            super.onStartSuccess(settingsInEffect);
            ToastUtils.showToast(MainActivity.this, "开启广播成功", 2000);
        }

        @Override
        public void onStartFailure(int errorCode) {
            super.onStartFailure(errorCode);
            ToastUtils.showToast(MainActivity.this, "开启广播失败 errorCode：" + errorCode, 2000);
        }
    };

    @OnClick(R.id.edit_data)
    public void onViewClicked() {
    }


    static class ViewHolder {
        @BindView(R.id.edit_data)
        EditText editData;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
