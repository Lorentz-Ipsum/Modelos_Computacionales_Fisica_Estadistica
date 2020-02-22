import java.awt.Component;
import java.awt.Panel;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.AdjustmentEvent;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.Label;
import java.awt.Scrollbar;
import java.awt.TextField;
import java.awt.Button;
import java.awt.Image;
import java.awt.event.AdjustmentListener;
import java.awt.event.ActionListener;
import java.applet.Applet;

//
// Decompiled by Procyon v0.5.36
//

public class BEFDApplet extends Applet implements ActionListener, Runnable, AdjustmentListener
{
    Image bolaB;
    Image bolaF;
    CuadroMath bosones;
    CuadroMath fermiones;
    Button histogramaB;
    Button calc;
    Button pausa;
    Button histogramaF;
    TextField N_val;
    int N;
    int Nmax;
    int altura;
    int anchura;
    boolean corriendo;
    boolean activo;
    Thread runner;
    double kT;
    int radio;
    int velocidad;
    int Niv;
    int[] occB;
    int[] occF;
    int[] hisB;
    int[] hisF;
    double beta;
    double sumB;
    double sumF;
    double maxHistB;
    double maxHistF;
    int levelshift;
    Scrollbar temperatura;
    int PASO;
    Label temp_val;

    public BEFDApplet() {
        this.N = 10;
        this.Nmax = 20;
        this.corriendo = false;
        this.activo = true;
        this.kT = 0.5;
        this.radio = 4;
        this.velocidad = 1;
        this.Niv = 20;
        this.occB = new int[this.Niv + 1];
        this.occF = new int[this.Niv + 1];
        this.hisB = new int[this.Niv + 1];
        this.hisF = new int[this.Niv + 1];
        this.beta = 0.1;
        this.sumB = 0.0;
        this.sumF = 0.0;
        this.maxHistB = 0.0;
        this.maxHistF = 0.0;
        this.PASO = 100;
    }

    void Bose() {
        final int n = (int)Math.floor(Math.random() * this.Niv);
        for (int i = 0; i <= this.Niv; ++i) {
            if (this.occB[i] != 0) {
                int n2 = i + (int)(2.0 * Math.floor(Math.random() * 2.0) - 1.0);
                if (n2 < 0) {
                    n2 = 0;
                }
                if (n2 >= this.Niv) {
                    n2 = this.Niv - 1;
                }
                final double exp = Math.exp(-this.beta * (n2 - i));
                if (exp >= 1.0) {
                    final int[] occB = this.occB;
                    final int n3 = n2;
                    ++occB[n3];
                    final int[] occB2 = this.occB;
                    final int n4 = i;
                    --occB2[n4];
                }
                else if (Math.random() < exp) {
                    final int[] occB3 = this.occB;
                    final int n5 = n2;
                    ++occB3[n5];
                    final int[] occB4 = this.occB;
                    final int n6 = i;
                    --occB4[n6];
                }
            }
        }
    }

    void Fermi() {
        final int n = (int)Math.floor(Math.random() * this.Niv);
        for (int i = 0; i <= this.Niv; ++i) {
            if (this.occF[i] != 0) {
                int n2 = i + (int)(2.0 * Math.floor(Math.random() * 2.0) - 1.0);
                if (n2 < 0) {
                    n2 = 0;
                }
                if (n2 >= this.Niv) {
                    n2 = this.Niv - 1;
                }
                if (this.occF[n2] != 1) {
                    final double exp = Math.exp(-this.beta * (n2 - i));
                    if (exp >= 1.0) {
                        final int[] occF = this.occF;
                        final int n3 = n2;
                        ++occF[n3];
                        final int[] occF2 = this.occF;
                        final int n4 = i;
                        --occF2[n4];
                    }
                    else if (Math.random() < exp) {
                        final int[] occF3 = this.occF;
                        final int n5 = n2;
                        ++occF3[n5];
                        final int[] occF4 = this.occF;
                        final int n6 = i;
                        --occF4[n6];
                    }
                }
            }
        }
    }

    void LeeCampos() {
        try {
            this.N = Integer.valueOf(this.N_val.getText());
        }
        catch (NumberFormatException ex) {
            this.N = 50;
            this.N_val.setText("50");
        }
        if (this.N < 1 || this.N > this.Nmax) {
            this.N = 10;
        }
        this.N_val.setText(String.valueOf(this.N));
    }

