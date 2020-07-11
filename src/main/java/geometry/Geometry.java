package geometry;

public class Geometry {
  private static final int RADIUS_OF_EARTH_KM = 6371;

  public static double calculateHaversineDistanceKm(Coordinate c1, Coordinate c2) {
    double lat1 = c1.getLat();
    double lng1 = c1.getLng();
    double lat2 = c2.getLat();
    double lng2 = c2.getLat();

    var φ1 = lat1 * Math.PI / 180;
    var φ2 = lat2 * Math.PI / 180;
    var Δφ = (lat2 - lat1) * Math.PI / 180;
    var Δλ = (lng2 - lng1) * Math.PI / 180;

    var a = Math.sin(Δφ / 2) * Math.sin(Δφ / 2) +
            Math.cos(φ1) * Math.cos(φ2) *
            Math.sin(Δλ / 2) * Math.sin(Δλ / 2);
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    return RADIUS_OF_EARTH_KM * c;
  }

  public static double calculateCartesianDistance(Coordinate c1, Coordinate c2) {
    double y = Math.pow(c1.getLat() - c2.getLat(), 2);
    double x = Math.pow(c1.getLng() - c2.getLng(), 2);

    return Math.sqrt(x + y);
  }
}
