import java.util.logging.Handler;

import javax.naming.Context;

public class MBLMapView extends MapView implements OnMapReadyCallback, MapboxMap.OnMapClickListener,
    MapboxMap.OnCameraMoveListener, MapboxMap.OnCameraIdleListener, LocationListener {

  private MapboxMap mMap;
  private MBLMapViewManager mManager;
  private LatLng mCenter;
  private double mZoom;
  private Handler mHandler;
  private Context mContext;

  public MBLMapView(Context context, MBLMapViewManager manager, MapboxMapOptions options) {
    super(context, options);

    mContext = context;
    mManager = manager;

  }

  public void setCenter(double lat, double lng) {
    mCenter = new LatLng(lat, lng);
    updateCameraPosition(true, false, 0);
  }

  public void setZoom(double zoom) {
    mZoom = zoom;
    updateCameraPosition(false, false, 0);
  }

  private void updateCameraPosition(boolean updateTarget, boolean animate, int duration) {
    if (mMap != null) {
      CameraPosition.Builder builder = new CameraPosition.Builder(mMap.getCameraPosition()).bearing(mBearing)
          .tilt(mTilt).zoom(mZoom).padding(mInsets[3], mInsets[0], mInsets[1], mInsets[2]);
      if (updateTarget) {
        builder.target(mCenter);
      }
      CameraPosition newPosition = builder.build();
      CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(newPosition);
      if (animate) {
        mMap.animateCamera(cameraUpdate, duration);
      } else {
        mMap.setCameraPosition(newPosition);
      }
    }
  }

}