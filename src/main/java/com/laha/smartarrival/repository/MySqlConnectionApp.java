package com.laha.smartarrival.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.laha.smartarrival.model.Customer;
import com.laha.smartarrival.model.CustomerMapper;

@Component
public class MySqlConnectionApp implements CommandLineRunner {
	
    @Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		int result = jdbcTemplate.queryForObject(
				"select count(*) FROM smartarrival.customer_info", Integer.class);
        if (result > 0) {
            System.out.println("ROWS PRESENT");
        }
	}
	
	public Customer getCustomerInfo(String accountId){
		
		String query = "select * FROM smartarrival.customer_info where acc_id=?";
		Customer customer =null;
		try {
		customer= jdbcTemplate.queryForObject(
		  query, new Object[] { accountId }, new CustomerMapper());
		}catch(Exception e) {
		     System.out.println(" DB exception:" + e.getMessage());
		}
		return customer;
	}

}
