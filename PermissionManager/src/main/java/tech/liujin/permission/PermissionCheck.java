package tech.liujin.permission;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;

/**
 * @author Liujin 2018-10-24:23:30
 */
@SuppressWarnings("WeakerAccess")
public class PermissionCheck {

      /**
       * checkPermission permission
       *
       * @return true have permission
       */
      @SuppressWarnings("BooleanMethodIsAlwaysInverted")
      public static boolean checkPermission ( Context context, String permissions ) {

            return ContextCompat.checkSelfPermission( context, permissions ) == PackageManager.PERMISSION_GRANTED;
      }

      /**
       * 检测GPS是否打开
       *
       * @param context context
       *
       * @return true:打开
       */
      private boolean checkGPSIsOpen ( Context context ) {

            LocationManager locationManager = (LocationManager) context.getApplicationContext()
                                                                       .getSystemService( Context.LOCATION_SERVICE );
            return locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER );
      }
}
