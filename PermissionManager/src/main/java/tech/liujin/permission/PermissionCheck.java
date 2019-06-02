package tech.liujin.permission;

import android.content.Context;
import android.location.LocationManager;

/**
 * @author Liujin 2018-10-24:23:30
 */
@SuppressWarnings("WeakerAccess")
public class PermissionCheck {



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
