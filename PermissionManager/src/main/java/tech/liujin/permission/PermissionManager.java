package tech.liujin.permission;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

/**
 * @author Liujin 2019/4/22:9:39:10
 */
public class PermissionManager {

      public static boolean check ( Context context, String permission ) {

            return PermissionCheck.checkPermission( context, permission );
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
