import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DiatomicApplet extends Applet implements ActionListener {
   double TROT = 30.0D;
   double TVIB = 3000.0D;
   double tmin;
   double tmax;
   double tstep;
   double tln;
   double t;
   double ttr;
   double ttv;
   double cv;
   int j;
   CuadroMath densidad;
   TextField Trot_val;
   TextField Tvib_val;
   Button calc;
   int altura;
   int anchura;
   Color[] paleta;
   int coloractual;
   double base10;

   public DiatomicApplet() {
      this.paleta = new Color[]{Color.black, Color.red, Color.blue, Color.green, Color.yellow};
      this.coloractual = 0;
      this.base10 = Math.log(10.0D);
   }

   void LeeCampos() {
      try {
         this.TVIB = Double.valueOf(this.Tvib_val.getText());
         this.TROT = Double.valueOf(this.Trot_val.getText());
      } catch (NumberFormatException var1) {
         this.TROT = 30.0D;
         this.TVIB = 3000.0D;
      }

      if (this.TVIB < 0.0D) {
         this.TVIB = 3000.0D;
      }

      if (this.TROT < 0.0D) {
         this.TROT = 30.0D;
      }

      this.Tvib_val.setText(String.valueOf(this.TVIB));
      this.Trot_val.setText(String.valueOf(this.TROT));
   }

   public void actionPerformed(ActionEvent var1) {
      if (var1.getSource() == this.calc) {
         this.LeeCampos();
         this.calcula();
      }

   }

   void calcula() {
      double var1 = 0.0D;
      if (this.TROT <= this.TVIB) {
         this.tmin = Math.log(this.TROT / 50.0D) / this.base10;
      } else {
         this.tmin = Math.log(this.TVIB / 50.0D) / this.base10;
      }

      double var3 = this.tmin + Math.log(50.0D) / this.base10;
      this.tmax = Math.log(5.0D * (this.TROT + this.TVIB)) / this.base10;
      this.tstep = (this.tmax - this.tmin) / 200.0D;
      this.densidad.fijaEscalas(Math.floor(var3) - 1.0D, Math.floor(this.tmax) + 0.0D, 1.0D, 4.0D);
      this.densidad.Limpia();
      this.densidad.setCol(Color.lightGray);
      this.densidad.Recta(Math.log(this.TROT) / this.base10, 1.0D, Math.log(this.TROT) / this.base10, 4.0D);
      this.densidad.Recta(Math.log(this.TVIB) / this.base10, 1.0D, Math.log(this.TVIB) / this.base10, 4.0D);
      this.densidad.Recta(Math.floor(var3) - 1.0D, 1.5D, Math.floor(this.tmax) + 1.0D, 1.5D);
      this.densidad.Recta(Math.floor(var3) - 1.0D, 2.5D, Math.floor(this.tmax) + 1.0D, 2.5D);
      this.densidad.Recta(Math.floor(var3) - 1.0D, 3.5D, Math.floor(this.tmax) + 1.0D, 3.5D);
      this.densidad.setCol(Color.black);

      for(this.tln = this.tmin; this.tln <= this.tmax; this.tln += this.tstep) {
         this.t = Math.pow(10.0D, this.tln);
         this.ttr = this.TROT / this.t;
         this.ttv = this.TVIB / this.t;
         this.cv = 1.5D + this.cvvib(this.ttv) + this.cvrot(this.ttr);
         this.densidad.Recta(this.tln - this.tstep, var1, this.tln, this.cv);
         var1 = this.cv;
      }

   }

   double cvrot(double var1) {
      double var3;
      if (var1 < 0.1D) {
         var3 = 1.0D;
      } else {
         double var5 = 0.0D;
         double var7 = 0.0D;
         double var9 = 0.0D;

         for(this.j = 0; this.j <= 1000; ++this.j) {
            double var11 = Math.exp(-var1 * (double)this.j * (double)(this.j + 1));
            var5 += (double)(2 * this.j + 1) * var11;
            var7 += (double)((2 * this.j + 1) * this.j * (this.j + 1)) * var11;
            var9 += (double)((2 * this.j + 1) * this.j * (this.j + 1) * this.j * (this.j + 1)) * var11;
         }

         var3 = (var9 / var5 - var7 * var7 / var5 / var5) * var1 * var1;
      }

      return var3;
   }

   double cvvib(double var1) {
      double var3;
      if (var1 > 100.0D) {
         var3 = 0.0D;
      } else {
         var3 = var1 * var1 * Math.exp(var1) / (Math.exp(var1) - 1.0D) / (Math.exp(var1) - 1.0D);
      }

      return var3;
   }

   public void init() {
      Font var1 = new Font("SansSerif", 0, 12);
      this.setFont(var1);
      this.setBackground(Color.lightGray);
      this.altura = this.getSize().width - 50;
      this.anchura = this.getSize().width - 20;
      this.densidad = new CuadroMath(0.0D, 4.0D, 1.0D, 4.0D, this.anchura, this.altura, "log10(T)", "Cv");
      this.Trot_val = new TextField(String.valueOf(this.TROT), 6);
      this.Tvib_val = new TextField(String.valueOf(this.TVIB), 6);
      this.calc = new Button("Calcula");
      this.calc.addActionListener(this);
      this.setLayout(new BorderLayout());
      Panel var2 = new Panel();
      Panel var3 = new Panel();
      Panel var4 = new Panel();
      Panel var5 = new Panel();
      Panel var6 = new Panel();
      var5.add(new Label("Temp. rotación:"));
      var5.add(this.Trot_val);
      var6.add(new Label("Temp. vibración:"));
      var6.add(this.Tvib_val);
      var2.setLayout(new BorderLayout());
      var2.add("North", var5);
      var2.add("South", var6);
      var3.add(this.densidad);
      var4.add(this.calc);
      this.add("North", var2);
      this.add("Center", var3);
      this.add("South", var4);
   }
}
