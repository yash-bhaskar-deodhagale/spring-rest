package com.yash.org.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.yash.org.dao.EmployeeDao;
import com.yash.org.model.Employee;
import com.yash.org.service.EmployeeService;
import com.yash.org.service.EmployeeServiceImpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

	@InjectMocks
	private EmployeeService employeeService = new EmployeeServiceImpl();

	@Mock
	private EmployeeDao employeeDao;

	@Test
	public void shouldReturnListOfAllEmployees() {
		List<Employee> employeeList = new ArrayList<Employee>();
		Employee employeeOne = new Employee(1, "John", "John", 10000);
		Employee employeeTwo = new Employee(2, "Alex", "kolenchiski", 20000);
		Employee empplyeeThree = new Employee(3, "Steve", "Waugh", 30000);

		employeeList.add(employeeOne);
		employeeList.add(employeeTwo);
		employeeList.add(empplyeeThree);

		when(employeeDao.getAllEmployees()).thenReturn(employeeList);

		List<Employee> empList = employeeService.getAllEmployees();

		assertEquals(3, empList.size());
		verify(employeeDao, times(1)).getAllEmployees();
	}

	@Test
	public void shouldReturnEmployeeWhenEmployeeIdIsGiven() {
		when(employeeDao.getEmployeeById(1)).thenReturn(new Employee(1, "Lokesh", "Gupta", 10000));

		Employee employee = employeeService.getEmployeeById(1);

		assertEquals(1, employee.getEmpId());
		assertEquals("Lokesh", employee.getFirstName());
		assertEquals("Gupta", employee.getLastName());
		assertEquals(10000, employee.getSalary());
		verify(employeeDao, times(1)).getEmployeeById(1);
	}

	@Test
	public void shouldCreateEmployee() {
		Employee employee = new Employee(1, "Lokesh", "Gupta", 2000);
		doNothing().when(employeeDao).addEmployee(employee);
		employeeService.addEmployee(employee);

		verify(employeeDao, times(1)).addEmployee(employee);
	}

	@Test
	public void shouldUpdateEmployeeGivenUpdatedEmployee() {
		Employee employee = new Employee(1, "Lokesh", "Gupta", 2000);
		doNothing().when(employeeDao).updateEmployee(employee);
		employeeService.updateEmployee(employee);

		verify(employeeDao, times(1)).updateEmployee(employee);
	}

	@Test
	public void shouldDeleteEmployeeGivenEmployeeId() {
		doNothing().when(employeeDao).deleteEmployee(1);
		employeeService.deleteEmployee(1);

		verify(employeeDao, times(1)).deleteEmployee(1);
	}

}
