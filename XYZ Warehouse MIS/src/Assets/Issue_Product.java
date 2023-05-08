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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import net.proteanit.sql.DbUtils;

public class Issue_Product extends javax.swing.JInternalFrame {

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

    public Issue_Product() {
        initComponents();

//        txt_DateReceived.setMaxSelectableDate(new java.util.Date());
//        txt_receivedBy.setText(CAMS_Main.user.getText());
        try {

            conn = DatabaseConnection.connectToDb();
            st = (Statement) conn.createStatement();
            IncCodeNo();
            IncIssueNo();
            setAvaillableAssets();
            FillPosComboAll();
            FillEmpComboAll();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void IncIssueNo() {
        try {
            String sql = "select max(issue_no) from orders";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            rs.next();
            if (rs != null) {
                i_no = rs.getInt(1);
                i_no++;
            } else {
                i_no = 1;
            }

            issue_no.setText("" + i_no);
            issue_no.setEditable(false);
        } catch (SQLException ex) {
            Logger.getLogger(Issue_Product.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void IncCodeNo() {
        try {
            String stat = "Active";
            String sql = "select max(code_no) from orders WHERE Status='" + stat + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            rs.next();
            if (rs != null) {
                c_no = rs.getInt(1);
                c_no++;
            } else {
                c_no = 1;
            }

            code_no.setText("" + c_no);
            code_no.setEditable(false);
        } catch (SQLException ex) {
            Logger.getLogger(Issue_Product.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setAvaillableAssets() {
        try {

            String stat = "Available";
            String sql = "select Asset_No,Asset_Name,Model,serial_no from hardware where Status='" + stat + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            sc.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception ex) {
            Logger.getLogger(Issue_Product.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void FillPosComboAll() {
        try {

            String sql = "SELECT DISTINCT position FROM users";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            String add;
            while (rs.next()) {

                add = rs.getString("position");

                //cmb_ReceiverDesignation.addItem(add);
                cmb_issuerDesignation.addItem(add);

            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    private void FillEmpComboAll() {
        try {

            String sql = "SELECT * FROM employees";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            String add;
            while (rs.next()) {

                cmb_ReceiverDesignation.addItem(rs.getString("position"));
                jComboBox1.addItem(rs.getString("name"));

            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    private void generateReport() {
        try {
            com.itextpdf.text.Document doc = new com.itextpdf.text.Document(PageSize.A4);

            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("Receipt.pdf"));

            doc.open();
            Paragraph p;
            p = new Paragraph("Form S13                                                                                     No." + issue_no.getText() + "\n", FontFactory.getFont(FontFactory.TIMES_BOLD, 12));
            p.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            doc.add(p);

            com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance("LOG.png");

            img.scaleAbsolute(70, 70);
            img.setAlignment(Image.ALIGN_CENTER);
            doc.add(img);

            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date1 = ((JTextField) txt_dated1.getDateEditor().getUiComponent()).getText();
            // Paragraph p;
            p = new Paragraph("XYZ WAREHOUSE", FontFactory.getFont(FontFactory.TIMES_BOLD, 12));
            p.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            doc.add(p);
            p = new Paragraph("Management Information System", FontFactory.getFont(FontFactory.TIMES_BOLD, 18));
            p.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            doc.add(p);
            p = new Paragraph("COUNTER RECEIPT VOUCHER \n P.O. Box 19-20400 XYZ", FontFactory.getFont(FontFactory.TIMES_BOLD, 12));
            p.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            doc.add(p);
            p = new Paragraph(((JTextField) txt_dated1.getDateEditor().getUiComponent()).getText(), FontFactory.getFont(FontFactory.TIMES_BOLD, 12));
            p.setAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            doc.add(p);

            //doc.addTitle(head);
            p = new com.itextpdf.text.Paragraph("COUNTER RECEIPT VOUCHER", FontFactory.getFont(FontFactory.TIMES_BOLD, 14, Font.BOLD, BaseColor.RED));
            p.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            doc.add(p);

            doc.add(new com.itextpdf.text.Paragraph("=========================================================================="));

            p = new Paragraph("Ministry.....III...................     Dept./Branch....BUILDING............     Unit....STRUCTURAL..........\n\n", FontFactory.getFont(FontFactory.TIMES_BOLD, 12));
            p.setAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            doc.add(p);

            p = new Paragraph("Receipt Details Saved As:                    Issue No.:  " + issue_no.getText() + "                           Date:  " + date1 + "\n\n", FontFactory.getFont(FontFactory.TIMES_BOLD, 12));
            p.setAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            doc.add(p);

            p = new Paragraph("Received the items listed below from (source)..........." + issuer.getText() + " (" + cmb_issuerDesignation.getSelectedItem() + ").....................................................\n\n", FontFactory.getFont(FontFactory.TIMES_BOLD, 12));
            p.setAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            doc.add(p);

            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);
            table.setSpacingAfter(10);
            table.setWidths(new int[]{3, 3, 5, 3, 4,3,5});
            PdfPCell hd = new PdfPCell(new com.itextpdf.text.Paragraph("Items Issued"));
            hd.setColspan(7);
            hd.setHorizontalAlignment(Element.ALIGN_CENTER);
            hd.setBackgroundColor(BaseColor.BLUE);
            hd.setFixedHeight(20);
            table.addCell(hd);

            table.addCell("Code No.");
            table.addCell("Asset No.");
            table.addCell("Item Name");
            table.addCell("Model");
            table.addCell("Serial No");
            table.addCell("No of Days");
            table.addCell("Exp. Return Date");

            String stat = "Active";

            String sql1 = "select * from orders where Issue_No='" + issue_no.getText().toUpperCase() + "' ";
            pst = conn.prepareStatement(sql1);
            rs = pst.executeQuery();
            while (rs.next()) {

                table.addCell(rs.getString("Code_No"));
                table.addCell(rs.getString("Asset_No"));
                table.addCell(rs.getString("Asset_Name"));
                table.addCell(rs.getString("Model"));
                table.addCell(rs.getString("Serial_no"));
                table.addCell(rs.getString("number_of_days"));
                table.addCell(rs.getString("return_date"));

            }

            PdfPCell fd = new PdfPCell(new com.itextpdf.text.Paragraph("TOTAL ITEMS ISSUED"));
            fd.setColspan(6);
            fd.setHorizontalAlignment(Element.ALIGN_LEFT);
            fd.setBackgroundColor(BaseColor.BLUE);
            table.addCell(fd);
            table.addCell(total_items.getText());
            //table.addCell("#");

            doc.add(table);

            p = new Paragraph("Issuing Officer:....." + issuer.getText() + "......     Signature:...............     Designation:....." + cmb_issuerDesignation.getSelectedItem().toString() + "......\n\n", FontFactory.getFont(FontFactory.TIMES_BOLD, 12));
            p.setAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            doc.add(p);
            p = new Paragraph("Certified that the quantities received have been taken on charge.\n\n", FontFactory.getFont(FontFactory.TIMES_BOLD, 12));
            p.setAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            doc.add(p);
            p = new Paragraph("Receiving Officer:....." + jComboBox1.getSelectedItem() + "......     Signature:...............     Designation:....." + cmb_ReceiverDesignation.getSelectedItem().toString() + "......\n\n", FontFactory.getFont(FontFactory.TIMES_BOLD, 12));
            p.setAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            doc.add(p);
            p = new Paragraph("Account No:...................................................                       Date:....." + date1 + "......\n\n", FontFactory.getFont(FontFactory.TIMES_BOLD, 12));
            p.setAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            doc.add(p);

            JOptionPane.showMessageDialog(null, "Report Saved");
            openReceipt();

            doc.close();
        } catch (DocumentException | IOException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void openReceipt() {
        try {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + "Receipt.pdf");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void calculateDate() {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = ((JTextField) txt_dated.getDateEditor().getUiComponent()).getText();
        String oldDate = "2017-01-29";
        System.out.println("Date of Conception: " + date1);
        //Specifying date format that matches the given date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            //Setting the date to the given date
            c.setTime(sdf.parse(date1));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Number of Days to add
        c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(txt_numberOfDays.getText()));
        //Date after adding the days to the given date
        txt_dated1.setDate(c.getTime());
        String newDate = sdf.format(c.getTime());
        //Displaying the new Date after addition of Days
        System.out.println("Approx Return Date: " + newDate);

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
        jLabel62 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        cmb_ReceiverDesignation = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        sc = new javax.swing.JTable();
        jLabel67 = new javax.swing.JLabel();
        search = new javax.swing.JTextField();
        add = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        cart_table = new javax.swing.JTable();
        remove = new javax.swing.JButton();
        jLabel68 = new javax.swing.JLabel();
        total_items = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        issue_no = new javax.swing.JTextField();
        jPanel22 = new javax.swing.JPanel();
        asset_no = new javax.swing.JTextField();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        asset_name = new javax.swing.JTextField();
        jLabel75 = new javax.swing.JLabel();
        model = new javax.swing.JTextField();
        jLabel76 = new javax.swing.JLabel();
        serial_no = new javax.swing.JTextField();
        jLabel70 = new javax.swing.JLabel();
        code_no = new javax.swing.JTextField();
        txt_dated1 = new com.toedter.calendar.JDateChooser();
        jLabel77 = new javax.swing.JLabel();
        txt_numberOfDays = new javax.swing.JTextField();
        jLabel78 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        issuer = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        cmb_issuerDesignation = new javax.swing.JComboBox<>();
        jComboBox1 = new javax.swing.JComboBox<>();

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
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Issue Product", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Modern No. 20", 1, 18))); // NOI18N
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
        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 255)), "Please Fill In Details Below", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Monotype Corsiva", 1, 18))); // NOI18N
        jPanel15.setName("jPanel15"); // NOI18N

        jLabel62.setText("Receiving Officer:");
        jLabel62.setName("jLabel62"); // NOI18N

        jLabel65.setText("Designation:");
        jLabel65.setName("jLabel65"); // NOI18N

        cmb_ReceiverDesignation.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--------------", "Other" }));
        cmb_ReceiverDesignation.setName("cmb_ReceiverDesignation"); // NOI18N
        cmb_ReceiverDesignation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_ReceiverDesignationActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Monotype Corsiva", 1, 24)); // NOI18N
        jButton3.setText("Issue");
        jButton3.setName("jButton3"); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Monotype Corsiva", 1, 24)); // NOI18N
        jButton4.setText("Cancel");
        jButton4.setName("jButton4"); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 255), 2), "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));
        jPanel16.setName("jPanel16"); // NOI18N

        jPanel21.setName("jPanel21"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        sc.setModel(new javax.swing.table.DefaultTableModel(data,header));
        sc.setName("sc"); // NOI18N
        sc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scMouseClicked(evt);
            }
        });
        new JScrollPane(sc,jScrollPane1.VERTICAL_SCROLLBAR_ALWAYS,jScrollPane1.HORIZONTAL_SCROLLBAR_ALWAYS);
        sc.setAutoResizeMode(sc.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(sc);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel67.setFont(new java.awt.Font("Pristina", 0, 18)); // NOI18N
        jLabel67.setText("SEARCH ");
        jLabel67.setName("jLabel67"); // NOI18N

        search.setName("search"); // NOI18N
        search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchKeyReleased(evt);
            }
        });

        add.setFont(new java.awt.Font("SimHei", 0, 24)); // NOI18N
        add.setText("ADD");
        add.setName("add"); // NOI18N
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        cart_table.setModel(new javax.swing.table.DefaultTableModel(data1,header));
        cart_table.setName("cart_table"); // NOI18N
        cart_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cart_tableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(cart_table);

        remove.setFont(new java.awt.Font("Monotype Corsiva", 1, 18)); // NOI18N
        remove.setText("REMOVE ITEM");
        remove.setName("remove"); // NOI18N
        remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeActionPerformed(evt);
            }
        });

        jLabel68.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel68.setText("TOTAL NO. OF ASSETS:");
        jLabel68.setName("jLabel68"); // NOI18N

        total_items.setBackground(new java.awt.Color(255, 204, 255));
        total_items.setName("total_items"); // NOI18N

        jLabel69.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel69.setText("Issue No.:");
        jLabel69.setName("jLabel69"); // NOI18N

        issue_no.setBackground(new java.awt.Color(255, 255, 204));
        issue_no.setName("issue_no"); // NOI18N
        issue_no.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                issue_noActionPerformed(evt);
            }
        });

        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 255)), "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));
        jPanel22.setName("jPanel22"); // NOI18N

        asset_no.setBackground(new java.awt.Color(255, 255, 204));
        asset_no.setName("asset_no"); // NOI18N

        jLabel73.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel73.setText("Asset No.:");
        jLabel73.setName("jLabel73"); // NOI18N

        jLabel74.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel74.setText("Asset Name");
        jLabel74.setName("jLabel74"); // NOI18N

        asset_name.setBackground(new java.awt.Color(255, 255, 204));
        asset_name.setName("asset_name"); // NOI18N

        jLabel75.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel75.setText("Model");
        jLabel75.setName("jLabel75"); // NOI18N

        model.setBackground(new java.awt.Color(255, 255, 204));
        model.setName("model"); // NOI18N

        jLabel76.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel76.setText("Serial No.:");
        jLabel76.setName("jLabel76"); // NOI18N

        serial_no.setBackground(new java.awt.Color(255, 255, 204));
        serial_no.setName("serial_no"); // NOI18N

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel73))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(asset_no, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(asset_name)
                    .addComponent(jLabel74, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(model)
                    .addComponent(jLabel75, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(serial_no)
                    .addComponent(jLabel76, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))
                .addContainerGap(172, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel74)
                        .addComponent(jLabel75)
                        .addComponent(jLabel73))
                    .addComponent(jLabel76)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(asset_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(model, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(asset_no, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(serial_no, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jLabel70.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel70.setText("Code No.:");
        jLabel70.setName("jLabel70"); // NOI18N

        code_no.setBackground(new java.awt.Color(255, 255, 204));
        code_no.setName("code_no"); // NOI18N
        code_no.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                code_noActionPerformed(evt);
            }
        });

        txt_dated1.setBackground(new java.awt.Color(255, 255, 255));
        txt_dated1.setForeground(new java.awt.Color(255, 51, 51));
        txt_dated1.setDateFormatString("yyyy-MM-dd");
        txt_dated1.setEnabled(false);
        txt_dated1.setName("txt_dated1"); // NOI18N
        txt_dated1.setOpaque(false);

        jLabel77.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel77.setText("Date To Return:");
        jLabel77.setName("jLabel77"); // NOI18N

        txt_numberOfDays.setBackground(new java.awt.Color(204, 255, 204));
        txt_numberOfDays.setName("txt_numberOfDays"); // NOI18N
        txt_numberOfDays.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_numberOfDaysKeyReleased(evt);
            }
        });

        jLabel78.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel78.setText("No. of Days:");
        jLabel78.setName("jLabel78"); // NOI18N

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel67)
                        .addGap(10, 10, 10)
                        .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 189, Short.MAX_VALUE)
                        .addComponent(remove, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(186, 186, 186)
                        .addComponent(jLabel68)
                        .addGap(18, 18, 18)
                        .addComponent(total_items, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel16Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane3))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel16Layout.createSequentialGroup()
                            .addGap(258, 258, 258)
                            .addComponent(jLabel78, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txt_numberOfDays, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel77)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txt_dated1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(53, 53, 53)
                            .addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel16Layout.createSequentialGroup()
                            .addGap(21, 21, 21)
                            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel16Layout.createSequentialGroup()
                                    .addComponent(jLabel69)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(issue_no, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel16Layout.createSequentialGroup()
                                    .addComponent(jLabel70)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(code_no, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(18, 18, 18)
                            .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_dated1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel77)
                                        .addComponent(txt_numberOfDays, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel78)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel68)
                                .addComponent(total_items, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(remove))
                        .addGap(49, 49, 49))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(issue_no, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel67)
                            .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(code_no, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))))
        );

        jLabel59.setText("Issuing Officer:");
        jLabel59.setName("jLabel59"); // NOI18N

        issuer.setName("issuer"); // NOI18N

        jLabel60.setText("Designation:");
        jLabel60.setName("jLabel60"); // NOI18N

        cmb_issuerDesignation.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--------------", "Other" }));
        cmb_issuerDesignation.setName("cmb_issuerDesignation"); // NOI18N

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-----------------" }));
        jComboBox1.setName("jComboBox1"); // NOI18N
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addComponent(jLabel62)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(jLabel65)
                                .addGap(18, 18, 18)
                                .addComponent(cmb_ReceiverDesignation, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel59)
                                .addGap(18, 18, 18)
                                .addComponent(issuer, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel60)
                                .addGap(18, 18, 18)
                                .addComponent(cmb_issuerDesignation, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(434, 434, 434)
                        .addComponent(jButton3)
                        .addGap(35, 35, 35)
                        .addComponent(jButton4)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_ReceiverDesignation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel65)
                    .addComponent(jLabel62)
                    .addComponent(jLabel59)
                    .addComponent(issuer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel60)
                    .addComponent(cmb_issuerDesignation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
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

    private void cmb_ReceiverDesignationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_ReceiverDesignationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmb_ReceiverDesignationActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {

            String date = ((JTextField) txt_dated.getDateEditor().getUiComponent()).getText();
             String date1 = ((JTextField) txt_dated1.getDateEditor().getUiComponent()).getText();
            String stat = "Inactive";
            String instat = "Active";

            conn = DatabaseConnection.connectToDb();
            st = conn.createStatement();
            pst = conn.prepareStatement("Update orders SET Status='" + stat + "' where Issue_No='" + issue_no.getText() + "' AND Status='" + instat + "'");
            pst.executeUpdate();

            String sql1 = "INSERT INTO issue VALUES(?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql1);
            pst.setString(1, issue_no.getText());
            pst.setString(2, total_items.getText());
            pst.setString(3, issuer.getText());
            pst.setString(4, cmb_issuerDesignation.getSelectedItem().toString());
            pst.setString(5, date);
            pst.setString(6, date1);
            pst.setString(7, jComboBox1.getSelectedItem().toString());
            pst.setString(8, cmb_ReceiverDesignation.getSelectedItem().toString());
            pst.execute();

            String sql = "select Code_No,Asset_No,Asset_Name,Model,Serial_No from orders WHERE Issue_No='" + issue_no.getText() + "' AND Status='" + instat + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            cart_table.setModel(DbUtils.resultSetToTableModel(rs));

            JOptionPane.showMessageDialog(null, "Successfully Issued");
            generateReport();

            asset_no.setText("");
            asset_name.setText("");
            serial_no.setText("");
            txt_numberOfDays.setText("");
            txt_dated1.setDate(null);

            issue_no.setText("");
            total_items.setText("");
            total_items.setText("");
            jComboBox1.setSelectedIndex(0);
            cmb_ReceiverDesignation.setSelectedIndex(0);
            issuer.setText("");
            cmb_ReceiverDesignation.setSelectedIndex(0);

            IncIssueNo();
            IncCodeNo();

            conn.close();
            conn = DatabaseConnection.connectToDb();
        } catch (Exception ex) {
            System.out.print("error" + ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {

            conn = DatabaseConnection.connectToDb();
            st = conn.createStatement();
            pst = conn.prepareStatement("delete from orders where Issue_No='" + issue_no.getText() + "'");
            pst.executeUpdate();

            IncIssueNo();

            String sql = "select Code_No,Asset_No,Asset_Name,Quantity,Price,Total from orders WHERE Issue_No='" + issue_no.getText() + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            cart_table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void scMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scMouseClicked

        try {

            int r = sc.getSelectedRow();
            int c = sc.getSelectedColumn();
            Object s = sc.getValueAt(r, 0);
            String NameVal = s.toString();
            String sql = "select * from hardware where Asset_No='" + NameVal + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            rs.next();
            asset_no.setText("" + rs.getString("Asset_No"));
            serial_no.setText("" + rs.getString("serial_no"));
            model.setText("" + rs.getString("model"));
            asset_name.setText("" + rs.getString("Asset_Name"));

            conn = DatabaseConnection.connectToDb();
            st = conn.createStatement();
        } catch (SQLException ex) {

            ex.printStackTrace();
        }
    }//GEN-LAST:event_scMouseClicked

    private void searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchKeyReleased
        try {

            DCEngin dce = new DCEngin("select * from hardware where Asset_Name like '" + search.getText() + "%'");
            data = dce.getData();
            header = new Vector<String>();
            header.add("S/N");
            header.add("Asset_No");
            header.add("Asset_Name");
            header.add("Model");
            header.add("Serial_No");

            sc.setModel(new javax.swing.table.DefaultTableModel(data, header));
        } catch (Exception ex) {
            Logger.getLogger(Issue_Product.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_searchKeyReleased

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        if (txt_numberOfDays.getText().isEmpty() || asset_no.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please ensure every field has the needed data");
        } else {
            try {
                SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date = ((JTextField) txt_dated1.getDateEditor().getUiComponent()).getText();
                String date1 = ((JTextField) txt_dated.getDateEditor().getUiComponent()).getText();
                int tot = 0;
                conn = DatabaseConnection.connectToDb();
                st = conn.createStatement();
                String stat = "Active";

                pst = conn.prepareStatement("insert into orders values(?,?,?,?,?,?,?,?,?,?,?,?)");
                pst.setString(1, null);
                pst.setInt(2, Integer.parseInt(issue_no.getText()));
                pst.setString(3, code_no.getText());
                pst.setString(4, asset_no.getText());
                pst.setString(5, asset_name.getText());
                pst.setString(6, model.getText());
                pst.setString(7, serial_no.getText());
                pst.setString(8, txt_numberOfDays.getText());
                pst.setString(9, date);
                pst.setString(10, date1);
                pst.setString(11, stat);
                pst.setString(12, "No");
                pst.executeUpdate();

                rs = st.executeQuery("select COUNT(asset_no) from orders WHERE  Issue_no='" + issue_no.getText() + "'");
                while (rs.next()) {
                    tot = Integer.parseInt(rs.getString("COUNT(Asset_no)"));
                }
                total_items.setText(String.format("%s", tot));

                String sql = "select Code_No,Asset_No,Asset_Name,Model,Serial_No from orders WHERE Issue_No='" + issue_no.getText() + "'";
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                cart_table.setModel(DbUtils.resultSetToTableModel(rs));

                String stati = "Issued";
                String sql1 = "UPDATE hardware SET Status='" + stati + "' WHERE Asset_No='" + asset_no.getText() + "'";
                pst = conn.prepareStatement(sql1);
                pst.execute();

                setAvaillableAssets();
                IncCodeNo();

                asset_no.setText("");
                asset_name.setText("");
                serial_no.setText("");
                model.setText("");
                //txt_numberOfDays.setText("");

            } catch (Exception ex) {
                System.out.println("Error = " + ex);
            }
        }
    }//GEN-LAST:event_addActionPerformed

    private void cart_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cart_tableMouseClicked

        try {
            String stat = "Active";
            String stati = "Available";

            conn = DatabaseConnection.connectToDb();
            st = conn.createStatement();

            int r = cart_table.getSelectedRow();
            int c = cart_table.getSelectedColumn();
            Object s = cart_table.getValueAt(r, 0);
            Object d = cart_table.getValueAt(r, 1);
            String NameVal = s.toString();
            String del = d.toString();
            pst = conn.prepareStatement("delete from orders where Code_No='" + NameVal + "' AND Status='" + stat + "'");
            pst.executeUpdate();
            pst = conn.prepareStatement("UPDATE Hardware SET Status='" + stati + "' WHERE Asset_No='" + del + "'");
            pst.execute();
            setAvaillableAssets();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_cart_tableMouseClicked

    private void removeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeActionPerformed

        try {

            String sql = "select Code_No,Asset_No,Asset_Name,Model,Serial_No from orders WHERE Issue_No='" + issue_no.getText() + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            cart_table.setModel(DbUtils.resultSetToTableModel(rs));
            IncCodeNo();

        } catch (Exception ex) {
            Logger.getLogger(Issue_Product.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_removeActionPerformed

    private void issue_noActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_issue_noActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_issue_noActionPerformed

    private void code_noActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_code_noActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_code_noActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        try {

            String sql = "SELECT * FROM employees WHERE Name='" + jComboBox1.getSelectedItem() + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            String add;
            while (rs.next()) {

                cmb_ReceiverDesignation.setSelectedItem(rs.getString("position"));
                cmb_ReceiverDesignation.setEnabled(false);

            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void txt_numberOfDaysKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numberOfDaysKeyReleased
        calculateDate();
    }//GEN-LAST:event_txt_numberOfDaysKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JTextField asset_name;
    private javax.swing.JTextField asset_no;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTable cart_table;
    private javax.swing.JComboBox<String> cmb_ReceiverDesignation;
    private javax.swing.JComboBox<String> cmb_issuerDesignation;
    private javax.swing.JTextField code_no;
    private javax.swing.JTextField issue_no;
    private javax.swing.JTextField issuer;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField model;
    private javax.swing.JButton remove;
    private javax.swing.JTable sc;
    private javax.swing.JTextField search;
    private javax.swing.JTextField serial_no;
    private javax.swing.JTextField total_items;
    public static com.toedter.calendar.JDateChooser txt_dated1;
    private javax.swing.JTextField txt_numberOfDays;
    // End of variables declaration//GEN-END:variables

}
