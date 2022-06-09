package com.company.musicstorerecommendations.repository;


import com.company.musicstorerecommendations.model.LabelRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRecommendationRepository extends JpaRepository<LabelRecommendation, Integer> {
}
