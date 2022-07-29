package com.nnk.springboot.service.implement;

import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.entity.TradeEntity;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.TradeRepository;
import com.nnk.springboot.service.ITradeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TradeService implements ITradeService {

    private final TradeRepository tradeRepository;
    private final ModelMapper modelMapper;

    @Override
    public TradeDTO findById(Integer id) throws ResourceNotFoundException {
        TradeEntity tradeEntity = tradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trade doesn't exist with id : " + id));

        return modelMapper.map(tradeEntity, TradeDTO.class);
    }

    @Override
    public List<TradeEntity> findAll() {
        return tradeRepository.findAll();
    }

    @Override
    public TradeDTO create(TradeDTO tradeDTO) {
        TradeEntity tradeEntity = modelMapper.map(tradeDTO, TradeEntity.class);
        TradeEntity tradeEntitySaved = tradeRepository.save(tradeEntity);

        return modelMapper.map(tradeEntitySaved, TradeDTO.class);
    }

    @Override
    public TradeDTO update(Integer id, TradeDTO tradeDTO) throws ResourceNotFoundException {

        TradeEntity tradeFound = tradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trade doesn't exist : " + id));

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

    @Override
    public void delete(Integer id) throws ResourceNotFoundException {
        TradeEntity tradeFound = tradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trade doesn't exist : " + id));

        tradeRepository.delete(tradeFound);
    }

}
