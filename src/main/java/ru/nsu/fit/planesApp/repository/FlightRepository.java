package ru.nsu.fit.planesApp.repository;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.fit.planesApp.model.Flight;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
  @Override
  List<Flight> findAll();

  List<Flight> findAllByStatusIn(Collection<String> statuses);

  List<Flight> findAllByDepartureAirport(String departureAirport);

  List<Flight> findAllByArrivalAirport(String arrivalAirport);

  List<Flight> findAllByDepartureAirportAndScheduledDepartureBetween(
    String departureAirport,
    Date scheduledDeparture,
    Date scheduledDeparture2);

  List<Flight> findAllByDepartureCityAndScheduledDepartureBetween(
    String departureAirport,
    Date scheduledDeparture,
    Date scheduledDeparture2);

  List<Flight> findAllByDepartureCity(String departureCity);

}
