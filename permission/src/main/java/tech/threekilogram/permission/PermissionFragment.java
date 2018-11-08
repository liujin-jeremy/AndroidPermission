package tech.threekilogram.permission;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * for request permission by fragment
 *
 * @author liujin
 */
public class PermissionFragment extends DialogFragment {

      private static final String TAG = PermissionFragment.class.getSimpleName();

      /**
       * request permission code
       */
      private static final int REQUEST_CODE = 0b11101101;

      /**
       * listener for result
       */
      private OnRequestPermissionResultListener mOnRequestPermissionResult;
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

            PermissionFragment fragment = new PermissionFragment();
            fragment.mPermissions = new String[]{ permission };
            fragment.mOnRequestPermissionResult = onRequestPermissionResult;

            fragment.show( activity.getSupportFragmentManager(), fragment.toString() );
      }

      @Nullable
      @Override
      public View onCreateView (
          @NonNull LayoutInflater inflater, @Nullable ViewGroup container,
          @Nullable Bundle savedInstanceState ) {

            FrameLayout frameLayout = new FrameLayout( getContext() );
            frameLayout.setLayoutParams(
                new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                ) );

            return frameLayout;
      }

      @Override
      public void onActivityCreated ( Bundle savedInstanceState ) {

            super.onActivityCreated( savedInstanceState );

            FragmentActivity activity = getActivity();
            for( String permission : mPermissions ) {
                  if( !CheckPermission.check( activity, permission ) ) {
                        return;
                  }
            }
            for( String permission : mPermissions ) {
                  mOnRequestPermissionResult.onResult( permission, true, false );
            }
            dismiss();
      }

      @Override
      public void onViewCreated ( @NonNull View view, @Nullable Bundle savedInstanceState ) {

            super.onViewCreated( view, savedInstanceState );
            /* check permission */
            handlePermissions();
      }

      private void handlePermissions ( ) {

            requestPermissions( mPermissions, REQUEST_CODE );
      }

      @Override
      public void onRequestPermissionsResult (
          int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults ) {

            super.onRequestPermissionsResult( requestCode, permissions, grantResults );

            OnRequestPermissionResultListener onRequestPermissionResult = mOnRequestPermissionResult;

            for( int i = 0; i < permissions.length; i++ ) {
                  String permission = permissions[ i ];

                  boolean success = grantResults[ i ] == PackageManager.PERMISSION_GRANTED;
                  if( success ) {

                        onRequestPermissionResult.onResult( permission, true, true );
                  } else {

                        FragmentActivity activity = getActivity();
                        if( activity != null ) {
                              boolean showed = ActivityCompat.shouldShowRequestPermissionRationale(
                                  activity,
                                  permission
                              );
                              if( showed ) {
                                    onRequestPermissionResult.onResult( permission, false, true );
                              } else {
                                    onRequestPermissionResult.onResult( permission, false, false );
                              }
                        }
                  }
            }
            dismiss();
      }
}
