package com.airbnb.android.react.maps;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.amap.api.maps.model.LatLngBounds;

public class RegionChangeEvent extends Event<RegionChangeEvent> {
  private final LatLngBounds bounds;
  private final boolean continuous;

  public RegionChangeEvent(int id, LatLngBounds bounds, boolean continuous) {
    super(id);
    this.bounds = bounds;
    this.continuous = continuous;
  }

  @Override
  public String getEventName() {
    return "topChange";
  }

  @Override
  public boolean canCoalesce() {
    return false;
  }

  @Override
  public void dispatch(RCTEventEmitter rctEventEmitter) {

    WritableMap event = new WritableNativeMap();
    event.putBoolean("continuous", continuous);

    WritableMap region = new WritableNativeMap();

    double latitude = (bounds.northeast.latitude + bounds.southwest.latitude) / 2;
    double longitude = (bounds.northeast.longitude + bounds.southwest.longitude) / 2;
    region.putDouble("latitude", latitude);
    region.putDouble("longitude", longitude);
    region.putDouble("latitudeDelta", bounds.northeast.latitude - bounds.southwest.latitude);
    region.putDouble("longitudeDelta", bounds.northeast.longitude - bounds.southwest.longitude);
    event.putMap("region", region);

    rctEventEmitter.receiveEvent(getViewTag(), getEventName(), event);
  }
}
