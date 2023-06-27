package android.components.mapview;

import java.util.Map;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

public class MBLMapViewManager extends SimpleViewManager<MBLMapView> {

  public static final String REACT_CLASS = "MBLMapView";

  private MBLMapView mView;
  private ReactApplicationContext mContext;

  public MBLMapViewManager(ReactApplicationContext context) {
    // super(context);
    mContext = context;
  }

  public MBLMapView getMapView() {
    return mView;
  }

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  public MBLMapView createViewInstance(ThemedReactContext context) {
    MapboxMapOptions options = new MapboxMapOptions()
        .camera(new CameraPosition.Builder().target(new LatLng(39.984017, -83.005017)).zoom(9).build());
    return new MBLMapView(context, this, null);
  }

  @Override
  public Map getExportedCustomBubblingEventTypeConstants() {
    return MapBuilder.builder()
        .put("mapClick", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onPress")))
        .put("mapCenterChanged",
            MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onMapCenterChange")))
        .build();
  }

  public static final int METHOD_FIT_BOUNDS = 2;

  @Nullable
  @Override
  public Map<String, Integer> getCommandsMap() {
    return MapBuilder.<String, Integer>builder().put("fitBounds", METHOD_FIT_BOUNDS)
        .build();
  }

  @Override
  public void receiveCommand(MBLMapView mapView, int commandID, @Nullable ReadableArray args) {
    switch (commandID) {
      case METHOD_FIT_BOUNDS:
        mapView.setCamera(args.getDouble(0), args.getDouble(1), args.getDouble(2), args.getDouble(3), args.getInt(4),
            args.getInt(5), args.getInt(6), args.getInt(7), args.getInt(8));
        break;
    }
  }

  @ReactProp(name = "center")
  public void setCenter(MBLMapView mapView, ReadableArray coordinates) {
    mapView.setCenter(coordinates.getDouble(0), coordinates.getDouble(1));
  }

  @ReactProp(name = "zoom")
  public void setZoom(MBLMapView mapView, double zoomLevel) {
    mapView.setZoom(zoomLevel);
  }

}
