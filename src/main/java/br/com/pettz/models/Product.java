package br.com.pettz.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(of = "productId")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "products")
public class Product implements Serializable {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id", columnDefinition = "UUID")
    private UUID productId;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private final Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<ImgUrl> imgUrls = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_color", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "color_id"))
    private final Set<Color> colors = new HashSet<>();
}