package com.dominyuk.pgn2pdf;

import freemarker.template.*;
import java.util.*;
import java.io.*;

/**
 * Created by roman on 09.01.17.
 */
public class JsCreator {
    // array of positions in Forsyth–Edwards Notation (FEN) format
    private String[] fens;
    // array of moves in long algebraic notation (LAN) format
    private String[] lanMoves;
    private PdfConfig config;

    public JsCreator (PgnParser parser, PdfConfig config) {
        fens = parser.getFens();
        lanMoves = parser.getLanMoves();
        this.config = config;
    }


    public String create() throws IOException, TemplateException {
        /* ------------------------------------------------------------------------ */
        /* You should do this ONLY ONCE in the whole application life-cycle:        */
        /* Create and adjust the configuration singleton */
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_25);
        cfg.setClassForTemplateLoading(JsCreator.class, "/");
//        cfg.setDirectoryForTemplateLoading(new File("/mnt/mystorage/notes/focus/programming/YaroslavTraining/PgnToPdf/src/main/resources/chess_game.js.fm"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);

        /* ------------------------------------------------------------------------ */
        /* You usually do these for MULTIPLE TIMES in the application life-cycle:   */

        /* Create a data-model */
        Map root = new HashMap();


        List<String> positions = new ArrayList<>();
        for (String fen: fens) {
            positions.add(fen);
        }
        root.put("positions", positions);

        List<String> moves = new ArrayList<>();
        for (String move: lanMoves) {
            moves.add(move);
        }
        root.put("moves", moves);
        root.put("BOARD_X0", config.BOARD_X0);
        root.put("BOARD_Y0", config.BOARD_Y0);
        root.put("cellSize", config.BOARD_CELL_SIZE);

        /* Get the template (uses cache internally) */
        Template temp = cfg.getTemplate("chess_game.js");

        /* Merge data-model with template */
//        Writer out = new OutputStreamWriter(System.out);
        Writer out = new StringWriter();
        temp.process(root, out);
        return out.toString();
        // Note: Depending on what `out` is, you may need to call `out.close()`.
        // This is usually the case for file output, but not for servlet output.
    }
}