    public synchronized void actionPerformed(final ActionEvent actionEvent) {
        if (actionEvent.getSource() == this.calc) {
            if (!this.corriendo) {
                this.runner.start();
                this.corriendo = true;
            }
            this.LeeCampos();
            this.inicializa();
        }
        else if (actionEvent.getSource() == this.pausa) {
            if (!this.corriendo) {
                return;
            }
            if (!(this.activo ^= true)) {
                this.pausa.setLabel("Activa");
            }
            if (this.activo) {
                this.pausa.setLabel("Pausa ");
                this.notify();
            }
        }
        else if (actionEvent.getSource() == this.histogramaB) {
            final GraficoAuxiliar graficoAuxiliar = new GraficoAuxiliar(this.anchura, this.altura, 0.0, (double)this.Niv, 0.0, Math.floor(15.0 * this.maxHistB / this.sumB * this.N) / 10.0, "j", "N(j)");
            graficoAuxiliar.cuadro.FG_BG(false);
            graficoAuxiliar.cuadro.setCol(Color.blue);
            for (int i = 0; i < this.Niv; ++i) {
                graficoAuxiliar.cuadro.Cuadrado((double)i, 0.0, (double)(i + 1), this.hisB[i] / this.sumB * this.N);
            }
            graficoAuxiliar.cuadro.FG_BG(true);
        }
        else if (actionEvent.getSource() == this.histogramaF) {
            final double n = Math.floor(20.0 * this.maxHistF / this.sumF) / 10.0 * this.N;
            final GraficoAuxiliar graficoAuxiliar2 = new GraficoAuxiliar(this.anchura, this.altura, 0.0, (double)this.Niv, 0.0, 1.5, "j", "N(j)");
            graficoAuxiliar2.cuadro.FG_BG(false);
            graficoAuxiliar2.cuadro.setCol(Color.lightGray);
            graficoAuxiliar2.cuadro.Recta((double)this.N, 0.0, (double)this.N, 2.0);
            graficoAuxiliar2.cuadro.setCol(Color.blue);
            for (int j = 0; j < this.Niv; ++j) {
                graficoAuxiliar2.cuadro.Cuadrado((double)j, 0.0, (double)(j + 1), this.hisF[j] / this.sumF * this.N);
            }
            graficoAuxiliar2.cuadro.FG_BG(true);
        }
    }

    public void adjustmentValueChanged(final AdjustmentEvent adjustmentEvent) {
        this.beta = 20.0 / this.temperatura.getValue();
        this.temp_val.setText("T=" + this.temperatura.getValue() / 10.0);
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
            }
            else {
                this.occF[i] = 0;
                this.occB[i] = 0;
            }
            this.hisF[i] = 0;
            this.hisB[i] = 0;
        }
    }

    public void init() {
        this.setFont(new Font("SansSerif", 0, 12));
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
        (this.temperatura = new Scrollbar(0, this.PASO, 1, 1, this.PASO + 1)).addAdjustmentListener(this);
        this.setLayout(new BorderLayout());
        final Panel comp = new Panel();
        final Panel comp2 = new Panel();
        final Panel comp3 = new Panel();
        final Panel comp4 = new Panel();
        comp3.add(new Label("N\u00famero de part\u00edculas (<=" + this.Nmax + "):"));
        comp3.add(this.N_val);
        comp4.add(this.temperatura);
        comp4.add(this.temp_val);
        comp.add(comp3);
        comp.add(comp4);
        comp2.add(this.histogramaB);
        comp2.add(this.calc);
        comp2.add(this.pausa);
        comp2.add(this.histogramaF);
        this.add("North", comp);
        final Panel comp5 = new Panel();
        comp5.add((Component)this.bosones);
        comp5.add((Component)this.fermiones);
        this.add("Center", comp5);
        this.add("South", comp2);
        this.bolaB = this.getImage(this.getDocumentBase(), "bolaazul.gif");
        this.bolaF = this.getImage(this.getDocumentBase(), "bolanaranja.gif");
        this.runner = new Thread(this, "Tarea1");
    }

    void montecarlo() {
        this.Bose();
        this.Fermi();
        this.bosones.FG_BG(false);
        this.bosones.Limpia();
        this.bosones.setCol(Color.black);
        this.fermiones.FG_BG(false);
        this.fermiones.Limpia();
        this.fermiones.setCol(Color.black);
        for (int i = 0; i < this.Niv; ++i) {
            final int[] hisB = this.hisB;
            final int n = i;
            hisB[n] += this.occB[i];
            this.sumB += this.occB[i];
            final int[] hisF = this.hisF;
            final int n2 = i;
            hisF[n2] += this.occF[i];
            this.sumF += this.occF[i];
            if (this.hisB[i] > this.maxHistB) {
                this.maxHistB = this.hisB[i];
            }
            if (this.hisF[i] > this.maxHistF) {
                this.maxHistF = this.hisF[i];
            }
            final int n3 = this.levelshift * i + 20;
            this.bosones.Recta(70.0, (double)n3, (double)(this.anchura - 70), (double)n3);
            this.fermiones.Recta(80.0, (double)n3, (double)(this.anchura - 80), (double)n3);
            if (this.occB[i] > 0) {
                int n4 = (this.anchura - 140) / (this.occB[i] + 1);
                if (n4 > 2 * this.radio + 2) {
                    n4 = 2 * this.radio + 2;
                }
                for (int j = 1; j <= this.occB[i]; ++j) {
                    this.bosones.Imagen(this.bolaB, (double)(this.anchura / 2 + n4 * (j - 1) - this.radio - this.occB[i] * n4 / 2), (double)(n3 + 2 * this.radio));
                }
            }
            if (this.occF[i] != 0) {
                this.fermiones.Imagen(this.bolaF, this.anchura / 2 - 1.5 * this.radio, (double)(n3 + 2 * this.radio));
            }
        }
        this.bosones.FG_BG(true);
        this.fermiones.FG_BG(true);
    }

    public void run() {
        while (this.runner == Thread.currentThread()) {
            try {
                Thread.sleep(this.velocidad);
                synchronized (this) {
                    while (!this.activo) {
                        this.wait();
                    }
                }
            }
            catch (InterruptedException ex) {}
            this.montecarlo();
        }
    }
}
