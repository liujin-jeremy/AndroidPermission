package tech.threekilogram.permission;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;

/**
 * @author liujin
 */
public class PermissionActivity extends AppCompatActivity {

      private static final String TAG = PermissionActivity.class.getSimpleName();

      private static ArrayMap<String, OnRequestPermissionResultListener> sRequest = new ArrayMap<>();
      private static ArrayMap<String, OnRequestPermissionResultListener> sResult  = new ArrayMap<>();

      public static void start ( Context context ) {

            Intent starter = new Intent( context, PermissionActivity.class );
            context.startActivity( starter );
      }

      public static void requestPermission (
          Context context,
          String permission,
          OnRequestPermissionResultListener onRequestPermissionResult ) {

            sRequest.put( permission, onRequestPermissionResult );

            Intent starter = new Intent( context, PermissionActivity.class );
            starter.addFlags( Intent.FLAG_ACTIVITY_SINGLE_TOP );
            context.startActivity( starter );
      }

      @Override
      protected void onCreate ( Bundle savedInstanceState ) {

            super.onCreate( savedInstanceState );

            requestWindowFeature( Window.FEATURE_NO_TITLE );
            getWindow().setDimAmount( 0 );

            if( getSupportActionBar() != null ) {
                  getSupportActionBar().hide();
            }

            View view = new View( this );
            view.setBackgroundColor( Color.TRANSPARENT );
            view.setLayoutParams( new LayoutParams( 0, 0 ) );
            setContentView( view );

            handlePermissions();
      }

      @Override
      protected void onNewIntent ( Intent intent ) {

            super.onNewIntent( intent );

            handlePermissions();
      }

      private void handlePermissions ( ) {

            while( sRequest.size() > 0 ) {
                  String permission = sRequest.keyAt( 0 );
                  OnRequestPermissionResultListener listener = sRequest.remove( permission );

                  if( PermissionFun.checkPermission( this, permission ) ) {

                        listener.onResult( permission, true, false );
                  } else {
                        sResult.put( permission, listener );
                        ActivityCompat
                            .requestPermissions( this, new String[]{ permission }, 12 );
                  }
            }
      }

      @Override
      public void onRequestPermissionsResult (
          int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults ) {

            super.onRequestPermissionsResult( requestCode, permissions, grantResults );

            String permission = permissions[ 0 ];
            OnRequestPermissionResultListener listener = sResult.remove( permission );

            boolean success = grantResults[ 0 ] == PackageManager.PERMISSION_GRANTED;
            if( success ) {

                  listener.onResult( permission, success, false );
            } else {

                  if( ActivityCompat.shouldShowRequestPermissionRationale( this, permission ) ) {
                        listener.onResult( permission, false, false );
                  } else {
                        listener.onResult( permission, false, true );
                  }
            }

            if( sResult.size() == 0 ) {
                  finish();
                  overridePendingTransition( 0, 0 );
            }
      }

      public static class Holder {

            private String permission;

            private OnRequestPermissionResultListener onRequestPermissionResult;

            public Holder (
                String permission,
                OnRequestPermissionResultListener onRequestPermissionResult ) {

                  this.permission = permission;
                  this.onRequestPermissionResult = onRequestPermissionResult;
            }
      }
}
