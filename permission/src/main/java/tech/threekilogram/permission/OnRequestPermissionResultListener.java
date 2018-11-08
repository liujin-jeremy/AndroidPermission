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
       * @param isShowDialog 是否显示了请求对话框, true:显示了
       */
      void onResult ( String permission, boolean success, boolean isShowDialog );
}
