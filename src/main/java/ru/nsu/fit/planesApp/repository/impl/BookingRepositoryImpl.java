package ru.nsu.fit.planesApp.repository.impl;

import java.sql.Date;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.planesApp.dto.RouteDto;
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
  private Integer num = 0;
  private Integer passengerId = 0;

  @Transactional
  @Override
  public void bookRoute(Collection<RouteDto> route) {
    Calendar calendar = Calendar.getInstance();
    for (RouteDto routeDto : route) {
      Map<String, Object> parameters = new HashMap<>();
      parameters.put("book_ref", "_" + num);
      parameters.put("book_date", calendar.getTime());
      parameters.put("total_amount",  0); //TODO FIX
      jdbcTemplate.update(BOOK_ROUTE, parameters);

      parameters.clear();

      parameters.put("ticket_no", "_" + num);
      parameters.put("book_ref", "_" + num);
      parameters.put("passenger_id", "_" + passengerId++);
      parameters.put("passenger_name",  "JOPA"); //TODO ADD NORMAL NAME
      jdbcTemplate.update(INSERT_TICKETS, parameters);

      parameters.clear();

      parameters.put("ticket_no", "_" + num);
      parameters.put("flight_id", 182); //TODO FIND NORMAL AND ADD
      parameters.put("fare_conditions", "Business"); //TODO ADD NORMAL
      parameters.put("amount",  0); //TODO ADD NORMAL AMOUNT
      jdbcTemplate.update(INSERT_TICKETS_FLIGHTS, parameters);

      num++;
    }
  }
}
