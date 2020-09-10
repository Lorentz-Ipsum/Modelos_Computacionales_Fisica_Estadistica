import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Scrollbar;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class BEFDApplet extends Applet implements ActionListener, Runnable, AdjustmentListener {

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
   double kT = 0.5D;
   int radio = 4;
   int velocidad = 1;
   int Niv = 20;
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
      this.occB = new int[this.Niv + 1];
      this.occF = new int[this.Niv + 1];
      this.hisB = new int[this.Niv + 1];
      this.hisF = new int[this.Niv + 1];
      this.beta = 0.1D;
      this.sumB = 0.0D;
      this.sumF = 0.0D;
      this.maxHistB = 0.0D;
      this.maxHistF = 0.0D;
      this.PASO = 100;
   }

   void Bose() {
      int var1 = (int)Math.floor(Math.random() * (double)this.Niv);

      for(var1 = 0; var1 <= this.Niv; ++var1) {
         if(this.occB[var1] != 0) {
            int var2 = var1 + (int)(2.0D * Math.floor(Math.random() * 2.0D) - 1.0D);
            if(var2 < 0) {
               var2 = 0;
            }

            if(var2 >= this.Niv) {
               var2 = this.Niv - 1;
            }

            double var5 = -this.beta * (double)(var2 - var1);
            var5 = Math.exp(var5);
            if(var5 >= 1.0D) {
               ++this.occB[var2];
               --this.occB[var1];
            } else {
               double var3 = Math.random();
               if(var3 < var5) {
                  ++this.occB[var2];
                  --this.occB[var1];
               }
            }
         }
      }

   }

   void Fermi() {
      int var1 = (int)Math.floor(Math.random() * (double)this.Niv);

      for(var1 = 0; var1 <= this.Niv; ++var1) {
         if(this.occF[var1] != 0) {
            int var2 = var1 + (int)(2.0D * Math.floor(Math.random() * 2.0D) - 1.0D);
            if(var2 < 0) {
               var2 = 0;
            }

            if(var2 >= this.Niv) {
               var2 = this.Niv - 1;
            }

            if(this.occF[var2] != 1) {
               double var5 = -this.beta * (double)(var2 - var1);
               var5 = Math.exp(var5);
               if(var5 >= 1.0D) {
                  ++this.occF[var2];
                  --this.occF[var1];
               } else {
                  double var3 = Math.random();
                  if(var3 < var5) {
                     ++this.occF[var2];
                     --this.occF[var1];
                  }
               }
            }
         }
      }

   }

   void LeeCampos() {
      try {
         this.N = Integer.valueOf(this.N_val.getText()).intValue();
      } catch (NumberFormatException var1) {
         this.N = 50;
         this.N_val.setText("50");
      }

      if(this.N < 1 || this.N > this.Nmax) {
         this.N = 10;
      }

      this.N_val.setText(String.valueOf(this.N));
   }

   public synchronized void actionPerformed(ActionEvent var1) {
      if(var1.getSource() == this.calc) {
         if(!this.corriendo) {
            this.runner.start();
            this.corriendo = true;
         }

         this.LeeCampos();
         this.inicializa();
      } else if(var1.getSource() == this.pausa) {
         if(!this.corriendo) {
            return;
         }

         this.activo ^= true;
         if(!this.activo) {
            this.pausa.setLabel("Activa");
         }

         if(this.activo) {
            this.pausa.setLabel("Pausa ");
            this.notify();
         }
      } else {
         double var2;
         GraficoAuxiliar var4;
         int var5;
         if(var1.getSource() == this.histogramaB) {
            var2 = Math.floor(15.0D * this.maxHistB / this.sumB * (double)this.N) / 10.0D;
            var4 = new GraficoAuxiliar(this.anchura, this.altura, 0.0D, (double)this.Niv, 0.0D, var2, "j", "N(j)");
            var4.cuadro.FG_BG(false);
            var4.cuadro.setCol(Color.blue);

            for(var5 = 0; var5 < this.Niv; ++var5) {
               var4.cuadro.Cuadrado((double)var5, 0.0D, (double)(var5 + 1), (double)this.hisB[var5] / this.sumB * (double)this.N);
            }

            var4.cuadro.FG_BG(true);
         } else if(var1.getSource() == this.histogramaF) {
            var2 = Math.floor(20.0D * this.maxHistF / this.sumF) / 10.0D * (double)this.N;
            var4 = new GraficoAuxiliar(this.anchura, this.altura, 0.0D, (double)this.Niv, 0.0D, 1.5D, "j", "N(j)");
            var4.cuadro.FG_BG(false);
            var4.cuadro.setCol(Color.lightGray);
            var4.cuadro.Recta((double)this.N, 0.0D, (double)this.N, 2.0D);
            var4.cuadro.setCol(Color.blue);

            for(var5 = 0; var5 < this.Niv; ++var5) {
               var4.cuadro.Cuadrado((double)var5, 0.0D, (double)(var5 + 1), (double)this.hisF[var5] / this.sumF * (double)this.N);
            }

            var4.cuadro.FG_BG(true);
         }
      }

   }

   public void adjustmentValueChanged(AdjustmentEvent var1) {
      int var2 = this.temperatura.getValue();
      this.beta = 20.0D / (double)var2;
      this.temp_val.setText("T=" + (double)this.temperatura.getValue() / 10.0D);
   }

   void inicializa() {
      this.maxHistB = 0.0D;
      this.maxHistF = 0.0D;
      this.sumB = 0.0D;
      this.sumF = 0.0D;

      for(int var1 = 0; var1 < this.Niv; ++var1) {
         if(var1 < this.N) {
            this.occF[var1] = 1;
            this.occB[var1] = 1;
         } else {
            this.occF[var1] = 0;
            this.occB[var1] = 0;
         }

         this.hisF[var1] = 0;
         this.hisB[var1] = 0;
      }

   }

   public void init() {
      Font var1 = new Font("SansSerif", 0, 12);
      this.setFont(var1);
      this.setBackground(Color.white);
      this.anchura = (this.getSize().width - 20) / 2;
      this.altura = this.getSize().height - 80;
      this.levelshift = (this.altura - 20) / this.Niv;
      this.bosones = new CuadroMath(0.0D, (double)this.anchura, 0.0D, (double)this.altura, this.anchura, this.altura, "", "", Color.white, false, false);
      this.fermiones = new CuadroMath(0.0D, (double)this.anchura, 0.0D, (double)this.altura, this.anchura, this.altura, "", "", Color.white, false, false);
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
      Panel var2 = new Panel();
      Panel var3 = new Panel();
      Panel var4 = new Panel();
      Panel var5 = new Panel();
      var4.add(new Label("Número de partículas (<=" + this.Nmax + "):"));
      var4.add(this.N_val);
      var5.add(this.temperatura);
      var5.add(this.temp_val);
      var2.add(var4);
      var2.add(var5);
      var3.add(this.histogramaB);
      var3.add(this.calc);
      var3.add(this.pausa);
      var3.add(this.histogramaF);
      this.add("North", var2);
      Panel var6 = new Panel();
      var6.add(this.bosones);
      var6.add(this.fermiones);
      this.add("Center", var6);
      this.add("South", var3);
      this.bolaB = this.getImage(this.getDocumentBase(), "bolaazul.gif");
      this.bolaF = this.getImage(this.getDocumentBase(), "bolanaranja.gif");
      this.runner = new Thread(this, "Tarea1");
   }

   void montecarlo() {
      boolean var3 = false;
      boolean var4 = false;
      boolean var5 = false;
      boolean var6 = false;
      this.Bose();
      this.Fermi();
      this.bosones.FG_BG(false);
      this.bosones.Limpia();
      this.bosones.setCol(Color.black);
      this.fermiones.FG_BG(false);
      this.fermiones.Limpia();
      this.fermiones.setCol(Color.black);

      for(int var1 = 0; var1 < this.Niv; ++var1) {
         this.hisB[var1] += this.occB[var1];
         this.sumB += (double)this.occB[var1];
         this.hisF[var1] += this.occF[var1];
         this.sumF += (double)this.occF[var1];
         if((double)this.hisB[var1] > this.maxHistB) {
            this.maxHistB = (double)this.hisB[var1];
         }

         if((double)this.hisF[var1] > this.maxHistF) {
            this.maxHistF = (double)this.hisF[var1];
         }

         int var8 = this.levelshift * var1 + 20;
         this.bosones.Recta(70.0D, (double)var8, (double)(this.anchura - 70), (double)var8);
         this.fermiones.Recta(80.0D, (double)var8, (double)(this.anchura - 80), (double)var8);
         if(this.occB[var1] > 0) {
            int var9 = (this.anchura - 140) / (this.occB[var1] + 1);
            if(var9 > 2 * this.radio + 2) {
               var9 = 2 * this.radio + 2;
            }

            for(int var2 = 1; var2 <= this.occB[var1]; ++var2) {
               int var7 = this.anchura / 2 + var9 * (var2 - 1) - this.radio - this.occB[var1] * var9 / 2;
               this.bosones.Imagen(this.bolaB, (double)var7, (double)(var8 + 2 * this.radio));
            }
         }

         if(this.occF[var1] != 0) {
            this.fermiones.Imagen(this.bolaF, (double)(this.anchura / 2) - 1.5D * (double)this.radio, (double)(var8 + 2 * this.radio));
         }
      }

      this.bosones.FG_BG(true);
      this.fermiones.FG_BG(true);
   }

   public void run() {
      for(Thread var1 = Thread.currentThread(); this.runner == var1; this.montecarlo()) {
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
            ;
         }
      }

   }
}
