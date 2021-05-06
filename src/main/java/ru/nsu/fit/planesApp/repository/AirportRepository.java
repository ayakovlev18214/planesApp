package ru.nsu.fit.planesApp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.fit.planesApp.model.Airport;

public interface AirportRepository extends JpaRepository<Airport, String> {
  List<Airport> findAllByCity(String city);
}
