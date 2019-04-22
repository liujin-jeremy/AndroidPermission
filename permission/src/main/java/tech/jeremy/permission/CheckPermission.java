package tech.jeremy.permission;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * @author Liujin 2018-10-24:23:30
 */
@SuppressWarnings("WeakerAccess")
public class CheckPermission {

      /**
       * check permission
       *
       * @return true have permission
       */
      @SuppressWarnings("BooleanMethodIsAlwaysInverted")
      public static boolean check ( Context context, String permissions ) {

            return ContextCompat.checkSelfPermission( context, permissions ) == PackageManager.PERMISSION_GRANTED;
      }
}
