#import <React/RCTComponent.h>

@interface MBLMapView : MGLMapView

// properties
@property (nonatomic, copy) NSArray<NSNumber*> *reactCenterCoordinate;
@property (nonatomic, assign) double reactZoomLevel;
// events
@property (nonatomic, copy) RCTBubblingEventBlock onPress;
@property (nonatomic, copy) RCTBubblingEventBlock onMapCenterChange;

@end
