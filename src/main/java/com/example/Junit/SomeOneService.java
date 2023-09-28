package com.example.Junit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SomeOneService {
    @Autowired
    SomeOneRepository oneRepository;

    public SomeOne addSomeone(SomeOne someOne){
        return oneRepository.save(someOne);
    }

    public List<SomeOne> getAll(){
        return oneRepository.findAll();
    }

    public SomeOne getById(int id){
        return oneRepository.findById(id).get();
    }

    public SomeOne updateName(int id, SomeOne someOne){
        SomeOne someOne1 = oneRepository.findById(id).get();
        someOne1.setName(someOne.getName());
        return oneRepository.save(someOne1);
    }

    public void deleteById(int id){
        oneRepository.deleteById(id);
    }
}
