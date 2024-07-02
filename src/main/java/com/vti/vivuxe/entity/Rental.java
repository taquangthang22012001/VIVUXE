package com.vti.vivuxe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vti.vivuxe.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Data
@NoArgsConstructor
public class Rental {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rental_id")
	private Long rentalId;

	@Temporal(TemporalType.DATE)
	private Date rentalDate;

	@Temporal(TemporalType.DATE)
	private Date rentalReturn;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status status;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", nullable = false, referencedColumnName = "user_id")
	private User user;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "car_id", nullable = false, referencedColumnName = "car_id")
	private Car car;

	@Override
	public String toString() {
		return "Rental{" +
				"rentalId=" + rentalId +
				", rental_date='" + rentalDate + '\'' +
				", rental_return='" + rentalReturn + '\'' +
				", status=" + status +
				'}';
	}


}
