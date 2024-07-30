package com.mitocode.service;

import com.mitocode.model.ResetMail;

public interface IResetMailService {

    ResetMail findByRandom(String random);
    void save(ResetMail resetMail);
    void delete(ResetMail resetMail);
}
