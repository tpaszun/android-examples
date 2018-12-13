package com.example.user.examplesandroid;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{

	private ContextRegisteredBroadcastReceiver contextReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (!isSmsPermissionGranted()) {
			requestReadSmsPermission();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		this.contextReceiver = new ContextRegisteredBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
		this.registerReceiver(contextReceiver, filter);
	}

	@Override
	protected void onPause() {
		this.unregisterReceiver(this.contextReceiver);
		super.onPause();
	}

	public void onSend() {
		Intent intent = new Intent("MY_EXAMPLE_ACTION");
		intent.putExtra("IMPORTANT_VALUE", 42);
		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}

	private boolean isSmsPermissionGranted() {
		return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED;
	}

	private void requestReadSmsPermission() {
	    int requestCode = 0;
		//ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS);
		ActivityCompat.requestPermissions(this, new String[]{
				Manifest.permission.READ_SMS,
				Manifest.permission.RECEIVE_SMS
		}, requestCode);
	}
}
