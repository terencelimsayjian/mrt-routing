package csv;

import com.opencsv.bean.CsvBindByName;

public class TrainData {
  public TrainData(String stationName, String stationId, double latitude, double longitude, String lineCode, int index) {
    this.stationName = stationName;
    this.stationId = stationId;
    this.latitude = latitude;
    this.longitude = longitude;
    this.lineCode = lineCode;
    this.index = index;
  }

  @CsvBindByName(column = "STATION_NAME", required = true)
  private String stationName;

  @CsvBindByName(column = "STATION_ID", required = true)
  private String stationId;

  @CsvBindByName(column = "X", required = true)
  private double xCoordinate;

  @CsvBindByName(column = "Y", required = true)
  private double yCoordinate;

  @CsvBindByName(column = "LATITUDE", required = true)
  private double latitude;

  @CsvBindByName(column = "LONGITUDE", required = true)
  private double longitude;

  @CsvBindByName(column = "COLOR", required = true)
  private String color;

  private String lineCode;

  private int index;

  public String getStationName() {
    return stationName;
  }

  public String getStationId() {
    return stationId;
  }

  public double getxCoordinate() {
    return xCoordinate;
  }

  public double getyCoordinate() {
    return yCoordinate;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public String getColor() {
    return color;
  }

  public String getLineCode() {
    return lineCode;
  }

  public void setLineCode(String lineCode) {
    this.lineCode = lineCode;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }
}
