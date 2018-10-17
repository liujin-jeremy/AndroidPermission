package teck.threekilogram.permission;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * permission common function
 *
 * @author liujin
 */
class PermissionFunctions {

      /**
       * key for add permission
       */
      static final String KEY_PERMISSION     = "PERMISSION";
      /**
       * key for index of listener
       */
      static final String KEY_LISTENER_INDEX = "INDEX";

      /**
       * request permission code
       */
      static final int REQUEST_CODE = 0b11101101;

      /**
       * index of listener in Temp
       */
      static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(12);

      /**
       * save listener temporary
       */
      static final ArrayMap<Integer, OnRequestPermissionResultListener> TEMP_LISTENER = new ArrayMap<>();

      /**
       * check permission
       *
       * @param context
       * @param permissions
       *
       * @return true have permission
       */
      public static boolean checkPermission (Context context, String permissions) {

            return ContextCompat.checkSelfPermission(context, permissions)
                == PackageManager.PERMISSION_GRANTED;
      }
}
