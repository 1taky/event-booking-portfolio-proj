package com.portfolio.bookingapp.repositories;

import com.portfolio.bookingapp.models.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
    boolean existsByAddressAndName(String address, String name);
}
