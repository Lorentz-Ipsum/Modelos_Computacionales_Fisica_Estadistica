/*
 * Decompiled with CFR 0.145.
 *
 * Could not load the following classes:
 *  CuadroMath
 */
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArnoldApplet
extends Applet
implements ActionListener {
    Button calc;
    Button itera;
    CuadroMath principal;
    int altura;
    int anchura;
    TextField t11_val;
    TextField t12_val;
    TextField t21_val;
    double t11 = 2.0;
    double t12 = 1.0;
    double t21 = 1.0;
    double t22;
    boolean corriendo = false;
    boolean activo = true;
    Choice condinicial;
    int velocidad = 1000;
    int n = 9000;
    double[] x = new double[this.n + 1];
    double[] y = new double[this.n + 1];
    double aux;

    void LeeCampos() {
        try {
            this.t11 = Double.valueOf(this.t11_val.getText());
            this.t12 = Double.valueOf(this.t12_val.getText());
            this.t21 = Double.valueOf(this.t21_val.getText());
        }
        catch (NumberFormatException numberFormatException) {
            this.t11 = 2.0;
            this.t12 = 1.0;
            this.t21 = 1.0;
        }
        if (this.t11 == 0.0) {
            this.t11 = 2.0;
        }
        this.t11_val.setText(String.valueOf(this.t11));
        this.t12_val.setText(String.valueOf(this.t12));
        this.t21_val.setText(String.valueOf(this.t21));
    }

    public synchronized void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == this.calc) {
            this.LeeCampos();
            this.inicializa();
        } else {
            this.iteracion();
        }
    }

    void inicializa() {
        int n;
        int n2 = this.condinicial.getSelectedIndex();
        this.principal.Limpia();
        this.t22 = (this.t12 * this.t21 + 1.0) / this.t11;
        switch (n2) {
            case 2: {
                for (n = 1; n <= this.n; ++n) {
                    this.x[n - 1] = Math.random() * 0.3;
                    this.y[n - 1] = Math.random() * 0.3;
                }
                break;
            }
            case 1: {
                for (n = 1; n <= this.n; ++n) {
                    this.x[n - 1] = Math.cos((double)n * 6.29 / (double)this.n) * 0.4 + 0.5;
                    this.y[n - 1] = Math.sin((double)n * 6.29 / (double)this.n) * 0.4 + 0.5;
                }
                break;
            }
            case 0: {
                for (n = 1; n <= 4000; ++n) {
                    this.x[n - 1] = ((double)n - 1.0) * 0.8 / 3999.0 + 0.1;
                    this.aux = this.x[n - 1] - 0.5;
                    this.y[n - 1] = Math.abs(this.aux * this.aux * this.aux) * 12.0 + 0.1;
                }
                for (n = 4001; n <= 4500; ++n) {
                    this.x[n - 1] = (double)(n - 4001) * 0.1 / 499.0 + 0.1;
                    this.x[n + 499] = (double)(n - 4001) * 0.1 / 499.0 + 0.8;
                    this.aux = this.x[n - 1] - 0.5;
                    this.y[n - 1] = this.aux * this.aux * 2.3 + 0.5;
                    this.aux = this.x[n + 499] - 0.5;
                    this.y[n + 499] = this.aux * this.aux * 2.3 + 0.5;
                }
                for (n = 5001; n <= 6000; ++n) {
                    this.x[n - 1] = (double)(n - 5001) * 0.6 / 999.0 + 0.2;
                    this.aux = this.x[n - 1] - 0.5;
                    this.y[n - 1] = -this.aux * this.aux * 1.0333333 + 0.8;
                }
                for (n = 6001; n <= 6500; ++n) {
                    this.x[n - 1] = ((double)n - 6001.0) * 0.3 / 499.0 + 0.35;
                    this.x[n + 499] = ((double)n - 6001.0) * 0.3 / 499.0 + 0.35;
                    this.aux = this.x[n - 1] - 0.5;
                    this.y[n - 1] = this.aux * this.aux * 3.0 + 0.22;
                    this.aux = this.x[n + 499] - 0.5;
                    this.y[n + 499] = this.aux * this.aux * 0.777777777777 + 0.27;
                }
                for (n = 7001; n <= 8000; ++n) {
                    this.x[n - 1] = Math.cos(((double)n - 7001.0) * 6.29 / 999.0) * 0.07 + 0.37;
                    this.y[n - 1] = Math.sin(((double)n - 7001.0) * 6.29 / 999.0) * 0.04 + 0.58;
                    this.x[n + 999] = Math.cos(((double)n - 8001.0) * 6.29 / 999.0) * 0.07 + 0.63;
                    this.y[n + 999] = Math.sin(((double)n - 8001.0) * 6.29 / 999.0) * 0.04 + 0.58;
                }
            }
        }
        this.principal.FG_BG(false);
        for (n = 0; n <= this.n; ++n) {
            this.principal.Recta(this.x[n], this.y[n], this.x[n], this.y[n]);
        }
        this.principal.FG_BG(true);
    }

    public void init() {
        Font font = new Font("SansSerif", 0, 12);
        this.setFont(font);
        this.setBackground(Color.lightGray);
        this.anchura = this.altura = this.getSize().height - 140;
        this.principal = new CuadroMath(0.0, 1.0, 0.0, 1.0, this.anchura, this.altura, "", "", Color.white, false, false);
        this.condinicial = new Choice();
        this.condinicial.add("Gato");
        this.condinicial.add("Circulo");
        this.condinicial.add("Nube de puntos");
        this.t11_val = new TextField(String.valueOf(this.t11) + " ");
        this.t12_val = new TextField(String.valueOf(this.t12) + " ");
        this.t21_val = new TextField(String.valueOf(this.t21) + " ");
        this.calc = new Button("Inicia");
        this.itera = new Button("Itera");
        this.calc.addActionListener(this);
        this.itera.addActionListener(this);
        this.setLayout(new BorderLayout());
        Panel panel = new Panel();
        Panel panel2 = new Panel();
        Panel panel3 = new Panel();
        panel.setLayout(new BorderLayout());
        panel3.add(new Label("t11="));
        panel3.add(this.t11_val);
        panel3.add(new Label("t12="));
        panel3.add(this.t12_val);
        panel3.add(new Label("t21="));
        panel3.add(this.t21_val);
        panel2.add(this.condinicial);
        panel.add("North", panel2);
        panel.add("South", panel3);
        Panel panel4 = new Panel();
        panel4.add((Component)this.principal);
        Panel panel5 = new Panel();
        panel5.add(this.calc);
        panel5.add(this.itera);
        this.add("North", panel);
        this.add("Center", panel4);
        this.add("South", panel5);
    }

    void iteracion() {
        this.principal.FG_BG(false);
        this.principal.Limpia();
        for (int i = 0; i <= this.n; ++i) {
            double d = this.t11 * this.x[i] + this.t12 * this.y[i];
            double d2 = this.t21 * this.x[i] + this.t22 * this.y[i];
            this.x[i] = d;
            this.y[i] = d2;
            this.principal.Recta(d - Math.floor(d), d2 - Math.floor(d2), d - Math.floor(d), d2 - Math.floor(d2));
        }
        this.principal.FG_BG(true);
    }
}
