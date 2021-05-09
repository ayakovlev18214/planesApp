package ru.nsu.fit.planesApp.service;

import java.util.List;
import java.util.Set;
import ru.nsu.fit.planesApp.dto.AirportScheduleInboundDto;
import ru.nsu.fit.planesApp.dto.AirportScheduleOutboundDto;
import ru.nsu.fit.planesApp.dto.AvailableAirportsDto;
import ru.nsu.fit.planesApp.dto.AvailableCitiesDto;
import ru.nsu.fit.planesApp.dto.RouteDto;


public interface FlightService {
  Set<AvailableCitiesDto> getAllAvailableCities();
  Set<AvailableAirportsDto> getAllAvailableAirports();

  List<AirportScheduleInboundDto> getInboundAirportFlights(String airport);
  List<AirportScheduleOutboundDto> getOutboundAirportFlights(String airport);

  List<RouteDto> getRoute(String from, String to, String date, String flightClass, Integer numberOfConn) ;

  void bookRoute(RouteDto route, String name);
  void checkIn(String ticketNum, Integer flightId);
}
