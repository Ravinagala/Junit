package com.example.Junit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SomeOneController {
    @Autowired
    SomeOneService someOneService;

    @PostMapping("/add")
    public SomeOne add(@RequestBody SomeOne someOne){
        return someOneService.addSomeone(someOne);
    }

    @GetMapping("/fetch")
    public List<SomeOne> get(){
        return someOneService.getAll();
    }

    @GetMapping("/fetch/{id}")
    public SomeOne getById(@PathVariable int id){
        return someOneService.getById(id);
    }

    @PutMapping("/update/{id}")
    public SomeOne update(@PathVariable int id,@RequestBody SomeOne someOne){
        return someOneService.updateName(id,someOne);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable int id){
        someOneService.deleteById(id);
        return "DELETED";
    }
}
