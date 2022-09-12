package com.nnk.springboot.service.implement;

import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.entity.TradeEntity;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.impl.TradeRepository;
import com.nnk.springboot.service.AbstractServiceCrud;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TradeService extends AbstractServiceCrud<TradeEntity, TradeDTO> {

    private final TradeRepository tradeRepository;
    private final ModelMapper modelMapper;

    public TradeService(TradeRepository theTradeRepository) {
        super(theTradeRepository);
        this.modelMapper = new ModelMapper();
        this.tradeRepository = theTradeRepository;
    }

    @Override
    public TradeDTO update(Integer id, TradeDTO tradeDTO) throws ResourceNotFoundException {

        TradeEntity tradeFound = tradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        String account = tradeDTO.getAccount() != null
                ? tradeDTO.getAccount()
                : tradeFound.getAccount();

        String type = tradeDTO.getType() != null
                ? tradeDTO.getType()
                : tradeFound.getType();

        Double buyQuantity = tradeDTO.getBuyQuantity() != null
                ? tradeDTO.getBuyQuantity()
                : tradeFound.getBuyQuantity();

        tradeFound.setAccount(account);
        tradeFound.setType(type);
        tradeFound.setBuyQuantity(buyQuantity);

        tradeRepository.save(tradeFound);

        return modelMapper.map(tradeFound, TradeDTO.class);
    }

}
