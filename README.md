## 权限申请库

```
implementation 'tech.liujin:permission:1.0.0'
```

## 使用

请求

```
PermissionManager.request(
    this,
    new OnRequestPermissionResultListener() {
          @Override
          public void onResult ( String permission, boolean success, boolean isShowDialog ) {
          }
    },
    permission.ACCESS_FINE_LOCATION
);
```

![](img/pic00.gif)

