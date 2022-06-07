package com.company.musicstorecatalog.repository;

import com.company.musicstorecatalog.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Integer> {
}
