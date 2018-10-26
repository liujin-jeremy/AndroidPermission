package tech.threekilogram.permission;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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

      /**
       * request permission code
       */
      private static final int REQUEST_CODE = 0b11101101;

      /**
       * listener for result
       */
      private OnRequestPermissionResultListener mOnRequestPermissionResult;
      private String                            mPermission;

      /**
       * request a permission
       *
       * @param activity support context and fragment manager
       * @param permission permission to request
       * @param onRequestPermissionResult listener for result
       */
      public static void request (
          AppCompatActivity activity,
          String permission,
          OnRequestPermissionResultListener onRequestPermissionResult ) {

            PermissionFragment fragment = new PermissionFragment();
            fragment.mPermission = permission;
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
      public void onViewCreated ( @NonNull View view, @Nullable Bundle savedInstanceState ) {

            super.onViewCreated( view, savedInstanceState );

            /* check permission */

            if( PermissionFun.checkPermission( getContext(), mPermission ) ) {

                  if( mOnRequestPermissionResult != null ) {

                        mOnRequestPermissionResult.onResult( mPermission, true, false );
                  }

                  dismiss();
            } else {

                  requestPermissions(
                      new String[]{ mPermission }, REQUEST_CODE );
            }
      }

      @Override
      public void onRequestPermissionsResult (
          int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults ) {

            super.onRequestPermissionsResult( requestCode, permissions, grantResults );

            if( requestCode == REQUEST_CODE ) {

                  String permission = permissions[ 0 ];
                  int grantResult = grantResults[ 0 ];

                  if( mOnRequestPermissionResult != null ) {

                        if( grantResult == PackageManager.PERMISSION_GRANTED ) {

                              mOnRequestPermissionResult.onResult( permission, true, false );
                        } else {

                              if( shouldShowRequestPermissionRationale( permission ) ) {

                                    mOnRequestPermissionResult.onResult( permission, false, false );
                              } else {

                                    mOnRequestPermissionResult.onResult( permission, false, true );
                              }
                        }
                  }

                  dismiss();
            }
      }
}
