package entities;

//import main.java.enums.PerfomanceName;

import enums.PerfomanceName;

public class Perfomance {
    private PerfomanceName name;

    public Perfomance(PerfomanceName name) {
        this.name = name;
    }

    public PerfomanceName getName() {
        return name;
    }
}
