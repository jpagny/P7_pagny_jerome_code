package com.nnk.springboot.service;

import com.nnk.springboot.exception.ResourceNotFoundException;
import com.nnk.springboot.repository.IBaseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractServiceCrud<Entity, DTO> implements IGenericService<DTO> {

    private final ModelMapper modelMapper;
    private final IBaseRepository<Entity> iBaseRepository;

    private final Class<Entity> entityClass = (Class<Entity>) ((ParameterizedType) getClass()
            .getGenericSuperclass()).getActualTypeArguments()[0];

    private final Class<DTO> dtoCLass = (Class<DTO>) ((ParameterizedType) getClass()
            .getGenericSuperclass()).getActualTypeArguments()[1];

    @Autowired
    public AbstractServiceCrud(IBaseRepository<Entity> iBaseRepository) {
        this.iBaseRepository = iBaseRepository;
        modelMapper = new ModelMapper();
    }

    @Override
    public void delete(Integer id) throws ResourceNotFoundException {
        Entity resource = iBaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(id)));
        iBaseRepository.delete(resource);
    }

    @Override
    public DTO findById(Integer id) throws ResourceNotFoundException {
        Entity entity = iBaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(id)));

        return modelMapper.map(entity, dtoCLass);
    }

    @Override
    public List<DTO> findAll() {
        return iBaseRepository.findAll().stream()
                .map(user -> modelMapper.map(user, dtoCLass))
                .collect(Collectors.toList());
    }


    @Override
    public DTO create(DTO dto) {
        Entity entity = modelMapper.map(dto, entityClass);
        Entity entitySaved = iBaseRepository.save(entity);

        return modelMapper.map(entitySaved, dtoCLass);

    }

    @Override
    public abstract DTO update(Integer id, DTO dto) throws ResourceNotFoundException;


}
