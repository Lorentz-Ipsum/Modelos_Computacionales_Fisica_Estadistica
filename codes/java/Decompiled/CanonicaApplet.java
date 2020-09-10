import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class CanonicaApplet extends Applet implements ActionListener, Runnable, AdjustmentListener, ItemListener {
   Image bola;
   Image marco;
   Checkbox rapido;
   CuadroMath container;
   CuadroMath energia;
   Button calc;
   Button pausa;
   Button histograma;
   TextField particulas;
   TextField kT_val;
   int N = 50;
   int max;
   int Nmax;
   int altura;
   int anchura;
   boolean corriendo;
   boolean activo;
   int[] histo;
   int maxHist;
   int totHist;
   Thread runner;
   double[] posx;
   double[] posy;
   double[] velx;
   double[] vely;
   double kT;
   int radio;
   int paredx;
   int paredy;
   double TMAX;
   int tiempo;
   double dt;
   double y1;
   double y2;
   double lambda;
   int STEP;
   int[] numero;
   int dentro;
   int dentroold;
   int mx;
   int my;
   int dx;
   int dy;
   int dx_old;
   int dy_old;
   double energia_dentro;
   double energia_dentro_old;
   double vmax;

   public CanonicaApplet() {
      this.max = this.N;
      this.Nmax = 200;
      this.corriendo = false;
      this.activo = true;
      this.histo = new int[this.Nmax + 1];
      this.maxHist = 0;
      this.posx = new double[this.Nmax + 1];
      this.posy = new double[this.Nmax + 1];
      this.velx = new double[this.Nmax + 1];
      this.vely = new double[this.Nmax + 1];
      this.kT = 100.0D;
      this.radio = 4;
      this.paredx = 17;
      this.paredy = 13;
      this.TMAX = 100.0D;
      this.tiempo = 0;
      this.dt = 0.1D;
      this.STEP = 10;
      this.numero = new int[10000];
      this.dentro = 0;
      this.dentroold = 0;
      this.mx = 0;
      this.my = 0;
      this.energia_dentro = 0.0D;
      this.energia_dentro_old = 0.0D;
      this.vmax = 50.0D;
   }

   void Boltzmann(double var1, int var3) {
      double var4 = Math.sqrt(-2.0D * Math.log(1.0D - Math.random()));
      double var6 = 6.283185307179586D * Math.random();
      this.velx[var3] = var4 * Math.cos(var6) * Math.sqrt(var1);
      this.vely[var3] = var4 * Math.sin(var6) * Math.sqrt(var1);
   }

   void DibujaParticulas() {
      boolean var2 = false;
      int var4 = this.container.mousex;
      int var3 = this.container.mousey;
      this.dx = this.container.dx;
      this.dy = this.container.dy;
      if (this.dx != this.dx_old || this.dy != this.dy_old) {
         this.dx_old = this.dx;
         this.dy_old = this.dy;
      }

      this.container.FG_BG(false);
      this.container.Limpia();
      this.container.Imagen(this.marco, 0.0D, (double)this.altura);

      for(int var1 = 1; var1 <= this.N; ++var1) {
         this.container.Imagen(this.bola, this.posx[var1] - (double)(2 * this.radio) + 1.0D, this.posy[var1] + (double)(2 * this.radio) - 1.0D);
      }

      this.container.setCol(Color.green);
      this.container.Recuadro(var4, var3, this.dx, this.dy);
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
         this.kT = 100.0D;
      }

      this.particulas.setText(String.valueOf(this.N));
      this.kT_val.setText(String.valueOf(this.kT));
      this.vmax = 3.61D * Math.sqrt(this.kT);
   }

   public synchronized void actionPerformed(ActionEvent var1) {
      if (var1.getSource() == this.calc) {
         if (!this.corriendo) {
            this.runner.start();
            this.corriendo = true;
         }

         this.tiempo = 0;
         this.TMAX = 100.0D;
         this.LeeCampos();
         this.container.Limpia();
         this.inicializa();
         int var2 = 2 * (int)Math.floor((double)(this.N * this.dx * this.dy) * this.vmax * this.vmax / 2.0D / (double)this.max);
         var2 = this.N;
         this.energia.fijaEscalas(0.0D, this.TMAX, 0.0D, (double)var2);
         this.energia.Limpia();

         for(int var3 = 0; var3 < this.N - 1; ++var3) {
            this.histo[var3] = 0;
         }

         this.maxHist = 0;
         this.totHist = 0;
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
         double var9 = Math.floor(10.0D) / 10.0D;
         double var4 = Math.floor((double)((float)(this.maxHist + 5) / 5.0F)) * 5.0D;
         GraficoAuxiliar var6 = new GraficoAuxiliar(this.anchura, this.altura, 0.0D, var4, 0.0D, var9, "N", "Frec");
         var6.cuadro.FG_BG(false);

         for(int var7 = 0; var7 < this.N + 1; ++var7) {
            var6.cuadro.setCol(Color.red);
            var6.cuadro.Cuadrado((double)var7, 0.0D, (double)(var7 + 1), (double)((float)this.histo[var7] / (float)this.totHist));
         }

         var6.cuadro.setCol(Color.blue);
         this.lambda = (double)(this.N * this.dx * this.dy) / (double)(this.anchura - 2 * this.paredx) / (double)(this.altura - 2 * this.paredy);

         for(int var8 = 0; var8 < this.N + 1; ++var8) {
            this.y1 = -this.lambda * Math.log(2.718281828D) + (double)var8 * Math.log(this.lambda) - (double)lnfactorial((long)var8);
            this.y2 = -this.lambda * Math.log(2.718281828D) + (double)(var8 + 1) * Math.log(this.lambda) - (double)lnfactorial((long)var8 + 1L);
            this.y1 = Math.exp(this.y1);
            this.y2 = Math.exp(this.y2);
            var6.cuadro.Recta((double)var8 + 0.5D, this.y1, (double)var8 + 1.5D, this.y2);
         }

         var6.cuadro.FG_BG(true);
      }

   }

   public void adjustmentValueChanged(AdjustmentEvent var1) {
   }

   void inicializa() {
      this.dx_old = this.anchura;
      this.dy_old = this.altura;
      this.max = this.anchura * this.altura;
      this.tiempo = 0;
      this.TMAX = 100.0D;

      for(int var1 = 1; var1 <= this.N; ++var1) {
         int var2 = this.paredx + this.radio + (int)Math.floor(Math.random() * (double)(this.anchura - 2 * this.radio - 2 * this.paredx));
         int var3 = this.paredy + this.radio + (int)Math.floor(Math.random() * (double)(this.altura - 2 * this.radio - 2 * this.paredy));
         this.posx[var1] = (double)var2;
         this.posy[var1] = (double)var3;
         this.Boltzmann(this.kT, var1);
      }

      this.DibujaParticulas();
   }

   public void init() {
      Font var1 = new Font("SansSerif", 0, 12);
      this.setFont(var1);
      this.setBackground(Color.gray.brighter());
      this.anchura = (this.getSize().width - 20) / 2;
      this.altura = this.getSize().height - 80;
      this.container = new CuadroMath(0.0D, (double)this.anchura, 0.0D, (double)this.altura, this.anchura, this.altura, "", "", Color.white, false, false);
      this.energia = new CuadroMath(0.0D, this.TMAX, 0.0D, 1.0D, this.anchura, this.altura, "tiempo", "N");
      this.rapido = new Checkbox("Rápido");
      this.rapido.addItemListener(this);
      this.particulas = new TextField(String.valueOf(this.N), 3);
      this.kT_val = new TextField(String.valueOf(this.kT), 5);
      this.calc = new Button("Inicia");
      this.pausa = new Button("Pausa ");
      this.histograma = new Button(" Histograma ");
      this.calc.addActionListener(this);
      this.pausa.addActionListener(this);
      this.histograma.addActionListener(this);
      this.setLayout(new BorderLayout());
      Panel var2 = new Panel();
      Panel var3 = new Panel();
      var2.add(new Label("Número de partículas (<=" + this.Nmax + "):"));
      var2.add(this.particulas);
      var2.add(new Label("   Temperatura"));
      var2.add(this.kT_val);
      var3.add(this.calc);
      var3.add(this.pausa);
      var3.add(this.rapido);
      var3.add(this.histograma);
      this.add("North", var2);
      Panel var4 = new Panel();
      var4.add(this.container);
      var4.add(this.energia);
      this.add("Center", var4);
      this.add("South", var3);
      this.bola = this.getImage(this.getDocumentBase(), "bolaazul.gif");
      this.marco = this.getImage(this.getDocumentBase(), "Marco3.gif");
      this.runner = new Thread(this, "Tarea1");
   }

   public void itemStateChanged(ItemEvent var1) {
      if (!this.rapido.getState()) {
         this.dt = 0.1D;
         this.STEP = 10;
      } else {
         this.dt = 1.0D;
         this.STEP = 1;
      }

   }

   public static float lnfactorial(long var0) {
      return var0 <= 1L ? 0.0F : (float)((double)var0 * Math.log((double)var0) - (double)var0 * Math.log(2.718281828D) + 0.5D * Math.log(6.2831854D * (double)var0));
   }

   void movimiento() {
      boolean var2 = false;
      boolean var3 = false;
      int var4 = this.container.mousex;
      int var5 = this.container.mousey;
      int var6 = this.container.dx;
      int var7 = this.container.dy;
      this.dentro = 0;
      this.energia_dentro = 0.0D;

      for(int var1 = 1; var1 <= this.N; ++var1) {
         double[] var10000 = this.posx;
         var10000[var1] += this.velx[var1] * this.dt;
         var10000 = this.posy;
         var10000[var1] += this.vely[var1] * this.dt;
         if (this.posx[var1] < (double)(this.paredx + this.radio)) {
            this.posx[var1] = (double)(2 * (this.radio + this.paredx)) - this.posx[var1];
            var10000 = this.velx;
            var10000[var1] *= -1.0D;
         }

         if (this.posy[var1] < (double)(this.radio + this.paredy)) {
            this.posy[var1] = (double)(2 * (this.radio + this.paredy)) - this.posy[var1];
            var10000 = this.vely;
            var10000[var1] *= -1.0D;
         }

         if (this.posx[var1] > (double)(this.anchura - (this.radio + this.paredx))) {
            this.posx[var1] = (double)(2 * (this.anchura - this.paredx - this.radio)) - this.posx[var1];
            var10000 = this.velx;
            var10000[var1] *= -1.0D;
         }

         if (this.posy[var1] > (double)(this.altura - (this.radio + this.paredy))) {
            this.posy[var1] = (double)(2 * (this.altura - this.paredy - this.radio)) - this.posy[var1];
            var10000 = this.vely;
            var10000[var1] *= -1.0D;
         }

         int var14 = (int)Math.floor(this.posx[var1]);
         int var15 = (int)Math.floor(this.posy[var1]);
         double var16 = this.velx[var1];
         var16 = this.vely[var1];
         if (var14 > var4 && var14 < var4 + var6 && var15 > var5 && var15 < var5 + var7) {
            ++this.energia_dentro;
         }
      }

      this.DibujaParticulas();
      this.energia.setCol(Color.black);
      this.energia.Recta((double)this.tiempo, this.energia_dentro_old, (double)(this.tiempo + 1), this.energia_dentro);
      ++this.tiempo;
      this.numero[this.tiempo] = (int)this.energia_dentro;
      if ((int)this.energia_dentro > this.maxHist) {
         this.maxHist = (int)this.energia_dentro;
      }

      int var10002 = this.histo[(int)this.energia_dentro]++;
      ++this.totHist;
      this.energia.FG_BG(false);
      if ((double)this.tiempo > this.TMAX) {
         this.TMAX *= 2.0D;
         int var12 = this.N;
         this.energia.fijaEscalas(0.0D, this.TMAX, 0.0D, (double)var12);
         this.energia.Limpia();

         for(int var13 = 0; (double)var13 < this.TMAX / 2.0D; ++var13) {
            this.energia.Recta((double)var13, (double)this.numero[var13], (double)(var13 + 1), (double)this.numero[var13 + 1]);
         }
      }

      this.energia.FG_BG(true);
      this.energia_dentro_old = this.energia_dentro;
   }

   public void run() {
      for(Thread var1 = Thread.currentThread(); this.runner == var1; this.movimiento()) {
         try {
            Thread.sleep(10L);
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
