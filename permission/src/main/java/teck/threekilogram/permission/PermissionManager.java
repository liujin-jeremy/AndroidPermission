package teck.threekilogram.permission;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
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
public class PermissionManager extends DialogFragment {

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

            PermissionManager fragment = new PermissionManager();
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

            if( checkPermission( getContext(), mPermission ) ) {

                  if( mOnRequestPermissionResult != null ) {

                        mOnRequestPermissionResult.onSuccess( mPermission );
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

                        boolean result = grantResult == PackageManager.PERMISSION_GRANTED;

                        if( result ) {

                              mOnRequestPermissionResult.onSuccess( permission );
                        } else {

                              if( shouldShowRequestPermissionRationale( permission ) ) {

                                    mOnRequestPermissionResult.onFailed( permission );
                              } else {

                                    mOnRequestPermissionResult.onFinalDenied( permission );
                              }
                        }
                  }

                  dismiss();
            }
      }

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
