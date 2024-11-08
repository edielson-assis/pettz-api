package br.com.pettz.models;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(of = "imgUrlId")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "img_urls")
public class ImgUrl implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "img_url_id", columnDefinition = "UUID")
    private UUID imgUrlId;

    @Column(nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}