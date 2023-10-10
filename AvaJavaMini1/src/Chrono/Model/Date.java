package TickTock.Chrono.Model;


import TickTock.Chrono.Controller.Chrono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Date extends Chrono {

    private static Date INSTANCE;
    // Jag har stora bokstäver för att markera att ÄVEN om denna variabel inte är final så är den "ändå" det på grund av
    // att jag gjort en singleton både i denna klass och i Time så vi kommer alltid använda samma "instans" av
    // Time och Date utan att nya instanser skapas

    // protected static final DateTimeFormatter format = DateTimeFormatter.ISO_LOCAL_DATE;

    // #1 OBS!! 'DateTimeFormatter format' används bara en gång så cancel:ar den här uppe och kodar in den direkt.
    // Säg till om du har andra tankar

    protected static int offsetYears = 0, offsetMonths = 0, offsetDays = 0;
    // offsetVärden som gäller både i Time och Date

    public Date() {} // tom default konstruktor

    public static Date getInstance() { // här hämtar vi vår Date instans, finns den inte så skapas den (singleton)

        if(INSTANCE == null) {
            INSTANCE = new Date();
        }

        return INSTANCE;
    }

    // setOffset som ärvs från interfacet genom ChronoManager som vi extendar
    // så den finns inte i parent
    @Override
    public void setOffset(int x, int y, int z) {
        offsetYears += x;
        offsetMonths += y;
        offsetDays   += z;
    }

    // samma med denna
    @Override
    public String getInstant() {
        return LocalDateTime
                .now()
                .plusYears(offsetYears)
                .plusMonths(offsetMonths)
                .plusDays(offsetDays)
                .format(DateTimeFormatter.ISO_LOCAL_DATE); //#1 rad 15
    }
}
