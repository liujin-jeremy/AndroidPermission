package tech.threekilogram.permission;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author liujin
 */
public class PermissionActivity extends AppCompatActivity {

      private static final String TAG = PermissionActivity.class.getSimpleName();

      private static final ArrayList<Holder> HOLDERS = new ArrayList<>();

      public static void start ( Context context ) {

            Intent starter = new Intent( context, PermissionActivity.class );
            context.startActivity( starter );
      }

      public static void requestPermission (
          Context context,
          String permission,
          OnRequestPermissionResultListener onRequestPermissionResult ) {

            HOLDERS.add( new Holder( permission, onRequestPermissionResult ) );

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

            while( HOLDERS.size() > 0 ) {
                  Holder holder = HOLDERS.remove( 0 );
                  if( PermissionFun.checkPermission( this, holder.permission ) ) {

                  } else {
                        ActivityCompat
                            .requestPermissions( this, new String[]{ holder.permission }, 12 );
                  }
            }
      }

      @Override
      public void onRequestPermissionsResult (
          int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults ) {

            super.onRequestPermissionsResult( requestCode, permissions, grantResults );
            Log.e( TAG, "onRequestPermissionsResult : " + Arrays.toString( permissions ) );

            finish();
            overridePendingTransition( 0, 0 );
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
