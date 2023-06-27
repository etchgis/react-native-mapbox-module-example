#import <React/RCTViewManager.h>

@interface RCT_EXTERN_MODULE(MBLMapViewManager, RCTViewManager)

RCT_REMAP_VIEW_PROPERTY(center, reactCenterCoordinate, NSArray<NSNumber*>*)
RCT_REMAP_VIEW_PROPERTY(zoom, reactZoomLevel, double)

RCT_EXPORT_VIEW_PROPERTY(onPress, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onMapCenterChange, RCTBubblingEventBlock)

RCT_EXTERN_METHOD(fitBounds:(nonnull NSNumber*)node
                  latNorth:(double)latNorth
                  lonEast:(double)lonEast
                  latSouth:(double)latSouth
                  lonWest:(double)lonWest
                  padLeft:(int)padLeft
                  padTop:(int)padTop
                  padRight:(int)padRight
                  padBottom:(int)padBottom
                  duration:(int)duration)

@end