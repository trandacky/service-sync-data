package com.dacky.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dacky.entity.Connect;
import com.dacky.repository.ConnectRepository;
import com.dacky.service.ConnectService;

@Service
public class ConnectImpl implements ConnectService{

	@Autowired
	private ConnectRepository connectRepository;
	
	@Override
	public List<Connect> findAll() {
		return connectRepository.findAll();
	}

	@Override
	public void delete(Long id) {
		connectRepository.deleteById(id);
	}

	@Override
	public Connect save(Connect connect) {
		return connectRepository.save(connect);
	}

}
