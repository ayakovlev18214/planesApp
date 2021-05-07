package ru.nsu.fit.planesApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.nsu.fit.planesApp.model.Price;

public interface PriceRepository extends JpaRepository<Price, Long> {
  @Query("select p from Price p " +
      "where p.arrivalAirport = ?1 and " +
      "p.departureAirport = ?2 and " +
      "p.fareConditions = ?3")
  Price findPrice(String arrival, String departure, String fareCond);
}
