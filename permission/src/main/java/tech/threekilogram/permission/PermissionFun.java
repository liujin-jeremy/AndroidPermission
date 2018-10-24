package tech.threekilogram.permission;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * @author Liujin 2018-10-24:23:30
 */
public class PermissionFun {

      /**
       * check permission
       *
       * @return true have permission
       */
      public static boolean checkPermission ( Context context, String permissions ) {

            return ContextCompat.checkSelfPermission( context, permissions )
                == PackageManager.PERMISSION_GRANTED;
      }
}
