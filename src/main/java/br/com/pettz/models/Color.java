package br.com.pettz.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(of = "colorId")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "colors")
public class Color implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "color_id", columnDefinition = "UUID")
    private UUID colorId;

    @Column(nullable = false)
    private String color;

    @ManyToMany(mappedBy = "colors", fetch = FetchType.LAZY)
    private final List<Product> products = new ArrayList<>();
}