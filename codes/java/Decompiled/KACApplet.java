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

public class KACApplet extends Applet implements ActionListener, ItemListener, Runnable {
   CuadroMath principal;
   CuadroMath diferencia;
   Button calc;
   Button pausa;
   Checkbox rapido;
   TextField N_val;
   TextField M_val;
   int N = 50;
   int M = 5;
   int Nmax = 50;
   int Mmax;
   int altura;
   int anchura;
   int radio_anillo;
   double anillox;
   double anilloy;
   double angulo;
   boolean corriendo;
   boolean activo;
   Thread runner;
   int[] anillo;
   int[] lista;
   Color[] paleta;
   int TMAX;
   int TLIMITE;
   int[] numeroazules;
   int tiempo;
   Choice condicioninicial;
   int velocidad;
   int opcion_inicial;

   public KACApplet() {
      this.Mmax = this.Nmax;
      this.corriendo = false;
      this.activo = true;
      this.anillo = new int[this.Nmax + 1];
      this.lista = new int[this.Nmax];
      this.paleta = new Color[]{Color.red, Color.blue};
      this.TMAX = 50;
      this.TLIMITE = 10000;
      this.numeroazules = new int[this.TLIMITE];
      this.tiempo = 0;
      this.velocidad = 1000;
   }

   void LeeCampos() {
      try {
         this.N = Integer.valueOf(this.N_val.getText());
         this.M = Integer.valueOf(this.M_val.getText());
      } catch (NumberFormatException var1) {
         this.N = 50;
         this.N_val.setText(String.valueOf(this.N));
         this.M = 5;
         this.M_val.setText(String.valueOf(this.M));
      }

      if (this.N < 1 || this.N > this.Nmax) {
         this.N = 50;
      }

      if (this.M < 1 || this.M > this.Mmax) {
         this.M = 5;
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
         this.TMAX = 50;
         this.diferencia.fijaEscalas(0.0D, (double)this.TMAX, (double)(-this.N), (double)this.N);
         this.principal.Limpia();
         this.diferencia.Limpia();
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
      int var1;
      for(var1 = 0; var1 < this.Nmax; this.lista[var1] = var1++) {
      }

      this.tiempo = 0;
      this.numeroazules[this.tiempo] = 0;
      this.anillox = (double)(this.anchura / 2);
      this.anilloy = (double)(this.altura / 2);
      this.principal.setCol(Color.black);
      this.principal.Circulo(this.anillox, this.anilloy, this.radio_anillo);
      this.principal.Circulo(this.anillox, this.anilloy, this.radio_anillo + 20);
      this.principal.setCol(Color.green);
      this.angulo = 6.283185307179586D / (double)this.N;

      for(var1 = 0; var1 < this.M; ++var1) {
         int var15;
         do {
            var15 = (int)Math.floor(Math.random() * (double)this.N);
         } while(this.lista[var15] == -1);

         this.lista[var15] = -1;
      }

      double var3;
      double var5;
      for(var1 = 0; var1 < this.N; ++var1) {
         if (this.lista[var1] == -1) {
            var3 = (double)(this.anchura / 2) + (double)(this.radio_anillo - 10) * Math.cos(((double)var1 + 0.5D) * this.angulo);
            var5 = (double)(this.altura / 2) + (double)(this.radio_anillo - 10) * Math.sin(((double)var1 + 0.5D) * this.angulo);
            double var7 = (double)(this.anchura / 2) + (double)(this.radio_anillo + 30) * Math.cos(((double)var1 + 0.5D) * this.angulo);
            double var9 = (double)(this.altura / 2) + (double)(this.radio_anillo + 30) * Math.sin(((double)var1 + 0.5D) * this.angulo);
            this.principal.Recta(var3, var5, var7, var9);
         }
      }

      this.opcion_inicial = this.condicioninicial.getSelectedIndex();
      int var2 = 0;

      for(var1 = 0; var1 < this.N; ++var1) {
         if (this.opcion_inicial == 0) {
            var2 = Math.random() > 0.5D ? 1 : 0;
         } else if (this.opcion_inicial == 1) {
            var2 = Math.random() > 0.3D ? 1 : 0;
         } else if (this.opcion_inicial == 2) {
            var2 = 1;
         } else {
            var2 = 1 - var2;
         }

         this.anillo[var1] = var2;
         if (var2 == 1) {
            int var10002 = this.numeroazules[this.tiempo]++;
         }

         this.principal.setCol(this.paleta[var2]);
         var3 = (double)(this.anchura / 2) + (double)this.radio_anillo * Math.cos((double)var1 * this.angulo);
         var5 = (double)(this.altura / 2) + (double)this.radio_anillo * Math.sin((double)var1 * this.angulo);
         double var11 = (double)(this.anchura / 2) + (double)(this.radio_anillo + 20) * Math.cos((double)var1 * this.angulo);
         double var13 = (double)(this.altura / 2) + (double)(this.radio_anillo + 20) * Math.sin((double)var1 * this.angulo);
         this.principal.CirculoRelleno(var3, var5, 6);
         this.principal.CirculoRelleno(var11, var13, 6);
      }

      if (this.opcion_inicial == 1 || this.opcion_inicial == 2) {
         int var16 = 2 * this.numeroazules[0] - this.N;
         double var17 = (double)var16;
         this.diferencia.FG_BG(false);
         this.diferencia.setCol(Color.blue);

         for(int var19 = 1; var19 <= this.TMAX; ++var19) {
            this.diferencia.Recta((double)(var19 - 1), var17, (double)var19, Math.pow(1.0D - 2.0D * (double)this.M / (double)this.N, (double)var19) * (double)var16);
            var17 = Math.pow(1.0D - 2.0D * (double)this.M / (double)this.N, (double)var19) * (double)var16;
         }

         this.diferencia.FG_BG(true);
      }

   }

   public void init() {
      Font var1 = new Font("SansSerif", 0, 12);
      this.setFont(var1);
      this.setBackground(Color.gray.brighter());
      this.anchura = (this.getSize().width - 20) / 2;
      this.altura = this.getSize().height - 150;
      this.radio_anillo = Math.min(this.anchura / 2, this.altura / 2) - 33;
      this.principal = new CuadroMath(0.0D, (double)this.anchura, 0.0D, (double)this.altura, this.anchura, this.altura, "", "", Color.white, false, false);
      this.diferencia = new CuadroMath(0.0D, (double)this.TMAX, (double)(-this.Nmax), (double)this.Nmax, this.anchura, this.altura - 10, "tiempo", "Na-Nr", Color.white);
      this.N_val = new TextField(String.valueOf(this.N), 3);
      this.M_val = new TextField(String.valueOf(this.M), 3);
      this.condicioninicial = new Choice();
      this.condicioninicial.add("Aleatorio (50/50)");
      this.condicioninicial.add("Aleatorio (70/30)");
      this.condicioninicial.add("Todos azules");
      this.condicioninicial.add("Alternado");
      this.calc = new Button("Inicia");
      this.pausa = new Button("Pausa ");
      this.calc.addActionListener(this);
      this.pausa.addActionListener(this);
      this.rapido = new Checkbox("Rápido");
      this.rapido.addItemListener(this);
      this.setLayout(new BorderLayout());
      Panel var2 = new Panel();
      Panel var3 = new Panel();
      Panel var4 = new Panel();
      Panel var5 = new Panel();
      Panel var6 = new Panel();
      Panel var7 = new Panel();
      var5.add(new Label("Número de partículas(<=" + this.Nmax + "):"));
      var5.add(this.N_val);
      var6.add(new Label("Número de sectores(<=" + this.Mmax + "):"));
      var6.add(this.M_val);
      var7.add(this.condicioninicial);
      var2.setLayout(new BorderLayout());
      var2.add("North", var5);
      var2.add("Center", var6);
      var2.add("South", var7);
      var3.add(this.principal);
      var3.add(this.diferencia);
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
         this.velocidad = 1;
      } else {
         this.velocidad = 1000;
      }

   }

