package com.nnk.springboot.service.implement;

import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.entity.BidListEntity;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.BidListRepository;
import com.nnk.springboot.service.IBidListService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BidListService implements IBidListService {

    private final BidListRepository bidListRepository;
    private final ModelMapper modelMapper;

    @Override
    public BidListDTO findById(Integer id) throws ResourceNotFoundException {
        BidListEntity bidListEntity = bidListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bid list doesn't exist with id : " + id));

        return modelMapper.map(bidListEntity, BidListDTO.class);
    }

    @Override
    public List<BidListEntity> findAll() {
        return bidListRepository.findAll();
    }

    @Override
    public BidListDTO create(BidListDTO bidListDTO) {

        BidListEntity bidListEntity = modelMapper.map(bidListDTO, BidListEntity.class);
        BidListEntity bidListEntitySaved = bidListRepository.save(bidListEntity);

        return modelMapper.map(bidListEntitySaved, BidListDTO.class);
    }

    @Override
    public BidListDTO update(Integer id, BidListDTO bidListDTO) throws ResourceNotFoundException {
        BidListEntity bidListFound = bidListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bid list doesn't exist with id : " + id));

        String account = bidListDTO.getAccount() != null
                ? bidListDTO.getAccount()
                : bidListFound.getAccount();

        String type = bidListDTO.getType() != null
                ? bidListDTO.getType()
                : bidListFound.getType();

        Double quantity = bidListDTO.getBidQuantity() != null
                ? bidListDTO.getBidQuantity()
                : bidListFound.getBidQuantity();

        bidListFound.setAccount(account);
        bidListFound.setType(type);
        bidListFound.setBidQuantity(quantity);

        bidListRepository.save(bidListFound);

        return modelMapper.map(bidListFound, BidListDTO.class);
    }

    @Override
    public void delete(Integer id) throws ResourceNotFoundException {
        BidListEntity bidListFound = bidListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bid list doesn't exist with id : " + id));

        bidListRepository.delete(bidListFound);
    }
}
