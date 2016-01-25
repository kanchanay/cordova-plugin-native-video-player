package com.ksy.cordova.nativevideoplayer;

import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.content.Context;
import android.content.Intent;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaResourceApi;
import org.apache.cordova.PluginResult;
import org.json.JSONException;
import org.json.JSONObject;

public class NativeVideoPlayer extends CordovaPlugin {

  /**
   * Executes the request and returns PluginResult.
   *
   * @param action        The action to execute.
   * @param args          JSONArray of arguments for the plugin.
   * @param callbackId    The callback id used when calling back into JavaScript.
   * @return              A PluginResult object with a status and message.
   */
  public boolean execute(String action, CordovaArgs args, CallbackContext callbackContext) throws JSONException {

    if(action.equals("play")) {
      final String target = args.getString(0);
      return this.play(target);
    }

    // Don't return any result now
    PluginResult pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
    pluginResult.setKeepCallback(true);
    callbackContext.sendPluginResult(pluginResult);
    callbackContext = null;

    return false;
  }

  /**
   * Exute native player on android with the specified uri
   * @param target media url
   * @param callbackContext callback context
   * @return  A PluginResult object with a status and message.
   */
  private boolean play(String target) {
    final CordovaResourceApi resourceApi = webView.getResourceApi();
    final String targetStr = target;

    // Create dialog in new thread
    cordova.getActivity().runOnUiThread(new Runnable() {
        public void run() {
          Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
          try {
              Uri targetUri = resourceApi.remapUri(Uri.parse(targetStr));
              intent.setDataAndType(targetUri, "video/*");
              webView.getContext().startActivity(intent);
          } catch (IllegalArgumentException e) {

            return;
          }
        }
    });
    return false;
  }

}
