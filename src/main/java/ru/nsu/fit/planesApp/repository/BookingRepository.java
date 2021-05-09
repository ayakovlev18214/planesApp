package ru.nsu.fit.planesApp.repository;

import org.springframework.data.jpa.repository.Query;
import ru.nsu.fit.planesApp.dto.RouteDto;

public interface BookingRepository {
  void bookRoute(RouteDto route, String personName);

  void checkIn(String ticketNum, Integer flightId);
}
