package com.luv2code.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;
import com.luv2code.util.SortUtils;

@Controller
@RequestMapping("customer")
public class CustomerController {
	
	//inject CustomerService
	@Autowired
	CustomerService customerService;
	
	@GetMapping("listCustomers")
	public String listCustomers(Model model) {
		
		//get customers from CustomerService
		List<Customer> listCustomers = customerService.getCustomers();
		
		//add customers to model
		model.addAttribute("customers",listCustomers);
		
		return "listCustomers";
	}
	@GetMapping("showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		//create Customer object
		 Customer customer=new Customer();
		//add Customer to model
		theModel.addAttribute("customer", customer);
		return "customer-form";
	}
	
	@PostMapping("saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer customer) {
			customerService.saveCustomer(customer);
		return "redirect:/customer/listCustomers";
	}
	@GetMapping("showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId,
			Model theModel) {
			//get customer from the database
			Customer customer= customerService.getCustomer(theId);
			//set customer as a model attribute to pre-populate the form
			theModel.addAttribute("customer",customer);
		
		return "customer-form";
	}
	@GetMapping("deleteCustomer")
	public String deleteCustomer(@RequestParam("customerId") int theId) {
			customerService.deleteCustomer(theId);
		return "redirect:/customer/listCustomers";
}
	   @GetMapping("/search")
	    public String searchCustomers(@RequestParam("theSearchName") String theSearchName,
	                                    Model theModel) {
	        // search customers from the service
	        List<Customer> theCustomers = customerService.searchCustomers(theSearchName);
	                
	        // add the customers to the model
	        theModel.addAttribute("customers", theCustomers);
	        return "listCustomers";
}
	   @GetMapping("sortedList")
	   public String sortCustomers(Model theModel,@RequestParam(required=false) String sort)
	   {
		   List<Customer> customers=null;
		   if(sort!=null)
		   {
			   int theSortField=Integer.parseInt(sort);
			   customers=customerService.getCustomers(theSortField);
		   }
		   else
		   {
			   customers=customerService.getCustomers(SortUtils.LAST_NAME);
		   }
		   theModel.addAttribute("customers",customers);
		   
		return "listCustomers";
}	
}