import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class OsciladoresApplet extends Applet implements ActionListener, ItemListener, Runnable {
   CuadroMath principal;
   CuadroMath entropia;
   int TMAX = 100;
   int TLIMITE = 100000;
   int tiempo;
   double[] S;
   Button calc;
   Button pausa;
   Button histograma;
   Checkbox rapido;
   TextField N_val;
   TextField M_val;
   int N;
   int M;
   int Nmax;
   int Mmax;
   int altura;
   int anchura;
   int radio;
   boolean corriendo;
   boolean activo;
   Thread runner;
   int[] cuenta;
   Color[] paleta;
   Choice frecuencias_ini;
   Choice fases_ini;
   int velocidad;
   double dt;
   double angulo_cajas;
   int opcion_inicial;
   double epsilon;
   double[] fases;
   double[] frecuencias;
   int BARRAS;
   int[] histo;
   int maxHist;

   public OsciladoresApplet() {
      this.S = new double[this.TLIMITE];
      this.N = 10;
      this.M = 6;
      this.Nmax = 100;
      this.Mmax = 100;
      this.corriendo = false;
      this.activo = true;
      this.cuenta = new int[this.Nmax];
      this.paleta = new Color[]{Color.red, Color.blue};
      this.velocidad = 1000;
      this.dt = 0.1D;
      this.epsilon = 0.1D;
      this.fases = new double[this.Nmax + 1];
      this.frecuencias = new double[this.Nmax + 1];
      this.BARRAS = 100;
      this.histo = new int[this.BARRAS + 1];
   }

   void LeeCampos() {
      try {
         this.N = Integer.valueOf(this.N_val.getText());
         this.M = Integer.valueOf(this.M_val.getText());
      } catch (NumberFormatException var1) {
         this.N = 10;
         this.N_val.setText(String.valueOf(this.N));
         this.M = 6;
         this.M_val.setText(String.valueOf(this.N));
      }

      if (this.N < 1 || this.N > this.Nmax) {
         this.N = 10;
      }

      if (this.M < 1 || this.M > this.Mmax) {
         this.M = 6;
      }

      if (this.M > this.N) {
         this.M = this.N;
      }

      this.N_val.setText(String.valueOf(this.N));
      this.M_val.setText(String.valueOf(this.M));
   }

   public synchronized void actionPerformed(ActionEvent var1) {
      if (var1.getSource() == this.calc) {
         if (!this.corriendo) {
            this.runner.start();
            this.corriendo = true;
         }

         this.LeeCampos();
         this.principal.Limpia();
         this.entropia.Limpia();
         this.inicializa();
         this.entropia.fijaEscalas(0.0D, (double)this.TMAX, 0.0D, 1.0D);
         this.entropia.Limpia();
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
         int var2 = 0;

         for(int var3 = 0; var3 < this.BARRAS; ++var3) {
            if (this.histo[var3] > 1 && var2 == 0) {
               var2 = var3;
            }
         }

         GraficoAuxiliar var4 = new GraficoAuxiliar(this.anchura, this.altura, (double)var2 / (double)this.BARRAS, 1.0D, 0.0D, Math.floor(Math.log((double)this.maxHist)) + 1.0D, "S", "N(S)");
         var4.cuadro.FG_BG(false);
         var4.cuadro.setCol(Color.blue);

         for(int var5 = var2; var5 < this.BARRAS; ++var5) {
            var4.cuadro.CirculoRelleno((double)var5 / (double)this.BARRAS, Math.log((double)this.histo[var5]), 4);
         }

         var4.cuadro.FG_BG(true);
      }

   }

   void inicializa() {
      int var2 = this.fases_ini.getSelectedIndex();
      this.TMAX = 100;
      this.tiempo = 0;
      this.maxHist = 0;

      int var1;
      for(var1 = 0; var1 <= this.BARRAS; ++var1) {
         this.histo[var1] = 0;
      }

      double var3 = (double)(this.anchura / 2);
      double var5 = (double)(this.altura / 2);
      this.principal.FG_BG(false);
      this.principal.setCol(Color.black);
      this.principal.Circulo(var3, var5, this.radio + 4);
      this.angulo_cajas = 6.283185307179586D / (double)this.M;
      this.principal.setCol(Color.red);

      double var7;
      double var9;
      for(var1 = 0; var1 < this.M; ++var1) {
         var7 = var3 + (double)(this.radio - 10) * Math.cos((double)var1 * this.angulo_cajas);
         var9 = var5 - (double)(this.radio - 10) * Math.sin((double)var1 * this.angulo_cajas);
         double var11 = var3 + (double)(this.radio + 15) * Math.cos((double)var1 * this.angulo_cajas);
         double var13 = var5 - (double)(this.radio + 15) * Math.sin((double)var1 * this.angulo_cajas);
         this.principal.Recta(var7, var9, var11, var13);
         this.cuenta[var1] = 0;
      }

      this.principal.setCol(Color.blue);
      boolean var15 = false;
      double var16;
      int var10002;
      int var20;
      if (var2 == 0) {
         for(var1 = 0; var1 < this.N; ++var1) {
            var16 = this.fases[var1] = 0.0D;
            var7 = var3 + (double)this.radio * Math.cos(var16);
            var9 = var5 - (double)this.radio * Math.sin(var16);
            this.principal.Recta(var3, var5, var7, var9);
            var20 = (int)Math.floor(var16 * (double)this.M / 2.0D / 3.141592653589793D);
            var10002 = this.cuenta[var20]++;
         }
      } else {
         for(var1 = 0; var1 < this.N; ++var1) {
            var16 = this.fases[var1] = Math.random() * 3.141592653589793D;
            var7 = var3 + (double)this.radio * Math.cos(var16);
            var9 = var5 - (double)this.radio * Math.sin(var16);
            this.principal.Recta(var3, var5, var7, var9);
            var20 = (int)Math.floor(var16 * (double)this.M / 2.0D / 3.141592653589793D);
            var10002 = this.cuenta[var20]++;
         }
      }

      this.principal.FG_BG(true);

      for(var1 = 0; var1 < this.M; ++var1) {
         double var18 = (double)this.cuenta[var1] / (double)this.N;
         if (this.cuenta[var1] != 0) {
            double[] var10000 = this.S;
            var10000[0] -= Math.log(var18) * var18 / Math.log((double)this.M);
         }
      }

      var2 = this.frecuencias_ini.getSelectedIndex();
      if (var2 == 0) {
         for(var1 = 0; var1 < this.N; ++var1) {
            this.frecuencias[var1] = Math.random() * 2.0D * 3.141592653589793D;
         }
      } else if (var2 == 1) {
         for(var1 = 0; var1 < this.N; ++var1) {
            this.frecuencias[var1] = 0.5235987755982988D + (2.0D * Math.random() - 1.0D) * this.epsilon;
         }
      } else {
         for(var1 = 0; var1 < this.N; ++var1) {
            this.frecuencias[var1] = (double)var1 * 2.0D * 3.141592653589793D / (double)this.Nmax;
         }
      }

   }

   public void init() {
      Font var1 = new Font("SansSerif", 0, 12);
      this.setFont(var1);
      this.setBackground(Color.gray.brighter());
      this.anchura = (this.getSize().width - 20) / 2;
      this.altura = this.getSize().height - 150;
      this.radio = Math.min(this.anchura / 2, this.altura / 2) - 10;
      this.N_val = new TextField(this.N + " ");
      this.M_val = new TextField(this.M + " ");
      this.principal = new CuadroMath(0.0D, (double)this.anchura, 0.0D, (double)this.altura, this.anchura, this.altura, "", "", Color.white, false, false);
      this.entropia = new CuadroMath(0.0D, (double)this.TMAX, 0.0D, 1.0D, this.anchura, this.altura, "tiempo", "S");
      this.frecuencias_ini = new Choice();
      this.frecuencias_ini.add("Aleatorias");
      this.frecuencias_ini.add("Casi iguales");
      this.frecuencias_ini.add("Equiespaciadas");
      this.fases_ini = new Choice();
      this.fases_ini.add("Nulas");
      this.fases_ini.add("Aleatorias");
      this.calc = new Button("Inicia");
      this.pausa = new Button("Pausa ");
      this.histograma = new Button("Histograma");
      this.calc.addActionListener(this);
      this.pausa.addActionListener(this);
      this.histograma.addActionListener(this);
      this.rapido = new Checkbox("Rápido");
      this.rapido.addItemListener(this);
      this.setLayout(new BorderLayout());
      Panel var2 = new Panel();
      Panel var3 = new Panel();
      Panel var4 = new Panel();
      Panel var5 = new Panel();
      Panel var6 = new Panel();
      Panel var7 = new Panel();
      var2.setLayout(new BorderLayout());
      var5.add(new Label("Frecuencias iniciales"));
      var5.add(this.frecuencias_ini);
      var6.add(new Label("Fases iniciales"));
      var6.add(this.fases_ini);
      var7.add(new Label("Osciladores (N<" + this.Nmax + ")"));
      var7.add(this.N_val);
      var7.add(new Label("Sectores (M<" + this.Mmax + ")"));
      var7.add(this.M_val);
      var2.add("North", var5);
      var2.add("Center", var6);
      var2.add("South", var7);
      var3.add(this.principal);
      var3.add(this.entropia);
      var4.add(this.calc);
      var4.add(this.pausa);
      var4.add(this.rapido);
      var4.add(this.histograma);
      this.add("North", var2);
      this.add("Center", var3);
      this.add("South", var4);
      this.runner = new Thread(this, "Tarea1");
   }

   public void itemStateChanged(ItemEvent var1) {
      if (this.rapido.getState()) {
         this.velocidad = 1;
      } else {
         this.velocidad = 1000;
      }

   }

   void rota_fases() {
      boolean var14 = false;
      double var2 = (double)(this.anchura / 2);
      double var8 = (double)(this.altura / 2);
      this.principal.FG_BG(false);
      this.principal.Limpia();
      this.principal.setCol(Color.black);
      this.principal.Circulo(var2, var8, this.radio + 4);
      this.principal.setCol(Color.red);

      int var1;
      double var4;
      double var10;
      for(var1 = 0; var1 < this.M; ++var1) {
         var4 = var2 + (double)(this.radio - 10) * Math.cos((double)var1 * this.angulo_cajas);
         var10 = var8 - (double)(this.radio - 10) * Math.sin((double)var1 * this.angulo_cajas);
         double var6 = var2 + (double)(this.radio + 15) * Math.cos((double)var1 * this.angulo_cajas);
         double var12 = var8 - (double)(this.radio + 15) * Math.sin((double)var1 * this.angulo_cajas);
         this.principal.Recta(var4, var10, var6, var12);
         this.cuenta[var1] = 0;
      }

      this.principal.setCol(Color.blue);

      int var10002;
      for(var1 = 0; var1 < this.N; ++var1) {
         double[] var10000 = this.fases;
         var10000[var1] += this.frecuencias[var1] * this.dt;
         if (this.fases[var1] > 6.283185307179586D) {
            var10000 = this.fases;
            var10000[var1] -= 6.283185307179586D;
         }

         double var15 = this.fases[var1];
         var4 = var2 + (double)this.radio * Math.cos(var15);
         var10 = var8 - (double)this.radio * Math.sin(var15);
         this.principal.Recta(var2, var8, var4, var10);
         int var22 = (int)Math.floor(var15 * (double)this.M / 2.0D / 3.141592653589793D);
         var10002 = this.cuenta[var22]++;
      }

      this.principal.FG_BG(true);
      double var17 = 0.0D;

      for(var1 = 0; var1 < this.M; ++var1) {
         double var19 = (double)this.cuenta[var1] / (double)this.N;
         if (this.cuenta[var1] != 0) {
            var17 -= Math.log(var19) * var19 / Math.log((double)this.M);
         }
      }

      this.entropia.FG_BG(false);
      ++this.tiempo;
      this.S[this.tiempo] = var17;
      this.entropia.Recta((double)(this.tiempo - 1), this.S[this.tiempo - 1], (double)this.tiempo, this.S[this.tiempo]);
      int var21;
      if (this.tiempo >= this.TMAX) {
         this.TMAX *= 2;
         this.entropia.fijaEscalas(0.0D, (double)this.TMAX, 0.0D, 1.0D);
         this.entropia.Limpia();

         for(var21 = 0; var21 < this.TMAX / 2; ++var21) {
            this.entropia.Recta((double)var21, this.S[var21], (double)(var21 + 1), this.S[var21 + 1]);
            if (this.tiempo >= this.TLIMITE) {
               this.tiempo = 0;
               this.inicializa();
            }
         }
      }

      this.entropia.FG_BG(true);
      var21 = (int)Math.floor(var17 * (double)this.BARRAS);
      var10002 = this.histo[var21]++;
      if (this.histo[var21] > this.maxHist) {
         this.maxHist = this.histo[var21];
      }

   }

   public void run() {
      for(Thread var1 = Thread.currentThread(); this.runner == var1; this.rota_fases()) {
         try {
            Thread.sleep((long)this.velocidad);
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
