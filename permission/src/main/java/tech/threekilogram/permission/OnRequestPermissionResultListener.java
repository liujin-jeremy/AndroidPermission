package tech.threekilogram.permission;

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
       * @param success 是否成功
       * @param isFinalResult 是否是最终结果 :false申请失败,但是还有弹窗 true永久拒绝了,申请不会弹出窗口了
       */
      void onResult ( String permission, boolean success, boolean isFinalResult );
}
