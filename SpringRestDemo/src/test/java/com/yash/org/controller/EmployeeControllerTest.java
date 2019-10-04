package com.yash.org.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yash.org.controller.EmployeeController;
import com.yash.org.exception.EmployeeExceptionHandler;
import com.yash.org.model.Employee;
import com.yash.org.service.EmployeeService;

@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private EmployeeController employeeController;
	@Mock
	private EmployeeService employeeService;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(employeeController)
				.setControllerAdvice(new EmployeeExceptionHandler()).build();
	}

	@Test
	public void shouldGetAllEmployeeInJSONFormat() throws Exception {
		Employee employeeOne = new Employee(1, "John", "John", 10000);
		Employee employeeTwo = new Employee(3, "Steve", "Waugh", 30000);
		List<Employee> employeesList = Arrays.asList(employeeOne, employeeTwo);

		when(employeeService.getAllEmployees()).thenReturn(employeesList);
		mockMvc.perform(get("/employees").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].empId", is(1)))
				.andExpect(jsonPath("$[0].firstName", is("John"))).andExpect(jsonPath("$[0].lastName", is("John")))
				.andExpect(jsonPath("$[0].salary", is(10000))).andExpect(jsonPath("$[1].empId", is(3)))
				.andExpect(jsonPath("$[1].firstName", is("Steve"))).andExpect(jsonPath("$[1].lastName", is("Waugh")))
				.andExpect(jsonPath("$[1].salary", is(30000)));

		verify(employeeService, times(1)).getAllEmployees();

	}

	@Test
	public void shouldGetAllEmployeeInXMLFormat() throws Exception {
		Employee employeeOne = new Employee(1, "John", "John", 10000);
		Employee employeeTwo = new Employee(3, "Steve", "Waugh", 30000);
		List<Employee> employeesList = Arrays.asList(employeeOne, employeeTwo);

		when(employeeService.getAllEmployees()).thenReturn(employeesList);
		mockMvc.perform(get("/employees").accept(MediaType.APPLICATION_XML)).andExpect(status().isOk())
				.andExpect(xpath("List/item").nodeCount(2)).andExpect(xpath("List/item[1]/empId").string(is("1")))
				.andExpect(xpath("List/item[1]/firstName").string(is("John")))
				.andExpect(xpath("List/item[1]/lastName").string(is("John")))
				.andExpect(xpath("List/item[1]/salary").string(is("10000")))
				.andExpect(xpath("List/item[2]/empId").string(is("3")))
				.andExpect(xpath("List/item[2]/firstName").string(is("Steve")))
				.andExpect(xpath("List/item[2]/lastName").string(is("Waugh")))
				.andExpect(xpath("List/item[2]/salary").string(is("30000")));

		verify(employeeService, times(1)).getAllEmployees();

	}

	@Test
	public void shouldGetEmployeeGivenEmployeeIdInJSONFormat() throws Exception {
		Employee employee = new Employee(3, "Steve", "Waugh", 30000);

		when(employeeService.getEmployeeById(3)).thenReturn(employee);
		mockMvc.perform(get("/employees/3").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.empId", is(3))).andExpect(jsonPath("$.firstName", is("Steve")))
				.andExpect(jsonPath("$.lastName", is("Waugh"))).andExpect(jsonPath("$.salary", is(30000)));

		verify(employeeService, times(1)).getEmployeeById(3);

	}

	@Test
	public void shouldGetEmployeeGivenEmployeeIdInXMlFormat() throws Exception {
		Employee employee = new Employee(3, "Steve", "Waugh", 10000);
		when(employeeService.getEmployeeById(3)).thenReturn(employee);
		mockMvc.perform(get("/employees/3").accept(MediaType.APPLICATION_XML)).andExpect(status().isOk())
				.andExpect(xpath("Employee/empId").string(is("3")))
				.andExpect(xpath("Employee/firstName").string(is("Steve")))
				.andExpect(xpath("Employee/lastName").string(is("Waugh")))
				.andExpect(xpath("Employee/salary").string(is("10000")));

		verify(employeeService, times(1)).getEmployeeById(3);

	}

	@Test
	public void shouldThrowEmployeeNotFoundExceptionGivenInvalidEmployeeId() throws Exception {
		when(employeeService.getEmployeeById(3)).thenReturn(null);
		mockMvc.perform(get("/employees/3").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status", is(404)))
				.andExpect(jsonPath("$.message", is("Employee id not found -3")));
		verify(employeeService, times(1)).getEmployeeById(3);

	}

	@Test
	public void shouldAddEmployeeGivenEmployeeInJSONFormat() throws Exception {
		Employee employee = new Employee();
		employee.setFirstName("Steve");
		employee.setLastName("Waugh");
		employee.setSalary(10000);
		ObjectMapper objectMapper = new ObjectMapper();
		String employeeString = objectMapper.writeValueAsString(employee);
		doNothing().when(employeeService).addEmployee(employee);
		mockMvc.perform(post("/employees").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(employeeString)).andExpect(status().isOk()).andExpect(jsonPath("$.firstName", is("Steve")))
				.andExpect(jsonPath("$.lastName", is("Waugh"))).andExpect(jsonPath("$.salary", is(10000)));
		verify(employeeService, times(1)).addEmployee(employee);

	}

	@Test
	public void shouldAddEmployeeGivenEmployeeInXMlFormat() throws Exception {
		Employee employee = new Employee();
		employee.setFirstName("Steve");
		employee.setLastName("Waugh");
		employee.setSalary(10000);
		ObjectMapper objectMapper = new ObjectMapper();
		String employeeString = objectMapper.writeValueAsString(employee);
		doNothing().when(employeeService).addEmployee(employee);
		mockMvc.perform(post("/employees").accept(MediaType.APPLICATION_XML).contentType(MediaType.APPLICATION_JSON)
				.content(employeeString)).andExpect(status().isOk())
				.andExpect(xpath("Employee/firstName").string(is("Steve")))
				.andExpect(xpath("Employee/lastName").string(is("Waugh")))
				.andExpect(xpath("Employee/salary").string(is("10000")));

		verify(employeeService, times(1)).addEmployee(employee);

	}

	@Test
	public void shouldUpdateEmployeeGivenEmployeeAndReturnUpdatedEmployeeDataInJSONFormat() throws Exception {
		Employee employee = new Employee(3, "Steve", "Waugh", 10000);
		when(employeeService.getEmployeeById(3)).thenReturn(employee);
		doNothing().when(employeeService).updateEmployee(employee);
		ObjectMapper objectMapper = new ObjectMapper();
		String employeeString = objectMapper.writeValueAsString(employee);
		this.mockMvc
				.perform(put("/employees").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
						.content(employeeString))
				.andExpect(status().isOk()).andExpect(jsonPath("$.empId", is(3)))
				.andExpect(jsonPath("$.firstName", is("Steve"))).andExpect(jsonPath("$.lastName", is("Waugh")))
				.andExpect(jsonPath("$.salary", is(10000)));
		verify(employeeService, times(1)).updateEmployee(employee);
	}

	@Test
	public void shouldUpdateEmployeeGivenEmployeeAndReturnUpdatedEmployeeDataInXMlFormat() throws Exception {
		Employee employee = new Employee(4, "John", "John", 10000);
		when(employeeService.getEmployeeById(4)).thenReturn(employee);
		doNothing().when(employeeService).updateEmployee(employee);
		ObjectMapper objectMapper = new ObjectMapper();
		String employeeString = objectMapper.writeValueAsString(employee);
		this.mockMvc
				.perform(put("/employees").accept(MediaType.APPLICATION_XML).contentType(MediaType.APPLICATION_JSON)
						.content(employeeString))
				.andExpect(status().isOk()).andExpect(xpath("Employee/empId").string(is("4")))
				.andExpect(xpath("Employee/firstName").string(is("John")))
				.andExpect(xpath("Employee/lastName").string(is("John")))
				.andExpect(xpath("Employee/salary").string(is("10000")));

		verify(employeeService, times(1)).updateEmployee(employee);
	}

	@Test
	public void shouldThrowEmployeeNotFoundExceptionForUpdateGivenInvalidEmployeeToUpdate() throws Exception {
		Employee employee = new Employee(4, "John", "John", 10000);
		when(employeeService.getEmployeeById(4)).thenReturn(null);
		doNothing().when(employeeService).updateEmployee(employee);
		ObjectMapper objectMapper = new ObjectMapper();
		String employeeString = objectMapper.writeValueAsString(employee);
		mockMvc.perform(put("/employees").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(employeeString)).andExpect(status().is(404)).andExpect(jsonPath("$.status", is(404)))
				.andExpect(jsonPath("$.message", is("Employee not found to Update")));

	}

	@Test
	public void shouldDeleteEmployeeGivenEmployeeIdAndReturnNoContent() throws Exception {
		Employee employee = new Employee(3, "Steve", "Waugh", 10000);
		when(employeeService.getEmployeeById(3)).thenReturn(employee);
		doNothing().when(employeeService).deleteEmployee(3);
		this.mockMvc.perform(delete("/employees/3").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
		verify(employeeService, times(1)).deleteEmployee(3);
	}

	@Test
	public void shouldThrowEmployeeNotFoundExceptionForDeleteGivenInvalidEmployeeId() throws Exception {
		when(employeeService.getEmployeeById(3)).thenReturn(null);
		mockMvc.perform(delete("/employees/3").accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isNotFound()).andExpect(jsonPath("$.status", is(404)))
				.andExpect(jsonPath("$.message", is("Employee id not found -3")));

		verify(employeeService, times(1)).getEmployeeById(3);

	}

	@Test
	public void shouldGiveBadRequestGivenBadInput() throws Exception {
		mockMvc.perform(delete("/employees/yyuy").accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.status", is(400)));

	}

}
