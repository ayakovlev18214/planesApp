package ru.nsu.fit.planesApp.repository;

import java.util.Collection;
import ru.nsu.fit.planesApp.dto.RouteDto;

public interface BookingRepository {
  void bookRoute(Collection<RouteDto> route);
}
