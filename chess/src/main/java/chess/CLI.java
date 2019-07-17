package chess;

import chess.board.BoardException;
import chess.pieces.Piece;

import java.io.*;
import java.text.MessageFormat;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

/**
 * This class provides the basic CLI interface to the Chess game.
 */
public class CLI {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final BufferedReader inReader;
    private final PrintStream outStream;

    private GameState gameState = null;

    public CLI(InputStream inputStream, PrintStream outStream) {
        this.inReader = new BufferedReader(new InputStreamReader(inputStream));
        this.outStream = outStream;
        writeOutput("Welcome to Chess!");
    }

    /**
     * Write the string to the output
     * @param str The string to write
     */
    private void writeOutput(String str) {
        this.outStream.println(str);
    }

    /**
     * Retrieve a string from the console, returning after the user hits the 'Return' key.
     * @return The input from the user, or an empty-length string if they did not type anything.
     */
    private String getInput() {
        try {
            this.outStream.print("> ");
            return inReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from input: ", e);
        }
    }
    
	private static final String MOVE_POS_DELIMITER = " ";

    void startEventLoop() {
        writeOutput("Type 'help' for a list of commands.");
        doNewGame();

        while (true) {
            showBoard();
            writeOutput(gameState.getCurrentPlayer() + "'s Move");

            String input = StringUtils.trim(getInput());
            if (input == null) {
                break; // No more input possible; this is the only way to exit the event loop
            } else if (input.length() > 0) {
                if (input.equals("help")) {
                    showCommands();
                } else if (input.equals("new")) {
                    doNewGame();
                } else if (input.equals("quit")) {
                    writeOutput("Goodbye!");
                    System.exit(0);
                } else if (input.equals("board")) {
                    writeOutput("Current Game:");
                } else if (input.startsWith("list")) {
                	// parse parameter
                	String dirtyPosition=StringUtils.trimToNull(StringUtils.substringAfter(input, "list"));
                	if(dirtyPosition==null){
                    	writeOutput("list command must contains start position of your figure");
                    	continue;
                	};
                	// analize positions
                	Position startPosition=new Position(dirtyPosition);
                	Piece piece=gameState.getBoard().readPiece(startPosition);
                    try{
                    	Collection<Position> accessiblePositions=piece.getAccessiblePositions(gameState.getBoard(), startPosition, gameState.getCurrentPlayer());
                    	writeOutput(MessageFormat.format("{0} accessible steps: {1}", piece.getClass().getSimpleName(), accessiblePositions));
                    }catch(BoardException be){
                    	writeOutput("can't calculate accessible steps: "+be.getMessage());
                    }
                    
                } else if (input.startsWith("move")) {
                	// parse parameter
                	String dirtyPositions=StringUtils.trimToNull(StringUtils.substringAfter(input, "move"));
                	if(dirtyPositions==null){
                    	writeOutput("move command expected two parameters - start position [space] end position ");
                    	continue;
                	};
                	String userIntputStartPosition=StringUtils.trimToNull(StringUtils.substringBefore(dirtyPositions, MOVE_POS_DELIMITER));
                	String userInputEndPosition=StringUtils.trimToNull(StringUtils.substringAfter(dirtyPositions, MOVE_POS_DELIMITER));
                	if(userIntputStartPosition==null){
                    	writeOutput("move command expected two parameters - start position [space] end position ");
                    	continue;
                	}
                	if(userInputEndPosition==null){
                    	writeOutput("move command expected two parameters - start position [space] end position ");
                    	continue;
                	}
                	Position positionStart=new Position(userIntputStartPosition);
                	Position positionEnd=new Position(userInputEndPosition);
                	
                	// analize positions
                	Piece piece=gameState.getBoard().readPiece(positionStart);
                	if(piece==null){
                		writeOutput("check position ");
                		continue;
                	}
                	if(!piece.getOwner().equals(gameState.getCurrentPlayer())){
                		writeOutput("use only your figures");
                		continue;
                	}
                	if(piece==null){
                		writeOutput("pointer field is empty !!! ");
                		continue;
                	}
                	Collection<Position> accessiblePositions=null;
                    try{
                    	accessiblePositions=piece.getAccessiblePositions(gameState.getBoard(), positionStart, gameState.getCurrentPlayer());
                    }catch(BoardException be){
                    	writeOutput("can't calculate accessible steps: "+be.getMessage());
                    	continue;
                    }
                    if(!accessiblePositions.contains(positionEnd)){
                    	writeOutput("can't move to unaccessible position, only to:  "+accessiblePositions);
                    	continue;
                    }
                    
                    // figure UP
                    try {
                    	gameState.retrievePieceAt(positionStart); // UI
					} catch (BoardException e) {
                    	writeOutput("can't get piece from board: "+e.getMessage());
						continue;
					}
                    // figure DOWN
                    try {
						gameState.placePiece(piece, positionEnd); 
					} catch (BoardException e) {
                    	writeOutput("can't put piece from board: "+e.getMessage());
						continue;
					}
					gameState.changePlayer();
                } else {
                    writeOutput("I didn't understand that.  Type 'help' for a list of commands.");
                }
            }
        }
    }

    private void doNewGame() {
        gameState = new GameState();
        try{
        	gameState.reset();
        }catch(BoardException be){
        	writeOutput("critical Error - ask developer about new game creation");
        	System.exit(1);
        }
    }

    private void showBoard() {
        writeOutput(getBoardAsString());
    }

    private void showCommands() {
        writeOutput("Possible commands: ");
        writeOutput("    'help'                       Show this menu");
        writeOutput("    'quit'                       Quit Chess");
        writeOutput("    'new'                        Create a new game");
        writeOutput("    'board'                      Show the chess board");
        writeOutput("    'list'                       List all possible moves");
        writeOutput("    'move <colrow> <colrow>'     Make a move");
    }

    /**
     * Display the board for the user(s)
     */
    String getBoardAsString() {
        StringBuilder builder = new StringBuilder();
        builder.append(NEWLINE);

        printColumnLabels(builder);
        for (int i = Position.MAX_ROW; i >= Position.MIN_ROW; i--) {
            printSeparator(builder);
            printSquares(i, builder);
        }

        printSeparator(builder);
        printColumnLabels(builder);

        return builder.toString();
    }


    private void printSquares(int rowLabel, StringBuilder builder) {
        builder.append(rowLabel);

        for (char c = Position.MIN_COLUMN; c <= Position.MAX_COLUMN; c++) {
            Piece piece = gameState.getPieceAt(String.valueOf(c) + rowLabel);
            char pieceChar = piece == null ? ' ' : piece.getIdentifier();
            builder.append(" | ").append(pieceChar);
        }
        builder.append(" | ").append(rowLabel).append(NEWLINE);
    }

    private void printSeparator(StringBuilder builder) {
        builder.append("  +---+---+---+---+---+---+---+---+").append(NEWLINE);
    }

    private void printColumnLabels(StringBuilder builder) {
        builder.append("   ");
        for (char c = Position.MIN_COLUMN; c <= Position.MAX_COLUMN; c++) {
            builder.append(" ").append(c).append("  ");
        }

        builder.append(NEWLINE);
    }

    public static void main(String[] args) {
        CLI cli = new CLI(System.in, System.out);
        cli.startEventLoop();
    }
}
