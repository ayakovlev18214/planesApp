package ru.nsu.fit.planesApp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.fit.planesApp.model.BoardingPass;


public interface BoardingPassRepository extends JpaRepository<BoardingPass, String> {
  List<BoardingPass> findAllByTicketNoAndFlightId(String ticketNo, Integer flightId);
}
