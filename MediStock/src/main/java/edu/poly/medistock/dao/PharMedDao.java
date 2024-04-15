/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.poly.medistock.dao;

import edu.poly.medistock.entity.PharMed;
import edu.poly.medistock.entity.PharMedXML;
import edu.poly.medistock.utils.FileUtils;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author ADMINZ
 */

//lớp chứa các phương thức quản lý sinh viên: thêm, sửa, xóa, sắp xếp, đọc, ghi sinh viên
public class PharMedDao {
    private static  final String PHARMED_FILE_NAME = "pharmed.xml";
    List<PharMed> listPharMeds;

    public PharMedDao() {
        this.listPharMeds = readListPharMeds();
        if(listPharMeds == null){
            listPharMeds = new ArrayList<PharMed>();
        }
    }
    
    //lưu các đối tượng vào file xml
    public void writeListPharMeds(List<PharMed> pharMeds){
        PharMedXML pharMedXML = new PharMedXML();
        pharMedXML.setPharmed(pharMeds);
        
        //gọi phương thức write
        FileUtils.writeXMLtoFile(PHARMED_FILE_NAME, pharMedXML);
    }
    
    //đọc danh sách
    public List<PharMed> readListPharMeds(){
        //khai báo list chứa
        List<PharMed> list = new ArrayList<PharMed>();
        //đọc
        PharMedXML pharMedXML = (PharMedXML)FileUtils.readXMLFile(PHARMED_FILE_NAME, PharMedXML.class);
        if(pharMedXML != null){
            list = pharMedXML.getPharmed();
        }
        return list;
    }
        //thêm pharmed vào listPharMeds và lưu listPharMeds vào file
        public void add(PharMed pharMed){
        int id = 1;
        if (listPharMeds != null && listPharMeds.size() > 0 ){
        id = listPharMeds.size() + 1;
            }
        //thêm
        pharMed.setId(id);
        listPharMeds.add(pharMed);
        writeListPharMeds(listPharMeds);
        }
        
        //cập nhật pharmed vào listPharMed và lưu listPharmeds vào file
        public void edit(PharMed pharMed){
            int size = listPharMeds.size();
            for(int i = 0; i < size; i++){
                if(listPharMeds.get(i).getId() == pharMed.getId()){
                    listPharMeds.get(i).setName(pharMed.getName());
                    listPharMeds.get(i).setNSX(pharMed.getNSX());
                    listPharMeds.get(i).setHSD(pharMed.getHSD());
                    listPharMeds.get(i).setSource(pharMed.getSource());
                    listPharMeds.get(i).setBill(pharMed.getBill());
                    listPharMeds.get(i).setSet(pharMed.getSet());
                    listPharMeds.get(i).setNumber(pharMed.getNumber());
                    listPharMeds.get(i).setPrice(pharMed.getPrice());
                    writeListPharMeds(listPharMeds); //để trong hoặc đều được?
                    break;
                }
            }
        }
        
        // xóa
        public boolean delete(PharMed pharMeds){
            boolean isFound = false;
            int size = listPharMeds.size();
                for(int i = 0; i < size; i++){
                    if(listPharMeds.get(i).getId() == pharMeds.getId()){
                        pharMeds = listPharMeds.get(i);
                        isFound = true;
                    }
                }
                if(isFound){
                    listPharMeds.remove(pharMeds);
                    writeListPharMeds(listPharMeds);
                    return true;
                }
                return false;
        } 
        
        //sắp xếp theo ID
        public void sortPharMedsByID() {
        Collections.sort(listPharMeds, new Comparator<PharMed>() {
            public int compare(PharMed pharmed1, PharMed pharmed2) { //do ID là kiểu số
                return pharmed1.getId() - pharmed2.getId();
            }
        });
    }
        //sắp xếp theo tên
        public void sortPharMedsByName() {
        Collections.sort(listPharMeds, new Comparator<PharMed>() {
            public int compare(PharMed pharmed1, PharMed pharmed2) { //do ID là kiểu số
                return pharmed1.getName().compareTo(pharmed2.getName());
            }
        });
        }
        
