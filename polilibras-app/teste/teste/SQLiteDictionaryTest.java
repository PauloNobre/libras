package teste;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;


import br.usp.libras.data.SignDao;
import br.usp.libras.data.SignDaoFactory;
import br.usp.libras.dic.SQLiteDictionary;
import br.usp.libras.sign.Sign;
import junit.framework.TestCase;

public class SQLiteDictionaryTest extends TestCase {
	
	List<Sign> retorno;
	Sign sign;
	
	@Mock
	SQLiteDictionary dic = new SQLiteDictionary();
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		sign = new Sign();
		sign.setName("futebol");
		retorno.add(sign);
	}
	
	@Test
	public void testNearSigns() {
		
		when(dic.nearSigns("Gosto")).thenReturn(retorno);
		
		List<Sign> palavra = dic.nearSigns("Gosto");
		assertNotNull(palavra);
	}

	@Test
	public void testSignByName() {
		when(dic.signByName("futebol")).thenReturn(sign);
		Sign palavra = dic.signByName("futebol");
		assertEquals("futebol", palavra.getName());
	}

	@Test
	public void testSimpleTranslate() {
		Sign palavra = dic.simpleTranslate("futebol");
		assertNotNull(palavra);
	}

	@Test
	public void testTranslate() {
		List<Sign> palavra = dic.translate("futebol");
		assertNotNull(palavra);
	}
}
