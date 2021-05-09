package ru.nsu.fit.planesApp.repository.impl;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.planesApp.dto.RouteDto;
import ru.nsu.fit.planesApp.dto.RouteNode;
import ru.nsu.fit.planesApp.model.BoardingPass;
import ru.nsu.fit.planesApp.repository.BoardingPassRepository;
import ru.nsu.fit.planesApp.repository.BookingRepository;

@Repository
@RequiredArgsConstructor
public class BookingRepositoryImpl implements BookingRepository {

  private static final String BOOK_ROUTE =
    "INSERT INTO bookings (book_ref, book_date, total_amount) " +
      "VALUES (:book_ref, :book_date, :total_amount);";
  private static final String INSERT_TICKETS =
    "INSERT INTO tickets (ticket_no, book_ref, passenger_id, passenger_name) " +
      "VALUES (:ticket_no, :book_ref, :passenger_id, :passenger_name);";
  private static final String INSERT_TICKETS_FLIGHTS =
    "INSERT INTO ticket_flights (ticket_no, flight_id, fare_conditions, amount) " +
      "VALUES (:ticket_no, :flight_id, :fare_conditions, :amount);";

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final BoardingPassRepository boardingPassRepository;

  private static final String INSERT_BOARDING_PASS =
    "INSERT INTO boarding_passes (ticket_no, flight_id, boarding_no, seat_no)" +
    "VALUES (:ticket_no, :flight_id, :boarding_no, :seat_no)";

  @Transactional
  @Override
  public void bookRoute(RouteDto route, String personName) {
    Calendar calendar = Calendar.getInstance();

    String bookRef = "_" + UUID.randomUUID().toString().substring(0, 5);
    Map<String, Object> parameters = new HashMap<>();;
    parameters.put("book_ref", bookRef);
    parameters.put("book_date", calendar.getTime());
    parameters.put("total_amount",  route.getPrice());
    jdbcTemplate.update(BOOK_ROUTE, parameters);

    parameters.clear();

    String ticket_no = "_" + UUID.randomUUID().toString().substring(0, 12);
    parameters.put("ticket_no", ticket_no);
    parameters.put("book_ref", bookRef);
    parameters.put("passenger_id", UUID.randomUUID().toString().substring(0, 20));
    parameters.put("passenger_name",  personName);
    jdbcTemplate.update(INSERT_TICKETS, parameters);

    for (RouteNode routeNode : route.getRouteNodes()) {
      parameters.clear();

      parameters.put("ticket_no", ticket_no);
      parameters.put("flight_id", routeNode.getFlightId());
      parameters.put("fare_conditions", route.getFareCondition());
      parameters.put("amount",  routeNode.getPrice());
      jdbcTemplate.update(INSERT_TICKETS_FLIGHTS, parameters);
    }
  }

  @Override
  public void checkIn(String ticketNum, Integer flightId) {
    Map<String, Object> parameters = new HashMap<>();

    List<BoardingPass> boardingPasses =
      boardingPassRepository.findAllByTicketNoAndFlightId(ticketNum, flightId);
    int boardingNo = 1;
    String seatNo = "1A";
    if (boardingPasses.size() > 0) {
      boardingNo = boardingPasses.get(boardingPasses.size()-1).getBoardingNo() + 1;
    }
    parameters.put("ticket_no", ticketNum);
    parameters.put("flight_id", flightId);
    parameters.put("boarding_no", boardingNo);
    parameters.put("seat_no",  seatNo); // TODO NORMAL SEAT NUM

    jdbcTemplate.update(INSERT_BOARDING_PASS, parameters);
  }
}