        //sắp xếp theo số tồn kho
        public void sortPharMedsByNumber(){
            Collections.sort(listPharMeds, new Comparator<PharMed>(){
                @Override
                public int compare(PharMed pharMed1, PharMed pharMed2) {
                    return (int) (pharMed1.getNumber() - pharMed2.getNumber());
                }
            });
        }
        
        //sắp xếp theo khoảng giá
        public void sortPharMedsByPrice() {
        Collections.sort(listPharMeds, new Comparator<PharMed>() {
            public int compare(PharMed pharmed1, PharMed pharmed2) {
                return (int) (pharmed1.getPrice() - pharmed2.getPrice());
            }
        });
        }
        
        //hàm sắp xếp theo HSD
        public void sortPharMedByHSD(){
            Collections.sort(listPharMeds, new Comparator<PharMed>(){
            public int compare(PharMed pharMed1, PharMed pharMed2){
                
                DateTimeFormatter format = DateTimeFormatter.ofPattern("d/M/yyyy");
                LocalDate date1 = LocalDate.parse(pharMed1.getHSD(), format);
                LocalDate date2 = LocalDate.parse(pharMed2.getHSD(), format);
                //có thể in ra
                return date1.compareTo(date2);
            }
            });
        }
        
        //sắp xếp theo NSX
        public void sortPharMedByNSX(){
            Collections.sort(listPharMeds, new Comparator<PharMed>(){
            public int compare(PharMed pharMed1, PharMed pharMed2){
                
                DateTimeFormatter format = DateTimeFormatter.ofPattern("d/M/yyyy");
                LocalDate date1 = LocalDate.parse(pharMed1.getNSX(), format);
                LocalDate date2 = LocalDate.parse(pharMed2.getNSX(), format);
                //có thể in ra
                return date1.compareTo(date2);
            }
            });
        }
        //Sắp xếp theo số hóa đơn
        public void sortPharMedByBill()
        {
            Collections.sort(listPharMeds, new Comparator<PharMed>(){
                public int compare(PharMed pharMed1, PharMed pharMed2){
                    return pharMed1.getBill().compareTo(pharMed2.getBill());
                }
            });
        }
        //sắp xếp theo số lô
        public void sortPharMedsBySet() {
        Collections.sort(listPharMeds, new Comparator<PharMed>() {
            public int compare(PharMed pharmed1, PharMed pharmed2) {
                return pharmed1.getSet().compareTo(pharmed2.getSet());
            }
        });
        }
        
        //sắp xếp theo nguồn hàng
        public void sortPharMedsBySource() {
        Collections.sort(listPharMeds, new Comparator<PharMed>() {
            public int compare(PharMed pharmed1, PharMed pharmed2) {
                return pharmed1.getSource().compareTo(pharmed2.getSource());
            }
        });
        }
        
        //tìm kiếm gần đúng theo ID
        public List<PharMed> searchByID(String ID){
            List<PharMed> list = new ArrayList<>();
            for(PharMed pharMed : listPharMeds){
                if(String.valueOf(pharMed.getId()).contains(ID)) list.add(pharMed);
            }
            return list;
        }
        
