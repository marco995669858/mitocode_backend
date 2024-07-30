package com.mitocode.service.impl;

import com.mitocode.model.ResetMail;
import com.mitocode.repo.IResetMailRepo;
import com.mitocode.service.IResetMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResetMailServiceImpl implements IResetMailService {

    private final IResetMailRepo repo;

    @Override
    public ResetMail findByRandom(String random) {
        return repo.findByRandom(random);
    }

    @Override
    public void save(ResetMail resetMail) {
        repo.save(resetMail);
    }

    @Override
    public void delete(ResetMail resetMail) {
        repo.delete(resetMail);
    }
}
