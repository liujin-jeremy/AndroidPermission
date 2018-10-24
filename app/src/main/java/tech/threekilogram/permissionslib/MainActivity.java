package tech.threekilogram.permissionslib;

import android.Manifest.permission;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import tech.threekilogram.permission.OnRequestPermissionResultListener;
import tech.threekilogram.permission.PermissionManager;
import tech.threekilogram.permission.TransparentActivity;

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

            TransparentActivity.start( this );
      }

      public void toTranslucentActivity ( View view ) {

            PermissionManager.request(
                this,
                permission.WRITE_EXTERNAL_STORAGE,
                mPermissionResult
            );
      }

      public void toManager ( View view ) {

            PermissionManager.request(
                this,
                permission.READ_CONTACTS,
                mPermissionResult
            );
      }

      public void toMsm ( View view ) {

            PermissionManager.request(
                this,
                permission.SEND_SMS,
                mPermissionResult
            );
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
                              )
                                   .show();
                        } else {
                              Log.e( TAG, "onFailed: " + permission );
                              Toast.makeText( MainActivity.this, permission + "失败了,解释一下",
                                              Toast.LENGTH_SHORT
                              )
                                   .show();
                        }
                  }
            }
      }
}
