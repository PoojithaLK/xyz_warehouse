package Assets;

import Assets.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

public class RegisterEmployee extends javax.swing.JInternalFrame {

    Connection conn = null;
    Statement st = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
   
    int i_no = 0;
   

    public RegisterEmployee() {
        initComponents();

        try {
           
            conn = DatabaseConnection.connectToDb();
            st = (Statement) conn.createStatement();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        UpdateTable();
        IncEmpNo();

    }

    private void clearFields() {
        emp_id.setText("");
        license_no.setText("");
        Name.setText("");
        cmb_position.setSelectedIndex(0);
    }

    private void UpdateTable() {
        try {
            String sql = "SELECT * FROM employees";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            users_table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException ex) {
            Logger.getLogger(RegisterEmployee.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     private void IncEmpNo() {
        try {
            String sql = "select max(emp_id) from employees";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            rs.next();
            if (rs != null) {
                i_no = rs.getInt(1);
                i_no++;
            } else {
                i_no = 1;
            }

            emp_id.setText("" + i_no);
            emp_id.setEditable(false);
        } catch (SQLException ex) {
            Logger.getLogger(Issue_Product.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        scrollPane1 = new java.awt.ScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        users_table = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        Name = new javax.swing.JTextField();
        emp_id = new javax.swing.JTextField();
        license_no = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cmb_position = new javax.swing.JComboBox<>();

        jButton1.setText("jButton1");
        jButton1.setName("jButton1"); // NOI18N

        setBackground(new java.awt.Color(0, 0, 0));
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel3.setName("jPanel3"); // NOI18N

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));
        jPanel4.setName("jPanel4"); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "USERS", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 153, 0))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N

        scrollPane1.setName("scrollPane1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        users_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        users_table.setName("users_table"); // NOI18N
        jScrollPane1.setViewportView(users_table);

        scrollPane1.add(jScrollPane1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(scrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 153, 0));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("EMPLOYEE Registration");
        jLabel10.setName("jLabel10"); // NOI18N

        jPanel5.setBackground(new java.awt.Color(188, 155, 122));
        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel5.setName("jPanel5"); // NOI18N
        jPanel5.setLayout(null);

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 11)); // NOI18N
        jLabel12.setText("Emp ID:");
        jLabel12.setName("jLabel12"); // NOI18N
        jPanel5.add(jLabel12);
        jLabel12.setBounds(42, 61, 41, 13);

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 11)); // NOI18N
        jLabel14.setText("License No.(Optional)");
        jLabel14.setName("jLabel14"); // NOI18N
        jPanel5.add(jLabel14);
        jLabel14.setBounds(42, 101, 120, 13);

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 11)); // NOI18N
        jLabel15.setText("Name:");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });
        jPanel5.add(jLabel15);
        jLabel15.setBounds(42, 21, 34, 13);

        Name.setName("Name"); // NOI18N
        Name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NameKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                NameKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                NameKeyTyped(evt);
            }
        });
        jPanel5.add(Name);
        Name.setBounds(168, 15, 222, 22);

        emp_id.setName("emp_id"); // NOI18N
        jPanel5.add(emp_id);
        emp_id.setBounds(168, 55, 220, 22);

        license_no.setName("license_no"); // NOI18N
        jPanel5.add(license_no);
        license_no.setBounds(168, 95, 220, 22);

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/10_1.PNG"))); // NOI18N
        jButton4.setText("SAVE");
        jButton4.setName("jButton4"); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton4);
        jButton4.setBounds(168, 215, 83, 29);

        jButton6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/7.PNG"))); // NOI18N
        jButton6.setText("Clear Fields");
        jButton6.setName("jButton6"); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton6);
        jButton6.setBounds(269, 216, 121, 27);

        jLabel1.setText("Position:");
        jLabel1.setName("jLabel1"); // NOI18N
        jPanel5.add(jLabel1);
        jLabel1.setBounds(40, 140, 49, 16);

        cmb_position.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Position-", "Engineer", "Manager", "Driver", "Foreman", "Groundsman", "Intern", " " }));
        cmb_position.setName("cmb_position"); // NOI18N
        jPanel5.add(cmb_position);
        cmb_position.setBounds(170, 140, 222, 22);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel15MouseClicked

    private void NameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NameKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NameKeyPressed

    private void NameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NameKeyReleased

    }//GEN-LAST:event_NameKeyReleased

    private void NameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NameKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_NameKeyTyped

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (emp_id.getText().toUpperCase().isEmpty() == true) {
            JOptionPane.showMessageDialog(null, "Give The Name!");
        }  else if (cmb_position.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Select Appropriate Position!");
        }else if (cmb_position.getSelectedItem().equals("Driver")&&license_no.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "License No. Not optional for drivers!");
        } else {
            try {
                String sql1 = "select * from employees where Name='" + emp_id.getText().toUpperCase() + "' ";
                pst = conn.prepareStatement(sql1);
                rs = pst.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, " User Already Registered ");
                } else {
                    String sql2 = "insert into employees(Name,Emp_id,license,position)VALUES (?,?,?,?)";
                    pst = conn.prepareStatement(sql2);
                    pst.setString(1, Name.getText().toUpperCase());
                    pst.setString(2, emp_id.getText());
                    pst.setString(3, license_no.getText());
                    pst.setString(4, cmb_position.getSelectedItem().toString());
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Employee Successfully Registered");
                    UpdateTable();
                    clearFields();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {
                    pst.close();
                    rs.close();
                } catch (Exception e) {
                }
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        clearFields();
    }//GEN-LAST:event_jButton6ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Name;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cmb_position;
    private javax.swing.JTextField emp_id;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField license_no;
    private java.awt.ScrollPane scrollPane1;
    private javax.swing.JTable users_table;
    // End of variables declaration//GEN-END:variables

}
