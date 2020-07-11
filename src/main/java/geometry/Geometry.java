package geometry;

public class Geometry {
  public final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;

  public static double calculateCartesianDistance(Coordinate c1, Coordinate c2) {
    double y = Math.pow(c1.getLat() - c2.getLat(), 2);
    double x = Math.pow(c1.getLng() - c2.getLng(), 2);

    return Math.sqrt(x + y);
  }

  public static double calculateHaversineDistanceKm(Coordinate c1, Coordinate c2) {
    double userLat = c1.getLat();
    double userLng = c1.getLng();
    double venueLat = c2.getLat();
    double venueLng = c2.getLng();

    double latDistance = Math.toRadians(userLat - venueLat);
    double lngDistance = Math.toRadians(userLng - venueLng);

    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
               Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(venueLat)) *
               Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    return AVERAGE_RADIUS_OF_EARTH_KM * c;
  }
}
