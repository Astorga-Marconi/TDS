package Compiladores;

import static org.junit.Assert.*;
import org.junit.Test;

public class SymbolTableTest {

	@Test
	public void testAmountLevels() {
		SymbolTable s =  new SymbolTable();
		s.insertNewBlock();
		s.insertNewBlock();
		s.insertNewBlock();
		s.insertNewBlock();
		assertEquals(s.amountLevels, 4);
	}
	
	@Test
	public void testCloseBlock() {
		SymbolTable s =  new SymbolTable();
		s.insertNewBlock();
		s.insertNewBlock();
		s.insertNewBlock();
		s.insertNewBlock();
		s.closeBlock();
		assertEquals(s.amountLevels, 3);
	}
	
	@Test
	public void testDescriptor() {
		Descriptor d = new Descriptor();
		d.setName("a");
		assertEquals(d.getName(), "a");
	}
}
