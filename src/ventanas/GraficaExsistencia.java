/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas;

import clases.Conexiones;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import static ventanas.Inicio.producto;

/**
 *
 * @author MINEDUCYT
 */
public class GraficaExsistencia extends javax.swing.JFrame {

    int SI = 0, No = 0;
    String[] Estatus = new String[5];
    int[] Count = new int[5];
    DefaultTableModel model = new DefaultTableModel();

    /**
     * Creates new form GrafiaEstatus
     */
    public GraficaExsistencia() {
        initComponents();

        setResizable(false);
        setTitle("Grafica de Exsistencias");
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        ImageIcon Wallpaper = new ImageIcon("src/images/Wallpaper2.jpg");
        Icon icono = new ImageIcon(Wallpaper.getImage().getScaledInstance(LabelWallpaper.getWidth(),
                LabelWallpaper.getHeight(), Image.SCALE_DEFAULT));

        LabelWallpaper.setIcon(icono);
        this.repaint();

        try {
            Connection cn = Conexiones.conexion();
            PreparedStatement pst = cn.prepareStatement(
                    "select Existencias, count(Existencias) as Existencias from articulos group by Existencias;");
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int posicion = 0;
                do {
                    Estatus[posicion] = rs.getString(1);
                    Count[posicion] = rs.getInt(2);

                    if (Estatus[posicion].equals("Si Tenemos")) {
                        SI = Count[posicion];
                    } else if (Estatus[posicion].equals("No Tenemos")) {
                        No = Count[posicion];
                    }
                    posicion++;
                } while (rs.next());
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        repaint();
        try {

            Connection cn2 = Conexiones.conexion();
            PreparedStatement pst2 = cn2.prepareStatement(
                    "select ID, Nombre, Marca, Precio, Existencias from articulos where Existencias = 'No Tenemos'");

            ResultSet rs2 = pst2.executeQuery();

            Table = new JTable(model);
            jScrollPane1.setViewportView(Table);

            model.addColumn("ID");
            model.addColumn("Nombre");
            model.addColumn("Marca");
            model.addColumn("Precio");
            model.addColumn("En existencias");

            while (rs2.next()) {
                Object[] fila = new Object[5];

                for (int i = 0; i < 5; i++) {
                    fila[i] = rs2.getObject(i + 1);

                }
                model.addRow(fila);
            }
            Table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int Filap = Table.rowAtPoint(e.getPoint());
                    int Columna = 0;
                    if (Filap >= -1) {
                        try {
                            producto = (int) model.getValueAt(Filap, Columna);

                            new InformacionProducto().setVisible(true);

                        } catch (Exception ex) {
                            System.out.println(ex);
                        }

                    }
                }
            });
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource(
                "images/iconoapp.png"));
        return retValue;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        LabelWallpaper = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("No Tenemos:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, -1, -1));

        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(Table);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 530, 180));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Grafica de Estatus");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));
        getContentPane().add(LabelWallpaper, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 550, 450));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GraficaExsistencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GraficaExsistencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GraficaExsistencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GraficaExsistencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GraficaExsistencia().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelWallpaper;
    private javax.swing.JTable Table;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    public void paint(Graphics g) {
        super.paint(g);

        int Mayor = SI + No;

        int largo3 = Mayor * 350 / Mayor;
        int largo1 = SI * 350 / Mayor;
        int largo2 = No * 350 / Mayor;

        g.setColor(Color.black);
        g.fillRect(100, 100, largo3, 0);

        g.setColor(Color.GREEN);
        g.fillRect(100, 150, largo1, 40);
        g.setColor(Color.white);
        g.drawString("Si Tenemos", 10, 168);
        g.drawString("Cantidad: " + SI, 10, 183);

        g.setColor(Color.RED);
        g.fillRect(100, 200, largo2, 40);
        g.setColor(Color.white);
        g.drawString("No Tenemos", 10, 218);
        g.drawString("Cantidad: " + No, 10, 233);

    }
}