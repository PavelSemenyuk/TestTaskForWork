import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();

        // Список рейсов
        System.out.println("Список рейсов:");
        flights.forEach(System.out::println);

        System.out.println();
        // Отфильтрованный список рейсов
        System.out.println("Отфильтрованный список рейсов:");
        List<Flight> filteredFlights = FlightFilter.filterFlights(flights);
        filteredFlights.forEach(System.out::println);
    }
}