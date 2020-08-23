import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalorEspecificoApplet extends Applet implements ActionListener {
   CuadroMath cv;
   int Nx = 5;
   int Ny = 5;
   int aux;
   int inicial = 0;
   TextField Nx_val;
   TextField Ny_val;
   Button calc;
   Button limpia;
   int altura;
   int anchura;
   Color[] paleta;
   int coloractual;
   int[][] indice;
   double[][] matriz;
   double[] unaD;
   double[] dosD;
   double leyendax;
   double leyenday;

   public CalorEspecificoApplet() {
      this.paleta = new Color[]{Color.red, Color.blue, Color.pink, Color.green, Color.yellow, Color.cyan, Color.magenta, Color.orange, Color.black};
      this.coloractual = 0;
      this.indice = new int[26][26];
      this.matriz = new double[46][102];
      this.unaD = new double[102];
      this.dosD = new double[102];
      this.leyendax = 4.0D;
      this.leyenday = 1.9D;
   }

   int LeeCampos() {
      int var1 = this.Nx;
      int var2 = this.Ny;

      try {
         this.Nx = Integer.valueOf(this.Nx_val.getText());
         this.Ny = Integer.valueOf(this.Ny_val.getText());
      } catch (NumberFormatException var3) {
         return -1;
      }

      if (this.Nx > 0 && this.Ny > 0 && this.Nx * this.Ny <= 25 && (this.Nx != 1 || this.Ny != 1)) {
         if (this.Ny < this.Nx) {
            this.aux = this.Ny;
            this.Ny = this.Nx;
            this.Nx = this.aux;
         }

         this.Nx_val.setText(String.valueOf(this.Nx));
         this.Ny_val.setText(String.valueOf(this.Ny));
         if (var1 == this.Nx && var2 == this.Ny && this.inicial != 0) {
            return -1;
         } else {
            this.inicial = 1;
            return 0;
         }
      } else {
         return -1;
      }
   }

   public void actionPerformed(ActionEvent var1) {
      if (var1.getSource() == this.calc) {
         if (this.LeeCampos() == 0) {
            this.calcula();
         }
      } else if (var1.getSource() == this.limpia) {
         this.cv.Limpia();
         this.exacto();
         this.leyenday = 1.9D;
         this.inicial = 0;
         this.coloractual = 0;
      }

   }

   void calcula() {
      int var1 = this.indice[this.Nx][this.Ny];
      this.cv.FG_BG(false);
      this.cv.setCol(this.paleta[this.coloractual]);

      for(int var2 = 1; var2 < 101; ++var2) {
         this.cv.Recta((double)(var2 - 1) * 0.05D, this.matriz[var1][var2 - 1], (double)var2 * 0.05D, this.matriz[var1][var2]);
      }

      this.cv.Recta(this.leyendax - 0.5D, this.leyenday + 0.025D, this.leyendax, this.leyenday + 0.025D);
      this.cv.Texto(this.leyendax, this.leyenday, " " + this.Nx + "x" + this.Ny, this.paleta[this.coloractual]);
      this.leyenday -= 0.1D;
      if (++this.coloractual > 8) {
         this.coloractual = 0;
      }

      this.cv.FG_BG(true);
   }

   void exacto() {
      this.cv.FG_BG(false);
      this.cv.setCol(Color.lightGray);

      for(int var1 = 1; var1 < 101; ++var1) {
         this.cv.Recta((double)(var1 - 2) * 0.05D, this.unaD[var1 - 1], (double)(var1 - 1) * 0.05D, this.unaD[var1]);
         this.cv.Recta((double)(var1 - 1) * 0.05D, this.dosD[var1 - 1], (double)var1 * 0.05D, this.dosD[var1]);
      }

      this.cv.Recta(4.95D, this.unaD[100], 5.0D, this.unaD[101]);
      this.cv.Recta(2.27D, 0.0D, 2.27D, 2.0D);
      this.cv.Texto(2.5D, 1.9D, "2D", Color.lightGray);
      this.cv.Texto(0.7D, 0.5D, "1D", Color.lightGray);
      this.cv.FG_BG(true);
   }

   void generaexactas() {
      this.unaD[0] = 0.0D;
      this.unaD[1] = 0.0D;
      this.unaD[2] = 0.0D;
      this.unaD[3] = 0.0D;
      this.unaD[4] = 2.8E-4D;
      this.unaD[5] = 0.00454D;
      this.unaD[6] = 0.02146D;
      this.unaD[7] = 0.05642D;
      this.unaD[8] = 0.107D;
      this.unaD[9] = 0.1662D;
      this.unaD[10] = 0.22662D;
      this.unaD[11] = 0.2826D;
      this.unaD[12] = 0.33074D;
      this.unaD[13] = 0.36954D;
      this.unaD[14] = 0.39884D;
      this.unaD[15] = 0.41929D;
      this.unaD[16] = 0.43199D;
      this.unaD[17] = 0.43815D;
      this.unaD[18] = 0.43899D;
      this.unaD[19] = 0.43562D;
      this.unaD[20] = 0.42901D;
      this.unaD[21] = 0.41997D;
      this.unaD[22] = 0.40919D;
      this.unaD[23] = 0.39719D;
      this.unaD[24] = 0.38441D;
      this.unaD[25] = 0.37119D;
      this.unaD[26] = 0.3578D;
      this.unaD[27] = 0.34442D;
      this.unaD[28] = 0.3312D;
      this.unaD[29] = 0.31826D;
      this.unaD[30] = 0.30568D;
      this.unaD[31] = 0.2935D;
      this.unaD[32] = 0.28176D;
      this.unaD[33] = 0.27048D;
      this.unaD[34] = 0.25967D;
      this.unaD[35] = 0.24933D;
      this.unaD[36] = 0.23945D;
      this.unaD[37] = 0.23003D;
      this.unaD[38] = 0.22105D;
      this.unaD[39] = 0.2125D;
      this.unaD[40] = 0.20436D;
      this.unaD[41] = 0.19661D;
      this.unaD[42] = 0.18924D;
      this.unaD[43] = 0.18222D;
      this.unaD[44] = 0.17554D;
      this.unaD[45] = 0.16918D;
      this.unaD[46] = 0.16313D;
      this.unaD[47] = 0.15737D;
      this.unaD[48] = 0.15188D;
      this.unaD[49] = 0.14664D;
      this.unaD[50] = 0.14166D;
      this.unaD[51] = 0.1369D;
      this.unaD[52] = 0.13237D;
      this.unaD[53] = 0.12804D;
      this.unaD[54] = 0.1239D;
      this.unaD[55] = 0.11995D;
      this.unaD[56] = 0.11618D;
      this.unaD[57] = 0.11257D;
      this.unaD[58] = 0.10912D;
      this.unaD[59] = 0.10582D;
      this.unaD[60] = 0.10265D;
      this.unaD[61] = 0.09963D;
      this.unaD[62] = 0.09672D;
      this.unaD[63] = 0.09394D;
      this.unaD[64] = 0.09127D;
      this.unaD[65] = 0.08871D;
      this.unaD[66] = 0.08625D;
      this.unaD[67] = 0.08389D;
      this.unaD[68] = 0.08162D;
      this.unaD[69] = 0.07943D;
      this.unaD[70] = 0.07733D;
      this.unaD[71] = 0.07532D;
      this.unaD[72] = 0.07337D;
      this.unaD[73] = 0.0715D;
      this.unaD[74] = 0.0697D;
      this.unaD[75] = 0.06796D;
      this.unaD[76] = 0.06628D;
      this.unaD[77] = 0.06467D;
      this.unaD[78] = 0.06311D;
      this.unaD[79] = 0.06161D;
      this.unaD[80] = 0.06015D;
      this.unaD[81] = 0.05875D;
      this.unaD[82] = 0.0574D;
      this.unaD[83] = 0.05609D;
      this.unaD[84] = 0.05482D;
      this.unaD[85] = 0.05359D;
      this.unaD[86] = 0.05241D;
      this.unaD[87] = 0.05126D;
      this.unaD[88] = 0.05015D;
      this.unaD[89] = 0.04907D;
      this.unaD[90] = 0.04803D;
      this.unaD[91] = 0.04702D;
      this.unaD[92] = 0.04604D;
      this.unaD[93] = 0.04509D;
      this.unaD[94] = 0.04417D;
      this.unaD[95] = 0.04328D;
      this.unaD[96] = 0.04241D;
      this.unaD[97] = 0.04157D;
      this.unaD[98] = 0.04075D;
      this.unaD[99] = 0.03996D;
      this.unaD[100] = 0.03919D;
      this.unaD[101] = 0.03844D;
      this.dosD[1] = 0.0D;
      this.dosD[1] = 0.0D;
      this.dosD[2] = 0.0D;
      this.dosD[3] = 0.0D;
      this.dosD[4] = 0.0D;
      this.dosD[5] = 0.0D;
      this.dosD[6] = 0.0D;
      this.dosD[7] = 0.0D;
      this.dosD[8] = 0.0D;
      this.dosD[9] = 0.0D;
      this.dosD[10] = 3.0E-5D;
      this.dosD[11] = 1.0E-4D;
      this.dosD[12] = 2.9E-4D;
      this.dosD[13] = 6.9E-4D;
      this.dosD[14] = 0.00144D;
      this.dosD[15] = 0.00271D;
      this.dosD[16] = 0.00468D;
      this.dosD[17] = 0.00755D;
      this.dosD[18] = 0.0115D;
      this.dosD[19] = 0.01672D;
      this.dosD[20] = 0.02338D;
      this.dosD[21] = 0.03162D;
      this.dosD[22] = 0.04159D;
      this.dosD[23] = 0.0534D;
      this.dosD[24] = 0.06717D;
      this.dosD[25] = 0.083D;
      this.dosD[26] = 0.10101D;
      this.dosD[27] = 0.1213D;
      this.dosD[28] = 0.14401D;
      this.dosD[29] = 0.16928D;
      this.dosD[30] = 0.19727D;
      this.dosD[31] = 0.22821D;
      this.dosD[32] = 0.26235D;
      this.dosD[33] = 0.30003D;
      this.dosD[34] = 0.34167D;
      this.dosD[35] = 0.38782D;
      this.dosD[36] = 0.43922D;
      this.dosD[37] = 0.49687D;
      this.dosD[38] = 0.56216D;
      this.dosD[39] = 0.63714D;
      this.dosD[40] = 0.72487D;
      this.dosD[41] = 0.8304D;
      this.dosD[42] = 0.96273D;
      this.dosD[43] = 1.14056D;
      this.dosD[44] = 1.41428D;
      this.dosD[45] = 2.0D;
      this.dosD[46] = 2.0D;
      this.dosD[47] = 1.35265D;
      this.dosD[48] = 1.12303D;
      this.dosD[49] = 0.9724D;
      this.dosD[50] = 0.8617D;
      this.dosD[51] = 0.77517D;
      this.dosD[52] = 0.70486D;
      this.dosD[53] = 0.64617D;
      this.dosD[54] = 0.5962D;
      this.dosD[55] = 0.55301D;
      this.dosD[56] = 0.51522D;
      this.dosD[57] = 0.48184D;
      this.dosD[58] = 0.45211D;
      this.dosD[59] = 0.42544D;
      this.dosD[60] = 0.40138D;
      this.dosD[61] = 0.37956D;
      this.dosD[62] = 0.35967D;
      this.dosD[63] = 0.34149D;
      this.dosD[64] = 0.32478D;
      this.dosD[65] = 0.30998D;
      this.dosD[66] = 0.29518D;
      this.dosD[67] = 0.28248D;
      this.dosD[68] = 0.26977D;
      this.dosD[69] = 0.25876D;
      this.dosD[70] = 0.24775D;
      this.dosD[71] = 0.23781D;
      this.dosD[72] = 0.22851D;
      this.dosD[73] = 0.21977D;
      this.dosD[74] = 0.21156D;
      this.dosD[75] = 0.20383D;
      this.dosD[76] = 0.19655D;
      this.dosD[77] = 0.18967D;
      this.dosD[78] = 0.18317D;
      this.dosD[79] = 0.17702D;
      this.dosD[80] = 0.17119D;
      this.dosD[81] = 0.16566D;
      this.dosD[82] = 0.1604D;
      this.dosD[83] = 0.15541D;
      this.dosD[84] = 0.15066D;
      this.dosD[85] = 0.14613D;
      this.dosD[86] = 0.14182D;
      this.dosD[87] = 0.1377D;
      this.dosD[88] = 0.13377D;
      this.dosD[89] = 0.13001D;
      this.dosD[90] = 0.12642D;
      this.dosD[91] = 0.12298D;
      this.dosD[92] = 0.11968D;
      this.dosD[93] = 0.11652D;
      this.dosD[94] = 0.11349D;
      this.dosD[95] = 0.11059D;
      this.dosD[96] = 0.10779D;
      this.dosD[97] = 0.10511D;
      this.dosD[98] = 0.10253D;
      this.dosD[99] = 0.10004D;
      this.dosD[100] = 0.09765D;
   }

   void generaindice() {
      this.indice[1][10] = 1;
      this.indice[1][11] = 2;
      this.indice[1][12] = 3;
      this.indice[1][13] = 4;
      this.indice[1][14] = 5;
      this.indice[1][15] = 6;
      this.indice[1][16] = 7;
      this.indice[1][17] = 8;
      this.indice[1][18] = 9;
      this.indice[1][19] = 10;
      this.indice[1][2] = 11;
      this.indice[1][20] = 12;
      this.indice[1][21] = 13;
      this.indice[1][22] = 14;
      this.indice[1][23] = 15;
      this.indice[1][24] = 16;
      this.indice[1][25] = 17;
      this.indice[1][3] = 18;
      this.indice[1][4] = 19;
      this.indice[1][5] = 20;
      this.indice[1][6] = 21;
      this.indice[1][7] = 22;
      this.indice[1][8] = 23;
      this.indice[1][9] = 24;
      this.indice[2][10] = 25;
      this.indice[2][11] = 26;
      this.indice[2][12] = 27;
      this.indice[2][2] = 28;
      this.indice[2][3] = 29;
      this.indice[2][4] = 30;
      this.indice[2][5] = 31;
      this.indice[2][6] = 32;
      this.indice[2][7] = 33;
      this.indice[2][8] = 34;
      this.indice[2][9] = 35;
      this.indice[3][3] = 36;
      this.indice[3][4] = 37;
      this.indice[3][5] = 38;
      this.indice[3][6] = 39;
      this.indice[3][7] = 40;
      this.indice[3][8] = 41;
      this.indice[4][4] = 42;
      this.indice[4][5] = 43;
      this.indice[4][6] = 44;
      this.indice[5][5] = 45;
   }

   void generamatriz() {
      // $FF: Couldn't be decompiled
   }

   public void init() {
      Font var1 = new Font("SansSerif", 0, 12);
      this.setFont(var1);
      this.setBackground(Color.gray);
      this.anchura = this.altura = this.getSize().width - 20;
      this.cv = new CuadroMath(0.0D, 5.0D, 0.0D, 2.0D, this.anchura, this.altura, "T", "Cv");
      this.Nx_val = new TextField(String.valueOf(this.Nx));
      this.Ny_val = new TextField(String.valueOf(this.Ny));
      this.calc = new Button("Calcula");
      this.limpia = new Button("Limpia");
      this.calc.addActionListener(this);
      this.limpia.addActionListener(this);
      this.setLayout(new BorderLayout());
      Panel var2 = new Panel();
      Panel var3 = new Panel();
      Panel var4 = new Panel();
      new Panel();
      new Panel();
      var2.add(new Label("Nx:"));
      var2.add(this.Nx_val);
      var2.add(new Label("      Ny:"));
      var2.add(this.Ny_val);
      var3.add(this.cv);
      var4.add(this.calc);
      var4.add(this.limpia);
      this.add("North", var2);
      this.add("Center", var3);
      this.add("South", var4);
      this.generaindice();
      this.generamatriz();
      this.generaexactas();
      this.paint(this.getGraphics());
   }

   public void paint(Graphics var1) {
      this.cv.paint(var1);
      this.exacto();
   }
}
