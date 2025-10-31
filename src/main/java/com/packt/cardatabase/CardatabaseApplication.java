package com.packt.cardatabase;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.packt.cardatabase.domain.AppUser;
import com.packt.cardatabase.domain.AppUserRepository;
import com.packt.cardatabase.domain.Car;
import com.packt.cardatabase.domain.CarRepository;
import com.packt.cardatabase.domain.Owner;
import com.packt.cardatabase.domain.OwnerRepository;

@SpringBootApplication
public class CardatabaseApplication implements CommandLineRunner{
	private static final Logger logger = LoggerFactory.getLogger(
			CardatabaseApplication.class
			);
	
	private final CarRepository repository;
	private final OwnerRepository orepository;
	private final AppUserRepository urepository;
	
	public CardatabaseApplication(CarRepository repository, OwnerRepository orepository, AppUserRepository urepository) {
		this.repository = repository;
		this.orepository = orepository;
		this.urepository = urepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(CardatabaseApplication.class, args);
		logger.info("Applicaion started");
	}
	
	@Override
	public void run(String... args) throws Exception {
		Owner owner1 = new Owner("John", "Johnson");
		Owner owner2 = new Owner("Mary", "Robinson");
		orepository.saveAll(Arrays.asList(owner1, owner2));
		
		repository.save(new Car("Ford", "Mustang", "Red", "ADF-1221", 2023, 59000, owner1));
		repository.save(new Car("Nissan", "Leaf", "White", "SSJ-1221", 2020, 29000, owner2));
		repository.save(new Car("Toyota", "Highlander", "Black", "XSE", 2022, 39000, owner2));
		
		urepository.save(new AppUser("user","$2y$10$GalEz.Vfy0dF/bqrof0KhulmljPY2.qrZjaWQ80XZ/1G0w1lXHHkq","USER"));
		urepository.save(new AppUser("admin", "$2y$10$cVn0e0uEZlFbPHXYelqGkepydYvtHg89haW2R0vOWzXN3d.FslfV2", "ADMIN"));

	
	// Fetch all cars and log to console
	for (Car car : repository.findAll()) {
		logger.info("brand: {}, model: {}", car.getBrand(), car.getModel());
	}
}
	
}
