package teck.threekilogram.permission;

/**
 * listener for request permission
 *
 * @author liujin
 */
public interface OnRequestPermissionResult {

      /**
       * success call this
       *
       * @param permission request permission
       */
      void onSuccess (String permission);

      /**
       * explain why need this permission
       *
       * @param permission request permission
       */
      void onExplain (String permission);

      /**
       * denied finally you should find another way
       *
       * @param permission request permission
       */
      void onFinalDenied (String permission);
}
