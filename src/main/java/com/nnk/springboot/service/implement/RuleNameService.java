package com.nnk.springboot.service.implement;

import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.entity.RuleNameEntity;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.RuleNameRepository;
import com.nnk.springboot.service.IRuleNameService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RuleNameService implements IRuleNameService {

    private final RuleNameRepository ruleNameRepository;
    private final ModelMapper modelMapper;

    @Override
    public RuleNameDTO findById(Integer id) throws ResourceNotFoundException {
        RuleNameEntity ruleNameEntity = ruleNameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rule name doesn't exist with id : " + id));

        return modelMapper.map(ruleNameEntity, RuleNameDTO.class);
    }

    @Override
    public List<RuleNameEntity> findAll() {
        return ruleNameRepository.findAll();
    }

    @Override
    public RuleNameDTO create(RuleNameDTO ruleNameDTO) {
        RuleNameEntity ruleNameEntity = modelMapper.map(ruleNameDTO, RuleNameEntity.class);
        RuleNameEntity ruleNameEntitySaved = ruleNameRepository.save(ruleNameEntity);

        return modelMapper.map(ruleNameEntitySaved, RuleNameDTO.class);
    }

    @Override
    public RuleNameDTO update(Integer id, RuleNameDTO ruleNameDTO) throws ResourceNotFoundException {

        RuleNameEntity ruleNameFound = ruleNameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rule name doesn't exist with id : " + id));

        String name = ruleNameDTO.getName() != null
                ? ruleNameDTO.getName()
                : ruleNameFound.getName();

        String description = ruleNameDTO.getDescription() != null
                ? ruleNameDTO.getDescription()
                : ruleNameFound.getDescription();

        String json = ruleNameDTO.getJson() != null
                ? ruleNameDTO.getJson()
                : ruleNameFound.getJson();

        String template = ruleNameDTO.getTemplate() != null
                ? ruleNameDTO.getTemplate()
                : ruleNameFound.getTemplate();

        String sqlStr = ruleNameDTO.getSqlStr() != null
                ? ruleNameDTO.getSqlStr()
                : ruleNameFound.getSqlStr();

        String sqlPart = ruleNameDTO.getSqlPart() != null
                ? ruleNameDTO.getSqlPart()
                : ruleNameFound.getSqlPart();

        ruleNameFound.setName(name);
        ruleNameFound.setDescription(description);
        ruleNameFound.setJson(json);
        ruleNameFound.setTemplate(template);
        ruleNameFound.setSqlStr(sqlStr);
        ruleNameFound.setSqlPart(sqlPart);

        ruleNameRepository.save(ruleNameFound);

        return modelMapper.map(ruleNameFound, RuleNameDTO.class);
    }

    @Override
    public void delete(Integer id) throws ResourceNotFoundException {
        RuleNameEntity ruleNameFound = ruleNameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rule name doesn't exist with id : " + id));

        ruleNameRepository.delete(ruleNameFound);
    }
}
