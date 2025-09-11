package com.theatre;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.theatre.entities.Perfomance;
import com.theatre.entities.Student;
import com.theatre.entities.TicketCount;
import com.theatre.enums.PerfomanceName;
import com.theatre.service.TheatreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestTheatreMokito {

    @Mock
    private Student mockStudent;

    @InjectMocks
    private TheatreService theatreService;

    @Test
    void getCountTicketsForPerfomance_shouldReturnEmptyList_whenNoStudents() {
        List<Student> emptyList = Collections.emptyList();
        List<TicketCount> result = TheatreService.getCountTicketsForPerfomance(emptyList);

        assertNotNull(result);
        assertEquals(PerfomanceName.values().length, result.size());
        assertTrue(result.stream().allMatch(tc -> tc.getCount() == 0));
    }

    @Test
    void getCountTicketsForPerfomance_shouldCountTicketsCorrectly() {
        Perfomance perf1 = new Perfomance(PerfomanceName.HAMLET);
        Perfomance perf2 = new Perfomance(PerfomanceName.ROMEO_AND_JULIET);

        Student student1 = mock(Student.class);
        when(student1.getPerfomanceChoice()).thenReturn(Arrays.asList(perf1, perf2));

        Student student2 = mock(Student.class);
        when(student2.getPerfomanceChoice()).thenReturn(Arrays.asList(perf1));

        List<Student> students = Arrays.asList(student1, student2);

        List<TicketCount> result = TheatreService.getCountTicketsForPerfomance(students);

        assertEquals(2, result.stream()
                .filter(tc -> tc.getPerfomanceName() == PerfomanceName.HAMLET)
                .findFirst().get().getCount());
        assertEquals(1, result.stream()
                .filter(tc -> tc.getPerfomanceName() == PerfomanceName.ROMEO_AND_JULIET)
                .findFirst().get().getCount());
    }

    @Test
    void getPurchasedPerfomances_shouldReturnNull_whenNullInput() {
        List<PerfomanceName> result = TheatreService.getPurchasedPerfomances(null);
        assertNull(result);
    }

    @Test
    void getPurchasedPerfomances_shouldReturnUniqueSortedPerfomances() {
        Perfomance perf1 = new Perfomance(PerfomanceName.HAMLET);
        Perfomance perf2 = new Perfomance(PerfomanceName.ROMEO_AND_JULIET);
        Perfomance perf3 = new Perfomance(PerfomanceName.HAMLET); // duplicate

        Student student1 = mock(Student.class);
        when(student1.getPerfomanceChoice()).thenReturn(Arrays.asList(perf1, perf2));

        Student student2 = mock(Student.class);
        when(student2.getPerfomanceChoice()).thenReturn(Arrays.asList(perf3));

        List<Student> students = Arrays.asList(student1, student2);

        List<PerfomanceName> result = TheatreService.getPurchasedPerfomances(students);

        assertEquals(2, result.size());
        assertEquals(PerfomanceName.HAMLET, result.get(0)); // sorted first
        assertEquals(PerfomanceName.ROMEO_AND_JULIET, result.get(1));
    }

    @Test
    void getPurchasedPerfomances_shouldHandleStudentWithNoPerfomances() {
        Student student = mock(Student.class);
        when(student.getPerfomanceChoice()).thenReturn(new ArrayList<>());

        List<Student> students = Collections.singletonList(student);

        List<PerfomanceName> result = TheatreService.getPurchasedPerfomances(students);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getCountTicketsForPerfomance_shouldHandleStudentWithNoPerfomances() {
        Student student = mock(Student.class);
        when(student.getPerfomanceChoice()).thenReturn(new ArrayList<>());

        List<Student> students = Collections.singletonList(student);

        List<TicketCount> result = TheatreService.getCountTicketsForPerfomance(students);

        assertNotNull(result);
        assertEquals(PerfomanceName.values().length, result.size());
        assertTrue(result.stream().allMatch(tc -> tc.getCount() == 0));
    }
}