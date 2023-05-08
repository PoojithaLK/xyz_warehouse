package Assets;

import static Assets.Warehouse_Main.txt_dated;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.Element;
import java.awt.Font;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.proteanit.sql.DbUtils;

public class Reports1 extends javax.swing.JInternalFrame {
private Vector<Vector<String>> data;
    private Vector<Vector<String>> data1;
    private Vector<String> header;
    int tot = 0;
    
    Connection conn = null;
    Statement st = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    int i_no = 0;
    int c_no = 000;

    public Reports1() {
        initComponents();
        
        
//        txt_DateReceived.setMaxSelectableDate(new java.util.Date());
//        txt_receivedBy.setText(CAMS_Main.user.getText());

        try {
            
            conn = DatabaseConnection.connectToDb();
            st = (Statement) conn.createStatement();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }


private void generateReport1() {

        try {
            com.itextpdf.text.Document doc = new com.itextpdf.text.Document(PageSize.A4);

            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("Report.pdf"));

            doc.open();

            com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance("LOG.png");

            img.scaleAbsolute(70, 70);
            img.setAlignment(Image.ALIGN_CENTER);
            doc.add(img);

            Paragraph p;
            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date1 = ((JTextField) txt_dated.getDateEditor().getUiComponent()).getText();
            // Paragraph p;
            p = new Paragraph("XYZ WAREHOUSE", FontFactory.getFont(FontFactory.TIMES_BOLD, 12));
            p.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            doc.add(p);
            p = new Paragraph("Management Information System", FontFactory.getFont(FontFactory.TIMES_BOLD, 18));
            p.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            doc.add(p);
            p = new Paragraph("P.O. Box 19-20400 XYZ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12));
            p.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            doc.add(p);
            p = new Paragraph(((JTextField) txt_dated.getDateEditor().getUiComponent()).getText(), FontFactory.getFont(FontFactory.TIMES_BOLD, 12));
            p.setAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            doc.add(p);

            //doc.addTitle(head);
            p = new com.itextpdf.text.Paragraph("ASSET ISSUE RECORDS", FontFactory.getFont(FontFactory.TIMES_BOLD, 14, Font.BOLD, BaseColor.RED));
            p.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            doc.add(p);

            doc.add(new com.itextpdf.text.Paragraph("=========================================================================="));

            p = new Paragraph("Department.....III................     Branch....Store..............     Unit....NETWORKS...........\n\n", FontFactory.getFont(FontFactory.TIMES_BOLD, 12));
            p.setAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            doc.add(p);

            p = new Paragraph("Being Assets Issued Between Date: " + ((JTextField) date_from_Report.getDateEditor().getUiComponent()).getText() + "       To: " + ((JTextField) date_to_Report.getDateEditor().getUiComponent()).getText() + "\n\n", FontFactory.getFont(FontFactory.COURIER, 12));
            p.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            doc.add(p);

            PdfPTable table = new PdfPTable(8);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);
            table.setSpacingAfter(10);
            //table.setWidths(new int[]{3, 6, 3});
            PdfPCell hd = new PdfPCell(new com.itextpdf.text.Paragraph("Issue Details"));
            hd.setColspan(8);
            hd.setHorizontalAlignment(Element.ALIGN_CENTER);
            hd.setBackgroundColor(BaseColor.BLUE);
            hd.setFixedHeight(20);
            table.addCell(hd);

            table.addCell("Issue No");
            table.addCell("Issued By");
            table.addCell("Issuer Designation");
            table.addCell("Date Issued");
            table.addCell("Exp. Return Date");
            table.addCell("Received By");
            table.addCell("Receiver Designation");
            table.addCell("Total Items");

            String date2 = ((JTextField) date_from_Report.getDateEditor().getUiComponent()).getText();
            String date3 = ((JTextField) date_to_Report.getDateEditor().getUiComponent()).getText();

            String sql1 = "select * from issue where Date_issued between '" + date2 + "' AND '" + date3 + "'";
            pst = conn.prepareStatement(sql1);
            rs = pst.executeQuery();
            while (rs.next()) {

                java.util.Date tarehe;
                tarehe = rs.getDate("Date_issued");

                table.addCell(rs.getString("issue_no"));
                table.addCell(rs.getString("Issued_By"));
                table.addCell(rs.getString("issuer_designation"));
                table.addCell(tarehe.toString());
                table.addCell(rs.getDate("Return_date").toString());
                table.addCell(rs.getString("Received_by"));
                table.addCell(rs.getString("Receiver_designation"));
                table.addCell(rs.getString("total_items"));

            }
            PdfPCell fd = new PdfPCell(new com.itextpdf.text.Paragraph("TOTAL Items"));
            fd.setColspan(7);
            fd.setHorizontalAlignment(Element.ALIGN_LEFT);
            fd.setBackgroundColor(BaseColor.BLUE);
            table.addCell(fd);
            table.addCell("#");
            doc.add(table);

            JOptionPane.showMessageDialog(null, "Report Saved");
            openReport2();

            doc.close();
        } catch (DocumentException | IOException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void openReport2() {
        try {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + "Report.pdf");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    
    
private void generateReport() {

        try {
            com.itextpdf.text.Document doc = new com.itextpdf.text.Document(PageSize.A4);

            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("AllReport.pdf"));

            doc.open();

            com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance("LOG.png");

            img.scaleAbsolute(70, 70);
            img.setAlignment(Image.ALIGN_CENTER);
            doc.add(img);

            Paragraph p;
            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date1 = ((JTextField) txt_dated.getDateEditor().getUiComponent()).getText();
            // Paragraph p;
            p = new Paragraph("XYZ WAREHOUSE", FontFactory.getFont(FontFactory.TIMES_BOLD, 12));
            p.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            doc.add(p);
            p = new Paragraph("Management Information System", FontFactory.getFont(FontFactory.TIMES_BOLD, 18));
            p.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            doc.add(p);
            p = new Paragraph("P.O. Box 19-20400 XYZ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12));
            p.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            doc.add(p);
            p = new Paragraph(((JTextField) txt_dated.getDateEditor().getUiComponent()).getText(), FontFactory.getFont(FontFactory.TIMES_BOLD, 12));
            p.setAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            doc.add(p);

            //doc.addTitle(head);
            p = new com.itextpdf.text.Paragraph("ASSET RECORDS", FontFactory.getFont(FontFactory.TIMES_BOLD, 14, Font.BOLD, BaseColor.RED));
            p.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            doc.add(p);

            doc.add(new com.itextpdf.text.Paragraph("=========================================================================="));

            p = new Paragraph("Department.....III................     Branch....Store..............     Unit....NETWORKS...........\n\n", FontFactory.getFont(FontFactory.TIMES_BOLD, 12));
            p.setAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            doc.add(p);

            p = new Paragraph("Being Assets Issued Between Date: " + ((JTextField) date_from_Report.getDateEditor().getUiComponent()).getText() + "       To: " + ((JTextField) date_to_Report.getDateEditor().getUiComponent()).getText() + "\n\n", FontFactory.getFont(FontFactory.COURIER, 12));
            p.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
           // doc.add(p);

            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);
            table.setSpacingAfter(10);
            //table.setWidths(new int[]{3, 6, 3});
            PdfPCell hd = new PdfPCell(new com.itextpdf.text.Paragraph("All Assets Registered"));
            hd.setColspan(7);
            hd.setHorizontalAlignment(Element.ALIGN_CENTER);
            hd.setBackgroundColor(BaseColor.BLUE);
            hd.setFixedHeight(20);
            table.addCell(hd);

            table.addCell("ASSET No");
            table.addCell("ASSET NAME");
            table.addCell("MODEL");
            table.addCell("Model No.");
            table.addCell("SERIAL No.");
            table.addCell("DATE RECEIVED");
            table.addCell("A. CONDITION");

            String date2 = ((JTextField) date_from_Report.getDateEditor().getUiComponent()).getText();
            String date3 = ((JTextField) date_to_Report.getDateEditor().getUiComponent()).getText();

            String sql1 = "select * from hardware";
            pst = conn.prepareStatement(sql1);
            rs = pst.executeQuery();
            while (rs.next()) {

                java.util.Date tarehe;
                tarehe = rs.getDate("Date_received");

                table.addCell(rs.getString("asset_no"));
                table.addCell(rs.getString("asset_name"));
                table.addCell(rs.getString("model"));
                table.addCell(rs.getString("model_no"));
                table.addCell(rs.getString("serial_no"));
                table.addCell(tarehe.toString());                
                table.addCell(rs.getString("asset_condition"));

            }
            PdfPCell fd = new PdfPCell(new com.itextpdf.text.Paragraph("TOTAL Items"));
            fd.setColspan(6);
            fd.setHorizontalAlignment(Element.ALIGN_LEFT);
            fd.setBackgroundColor(BaseColor.BLUE);
            table.addCell(fd);
            table.addCell("#");
            doc.add(table);

            JOptionPane.showMessageDialog(null, "Report Saved");
            openReport();

            doc.close();
        } catch (DocumentException | IOException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void openReport() {
        try {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + "AllReport.pdf");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jButton1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        date_from_Report = new com.toedter.calendar.JDateChooser();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        date_to_Report = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        jButton13 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        searchreport_Table2 = new javax.swing.JTable();

        jButton1.setText("jButton1");
        jButton1.setName("jButton1"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        setBackground(new java.awt.Color(0, 0, 0));
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel3.setName("jPanel3"); // NOI18N

        jPanel8.setBackground(new java.awt.Color(230, 230, 251));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Reports", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Modern No. 20", 1, 18))); // NOI18N
        jPanel8.setName("jPanel8"); // NOI18N

        jPanel14.setBackground(new java.awt.Color(188, 155, 122));
        jPanel14.setName("jPanel14"); // NOI18N

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 47, Short.MAX_VALUE)
        );

        jPanel15.setBackground(new java.awt.Color(188, 155, 122));
        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 255)), "Please Select Appropriate Details", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Monotype Corsiva", 1, 18))); // NOI18N
        jPanel15.setName("jPanel15"); // NOI18N

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 255)), "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));
        jPanel13.setName("jPanel13"); // NOI18N

        jLabel36.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("Assets Report");
        jLabel36.setName("jLabel36"); // NOI18N

        jLabel14.setText("Issue Date FROM:");
        jLabel14.setName("jLabel14"); // NOI18N

        date_from_Report.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 255)));
        date_from_Report.setForeground(new java.awt.Color(204, 0, 0));
        date_from_Report.setDateFormatString("yyyy-MM-dd");
        date_from_Report.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 14)); // NOI18N
        date_from_Report.setMinSelectableDate(null);
        date_from_Report.setName("date_from_Report"); // NOI18N
        date_from_Report.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                date_from_ReportKeyReleased(evt);
            }
        });

        jButton11.setText("Generate Report");
        jButton11.setName("jButton11"); // NOI18N
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setText("Search");
        jButton12.setName("jButton12"); // NOI18N
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        date_to_Report.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 255)));
        date_to_Report.setForeground(new java.awt.Color(204, 0, 0));
        date_to_Report.setDateFormatString("yyyy-MM-dd");
        date_to_Report.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 14)); // NOI18N
        date_to_Report.setMinSelectableDate(null);
        date_to_Report.setName("date_to_Report"); // NOI18N
        date_to_Report.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                date_to_ReportKeyReleased(evt);
            }
        });

        jLabel16.setText("TO:");
        jLabel16.setName("jLabel16"); // NOI18N

        jButton13.setText("ALL ASSETS REGISTERED");
        jButton13.setName("jButton13"); // NOI18N
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addComponent(date_from_Report, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(date_to_Report, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(442, 442, 442))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(532, 532, 532)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(618, 618, 618))))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(date_from_Report, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(date_to_Report, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton12)
                    .addComponent(jButton13))
                .addGap(18, 18, 18)
                .addComponent(jButton11)
                .addContainerGap())
        );

        jScrollPane5.setName("jScrollPane5"); // NOI18N

        searchreport_Table2.setBackground(java.awt.Color.gray);
        searchreport_Table2.setForeground(new java.awt.Color(102, 255, 102));
        searchreport_Table2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        searchreport_Table2.setName("searchreport_Table2"); // NOI18N
        jScrollPane5.setViewportView(searchreport_Table2);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void date_from_ReportKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_date_from_ReportKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_date_from_ReportKeyReleased

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        if (date_to_Report.getDate().equals(null)) {
            JOptionPane.showMessageDialog(null, "Make Sure All Fields has the neede data");
        } else {
            generateReport1();
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        if (date_from_Report.getDate().equals(null)) {
            JOptionPane.showMessageDialog(null, "Select Appropriate Date");
        } else if (date_to_Report.getDate().equals(null)) {
            JOptionPane.showMessageDialog(null, "Select Appropriate Date");
        } else {
            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date1 = ((JTextField) date_from_Report.getDateEditor().getUiComponent()).getText();
            String date2 = ((JTextField) date_to_Report.getDateEditor().getUiComponent()).getText();

            try {

                String sql1 = "select * from issue where Date_issued Between '" + date1 + "' AND '" + date2 + "'";
                pst = conn.prepareStatement(sql1);
                rs = pst.executeQuery();
                searchreport_Table2.setModel(DbUtils.resultSetToTableModel(rs));
                if (rs.next()) {

                    searchreport_Table2.setVisible(true);

                } else {

                    searchreport_Table2.setVisible(true);
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
    }//GEN-LAST:event_jButton12ActionPerformed

    private void date_to_ReportKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_date_to_ReportKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_date_to_ReportKeyReleased

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
       generateReport();
    }//GEN-LAST:event_jButton13ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    public com.toedter.calendar.JDateChooser date_from_Report;
    public com.toedter.calendar.JDateChooser date_to_Report;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable searchreport_Table2;
    // End of variables declaration//GEN-END:variables

}
