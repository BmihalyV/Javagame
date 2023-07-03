package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {

    static int TablaMeretSzelesseg = 600;
    static int TablaMeretMagassag = 300;
    static int Bogyo = 10;
    static int OsszesBogyo = 1800;
    static int RandomPont = 30;
    static int Delay = 140;

    static int x[] = new int[OsszesBogyo];
    static int y[] = new int[OsszesBogyo];

    static int bogyok;
    static int appleCoordx;
    static int appleCoordY;

    static boolean Iranybal = false;
    static boolean IranyJobb = true;
    static boolean IranyFel = false;
    static boolean IranyLe = false;
    static boolean jatekban = true;

    static Timer stopper;
    static Image bogyoKep;
    static Image almaKep;
    static Image almaFejKep;

    public Board() {
        
        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(TablaMeretSzelesseg, TablaMeretMagassag));
        loadImages();
        initGame();
    }

    static void loadImages() {

        ImageIcon bogyokep = new ImageIcon("src/resources/bogyo.png");
        bogyoKep = bogyokep.getImage();

        ImageIcon almakep = new ImageIcon("src/resources/alma.png");
        almaKep = almakep.getImage();

        ImageIcon fejkep = new ImageIcon("src/resources/fej.png");
        almaFejKep = fejkep.getImage();
    }

    private void initGame() {

        bogyok = 3;

        for (int z = 0; z < bogyok; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }
        
        almaPozicionalasa();

        stopper = new Timer(Delay, this);

        stopper.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Beszuras(g);
    }
    
    private void Beszuras(Graphics g) {
        
        if (jatekban) {

            g.drawImage(almaKep, appleCoordx, appleCoordY, this);

            for (int i = 0;i < bogyok; i++) {
                if (i == 0) {
                    g.drawImage(almaFejKep, x[i], y[i], this);
                } else {
                    g.drawImage(bogyoKep, x[i], y[i], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else {

            JatekVege(g);
        }        
    }

    private void JatekVege(Graphics g) {
        
        String szoveg = "Játék vége";
        Font fo = new Font("Italic", Font.ITALIC, 20);
        FontMetrics fm = getFontMetrics(fo);

        g.setColor(Color.CYAN);
        g.setFont(fo);
        g.drawString(szoveg, (TablaMeretSzelesseg - fm.stringWidth(szoveg)) / 2, TablaMeretMagassag / 2);
    }

    private void AlmaVoid() {

        if ((x[0] == appleCoordx) && (y[0] == appleCoordY)) {

            bogyok++;
            almaPozicionalasa();
        }
    }

    static void Mozgas() {

        for (int i = bogyok; i > 0; i--) {
            x[i] = x[(i - 1)];
            y[i] = y[(i - 1)];
        }

        if (Iranybal) {
            x[0] -= Bogyo;
        }

        if (IranyJobb) {
            x[0] += Bogyo;
        }

        if (IranyFel) {
            y[0] -= Bogyo;
        }

        if (IranyLe) {
            y[0] += Bogyo;
        }
    }

    private void AllapotEllenorzes() {

        for (int i = bogyok; i > 0; i--) {

            if ((i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {
                jatekban = false;
            }
        }

        if (y[0] >= TablaMeretMagassag) {
            jatekban = false;
        }

        if (y[0] < 0) {
            jatekban = false;
        }

        if (x[0] >= TablaMeretSzelesseg) {
            jatekban = false;
        }

        if (x[0] < 0) {
            jatekban = false;
        }
        
        if (!jatekban) {
            stopper.stop();
        }
    }

    private void almaPozicionalasa() {

        int x = (int) (Math.random() * RandomPont);
        appleCoordx = ((x * Bogyo));

        x = (int) (Math.random() * RandomPont);
        appleCoordY = ((x * Bogyo));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (jatekban) {

            AlmaVoid();
            AllapotEllenorzes();
            Mozgas();
        }

        repaint();
    }

    static class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int gomb = e.getKeyCode();

            if ((gomb == KeyEvent.VK_UP)) {
                IranyFel = true;
                IranyLe = false;
                IranyJobb = false;
                Iranybal = false;
            }

            if ((gomb == KeyEvent.VK_DOWN)) {
                IranyLe = true;
                IranyFel = false;
                IranyJobb = false;
                Iranybal = false;
            }

            if ((gomb == KeyEvent.VK_LEFT)) {
                Iranybal = true;
                IranyJobb = false;
                IranyFel = false;
                IranyLe = false;
            }

            if ((gomb == KeyEvent.VK_RIGHT)) {
                IranyJobb = true;
                Iranybal = false;
                IranyFel = false;
                IranyLe = false;
            }
        }
    }
}
