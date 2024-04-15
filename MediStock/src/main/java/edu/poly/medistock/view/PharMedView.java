/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package edu.poly.medistock.view;

import edu.poly.medistock.entity.PharMed;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.metal.MetalBorders;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author msi gameming
 */
public class PharMedView extends javax.swing.JFrame {

    //Định nghĩa các cột của bảng
    String[] columnNames = new String[]{
        "ID", "Tên sản phẩm", "NSX", "HSD", "Nguồn nhập", "Số hóa đơn", "Số lô", "Số lượng", "Giá sản phẩm" 
    };
    
    private  Object data = new Object[][]{};
    
    public PharMedView() {
        initComponents();
        setLocationRelativeTo(null);
    }
    
    //hiển thị thông báo 
    public void showMessage(String message){
        JOptionPane.showMessageDialog(this, message);
    }
    
    //đổ dữ liệu vào bảng pharMedTable
    public void showListPharMeds(List<PharMed> list){
        //mảng 2 chiều hàng và cột
        if(list == null || list.isEmpty()){
            DefaultTableModel model = (DefaultTableModel) tblPharMed.getModel();
            model.setRowCount(0);
        }
        int size = list.size();
        Object[][] pharMeds = new Object[size][9];
        for(int i = 0; i < size; i++){
            pharMeds[i][0] = list.get(i).getId();
            pharMeds[i][1] = list.get(i).getName();
            pharMeds[i][2] = list.get(i).getNSX();
            pharMeds[i][3] = list.get(i).getHSD();
            pharMeds[i][4] = list.get(i).getSource();
            pharMeds[i][5] = list.get(i).getBill();
            pharMeds[i][6] = list.get(i).getSet();
            pharMeds[i][7] = list.get(i).getNumber();
            pharMeds[i][8] = list.get(i).getPrice();
        }
        tblPharMed.setModel(new DefaultTableModel(pharMeds, columnNames));
    }
    //lấy thông tin từ ô được chọn trong bảng
    public void fillPharMedFromSelectedRow(){
        int row = tblPharMed.getSelectedRow();
        if(row >= 0){
            idField.setText(tblPharMed.getModel().getValueAt(row, 0).toString());
            nameField.setText(tblPharMed.getModel().getValueAt(row, 1).toString());
            nsxField.setText(tblPharMed.getModel().getValueAt(row, 2).toString());
            hsdField.setText(tblPharMed.getModel().getValueAt(row, 3).toString());
            sourceField.setText(tblPharMed.getModel().getValueAt(row, 4).toString());
            billField.setText(tblPharMed.getModel().getValueAt(row, 5).toString());
            setField.setText(tblPharMed.getModel().getValueAt(row, 6).toString());
            numberField.setText(tblPharMed.getModel().getValueAt(row, 7).toString());
            priceField.setText(tblPharMed.getModel().getValueAt(row, 8).toString());
            // enable Edit and Delete buttons
            editBtn.setEnabled(true);
            deleteBtn.setEnabled(true);
            // disable Add button
            addBtn.setEnabled(false);
        }
    }
    
    //xóa thông tin thuốc
        public void clearPharMedInfo() {
        idField.setText("");
        nameField.setText("");
        nsxField.setText("");
        hsdField.setText("");
        sourceField.setText("");
        billField.setText("");
        setField.setText("");
        numberField.setText("");
        priceField.setText("");

        // disable Edit and Delete buttons
        editBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
        // enable Add button
        addBtn.setEnabled(true);
    }
    
    //hiển thị thông tin thuốc
    public void showPharMedInfo(PharMed pharMed){
        idField.setText("" + pharMed.getId());
        nameField.setText("" + pharMed.getName());
        nsxField.setText("" + pharMed.getNSX());
        hsdField.setText("" + pharMed.getHSD());
        sourceField.setText("" + pharMed.getSource());
        billField.setText("" + pharMed.getBill());
        setField.setText("" + pharMed.getSet());
        numberField.setText("" + pharMed.getNumber());
        priceField.setText("" + pharMed.getPrice());
        
        // enable Edit and Delete buttons
        editBtn.setEnabled(true);
        deleteBtn.setEnabled(true);
        // disable Add button
        addBtn.setEnabled(false);
    }
    
