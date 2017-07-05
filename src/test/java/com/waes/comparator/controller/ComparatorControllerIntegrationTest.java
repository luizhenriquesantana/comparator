package com.waes.comparator.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.waes.comparator.entity.Document;
import com.waes.comparator.repository.DocumentRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ComparatorControllerIntegrationTest {
	
	@Autowired
	private MockMvc mvc;

	@Autowired
	public DocumentRepository repository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setup() throws Exception {
		this.mvc = webAppContextSetup(webApplicationContext).build();
		this.repository.deleteAll();
	}
	

	@Test
	public void insert() throws Exception {
		left();
		Thread.sleep(3000);
		right();
	}
	
	private void left() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/v1/diff/1/left").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n" + "  \"data\": " + "  \"dGVzdGluZyB0aGUgYmFzZTY0\"" + "}")).andExpect(status().isOk()).andReturn();
		Document document = repository.findById(1L);
		Assert.assertThat(document.getId(), Matchers.is(1L));
		Assert.assertThat(document.getLeft(), Matchers.is("dGVzdGluZyB0aGUgYmFzZTY0"));
		Assert.assertThat(document.getRight(), Matchers.isEmptyOrNullString());
	}
	
	private void right() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/v1/diff/1/right").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n" + "  \"data\": " + "  \"dGVzdGluZyB0aGUgYmFzZTY0\"" + "}")).andExpect(status().isOk()).andReturn();
		Document document = repository.findById(1L);
		Assert.assertThat(document.getId(), Matchers.is(1L));
		Assert.assertThat(document.getRight(), Matchers.is("DBVsbG8gd29ybGJK="));
		Assert.assertThat(document.getLeft(), Matchers.isEmptyOrNullString());
	}
	
	@Test
	public void equal() throws Exception {
		repository.save(new Document(1l, "dGVzdGluZyB0aGUgYmFzZTY0", "dGVzdGluZyB0aGUgYmFzZTY0"));
		mvc.perform(MockMvcRequestBuilders.get("/v1/diff/1").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n" + "  \"data\": " + "  \"DBVsbG8gd29ybGJK=\"" + "}")).andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is("Base64 data are equal")))
				.andReturn();		
	}
	
	@Test
	public void different() throws Exception {
		repository.save(new Document(1l, "dGVzdGluZyB0aGUgYmFzZTY0", "dGhlIDJuZCB0ZXN0IGZvciBjb21wYXJpbmc="));
		mvc.perform(MockMvcRequestBuilders.get("/v1/diff/1").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n" + "  \"data\": " + "  \"DBVsbG8gd29ybGJK=\"" + "}")).andExpect(status().isOk())
		.andExpect(jsonPath("$.message", is("Base64 data have not same size.")))
		.andReturn();		
	}
}
