package com.rubyplayapp.repositories;

import com.rubyplayapp.domain.Visitor;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorRepository extends JpaRepository<Visitor, Integer> {

	Optional<Visitor> findByName(final String name);

	Boolean existsByName(final String name);
}
