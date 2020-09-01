/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  CuadroMath
 *  GraficoAuxiliar
 */
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.Scrollbar;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.net.URL;

public class BEFDApplet
extends Applet
implements ActionListener,
Runnable,
AdjustmentListener {
    Image bolaB;
    Image bolaF;
    CuadroMath bosones;
    CuadroMath fermiones;
    Button histogramaB;
    Button calc;
    Button pausa;
    Button histogramaF;
    TextField N_val;
    int N = 10;
    int Nmax = 20;
    int altura;
    int anchura;
    boolean corriendo = false;
    boolean activo = true;
    Thread runner;
    double kT = 0.5;
    int radio = 4;
    int velocidad = 1;
    int Niv = 20;
    int[] occB = new int[this.Niv + 1];
    int[] occF = new int[this.Niv + 1];
    int[] hisB = new int[this.Niv + 1];
    int[] hisF = new int[this.Niv + 1];
    double beta = 0.1;
    double sumB = 0.0;
    double sumF = 0.0;
    double maxHistB = 0.0;
    double maxHistF = 0.0;
    int levelshift;
    Scrollbar temperatura;
    int PASO = 100;
    Label temp_val;

    void Bose() {
        int n = (int)Math.floor(Math.random() * (double)this.Niv);
        for (n = 0; n <= this.Niv; ++n) {
            if (this.occB[n] == 0) continue;
            int n2 = n + (int)(2.0 * Math.floor(Math.random() * 2.0) - 1.0);
            if (n2 < 0) {
                n2 = 0;
            }
            if (n2 >= this.Niv) {
                n2 = this.Niv - 1;
            }
            double d = -this.beta * (double)(n2 - n);
            if ((d = Math.exp(d)) >= 1.0) {
                int[] arrn = this.occB;
                int n3 = n2;
                arrn[n3] = arrn[n3] + 1;
                int[] arrn2 = this.occB;
                int n4 = n;
                arrn2[n4] = arrn2[n4] - 1;
                continue;
            }
            double d2 = Math.random();
            if (!(d2 < d)) continue;
            int[] arrn = this.occB;
            int n5 = n2;
            arrn[n5] = arrn[n5] + 1;
            int[] arrn3 = this.occB;
            int n6 = n;
            arrn3[n6] = arrn3[n6] - 1;
        }
    }

    void Fermi() {
        int n = (int)Math.floor(Math.random() * (double)this.Niv);
        for (n = 0; n <= this.Niv; ++n) {
            if (this.occF[n] == 0) continue;
            int n2 = n + (int)(2.0 * Math.floor(Math.random() * 2.0) - 1.0);
            if (n2 < 0) {
                n2 = 0;
            }
            if (n2 >= this.Niv) {
                n2 = this.Niv - 1;
            }
            if (this.occF[n2] == 1) continue;
            double d = -this.beta * (double)(n2 - n);
            if ((d = Math.exp(d)) >= 1.0) {
                int[] arrn = this.occF;
                int n3 = n2;
                arrn[n3] = arrn[n3] + 1;
                int[] arrn2 = this.occF;
                int n4 = n;
                arrn2[n4] = arrn2[n4] - 1;
                continue;
            }
            double d2 = Math.random();
            if (!(d2 < d)) continue;
            int[] arrn = this.occF;
            int n5 = n2;
            arrn[n5] = arrn[n5] + 1;
            int[] arrn3 = this.occF;
            int n6 = n;
            arrn3[n6] = arrn3[n6] - 1;
        }
    }

    void LeeCampos() {
        try {
            this.N = Integer.valueOf(this.N_val.getText());
        }
        catch (NumberFormatException numberFormatException) {
            this.N = 50;
            this.N_val.setText("50");
        }
        if (this.N < 1 || this.N > this.Nmax) {
            this.N = 10;
        }
        this.N_val.setText(String.valueOf(this.N));
    }

    public synchronized void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == this.calc) {
            if (!this.corriendo) {
                this.runner.start();
                this.corriendo = true;
            }
            this.LeeCampos();
            this.inicializa();
        } else if (actionEvent.getSource() == this.pausa) {
            if (!this.corriendo) {
                return;
            }
            this.activo ^= true;
            if (!this.activo) {
                this.pausa.setLabel("Activa");
            }
            if (this.activo) {
                this.pausa.setLabel("Pausa ");
                this.notify();
            }
        } else if (actionEvent.getSource() == this.histogramaB) {
            double d = Math.floor(15.0 * this.maxHistB / this.sumB * (double)this.N) / 10.0;
            GraficoAuxiliar graficoAuxiliar = new GraficoAuxiliar(this.anchura, this.altura, 0.0, (double)this.Niv, 0.0, d, "j", "N(j)");
            graficoAuxiliar.cuadro.FG_BG(false);
            graficoAuxiliar.cuadro.setCol(Color.blue);
            for (int i = 0; i < this.Niv; ++i) {
                graficoAuxiliar.cuadro.Cuadrado((double)i, 0.0, (double)(i + 1), (double)this.hisB[i] / this.sumB * (double)this.N);
            }
            graficoAuxiliar.cuadro.FG_BG(true);
        } else if (actionEvent.getSource() == this.histogramaF) {
            double d = Math.floor(20.0 * this.maxHistF / this.sumF) / 10.0 * (double)this.N;
            GraficoAuxiliar graficoAuxiliar = new GraficoAuxiliar(this.anchura, this.altura, 0.0, (double)this.Niv, 0.0, 1.5, "j", "N(j)");
            graficoAuxiliar.cuadro.FG_BG(false);
            graficoAuxiliar.cuadro.setCol(Color.lightGray);
            graficoAuxiliar.cuadro.Recta((double)this.N, 0.0, (double)this.N, 2.0);
            graficoAuxiliar.cuadro.setCol(Color.blue);
            for (int i = 0; i < this.Niv; ++i) {
                graficoAuxiliar.cuadro.Cuadrado((double)i, 0.0, (double)(i + 1), (double)this.hisF[i] / this.sumF * (double)this.N);
            }
            graficoAuxiliar.cuadro.FG_BG(true);
        }
    }

    public void adjustmentValueChanged(AdjustmentEvent adjustmentEvent) {
        int n = this.temperatura.getValue();
        this.beta = 20.0 / (double)n;
        this.temp_val.setText("T=" + (double)this.temperatura.getValue() / 10.0);
    }

    void inicializa() {
        this.maxHistB = 0.0;
        this.maxHistF = 0.0;
        this.sumB = 0.0;
        this.sumF = 0.0;
        for (int i = 0; i < this.Niv; ++i) {
            if (i < this.N) {
                this.occF[i] = 1;
                this.occB[i] = 1;
            } else {
                this.occF[i] = 0;
                this.occB[i] = 0;
            }
            this.hisF[i] = 0;
            this.hisB[i] = 0;
        }
    }

    public void init() {
        Font font = new Font("SansSerif", 0, 12);
        this.setFont(font);
        this.setBackground(Color.white);
        this.anchura = (this.getSize().width - 20) / 2;
        this.altura = this.getSize().height - 80;
        this.levelshift = (this.altura - 20) / this.Niv;
        this.bosones = new CuadroMath(0.0, (double)this.anchura, 0.0, (double)this.altura, this.anchura, this.altura, "", "", Color.white, false, false);
        this.fermiones = new CuadroMath(0.0, (double)this.anchura, 0.0, (double)this.altura, this.anchura, this.altura, "", "", Color.white, false, false);
        this.N_val = new TextField(String.valueOf(this.N), 2);
        this.temp_val = new Label("T=10  ");
        this.histogramaB = new Button(" Histograma Bosones");
        this.calc = new Button(" Inicia ");
        this.pausa = new Button(" Pausa ");
        this.histogramaF = new Button(" Histograma Fermiones");
        this.histogramaB.addActionListener(this);
        this.calc.addActionListener(this);
        this.pausa.addActionListener(this);
        this.histogramaF.addActionListener(this);
        this.temperatura = new Scrollbar(0, this.PASO, 1, 1, this.PASO + 1);
        this.temperatura.addAdjustmentListener(this);
        this.setLayout(new BorderLayout());
        Panel panel = new Panel();
        Panel panel2 = new Panel();
        Panel panel3 = new Panel();
        Panel panel4 = new Panel();
        panel3.add(new Label("N\u00famero de part\u00edculas (<=" + this.Nmax + "):"));
        panel3.add(this.N_val);
        panel4.add(this.temperatura);
        panel4.add(this.temp_val);
        panel.add(panel3);
        panel.add(panel4);
        panel2.add(this.histogramaB);
        panel2.add(this.calc);
        panel2.add(this.pausa);
        panel2.add(this.histogramaF);
        this.add("North", panel);
        Panel panel5 = new Panel();
        panel5.add((Component)this.bosones);
        panel5.add((Component)this.fermiones);
        this.add("Center", panel5);
        this.add("South", panel2);
        this.bolaB = this.getImage(this.getDocumentBase(), "bolaazul.gif");
        this.bolaF = this.getImage(this.getDocumentBase(), "bolanaranja.gif");
        this.runner = new Thread((Runnable)this, "Tarea1");
    }

    void montecarlo() {
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        boolean bl = false;
        this.Bose();
        this.Fermi();
        this.bosones.FG_BG(false);
        this.bosones.Limpia();
        this.bosones.setCol(Color.black);
        this.fermiones.FG_BG(false);
        this.fermiones.Limpia();
        this.fermiones.setCol(Color.black);
        for (int i = 0; i < this.Niv; ++i) {
            int[] arrn = this.hisB;
            int n4 = i;
            arrn[n4] = arrn[n4] + this.occB[i];
            this.sumB += (double)this.occB[i];
            int[] arrn2 = this.hisF;
            int n5 = i;
            arrn2[n5] = arrn2[n5] + this.occF[i];
            this.sumF += (double)this.occF[i];
            if ((double)this.hisB[i] > this.maxHistB) {
                this.maxHistB = this.hisB[i];
            }
            if ((double)this.hisF[i] > this.maxHistF) {
                this.maxHistF = this.hisF[i];
            }
            n2 = this.levelshift * i + 20;
            this.bosones.Recta(70.0, (double)n2, (double)(this.anchura - 70), (double)n2);
            this.fermiones.Recta(80.0, (double)n2, (double)(this.anchura - 80), (double)n2);
            if (this.occB[i] > 0) {
                n3 = (this.anchura - 140) / (this.occB[i] + 1);
                if (n3 > 2 * this.radio + 2) {
                    n3 = 2 * this.radio + 2;
                }
                for (int j = 1; j <= this.occB[i]; ++j) {
                    n = this.anchura / 2 + n3 * (j - 1) - this.radio - this.occB[i] * n3 / 2;
                    this.bosones.Imagen(this.bolaB, (double)n, (double)(n2 + 2 * this.radio));
                }
            }
            if (this.occF[i] == 0) continue;
            this.fermiones.Imagen(this.bolaF, (double)(this.anchura / 2) - 1.5 * (double)this.radio, (double)(n2 + 2 * this.radio));
        }
        this.bosones.FG_BG(true);
        this.fermiones.FG_BG(true);
    }

    public void run() {
        Thread thread = Thread.currentThread();
        while (this.runner == thread) {
            try {
                Thread.sleep(this.velocidad);
                BEFDApplet bEFDApplet = this;
                synchronized (bEFDApplet) {
                    while (!this.activo) {
                        this.wait();
                    }
                }
            }
            catch (InterruptedException interruptedException) {}
            this.montecarlo();
        }
    }
}
