package ru.nsu.fit.planesApp.repository;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.nsu.fit.planesApp.model.Flight;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
  @Override
  List<Flight> findAll();

  List<Flight> findAllByStatusIn(Collection<String> statuses);

  List<Flight> findAllByDepartureAirport(String departureAirport);

  List<Flight> findAllByArrivalAirport(String arrivalAirport);

  @Query("select f from Flight f where " +
      "f.departureAirport = ?1 and " +
      "(f.scheduledDeparture between ?2 and ?3) and " +
      "f.actualArrival is null and f.actualDeparture is null")
  List<Flight> findByAirport(
      String departureAirport,
      Date scheduledDeparture,
      Date scheduledDeparture2
  );

  @Query("select f from Flight f where " +
      "f.departureCity = ?1 and " +
      "(f.scheduledDeparture between ?2 and ?3) and " +
      "f.actualArrival is null and f.actualDeparture is null")
  List<Flight> findByCity(
      String departureCity,
      Date scheduledDeparture,
      Date scheduledDeparture2
  );

  List<Flight> findAllByDepartureCity(String departureCity);

}
