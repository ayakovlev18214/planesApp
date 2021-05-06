package ru.nsu.fit.planesApp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import ru.nsu.fit.planesApp.model.Airport;
import ru.nsu.fit.planesApp.model.Flight;

@Value
@Builder
@JsonDeserialize(builder = FliightDtoContainer.FliightDtoContainerBuilder.class)
public class FliightDtoContainer {

  List<Flight> flight; //TODO SHOULD BE DTO

  @JsonPOJOBuilder(withPrefix = "")
  public static class FliightDtoContainerBuilder {
  }
}
