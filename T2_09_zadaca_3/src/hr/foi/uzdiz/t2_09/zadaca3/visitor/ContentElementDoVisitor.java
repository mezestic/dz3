/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3.visitor;

import hr.foi.uzdiz.t2_09.zadaca3.mvc.View;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vedra
 */
public class ContentElementDoVisitor implements ContentVisitor {

    double ukupnaVelicina = 0;

    @Override
    public void visit(SizeConverter sizeConverter) {

        View view = sizeConverter.getView();
        view.cleanPrimaryScreen();
        view.printLnToPrimary("SizeConverter:");

        ukupnaVelicina = (double) sizeConverter.getUkupnaVelicina();
        double converted = 0;
        DecimalFormat f = new DecimalFormat("####.00");

        if (ukupnaVelicina > 1048576 && ukupnaVelicina < 1073741824) {
            converted = ukupnaVelicina / 1048576;
            view.printLnToPrimary("Ukupna konvertirana velicina: " + String.valueOf(f.format(converted)) + " MB");
        } else if (ukupnaVelicina > 1073741824) {
            converted = ukupnaVelicina / 1073741824;
            view.printLnToPrimary("Ukupna konvertirana velicina: " + String.valueOf(f.format(converted)) + " GB");
        } else {
            converted = ukupnaVelicina / 1024;
            view.printLnToPrimary("Ukupna konvertirana velicina: " + String.valueOf(f.format(converted)) + " KB");
        }

    }

    @Override
    public void visit(FileSaver fileSaver) {

        View view = fileSaver.getView();

       // File file = new File("D:\\sizeOutput.txt");
        File file = new File("sizeOutput.txt");
        double converted = 0;
        DecimalFormat f = new DecimalFormat("####.00");

        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(file));
            if (ukupnaVelicina > 1048576 && ukupnaVelicina < 1073741824) {
                converted = ukupnaVelicina / 1048576;
                bw.write("Ukupna konvertirana velicina: " + String.valueOf(f.format(converted)) + " MB");
            } else if (ukupnaVelicina > 1073741824) {
                converted = ukupnaVelicina / 1073741824;
                bw.write("Ukupna konvertirana velicina: " + String.valueOf(f.format(converted)) + " GB");
            } else {
                converted = ukupnaVelicina / 1024;
                bw.write("Ukupna konvertirana velicina: " + String.valueOf(f.format(converted)) + " KB");
            }

            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(ContentElementDoVisitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        view.printLnToPrimary(" ");
        view.printLnToPrimary("FileSaver:");
        view.printLnToPrimary("Podatak spremljen u datoteku: sizeOutput.txt");

    }

    @Override
    public void visitMethods(Content content) {
        for (ContentElement contentElement : content.getElements()) {
            contentElement.accept(this);
        }
    }

}
