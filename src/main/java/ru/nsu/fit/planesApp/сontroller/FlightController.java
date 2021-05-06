package ru.nsu.fit.planesApp.сontroller;

import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.planesApp.dto.AirportScheduleInboundDto;
import ru.nsu.fit.planesApp.dto.AirportScheduleOutboundDto;
import ru.nsu.fit.planesApp.dto.AvailableAirportsDto;
import ru.nsu.fit.planesApp.dto.AvailableCitiesDto;
import ru.nsu.fit.planesApp.service.FlightService;

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
}
