package geometry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeometryTest {

  @Test
  void calculateHaversineDistanceBetweenTwoPoints1() {
    Coordinate c1 = new Coordinate(64.1265, -21.8174);
    Coordinate c2 = new Coordinate(40.7128, -74.0060);

    double haversineDistance = Geometry.calculateHaversineDistanceKm(c1, c2);

    assertEquals(4705.12, haversineDistance, 0.1);
  }

  @Test
  void calculateHaversineDistanceBetweenTwoPoints2() {
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(3, 4);

    double haversineDistance = Geometry.calculateHaversineDistanceKm(c1, c2);

    assertEquals(248.6, haversineDistance, 0.1);
  }
}
