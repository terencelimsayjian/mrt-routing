package traindataparser;

public class Edge {
  private String STATION_ID_A;
  private String STATION_ID_B;
  private String TIME_IN_MINUTES;

  public Edge(String STATION_ID_A, String STATION_ID_B, String TIME_IN_MINUTES) {
    this.STATION_ID_A = STATION_ID_A;
    this.STATION_ID_B = STATION_ID_B;
    this.TIME_IN_MINUTES = TIME_IN_MINUTES;
  }

  public String getStationIdA() {
    return STATION_ID_A;
  }

  public String getStationIdB() {
    return STATION_ID_B;
  }

  public String getTimeInMinutes() {
    return TIME_IN_MINUTES;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Edge edge = (Edge) o;

    if (STATION_ID_A.equals(((Edge) o).STATION_ID_B) && STATION_ID_B.equals(((Edge) o).STATION_ID_A) && TIME_IN_MINUTES.equals(((Edge) o).TIME_IN_MINUTES)) {
      return true;
    }

    if (!STATION_ID_A.equals(edge.STATION_ID_A)) return false;
    if (!STATION_ID_B.equals(edge.STATION_ID_B)) return false;
    return TIME_IN_MINUTES.equals(edge.TIME_IN_MINUTES);
  }

  @Override
  public int hashCode() {
    int result = STATION_ID_A.hashCode();
    result = 31 * result + STATION_ID_B.hashCode();
    result = 31 * result + TIME_IN_MINUTES.hashCode();
    return result;
  }
}
