package com.nnk.springboot.service.implement;

import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.entity.RatingEntity;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.impl.RatingRepository;
import com.nnk.springboot.service.AbstractServiceCrud;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RatingService extends AbstractServiceCrud<RatingEntity, RatingDTO> {

    private final RatingRepository ratingRepository;
    private final ModelMapper modelMapper;

    public RatingService(RatingRepository theRatingRepository) {
        super(theRatingRepository);
        this.modelMapper = new ModelMapper();
        this.ratingRepository = theRatingRepository;
    }

    @Override
    public RatingDTO update(Integer id, RatingDTO ratingDTO) throws ResourceNotFoundException {

        RatingEntity ratingFound = ratingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

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

}
