package csv;

class Edge {
  private String STATION_ID_A;
  private String STATION_ID_B;
  private String TIME_IN_MINUTES;

  public Edge(String STATION_ID_A, String STATION_ID_B, String TIME_IN_MINUTES) {
    this.STATION_ID_A = STATION_ID_A;
    this.STATION_ID_B = STATION_ID_B;
    this.TIME_IN_MINUTES = TIME_IN_MINUTES;
  }
}
