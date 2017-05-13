package com.dominyuk.pgn2pdf;

import chesspresso.game.Game;
import chesspresso.move.Move;
import chesspresso.pgn.PGNReader;
import chesspresso.pgn.PGNSyntaxError;
import chesspresso.position.Position;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static org.apache.commons.io.FileUtils.readFileToString;

/**
 * Created by roman on 04.01.17.
 */
public class PgnParser {
    private PGNReader pgnReader;
    private Game game;
    private String header;
    private String description;
    private String allMoves;
    private String[] lanMoves;
    private String[] fens;


    public PgnParser (String inputFilePath) throws IOException, PGNSyntaxError {
        if (!PGNReader.isPGNFile(inputFilePath)) {
            throw new IllegalArgumentException("Input file hasn't proper pgn syntax");
        }
        pgnReader = new PGNReader(new FileInputStream(inputFilePath), "");
    }

    public boolean parseNextGame() throws IOException, PGNSyntaxError {
        game = pgnReader.parseGame();
        if (game != null) {
            assignHeader();
            assignDescription();
            assignMovesAndFens();
        }
        return game != null;
    }

    private void assignHeader() {
        StringBuffer sb = new StringBuffer();
        sb.append(game.getWhite());
        int elo = game.getWhiteElo();
        if (elo != 0)
            sb.append(" (").append(elo).append(")");
        sb.append(" vs ").append(game.getBlack());
        elo = game.getBlackElo();
        if (elo != 0)
            sb.append(" (").append(elo).append(")");
        sb.append("  ").append(game.getResultStr()).append("  (")
                .append(game.getNumOfMoves()).append(" moves, opening ").append(game.getECO()).append(")");
        header = sb.toString();
    }

    private void assignDescription() {
        StringBuffer sb = new StringBuffer();
        sb.append(game.getEvent()).append(", ").append(game.getSite());
        sb.append(", ").append(game.getDate()).append("  (Round ");
        sb.append(game.getRound()).append(")");
        description = sb.toString();
    }

    private void assignMovesAndFens() {
        int plyNumbers = game.getTotalNumOfPlies();
        lanMoves = new String[plyNumbers];
        // default position should not be in the fens array
        fens = new String[plyNumbers];
        StringBuffer sb = new StringBuffer();
        game.gotoStart();
        for (int i = 0; i < plyNumbers; i++) {
            Move m = game.getNextMove();
            lanMoves[i] = m.getLAN();
            int moveNumber = i / 2 + 1;
            if (i % 2 == 0) {
                sb.append(moveNumber + ".").append(m).append(" ");
            } else {
                sb.append(m).append(" ");
            }
            game.goForward();
            // position should be read after last move, default should be rejected
            Position p = game.getPosition();
                fens[i] = p.toString();
        }
        allMoves = sb.toString();
    }

    public String getHeader() {
        return header;
    }

    public String getDescription() {
        return description;
    }

    public String getAllMoves() {
        return allMoves;
    }

    public String[] getLanMoves() {
        return lanMoves;
    }

    public String[] getFens() {
        return fens;
    }
}
