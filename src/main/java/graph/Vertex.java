package graph;

public class Vertex {
  private final String id;
  private final String displayName;
  private final double lat;
  private final double lng;

  public Vertex(String id) {
    this.id = id;
    this.lat = 0;
    this.lng = 0;
    this.displayName = id;
  }

  public Vertex(String id, String displayName, double lat, double lng) {
    this.id = id;
    this.displayName = displayName;
    this.lat = lat;
    this.lng = lng;
  }

  public Vertex(String id, String displayName) {
    this.id = id;
    this.displayName = displayName;
    this.lat = 0;
    this.lng = 0;
  }

  String getId() {
    return id;
  }

  public double getLat() {
    return lat;
  }

  public double getLng() {
    return lng;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Vertex vertex = (Vertex) o;

    return id.equals(vertex.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString() {
    return "Vertex{" +
           "id='" + id + '\'' +
           ", displayName='" + displayName + '\'' +
           '}';
  }
}
