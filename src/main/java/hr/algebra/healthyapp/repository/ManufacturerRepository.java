package hr.algebra.healthyapp.repository;

import hr.algebra.healthyapp.model.Manufacturer;
import hr.algebra.healthyapp.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

}
