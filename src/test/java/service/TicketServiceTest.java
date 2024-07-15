package service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TicketServiceTest {

    @Mock
    private Resource ticketsResource;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadTicketsFromFile_positive() throws IOException {
        String json = "[{\"id\":1,\"userId\":1,\"ticketType\":\"Type1\",\"creationDate\":\"2023-01-01T12:00:00.000+00:00\"}," +
                "{\"id\":2,\"userId\":2,\"ticketType\":\"Type2\",\"creationDate\":\"2023-01-02T12:00:00.000+00:00\"}]";
        InputStream inputStream = new ByteArrayInputStream(json.getBytes());

        when(ticketsResource.getInputStream()).thenReturn(inputStream);

        List<Ticket> mockTickets = List.of(
                new Ticket(1, 1, "Type1", Timestamp.valueOf("2023-01-01 12:00:00")),
                new Ticket(2, 2, "Type2", Timestamp.valueOf("2023-01-02 12:00:00"))
        );
        when(objectMapper.readValue(inputStream, new TypeReference<List<Ticket>>() {})).thenReturn(mockTickets);

        List<Ticket> tickets = ticketService.loadTicketsFromFile();

        assertNotNull(tickets);
        assertEquals(2, tickets.size());
        assertEquals(1, tickets.get(0).getId());
        assertEquals(2, tickets.get(1).getId());
    }

    @Test
    void testLoadTicketsFromFile_negative() throws IOException {
        when(ticketsResource.getInputStream()).thenThrow(new IOException("File not found"));

        IOException exception = assertThrows(IOException.class, () -> ticketService.loadTicketsFromFile());

        assertEquals("File not found", exception.getMessage());
    }

    @Test
    void testLoadTicketsFromFile_cornerCase() throws IOException {
        String json = "[]";
        InputStream inputStream = new ByteArrayInputStream(json.getBytes());

        when(ticketsResource.getInputStream()).thenReturn(inputStream);

        List<Ticket> mockTickets = Collections.emptyList();
        when(objectMapper.readValue(inputStream, new TypeReference<List<Ticket>>() {})).thenReturn(mockTickets);

        List<Ticket> tickets = ticketService.loadTicketsFromFile();

        assertNotNull(tickets);
        assertTrue(tickets.isEmpty());
    }
}