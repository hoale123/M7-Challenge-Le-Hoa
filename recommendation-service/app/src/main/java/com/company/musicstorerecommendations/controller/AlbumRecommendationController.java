package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.exception.BadIdException;
import com.company.musicstorerecommendations.model.AlbumRecommendation;
import com.company.musicstorerecommendations.repository.AlbumRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/albumRecommendation")
public class AlbumRecommendationController {
    @Autowired
    private AlbumRecommendationRepository albumRecommendationRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumRecommendation createAlbumRecommendation(@RequestBody AlbumRecommendation albumRecommendation){
        return albumRecommendationRepository.save(albumRecommendation);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AlbumRecommendation> getAllAlbumRecommendations(){
        return albumRecommendationRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AlbumRecommendation getAlbumRecommendationById(@PathVariable int id){
        Optional<AlbumRecommendation> optionalAlbumRecommendation= albumRecommendationRepository.findById(id);// what happen if we dont use optional; null has many reason like none, error
        if (optionalAlbumRecommendation.isPresent() == false){
            throw new RuntimeException("there is no AlbumRecommendation with id " + id);
        }
        return optionalAlbumRecommendation.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAlbumRecommendation(@PathVariable int id, @RequestBody AlbumRecommendation albumRecommendation){
        if (albumRecommendation.getId() == null){
            albumRecommendation.setId(id);
        } else if (albumRecommendation.getId() != id) {
            throw new BadIdException("the id in our path (" + id + ") is " +
                    "different from the id in your body (" + albumRecommendation.getId() + ").");
        }
        albumRecommendationRepository.save(albumRecommendation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbumRecommendation(@PathVariable("id") int id) {
        albumRecommendationRepository.deleteById(id);
    }
}
