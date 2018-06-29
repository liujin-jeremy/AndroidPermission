package teck.threekilogram.permission;

import static teck.threekilogram.permission.PermissionFunctions.ATOMIC_INTEGER;
import static teck.threekilogram.permission.PermissionFunctions.KEY_LISTENER_INDEX;
import static teck.threekilogram.permission.PermissionFunctions.KEY_PERMISSION;
import static teck.threekilogram.permission.PermissionFunctions.REQUEST_CODE;
import static teck.threekilogram.permission.PermissionFunctions.TEMP_LISTENER;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

/**
 * for request permission by fragment
 *
 * @author liujin
 */
public class PermissionManager extends DialogFragment {

      /**
       * listener for result
       */
      private OnRequestPermissionResult mOnRequestPermissionResult;

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
          OnRequestPermissionResult onRequestPermissionResult) {

            Bundle args = new Bundle();
            args.putString(KEY_PERMISSION, permission);

            int index = ATOMIC_INTEGER.getAndAdd(1);
            TEMP_LISTENER.put(index, onRequestPermissionResult);
            args.putInt(KEY_LISTENER_INDEX, index);

            PermissionManager fragment = new PermissionManager();
            fragment.setArguments(args);

            fragment.show(activity.getSupportFragmentManager(), fragment.toString());
      }

      @Override
      public void onRequestPermissionsResult (
          int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if(requestCode == REQUEST_CODE) {

                  String permission = permissions[0];
                  int grantResult = grantResults[0];

                  if(mOnRequestPermissionResult != null) {

                        boolean result = grantResult == PackageManager.PERMISSION_GRANTED;

                        if(result) {

                              mOnRequestPermissionResult.onSuccess(permission);
                        } else {

                              if(shouldShowRequestPermissionRationale(permission)) {

                                    mOnRequestPermissionResult.onExplain(permission);
                              } else {

                                    mOnRequestPermissionResult.onFinalDenied(permission);
                              }
                        }
                  }

                  dismiss();
            }
      }

      @Nullable
      @Override
      public View onCreateView (
          @NonNull LayoutInflater inflater, @Nullable ViewGroup container,
          @Nullable Bundle savedInstanceState) {

            return inflater.inflate(R.layout.empty, container, false);
      }

      @Override
      public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {

            super.onViewCreated(view, savedInstanceState);

            /* make window not dark */

            Window window = getDialog().getWindow();
            if(window != null) {

                  LayoutParams params = window.getAttributes();
                  params.dimAmount = 0f;
                  window.setAttributes(params);
            }

            /* get arguments */

            String permission =
                getArguments() != null ? getArguments().getString(KEY_PERMISSION) : null;

            int index =
                getArguments() != null ? getArguments().getInt(KEY_LISTENER_INDEX) : 0;

            if(index > 0) {

                  mOnRequestPermissionResult = TEMP_LISTENER.get(index);
            }

            /* check permission */

            if(PermissionFunctions.checkPermission(getContext(), permission)) {

                  if(mOnRequestPermissionResult != null) {

                        mOnRequestPermissionResult.onSuccess(permission);
                  }

                  dismiss();
            } else {

                  requestPermissions(new String[]{permission}, PermissionFunctions.REQUEST_CODE);
            }
      }
}
