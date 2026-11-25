package ec.edu.uta.aulalogica.service;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Escritor mínimo de PDF de una página A4, sin librerías externas.
 * Se dibuja en "unidades de diseño" (924 x 1307, origen arriba a la izquierda) y se escala a A4.
 * Usa las fuentes estándar Helvetica y Helvetica-Bold (codificación WinAnsi), por lo que
 * las tildes y la ñ se ven bien sin incrustar fuentes.
 */
public final class PdfBasico {
    public static final double ANCHO = 924;
    public static final double ALTO = 1307;
    private static final double A4_ANCHO = 595.28;
    private static final double A4_ALTO = 841.89;
    private static final Charset WIN = Charset.forName("windows-1252");
    private static final double K = 0.5522847498; // constante de Bézier para círculos

    /** Anchos de Helvetica (códigos 32 a 126, en milésimas de em). */
    private static final int[] W_NORMAL = {
        278, 278, 355, 556, 556, 889, 667, 191, 333, 333, 389, 584, 278, 333, 278, 278,
        556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 278, 278, 584, 584, 584, 556,
        1015, 667, 667, 722, 722, 667, 611, 778, 722, 278, 500, 667, 556, 833, 722, 778,
        667, 778, 722, 667, 611, 722, 667, 944, 667, 667, 611, 278, 278, 278, 469, 556,
        333, 556, 556, 500, 556, 556, 278, 556, 556, 222, 222, 500, 222, 833, 556, 556,
        556, 556, 333, 500, 278, 556, 500, 722, 500, 500, 500, 334, 260, 334, 584};

    /** Anchos de Helvetica-Bold (códigos 32 a 126). */
    private static final int[] W_NEGRITA = {
        278, 333, 474, 556, 556, 889, 722, 238, 333, 333, 389, 584, 278, 333, 278, 278,
        556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 333, 333, 584, 584, 584, 611,
        975, 722, 722, 722, 722, 667, 611, 778, 722, 278, 556, 722, 611, 833, 722, 778,
        667, 778, 722, 667, 611, 722, 667, 944, 667, 667, 611, 333, 278, 333, 584, 556,
        333, 556, 611, 556, 611, 556, 333, 611, 611, 278, 278, 556, 278, 889, 611, 611,
        611, 611, 389, 556, 333, 611, 556, 778, 556, 556, 500, 389, 280, 389, 584};

    private final StringBuilder cs = new StringBuilder();

    // ---------- Formas ----------

    public PdfBasico rect(double x, double y, double w, double h, int rgb) {
        color(rgb, true);
        cs.append(n(x)).append(' ').append(n(y)).append(' ').append(n(w)).append(' ').append(n(h)).append(" re f\n");
        return this;
    }

    public PdfBasico rectRedondeado(double x, double y, double w, double h, double r, int rgb) {
        double rr = Math.max(0, Math.min(r, Math.min(w, h) / 2));
        color(rgb, true);
        cs.append(n(x + rr)).append(' ').append(n(y)).append(" m\n");
        cs.append(n(x + w - rr)).append(' ').append(n(y)).append(" l\n");
        curva(x + w - rr + K * rr, y, x + w, y + rr - K * rr, x + w, y + rr);
        cs.append(n(x + w)).append(' ').append(n(y + h - rr)).append(" l\n");
        curva(x + w, y + h - rr + K * rr, x + w - rr + K * rr, y + h, x + w - rr, y + h);
        cs.append(n(x + rr)).append(' ').append(n(y + h)).append(" l\n");
        curva(x + rr - K * rr, y + h, x, y + h - rr + K * rr, x, y + h - rr);
        cs.append(n(x)).append(' ').append(n(y + rr)).append(" l\n");
        curva(x, y + rr - K * rr, x + rr - K * rr, y, x + rr, y);
        cs.append("f\n");
        return this;
    }

    public PdfBasico elipse(double cx, double cy, double rx, double ry, int rgb) {
        color(rgb, true);
        cs.append(n(cx + rx)).append(' ').append(n(cy)).append(" m\n");
        curva(cx + rx, cy + K * ry, cx + K * rx, cy + ry, cx, cy + ry);
        curva(cx - K * rx, cy + ry, cx - rx, cy + K * ry, cx - rx, cy);
        curva(cx - rx, cy - K * ry, cx - K * rx, cy - ry, cx, cy - ry);
        curva(cx + K * rx, cy - ry, cx + rx, cy - K * ry, cx + rx, cy);
        cs.append("f\n");
        return this;
    }

