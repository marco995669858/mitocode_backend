package com.mitocode.service.impl;

import org.springframework.stereotype.Service;
import com.mitocode.model.VitalSigns;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IVitalSignsRepo;
import com.mitocode.service.IVitalSignsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VitalSignServiceImpl  extends CRUDImpl<VitalSigns, Integer> implements IVitalSignsService{

    private final IVitalSignsRepo repo;

    @Override
    protected IGenericRepo<VitalSigns, Integer> getRepo() {
        return repo;
    }
    
}
