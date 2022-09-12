package com.nnk.springboot.service.implement;

import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.entity.RuleNameEntity;
import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.impl.RuleNameRepository;
import com.nnk.springboot.service.AbstractServiceCrud;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RuleNameService extends AbstractServiceCrud<RuleNameEntity, RuleNameDTO> {

    private final RuleNameRepository ruleNameRepository;
    private final ModelMapper modelMapper;

    public RuleNameService(RuleNameRepository theRuleNameRepository) {
        super(theRuleNameRepository);
        this.modelMapper = new ModelMapper();
        this.ruleNameRepository = theRuleNameRepository;
    }

    @Override
    public RuleNameDTO update(Integer id, RuleNameDTO ruleNameDTO) throws ResourceNotFoundException {

        RuleNameEntity ruleNameFound = ruleNameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(id)));

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

}
