package graph;

class Vertex {
  private final String name;
  private final double lat;
  private final double lng;
  private final String code;

  Vertex(String name, double lat, double lng, String code) {
    this.name = name;
    this.lat = lat;
    this.lng = lng;
    this.code = code;
  }

  Vertex(String name) {
    this.name = name;
    this.lat = 0;
    this.lng = 0;
    this.code = "";
  }

  String getName() {
    return name;
  }

  public double getLat() {
    return lat;
  }

  public double getLng() {
    return lng;
  }

  public String getCode() {
    return code;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Vertex vertex = (Vertex) o;

    return name.equals(vertex.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public String toString() {
    return "Vertex{" +
           "name='" + name + '\'' +
           '}';
  }
}
