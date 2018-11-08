package tech.threekilogram.permission;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liujin
 */
public class PermissionActivity extends AppCompatActivity {

      private static final SparseArray<OnRequestPermissionResultListener> sRequest = new SparseArray<>();

      private static AtomicInteger sIndex = new AtomicInteger( 24 );

      private static final String KEY_PERMISSION_GROUP = "KEY_PERMISSION_GROUP";
      private static final String KEY_LISTENER_INDEX   = "KEY_LISTENER_INDEX";

      public static void start ( Context context ) {

            Intent starter = new Intent( context, PermissionActivity.class );
            context.startActivity( starter );
      }

      public static void request (
          Context context,
          OnRequestPermissionResultListener onRequestPermissionResult,
          String permission ) {

            String[] group = { permission };
            int index = sIndex.getAndAdd( 1 );
            sRequest.put( index, onRequestPermissionResult );

            Intent starter = new Intent( context, PermissionActivity.class );
            starter.putExtra( KEY_PERMISSION_GROUP, group );
            starter.putExtra( KEY_LISTENER_INDEX, index );
            context.startActivity( starter );
      }

      public static void request (
          Context context,
          OnRequestPermissionResultListener onRequestPermissionResult,
          String... permissions ) {

            int index = sIndex.getAndAdd( 1 );
            sRequest.put( index, onRequestPermissionResult );

            Intent starter = new Intent( context, PermissionActivity.class );
            starter.putExtra( KEY_PERMISSION_GROUP, permissions );
            starter.putExtra( KEY_LISTENER_INDEX, index );
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

            String[] stringArrayExtra = getIntent().getStringArrayExtra( KEY_PERMISSION_GROUP );
            if( stringArrayExtra != null ) {
                  handlePermissions( stringArrayExtra );
            }
      }

      private void handlePermissions ( String[] permissions ) {

            boolean toRequest = false;
            for( int i = 0; i < permissions.length; i++ ) {
                  if( !CheckPermission.check( this, permissions[ i ] ) ) {
                        toRequest = true;
                        break;
                  }
            }

            int index = getIntent().getIntExtra( KEY_LISTENER_INDEX, -1 );

            if( toRequest ) {
                  ActivityCompat.requestPermissions( this, permissions, index );
            } else {

                  OnRequestPermissionResultListener listener = sRequest.get( index );
                  sRequest.delete( index );
                  for( String permission : permissions ) {
                        listener.onResult( permission, true, false );
                  }
                  finish();
                  overridePendingTransition( 0, 0 );
            }
      }

      @Override
      public void onRequestPermissionsResult (
          int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults ) {

            super.onRequestPermissionsResult( requestCode, permissions, grantResults );

            OnRequestPermissionResultListener listener = sRequest.get( requestCode );
            sRequest.delete( requestCode );

            for( int i = 0; i < permissions.length; i++ ) {
                  String permission = permissions[ i ];

                  boolean success = grantResults[ i ] == PackageManager.PERMISSION_GRANTED;
                  if( success ) {

                        listener.onResult( permission, true, true );
                  } else {

                        if( ActivityCompat
                            .shouldShowRequestPermissionRationale( this, permission ) ) {
                              listener.onResult( permission, false, true );
                        } else {
                              listener.onResult( permission, false, false );
                        }
                  }
            }
            finish();
            overridePendingTransition( 0, 0 );
      }
}
