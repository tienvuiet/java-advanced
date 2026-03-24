package session11.db.entity;

import java.time.LocalDate;
import java.util.Date;

public class Product {
    private Integer proId;
    private String proName;
    private String producer;
    private Integer yearMaking;
    private Date expiryDate;
    private Double price;

    public Product() {
    }

    public Product(Integer proId, String proName, String producer, Integer yearMaking, Date expiryDate, Double price) {
        this.proId = proId;
        this.proName = proName;
        this.producer = producer;
        this.yearMaking = yearMaking;
        this.expiryDate = expiryDate;
        this.price = price;
    }

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public Integer getYearMaking() {
        return yearMaking;
    }

    public void setYearMaking(Integer yearMaking) {
        this.yearMaking = yearMaking;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "proId=" + proId +
                ", proName='" + proName + '\'' +
                ", producer='" + producer + '\'' +
                ", yearMaking=" + yearMaking +
                ", expiryDate=" + expiryDate +
                ", price=" + price +
                '}';
    }
}
