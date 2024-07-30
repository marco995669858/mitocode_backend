package com.mitocode.repo;

import com.mitocode.model.ResetMail;

public interface IResetMailRepo extends IGenericRepo<ResetMail, Integer>{

    //FROM ResetMail rm WHERE rm.random = ?
    ResetMail findByRandom(String random);
}
