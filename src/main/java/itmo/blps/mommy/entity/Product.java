package itmo.blps.mommy.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "consumer_info")
    private String consumerInfo;

    public Product(String name, Float weight, String consumerInfo) {
        this.name = name;
        this.weight = weight;
        this.consumerInfo = consumerInfo;
    }

    public Product() {
    }
}
