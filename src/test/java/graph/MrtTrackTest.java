package graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MrtTrackTest {

  @Test
  void shouldGetEnumFromCodeString() {
    assertEquals(MrtTrack.NORTH_SOUTH_LINE, MrtTrack.fromCode("NS"));
    assertEquals(MrtTrack.EAST_WEST_LINE, MrtTrack.fromCode("EW"));
    assertEquals(MrtTrack.NORTH_EAST_LINE, MrtTrack.fromCode("NE"));
    assertEquals(MrtTrack.CIRCLE_LINE, MrtTrack.fromCode("CC"));
    assertEquals(MrtTrack.DOWNTOWN_LINE, MrtTrack.fromCode("DT"));
    assertEquals(MrtTrack.THOMSON_EAST_LINE, MrtTrack.fromCode("TE"));
    assertEquals(MrtTrack.BUKIT_PANJANG_LRT, MrtTrack.fromCode("BPLRT"));
    assertEquals(MrtTrack.SENGKANG_LRT, MrtTrack.fromCode("SLRT"));
    assertEquals(MrtTrack.PUNGGOL_LRT, MrtTrack.fromCode("PLRT"));
  }
}
