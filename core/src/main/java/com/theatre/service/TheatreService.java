package com.theatre.service;

import com.theatre.entities.Student;
import com.theatre.entities.TicketCount;
import com.theatre.enums.PerfomanceName;
import com.theatre.entities.Perfomance;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TheatreService {
    private static final Logger logger = LogManager.getLogger(TheatreService.class);

    public static List<TicketCount> getCountTicketsForPerfomance(List<Student> students) {
        logger.info("Starting getCountTicketsForPerfomance with students list size: {}",
                students == null ? 0 : students.size());

        List<TicketCount> ticketsCounts = PerfomanceName.getAllPerfomanceNames().stream()
                .map(perfomanceName -> new TicketCount(perfomanceName, 0))
                .collect(Collectors.toList());

        students.stream()
                .map(Student::getPerfomanceChoice)
                .flatMap(List::stream)
                .forEach(perfomance -> {
                    for (TicketCount ticketCount : ticketsCounts) {
                        if (ticketCount.getPerfomanceName() == perfomance.getName()) {
                            ticketCount.incrementCount();
                            break;
                        }
                    }
                });

        logger.debug("Calculated ticket counts: {}", ticketsCounts);
        return ticketsCounts;
    }

    public List<PerfomanceName> getMostPopularPerfomance(List<Integer> ticketsCount) {
        logger.info("Starting getMostPopularPerfomance with ticketsCount: {}", ticketsCount);

        if (ticketsCount == null || ticketsCount.isEmpty()) {
            logger.warn("Empty or null ticketsCount list provided");
            return new ArrayList<>();
        }

        Optional<Integer> maxTickets = ticketsCount.stream().max(Integer::compareTo);

        if (maxTickets.isPresent()) {
            int maxCount = maxTickets.get();
            List<PerfomanceName> result = IntStream.range(0, ticketsCount.size())
                    .filter(i -> Integer.compare(ticketsCount.get(i), maxCount) == 0)
                    .mapToObj(i -> PerfomanceName.values()[i])
                    .toList();

            logger.debug("Most popular performances: {}", result);
            return result;
        } else {
            logger.warn("No max tickets count found in the list");
            return new ArrayList<>();
        }
    }

    public static List<PerfomanceName> getPurchasedPerfomances(List<Student> students) {
        logger.info("Starting getPurchasedPerfomances with students list size: {}",
                students == null ? 0 : students.size());

        if (students == null || students.isEmpty()) {
            logger.warn("Empty or null students list provided");
            return null;
        }

        List<PerfomanceName> result = students.stream()
                .map(Student::getPerfomanceChoice)
                .flatMap(List::stream)
                .map(Perfomance::getName)
                .distinct()
                .sorted(Comparator.comparing(PerfomanceName::name))
                .collect(Collectors.toList());

        logger.debug("Purchased performances: {}", result);
        return result;
    }
}
