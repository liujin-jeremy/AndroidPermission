package tech.liujin.permissionslib;

import android.Manifest.permission;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import tech.liujin.permission.IntentBuilder;
import tech.liujin.permission.OnRequestPermissionResultListener;
import tech.liujin.permission.PermissionManager;

/**
 * @author liujin
 */
public class MainActivity extends AppCompatActivity implements OnClickListener {

      private static final String TAG = "MainActivity";

      private PermissionResult mPermissionResult;
      private Button           mSd;
      private Button           mContact;
      private Button           mSms;
      private Button           mCameraRecord;
      private ConstraintLayout mRoot;
      private Button           mAppSetting;
      private Button           mSetting;
      private Button           mDetailSetting;
      private Button           mGPSSetting;
      private Button           mWireLessSetting;
      private Button           mWifiSetting;
      private Button           mData;
      private Button           mBlueTooth;

      @Override
      protected void onCreate ( Bundle savedInstanceState ) {

            super.onCreate( savedInstanceState );
            setContentView( R.layout.activity_main );
            initView();

            mPermissionResult = new PermissionResult();
      }

      private void initView ( ) {

            mSd = (Button) findViewById( R.id.sd );
            mSd.setOnClickListener( this );
            mContact = (Button) findViewById( R.id.contact );
            mContact.setOnClickListener( this );
            mSms = (Button) findViewById( R.id.sms );
            mSms.setOnClickListener( this );
            mCameraRecord = (Button) findViewById( R.id.cameraRecord );
            mCameraRecord.setOnClickListener( this );
            mRoot = (ConstraintLayout) findViewById( R.id.root );
            mAppSetting = (Button) findViewById( R.id.appSetting );
            mAppSetting.setOnClickListener( this );
            mSetting = (Button) findViewById( R.id.setting );
            mSetting.setOnClickListener( this );
            mDetailSetting = (Button) findViewById( R.id.detailSetting );
            mDetailSetting.setOnClickListener( this );
            mGPSSetting = (Button) findViewById( R.id.GPSSetting );
            mGPSSetting.setOnClickListener( this );
            mWireLessSetting = (Button) findViewById( R.id.wireLessSetting );
            mWireLessSetting.setOnClickListener( this );
            mWifiSetting = (Button) findViewById( R.id.wifiSetting );
            mWifiSetting.setOnClickListener( this );
            mData = (Button) findViewById( R.id.data );
            mData.setOnClickListener( this );
      }

      @Override
      public void onClick ( View v ) {

            switch( v.getId() ) {
                  case R.id.sd:
                        PermissionManager.request(
                            this,
                            mPermissionResult,
                            permission.READ_EXTERNAL_STORAGE
                        );
                        break;
                  case R.id.contact:
                        PermissionManager.request(
                            this,
                            mPermissionResult,
                            permission.READ_CONTACTS
                        );
                        break;
                  case R.id.sms:
                        PermissionManager.request(
                            this,
                            mPermissionResult,
                            permission.SEND_SMS
                        );
                        break;
                  case R.id.cameraRecord:
                        PermissionManager.request(
                            this,
                            mPermissionResult,
                            permission.CAMERA, permission.RECORD_AUDIO
                        );
                        break;
                  case R.id.appSetting:
                        startActivity( IntentBuilder.appSettingsIntent() );
                        break;
                  case R.id.setting:
                        startActivity( IntentBuilder.settingIntent() );
                        break;
                  case R.id.detailSetting:
                        startActivity( IntentBuilder.appDetailSetting( this ) );
                        break;
                  case R.id.GPSSetting:
                        startActivity( IntentBuilder.GPSSetting() );
                        break;
                  case R.id.wireLessSetting:
                        startActivity( IntentBuilder.wirelessSetting() );
                        break;
                  case R.id.wifiSetting:
                        startActivity( IntentBuilder.wifiSetting() );
                        break;
                  case R.id.data:
                        startActivity( IntentBuilder.dataRoaming() );
                        break;
                  default:
                        break;
            }
      }

      private class PermissionResult implements OnRequestPermissionResultListener {

            @Override
            public void onResult ( String permission, boolean success, boolean isShowDialog ) {

                  if( success ) {
                        Log.e( TAG, "onSuccess: " + permission );
                        Toast.makeText( MainActivity.this, permission + "成功", Toast.LENGTH_SHORT )
                             .show();
                  } else {

                        if( isShowDialog ) {
                              Log.e( TAG, "onFailed: " + permission );
                              Toast.makeText( MainActivity.this, permission + "显示对话框",
                                              Toast.LENGTH_SHORT
                              ).show();
                        } else {
                              Log.e( TAG, "onFailed: " + permission );
                              Toast.makeText( MainActivity.this, permission + "失败了,并且没有显示对话框",
                                              Toast.LENGTH_SHORT
                              ).show();
                        }
                  }
            }
      }
}
