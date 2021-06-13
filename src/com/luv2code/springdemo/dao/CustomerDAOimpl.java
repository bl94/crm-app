package com.luv2code.springdemo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.util.SortUtils;

@Repository
public class CustomerDAOimpl implements CustomerDAO {


	//inject the sessionFactory
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Customer> getCustomers() {
		
		//get the current hibernate  session
		Session session = sessionFactory.getCurrentSession();
		//create query
		Query<Customer> query= session.createQuery("from Customer order by lastName",Customer.class);
		//execute a query and get resultList
		List<Customer> listCustomers=query.getResultList();
		//return the results
		
		return listCustomers;
	}

	@Override
	public void saveCustomer(Customer customer) {
		//get the current hibernate  session
		Session session = sessionFactory.getCurrentSession();
		
		//save customer
		session.saveOrUpdate(customer);
	}

	@Override
	public Customer getCustomer(int theId) {
				//get the current hibernate  session
				Session session = sessionFactory.getCurrentSession();
				//customer Update
				Customer customer=session.get(Customer.class,theId);
				
				return customer;
	}

	@Override
	public void deleteCustomer(int theId) {
		//get the current hibernate  session
		Session session = sessionFactory.getCurrentSession();
		//customer Update
		Customer customer=session.get(Customer.class,theId);
		session.delete(customer);

		/*SPOSÓB CHADA// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// delete object with primary key
		Query theQuery = 
				currentSession.createQuery("delete from Customer where id=:customerId");
		theQuery.setParameter("customerId", theId);
		
		theQuery.executeUpdate();*/ 
	}

	@Override
	public List<Customer> searchCustomers(String theSearchName) {
		        // get the current hibernate session
	        Session currentSession = sessionFactory.getCurrentSession();
	        
	        Query theQuery = null;
	        
	        //
	        // only search by name if theSearchName is not empty
	        //
	        if (theSearchName != null && theSearchName.trim().length() > 0) {
	            // search for firstName or lastName ... case insensitive
	            theQuery =currentSession.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName", Customer.class);
	            theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
	        }
	        else {
	            // theSearchName is empty ... so just get all customers
	            theQuery =currentSession.createQuery("from Customer", Customer.class);            
	        }
	        
	        // execute query and get result list
	        List<Customer> customers = theQuery.getResultList();
	                
	        // return the results        
	        return customers;
	        
	}

	@Override
	public List<Customer> getCustomers(int theSortField) {
		//get hibernate session
		Session session= sessionFactory.getCurrentSession();
		//sort field
		String sortingMethod=null;
		
		//switch case, appropriate choice from avalaible options
		switch(theSortField) {
		case SortUtils.FIRST_NAME:
			sortingMethod="firstName";
			break;
		case SortUtils.LAST_NAME:
			sortingMethod="lastName";
			break;
		case SortUtils.EMAIL:
			sortingMethod="email";
			break;
			default:
				sortingMethod="lastName";
			}
		//create query and sort table
		String statement="from Customer order by "+sortingMethod;
		Query<Customer> theQuery =session.createQuery(statement, Customer.class);      
		
		List<Customer>customers=theQuery.getResultList();
		//get sorted list of customers
		return customers;
	}

}
