package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.exception.BadIdException;
import com.company.musicstorerecommendations.model.LabelRecommendation;
import com.company.musicstorerecommendations.repository.LabelRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("labelRecommendation")
public class LabelRecommendationController {
    @Autowired
    private LabelRecommendationRepository labelRecommendationRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LabelRecommendation createLabelRecommendation(@RequestBody LabelRecommendation labelRecommendation){
        return labelRecommendationRepository.save(labelRecommendation);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LabelRecommendation> getAllLabelRecommendations(){
        return labelRecommendationRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LabelRecommendation getLabelRecommendationById(@PathVariable int id){
        Optional<LabelRecommendation> optionalLabelRecommendation= labelRecommendationRepository.findById(id);// what happen if we dont use optional; null has many reason like none, error
        if (optionalLabelRecommendation.isPresent() == false){
            throw new RuntimeException("there is no LabelRecommendation with id " + id);
        }
        return optionalLabelRecommendation.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLabelRecommendation(@PathVariable int id, @RequestBody LabelRecommendation labelRecommendation){
        if (labelRecommendation.getId() == null){
            labelRecommendation.setId(id);
        } else if (labelRecommendation.getId() != id) {
            throw new BadIdException("the id in our path (" + id + ") is " +
                    "different from the id in your body (" + labelRecommendation.getId() + ").");
        }
        labelRecommendationRepository.save(labelRecommendation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabelRecommendation(@PathVariable("id") int id) {
        labelRecommendationRepository.deleteById(id);
    }
}
