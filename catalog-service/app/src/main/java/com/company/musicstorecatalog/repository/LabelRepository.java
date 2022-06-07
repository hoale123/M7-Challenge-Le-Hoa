package com.company.musicstorecatalog.repository;

import com.company.musicstorecatalog.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label, Integer> {
}