    //xóa bảng khi không tìm thấy
    public void clearTable(){
    }
    
    //lấy thông tin đơn thuốc
public PharMed getPharMedInfo() {
        // validate student
        if (!validateName() || !validateNSX() || !validateHSD() || !validateSource()|| !validateBill()|| !validateSet() 
                || !validateNumber()    || !validatePrice()) {
            return null;
        }
        try {
            PharMed pharMed = new PharMed();
            if (idField.getText() != null && !"".equals(idField.getText())) {
                pharMed.setId(Integer.parseInt(idField.getText()));
            }
            pharMed.setName(nameField.getText().trim());
            pharMed.setNSX(nsxField.getText().trim());
            pharMed.setHSD(hsdField.getText().trim());
            pharMed.setSource(sourceField.getText().trim());
            pharMed.setBill(billField.getText().trim());
            pharMed.setSet(setField.getText().trim());
            pharMed.setNumber(Integer.parseInt(numberField.getText().trim()));
            pharMed.setPrice(Double.parseDouble(priceField.getText().trim()));
            return pharMed;
        } catch (Exception e) {
            showMessage(e.getMessage());
        }
        return null;
    }    
    
    //các phương thức validate
     private boolean validateName() {
        String name = nameField.getText();
        if (name == null || "".equals(name.trim())) {
            nameField.requestFocus();
            showMessage("Tên không được trống.");
            return false;
        }
        return true;
    }
     
    //validate NSX
     LocalDate nsx = null; //khai báo bên ngoài để sau tiện so sánh
    public boolean validateNSX(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("d/M/yyyy");
         
        try{
             nsx = LocalDate.parse(nsxField.getText().trim(), format);
             if(nsx.isAfter(LocalDate.now())){
                 showMessage("Sản phẩm chưa được sản xuất!");
                 return false;
             }
             nsxField.requestFocus();
         }catch(Exception e){
             showMessage("NSX không đúng định dạng");
             return false;
         }
         return true;
     }
     
    //validate HSD: đúng kiểu ngày tháng và sau NSX
    public boolean validateHSD(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate hsd = null;
        try{
            hsd = LocalDate.parse(hsdField.getText().trim(), format);
            hsdField.requestFocus();
            
            if(hsd.isBefore(LocalDate.now())){
                showMessage("Chỉ được thêm hàng chưa hết hạn");
                return false;
            }
            
            if(!hsd.isAfter(nsx)){
                showMessage("HSD phải sau NSX");
                return false;
            }
        } catch(Exception e){
            showMessage("HSD không đúng định dạng");
        }
        return true;
    }
     
    private boolean validateSource() {
        String source = sourceField.getText();
        if (source == null || "".equals(source.trim())) {
            sourceField.requestFocus();
            showMessage("Nguồn hàng không được trống.");
            return false;
        }
        return true;
    }
    private boolean validateBill() {
        String bill = billField.getText();
        if (bill == null || "".equals(bill.trim())) {
            billField.requestFocus();
            showMessage("Số hóa đơn không được trống.");
            return false;
        }
        return true;
    }
    private boolean validateSet() {
        String set = setField.getText();
        if (set == null || "".equals(set.trim())) {
            setField.requestFocus();
            showMessage("Số lô không được trống.");
            return false;
        }
        return true;
    }
    
    private boolean validateNumber() {
        try {
            int number = Integer.parseInt(numberField.getText().trim());
            if (number <= 0) {
                priceField.requestFocus();
                showMessage("Số lượng phải > 0");
                return false;
            }
        } catch (Exception e) {
            priceField.requestFocus();
            showMessage("Số lượng không hợp lệ!");
            return false;
        }
        return true;
    }
    
    private boolean validatePrice() {
        try {
            Double price = Double.parseDouble(priceField.getText().trim());
            if (price < 0) {
                priceField.requestFocus();
                showMessage("Giá không được < 0");
                return false;
            }
        } catch (Exception e) {
            priceField.requestFocus();
            showMessage("Giá không hợp lệ!");
            return false;
        }
        return true;
    }
    
     public void valueChanged(ListSelectionEvent e) {
    }
     
      public void actionPerformed(ActionEvent e) {
    }
      
      public void addAddPharMedListener(ActionListener listener) {
        addBtn.addActionListener(listener);
    }
     
