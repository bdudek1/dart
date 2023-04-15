package com.example.dart.repository;

import com.example.dart.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlayerRepository {
    @Autowired
    private HibernateTemplate hibernateTemplate;

    public PlayerRepository(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    public void savePlayer (Player player) {
        hibernateTemplate.save(player);
    }

    public Player findPlayerById (int id) {
        return hibernateTemplate.get(Player.class, id);
    }

    public List<Player> findAllPlayers () {
        return hibernateTemplate.loadAll(Player.class);
    }
}
