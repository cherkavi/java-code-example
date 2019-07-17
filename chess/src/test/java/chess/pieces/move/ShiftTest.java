package chess.pieces.move;

import junit.framework.Assert;

import org.junit.Test;

import chess.Position;

public class ShiftTest {
	// TODO 
	
	@Test
	public void checkShift(){
		Assert.assertNotNull(Shift.moveTo(new Position("a1"), new Shift(0,1)));
		Assert.assertNotNull(Shift.moveTo(new Position("a1"), new Shift(1,1)));
		Assert.assertNotNull(Shift.moveTo(new Position("a1"), new Shift(1,0)));
		
		Assert.assertNull(Shift.moveTo(new Position("a1"), new Shift(0,-1)));
		Assert.assertNull(Shift.moveTo(new Position("a1"), new Shift(-1,-1)));
		Assert.assertNull(Shift.moveTo(new Position("a1"), new Shift(-1,0)));
	}
}
