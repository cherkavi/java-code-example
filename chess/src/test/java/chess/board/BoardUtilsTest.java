package chess.board;

import org.junit.Assert;
import org.junit.Test;

import chess.Position;

public class BoardUtilsTest {
	
	
	@Test
	public void checkPositionX(){
		// given
		Position position=new Position("d2");
		// when
		int result=BoardUtils.getX(position);
		// then
		Assert.assertEquals(3, result);
	}
	
	@Test
	public void checkPositionY(){
		// given
		Position position=new Position("d2");
		// when
		int result=BoardUtils.getY(position);
		// then
		Assert.assertEquals(1, result);
	}
	
}
