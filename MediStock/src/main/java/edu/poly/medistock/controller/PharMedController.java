
package edu.poly.medistock.controller;

import edu.poly.medistock.dao.PharMedDao;
import edu.poly.medistock.entity.PharMed;
import edu.poly.medistock.view.PharMedView;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author ADMINZ
 */
public class PharMedController {
    private PharMedDao pharMedDao;
    private PharMedView pharMedView;

    public PharMedController(PharMedView view) {
        this.pharMedView = view;
        pharMedDao = new PharMedDao();
        CombinedStatistics combinedStats = new CombinedStatistics(pharMedDao);
        
        view.addAddPharMedListener(new AddPharMedListener());
        view.addEditPharMedListener(new EditPharMedListener());
        view.addDeletePharMedListener(new DeletePharMedListener());
        view.addClearPharMedListener(new ClearPharMedListener());
        view.getListPharMedSelectionListener(new ListPharMedSelectionListener());
        view.sortListPharMedListener(new SortPharMedActionListener());
        view.searchPharMedListener(new SearchPharMedActionListener());
        view.SearchPharMedInSegmentListener(new SearchPharMedInSegmentActionListener());
        view.addStatisticsPharMedListener(combinedStats);
    }
    
    public void showPharMedView() {
        List<PharMed> pharMedList = pharMedDao.getListPharMeds();
        pharMedView.setVisible(true);
        pharMedView.showListPharMeds(pharMedList);
    }
    
