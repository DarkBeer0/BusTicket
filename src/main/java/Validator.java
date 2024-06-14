import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Validator {
    private Map<String, Integer> violationCounts = new HashMap<>();

    public List<String> validate(BusTicket ticket) {
        List<String> violations = new ArrayList<>();

        if (ticket.price == null || ticket.price == 0) {
            violations.add("price");
        }

        if (Arrays.asList("DAY", "WEEK", "YEAR").contains(ticket.ticketType) && (ticket.startDate == null || ticket.startDate.isEmpty())) {
            violations.add("start date");
        }

        if (ticket.startDate != null && !ticket.startDate.isEmpty()) {
            try {
                LocalDate startDate = LocalDate.parse(ticket.startDate);
                if (startDate.isAfter(LocalDate.now())) {
                    violations.add("start date in the future");
                }
            } catch (DateTimeParseException e) {
                violations.add("invalid start date format");
            }
        }

        if (ticket.ticketType != null && !Arrays.asList("DAY", "WEEK", "MONTH", "YEAR").contains(ticket.ticketType)) {
            violations.add("ticket type");
        }

        if (ticket.price != null && ticket.price % 2 != 0) {
            violations.add("price not even");
        }

        violations.forEach(violation -> violationCounts.put(violation, violationCounts.getOrDefault(violation, 0) + 1));
        return violations;
    }

    public String getMostPopularViolation() {
        return violationCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("none");
    }
}
