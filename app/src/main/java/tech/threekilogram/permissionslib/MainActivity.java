package tech.threekilogram.permissionslib;

import android.Manifest.permission;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.util.Arrays;
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

      public void toActivity ( View view ) {

            PermissionActivity.start( this );
      }

      public void toTranslucentActivity ( View view ) {

            PermissionFragment.request(
                this,
                permission.WRITE_EXTERNAL_STORAGE,
                mPermissionResult
            );
      }

      public void toManager ( View view ) {

            PermissionFragment.request(
                this,
                permission.READ_CONTACTS,
                mPermissionResult
            );
      }

      public void toMsm ( View view ) {

            PermissionFragment.request(
                this,
                permission.SEND_SMS,
                mPermissionResult
            );
      }

      public void toCalender ( View view ) {

            PermissionActivity.requestPermission(
                this, permission.SEND_SMS,
                new OnRequestPermissionResultListener() {

                      @Override
                      public void onResult (
                          String permission, boolean success,
                          boolean isFinalResult ) {

                            Log.e(
                                TAG,
                                "onResult : " + permission
                                    + " " + success + " " + isFinalResult
                            );
                      }
                }
            );
      }

      public void toCameraRecode ( View view ) {

            ActivityCompat.requestPermissions(
                this, new String[]{ permission.CAMERA, permission.RECORD_AUDIO }, 1002 );
      }

      @Override
      public void onRequestPermissionsResult (
          int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults ) {

            super.onRequestPermissionsResult( requestCode, permissions, grantResults );

            Log.e(
                TAG, "onRequestPermissionsResult : " + requestCode + " " + Arrays
                    .toString( permissions ) + " " + Arrays.toString( grantResults ) );
      }

      private class PermissionResult implements OnRequestPermissionResultListener {

            @Override
            public void onResult ( String permission, boolean success, boolean isFinalResult ) {

                  if( success ) {
                        Log.e( TAG, "onResult: " + permission );
                        Toast.makeText( MainActivity.this, permission + "成功", Toast.LENGTH_SHORT )
                             .show();
                  } else {

                        if( isFinalResult ) {
                              Log.e( TAG, "onFinalDenied: " + permission );
                              Toast.makeText( MainActivity.this, permission + "最终结果失败",
                                              Toast.LENGTH_SHORT
                              ).show();
                        } else {
                              Log.e( TAG, "onFailed: " + permission );
                              Toast.makeText( MainActivity.this, permission + "失败了,解释一下",
                                              Toast.LENGTH_SHORT
                              ).show();
                        }
                  }
            }
      }
}
