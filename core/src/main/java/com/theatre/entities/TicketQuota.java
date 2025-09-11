package com.theatre.entities;

import com.theatre.enums.PerfomanceName;

public class TicketQuota {
    private final PerfomanceName perfomanceName;
    private final int maxTickets;
    private int soldTickets;

    public TicketQuota(PerfomanceName perfomanceName, int maxTickets) {
        this.perfomanceName = perfomanceName;
        this.maxTickets = maxTickets;
        this.soldTickets = 0;
    }

    public PerfomanceName getPerfomanceName() {
        return perfomanceName;
    }

    public int getMaxTickets() {
        return maxTickets;
    }

    public int getSoldTickets() {
        return soldTickets;
    }

    public boolean canSellTicket() {
        return soldTickets < maxTickets;
    }

    public void sellTicket() {
        if (canSellTicket()) {
            soldTickets++;
        } else {
            throw new IllegalStateException("Достигнуто максимальное количество билетов для " + perfomanceName);
        }
    }

    @Override
    public String toString() {
        return String.format("%s: продано %d из %d билетов",
                perfomanceName, soldTickets, maxTickets);
    }
}
