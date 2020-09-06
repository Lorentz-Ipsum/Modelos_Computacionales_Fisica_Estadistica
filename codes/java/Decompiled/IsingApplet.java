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

      for(long i = 0L; i < this.SIZE; ++i) {
         int spinX = (int)Math.floor((double)(this.anchura + 1) * Math.random() / (double)this.pixelsize);
         int spinY = (int)Math.floor((double)(this.altura + 1) * Math.random() / (double)this.pixelsize);
         int E = this.energia(spinX, spinY);
         if (Math.random() <= this.razon[(E + 4) / 2]) {
            int[] spinColumn = this.spin[spinX];
            spinColumn[spinY] *= -1;
            this.plotSpin(spinX, spinY);
            double[] valImanacion = this.imanacion;
            int index = this.indice;
            valImanacion[index] += (double)(2 * this.spin[spinX][spinY]);
         }
      }

      this.espines.FG_BG(true);
      this.pintaMag();
   }

   public synchronized void actionPerformed(ActionEvent eventAction) {
      if (eventAction.getSource() == this.calc) {
         if (!this.corriendo) {
            this.runner.start();
            this.corriendo = true;
         }

         this.iniRed();
         this.iniRazon();
         this.magnetizacion.Limpia();
     } else if (eventAction.getSource() == this.pausa) {
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

   public void adjustmentValueChanged(AdjustmentEvent eventAdjust) {
      this.indiceold = this.indice;
      this.indice = this.setcanje.getValue();
      this.canje = (double)this.indice / (double)this.PASO;
      this.jval.setText("     j=" + this.formato.format(this.canje) + "     ");
      this.imanacion[this.indice] = this.imanacion[this.indiceold];
      this.iniRazon();
   }

   int energia(int X, int Y) {
      boolean var7 = false;
      int Xleft = IsingApplet.Vecinos.Left(X, this.anchura / this.pixelsize);
      int Xright = IsingApplet.Vecinos.Right(X, this.anchura / this.pixelsize);
      int Xup = IsingApplet.Vecinos.Down(Y, this.altura / this.pixelsize);
      int Xdown = IsingApplet.Vecinos.Up(Y, this.altura / this.pixelsize);
      int spinSum = this.spin[Xleft][Y] + this.spin[Xright][Y] + this.spin[X][Xup] + this.spin[var1][Xdown];
      return this.spin[X][Y] * spinSum;
   }

   void iniRazon() {
      for(int i = 0; i <= 4; ++i) {
         this.razon[i] = Math.exp(-2.0D * (2.0D * (double)i - 4.0D) * this.canje);
      }

   }

   void iniRed() {
      int energiaCanje = (int)Math.floor(this.canje * 50.0D);

      for(int i = 0; var4 <= 50; ++i) {
         this.imanacion[i] = 0.0D;
      }

      this.pixelsize = (int)Math.pow(2.0D, 1.0D + (double)this.system_size.getSelectedIndex());
      if (this.condicion_inicial.getSelectedIndex() == 0) {
         this.C = 0.5D;
      } else {
         this.C = 1.0D;
      }

      this.SIZE = (long)((this.altura / this.pixelsize + 1) * (this.anchura / this.pixelsize + 1));
      this.espines.FG_BG(false);

      for(int i = 0; i <= this.anchura / this.pixelsize; ++i) {
         for(int j = 0; j <= this.altura / this.pixelsize; ++j) {
            int imanacionInstant;
            if (Math.random() > this.C) {
               this.spin[i][j] = 1;
               imanacionInstant = this.imanacion[energiaCanje]++;
            } else {
               this.spin[i][j] = -1;
               imanacionInstant = this.imanacion[energiaCanje]--;
            }

            this.plotSpin(i, j);
         }
      }

      this.espines.FG_BG(true);
      this.pintaMag();
   }

   public void init() {
      Font fuente = new Font("SansSerif", 0, 12);
      this.setFont(fuente);
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
      int canjeValue = this.setcanje.getValue();
      this.canje = (double)canjeValue / (double)this.PASO;
      this.magnetizacion.FG_BG(false);
      this.magnetizacion.Limpia();
      this.magnetizacion.setCol(Color.blue);
      this.magnetizacion.Recta(0.43D, -1.1D, 0.43D, 1.1D);
      this.magnetizacion.setCol(Color.red);

      for(int i = 0; i <= 50; ++i) {
         this.magnetizacion.CirculoRelleno((double)i / 50.0D, this.imanacion[i] / (double)this.SIZE, 2);
      }

      this.magnetizacion.FG_BG(true);
   }

   void plotSpin(int i, int j) {
      this.espines.setCol(this.paleta[this.spin[i][j] + 1]);
      this.espines.CuadradoRelleno((double)(this.pixelsize * i), (double)(this.pixelsize * j), this.pixelsize, this.pixelsize);
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
