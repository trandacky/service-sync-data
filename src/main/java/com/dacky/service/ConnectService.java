package com.dacky.service;

import java.util.List;

import com.dacky.entity.Connect;

public interface ConnectService {

	List<Connect> findAll();

	void delete(Long id);

	Connect save(Connect connect);

}
