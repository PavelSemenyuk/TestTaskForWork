import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class FlightFilter {

    /* Метод filterFlights принимает в параметры список всех рейсов и фильтруит их через стрим*/
    public static List<Flight> filterFlights(List<Flight> flights) {
        return flights.stream()
                .filter(FlightFilter::isValid)
                .collect(Collectors.toList());
    }

    /* Метод isValid проверяет рейс на допустимость*/
    private static boolean isValid(Flight flight) {
        return isDepartureAfterCurrentTime(flight) &&
                arrivalDateBeforeDepartureDate(flight) &&
                parkingLessThanTwoHours(flight);
    }

    /* Метод isDepartureAfterCurrentTime проверяет рейс на отправлением после текущего времени*/
    private static boolean isDepartureAfterCurrentTime(Flight flight) {
        LocalDateTime currentTime = LocalDateTime.now(); // получаю текушее время

        // создаю стрим и проверяю, все ли сегменты полета имеют время отправления после текущего времени
        return flight.getSegments().stream()
                .allMatch(segment -> segment.getDepartureDate().isAfter(currentTime));
    }


    /* Метод arrivalDateBeforeDepartureDate проверяет имеются сегменты с время прилёта раньше времени вылета*/
    private static boolean arrivalDateBeforeDepartureDate(Flight flight) {
        // через стрим проверяю сегмент на прибытие-отправления ивозврашаю true или false
        return flight.getSegments().stream()
                .noneMatch(segment -> segment.getArrivalDate().isBefore(segment.getDepartureDate()));
    }


    /* Метод parkingLessThanTwoHours принимает в качестве параметра
    Flight и возвращает  true если рейс стоит на земле менее 2х часов.  */
    private static boolean parkingLessThanTwoHours(Flight flight) {
        List<Segment> segments = flight.getSegments(); // получаем список полетов

        // циклом прохожу по всему листу
        for (int i = 0; i < segments.size() - 1; i++) {
            Segment currentSegment = segments.get(i);   // текуший сегмент
            Segment nextSegment = segments.get(i + 1);   // следующий сегмент

            // вычисляем промежуток и если он больше 2 часов то false
            Duration groundTime = Duration.between(currentSegment.getArrivalDate(), nextSegment.getDepartureDate());
            if (groundTime.toHours() > 2) {
                return false;
            }
        }
        return true;
    }
}
