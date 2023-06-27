import Foundation

class MBLMapView : MGLMapView {

  @objc var onPress: RCTDirectEventBlock?
  @objc var onMapCenterChange: RCTDirectEventBlock?

  override init(frame: CGRect) {
    reactZoomLevel = 0
    reactStyleURL = ""
    reactCenterCoordinate = [0, 0]
    reactMapPadding = UIEdgeInsets(top:0, left:0, bottom:0, right:0)
    super.init(frame: frame)
  }

  required init?(coder aDecoder: NSCoder) {
    fatalError("init(coder:) has not been implemented")
  }

  @objc var reactCenterCoordinate : Array<NSNumber> {
    didSet {
      updateMapCenter()
    }
  }

  func updateMapCenter() {
    let coordinate = CLLocationCoordinate2DMake(reactCenterCoordinate[0].doubleValue, reactCenterCoordinate[1].doubleValue)

    let camera = self.camera.copy() as! MGLMapCamera
    camera.centerCoordinate = coordinate
    self.setCamera(camera, withDuration:0, animationTimingFunction:nil, edgePadding:self.reactMapPadding, completionHandler:nil)
  }

  @objc var reactZoomLevel : Double {
    didSet {
      self.zoomLevel = reactZoomLevel;
    }
  }

}