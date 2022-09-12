package com.nnk.springboot.service.implement;

import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.entity.BidListEntity;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.impl.BidListRepository;
import com.nnk.springboot.service.AbstractServiceCrud;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BidListService extends AbstractServiceCrud<BidListEntity, BidListDTO> {

    private final BidListRepository bidListRepository;
    private final ModelMapper modelMapper;

    public BidListService(BidListRepository theBidListRepository) {
        super(theBidListRepository);
        this.modelMapper = new ModelMapper();
        this.bidListRepository = theBidListRepository;
    }

    @Override
    public BidListDTO update(Integer id, BidListDTO dto) throws ResourceNotFoundException {
        BidListEntity bidListFound = bidListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        String account = dto.getAccount() != null
                ? dto.getAccount()
                : bidListFound.getAccount();

        String type = dto.getType() != null
                ? dto.getType()
                : bidListFound.getType();

        Double quantity = dto.getBidQuantity() != null
                ? dto.getBidQuantity()
                : bidListFound.getBidQuantity();

        bidListFound.setAccount(account);
        bidListFound.setType(type);
        bidListFound.setBidQuantity(quantity);

        bidListRepository.save(bidListFound);

        return modelMapper.map(bidListFound, BidListDTO.class);
    }
}
