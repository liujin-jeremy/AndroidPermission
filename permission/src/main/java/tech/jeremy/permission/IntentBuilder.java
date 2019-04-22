package tech.jeremy.permission;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

/**
 * @author Liujin 2018-11-08:14:36
 */
public class IntentBuilder {

      private IntentBuilder ( ) { }

      public static Intent settingIntent ( ) {

            return new Intent( Settings.ACTION_SETTINGS );
      }

      public static Intent appSettingsIntent ( ) {

            return new Intent( Settings.ACTION_APPLICATION_SETTINGS );
      }

      public static Intent appSettingIntent ( Context context ) {

            Intent intent = new Intent( Settings.ACTION_APPLICATION_DETAILS_SETTINGS );
            intent.setData( Uri.fromParts( "package", context.getPackageName(), null ) );
            return intent;
      }
}
