package TickTock.Chrono.Model;


import TickTock.Chrono.Controller.Chrono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Time extends Chrono {

    private static Time INSTANCE;

    //protected static final DateTimeFormatter format = DateTimeFormatter.ISO_LOCAL_TIME;
    protected static int offsetHours = 0, offsetMinutes = 0, offsetSeconds = 0;

    public Time() {}

    public static Time getInstance() {

        if(INSTANCE == null) {
            INSTANCE = new Time();
        }

        return INSTANCE;
    }
    @Override
    public void setOffset(int x, int y, int z) {
        offsetHours += x;
        offsetMinutes += y;
        offsetSeconds   += z;
    }

    @Override
    public String getInstant() {
        return LocalDateTime
                .now()
                .plusHours(offsetHours)
                .plusMinutes(offsetMinutes)
                .plusSeconds(offsetSeconds)
                .truncatedTo(ChronoUnit.SECONDS)
                .format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

}
