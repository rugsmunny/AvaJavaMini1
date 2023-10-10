package TickTock.Chrono.Controller;

import TickTock.Chrono.Model.Date;
import TickTock.Chrono.Model.Time;
import TickTock.Chrono.View.SwingGui;
import TickTock.Chrono.util.ChronoOperations;
import TickTock.Chrono.util.State;

import java.time.LocalDateTime;


public abstract class Chrono implements ChronoOperations {

    static protected State state = State.START_UP;

    protected static Chrono chronoType;
    // håller "type" beroende på användarens val

    protected Chrono() {}
    // tom default konstruktor

    public static void run() {
        // vår "state-machine" där vi kollar vilket state som "kallats på" och utför därefter önskad operation

        switch (getState()) {
            case START_UP -> SwingGui.build();
            case TIME -> chronoType = Time.getInstance();
            case DATE -> chronoType = Date.getInstance();
            case CONFIG -> {}
        }
    }

    public static State getState() {
        return state;
    }

    public static void setState(State state) {
        Chrono.state = state;
        run();
    }

    public static Chrono getChronoType() {
        return chronoType;
    }
}