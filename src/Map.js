import React from 'react';
import {
  View, requireNativeComponent, Platform, findNodeHandle, NativeModules, UIManager,
} from 'react-native';
import PropTypes from 'prop-types';
import config from '../config';

const NATIVE_MAP_MODULE = 'MBLMapView';

const MBLMapView = requireNativeComponent(NATIVE_MAP_MODULE, Map, {
  nativeOnly: { onAndroidCallback: true }, // TODO: is this needed?
});

class Map extends React.Component {
  constructor(props) {
    super(props);
    this._onPress = this._onPress.bind(this);
    this._onMapCenterChange = this._onMapCenterChange.bind(this);
  }

  fitBounds(
    northEastCoordinates,
    southWestCoordinates,
    paddingLeft = 0,
    paddingTop = 0,
    paddingRight = 0,
    paddingBottom = 0,
    duration = 0.0,
  ) {
    this._runNativeCommand(
      'fitBounds',
      [
        northEastCoordinates[1], northEastCoordinates[0],
        southWestCoordinates[1], southWestCoordinates[0],
        paddingLeft, paddingTop, paddingRight, paddingBottom, duration,
      ],
    );
  }

  _runNativeCommand(name, args = []) {
    const node = findNodeHandle(this._nativeRef);
    if (node == null) {
      return;
    }
    if (Platform.OS === 'android') {
      const viewManagerConfig = NativeModules.UIManager.getViewManagerConfig
        ? NativeModules.UIManager.getViewManagerConfig(NATIVE_MAP_MODULE)
        : NativeModules.UIManager[NATIVE_MAP_MODULE];
      const command = viewManagerConfig.Commands[name];
      NativeModules.UIManager.dispatchViewManagerCommand(node, command, args);
    } else {
      const command = UIManager[NATIVE_MAP_MODULE].Commands[name];
      UIManager.dispatchViewManagerCommand(node, command, args);
    }
  }

  _onPress(e) {
    const { onPress } = this.props;
    if (onPress) {
      onPress(e.nativeEvent.payload);
    }
  }

  render() {
    const props = {
      ...this.props,
    };
    const callbacks = {
      ref: (nativeRef) => this._setNativeRef(nativeRef),
      onPress: this._onPress,
    };

    return (
      <MBLMapView
        {...props}
        {...callbacks}
      />
    );
  }
}