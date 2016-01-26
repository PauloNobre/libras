package teste;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import br.usp.libras.data.SignDao;
import br.usp.libras.data.SignDaoFactory;
import br.usp.libras.dic.SQLiteDictionary;
import br.usp.libras.sign.Sign;
import junit.framework.TestCase;

public class SQLiteDictionaryTest extends TestCase {
	
	SQLiteDictionary dic = new SQLiteDictionary();
	

	@Test
	public void testNearSigns() {
		List<Sign> palavra = dic.nearSigns("Gosto");
		assertNotNull(palavra);
	}

	@Test
	public void testSignByName() {
		Sign palavra = dic.signByName("futebol");
		assertNotNull(palavra);
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
