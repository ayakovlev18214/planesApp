package ru.nsu.fit.planesApp.service;

import java.util.List;
import ru.nsu.fit.planesApp.dto.AirportDtoContainer;
import ru.nsu.fit.planesApp.dto.AirportScheduleInboundDto;
import ru.nsu.fit.planesApp.dto.AirportScheduleOutboundDto;


public interface AirportService {
  List<AirportDtoContainer> getAllAirportsInCity(String city);
}
