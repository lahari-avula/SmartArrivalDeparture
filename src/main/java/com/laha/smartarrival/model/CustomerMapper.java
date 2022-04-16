package com.laha.smartarrival.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CustomerMapper implements RowMapper<Customer>{
	
	@Override
	public Customer mapRow(ResultSet rs,int rownum) throws SQLException
	{
		Customer cust = new Customer();
		cust.setAcc_id(rs.getString("acc_id"));
		cust.setF_name(rs.getString("f_name"));
		cust.setL_name(rs.getString("l_name"));
		cust.setAddress(rs.getString("address"));
		cust.setLat(rs.getDouble("lat"));
		cust.setLongitude(rs.getDouble("lng"));
		return cust;
	}
	
	
}

