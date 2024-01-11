package com.example.bookmyshow.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
@Getter
@Setter
@Entity (name = "THEATRES")
public class Theatre extends BaseModel{
    private String theatreName;
    private String theatreAddress;

    @OneToMany (mappedBy = "theatre", fetch = FetchType.EAGER)
    private List<Screen> screenList = new ArrayList<>();

    @ManyToOne
    private City city;
}
