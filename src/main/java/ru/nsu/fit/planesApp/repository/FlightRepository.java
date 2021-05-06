package ru.nsu.fit.planesApp.repository;

import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.fit.planesApp.model.Airport;
import ru.nsu.fit.planesApp.model.Flight;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
  @Override
  List<Flight> findAll();

  List<Flight> findAllByStatusIn(Collection<String> statuses);

  List<Flight> findAllByDepartureAirport(String departureAirport);
  List<Flight> findAllByArrivalAirport(String arrivalAirport);
}
