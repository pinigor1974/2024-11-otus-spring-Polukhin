package ru.otus.hw.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "users")
@Entity
public class User {

    @Id
    private Long id;

    private String name;

    private String password;

    private String role;
}
