package com.airbnb.android.react.maps;

import com.amap.api.maps.model.LatLngBounds;

public class LatLngBoundsUtils {
  public static boolean BoundsAreDifferent(LatLngBounds a, LatLngBounds b) {


    double latA = (a.northeast.latitude + a.southwest.latitude) / 2;
    double lngA = (a.northeast.longitude + a.southwest.longitude) / 2;
    double latDeltaA = a.northeast.latitude - a.southwest.latitude;
    double lngDeltaA = a.northeast.longitude - a.southwest.longitude;

    double latB = (b.northeast.latitude + b.southwest.latitude) / 2;
    double lngB = (b.northeast.longitude + b.southwest.longitude) / 2;
    double latDeltaB = b.northeast.latitude - b.southwest.latitude;
    double lngDeltaB = b.northeast.longitude - b.southwest.longitude;

    double latEps = LatitudeEpsilon(a, b);
    double lngEps = LongitudeEpsilon(a, b);

    return
        different(latA, latB, latEps) ||
            different(lngA, lngB, lngEps) ||
            different(latDeltaA, latDeltaB, latEps) ||
            different(lngDeltaA, lngDeltaB, lngEps);
  }

  private static boolean different(double a, double b, double epsilon) {
    return Math.abs(a - b) > epsilon;
  }

  private static double LatitudeEpsilon(LatLngBounds a, LatLngBounds b) {
    double sizeA = a.northeast.latitude - a.southwest.latitude; // something mod 180?
    double sizeB = b.northeast.latitude - b.southwest.latitude; // something mod 180?
    double size = Math.min(Math.abs(sizeA), Math.abs(sizeB));
    return size / 2560;
  }

  private static double LongitudeEpsilon(LatLngBounds a, LatLngBounds b) {
    double sizeA = a.northeast.longitude - a.southwest.longitude;
    double sizeB = b.northeast.longitude - b.southwest.longitude;
    double size = Math.min(Math.abs(sizeA), Math.abs(sizeB));
    return size / 2560;
  }
}
