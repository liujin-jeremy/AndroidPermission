package teck.threekilogram.permissionslib;

import android.Manifest.permission;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import teck.threekilogram.permission.AndroidPermission;
import teck.threekilogram.permission.OnRequestPermissionResult;
import teck.threekilogram.permission.PermissionManager;

/**
 * @author liujin
 */
public class MainActivity extends AppCompatActivity implements OnRequestPermissionResult {

      private static final String TAG = "MainActivity";

      @Override
      protected void onCreate (Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
      }

      public void toTranslucentActivity (View view) {

            AndroidPermission.request(this, permission.WRITE_EXTERNAL_STORAGE, this);
      }

      public void toManager (View view) {

            PermissionManager.request(
                this,
                permission.READ_CONTACTS,
                this
            );
      }

      @Override
      public void onSuccess (String permission) {

            Log.i(TAG, "onSuccess: " + permission);
      }

      @Override
      public void onExplain (String permission) {

            Log.i(TAG, "onExplain: " + permission);
      }

      @Override
      public void onFinalDenied (String permission) {

            Log.i(TAG, "onFinalDenied: " + permission);
      }
}
