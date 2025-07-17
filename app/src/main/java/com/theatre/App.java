package com.theatre;
/*Городское управление культуры предложило ученикам (10000 учеников во всех школах) приобрести билеты на любые
 из 10 спектаклей городского театра. Каждый ученик получил право приобрести по одному билету на каждый спектакль,
 но не обязан приобретать билет на все спектакли.

Напишите класс, который принимает с клавиатуры информацию о тех спектаклях, на которые решил приобрести билет каждый
(подумайте, как можно смоделировать ввод с помощью случайных чисел), и затем выводит на экран следующую информацию:
• количество билетов, заказанных на каждый спектакль;
• самый популярный спектакль (следует учесть вариант, что может быть несколько таких спектаклей);
• спектакль (спектакли), на который решили приобрести билеты*/

import entities.Perfomance;
import entities.Student;
import entities.TicketCount;
import enums.PerfomanceName;
import service.TheatreService;

import java.util.List;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class App
{
    public static final int NUMBER_STUDENTS = 10000;
    private static List<Perfomance> choices;

    public static void main(String[] args) {
            List<Student> students = new ArrayList<>();
            for (int i = 0; i < NUMBER_STUDENTS; i++) {
                students.add(new Student(choices));
            }

            TheatreService theatreService = new TheatreService();
            List<TicketCount> ticketsCount = theatreService.getCountTicketsForPerfomance(students);


            System.out.println("Perfomances with tickets count: \n");
            ticketsCount.forEach(System.out::println);

            printMostPopularPerformances(theatreService, ticketsCount);


            System.out.println("\nPerfomances, in which tickets was purchased: \n");
            List<PerfomanceName> purchasedPerfomance = theatreService.getPurchasedPerfomances(students);
            purchasedPerfomance.forEach(System.out::println);
    }


    //Изменить форму вывода
    private static void printMostPopularPerformances(TheatreService theatreService, List<TicketCount> ticketsCount) {
        List<PerfomanceName> mostPopularPerformances = theatreService.getMostPopularPerfomance(
                ticketsCount.stream().map(TicketCount::getCount).collect(Collectors.toList())
        );

        StringBuilder sb = new StringBuilder("\nMost popular performance(s): ");
        if (!mostPopularPerformances.isEmpty()) {
            for (int i = 0; i < mostPopularPerformances.size(); i++) {
                sb.append(mostPopularPerformances.get(i));
                if (i < mostPopularPerformances.size() - 1) {
                    sb.append(", ");
                }
            }
        } else {
            sb.append("None");
        }
        System.out.println(sb.toString());
    }
}
