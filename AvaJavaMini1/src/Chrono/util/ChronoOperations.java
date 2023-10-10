package TickTock.Chrono.util;

public interface ChronoOperations {

    // sätter skillnad i hh:mm:ss || YYYY:mm:dd från aktuellt ögonblick
    void setOffset(int x, int y, int z);
    // returnera aktuell instant (tidpunkt) som String
    String getInstant();
}