    public void addEditPharMedListener(ActionListener listener) {
        editBtn.addActionListener(listener);
    }
     
    public void addDeletePharMedListener(ActionListener listener) {
        deleteBtn.addActionListener(listener);
    }
     
    public void addClearPharMedListener(ActionListener listener) {
        clearBtn.addActionListener(listener);
    }
    

    public void getListPharMedSelectionListener(ListSelectionListener listener) {
        tblPharMed.getSelectionModel().addListSelectionListener(listener);
    }
    
    public void sortListPharMedListener(ActionListener listener){
        sortBtn.addActionListener(listener);
    }
    
    public String getTypeSort(){
        return (String) cbbSort.getSelectedItem();
    }
    
    public void searchPharMedListener(ActionListener listener){
        btnSearch1.addActionListener(listener);
    }
    
    public String getTypeSearch(){
       return (String) cbbSearchChoice1.getSelectedItem();
    }
    
    public String getSearchContent(){
        return (String) txtSearchInput.getText().trim();
    }
    
    
    //phục vụ tìm kiếm trong khoảng
    public void SearchPharMedInSegmentListener(ActionListener listener){
        btnSearch2.addActionListener(listener);
    }
    public String getTypeSearch2(){
        return (String) cbbSearchChoice2.getSelectedItem();
    }
    public String getSearchContent1(){
        return (String) txtSearchInput2.getText().trim();
    }
    
    public String getSearchContent2(){
        return (String) txtSearchInput3.getText().trim();
    }
    
    public void addStatisticsPharMedListener(ActionListener listener){
        btnStatistic.addActionListener(listener);
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        setField = new javax.swing.JTextField();
        billField = new javax.swing.JTextField();
        priceField = new javax.swing.JTextField();
        nameField = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        nsxField = new javax.swing.JTextField();
        idField = new javax.swing.JTextField();
        sourceField = new javax.swing.JTextField();
        hsdField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPharMed = new javax.swing.JTable();
        editBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        clearBtn = new javax.swing.JButton();
        btnSearch1 = new javax.swing.JButton();
        txtSearchInput = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        cbbSearchChoice1 = new javax.swing.JComboBox<>();
        addBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cbbSort = new javax.swing.JComboBox<>();
        jLabel41 = new javax.swing.JLabel();
        numberField = new javax.swing.JTextField();
        sortBtn = new javax.swing.JButton();
        btnSearch2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtSearchInput3 = new javax.swing.JTextField();
        txtSearchInput2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cbbSearchChoice2 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        btnStatistic = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setForeground(new java.awt.Color(204, 255, 204));
        jPanel1.setPreferredSize(new java.awt.Dimension(885, 700));

        jPanel4.setBackground(new java.awt.Color(255, 239, 236));
        jPanel4.setForeground(new java.awt.Color(255, 204, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(885, 700));

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));

