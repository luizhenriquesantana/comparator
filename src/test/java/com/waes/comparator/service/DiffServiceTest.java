package com.waes.comparator.service;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.waes.comparator.entity.Document;
import com.waes.comparator.enums.Side;
import com.waes.comparator.repository.DocumentRepository;
import com.waes.comparator.service.DiffService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DiffServiceTest {

	@InjectMocks
	private DiffService service;

	@Mock
	public DocumentRepository repository;
	
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void notFound() throws Exception {
		Mockito.doReturn(null).when(repository).findById(Mockito.eq(1L));
		Mockito.doAnswer(returnsFirstArg()).when(repository).save(Mockito.any(Document.class));
		Document left = service.save(1L, "Left", Side.LEFT);
		Assert.assertThat(left.getId(), Matchers.is(1L));
		Assert.assertThat(left.getLeft(), Matchers.is("Left"));
		Assert.assertThat(left.getRight(), Matchers.isEmptyOrNullString());
	}
	
	@Test
	public void leftFound() throws Exception {
		Document document = new Document(1L, null, "Right");
		Mockito.doReturn(document).when(repository).findById(Mockito.eq(1L));
		Mockito.doAnswer(returnsFirstArg()).when(repository).save(Mockito.any(Document.class));
		Document left = service.save(1L, "Left", Side.LEFT);
		Assert.assertThat(left.getId(), Matchers.is(1L));
		Assert.assertThat(left.getLeft(), Matchers.is("Left"));
		Assert.assertThat(left.getRight(), Matchers.is("Right"));
	}
	
	@Test
	public void rightFound() throws Exception {
		Document document = new Document(1L, "Left", null);
		Mockito.doReturn(document).when(repository).findById(Mockito.eq(1L));
		Mockito.doAnswer(returnsFirstArg()).when(repository).save(Mockito.any(Document.class));
		Document left = service.save(1L, "Right", Side.RIGHT);
		Assert.assertThat(left.getId(), Matchers.is(1L));
		Assert.assertThat(left.getLeft(), Matchers.is("Left"));
		Assert.assertThat(left.getRight(), Matchers.is("Right"));
	}
	
	@Test
	public void rightNotFound() throws Exception {
		Mockito.doReturn(null).when(repository).findById(Mockito.eq(1L));
		Mockito.doAnswer(returnsFirstArg()).when(repository).save(Mockito.any(Document.class));
		Document left = service.save(1L, "Right", Side.RIGHT);
		Assert.assertThat(left.getId(), Matchers.is(1L));
		Assert.assertThat(left.getRight(), Matchers.is("Right"));
		Assert.assertThat(left.getLeft(), Matchers.isEmptyOrNullString());
	}
	
	@Test
	public void diffNoDataFound() throws Exception {
		Mockito.doReturn(null).when(repository).findById(Mockito.eq(1L));
		String result = service.validateBase64Data(1L);
		Assert.assertThat(result, Matchers.is("No data found"));
	}
	
	@Test
	public void diffMissingRight() throws Exception {
		Document document = new Document(1L, "Left", null);
		Mockito.doReturn(document).when(repository).findById(Mockito.eq(1L));
		String result = service.validateBase64Data(1L);
		Assert.assertThat(result, Matchers.is("Base64 data missing"));
	}

	@Test
	public void iffMissingLeft() throws Exception {
		Document document = new Document(1L, null, "Right");
		Mockito.doReturn(document).when(repository).findById(Mockito.eq(1L));
		String result = service.validateBase64Data(1L);
		Assert.assertThat(result, Matchers.is("Base64 data missing"));
	}
	
	@Test
	public void diffEqual() throws Exception {
		Document document = new Document(1L, "DBVsbG8gd29ybGJK=", "DBVsbG8gd29ybGJK=");
		Mockito.doReturn(document).when(repository).findById(Mockito.eq(1L));
		String result = service.validateBase64Data(1L);
		Assert.assertThat(result, Matchers.is("Base64 data are equal"));
	}
	
	@Test
	public void diffDifferentSize() throws Exception {
		Document document = new Document(1L, "DBVsbG8gd29ybG=", "DBVsbG8gd29ybGJK=");
		Mockito.doReturn(document).when(repository).findById(Mockito.eq(1L));
		String result = service.validateBase64Data(1L);
		Assert.assertThat(result, Matchers.is("Base64 data have not same size."));
	}
	
	@Test
	public void diffDifferentOffset() throws Exception {
		Document document = new Document(1L, "ABVsbG8gd29ybGJK=", "DBVsbG8gd29ybGJK=");
		Mockito.doReturn(document).when(repository).findById(Mockito.eq(1L));
		String result = service.validateBase64Data(1L);
		Assert.assertThat(result, Matchers.is("Base64 data got the same size, but their offsets are different: 0"));
	}
}
