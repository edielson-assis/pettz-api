package br.com.pettz.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import br.com.pettz.utils.validation.CategoryValidator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(of = "categoryId")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
@Getter
@Entity
@Table(name = "categories")
public class Category implements Serializable {
    
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id", columnDefinition = "UUID")
    private UUID categoryId;

    @CategoryValidator
    @Column(nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "category_product", joinColumns = @JoinColumn(name = "category_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private final Set<Product> products = new HashSet<>();
}