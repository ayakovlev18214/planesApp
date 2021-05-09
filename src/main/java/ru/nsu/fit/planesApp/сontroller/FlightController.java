package ru.nsu.fit.planesApp.сontroller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.planesApp.dto.*;
import ru.nsu.fit.planesApp.service.FlightService;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import springfox.documentation.builders.ResponseBuilder;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class FlightController {

  private final FlightService flightService;

  @GetMapping("cities/available")
  public Set<AvailableCitiesDto> getCities() {
    return flightService.getAllAvailableCities();
  }

  @GetMapping("airports/available")
  public Set<AvailableAirportsDto> getAirports() {
    return flightService.getAllAvailableAirports();
  }

  @GetMapping("schedule/inbound")
  public List<AirportScheduleInboundDto> airportScheduleInbound(@RequestParam String airport) {
    return flightService.getInboundAirportFlights(airport);
  }

  @GetMapping("schedule/outbound")
  public List<AirportScheduleOutboundDto> airportScheduleOutbound(@RequestParam String airport) {
    return flightService.getOutboundAirportFlights(airport);
  }

  @GetMapping("route")
  public List<RouteDto> findRoute(@RequestParam String from,
                                  @RequestParam String to,
                                  @RequestParam String departureDate,
                                  @RequestParam String flightClass,
                                  @RequestParam(required = false) Integer numberOfConnections) {
    return flightService.getRoute(from, to, departureDate, flightClass, numberOfConnections);
  }

  @PutMapping("book")
  public ResponseEntity<Resource> bookRoute(@RequestBody Data data) {
    flightService.bookRoute(data.getRoute(), data.getPersonFullName());
    return ResponseEntity.ok().build();
  }

  @PutMapping("checkIn")
  public ResponseEntity<Resource> checkIn(@RequestParam String ticketNum,
                                          @RequestParam Integer flightId) {
    flightService.checkIn(ticketNum, flightId);
    return ResponseEntity.ok().build();
  }
}

@Getter
@Setter
class Data {
  private String personFullName;
  private RouteDto route;
}
