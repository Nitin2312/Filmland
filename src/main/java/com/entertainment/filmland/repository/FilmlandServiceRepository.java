package com.entertainment.filmland.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entertainment.filmland.model.FilmLandService;

@Repository 
public interface FilmlandServiceRepository extends JpaRepository<FilmLandService, Long>{


	public List<FilmLandService> findByServiceIdNotIn(List<Long>serviceId); 
	
	public FilmLandService findByServiceId(Long serviceId);
	
	public Optional<FilmLandService> findByName(String name);
} 
