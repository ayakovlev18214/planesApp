package ru.nsu.fit.planesApp.сontroller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.planesApp.dto.AirportDtoContainer;
import ru.nsu.fit.planesApp.service.AirportService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class AirportsController {

  private final AirportService airportService;

  @GetMapping("airports")
  public List<AirportDtoContainer> airportsInCity(@RequestParam String city) {
    return airportService.getAllAirportsInCity(city);
  }
}
