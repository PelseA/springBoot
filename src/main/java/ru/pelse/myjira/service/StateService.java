package ru.pelse.myjira.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pelse.myjira.entity.State;
import ru.pelse.myjira.entity.Task;
import ru.pelse.myjira.repository.StateRepository;

@Service
public class StateService {
    @Autowired
    private StateRepository stateRepository;

    public State getStateByValue(String value) throws NoEntityException {
        return stateRepository
                .findByValue(value)
                .orElseThrow(() -> new NoEntityException(value));
    }

}
