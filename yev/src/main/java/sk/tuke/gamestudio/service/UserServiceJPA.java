package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Person;
import sk.tuke.gamestudio.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class UserServiceJPA implements UserService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean login(String login, String password) {
        return entityManager.createNamedQuery("login")
                .setParameter("login", login).setParameter("password", password) != null;
    }

    @Override
    public boolean register(String login, String password) {
        boolean check = entityManager.createNamedQuery("checkName")
                .setParameter("login", login) == null;
        if(check){
            Person pouzivatel = new Person(login, password);
            entityManager.persist(pouzivatel);
        }
        return check;
    }


}