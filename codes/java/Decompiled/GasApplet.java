import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GasApplet extends Applet implements ActionListener, Runnable {
   Image bola;
   Image marco;
   CuadroMath container;
   CuadroMath diferencia;
   Button calc;
   Button pausa;
   Button histograma;
   TextField particulas;
   TextField kT_val;
   int Ndcha;
   int Nizda;
   int N = 50;
   int TMAX = 500;
   int TLIMITE = 100000;
   int[] resta;
   int Nmax;
   int altura;
   int anchura;
   boolean corriendo;
   boolean activo;
   Thread runner;
   double[] posx;
   double[] posy;
   double[] velx;
   double[] vely;
   double kT;
   int radio;
   int paredx;
   int paredy;
   double tiempo;
   double dt;
   int timestep;
   int STEP;
   int velocidad;
   int[] histo;
   int maxHist;
   int totHist;

   public GasApplet() {
      this.resta = new int[this.TLIMITE];
      this.Nmax = 1000;
      this.corriendo = false;
      this.activo = true;
      this.posx = new double[this.Nmax + 1];
      this.posy = new double[this.Nmax + 1];
      this.velx = new double[this.Nmax + 1];
      this.vely = new double[this.Nmax + 1];
      this.kT = 1.0D;
      this.radio = 4;
      this.paredx = 17;
      this.paredy = 13;
      this.tiempo = 0.0D;
      this.dt = 1.0D;
      this.STEP = 1;
      this.velocidad = 100;
      this.histo = new int[2 * this.Nmax + 1];
      this.maxHist = 0;
   }

   void Boltzmann(double kT, int i) {
      double vel = Math.sqrt(-2.0D * Math.log(1.0D - Math.random()));
      double theta = 6.283185307179586D * Math.random();
      this.velx[i] = vel * Math.cos(theta) * Math.sqrt(kT);
      this.vely[i] = vel * Math.sin(theta) * Math.sqrt(kT);
   }

   void DibujaParticulas() {
      this.container.FG_BG(false);
      this.container.Limpia();
      this.container.Imagen(this.marco, 0.0D, (double)this.altura);

      for(int var1 = 1; var1 <= this.N; ++var1) {
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

         this.tiempo = 0.0D;
         this.timestep = 0;
         this.maxHist = 0;
         this.TMAX = 500;
         this.totHist = 0;
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
         GraficoAuxiliar var2 = new GraficoAuxiliar(this.anchura, this.altura, (double)(-this.N), (double)this.N, 0.0D, Math.floor((double)(20.0F * (float)this.maxHist / (float)this.totHist)) / 10.0D, "Ni-Nd", "Frec ");
         var2.cuadro.FG_BG(false);

         for(int var3 = -this.N; var3 <= this.N; ++var3) {
            var2.cuadro.setCol(Color.blue);
            var2.cuadro.Cuadrado((double)var3, 0.0D, (double)(var3 + 2), (double)((float)this.histo[var3 + this.N + 2] / (float)this.totHist));
            var2.cuadro.setCol(Color.red);
            var2.cuadro.Recta((double)var3, (double)((float)Math.exp((double)(-((float)var3) * (float)var3 / 2.0F / (float)this.N))) / Math.sqrt(6.283D * (double)this.N) * 2.0D, (double)(var3 + 1), (double)((float)Math.exp((double)(-((float)(var3 + 1)) * (float)(var3 + 1) / 2.0F / (float)this.N))) / Math.sqrt(6.283D * (double)this.N) * 2.0D);
         }

         var2.cuadro.FG_BG(true);
      }

   }

   void inicializa() {
      int var1;
      for(var1 = 0; var1 <= 2 * this.N; ++var1) {
         this.histo[var1] = 0;
      }

      for(var1 = 1; var1 <= this.N; ++var1) {
         int var2 = this.paredx + this.radio + (int)Math.floor(Math.random() * (double)(this.anchura - 2 * this.radio - 2 * this.paredx) / 2.0D);
         int var3 = this.paredx + this.radio + (int)Math.floor(Math.random() * (double)(this.altura - 2 * this.radio - 2 * this.paredx));
         this.posx[var1] = (double)var2;
         this.posy[var1] = (double)var3;
         this.Boltzmann(this.kT, var1);
      }

      this.DibujaParticulas();
   }

   public void init() {
      Font var1 = new Font("SansSerif", 0, 12);
      this.setFont(var1);
      this.setBackground(Color.lightGray);
      this.anchura = (this.getSize().width - 20) / 2;
      this.altura = this.getSize().height - 80;
      this.container = new CuadroMath(0.0D, (double)this.anchura, 0.0D, (double)this.altura, this.anchura, this.altura, "", "", Color.white, false, false);
      this.diferencia = new CuadroMath(0.0D, (double)this.TMAX, (double)(-this.N), (double)this.N, this.anchura, this.altura, "tiempo", "Ni-Nd");
      this.particulas = new TextField(String.valueOf(this.N), 3);
      this.kT_val = new TextField(String.valueOf(this.kT), 5);
      this.calc = new Button(" Inicia ");
      this.pausa = new Button(" Pausa ");
      this.histograma = new Button(" Histograma ");
      this.calc.addActionListener(this);
      this.pausa.addActionListener(this);
      this.histograma.addActionListener(this);
      this.setLayout(new BorderLayout());
      Panel var2 = new Panel();
      Panel var3 = new Panel();
      var2.add(new Label("Número de partículas (<=" + this.Nmax + "):"));
      var2.add(this.particulas);
      var2.add(new Label("   Temperatura:"));
      var2.add(this.kT_val);
      var3.add(this.calc);
      var3.add(this.pausa);
      var3.add(this.histograma);
      this.add("North", var2);
      Panel var4 = new Panel();
      var4.add(this.container);
      var4.add(this.diferencia);
      this.add("Center", var4);
      this.add("South", var3);
      this.bola = this.getImage(this.getDocumentBase(), "bolaazul.gif");
      this.marco = this.getImage(this.getDocumentBase(), "Marco2.gif");
      this.runner = new Thread(this, "Tarea1");
   }

   void movimiento() {
      this.Ndcha = this.Nizda = 0;

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

         if (this.posx[var1] > (double)(this.anchura / 2)) {
            ++this.Ndcha;
         } else {
            ++this.Nizda;
         }
      }

      this.DibujaParticulas();
      this.resta[++this.timestep] = -this.Ndcha + this.Nizda;
      this.diferencia.setCol(Color.black);
      this.tiempo += this.dt;
      this.diferencia.FG_BG(false);
      int var2;
      if (this.tiempo >= (double)this.TMAX && this.tiempo < (double)this.TLIMITE) {
         this.TMAX *= 2;
         this.diferencia.fijaEscalas(0.0D, (double)this.TMAX, (double)(-this.N), (double)this.N);
         this.diferencia.Limpia();
         this.diferencia.Recta(0.0D, Math.sqrt((double)this.N), (double)this.TMAX, Math.sqrt((double)this.N));
         this.diferencia.Recta(0.0D, -Math.sqrt((double)this.N), (double)this.TMAX, -Math.sqrt((double)this.N));
         this.diferencia.setCol(Color.lightGray);
         this.diferencia.Recta(0.0D, Math.sqrt((double)this.N) * 3.0D, (double)this.TMAX, Math.sqrt((double)this.N) * 3.0D);
         this.diferencia.Recta(0.0D, -Math.sqrt((double)this.N) * 3.0D, (double)this.TMAX, -Math.sqrt((double)this.N) * 3.0D);
         this.diferencia.setCol(Color.black);

         for(var2 = 0; var2 < this.TMAX * this.STEP / 2; var2 += this.STEP) {
            this.diferencia.Recta((double)var2 * this.dt, (double)this.resta[var2], (double)(var2 + this.STEP) * this.dt, (double)this.resta[var2 + this.STEP]);
         }
      }

      if (this.timestep % this.STEP == 0) {
         this.diferencia.Recta(this.tiempo - (double)this.STEP * this.dt, (double)this.resta[this.timestep - this.STEP], this.tiempo, (double)this.resta[this.timestep]);
      }

      this.diferencia.FG_BG(true);
      var2 = (int)Math.floor((double)(this.N + this.resta[this.timestep]));
      int var10002 = this.histo[var2]++;
      ++this.totHist;
      if (this.histo[var2] > this.maxHist) {
         this.maxHist = this.histo[var2];
      }

   }

   public void run() {
      for(Thread var1 = Thread.currentThread(); this.runner == var1; this.movimiento()) {
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
