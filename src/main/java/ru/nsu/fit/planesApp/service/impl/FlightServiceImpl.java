package ru.nsu.fit.planesApp.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.expression.Sets;
import ru.nsu.fit.planesApp.dto.AirportScheduleInboundDto;
import ru.nsu.fit.planesApp.dto.AirportScheduleOutboundDto;
import ru.nsu.fit.planesApp.dto.AvailableAirportsDto;
import ru.nsu.fit.planesApp.dto.AvailableCitiesDto;
import ru.nsu.fit.planesApp.dto.mappers.FlightMapper;
import ru.nsu.fit.planesApp.model.Flight;
import ru.nsu.fit.planesApp.repository.FlightRepository;
import ru.nsu.fit.planesApp.service.FlightService;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
  private final FlightRepository flightRepository;
  private final FlightMapper mapper;
  private final Set<String> availableFlights = Set.of("Scheduled");

  @Override
  public Set<AvailableAirportsDto> getAllAvailableAirports() {
    return flightRepository.findAllByStatusIn(availableFlights).stream()
      .map(mapper::mapAirports).collect(Collectors.toSet());
  }

  @Override
  public Set<AvailableCitiesDto> getAllAvailableCities() {
    return flightRepository.findAllByStatusIn(availableFlights).stream()
      .map(mapper::mapCities).collect(Collectors.toSet());
  }

  @Override
  public List<AirportScheduleInboundDto> getInboundAirportFlights(String airport) {

    return flightRepository.findAllByArrivalAirport(airport).stream()
       .map(mapper::mapInboundSchedule).collect(Collectors.toList());
  }

  @Override
  public List<AirportScheduleOutboundDto> getOutboundAirportFlights(String airport) {
    return flightRepository.findAllByDepartureAirport(airport).stream()
      .map(mapper::mapOutboundSchedule).collect(Collectors.toList());
  }
}
