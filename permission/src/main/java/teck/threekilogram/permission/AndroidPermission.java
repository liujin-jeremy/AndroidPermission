package teck.threekilogram.permission;

import static teck.threekilogram.permission.PermissionFunctions.ATOMIC_INTEGER;
import static teck.threekilogram.permission.PermissionFunctions.KEY_LISTENER_INDEX;
import static teck.threekilogram.permission.PermissionFunctions.KEY_PERMISSION;
import static teck.threekilogram.permission.PermissionFunctions.REQUEST_CODE;
import static teck.threekilogram.permission.PermissionFunctions.TEMP_LISTENER;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * 一个透明的Activity 用于申请权限
 *
 * @author liujin
 */
public class AndroidPermission extends AppCompatActivity {

      /**
       * listener for request permission
       */
      private OnRequestPermissionResult mOnRequestPermissionResult;

      /**
       * request permission
       *
       * @param context context
       * @param permission request permissions
       * @param permissionResult listener
       */
      public static void request (
          Context context, String permission, OnRequestPermissionResult permissionResult) {

            int index = ATOMIC_INTEGER.getAndAdd(1);
            TEMP_LISTENER.put(index, permissionResult);

            Intent starter = new Intent(context, AndroidPermission.class);
            starter.putExtra(KEY_PERMISSION, permission);
            starter.putExtra(KEY_LISTENER_INDEX, index);
            context.startActivity(starter);
      }

      @Override

      protected void onCreate (Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);

            Intent intent = getIntent();

            int index = intent.getIntExtra(KEY_LISTENER_INDEX, 0);
            if(index > 0) {

                  mOnRequestPermissionResult = TEMP_LISTENER.remove(index);
            }
            String permissions = intent.getStringExtra(KEY_PERMISSION);

            if(PermissionFunctions.checkPermission(this, permissions)) {

                  if(mOnRequestPermissionResult != null) {
                        mOnRequestPermissionResult.onSuccess(permissions);
                  }
                  finish();
            } else {

                  requestPermission(this, permissions, REQUEST_CODE);
            }
      }

      @Override
      public void finish () {

            super.finish();
            overridePendingTransition(0, 0);
      }

      /**
       * request permissions
       *
       * @param activity this activity will receive result
       * @param permission permission
       * @param requestCode code
       */
      public static void requestPermission (Activity activity, String permission, int requestCode) {

            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
      }

      @Override
      public void onRequestPermissionsResult (
          int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if(requestCode == REQUEST_CODE) {

                  String permission = permissions[0];
                  int result = grantResults[0];

                  if(mOnRequestPermissionResult != null) {

                        boolean resultBoolean = result == PackageManager.PERMISSION_GRANTED;

                        if(resultBoolean) {

                              /* success */

                              mOnRequestPermissionResult.onSuccess(permission);
                        } else {

                              /* failed but have a chance to explain */

                              if(ActivityCompat
                                  .shouldShowRequestPermissionRationale(this, permission)) {

                                    mOnRequestPermissionResult
                                        .onExplain(permission);
                              } else {

                                    /* unfortunately could not have this permission */

                                    mOnRequestPermissionResult.onFinalDenied(permission);
                              }
                        }
                  }

                  finish();
            }
      }
}