    public PdfBasico circulo(double cx, double cy, double r, int rgb) {
        return elipse(cx, cy, r, r, rgb);
    }

    /** Polígono relleno: pares x, y. */
    public PdfBasico poligono(int rgb, double... xy) {
        if (xy.length < 6) return this;
        color(rgb, true);
        cs.append(n(xy[0])).append(' ').append(n(xy[1])).append(" m\n");
        for (int i = 2; i + 1 < xy.length; i += 2) {
            cs.append(n(xy[i])).append(' ').append(n(xy[i + 1])).append(" l\n");
        }
        cs.append("f\n");
        return this;
    }

    public PdfBasico linea(double x1, double y1, double x2, double y2, double grosor, int rgb) {
        color(rgb, false);
        cs.append(n(grosor)).append(" w ").append(n(x1)).append(' ').append(n(y1)).append(" m ")
          .append(n(x2)).append(' ').append(n(y2)).append(" l S\n");
        return this;
    }

    // ---------- Texto ----------

    /** Texto con la línea base en y; espaciado extra entre letras en unidades de diseño. */
    public PdfBasico texto(String s, double x, double y, double tam, boolean negrita, int rgb, double espaciado) {
        if (s == null || s.isEmpty()) return this;
        color(rgb, true);
        cs.append("BT /").append(negrita ? "F2" : "F1").append(' ').append(n(tam)).append(" Tf ");
        if (espaciado != 0) cs.append(n(espaciado)).append(" Tc ");
        // la matriz invierte el eje y para que el texto salga derecho en el sistema "arriba-izquierda"
        cs.append("1 0 0 -1 ").append(n(x)).append(' ').append(n(y)).append(" Tm (")
          .append(escapar(s)).append(") Tj ET\n");
        return this;
    }

    public PdfBasico texto(String s, double x, double y, double tam, boolean negrita, int rgb) {
        return texto(s, x, y, tam, negrita, rgb, 0);
    }

    public PdfBasico textoCentrado(String s, double cx, double y, double tam, boolean negrita, int rgb, double espaciado) {
        double w = ancho(s, tam, negrita, espaciado) - espaciado;
        return texto(s, cx - w / 2, y, tam, negrita, rgb, espaciado);
    }

    public PdfBasico textoDerecha(String s, double xDer, double y, double tam, boolean negrita, int rgb, double espaciado) {
        double w = ancho(s, tam, negrita, espaciado) - espaciado;
        return texto(s, xDer - w, y, tam, negrita, rgb, espaciado);
    }

    /** Ancho aproximado (con las métricas de Helvetica) de un texto, en unidades de diseño. */
    public static double ancho(String s, double tam, boolean negrita, double espaciado) {
        if (s == null) return 0;
        double total = 0;
        for (int i = 0; i < s.length(); i++) {
            total += anchoCaracter(s.charAt(i), negrita) * tam / 1000.0 + espaciado;
        }
        return total;
    }

    /** Recorta el texto con "..." para que quepa en el ancho máximo. */
    public static String recortar(String s, double max, double tam, boolean negrita) {
        if (s == null) return "";
        if (ancho(s, tam, negrita, 0) <= max) return s;
        String t = s;
        while (t.length() > 1 && ancho(t + "...", tam, negrita, 0) > max) t = t.substring(0, t.length() - 1);
        return t.stripTrailing() + "...";
    }

    private static int anchoCaracter(char c, boolean negrita) {
        if (c == '\u00B7') return 278;                 // punto medio
        if (c == '\u00BF') return 611;                 // ¿
        if (c == '\u00A1') return 333;                 // ¡
        int[] t = negrita ? W_NEGRITA : W_NORMAL;
        if (c >= 32 && c <= 126) return t[c - 32];
        String base = Normalizer.normalize(String.valueOf(c), Normalizer.Form.NFD);
        char b = base.charAt(0);
        if (b >= 32 && b <= 126) return t[b - 32];
        return 556;
    }

    // ---------- Salida ----------

