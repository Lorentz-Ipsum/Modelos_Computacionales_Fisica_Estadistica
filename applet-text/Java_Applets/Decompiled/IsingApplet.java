 import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.text.DecimalFormat;

public class IsingApplet extends Applet implements ActionListener, AdjustmentListener, Runnable {
   int[][] spin;
   double C = 0.5D;
   CuadroMath espines;
   CuadroMath magnetizacion;
   Color[] paleta;
   int coloractual;
   Button calc;
   Button pausa;
   int altura;
   int anchura;
   DecimalFormat formato;
   double[] razon;
   Scrollbar setcanje;
   double[] imanacion;
   long SIZE;
   int PASO;
   int indice;
   int indiceold;
   double canje;
   Label jval;
   boolean corriendo;
   boolean activo;
   Thread runner;
   int pixelsize;
   Choice system_size;
   Choice condicion_inicial;

   public IsingApplet() {
      this.paleta = new Color[]{Color.blue, Color.red, Color.yellow, Color.green, Color.gray, Color.black};
      this.coloractual = 0;
      this.imanacion = new double[51];
      this.PASO = 50;
      this.indiceold = 0;
      this.canje = (double)(1 / this.PASO);
      this.corriendo = false;
      this.activo = true;
      this.pixelsize = 2;
   }

   void Metropolis() {
      long var5 = 2000L;
      boolean var7 = false;
      this.espines.FG_BG(false);

      for(long var3 = 0L; var3 < this.SIZE; ++var3) {
         int var1 = (int)Math.floor((double)(this.anchura + 1) * Math.random() / (double)this.pixelsize);
         int var2 = (int)Math.floor((double)(this.altura + 1) * Math.random() / (double)this.pixelsize);
         int var9 = this.energia(var1, var2);
         if (Math.random() <= this.razon[(var9 + 4) / 2]) {
            int[] var10000 = this.spin[var1];
            var10000[var2] *= -1;
            this.plotSpin(var1, var2);
            double[] var8 = this.imanacion;
            int var10001 = this.indice;
            var8[var10001] += (double)(2 * this.spin[var1][var2]);
         }
      }

      this.espines.FG_BG(true);
      this.pintaMag();
   }

   public synchronized void actionPerformed(ActionEvent var1) {
      if (var1.getSource() == this.calc) {
         if (!this.corriendo) {
            this.runner.start();
            this.corriendo = true;
         }

         this.iniRed();
         this.iniRazon();
         this.magnetizacion.Limpia();
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
      }

   }

   public void adjustmentValueChanged(AdjustmentEvent var1) {
      this.indiceold = this.indice;
      this.indice = this.setcanje.getValue();
      this.canje = (double)this.indice / (double)this.PASO;
      this.jval.setText("     j=" + this.formato.format(this.canje) + "     ");
      this.imanacion[this.indice] = this.imanacion[this.indiceold];
      this.iniRazon();
   }

   int energia(int var1, int var2) {
      boolean var7 = false;
      int var3 = IsingApplet.Vecinos.Left(var1, this.anchura / this.pixelsize);
      int var4 = IsingApplet.Vecinos.Right(var1, this.anchura / this.pixelsize);
      int var6 = IsingApplet.Vecinos.Down(var2, this.altura / this.pixelsize);
      int var5 = IsingApplet.Vecinos.Up(var2, this.altura / this.pixelsize);
      int var8 = this.spin[var3][var2] + this.spin[var4][var2] + this.spin[var1][var6] + this.spin[var1][var5];
      return this.spin[var1][var2] * var8;
   }

   void iniRazon() {
      for(int var1 = 0; var1 <= 4; ++var1) {
         this.razon[var1] = Math.exp(-2.0D * (2.0D * (double)var1 - 4.0D) * this.canje);
      }

   }

