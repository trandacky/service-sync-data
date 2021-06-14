package com.dacky.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dacky.entity.Connect;

@Repository
public interface ConnectRepository extends JpaRepository<Connect, Long>{

}
