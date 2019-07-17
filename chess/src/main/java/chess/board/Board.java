package chess.board;

import java.text.MessageFormat;

import chess.Position;
import chess.pieces.Piece;

/**
 * abstraction of real board
 */
public class Board {
	/** real space of board - X - letters, Y - numbers */
	Piece[][] space=new Piece[Position.MAX_COLUMN-Position.MIN_COLUMN+1][Position.MAX_ROW-Position.MIN_ROW+1];
	
	public Board(){
	}
	
	/**
	 * put one Piece to Board
	 * @param piece
	 * @param p
	 */
	public void putPiece(Piece piece, Position p) throws BoardException{
		if(piece==null){
			throw new BoardException("can't put NO Piece");
		}
		if(p==null){
			throw new BoardException("can't put to NO Position");
		}
		
		DekartPosition position=DekartPosition.convertFrom(p);
		
		if(this.space[position.x][position.y]!=null){
			throw new BoardException(MessageFormat.format("current position {0} contains another figure {1} ",p, this.space[position.x][position.y]));
		}
		
		this.space[position.x][position.y]=piece;
	}
	
	
	/**
	 * get one piece from board ( will be moved/was killed )
	 * @param piece
	 * @param position
	 * @return
	 */
	public Piece getPiece(Piece piece, Position p) throws BoardException{
		checkEmptyPiece(piece, "can't get NO figure");
		checkEmptyPosition(p, "can't get piece from NO position");
		
		DekartPosition position=DekartPosition.convertFrom(p);
		
		if(piece.equals(this.space[position.x][position.y])){
			this.space[position.x][position.y]=null;
			return piece;
		}else{
			throw new BoardException(MessageFormat.format(" position {0} contains figure {1} instead of {2} check your algorithm", position, this.space[position.x][position.y], piece));
		}
	}

	/**
	 * check board place for empty 
	 * @param p
	 * @return
	 * <ul>
	 * 	<li><b>true</b> - position is empty </li>
	 * 	<li><b>false</b> - position contains some figure </li>
	 * </ul>
	 * @throws BoardException
	 */
	public boolean isEmpty(Position p) throws BoardException{
		return this.readPiece(p)==null;
	}
	
	/**
	 * check board place for busy
	 * @param p
	 * @return
	 * <ul>
	 * 	<li><b>true</b> - position is empty </li>
	 * 	<li><b>false</b> - position contains some figure </li>
	 * </ul>
	 * @throws BoardException
	 */
	public boolean isNotEmpty(Position p) throws BoardException{
		return !isEmpty(p);
	}
	
	
	/**
	 * return figure from current position
	 * @param p
	 * @return
	 * @throws BoardException
	 */
	public Piece readPiece(Position p){
		if(p==null){
			return null;
		}
		DekartPosition position=DekartPosition.convertFrom(p);
		return this.space[position.x][position.y];
	}
	
	private void checkEmptyPiece(Piece piece, String message) throws BoardException{
		if(piece==null){
			throw new BoardException(message);
		}
	}

	private void checkEmptyPosition(Position position, String message) throws BoardException{
		if(position==null){
			throw new BoardException(message);
		}
	}

	public Position getPosition(Piece figure) {
		for(int xPosition=0;xPosition<this.space.length; xPosition++){
			for(int yPosition=0;yPosition<this.space[0].length; yPosition++){
				if(figure.equals(this.space[xPosition][yPosition])){
					return DekartPosition.convertTo(new DekartPosition(xPosition, yPosition));
				}
			}
		}
		return null;
	}

}


