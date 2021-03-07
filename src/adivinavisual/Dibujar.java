package adivinavisual;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.HashMap;


public class Dibujar extends javax.swing.JPanel {

    private ArbolB.Nodo arbol;
    private HashMap positionNodes, subtreeSizes;
    private Dimension empty; 
    private FontMetrics fontMetrics;
    private boolean dirty;
    private int parentToChiizquiederechaa = 20, chiizquiederechaaToChiizquiederechaa = 30;
    
    public Dibujar(ArbolB.Nodo arbol) {
        
        initComponents();
        
        this.arbol = arbol;
        
        super.repaint();
        
        this.positionNodes = new HashMap();
        this.subtreeSizes = new HashMap();
        this.empty = new Dimension(0,0);
        this.dirty = true;
    }

    private void calculateLocations() {
        positionNodes.clear();
        subtreeSizes.clear();
        
        ArbolB.Nodo root = arbol;
        
        if (root != null) {
            calculateSubtreeSize(root);
            calculatePosition(root, Integer.MAX_VALUE, Integer.MAX_VALUE, 0); 
        }
    }
    
    private Dimension calculateSubtreeSize(ArbolB.Nodo node) {
        if (node == null){
            return new Dimension(0,0);
        }
        Dimension izquiederechaa = calculateSubtreeSize(node.izq);
        Dimension derecha = calculateSubtreeSize(node.der);
          
        int height = fontMetrics.getHeight() + parentToChiizquiederechaa + Math.max(izquiederechaa.height, derecha.height);
        int weight = izquiederechaa.width + chiizquiederechaaToChiizquiederechaa + derecha.width;

        Dimension dimension = new Dimension(weight, height);
        subtreeSizes.put(node, dimension);

        return dimension;
    }
    
    private void calculatePosition(ArbolB.Nodo node, int left, int right, int top) { 
        if (node == null) {
            return;
        }
        
        Dimension izquiederechaa = (Dimension) subtreeSizes.get(node.izq);
        if (izquiederechaa == null) {
            izquiederechaa = empty;
        }  
      
        Dimension derecha = (Dimension) subtreeSizes.get(node.der);
        
        if (derecha == null) {
            derecha = empty;
        }
        int center = 0;
        if (right != Integer.MAX_VALUE) {   
            center = right - derecha.width - chiizquiederechaaToChiizquiederechaa/2;
        }else {   
            if (left != Integer.MAX_VALUE) {
                center = left + izquiederechaa.width + chiizquiederechaaToChiizquiederechaa/2;
            }
        } 
        
        int width = fontMetrics.stringWidth(node.info+"");
 
        positionNodes.put(node,new Rectangle(center - width/2 - 3, top, width + 6, fontMetrics.getHeight()));
      
        calculatePosition(node.izq, Integer.MAX_VALUE, center - chiizquiederechaaToChiizquiederechaa/2, top + fontMetrics.getHeight() + parentToChiizquiederechaa);
        calculatePosition(node.der, center + chiizquiederechaaToChiizquiederechaa/2, Integer.MAX_VALUE, top + fontMetrics.getHeight() + parentToChiizquiederechaa);
        
    }
    
    private void drawTree(Graphics2D g, ArbolB.Nodo n, int puntox, int puntoy, int yoffs) {
        if (n == null) {
            return;
        }
        Rectangle rect = (Rectangle) positionNodes.get(n);
        g.draw(rect);
      
        g.drawString(n.info + "", rect.x + 3, rect.y + yoffs);
        g.setFont(new java.awt.Font("Arial", 1, 12));
        g.setColor(Color.darkGray);
        if (puntox != Integer.MAX_VALUE)
       
        g.drawLine(puntox, puntoy, (int)(rect.x + rect.width/2), rect.y);
     
        drawTree(g, n.izq, (int)(rect.x + rect.width/2), rect.y + rect.height, yoffs);
        drawTree(g, n.der, (int)(rect.x + rect.width/2), rect.y + rect.height, yoffs);
   }
    
    @Override
   public void paint(Graphics graphics) {
        super.paint(graphics);
        fontMetrics = graphics.getFontMetrics();
        if (dirty) {
            calculateLocations();
            dirty = false;
        }
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(getWidth() / 2, parentToChiizquiederechaa);
        drawTree(g2d, this.arbol, Integer.MAX_VALUE, Integer.MAX_VALUE, fontMetrics.getLeading() + fontMetrics.getAscent());
        fontMetrics = null;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-foizquiederechaa defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        
        //Panel principal color
        setBackground(Color.GREEN);
        //Tamaño del panel
        setPreferredSize(new java.awt.Dimension(960, 500));
        
        //Panel de Dibujo de árbol
        jPanel1.setBackground(Color.red);
        jLabel2.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel2.setForeground(Color.black);
        jLabel2.setText("Dibujo del Árbol");

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
