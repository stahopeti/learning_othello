package teszt;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class junitTeszt {

	Asztal teszt1;
	Ujjatek teszt2;
	
	@Before
	public void setUp(){
		
		teszt1 = new Asztal();
		teszt2 = new Ujjatek();
	}
	
	@Test
	public void tesztUres(){
		
		int result =  teszt1.hanyUres();
		Assert.assertEquals(60, result);
		
	}
	
	@Test
	public void tesztSotet(){
		
		int result =  teszt1.hanySotet();
		Assert.assertEquals(2, result);
		
	}
	
	@Test
	public void tesztVilagos(){
		
		int result =  teszt1.hanyVilagos();
		Assert.assertEquals(2, result);
		
	}
	
	@Test 
	public void tesztJoLepes(){
		int result = 0;
		
		try {
			result = teszt2.sotetLep(5, 6);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Assert.assertEquals(1, result);
		
	}
	
	@Test
	public void tesztRosszLepes(){
		int result = 0;
		
		try {
			result = teszt2.sotetLep(1, 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Assert.assertEquals(0, result);
		
	}
	
}
