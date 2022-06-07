package com.company.musicstorecatalog.repository;

import com.company.musicstorecatalog.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track, Integer> {
}
