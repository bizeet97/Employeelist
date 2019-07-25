package com.cars.app;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins="http://18.224.182.74:8000")
@SpringBootApplication
@RestController

public class DemoApplication {
   public static void main(String[] args) {
      SpringApplication.run(DemoApplication.class, args);
   }
   
   @RequestMapping(value = "/")
   public String hello() {
      return "Hello World";
   }
   
   @RequestMapping(value="/employees")
   public List<Employee> getEmployee() {
	   List<Employee> employeeList = new ArrayList<>();
	   Employee emp = new Employee("Bizeet",22);
	   employeeList.add(emp);
	   Employee emp1 = new Employee("Ashish",24);
	   employeeList.add(emp1);
	   Employee emp2 = new Employee("Nitin",22);
	   employeeList.add(emp2);
	   Employee emp3 = new Employee("Ankana",21);
	   employeeList.add(emp3);
	   
	   return employeeList;
   }
}