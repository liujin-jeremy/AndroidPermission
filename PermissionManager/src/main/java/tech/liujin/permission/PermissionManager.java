package tech.liujin.permission;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * @author Liujin 2019/4/22:9:39:10
 */
public class PermissionManager {

      /**
       * 检查权限
       *
       * @param context context
       * @param permission 需要检查的权限
       *
       * @return true:拥有权限
       */
      public static boolean check ( Context context, String permission ) {

            return ContextCompat.checkSelfPermission( context, permission ) == PackageManager.PERMISSION_GRANTED;
      }

      /**
       * request a permission
       *
       * @param context context
       * @param permission permission to request
       * @param onRequestPermissionResult listener for result
       */
      public static void request (
          Context context,
          OnRequestPermissionResultListener onRequestPermissionResult,
          String permission ) {

            request( context, onRequestPermissionResult, new String[]{ permission } );
      }

      /**
       * request a permissions
       *
       * @param context support context and fragment manager
       * @param permissions permissions to request
       * @param onRequestPermissionResult listener for result
       */
      public static void request (
          Context context,
          OnRequestPermissionResultListener onRequestPermissionResult,
          String... permissions ) {

            if( context instanceof AppCompatActivity ) {
                  PermissionFragment.request( (AppCompatActivity) context, onRequestPermissionResult, permissions );
            } else {
                  PermissionActivity.request( context, onRequestPermissionResult, permissions );
            }
      }
}
