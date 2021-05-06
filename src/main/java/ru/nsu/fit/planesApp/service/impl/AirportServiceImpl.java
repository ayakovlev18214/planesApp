package ru.nsu.fit.planesApp.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.planesApp.dto.AirportDtoContainer;
import ru.nsu.fit.planesApp.dto.AirportScheduleInboundDto;
import ru.nsu.fit.planesApp.dto.AirportScheduleOutboundDto;
import ru.nsu.fit.planesApp.dto.mappers.AirportMapper;
import ru.nsu.fit.planesApp.repository.AirportRepository;
import ru.nsu.fit.planesApp.service.AirportService;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

  private final AirportRepository airportRepository;
  private final AirportMapper mapper;

  @Override
  public List<AirportDtoContainer> getAllAirportsInCity(String city) {
    return airportRepository.findAllByCity(city).stream()
      .map(mapper::mapAirportName).collect(Collectors.toList());
  }
}
