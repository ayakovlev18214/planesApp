package ru.nsu.fit.planesApp.service.impl;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.planesApp.dto.AirportScheduleInboundDto;
import ru.nsu.fit.planesApp.dto.AirportScheduleOutboundDto;
import ru.nsu.fit.planesApp.dto.AvailableAirportsDto;
import ru.nsu.fit.planesApp.dto.AvailableCitiesDto;
import ru.nsu.fit.planesApp.dto.RouteDto;
import ru.nsu.fit.planesApp.dto.mappers.FlightMapper;
import ru.nsu.fit.planesApp.model.Airport;
import ru.nsu.fit.planesApp.model.Flight;
import ru.nsu.fit.planesApp.model.Price;
import ru.nsu.fit.planesApp.repository.AirportRepository;
import ru.nsu.fit.planesApp.repository.BookingRepository;
import ru.nsu.fit.planesApp.repository.FlightRepository;
import ru.nsu.fit.planesApp.repository.PriceRepository;
import ru.nsu.fit.planesApp.service.FlightService;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
  private final FlightRepository flightRepository;
  private final FlightMapper mapper;
  private final Set<String> availableFlights = Set.of("Scheduled");
  private final BookingRepository bookingRepository;
  private final PriceRepository priceRepository;
  private final AirportRepository airportRepository;

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
  public List<RouteDto> getRoute(String from,
                                 String to,
                                 String date,
                                 String flightClass,
                                 Integer numberOfConn) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    try {
      Date departureDate = new Date(simpleDateFormat.parse(date).getTime());
      List<RouteDto> routes = new ArrayList<>();
      Set<String> visited = new HashSet<>();
      String city = from;
      if (from.length() == 3 && from.chars().allMatch(Character::isUpperCase)) {
        Airport airport = airportRepository.findFirstByAirportCode(from);
        assert airport != null;
        city = airport.getCity();
      }
      if (numberOfConn == null) {
        numberOfConn = 2;
      }
      visited.add(city);
      findRoutes(routes,
        new LinkedList<>(),
        visited,
        numberOfConn,
        city,
        from,
        to,
        flightClass,
        0,
        departureDate
      );
      return routes;
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  public void bookRoute(RouteDto route, String name) {
    bookingRepository.bookRoute(route, name);
  }

  @Override
  public void checkIn(String ticketNum, Integer flightId) {
    bookingRepository.checkIn(ticketNum, flightId);
  }

  @Override
  public List<AirportScheduleOutboundDto> getOutboundAirportFlights(String airport) {
    return flightRepository.findAllByDepartureAirport(airport).stream()
      .map(mapper::mapOutboundSchedule).collect(Collectors.toList());
  }

  private void findRoutes(
    List<RouteDto> routes,
    LinkedList<Flight> routeAccum,
    Set<String> visited,
    int maxIters,
    String fromCity,
    String from,
    String to,
    String flightClass,
    Integer sum,
    Date departureDate
  ) {
    if (visited.size() - 2 > maxIters) {
      routeAccum.removeLast();
      visited.remove(fromCity);
      return;
    }
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(departureDate);
    calendar.add(Calendar.DAY_OF_MONTH, 1);
    Date departureDatePlusOne = new Date(calendar.getTimeInMillis());
    //noinspection ConstantConditions
    if (routeAccum.size() > 0 && (routeAccum.peekLast().getArrivalAirport().equals(to)
      || routeAccum.peekLast().getArrivalCity().equals(to))) {
      //noinspection ConstantConditions
      routes.add(RouteDto.builder()
        .routeNodes(routeAccum.stream().map(x ->
          mapper.mapRouteNode(x,
            priceRepository.findPrice(x.getArrivalAirport(),
              x.getDepartureAirport(),
              flightClass))).collect(Collectors.toList()))
        .price(sum)
        .fareCondition(flightClass)
        .departureDate(routeAccum.get(0).getScheduledDeparture())
        .arrivalDate(routeAccum.peekLast().getScheduledArrival())
        .build());
      visited.remove(fromCity);
      routeAccum.removeLast();
      return;
    }
    Set<Flight> flights;
    if (from.length() == 3 && from.chars().allMatch(Character::isUpperCase)) {
      flights = new HashSet<>(flightRepository.findByAirport(
        from,
        departureDate,
        departureDatePlusOne));
    } else {
      flights = new HashSet<>(flightRepository.findByCity(
        from,
        departureDate,
        departureDatePlusOne));
    }
    flights.forEach(flight -> {
      if (!visited.contains(flight.getArrivalCity())) {
        Price p = priceRepository.findPrice(
          flight.getArrivalAirport(),
          flight.getDepartureAirport(),
          flightClass);
        if (p != null) {
          routeAccum.add(flight);
          visited.add(flight.getArrivalCity());
          findRoutes(
            routes,
            routeAccum,
            visited,
            maxIters,
            flight.getArrivalCity(),
            flight.getArrivalCity(),
            to,
            flightClass,
            sum + p.getMax(),
            flight.getScheduledArrival()
          );
        }
      }
    });
    if (routeAccum.size() > 0) {
      routeAccum.removeLast();
    }
    visited.remove(fromCity);
  }

}