   void iniRed() {
      int var3 = (int)Math.floor(this.canje * 50.0D);

      for(int var4 = 0; var4 <= 50; ++var4) {
         this.imanacion[var4] = 0.0D;
      }

      this.pixelsize = (int)Math.pow(2.0D, 1.0D + (double)this.system_size.getSelectedIndex());
      if (this.condicion_inicial.getSelectedIndex() == 0) {
         this.C = 0.5D;
      } else {
         this.C = 1.0D;
      }

      this.SIZE = (long)((this.altura / this.pixelsize + 1) * (this.anchura / this.pixelsize + 1));
      this.espines.FG_BG(false);

      for(int var1 = 0; var1 <= this.anchura / this.pixelsize; ++var1) {
         for(int var2 = 0; var2 <= this.altura / this.pixelsize; ++var2) {
            int var10002;
            if (Math.random() > this.C) {
               this.spin[var1][var2] = 1;
               var10002 = this.imanacion[var3]++;
            } else {
               this.spin[var1][var2] = -1;
               var10002 = this.imanacion[var3]--;
            }

            this.plotSpin(var1, var2);
         }
      }

      this.espines.FG_BG(true);
      this.pintaMag();
   }

   public void init() {
      Font var1 = new Font("SansSerif", 0, 12);
      this.setFont(var1);
      this.setBackground(Color.gray.brighter());
      this.anchura = this.altura = 256;
      this.SIZE = (long)((this.altura / this.pixelsize + 1) * (this.anchura / this.pixelsize + 1));
      this.spin = new int[this.anchura + 1][this.altura + 1];
      this.razon = new double[5];
      this.system_size = new Choice();
      this.system_size.addItem("128x128");
      this.system_size.addItem("64x64");
      this.system_size.addItem("32x32");
      this.system_size.addItem("16x16");
      this.system_size.addItem("8x8");
      this.system_size.select(2);
      this.condicion_inicial = new Choice();
      this.condicion_inicial.addItem("Aleatorio");
      this.condicion_inicial.addItem("Todos -1");
      this.setcanje = new Scrollbar(0, 0, 1, 1, this.PASO + 1);
      this.setcanje.addAdjustmentListener(this);
      this.espines = new CuadroMath(0.0D, (double)this.anchura, 0.0D, (double)this.altura, this.anchura, this.altura, "", "", Color.white, false, false);
      this.magnetizacion = new CuadroMath(0.0D, 1.0D, -1.1D, 1.1D, this.anchura, this.altura, "j", "M");
      this.calc = new Button("Genera red");
      this.pausa = new Button("Pausa ");
      this.calc.addActionListener(this);
      this.pausa.addActionListener(this);
      this.formato = new DecimalFormat("0.00");
      this.jval = new Label("     j=" + this.formato.format(this.canje) + "     ");
      this.setLayout(new BorderLayout());
      Panel var2 = new Panel();
      Panel var3 = new Panel();
      Panel var4 = new Panel();
      Panel var5 = new Panel();
      var5.setLayout(new BorderLayout());
      Panel var6 = new Panel();
      var6.add(this.system_size);
      var6.add(this.condicion_inicial);
      var5.add("North", var6);
      var5.add("Center", this.setcanje);
      var5.add("South", this.jval);
      var2.add(var5);
      var3.add(this.espines);
      var3.add(this.magnetizacion);
      var4.add(this.calc);
      var4.add(this.pausa);
      this.add("North", var2);
      this.add("Center", var3);
      this.add("South", var4);
      this.runner = new Thread(this, "Tarea1");
   }

   void pintaMag() {
      int var1 = this.setcanje.getValue();
      this.canje = (double)var1 / (double)this.PASO;
      this.magnetizacion.FG_BG(false);
      this.magnetizacion.Limpia();
      this.magnetizacion.setCol(Color.blue);
      this.magnetizacion.Recta(0.43D, -1.1D, 0.43D, 1.1D);
      this.magnetizacion.setCol(Color.red);

      for(int var2 = 0; var2 <= 50; ++var2) {
         this.magnetizacion.CirculoRelleno((double)var2 / 50.0D, this.imanacion[var2] / (double)this.SIZE, 2);
      }

      this.magnetizacion.FG_BG(true);
   }

   void plotSpin(int var1, int var2) {
      this.espines.setCol(this.paleta[this.spin[var1][var2] + 1]);
      this.espines.CuadradoRelleno((double)(this.pixelsize * var1), (double)(this.pixelsize * var2), this.pixelsize, this.pixelsize);
   }

   public void run() {
      for(Thread var1 = Thread.currentThread(); this.runner == var1; this.Metropolis()) {
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
