package tech.threekilogram.permission;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import java.util.ArrayList;

/**
 * @author liujin
 */
public class TransparentActivity extends AppCompatActivity {

      private static final ArrayList<Holder> HOLDERS = new ArrayList<>();

      public static void start ( Context context ) {

            Intent starter = new Intent( context, TransparentActivity.class );
            context.startActivity( starter );
      }

      public static void requestPermission (
          Context context,
          String permission,
          OnRequestPermissionResultListener onRequestPermissionResult ) {

            HOLDERS.add( new Holder( permission, onRequestPermissionResult ) );

            Intent starter = new Intent( context, TransparentActivity.class );
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
      }

      @Override
      protected void onNewIntent ( Intent intent ) {

            super.onNewIntent( intent );

            while( HOLDERS.size() > 0 ) {
                  Holder holder = HOLDERS.remove( 0 );
                  PermissionManager.request(
                      this,
                      holder.permission,
                      holder.onRequestPermissionResult
                  );
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