    class AddPharMedListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            PharMed pharMed = pharMedView.getPharMedInfo();
            if(pharMed != null){
                pharMedDao.add(pharMed);
                pharMedView.showPharMedInfo(pharMed);
                pharMedView.showListPharMeds(pharMedDao.getListPharMeds());
                pharMedView.showMessage("Thêm thành công");
            }
        }
    }
    
    
    class EditPharMedListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            PharMed pharMed = pharMedView.getPharMedInfo();
            if(pharMed != null){
                pharMedDao.edit(pharMed);
                pharMedView.showPharMedInfo(pharMed);
                pharMedView.showListPharMeds(pharMedDao.getListPharMeds());
                pharMedView.showMessage("Chỉnh sửa thành công");
            }
        }
    }
    

    class DeletePharMedListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            PharMed pharMed = pharMedView.getPharMedInfo();
            if(pharMed != null){
                pharMedDao.delete(pharMed);
                pharMedView.clearPharMedInfo();
                pharMedView.showListPharMeds(pharMedDao.getListPharMeds());
                pharMedView.showMessage("Đã xóa thành công");
            }
        }
    }
    
    class ClearPharMedListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            pharMedView.clearPharMedInfo();
        }
    }
    
    class ListPharMedSelectionListener implements ListSelectionListener{
            @Override
            public void valueChanged(ListSelectionEvent e) {
                pharMedView.fillPharMedFromSelectedRow();
            }
    }
    
    class SortPharMedActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //do combobox luôn có lựa chọn
            String type = pharMedView.getTypeSort();
            
            switch(type){
                case "ID":{
                    pharMedDao.sortPharMedsByID();
                    break;
                }
                case "Tên sản phẩm":{
                    pharMedDao.sortPharMedsByName();
                    break;
                }
                case "NSX":{
                    pharMedDao.sortPharMedByNSX();
                    break;
                }
                case "HSD":{
                    pharMedDao.sortPharMedByHSD();
                    break;
                }
                case "Nguồn nhập":{
                    pharMedDao.sortPharMedsBySource();
                    break;
                }
                case "Số lô":{
                    pharMedDao.sortPharMedsBySet();
                    break;
                }
                case "Số lượng nhập":{
                    pharMedDao.sortPharMedsByNumber();
                    break;
                }
                case "Giá sản phẩm":{
                    pharMedDao.sortPharMedsByPrice();
                    break;
                }
                case "Số hóa đơn":{
                    pharMedDao.sortPharMedByBill();
                    break;
                }
            }
            pharMedView.showListPharMeds(pharMedDao.getListPharMeds());
            //System.out.println("Sắp xếp thành công");
        }
        
    }
    
    class SearchPharMedActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //do combobox luôn có lựa chọn
            String type = pharMedView.getTypeSearch();
            String content = pharMedView.getSearchContent();
            
            if(content.equals("")){
                pharMedView.showMessage("Chưa nhập nội dung tìm kiếm");
                return;
            }
            
            switch(type){
                case "ID":{
                   pharMedView.showListPharMeds(pharMedDao.searchByID(content));
                    break;
                }
                case "Tên sản phẩm":{
                    pharMedView.showListPharMeds(pharMedDao.searchByName(content));
                    System.out.println("Succeeded");
                    break;
                }
                case "NSX":{
                    pharMedView.showListPharMeds(pharMedDao.searchByNSX(content));
                    break;
                }
                case "HSD":{
                    pharMedView.showListPharMeds(pharMedDao.searchByHSD(content));
                    break;
                }
                case "Nguồn nhập":{
                    pharMedView.showListPharMeds(pharMedDao.searchBySource(content));
                    break;
                }
                case "Số lô":{
                    pharMedView.showListPharMeds(pharMedDao.searchBySet(content));
                    break;
                }
            }
            //System.out.println("Thành công");
        }
        
    }
    
    class SearchPharMedInSegmentActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            
            String type = pharMedView.getTypeSearch2();
            String content1 = pharMedView.getSearchContent1();
            String content2 = pharMedView.getSearchContent2();
            
            switch(type){
                case "Số lượng nhập":{
                    try {
                        double start = Double.parseDouble(pharMedView.getSearchContent1());
                        double end = Double.parseDouble(pharMedView.getSearchContent2());
                    
                        pharMedView.showListPharMeds(pharMedDao.searchByNumber(start, end));                   
                        break;
                    } catch (Exception ex) {
                        pharMedView.showMessage("Nhập chưa đúng định dạng");
                    }
                }

                case "Giá sản phẩm":{
                    try {
                        double start = Double.parseDouble(pharMedView.getSearchContent1());
                        double end = Double.parseDouble(pharMedView.getSearchContent2());
                        
                        pharMedView.showListPharMeds(pharMedDao.searchByPrice(start, end));
                        break;
                    } catch (Exception er) {
                        pharMedView.showMessage("Nhập chưa đúng định dạng");
                    }
                    break;
                }
        }
        
    }
}
    
    class CombinedStatistics implements ActionListener{

        private final PharMedDao pharMedDao; 

    public CombinedStatistics(PharMedDao pharMedDao) {
        this.pharMedDao = pharMedDao;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2)); 
        mainPanel.setPreferredSize(new Dimension(800, 600));

        JFreeChart pieChart = createPieChart();
        mainPanel.add(new ChartPanel(pieChart));

        JFreeChart barChart = createBarChart();
        mainPanel.add(new ChartPanel(barChart));

        JFrame frame = new JFrame("Combined Statistics");
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }

    private JFreeChart createPieChart() {
        DefaultPieDataset pieDataset = pharMedDao.dataForPieChart(); // Assuming dataForPieChart returns DefaultPieDataset
        JFreeChart pieChart = ChartFactory.createPieChart(
                "Biểu đồ tròn thống kê số lượng sản phẩm",
                pieDataset,
                false,
                true, 
                false 
        );
        PiePlot plot = (PiePlot) pieChart.getPlot();
        return pieChart;
    }

    private JFreeChart createBarChart() {
       DefaultCategoryDataset barDataset = pharMedDao.dataForColumnChart(); // Assuming dataForColumnChart returns DefaultValueDataset
        JFreeChart barChart = ChartFactory.createBarChart(
                "Biểu đồ thống kê số thuốc và vật tư y tế trong tầm giá",
                "",
                "Số sản phẩm trong tầm giá",
                barDataset,
                PlotOrientation.VERTICAL,
                true, 
                true, 
                false 
        );
        return barChart;
    }
    }
}