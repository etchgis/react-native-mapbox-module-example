package android;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import com.mapbox.mapboxsdk.Mapbox;

public class MapBoxLiteModule extends ReactContextBaseJavaModule {

  private ReactContext reactContext;

  public MapBoxLiteModule(ReactApplicationContext context) {
    super(context);
    reactContext = context;
  }
  
}
