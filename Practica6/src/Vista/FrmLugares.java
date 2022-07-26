/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista;

import Controlador.LugarController.DatosLugares;
import Controlador.LugarController.DatosUbicacion;
import Controlador.LugarController.LugarController;
import Controlador.TDA_Lista.ListaEnlazada;
import Vista.Tablas.ModeloTablaLugar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author HMCC
 */
public class FrmLugares extends javax.swing.JFrame {

    LugarController lugarCtrl = new LugarController();
    private ModeloTablaLugar mtl = new ModeloTablaLugar();
    DatosLugares dl = new DatosLugares();
    DatosUbicacion du = new DatosUbicacion();

    /**
     * Creates new form FrmLugares
     */
    public FrmLugares() {
        initComponents();
        btnModificar.setEnabled(false);
    }

    private void crear() {
        if (txtNombre.getText().trim().length() == 0 || txtLongitud.getText().trim().length() == 0
                || txtLatitud.getText().trim().length() == 0 || txtTipo.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Llene todos los datos", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            lugarCtrl.getLugar().setNombre(txtNombre.getText());
            lugarCtrl.getLugar().setTipoLugar(txtTipo.getText());
            lugarCtrl.getLugar().getUbicacion().setLatitud(Double.parseDouble(txtLatitud.getText()));
            lugarCtrl.getLugar().getUbicacion().setLongitud(Double.parseDouble(txtLongitud.getText()));

            cargarTabla();

            du = new DatosUbicacion(lugarCtrl.getLugar().getUbicacion().getLongitud(), lugarCtrl.getLugar().getUbicacion().getLatitud());
            dl = new DatosLugares(lugarCtrl.getLugar().getNombre(), du, lugarCtrl.getLugar().getTipoLugar());
            lugarCtrl.agregarGrafo(dl);
            cargarComboVertice();
            limpiar();
            String[] aux = {""};
            jList.setListData(aux);
            String[] aux1 = {""};
            JListAnchura.setListData(aux1);
            String[] aux2 = {""};
            JListProfundidad.setListData(aux2);
        }
    }

    private void cargarComboVertice() {
        cbxOrigen.removeAllItems();
        cbxDestino.removeAllItems();
        try {
            for (int i = 1; i <= lugarCtrl.getGrafoLugar().numVertices(); i++) {
                cbxOrigen.addItem(lugarCtrl.getGrafoLugar().obtenerEtiqueta(i).toString());
                cbxDestino.addItem(lugarCtrl.getGrafoLugar().obtenerEtiqueta(i).toString());
            }
        } catch (Exception e) {
            System.out.println("ERROR EN CARGAR COMBO");
        }
    }

    private void limpiar() {
        txtLatitud.setText("");
        txtLongitud.setText("");
        txtNombre.setText("");
        txtTipo.setText("");

    }

