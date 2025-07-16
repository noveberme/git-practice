package main.java.entities;

import main.java.enums.PerfomanceName;

public class TicketCount {
    private PerfomanceName perfomanceName;
    private int count;

    public TicketCount(PerfomanceName perfomanceName, int count) {
        this.perfomanceName = perfomanceName;
        this.count = count;
    }

    public PerfomanceName getPerfomanceName() {
        return perfomanceName;
    }

    public int getCount() {
        return count;
    }

    public void incrementCount() {
        this.count++;
    }

    @Override
    public String toString() {
        return
                "perfomanceName: " + perfomanceName +
                        ", count: " + count;
    }
}
