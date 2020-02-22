/*
 * Decompiled with CFR 0.145.
 *
 * Could not load the following classes:
 *  CuadroMath
 */
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
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

public class DebyeApplet
extends Applet
implements ActionListener {
    double Tdebye = 200.0;
    double Told;
    CuadroMath densidad;
    TextField T_val;
    Button calc;
    int altura;
    int anchura;
    Color[] paleta = new Color[]{Color.black, Color.red, Color.blue, Color.green, Color.yellow};
    int coloractual = 0;

    void LeeCampos() {
        try {
            this.Tdebye = Double.valueOf(this.T_val.getText());
        }
        catch (NumberFormatException numberFormatException) {
            this.Tdebye = 200.0;
        }
        if (this.Tdebye < 0.0) {
            this.Tdebye = 200.0;
        }
        this.T_val.setText(String.valueOf(this.Tdebye));
    }

    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == this.calc) {
            this.LeeCampos();
            this.densidad.fijaEscalas(0.0, 3.0 * this.Tdebye, 0.0, 3.0);
            this.densidad.Limpia();
            this.calcula();
        }
    }

    void calcula() {
        double d;
        double d2 = 0.0;
        for (double d3 = d = this.Tdebye / 100.0; d3 <= 3.0 * this.Tdebye; d3 += d) {
            double d4 = this.integ(d3, this.Tdebye);
            this.densidad.Recta(d3 - d, d2, d3, d4);
            d2 = d4;
        }
    }

    public void init() {
        Font font = new Font("SansSerif", 0, 12);
        this.setFont(font);
        this.setBackground(Color.gray);
        this.anchura = this.altura = this.getSize().width - 20;
        this.densidad = new CuadroMath(0.0, 3.0 * this.Tdebye, 0.0, 3.0, this.anchura, this.altura, "T", "d");
        this.T_val = new TextField("200.0 ");
        this.calc = new Button("Calcula");
        this.calc.addActionListener(this);
        this.setLayout(new BorderLayout());
        Panel panel = new Panel();
        Panel panel2 = new Panel();
        Panel panel3 = new Panel();
        Panel panel4 = new Panel();
        Panel panel5 = new Panel();
        panel.add(new Label("Temperatura de Debye:"));
        panel.add(this.T_val);
        panel2.add((Component)this.densidad);
        panel3.add(this.calc);
        this.add("North", panel);
        this.add("Center", panel2);
        this.add("South", panel3);
    }

    double integ(double d, double d2) {
        double[] arrd = new double[6];
        arrd[1] = 0.1488743389;
        arrd[2] = 0.4333953941;
        arrd[3] = 0.6794095682;
        arrd[4] = 0.8650633666;
        arrd[5] = 0.97390652;
        double[] arrd2 = arrd;
        double[] arrd3 = new double[6];
        arrd3[1] = 0.2955242247;
        arrd3[2] = 0.2692667193;
        arrd3[3] = 0.2190863625;
        arrd3[4] = 0.1494513491;
        arrd3[5] = 0.06667134;
        double[] arrd4 = arrd3;
        double d3 = 0.5 * d2 / d;
        double d4 = 0.5 * d2 / d;
        double d5 = 0.0;
        for (int i = 1; i <= 5; ++i) {
            double d6 = d4 * arrd2[i];
            double d7 = d3 + d6;
            d5 += arrd4[i] * d7 * d7 * d7 * d7 * Math.exp(d7) / (Math.exp(d7) - 1.0) / (Math.exp(d7) - 1.0);
            d7 = d3 - d6;
            d5 += arrd4[i] * d7 * d7 * d7 * d7 * Math.exp(d7) / (Math.exp(d7) - 1.0) / (Math.exp(d7) - 1.0);
        }
        return d5 * 9.0 * (d / d2) * (d / d2) * (d / d2) * d4;
    }
}
