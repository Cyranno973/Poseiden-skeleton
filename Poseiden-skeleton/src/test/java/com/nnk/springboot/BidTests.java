package com.nnk.springboot;


import com.nnk.springboot.controllers.HomeController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BidTests {

    @Autowired
    private BidListRepository bidListRepository;
    @Autowired
    private HomeController homeController;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void bidListTest() {
        BidList bid = new BidList("Account Test", "Type Test", 10d);

        // Save
        bid = bidListRepository.save(bid);
        Assert.assertNotNull(bid.getBidListId());
        Assert.assertEquals(bid.getBidQuantity(), 10d, 10d);

        // Update
        bid.setBidQuantity(20d);
        bid = bidListRepository.save(bid);
        Assert.assertEquals(bid.getBidQuantity(), 20d, 20d);

        // Find
        List<BidList> listResult = bidListRepository.findAll();
        Assert.assertTrue(listResult.size() > 0);

        // Delete
        Integer id = bid.getBidListId();
        bidListRepository.delete(bid);
        Optional<BidList> bidList = bidListRepository.findById(id);
        Assert.assertFalse(bidList.isPresent());
    }

    @Test
    public void controllerNotNull() throws Exception {
        Assert.assertNotNull(homeController);
    }
//    @Test
//	public void defaultMessage() throws Exception{
//    	this.mockMvc.perform("/bidList/add").andDo(Mockito.any()).andExpect(status().isOk())
//				.andExpect()
//	}
}
