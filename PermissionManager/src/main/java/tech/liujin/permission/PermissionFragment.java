package tech.liujin.permission;

import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

/**
 * for request permission by fragment
 *
 * @author liujin
 */
public class PermissionFragment extends DialogFragment {

      /**
       * request permission code
       */
      private static final int REQUEST_CODE = 0b11101101;

      /**
       * listener for result
       */
      private OnRequestPermissionResultListener mListener;
      private String[]                          mPermissions;

      /**
       * request a permission
       *
       * @param activity support context and fragment manager
       * @param permission permission to request
       * @param onRequestPermissionResult listener for result
       */
      public static void request (
          AppCompatActivity activity,
          OnRequestPermissionResultListener onRequestPermissionResult,
          String permission ) {

            request( activity, onRequestPermissionResult, new String[]{ permission } );
      }

      /**
       * request a permissions
       *
       * @param activity support context and fragment manager
       * @param permissions permissions to request
       * @param onRequestPermissionResult listener for result
       */
      public static void request (
          AppCompatActivity activity,
          OnRequestPermissionResultListener onRequestPermissionResult,
          String... permissions ) {

            PermissionFragment fragment = new PermissionFragment();
            fragment.mPermissions = permissions;
            fragment.mListener = onRequestPermissionResult;

            fragment.show( activity.getSupportFragmentManager(), fragment.toString() );
      }

      @NonNull
      private FrameLayout getFrameLayout ( ) {

            FrameLayout frameLayout = new FrameLayout( getContext() );
            frameLayout.setLayoutParams(
                new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT
                )
            );
            frameLayout.setBackgroundColor( Color.TRANSPARENT );
            return frameLayout;
      }

      @NonNull
      @Override
      public Dialog onCreateDialog ( @Nullable Bundle savedInstanceState ) {

            Builder builder = new Builder( getContext(), R.style.TransparentDialog );
            builder.setView( getFrameLayout() );
            handlePermissions();
            return builder.create();
      }

      @Override
      public void onActivityCreated ( Bundle savedInstanceState ) {

            super.onActivityCreated( savedInstanceState );

            FragmentActivity activity = getActivity();
            for( String permission : mPermissions ) {
                  if( !PermissionCheck.checkPermission( activity, permission ) ) {
                        return;
                  }
            }
            if( mListener != null ) {
                  for( String permission : mPermissions ) {
                        mListener.onResult( permission, true, false );
                  }
            }
            dismiss();
      }

      private void handlePermissions ( ) {

            requestPermissions( mPermissions, REQUEST_CODE );
      }

      @Override
      public void onRequestPermissionsResult (
          int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults ) {

            super.onRequestPermissionsResult( requestCode, permissions, grantResults );

            for( int i = 0; i < permissions.length; i++ ) {
                  String permission = permissions[ i ];

                  boolean success = grantResults[ i ] == PackageManager.PERMISSION_GRANTED;
                  if( success ) {
                        if( mListener != null ) {
                              mListener.onResult( permission, true, true );
                        }
                  } else {

                        FragmentActivity activity = getActivity();
                        if( activity != null ) {
                              boolean showed = ActivityCompat.shouldShowRequestPermissionRationale(
                                  activity,
                                  permission
                              );
                              if( showed ) {
                                    if( mListener != null ) {
                                          mListener.onResult( permission, false, true );
                                    }
                              } else {
                                    if( mListener != null ) {
                                          mListener.onResult( permission, false, false );
                                    }
                              }
                        }
                  }
            }
            dismiss();
      }
}
