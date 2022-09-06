package com.nnk.springboot.service.implement;

import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.entity.RatingEntity;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.RatingRepository;
import com.nnk.springboot.service.IGenericService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RatingService implements IGenericService<RatingDTO> {

    private final RatingRepository ratingRepository;
    private final ModelMapper modelMapper;


    @Override
    public RatingDTO findById(Integer id) throws ResourceNotFoundException {

        RatingEntity ratingEntity = ratingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rating doesn't exist with id : " + id));

        return modelMapper.map(ratingEntity, RatingDTO.class);
    }

    @Override
    public List<RatingDTO> findAll() {
        return ratingRepository.findAll().stream()
                .map(rating -> modelMapper.map(rating, RatingDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RatingDTO create(RatingDTO ratingDTO) {

        RatingEntity ratingEntity = modelMapper.map(ratingDTO, RatingEntity.class);
        RatingEntity ratingEntitySaved = ratingRepository.save(ratingEntity);

        return modelMapper.map(ratingEntitySaved, RatingDTO.class);
    }

    @Override
    public RatingDTO update(Integer id, RatingDTO ratingDTO) throws ResourceNotFoundException {

        RatingEntity ratingFound = ratingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rating doesn't exist with id : " + id));

        String moodysRating = ratingDTO.getMoodysRating() != null
                ? ratingDTO.getMoodysRating()
                : ratingFound.getMoodysRating();

        String sandPRating = ratingDTO.getSandPRating() != null
                ? ratingDTO.getSandPRating()
                : ratingFound.getSandPRating();

        String fitchRating = ratingDTO.getFitchRating() != null
                ? ratingDTO.getFitchRating()
                : ratingFound.getFitchRating();

        Integer orderNumber = ratingDTO.getOrderNumber() != null
                ? ratingDTO.getOrderNumber()
                : ratingFound.getOrderNumber();


        ratingFound.setMoodysRating(moodysRating);
        ratingFound.setSandPRating(sandPRating);
        ratingFound.setFitchRating(fitchRating);
        ratingFound.setOrderNumber(orderNumber);

        ratingRepository.save(ratingFound);

        return modelMapper.map(ratingFound, RatingDTO.class);
    }

    @Override
    public void delete(Integer id) throws ResourceNotFoundException {

        RatingEntity ratingFound = ratingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rating doesn't exist with id : " + id));

        ratingRepository.delete(ratingFound);
    }
}
