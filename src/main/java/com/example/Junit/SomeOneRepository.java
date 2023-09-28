package com.example.Junit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SomeOneRepository extends JpaRepository<SomeOne,Integer> {

}
