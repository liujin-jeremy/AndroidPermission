package teck.threekilogram.permission;

/**
 * listener for request permission
 *
 * @author liujin
 */
public interface OnRequestPermissionResultListener {

      /**
       * 申请成功
       *
       * @param permission request permission
       */
      void onSuccess ( String permission );

      /**
       * 申请失败,可以再此处解释原因,之后再次尝试
       *
       * @param permission request permission
       */
      void onFailed ( String permission );

      /**
       * 永久拒绝了,看来需要好好劝说一下了
       *
       * @param permission request permission
       */
      void onFinalDenied ( String permission );
}