        //tìm kiếm gần đúng theo tên
        public List<PharMed> searchByName(String name){
            List<PharMed> list = new ArrayList<>();
            
            name = toNoDiacts(name).toLowerCase();
            for(PharMed pharMed : listPharMeds){
                //System.out.println(toNoDiacts(pharMed.getName()).toLowerCase());
                if(toNoDiacts(pharMed.getName()).toLowerCase().contains(name))
                    list.add(pharMed);
            }
            return list;
        }
        //hàm chuyển không dấu tìm kiếm tên, nguồn nhập
        public String toNoDiacts(String s){
            Map<Character, Character> charMap = new HashMap<>();
        charMap.put('à', 'a');
        charMap.put('á', 'a');
        charMap.put('ả', 'a');
        charMap.put('ã', 'a');
        charMap.put('ạ', 'a');
        charMap.put('ằ', 'a');
        charMap.put('ắ', 'a');
        charMap.put('ẳ', 'a');
        charMap.put('ẵ', 'a');
        charMap.put('ặ', 'a');
        charMap.put('ầ', 'a');
        charMap.put('ấ', 'a');
        charMap.put('ẩ', 'a');
        charMap.put('ẫ', 'a');
        charMap.put('ậ', 'a');

        charMap.put('è', 'e');
        charMap.put('é', 'e');
        charMap.put('ẻ', 'e');
        charMap.put('ẽ', 'e');
        charMap.put('ẹ', 'e');
        charMap.put('ề', 'e');
        charMap.put('ế', 'e');
        charMap.put('ể', 'e');
        charMap.put('ễ', 'e');
        charMap.put('ệ', 'e');

        charMap.put('ì', 'i');
        charMap.put('í', 'i');
        charMap.put('ỉ', 'i');
        charMap.put('ĩ', 'i');
        charMap.put('ị', 'i');

        charMap.put('ò', 'o');
        charMap.put('ó', 'o');
        charMap.put('ỏ', 'o');
        charMap.put('õ', 'o');
        charMap.put('ọ', 'o');
        charMap.put('ồ', 'o');
        charMap.put('ố', 'o');
        charMap.put('ổ', 'o');
        charMap.put('ỗ', 'o');
        charMap.put('ộ', 'o');
        charMap.put('ờ', 'o');
        charMap.put('ớ', 'o');
        charMap.put('ở', 'o');
        charMap.put('ỡ', 'o');
        charMap.put('ợ', 'o');

        charMap.put('ù', 'u');
        charMap.put('ú', 'u');
        charMap.put('ủ', 'u');
        charMap.put('ũ', 'u');
        charMap.put('ụ', 'u');
        charMap.put('ừ', 'u');
        charMap.put('ứ', 'u');
        charMap.put('ử', 'u');
        charMap.put('ữ', 'u');
        charMap.put('ự', 'u');

        charMap.put('ỳ', 'y');
        charMap.put('ý', 'y');
        charMap.put('ỷ', 'y');
        charMap.put('ỹ', 'y');
        charMap.put('ỵ', 'y');

        charMap.put('đ', 'd');
        charMap.put('Đ', 'D');
        
        StringBuilder res = new StringBuilder();
        for(char c : s.toCharArray()){
            if(charMap.containsKey(c)){
                res.append(charMap.get(c));
            }
            else res.append(c);
        }
            
            return res.toString();
        }
        //tìm kiếm gần đúng theo NSX
        public List<PharMed> searchByNSX(String nsx){
            
            List list = new ArrayList();
            try{    
                    DateTimeFormatter ft = DateTimeFormatter.ofPattern("d/M/yyyy");
                    LocalDate date1 = LocalDate.parse(nsx, ft);

                    for(PharMed each :listPharMeds){
                        LocalDate date2 = LocalDate.parse(each.getNSX(), ft);
                        if(date2.isEqual(date1)){
                            list.add(each);
                            }
                    }
            } catch(Exception e){
                JOptionPane.showMessageDialog(null, "Định dạng NSX không hợp lệ");
            }
            return list;
        }
        
        //tìm kiếm gần đúng theo HSD
        public List<PharMed> searchByHSD(String hsd){
            
            List list = new ArrayList();
            try{    
                    DateTimeFormatter ft = DateTimeFormatter.ofPattern("d/M/yyyy");
                    LocalDate date1 = LocalDate.parse(hsd, ft);

                    for(PharMed each :listPharMeds){
                        LocalDate date2 = LocalDate.parse(each.getHSD(), ft);
                        if(date2.isEqual(date1)){
                            list.add(each);
                            }
                    }
            } catch(Exception e){
                JOptionPane.showMessageDialog(null, "Định dạng HSD không hợp lệ");
            }
            return list;
        }
        
