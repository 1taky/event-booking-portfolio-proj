package com.portfolio.bookingapp.repositories;

import com.portfolio.bookingapp.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByVenueId(long venueId);

    boolean existsByTitleAndVenueId(String eventTitle, Long venueId);
}
