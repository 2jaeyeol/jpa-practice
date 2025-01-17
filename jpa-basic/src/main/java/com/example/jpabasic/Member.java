package com.example.jpabasic;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Member {

    @Id
    private Long id;
    private String name;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}
