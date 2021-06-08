package com.nnk.springboot.Service.impl;

import com.nnk.springboot.Service.BidService;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidServiceImpl implements BidService {
    private final BidListRepository bidListRepository;

    @Autowired
    public BidServiceImpl(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    public List<BidList> bidLists(){
        return bidListRepository.findAll();
    }
}
