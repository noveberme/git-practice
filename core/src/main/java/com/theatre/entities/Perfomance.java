package com.theatre.entities;

import com.theatre.enums.PerfomanceName;

public class Perfomance {
    private PerfomanceName name;

    public Perfomance(PerfomanceName name) {
        this.name = name;
    }

    public PerfomanceName getName() {
        return name;
    }
}
