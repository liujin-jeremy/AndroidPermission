package tech.threekilogram.permissionslib;

import android.Manifest.permission;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import tech.jeremy.permission.IntentBuilder;
import tech.jeremy.permission.OnRequestPermissionResultListener;
import tech.jeremy.permission.PermissionManager;

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