        //tìm kiếm gần đúng theo source
        public List<PharMed> searchBySource(String source){
            List<PharMed> list = new ArrayList<>();
            source = toNoDiacts(source).toLowerCase();
            
            for(PharMed pharMed : listPharMeds){
                if(toNoDiacts(pharMed.getSource()).toLowerCase().contains(source))
                    list.add(pharMed);
            }
            
            return list;
        }
        //tìm kiếm gần đúng theo bill
        public List<PharMed> searchByBill(String bill){
            List<PharMed> list = new ArrayList<>();
            
            for(PharMed pharMed : listPharMeds){
                if(pharMed.getBill().toLowerCase().contains(bill))
                    list.add(pharMed);
            }
            return list;
        }
        //tìm kiếm gần đúng theo set
        public List<PharMed> searchBySet(String set){
            List<PharMed> list = new ArrayList<>();
            
            for(PharMed pharMed : listPharMeds){
                if(pharMed.getSet().toLowerCase().contains(set))
                    list.add(pharMed);
            }
            return list;
        }
        
        //tìm kiếm theo khoảng số lượng
        public List<PharMed> searchByNumber(double s, double e){
            
            List<PharMed> list = new ArrayList<>();
            for(PharMed each : listPharMeds){
                if( (double) 1.0 * each.getNumber() >= s && (double) 1.0 * each.getNumber() <= e){
                    list.add(each);
                }
            }
            return list;
        }
        
        //tìm kiếm trong khoảng giá
        public List<PharMed> searchByPrice(double s, double e){
            
            List<PharMed> list = new ArrayList<>();
            for(PharMed each : listPharMeds){
                if(each.getPrice() >= s && each.getPrice() <= e){
                    list.add(each);
                }
            }
            return list;
        }
        
        //chuẩn bị dữ liệu cho biểu đồ tròn thống kê
        public DefaultPieDataset dataForPieChart(){
            DefaultPieDataset pieData = new DefaultPieDataset();
            DecimalFormat percent = new DecimalFormat("#0.00%");
            
            int totalValue = 0;
            for(PharMed each : getListPharMeds()){
                totalValue += each.getNumber();
            }
            
            for(PharMed each : getListPharMeds()){
                String name = each.getName();
                int num = each.getNumber();
                double value = (double)num / totalValue * 100.0;
                pieData.setValue(name, value);
                
            }
            return pieData;
        }

        //Chuẩn bị dữ liệu cho biểu đồ cột
        public DefaultCategoryDataset dataForColumnChart(){
            
        DefaultCategoryDataset data = new DefaultCategoryDataset();
        
        List<PharMed> list = getListPharMeds();
        int c1 = 0, c2 = 0, c3 = 0, c4 = 0, c5 = 0;
        
        for(PharMed each : list){
            double price = each.getPrice();
            if(price <= 50){
                c1++;}
            else if(price > 50 && price <= 100){
                c2++;
            }
            else if(price > 100 && price <= 150){
                c3++;
            }
            else if(price > 150 && price <= 200){
                c4++;
            }
            else c5++;
            
        }   
        data.setValue(c1, "", "Không quá 50");
        data.setValue(c2, "", "Từ 50 đến 100");
        data.setValue(c3, "", "Từ 100 đến 150");
        data.setValue(c4, "", "Từ 150 đến 200");
        data.setValue(c5, "", "Trên 200");

        return data;
        }


        
//        public Map<String, Color> colorMap(){
//            
//            Map<String, Color> colorMap = new HashMap<>();
//            for(PharMed each: getListPharMeds()){
//                Random random = new Random();
//                colorMap.put(each.getName(), new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
//            }
//            return colorMap;
//        }
        
        //chuâ
    public List<PharMed> getListPharMeds() {
        return listPharMeds;
    }

    public void setListPharMeds(List<PharMed> listPharMeds) {
        this.listPharMeds = listPharMeds;
    }    
        
}
