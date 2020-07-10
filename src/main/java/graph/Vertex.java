package graph;

class Vertex {
  private final String name;

  Vertex(String name) {
    this.name = name;
  }

  String getName() {
    return name;
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
