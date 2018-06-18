#import "AndroidApkPlugin.h"
#import <plugin_android_apk/plugin_android_apk-Swift.h>

@implementation AndroidApkPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftAndroidApkPlugin registerWithRegistrar:registrar];
}
@end
