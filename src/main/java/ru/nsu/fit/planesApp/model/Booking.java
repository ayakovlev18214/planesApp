package ru.nsu.fit.planesApp.model;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "bookings")
public class Booking {

  @Id
  @Column(name = "book_ref")
  private String book_ref;

  @Column(name = "book_date")
  private Date book_date;

  @Column(name = "total_amount")
  private double total_amount;

}