   void rota_anillo() {
      int var6 = this.anillo[0];
      this.numeroazules[++this.tiempo] = 0;
      this.principal.FG_BG(false);

      double var2;
      int var10002;
      double var4;
      for(int var1 = 0; var1 < this.N - 1; ++var1) {
         if (this.lista[var1] == -1) {
            this.anillo[var1] = 1 - this.anillo[var1 + 1];
         } else {
            this.anillo[var1] = this.anillo[var1 + 1];
         }

         var2 = (double)(this.anchura / 2) + (double)this.radio_anillo * Math.cos((double)var1 * this.angulo);
         var4 = (double)(this.altura / 2) + (double)this.radio_anillo * Math.sin((double)var1 * this.angulo);
         this.principal.setCol(this.paleta[this.anillo[var1]]);
         this.principal.CirculoRelleno(var2, var4, 6);
         if (this.anillo[var1] == 1) {
            var10002 = this.numeroazules[this.tiempo]++;
         }
      }

      if (this.lista[this.N - 1] == -1) {
         this.anillo[this.N - 1] = 1 - var6;
      } else {
         this.anillo[this.N - 1] = var6;
      }

      if (this.anillo[this.N - 1] == 1) {
         var10002 = this.numeroazules[this.tiempo]++;
      }

      this.principal.setCol(this.paleta[this.anillo[this.N - 1]]);
      var2 = (double)(this.anchura / 2) + (double)this.radio_anillo * Math.cos((double)(this.N - 1) * this.angulo);
      var4 = (double)(this.altura / 2) + (double)this.radio_anillo * Math.sin((double)(this.N - 1) * this.angulo);
      this.principal.CirculoRelleno(var2, var4, 6);
      this.diferencia.FG_BG(false);
      this.diferencia.setCol(Color.black);
      this.diferencia.Recta((double)(this.tiempo - 1), (double)(2 * this.numeroazules[this.tiempo - 1] - this.N), (double)this.tiempo, (double)(2 * this.numeroazules[this.tiempo] - this.N));
      if (this.tiempo >= this.TMAX && this.tiempo < this.TLIMITE) {
         this.diferencia.Limpia();
         this.TMAX *= 2;
         this.diferencia.fijaEscalas(0.0D, (double)this.TMAX, (double)(-this.N), (double)this.N);

         for(int var7 = 0; var7 < this.TMAX / 2; ++var7) {
            this.diferencia.Recta((double)var7, (double)(2 * this.numeroazules[var7] - this.N), (double)(var7 + 1), (double)(2 * this.numeroazules[var7 + 1] - this.N));
         }
      }

      if (this.opcion_inicial == 1 || this.opcion_inicial == 2) {
         int var8 = 2 * this.numeroazules[0] - this.N;
         double var9 = (double)var8;
         this.diferencia.FG_BG(false);
         this.diferencia.setCol(Color.blue);

         for(int var11 = 1; var11 <= this.TMAX; ++var11) {
            this.diferencia.Recta((double)(var11 - 1), var9, (double)var11, Math.pow(1.0D - 2.0D * (double)this.M / (double)this.N, (double)var11) * (double)var8);
            var9 = Math.pow(1.0D - 2.0D * (double)this.M / (double)this.N, (double)var11) * (double)var8;
         }

         this.diferencia.FG_BG(true);
      }

      this.diferencia.FG_BG(true);
      this.principal.FG_BG(true);
   }

   public void run() {
      for(Thread var1 = Thread.currentThread(); this.runner == var1; this.rota_anillo()) {
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
