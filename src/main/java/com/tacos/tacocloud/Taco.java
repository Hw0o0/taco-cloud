package com.tacos.tacocloud;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;

    @Data
    @Entity
    public class Taco {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "created_at")
        private Date createdAt;

        @NotNull
        @Size(min=5,message = "최소 5글자 필요")
        private String name;

        @ManyToMany(targetEntity = Ingredient.class)
        @Size(min=1, message = "최소 하나이상의 재료필요")
        private List<Ingredient> ingredients;

        @PrePersist
        void createdAt(){
            this.createdAt = new Date();
        }
    }

