package android;

import java.util.ArrayList;
import java.util.List;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import android.components.mapview.MBLMapViewManager;

public class MapBoxLitePackage implements ReactPackage {

  @Override
  public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
    List<NativeModule> modules = new ArrayList<>();
    modules.add(new MapBoxLiteModule(reactContext));
    return modules;
  }

  @Override
  public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
    List<ViewManager> managers = new ArrayList<>();
    managers.add(new MBLMapViewManager(reactContext));
    return managers;
  }
  
}
