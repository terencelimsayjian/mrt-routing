package graph;

public enum MrtTrack {
  NORTH_SOUTH_LINE("NS", "North South Line", 1),
  EAST_WEST_LINE("EW", "East West Line", 2),
  NORTH_EAST_LINE("NE", "North East Line", 3),
  CIRCLE_LINE("CC", "Circle Lin", 4),
  DOWNTOWN_LINE("DT", "Downtown Line", 5),
  THOMSON_EAST_LINE("TE", "Thomson East Line", 6),
  BUKIT_PANJANG_LRT("BPLRT", "Bukit Panjang LRT", 7),
  SENGKANG_LRT("SLRT", "Sengkang LRT", 8),
  PUNGGOL_LRT("PLRT", "Punggol LRT", 9);

  private final String code;

  private final String displayName;

  private final int displayPriority;

  MrtTrack(String code, String displayName, int displayPriority) {
    this.code = code;
    this.displayName = displayName;
    this.displayPriority = displayPriority;
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

  public String getCode() {
    return code;
  }

  public String getDisplayName() {
    return displayName;
  }

  public int getDisplayPriority() {
    return displayPriority;
  }
}