        billField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                billFieldActionPerformed(evt);
            }
        });

        priceField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                priceFieldtxtPriceActionPerformed(evt);
            }
        });

        jLabel33.setText("ID:");

        jLabel34.setText("Tên sản phẩm:");

        jLabel35.setText("Ngày sản xuất:");

        jLabel36.setText("Hạn sử dụng:");

        jLabel37.setText("Nguồn nhập");

        jLabel38.setText("Số hóa đơn:");

        jLabel39.setText("Số lô: ");

        jLabel40.setText("Giá sản phẩm:");

        idField.setEditable(false);

        sourceField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sourceFieldtxtSourceActionPerformed(evt);
            }
        });

        tblPharMed.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tblPharMed.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tên sản phẩm", "NSX", "HSD", "Nguồn nhập", "Số hoá đơn", "Số lô", "Giá sản phẩm"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblPharMed.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPane1.setViewportView(tblPharMed);

        editBtn.setText("Sửa");

        deleteBtn.setText("Xoá");

        clearBtn.setText("Làm mới");

        btnSearch1.setText("Tìm kiếm");

        jLabel42.setText("theo");

        cbbSearchChoice1.setMaximumRowCount(4);
        cbbSearchChoice1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Tên sản phẩm", "NSX", "HSD", "Nguồn nhập", "Số hoá đơn", "Số lô" }));

        addBtn.setText("Thêm");
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        jLabel1.setText("Tìm kiếm theo:    ");

        cbbSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Tên sản phẩm", "NSX", "HSD", "Nguồn nhập", "Số hóa đơn", "Số lô", "Số lượng nhập", "Giá sản phẩm" }));

        jLabel41.setText("Số lượng:");

        numberField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numberFieldtxtPriceActionPerformed(evt);
            }
        });

        sortBtn.setText("Sắp xếp");

        btnSearch2.setText("Tìm kiếm");

        jLabel5.setText("Tìm kiếm theo:");

        jLabel6.setText("đến:");

        cbbSearchChoice2.setMaximumRowCount(4);
        cbbSearchChoice2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Số lượng nhập", "Giá sản phẩm" }));

        jLabel7.setText("từ:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(180, 180, 180)
                        .addComponent(jLabel40)
                        .addGap(5, 5, 5)
                        .addComponent(priceField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(540, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel36)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel34)
                                    .addComponent(jLabel35)
                                    .addComponent(jLabel33))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(idField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nsxField, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(hsdField, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel39)
                            .addComponent(jLabel38)
                            .addComponent(jLabel37)
                            .addComponent(jLabel41))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(setField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(numberField, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(billField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                            .addComponent(sourceField, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbSearchChoice1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(cbbSearchChoice2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel7)))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtSearchInput2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtSearchInput3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtSearchInput, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnSearch1)
                            .addComponent(btnSearch2)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 892, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(addBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(492, 492, 492)
                                        .addComponent(sortBtn)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel42)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cbbSort, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(editBtn)
                                        .addGap(18, 18, 18)
                                        .addComponent(deleteBtn)
                                        .addGap(18, 18, 18)
                                        .addComponent(clearBtn)))))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(idField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37)
                    .addComponent(sourceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(setField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(nsxField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38)
                    .addComponent(billField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(hsdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41)
                    .addComponent(numberField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(priceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editBtn)
                    .addComponent(deleteBtn)
                    .addComponent(clearBtn)
                    .addComponent(addBtn)
                    .addComponent(sortBtn)
                    .addComponent(jLabel42)
                    .addComponent(cbbSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSearch1)
                    .addComponent(cbbSearchChoice1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearchInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchInput3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearchInput2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(btnSearch2)
                    .addComponent(jLabel6)
                    .addComponent(cbbSearchChoice2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(538, 538, 538))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        btnStatistic.setBackground(new java.awt.Color(0, 0, 204));
        btnStatistic.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnStatistic.setForeground(new java.awt.Color(255, 255, 255));
        btnStatistic.setText("Thống kê");
        btnStatistic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStatisticActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 153));
        jLabel3.setText("Quản lý kho Thuốc và Vật tư y tế");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnStatistic, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(103, 103, 103)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 990, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnStatistic, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1030, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 712, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void numberFieldtxtPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numberFieldtxtPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numberFieldtxtPriceActionPerformed

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addBtnActionPerformed

    private void sourceFieldtxtSourceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sourceFieldtxtSourceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sourceFieldtxtSourceActionPerformed

    private void priceFieldtxtPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_priceFieldtxtPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_priceFieldtxtPriceActionPerformed

    private void billFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_billFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_billFieldActionPerformed

    private void btnStatisticActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStatisticActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnStatisticActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JTextField billField;
    private javax.swing.JButton btnSearch1;
    private javax.swing.JButton btnSearch2;
    private javax.swing.JButton btnStatistic;
    private javax.swing.JComboBox<String> cbbSearchChoice1;
    private javax.swing.JComboBox<String> cbbSearchChoice2;
    private javax.swing.JComboBox<String> cbbSort;
    private javax.swing.JButton clearBtn;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton editBtn;
    private javax.swing.JTextField hsdField;
    private javax.swing.JTextField idField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nameField;
    private javax.swing.JTextField nsxField;
    private javax.swing.JTextField numberField;
    private javax.swing.JTextField priceField;
    private javax.swing.JTextField setField;
    private javax.swing.JButton sortBtn;
    private javax.swing.JTextField sourceField;
    private javax.swing.JTable tblPharMed;
    private javax.swing.JTextField txtSearchInput;
    private javax.swing.JTextField txtSearchInput2;
    private javax.swing.JTextField txtSearchInput3;
    // End of variables declaration//GEN-END:variables
}
