package tech.liujin.permission;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

/**
 * @author Liujin 2018-11-08:14:36
 */
public class IntentBuilder {

      private IntentBuilder ( ) { }

      /**
       * @return 设置界面
       */
      public static Intent settingIntent ( ) {

            return new Intent( Settings.ACTION_SETTINGS );
      }

      /**
       * @return 所有应用设置界面
       */
      public static Intent appSettingsIntent ( ) {

            return new Intent( Settings.ACTION_APPLICATION_SETTINGS );
      }

      /**
       * @param context context
       *
       * @return 应用设置界面
       */
      public static Intent appSettingIntent ( Context context ) {

            Intent intent = new Intent( Settings.ACTION_APPLICATION_DETAILS_SETTINGS );
            intent.setData( Uri.fromParts( "package", context.getPackageName(), null ) );
            return intent;
      }

      /**
       * @param context context
       *
       * @return 应用详细设置
       */
      public static Intent appDetailSetting ( Context context ) {

            Uri packageURI = Uri.fromParts( "package", context.getPackageName(), null );
            return new Intent( Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI );
      }

      /**
       * @return gps设置界面
       */
      public static Intent GPSSetting ( ) {
            //跳转GPS设置界面
            return new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS );
      }

      /**
       * @return 网络设置
       */
      public static Intent wirelessSetting ( ) {

            return new Intent( Settings.ACTION_WIRELESS_SETTINGS );
      }

      /**
       * @return 无线网络设置
       */
      public static Intent wifiSetting ( ) {

            return new Intent( Settings.ACTION_WIFI_SETTINGS );
      }

      /**
       * @return 数据漫游
       */
      public static Intent dataRoaming ( ) {

            return new Intent( android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS );
      }

      /**
       * @return 飞行模式
       */
      public static Intent airPlaneModeSetting ( ) {

            return new Intent( Settings.ACTION_AIRPLANE_MODE_SETTINGS );
      }

      /**
       * @return 蓝牙设置界面
       */
      public static Intent blueToothSetting ( ) {

            return new Intent( Settings.ACTION_BLUETOOTH_SETTINGS );
      }
}
