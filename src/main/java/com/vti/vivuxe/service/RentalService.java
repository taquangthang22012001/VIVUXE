package com.vti.vivuxe.service;

import com.vti.vivuxe.dto.request.RentalCreationRequest;
import com.vti.vivuxe.dto.response.RentalDTO;
import com.vti.vivuxe.entity.Car;
import com.vti.vivuxe.entity.Rental;
import com.vti.vivuxe.entity.User;
import com.vti.vivuxe.repository.CarRepository;
import com.vti.vivuxe.repository.RentalRepository;
import com.vti.vivuxe.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

@Service
@NoArgsConstructor
public class RentalService implements RentalServiceImp {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RentalRepository rentalRepository;

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private UserRepository userRepository;


	public void createRental(RentalCreationRequest request) {
		Rental rental = request.asRental();

		User user = userRepository.findById(request.getUserUserId())
				.orElseThrow(() -> new RuntimeException("User not found"));

		Car car = carRepository.findById(request.getCarCarId())
				.orElseThrow(() -> new RuntimeException("Car not found"));

		rental.setUser(user);

		rental.setCar(car);

		rentalRepository.save(rental);
	}

	public Page<RentalDTO> getAllRentals(Pageable pageable) {
		Page<Rental> rentalList = rentalRepository.findAll(pageable);

		Page<RentalDTO> rentalDTOS = rentalList.map(new Function<Rental, RentalDTO>() {
			@Override
			public RentalDTO apply(Rental entity) {
				RentalDTO dto = new RentalDTO(entity);
				// Conversion logic

				return dto;
			}
		});

		return rentalDTOS;
	}

	public RentalDTO getRentalById(Long id) {
		return rentalRepository.findById(id)
				.map(rental -> modelMapper.map(rental, RentalDTO.class)).orElse(null);
	}


	public Rental updateRental(Long id, RentalCreationRequest request) {
		Optional<Rental> optionalRental = rentalRepository.findById(id);

		if (optionalRental.isEmpty()) {
			throw new RuntimeException("Rental not found with id: " + id);
		}

		Rental existingRental = optionalRental.get();

		modelMapper.map(request, existingRental);

		return rentalRepository.save(existingRental);
	}


	public void deleteRentalById(Long id) {
		rentalRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Rental not found with id: " + id));

		Rental rental = rentalRepository.findById(id).get();
		rentalRepository.delete(rental);
	}


}
