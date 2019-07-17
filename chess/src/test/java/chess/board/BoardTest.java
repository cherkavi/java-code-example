package chess.board;

import java.util.Random;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import chess.Player;
import chess.Position;
import chess.pieces.King;
import chess.pieces.Piece;

public class BoardTest {
	Board boardEmpty;
	Piece figure;
	Position figurePosition;
	
	@Before
	public void prepareValues(){
		boardEmpty=new Board();
		figure=new King(Player.Black);
		figurePosition=new Position("g1");
	}
	
	@Test
	public void checkEmpty() throws BoardException{
		for(int index=0;index<100;index++){
			// given
			Position testPosition=new Position((char)(new Random().nextInt(Position.MAX_COLUMN-Position.MIN_COLUMN)+Position.MIN_COLUMN), new Random().nextInt(Position.MAX_ROW-Position.MIN_ROW)+Position.MIN_ROW);
			// when
			// then
			Assert.assertTrue(this.boardEmpty.isEmpty(testPosition));
			Assert.assertFalse(this.boardEmpty.isNotEmpty(testPosition));
		}
		
	}
	
	
	@Test
	public void putToBoard() throws BoardException{
		// given
		// when
		boardEmpty.putPiece(figure, figurePosition);
		// then
		Assert.assertFalse(boardEmpty.isEmpty(figurePosition));
		Assert.assertTrue(boardEmpty.isNotEmpty(figurePosition));
	}
	
	@Test
	public void checkFigureOnBoard() throws BoardException{
		// given
		putToBoard();
		
		// when
		Piece pieceOnBoard=boardEmpty.readPiece(figurePosition);
		
		// then
		Assert.assertNotNull(pieceOnBoard);
		Assert.assertEquals(figure, pieceOnBoard);
	}

	
	@Test
	public void getFromBoard() throws BoardException{
		// given
		putToBoard(); 
		
		// when
		Piece pieceFromBoard=boardEmpty.getPiece(figure, figurePosition);
		
		// then
		Assert.assertNotNull(pieceFromBoard);
		Assert.assertEquals(figure, pieceFromBoard);
	}
	
	@Test
	public void getPositionOfPiece() throws BoardException{
		// given
		this.boardEmpty.putPiece(this.figure, this.figurePosition);
		
		// when
		Position positionFromBoard=this.boardEmpty.getPosition(this.figure);
		
		// then
		Assert.assertNotNull(positionFromBoard);
		Assert.assertEquals(this.figurePosition, positionFromBoard);
	}

	
}
