package com.company.musicstorecatalog.repository;

import com.company.musicstorecatalog.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {
}
