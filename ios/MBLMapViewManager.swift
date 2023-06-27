import Mapbox;

@objc(MBLMapViewManager)

class MBLMapViewManager: RCTViewManager, MGLMapViewDelegate {

  var _mapView: MBLMapView? 

  override func view() -> UIView! {
    let mapView = MBLMapView(frame: MBLMapViewManager.RCT_MAPBOX_MIN_MAP_FRAME)

    let singleTap = UITapGestureRecognizer(target: self, action: #selector(handleMapTap(sender:)))

    _mapView = mapView
    
    return mapView
  }

  @objc func fitBounds(_ node: NSNumber,
                       latNorth: Double,
                       lonEast: Double,
                       latSouth: Double,
                       lonWest: Double,
                       padLeft: Int,
                       padTop: Int,
                       padRight: Int,
                       padBottom: Int,
                       duration: Int) {

    DispatchQueue.main.async {
      let view = self.bridge.uiManager.view(forReactTag: node)
      
      let mapView = view as! MBLMapView

      mapView.userTrackingMode = MGLUserTrackingMode.none

      let ne = CLLocationCoordinate2DMake(latNorth, lonEast)
      let sw = CLLocationCoordinate2DMake(latSouth, lonWest)

      let bounds = MGLCoordinateBoundsMake(sw, ne)

      let insets = mapView.reactMapPadding
      
      let camera = mapView.camera
      camera.pitch = 0
      camera.heading = 0
      mapView.setCamera(camera, animated: false)
      
      mapView.setVisibleCoordinateBounds(bounds, edgePadding:insets, animated: true) {
        
      }
    }
  }

  @objc @IBAction func handleMapTap(sender: UITapGestureRecognizer) {
    let mapView = sender.view as! MBLMapView
    let spot = sender.location(in: mapView)
     
    let area = CGFloat(44.0)
    let top = spot.y - (area / 2.0)
    let left = spot.x - (area / 2.0)
    let hitboxRect = CGRect(x: left, y: top, width: area, height: area)
    
    let features = mapView.visibleFeatures(in: hitboxRect, styleLayerIdentifiers: Set(["cota-stops"]))

    let tapCoordinate: CLLocationCoordinate2D = mapView.convert(spot, toCoordinateFrom: nil)
    var payload : [String:Any] = ["point":[tapCoordinate.longitude, tapCoordinate.latitude]]

    if let feature = features.first
      let geoJSON = feature.geoJSONDictionary()
      payload["feature"] = geoJSON
    }
    mapView.onPress?(["payload": payload])
  }

  func mapView(_ mapView: MGLMapView, regionDidChangeAnimated animated: Bool) {
    let reactMapView = mapView as! MBLMapView
    let event: [String:Any] = [
      "type": "mapcenterchange",
      "LatLng": [mapView.centerCoordinate.latitude, mapView.centerCoordinate.longitude]
    ]
    reactMapView.onMapCenterChange?(event)
  }

}