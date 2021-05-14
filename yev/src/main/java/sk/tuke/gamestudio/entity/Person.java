package sk.tuke.gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "login",
        query = "SELECT p FROM Person p WHERE p.login=:login AND p.password=:password")
@NamedQuery(name = "checkName",
        query = "SELECT p FROM Person p WHERE p.login=:login")
public class Person {
    @Id
    @GeneratedValue
    private int id;
    private String login;
    private String password;

    public Person(String login) {
        this.login = login;
    }

    public Person(String login, String password){
        this.login = login;
        this.password = password;
    }

    public Person() {

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public int getId() {
        return id;
    }


    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