    /** Arma el archivo PDF completo (una página A4). */
    public byte[] generar(String titulo) {
        double esc = A4_ANCHO / ANCHO;
        String contenido = "q " + n(esc) + " 0 0 " + n(-esc) + " 0 " + n(A4_ALTO) + " cm\n" + cs + "Q\n";
        byte[] cuerpo = contenido.getBytes(StandardCharsets.ISO_8859_1);

        List<byte[]> objs = new ArrayList<>();
        objs.add(ascii("<< /Type /Catalog /Pages 2 0 R >>"));
        objs.add(ascii("<< /Type /Pages /Kids [3 0 R] /Count 1 >>"));
        objs.add(ascii("<< /Type /Page /Parent 2 0 R /MediaBox [0 0 " + n(A4_ANCHO) + " " + n(A4_ALTO)
            + "] /Resources << /Font << /F1 5 0 R /F2 6 0 R >> >> /Contents 4 0 R >>"));
        ByteArrayOutputStream flujo = new ByteArrayOutputStream();
        flujo.writeBytes(ascii("<< /Length " + cuerpo.length + " >>\nstream\n"));
        flujo.writeBytes(cuerpo);
        flujo.writeBytes(ascii("endstream"));
        objs.add(flujo.toByteArray());
        objs.add(ascii("<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica /Encoding /WinAnsiEncoding >>"));
        objs.add(ascii("<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica-Bold /Encoding /WinAnsiEncoding >>"));
        objs.add(ascii("<< /Title (" + escapar(titulo) + ") /Producer (AulaLogica Web) >>"));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.writeBytes(ascii("%PDF-1.4\n%\u00E2\u00E3\u00CF\u00D3\n"));
        int[] pos = new int[objs.size()];
        for (int i = 0; i < objs.size(); i++) {
            pos[i] = out.size();
            out.writeBytes(ascii((i + 1) + " 0 obj\n"));
            out.writeBytes(objs.get(i));
            out.writeBytes(ascii("\nendobj\n"));
        }
        int xref = out.size();
        StringBuilder x = new StringBuilder("xref\n0 " + (objs.size() + 1) + "\n0000000000 65535 f \n");
        for (int p : pos) x.append(String.format(Locale.ROOT, "%010d 00000 n \n", p));
        x.append("trailer\n<< /Size ").append(objs.size() + 1).append(" /Root 1 0 R /Info 7 0 R >>\nstartxref\n")
         .append(xref).append("\n%%EOF\n");
        out.writeBytes(ascii(x.toString()));
        return out.toByteArray();
    }

    // ---------- Utilidades ----------

    private void curva(double x1, double y1, double x2, double y2, double x3, double y3) {
        cs.append(n(x1)).append(' ').append(n(y1)).append(' ').append(n(x2)).append(' ').append(n(y2)).append(' ')
          .append(n(x3)).append(' ').append(n(y3)).append(" c\n");
    }

    private void color(int rgb, boolean relleno) {
        cs.append(n(((rgb >> 16) & 255) / 255.0)).append(' ')
          .append(n(((rgb >> 8) & 255) / 255.0)).append(' ')
          .append(n((rgb & 255) / 255.0)).append(relleno ? " rg\n" : " RG\n");
    }

    /** Número sin notación científica y con punto decimal, sin importar el idioma del sistema. */
    private static String n(double v) {
        String s = String.format(Locale.ROOT, "%.3f", v);
        if (s.indexOf('.') >= 0) {
            s = s.replaceAll("0+$", "");
            if (s.endsWith(".")) s = s.substring(0, s.length() - 1);
        }
        return s.equals("-0") ? "0" : s;
    }

    /** Convierte el texto a WinAnsi y escapa lo que un PDF no admite dentro de ( ). */
    private static String escapar(String s) {
        byte[] b = s.replace('\n', ' ').replace('\r', ' ').getBytes(WIN);
        StringBuilder sb = new StringBuilder();
        for (byte x : b) {
            int c = x & 255;
            if (c == '(' || c == ')' || c == '\\') sb.append('\\').append((char) c);
            else if (c < 32 || c > 126) sb.append('\\').append(String.format(Locale.ROOT, "%03o", c));
            else sb.append((char) c);
        }
        return sb.toString();
    }

    private static byte[] ascii(String s) {
        return s.getBytes(StandardCharsets.ISO_8859_1);
    }
}