    private void adyacencia() {
        Integer origen = (cbxOrigen.getSelectedIndex() + 1);
        Integer destino = (cbxDestino.getSelectedIndex() + 1);

        if (origen == destino) {
            JOptionPane.showMessageDialog(null, "ESCOJA LUGARES DIFERENTES", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                Double distancia = lugarCtrl.calcularDistancia(lugarCtrl.getGrafoLugar().obtenerEtiqueta(origen), lugarCtrl.getGrafoLugar().obtenerEtiqueta(destino));
                lugarCtrl.getGrafoLugar().insertarAristaE(lugarCtrl.getGrafoLugar().obtenerEtiqueta(origen), lugarCtrl.getGrafoLugar().obtenerEtiqueta(destino), distancia);
                System.out.println("----------------");
                System.out.println(lugarCtrl.getGrafoLugar().toString());
                System.out.println("----------------");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cargarTabla() {
        mtl.setGrafoEND(lugarCtrl.getGrafoLugar());
        mtl.fireTableStructureChanged();
        mtl.fireTableDataChanged();
        tblTablaLugares.setModel(mtl);
        tblTablaLugares.updateUI();
        System.out.println(lugarCtrl.getGrafoLugar().toString());
    }

    private void modificar() {
        if (txtNombre.getText().trim().length() == 0 || txtLongitud.getText().trim().length() == 0 || txtLatitud.getText().trim().length() == 0 || txtTipo.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Datos incompletos para modificar", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                Integer pos = tblTablaLugares.getSelectedRow();
                lugarCtrl.getLugar().setNombre(txtNombre.getText());
                lugarCtrl.getLugar().setTipoLugar(txtTipo.getText());
                lugarCtrl.getLugar().getUbicacion().setLatitud(Double.parseDouble(txtLatitud.getText()));
                lugarCtrl.getLugar().getUbicacion().setLongitud(Double.parseDouble(txtLongitud.getText()));
                if (lugarCtrl.getGrafoLugar().modificar(lugarCtrl.getGrafoLugar().obtenerEtiqueta(pos), lugarCtrl.getLugar())) {
                    cargarComboVertice();
                    cargarTabla();
                    limpiar();
                    btnModificar.setEnabled(false);
                    JOptionPane.showMessageDialog(null, "Modificado con éxito", "OK", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "NO SE PUDO MODIFICAR", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cargarVista() {
        Integer fila = -1;
        fila = tblTablaLugares.getSelectedRow();
        if (fila >= 0) {
            try {
                lugarCtrl.setLugar(lugarCtrl.getGrafoLugar().obtenerEtiqueta(fila + 1));
                txtNombre.setText(lugarCtrl.getLugar().getNombre());
                txtTipo.setText(lugarCtrl.getLugar().getTipoLugar());
                txtLatitud.setText(String.valueOf(lugarCtrl.getLugar().getUbicacion().getLatitud()));
                txtLongitud.setText(String.valueOf(lugarCtrl.getLugar().getUbicacion().getLongitud()));
                btnModificar.setEnabled(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Escoja una fila de la tabla", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void busquedaAnchura() {
        Integer origen = (cbxOrigen.getSelectedIndex() + 1);
        try {
            Integer[] lista = lugarCtrl.getGrafoLugar().busquedaAnchura(origen);
            String[] aux = new String[lista.length];
            for (int i = 0; i < lista.length; i++) {
               if (lista[i]==null) {
                    continue;
                } else {
                    aux[i] = lugarCtrl.getGrafoLugar().obtenerEtiqueta(lista[i]).toString();
                }
            }
            JListAnchura.setListData(aux);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }
    
    private void busquedaProfundidad() {
        Integer origen = (cbxOrigen.getSelectedIndex() + 1);
        try {
            Integer[] lista = lugarCtrl.getGrafoLugar().busquedaProfundidad(origen);
            String[] aux = new String[lista.length];
            for (int i = 0; i < lista.length; i++) {
                if (lista[i]==null) {
                    continue;
                } else {
                    aux[i] = lugarCtrl.getGrafoLugar().obtenerEtiqueta(lista[i]).toString();
                }
                
            }
            JListProfundidad.setListData(aux);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void camino() {
        Integer origen = (cbxOrigen.getSelectedIndex() + 1);
        Integer destino = (cbxDestino.getSelectedIndex() + 1);

        if (origen == destino) {
            JOptionPane.showMessageDialog(null, "Elija lugares diferentes", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                ListaEnlazada<Integer> lista = lugarCtrl.getGrafoLugar().floyd(origen, destino);
                String[] aux = new String[lista.getSize()];
                for (int i = 0; i < lista.getSize(); i++) {
                    aux[i] = lugarCtrl.getGrafoLugar().obtenerEtiqueta(lista.obtenerDato(i)).toString();
                }
                jList.setListData(aux);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtLatitud = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtTipo = new javax.swing.JTextField();
        txtLongitud = new javax.swing.JTextField();
        btnRegistrar = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTablaLugares = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cbxDestino = new javax.swing.JComboBox<>();
        cbxOrigen = new javax.swing.JComboBox<>();
        btnVincular = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        btnCamino = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jList = new javax.swing.JList<>();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        btnProfundidad = new javax.swing.JButton();
        btnBusquedaAnchura = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        JListProfundidad = new javax.swing.JList<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        JListAnchura = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        btnModificar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(31, 40, 50));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Franklin Gothic Demi", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("REGISTRO DE LUGARES");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(40, 130, 200, 21);

        jLabel3.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Latitud:");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(40, 350, 70, 16);

        jLabel4.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Nombre:");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(20, 180, 50, 16);

        jLabel5.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Tipo:");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(20, 220, 50, 16);

        jLabel6.setFont(new java.awt.Font("Myanmar Text", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Ubicación");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(20, 260, 70, 28);

        jLabel7.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Longitud:");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(40, 310, 70, 16);
        jPanel1.add(txtLatitud);
        txtLatitud.setBounds(100, 340, 170, 30);
        jPanel1.add(txtNombre);
        txtNombre.setBounds(80, 170, 190, 30);
        jPanel1.add(txtTipo);
        txtTipo.setBounds(80, 210, 190, 30);
        jPanel1.add(txtLongitud);
        txtLongitud.setBounds(100, 300, 170, 30);

        btnRegistrar.setText("REGISTRAR");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnRegistrar);
        btnRegistrar.setBounds(160, 400, 100, 22);

        jLabel16.setFont(new java.awt.Font("Calibri Light", 0, 10)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Realizado por: Hilary Madeley Calva Camacho");
        jPanel1.add(jLabel16);
        jLabel16.setBounds(10, 470, 250, 16);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 280, 530);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(null);

        tblTablaLugares.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tblTablaLugares.setModel(new javax.swing.table.DefaultTableModel(
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
        tblTablaLugares.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTablaLugaresMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblTablaLugares);

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(20, 60, 550, 120);

        jLabel8.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(23, 35, 47));
        jLabel8.setText("LUGARES REGISTRADOS");
        jPanel2.add(jLabel8);
        jLabel8.setBounds(20, 20, 180, 19);

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(6, 6, 40));
        jLabel9.setText("Punto de destino");
        jPanel2.add(jLabel9);
        jLabel9.setBounds(250, 230, 100, 16);

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(6, 6, 40));
        jLabel10.setText("Punto de origen");
        jPanel2.add(jLabel10);
        jLabel10.setBounds(30, 230, 100, 16);

        cbxDestino.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cbxDestino);
        cbxDestino.setBounds(350, 230, 110, 20);

        cbxOrigen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cbxOrigen);
        cbxOrigen.setBounds(130, 230, 110, 20);

        btnVincular.setText("Vincular");
        btnVincular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVincularActionPerformed(evt);
            }
        });
        jPanel2.add(btnVincular);
        btnVincular.setBounds(480, 230, 73, 22);

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setLayout(null);

        jLabel11.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(18, 29, 39));
        jLabel11.setText("CAMINOS PARA LLEGAR");
        jPanel3.add(jLabel11);
        jLabel11.setBounds(50, 10, 180, 19);

        btnCamino.setText("CAMINO");
        btnCamino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCaminoActionPerformed(evt);
            }
        });
        jPanel3.add(btnCamino);
        btnCamino.setBounds(100, 40, 90, 22);

        jList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane5.setViewportView(jList);

        jPanel3.add(jScrollPane5);
        jScrollPane5.setBounds(60, 80, 170, 146);

        jPanel2.add(jPanel3);
        jPanel3.setBounds(0, 270, 280, 260);

        jPanel4.setBackground(new java.awt.Color(183, 183, 228));
        jPanel4.setLayout(null);

        jLabel12.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(18, 29, 39));
        jLabel12.setText("PROFUNDIDAD");
        jPanel4.add(jLabel12);
        jLabel12.setBounds(170, 30, 110, 19);

        btnProfundidad.setText("BUSCAR");
        btnProfundidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProfundidadActionPerformed(evt);
            }
        });
        jPanel4.add(btnProfundidad);
        btnProfundidad.setBounds(190, 60, 80, 22);

        btnBusquedaAnchura.setText("BUSCAR");
        btnBusquedaAnchura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBusquedaAnchuraActionPerformed(evt);
            }
        });
        jPanel4.add(btnBusquedaAnchura);
        btnBusquedaAnchura.setBounds(40, 60, 80, 22);

        jLabel13.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(18, 29, 39));
        jLabel13.setText("BUSQUEDA POR ");
        jPanel4.add(jLabel13);
        jLabel13.setBounds(170, 10, 120, 19);

        jLabel14.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(18, 29, 39));
        jLabel14.setText("BUSQUEDA POR ");
        jPanel4.add(jLabel14);
        jLabel14.setBounds(20, 10, 120, 19);

        jLabel15.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(18, 29, 39));
        jLabel15.setText("ANCHURA");
        jPanel4.add(jLabel15);
        jLabel15.setBounds(40, 30, 80, 19);

        JListProfundidad.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(JListProfundidad);

        jPanel4.add(jScrollPane2);
        jScrollPane2.setBounds(170, 100, 120, 120);

        JListAnchura.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane6.setViewportView(JListAnchura);

        jPanel4.add(jScrollPane6);
        jScrollPane6.setBounds(20, 100, 120, 120);

        jPanel2.add(jPanel4);
        jPanel4.setBounds(280, 270, 320, 260);

        jLabel2.setText("Para modificar un lugar registrado seleccione la fila de la tabla");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(20, 40, 340, 16);

        btnModificar.setText("MODIFICAR");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        jPanel2.add(btnModificar);
        btnModificar.setBounds(470, 190, 100, 22);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(280, 0, 600, 530);

        setSize(new java.awt.Dimension(886, 536));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        crear();
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnVincularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVincularActionPerformed
        adyacencia();
    }//GEN-LAST:event_btnVincularActionPerformed

    private void tblTablaLugaresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTablaLugaresMouseClicked
        cargarVista();
    }//GEN-LAST:event_tblTablaLugaresMouseClicked

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        modificar();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnCaminoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCaminoActionPerformed
        camino();
    }//GEN-LAST:event_btnCaminoActionPerformed

    private void btnBusquedaAnchuraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBusquedaAnchuraActionPerformed
        busquedaAnchura();
    }//GEN-LAST:event_btnBusquedaAnchuraActionPerformed

    private void btnProfundidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfundidadActionPerformed
        busquedaProfundidad();
    }//GEN-LAST:event_btnProfundidadActionPerformed

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
            java.util.logging.Logger.getLogger(FrmLugares.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmLugares.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmLugares.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmLugares.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmLugares().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> JListAnchura;
    private javax.swing.JList<String> JListProfundidad;
    private javax.swing.JButton btnBusquedaAnchura;
    private javax.swing.JButton btnCamino;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnProfundidad;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnVincular;
    private javax.swing.JComboBox<String> cbxDestino;
    private javax.swing.JComboBox<String> cbxOrigen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable tblTablaLugares;
    private javax.swing.JTextField txtLatitud;
    private javax.swing.JTextField txtLongitud;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTipo;
    // End of variables declaration//GEN-END:variables
}
