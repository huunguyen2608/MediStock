package edu.poly.medistock.entity;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name = "pharmed")
@XmlAccessorType(XmlAccessType.FIELD)
public class PharMed implements Serializable { //lớp serializable là lớp tuần tự hóa
    private static final long serialVersionUID = 1L;
    private int id; 
    private String name, NSX, HSD, Source, bill, set;
    private int number;
    private double price;
    
    //hàm dựng thử tí xóa
    public PharMed() {
    }

    public PharMed(int id, String name, String NSX, String HSD, String Source, String bill, String set, int number, double price) {
        this.id = id;
        this.name = name;
        this.NSX = NSX;
        this.HSD = HSD;
        this.Source = Source;
        this.bill = bill;
        this.set = set;
        this.number = number;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNSX() {
        return NSX;
    }

    public void setNSX(String NSX) {
        this.NSX = NSX;
    }

    public String getHSD() {
        return HSD;
    }

    public void setHSD(String HSD) {
        this.HSD = HSD;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String Source) {
        this.Source = Source;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    
    //viết để thử nghiệm lúc chạy
    @Override
    public String toString() {
        return "PharMed{" + "id=" + id + ", name=" + name + ", NSX=" + NSX + ", HSD=" + HSD + ", Source=" + Source + ", bill=" + bill + ", set=" + set + ", price=" + price + '}';
    }
    
    
}
