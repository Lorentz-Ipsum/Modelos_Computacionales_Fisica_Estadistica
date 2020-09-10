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

public class TLCApplet extends Applet implements ActionListener, ItemListener, Runnable {
   CuadroMath principal;
   Button calc;
   Button pausa;
   TextField N_val;
   Choice distribucion;
   int N = 25;
   int Nmax = 50;
   int altura;
   int anchura;
   int indice;
   boolean corriendo = false;
   boolean activo = true;
   Thread runner;
   double[] media = new double[3];
   double[] varianza = new double[3];
   double[] maxHist = new double[3];
   double[] histo;
   double[] gaussiana;
   Color[] paleta;
   Checkbox rapido;
   double norma;
   double max;
   double normagaussiana;
   double top;
   int velocidad;

   public TLCApplet() {
      this.histo = new double[6 * this.Nmax + 1];
      this.gaussiana = new double[5 * (6 * this.Nmax + 1)];
      this.paleta = new Color[]{Color.red, Color.blue};
      this.velocidad = 1;
   }

   void LeeCampos() {
      try {
         this.N = Integer.valueOf(this.N_val.getText());
      } catch (NumberFormatException var1) {
         this.N = 10;
         this.N_val.setText("10");
      }

      if (this.N < 1 || this.N > this.Nmax) {
         this.N = 10;
      }

      this.N_val.setText(String.valueOf(this.N));
   }

   public synchronized void actionPerformed(ActionEvent var1) {
      if (var1.getSource() == this.calc) {
         if (!this.corriendo) {
            this.runner.start();
            this.corriendo = true;
         }

         this.indice = this.distribucion.getSelectedIndex();
         this.LeeCampos();
         this.principal.fijaEscalas(0.0D, this.maxHist[this.indice] * (double)this.N + 0.0D, 0.0D, 1.0D);
         this.principal.Limpia();
         this.inicializa();
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

   void inicializa() {
      this.top = 1.0D;
      this.norma = 0.0D;
      this.max = 0.0D;
      this.normagaussiana = 1.0D / Math.sqrt(1.5707963267948966D * this.varianza[this.indice] * (double)this.N);

      int var3;
      for(var3 = 0; (double)var3 <= this.maxHist[this.indice] * (double)this.N; ++var3) {
         this.histo[var3] = 0.0D;
      }

      for(double var1 = 0.0D; var1 <= this.maxHist[this.indice] * (double)this.N; var1 += 0.2D) {
         var3 = (int)Math.round(5.0D * var1);
         this.gaussiana[var3] = Math.exp(-(var1 - this.media[this.indice] * (double)this.N) * (var1 - this.media[this.indice] * (double)this.N) / (0.5D * this.varianza[this.indice] * (double)this.N)) * this.normagaussiana;
      }

   }

   public void init() {
      Font var1 = new Font("SansSerif", 0, 12);
      this.setFont(var1);
      this.setBackground(Color.gray.brighter());
      this.anchura = this.getSize().width - 20;
      this.altura = this.getSize().height - 90;
      this.principal = new CuadroMath(0.0D, (double)(6 * this.N + 2), 0.0D, 1.0D, this.anchura, this.altura, "S", "P ");
      this.N_val = new TextField(String.valueOf(this.N), 3);
      this.calc = new Button("Inicia");
      this.pausa = new Button("Pausa ");
      this.calc.addActionListener(this);
      this.pausa.addActionListener(this);
      this.rapido = new Checkbox("Lento");
      this.rapido.addItemListener(this);
      this.distribucion = new Choice();
      this.distribucion.add("Dados");
      this.distribucion.add("[0,1]");
      this.distribucion.add("0 o 1");
      this.media[0] = 3.5D;
      this.media[1] = 0.5D;
      this.media[2] = 0.5D;
      this.varianza[0] = 8.5069D;
      this.varianza[1] = 0.285D;
      this.varianza[2] = 1.0D;
      this.maxHist[0] = 6.0D;
      this.maxHist[1] = 1.0D;
      this.maxHist[2] = 1.0D;
      this.setLayout(new BorderLayout());
      Panel var2 = new Panel();
      Panel var3 = new Panel();
      Panel var4 = new Panel();
      Panel var5 = new Panel();
      Panel var6 = new Panel();
      Panel var7 = new Panel();
      var5.add(new Label("Variables(<=" + this.Nmax + "):"));
      var5.add(this.N_val);
      var5.add(this.distribucion);
      var2.add("North", var5);
      var2.add("South", var6);
      var2.add("East", var7);
      var3.add(this.principal);
      var4.add(this.calc);
      var4.add(this.pausa);
      var4.add(this.rapido);
      this.add("North", var2);
      this.add("Center", var3);
      this.add("South", var4);
      this.runner = new Thread(this, "Tarea1");
   }

   public void itemStateChanged(ItemEvent var1) {
      if (this.rapido.getState()) {
         this.velocidad = 1000;
      } else {
         this.velocidad = 1;
      }

   }

   public void run() {
      for(Thread var1 = Thread.currentThread(); this.runner == var1; this.tiradas()) {
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

   void tiradas() {
      double var3 = 0.0D;
      double var5 = 0.0D;
      int var2;
      if (this.indice == 0) {
         for(var2 = 0; var2 < this.N; ++var2) {
            var5 += 1.0D + Math.floor(Math.random() * 6.0D);
         }

         if (++this.histo[(int)Math.floor(var5)] > this.max) {
            this.max = this.histo[(int)Math.floor(var5)];
         }

         ++this.norma;
      } else if (this.indice == 1) {
         for(var2 = 0; var2 < this.N; ++var2) {
            var5 += Math.random();
         }

         if (++this.histo[(int)Math.floor(var5)] > this.max) {
            this.max = this.histo[(int)Math.floor(var5)];
         }

         var3 = 0.5D;
         var3 = 1.0D;
         ++this.norma;
      } else {
         for(var2 = 0; var2 < this.N; ++var2) {
            var5 += Math.floor(Math.random() * 2.0D);
         }

         if (++this.histo[(int)Math.floor(var5)] > this.max) {
            this.max = this.histo[(int)Math.floor(var5)];
         }

         ++this.norma;
      }

      if (Math.max(this.normagaussiana, (this.max + 2.0D) / this.norma) <= this.top / 2.0D) {
         this.top /= 2.0D;
      }

      this.principal.FG_BG(false);
      this.principal.fijaEscalas(0.0D, this.maxHist[this.indice] * (double)this.N + 0.0D, 0.0D, this.top);
      this.principal.Limpia();
      this.principal.setCol(Color.red);

      for(int var1 = 0; (double)var1 < this.maxHist[this.indice] * (double)this.N - 1.0D; ++var1) {
         this.principal.Cuadrado((double)var1 + 0.5D, 0.0D, (double)var1 + 1.5D, this.histo[var1 + 1] / this.norma);
      }

      this.principal.setCol(Color.blue);
      double var8 = this.gaussiana[0];

      for(double var10 = 0.2D; var10 <= this.maxHist[this.indice] * (double)this.N; var10 += 0.2D) {
         int var7 = (int)Math.round(5.0D * var10);
         this.principal.Recta(var10 - var3, var8, var10 + 0.2D - var3, this.gaussiana[var7]);
         var8 = this.gaussiana[var7];
      }

      this.principal.FG_BG(true);
   }
}
