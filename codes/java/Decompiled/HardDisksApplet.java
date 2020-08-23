import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HardDisksApplet extends Applet implements ActionListener, Runnable {
   int N = 20;
   int Nmax = 100;
   double kT = 1.0D;
   double[] posx;
   double[] posy;
   double[] velx;
   double[] vely;
   int anchura;
   int altura;
   int radio;
   int paredx;
   int paredy;
   int rl;
   int rh;
   CuadroMath container;
   CuadroMath diferencia;
   Image bola;
   Image marco;
   Button calc;
   Button pausa;
   Button histograma;
   TextField particulas;
   TextField kT_val;
   Thread runner;
   int Ndcha;
   int Nizda;
   int TMAX;
   int TLIMITE;
   int tiempo;
   int[] resta;
   boolean corriendo;
   boolean activo;
   int[] histo;
   int maxHist;
   int totHist;

   public HardDisksApplet() {
      this.posx = new double[this.Nmax];
      this.posy = new double[this.Nmax];
      this.velx = new double[this.Nmax];
      this.vely = new double[this.Nmax];
      this.anchura = 200;
      this.altura = 200;
      this.radio = 4;
      this.paredx = 17;
      this.paredy = 13;
      this.TMAX = 200;
      this.TLIMITE = 10000;
      this.resta = new int[this.TLIMITE];
      this.corriendo = false;
      this.activo = true;
      this.histo = new int[2 * this.Nmax + 1];
      this.maxHist = 0;
   }

   void Boltzmann(double var1, int var3) {
      double var4 = Math.sqrt(-2.0D * Math.log(1.0D - Math.random()));
      double var6 = 6.283185307179586D * Math.random();
      this.velx[var3] = var4 * Math.cos(var6) * Math.sqrt(var1);
      this.vely[var3] = var4 * Math.sin(var6) * Math.sqrt(var1);
   }

   void DibujaParticulas() {
      this.container.FG_BG(false);
      this.container.Limpia();
      this.container.Imagen(this.marco, 0.0D, (double)this.altura);

      for(int var1 = 0; var1 < this.N; ++var1) {
         this.container.Imagen(this.bola, this.posx[var1] - (double)(2 * this.radio) + 1.0D, this.posy[var1] + (double)(2 * this.radio) - 1.0D);
      }

      this.container.FG_BG(true);
   }

   void LeeCampos() {
      try {
         this.N = Integer.valueOf(this.particulas.getText());
         this.kT = Double.valueOf(this.kT_val.getText());
      } catch (NumberFormatException var1) {
         this.N = 50;
         this.particulas.setText("50");
         this.kT = 100.0D;
         this.kT_val.setText(String.valueOf(this.kT));
      }

      if (this.N < 1 || this.N > this.Nmax) {
         this.N = 50;
      }

      if (this.kT < 0.0D) {
         this.kT = 1.0D;
      }

      this.particulas.setText(String.valueOf(this.N));
      this.kT_val.setText(String.valueOf(this.kT));
   }

   public synchronized void actionPerformed(ActionEvent var1) {
      if (var1.getSource() == this.calc) {
         if (!this.corriendo) {
            this.runner.start();
            this.corriendo = true;
         }

         this.tiempo = 0;
         this.TMAX = 100;
         this.resta[0] = this.N;
         this.LeeCampos();
         this.inicializa();
         this.diferencia.fijaEscalas(0.0D, (double)this.TMAX, (double)(-this.N), (double)this.N);
         this.diferencia.Limpia();
         this.diferencia.setCol(Color.black);
         this.diferencia.Recta(0.0D, Math.sqrt((double)this.N), (double)this.TMAX, Math.sqrt((double)this.N));
         this.diferencia.Recta(0.0D, -Math.sqrt((double)this.N), (double)this.TMAX, -Math.sqrt((double)this.N));
         this.diferencia.setCol(Color.lightGray);
         this.diferencia.Recta(0.0D, Math.sqrt((double)this.N) * 3.0D, (double)this.TMAX, Math.sqrt((double)this.N) * 3.0D);
         this.diferencia.Recta(0.0D, -Math.sqrt((double)this.N) * 3.0D, (double)this.TMAX, -Math.sqrt((double)this.N) * 3.0D);
         this.diferencia.setCol(Color.black);
      } else if (var1.getSource() == this.pausa) {
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
      } else if (var1.getSource() == this.histograma) {
         double var2 = Math.floor((double)(20.0F * (float)this.maxHist / (float)this.totHist)) / 10.0D;
         GraficoAuxiliar var4 = new GraficoAuxiliar(this.anchura, this.altura, (double)(-this.N), (double)this.N, 0.0D, var2, "Ni-Nd", "Frec");
         var4.cuadro.FG_BG(false);
         var4.cuadro.Texto((double)(-this.N), var2 - 0.05D, "  Número de partículas=" + this.N, Color.black);

         for(int var5 = -this.N; var5 < this.N - 1; ++var5) {
            var4.cuadro.setCol(Color.red);
            var4.cuadro.Cuadrado((double)(var5 + 1), 0.0D, (double)(var5 + 3), (double)((float)this.histo[var5 + this.N + 2] / (float)this.totHist));
            var4.cuadro.setCol(Color.blue);
            var4.cuadro.Recta((double)var5, (double)((float)Math.exp((double)(-((float)var5) * (float)var5 / 2.0F / (float)this.N))) / Math.sqrt(6.283D * (double)this.N) * 2.0D, (double)(var5 + 1), (double)((float)Math.exp((double)(-((float)(var5 + 1)) * (float)(var5 + 1) / 2.0F / (float)this.N))) / Math.sqrt(6.283D * (double)this.N) * 2.0D);
         }

         var4.cuadro.FG_BG(true);
      }

   }

   void inicializa() {
      this.tiempo = 0;
      this.Ndcha = 0;
      this.Nizda = 0;
      this.resta[0] = this.N;
      this.maxHist = this.totHist = 0;

      for(int var3 = 0; var3 <= 2 * this.N; ++var3) {
         this.histo[var3] = 0;
      }

      for(int var4 = 0; var4 < this.N; ++var4) {
         int var1 = this.paredx + this.radio + (int)Math.floor(Math.random() * (double)(this.anchura - 2 * this.radio - 2 * this.paredx) / 2.0D);
         int var2 = this.paredx + this.radio + (int)Math.floor(Math.random() * (double)(this.altura - 2 * this.radio - 2 * this.paredx));
         this.posx[var4] = (double)var1;
         this.posy[var4] = (double)var2;
         this.Boltzmann(this.kT, var4);
         if (this.posx[var4] > (double)(this.anchura / 2)) {
            ++this.Ndcha;
         } else {
            ++this.Nizda;
         }
      }

   }

   public void init() {
      Font var1 = new Font("SansSerif", 0, 12);
      this.setFont(var1);
      this.setBackground(Color.white);
      this.anchura = (this.getSize().width - 20) / 2;
      this.altura = this.getSize().height - 80;
      this.container = new CuadroMath(0.0D, (double)this.anchura, 0.0D, (double)this.altura, this.anchura, this.altura, "", "", Color.white, false, false);
      this.diferencia = new CuadroMath(0.0D, (double)this.TMAX, (double)(-this.N), (double)this.N, this.anchura, this.altura, "tiempo", "Ni-Nd");
      this.calc = new Button("Inicia");
      this.pausa = new Button("Pausa ");
      this.histograma = new Button(" Histograma ");
      this.calc.addActionListener(this);
      this.pausa.addActionListener(this);
      this.histograma.addActionListener(this);
      this.particulas = new TextField(String.valueOf(this.N), 3);
      this.kT_val = new TextField(String.valueOf(this.kT), 5);
      Panel var2 = new Panel();
      var2.add(new Label("Partículas (<100):"));
      var2.add(this.particulas);
      var2.add(new Label("  Temperatura:"));
      var2.add(this.kT_val);
      Panel var3 = new Panel();
      var3.add(this.container);
      var3.add(this.diferencia);
      Panel var4 = new Panel();
      var4.add(this.calc);
      var4.add(this.pausa);
      var4.add(this.histograma);
      this.setLayout(new BorderLayout());
      this.add("North", var2);
      this.add("Center", var3);
      this.add("South", var4);
      this.bola = this.getImage(this.getDocumentBase(), "bolaazul.gif");
      this.marco = this.getImage(this.getDocumentBase(), "Marco.gif");
      this.paint(this.getGraphics());
      this.runner = new Thread(this, "Tarea1");
   }

   public void movimiento() {
      this.Ndcha = this.Nizda = 0;

      for(int i = 0; i < this.N; ++i) {
         double[] var10000;
         for(int var38 = i + 1; var38 < this.N; ++var38) {
            double var21 = 0.5D;
            double var5 = this.velx[i] - this.velx[var38];
            double var7 = this.vely[i] - this.vely[var38];
            double var11 = var5 * var5 + var7 * var7;
            double var1 = this.posx[var38] - this.posx[i];
            double var3 = this.posy[var38] - this.posy[i];
            double var29 = var5 * var1 + var7 * var3;
            if (var29 > 0.0D) {
               double var27 = var7 * var1 - var5 * var3;
               double var9 = Math.sqrt(var11);
               double var23 = 1.0D / ((double)(2 * this.radio) * var9) * var27;
               double var25 = var29 / var11 - (double)(2 * this.radio) * (1.0D - var23 * var23) / var9;
               if (var23 * var23 < 1.0D && var25 >= 0.0D && var25 < 1.0D) {
                  var10000 = this.posx;
                  var10000[i] += var25 * this.velx[i];
                  var10000 = this.posy;
                  var10000[i] += var25 * this.vely[i];
                  var10000 = this.posx;
                  var10000[var38] += var25 * this.velx[var38];
                  var10000 = this.posy;
                  var10000[var38] += var25 * this.vely[var38];
                  double var31 = var23 * var23 * 2.0D - 1.0D;
                  double var33 = 2.0D * var23 * Math.sqrt(1.0D - var23 * var23);
                  double var35 = Math.atan2(var7, var5) + Math.atan2(var33, var31);
                  var33 = Math.sin(var35);
                  var31 = Math.cos(var35);
                  double var13 = var21 * (this.velx[i] + this.velx[var38]);
                  double var15 = var21 * (this.vely[i] + this.vely[var38]);
                  double var17 = var21 * var9 * var31;
                  double var19 = var21 * var9 * var33;
                  this.velx[i] = var17 + var13;
                  this.vely[i] = var19 + var15;
                  this.velx[var38] = -var17 + var13;
                  this.vely[var38] = -var19 + var15;
               }
            }
         }

         var10000 = this.posx;
         var10000[i] += this.velx[i];
         var10000 = this.posy;
         var10000[i] += this.vely[i];
         if (this.posx[i] < (double)(this.paredx + this.radio)) {
            this.posx[i] = (double)(2 * (this.radio + this.paredx)) - this.posx[i];
            var10000 = this.velx;
            var10000[i] *= -1.0D;
         }

         if (this.posy[i] < (double)(this.paredy + this.radio)) {
            this.posy[i] = (double)(2 * (this.radio + this.paredy)) - this.posy[i];
            var10000 = this.vely;
            var10000[i] *= -1.0D;
         }

         if (this.posx[i] > (double)(this.anchura - (this.radio + this.paredx))) {
            this.posx[i] = (double)(2 * (this.anchura - this.paredx - this.radio)) - this.posx[i];
            var10000 = this.velx;
            var10000[i] *= -1.0D;
         }

         if (this.posy[i] > (double)(this.altura - (this.radio + this.paredy))) {
            this.posy[i] = (double)(2 * (this.altura - this.paredy - this.radio)) - this.posy[i];
            var10000 = this.vely;
            var10000[i] *= -1.0D;
         }

         if (this.posx[i] > (double)(this.anchura / 2)) {
            ++this.Ndcha;
         } else {
            ++this.Nizda;
         }
      }

      this.DibujaParticulas();
      this.resta[++this.tiempo] = -this.Ndcha + this.Nizda;
      this.diferencia.FG_BG(false);
      this.diferencia.setCol(Color.gray);
      this.diferencia.Recta(0.0D, Math.sqrt((double)this.N), (double)this.TMAX, Math.sqrt((double)this.N));
      this.diferencia.Recta(0.0D, -Math.sqrt((double)this.N), (double)this.TMAX, -Math.sqrt((double)this.N));
      this.diferencia.setCol(Color.lightGray);
      this.diferencia.Recta(0.0D, Math.sqrt((double)this.N) * 3.0D, (double)this.TMAX, Math.sqrt((double)this.N) * 3.0D);
      this.diferencia.Recta(0.0D, -Math.sqrt((double)this.N) * 3.0D, (double)this.TMAX, -Math.sqrt((double)this.N) * 3.0D);
      this.diferencia.setCol(Color.black);
      this.diferencia.Recta((double)(this.tiempo - 1), (double)this.resta[this.tiempo - 1], (double)this.tiempo, (double)this.resta[this.tiempo]);
      int var39;
      if (this.tiempo >= this.TMAX && this.tiempo < this.TLIMITE) {
         this.TMAX *= 2;
         this.diferencia.fijaEscalas(0.0D, (double)this.TMAX, (double)(-this.N), (double)this.N);
         this.diferencia.Limpia();

         for(var39 = 0; var39 < this.TMAX / 2; ++var39) {
            this.diferencia.Recta((double)var39, (double)this.resta[var39], (double)var39, (double)this.resta[var39 + 1]);
         }
      }

      this.diferencia.FG_BG(true);
      var39 = (int)Math.floor((double)(this.N + this.resta[this.tiempo]));
      int var10002 = this.histo[var39]++;
      ++this.totHist;
      if (this.histo[var39] > this.maxHist) {
         this.maxHist = this.histo[var39];
      }

   }

   public void paint(Graphics var1) {
      this.container.paint(var1);
      this.diferencia.paint(var1);
      this.DibujaParticulas();
   }

   public void run() {
      for(Thread var1 = Thread.currentThread(); this.runner == var1; this.movimiento()) {
         try {
            Thread.sleep(1L);
            synchronized(this){}

            try {
               while(!this.activo) {
                  this.wait();
               }
            } catch (Throwable var5) {
               throw var5;
            }
         } catch (InterruptedException var6) {
         }
      }

   }
}
