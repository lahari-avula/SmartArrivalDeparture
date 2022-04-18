package com.laha.smartarrival.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CustomerMapper implements RowMapper<Customer>{

	@Override
	public Customer mapRow(ResultSet rs,int rownum) throws SQLException
	{
		Customer cust = new Customer();
		cust.setAccountId(rs.getString("acc_id"));
		cust.setFirstName(rs.getString("f_name"));
		cust.setLastName(rs.getString("l_name"));
		cust.setAddress(rs.getString("address"));
		cust.setLatitude(rs.getDouble("lat"));
		cust.setLongitude(rs.getDouble("lng"));
		return cust;
	}


}


