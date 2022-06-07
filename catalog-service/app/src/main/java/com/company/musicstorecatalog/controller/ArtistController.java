package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.exception.BadIdException;
import com.company.musicstorecatalog.model.Album;
import com.company.musicstorecatalog.model.Artist;
import com.company.musicstorecatalog.repository.AlbumRepository;
import com.company.musicstorecatalog.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/artist")
public class ArtistController {
    @Autowired
    private ArtistRepository artistRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Artist createArtist(@RequestBody Artist artist){
        return artistRepository.save(artist);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Artist> getAllArtists(){
        return artistRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Artist getArtistById(@PathVariable int id){
        Optional<Artist> optionalArtist = artistRepository.findById(id);// what happen if we dont use optional; null has many reason like none, error
        if (optionalArtist.isPresent() == false){
            throw new RuntimeException("there is no Artist with id " + id);
        }
        return optionalArtist.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateArtist(@PathVariable int id, @RequestBody Artist artist){
        if (artist.getId() == null){
            artist.setId(id);
        } else if (artist.getId() != id) {
            throw new BadIdException("the id in our path (" + id + ") is " +
                    "different from the id in your body (" + artist.getId() + ").");
        }
        artistRepository.save(artist);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtist(@PathVariable("id") int id) {
        artistRepository.deleteById(id);
    }
}
