package com.dominyuk.pgn2pdf;

import chesspresso.pgn.PGNSimpleErrorHandler;
import chesspresso.pgn.PGNSyntaxError;
import com.itextpdf.text.DocumentException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Created by roman on 12.01.17.
 */
public class Main {

       /**
     * Main method.
     * @param    args    no arguments needed
     * @throws DocumentException
     * @throws IOException
     */
    public static void main(String[] args) {
        PdfConfig config = new PdfConfig();
        try {
            PgnParser parser = new PgnParser(config.inputFilePath);
            int fileNameAppendix = 1;
            while (parser.parseNextGame()) {
                String resultingFilePath = config.resultingDirectoryPath
                        + config.resultingFileName + fileNameAppendix++ + ".pdf";
                JsCreator jc = new JsCreator(parser, config);
                String jsContent = jc.create();
                //todo: delete before shipping
                try(  PrintWriter jsOut = new PrintWriter( "/mnt/mystorage/notes/focus/programming/YaroslavTraining/PgnToPdf/etc/debug.js" )  ) {
                    jsOut.println(jsContent);
                }
                PdfCreator creator = new PdfCreator(config, parser, jsContent);
                try {
                    creator.initializeFont();
                } catch (Exception e) {
                    //todo: offer to reenter correct font file
                    throw new RuntimeException(e);
                }
                creator.createPdf(resultingFilePath);
            }
        } catch (Exception e) {
            // todo: call UI method with the message "Enter correct PGN file"
            // and possibility to chose new file
            throw new RuntimeException(e);
        }
    }
}