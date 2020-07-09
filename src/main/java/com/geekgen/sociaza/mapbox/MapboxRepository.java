package com.geekgen.sociaza.mapbox;

/*
    @TODO extend the appropriate JpaRepository to get common database operations for Post
    Add also the correct annotation to describe the Repository.
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface MapboxRepository extends JpaRepository<Mapbox,Long> {
}
