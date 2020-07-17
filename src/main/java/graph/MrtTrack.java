package graph;

public enum MrtTrack {
  NORTH_SOUTH_LINE("NS"),
  EAST_WEST_LINE("EW"),
  NORTH_EAST_LINE("NE"),
  CIRCLE_LINE("CC"),
  DOWNTOWN_LINE("DT"),
  THOMSON_EAST_LINE("TE"),
  BUKIT_PANJANG_LRT("BPLRT"),
  SENGKANG_LRT("SLRT"),
  PUNGGOL_LRT("PLRT");

  private final String code;

  MrtTrack(String code) {
    this.code = code;
  }

  public static MrtTrack fromCode(String code) {
    switch (code) {
      case "NS":
        return NORTH_SOUTH_LINE;
      case "EW":
        return EAST_WEST_LINE;
      case "NE":
        return NORTH_EAST_LINE;
      case "CC":
        return CIRCLE_LINE;
      case "DT":
        return DOWNTOWN_LINE;
      case "TE":
        return THOMSON_EAST_LINE;
      case "BPLRT":
        return BUKIT_PANJANG_LRT;
      case "SLRT":
        return SENGKANG_LRT;
      case "PLRT":
        return PUNGGOL_LRT;
      default:
        throw new RuntimeException("Failed to map code into MrtTrack");
    }
  }
}
