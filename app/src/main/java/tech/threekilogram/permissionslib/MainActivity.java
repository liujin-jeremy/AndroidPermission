package tech.threekilogram.permissionslib;

import android.Manifest.permission;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import tech.threekilogram.permission.OnRequestPermissionResultListener;
import tech.threekilogram.permission.PermissionActivity;
import tech.threekilogram.permission.PermissionFragment;

/**
 * @author liujin
 */
public class MainActivity extends AppCompatActivity {

      private static final String TAG = "MainActivity";
      private              PermissionResult mPermissionResult;

      @Override
      protected void onCreate ( Bundle savedInstanceState ) {

            super.onCreate( savedInstanceState );
            setContentView( R.layout.activity_main );

            mPermissionResult = new PermissionResult();
      }

      public void toTranslucentActivity ( View view ) {

            PermissionFragment.request(
                this,
                mPermissionResult,
                permission.READ_EXTERNAL_STORAGE
            );
      }

      public void toManager ( View view ) {

            PermissionFragment.request(
                this,
                mPermissionResult,
                permission.READ_CONTACTS
            );
      }

      public void toMsm ( View view ) {

            PermissionFragment.request(
                this,
                mPermissionResult,
                permission.SEND_SMS
            );
      }

      public void toCalender ( View view ) {

            PermissionActivity.request(
                this,
                mPermissionResult,
                permission.READ_CALENDAR
            );
      }

      public void toCameraRecode ( View view ) {

            PermissionActivity.request(
                this,
                mPermissionResult,
                permission.CAMERA, permission.RECORD_AUDIO
            );
      }

      public void toLocation ( View view ) {

            PermissionActivity
                .request( this, mPermissionResult, permission.ACCESS_FINE_LOCATION );
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
