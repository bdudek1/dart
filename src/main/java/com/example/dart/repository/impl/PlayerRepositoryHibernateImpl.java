package com.example.dart.repository.impl;

import com.example.dart.model.Player;
import com.example.dart.repository.PlayerRepository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Repository
@Transactional
public class PlayerRepositoryHibernateImpl implements PlayerRepository {
    private static final Logger logger = LoggerFactory.getLogger(PlayerRepositoryHibernateImpl.class);
    private final SessionFactory sessionFactory;

    @Autowired
    public PlayerRepositoryHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void savePlayer (Player player) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(player);
        logger.info("Player {} successfully saved.", player);
    }

    public void updatePlayer(Player player) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(player);
        logger.info("Player {} successfully updated.", player);
    }

    public void deletePlayer(Player player) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(player);
        logger.info("Player {} successfully deleted.", player);
    }

    public Optional<Player> findPlayerById (int id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Player.class, id));
    }

    public Optional<Player> findPlayerByName (String name) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.createQuery("from Player where name = :name", Player.class)
                .setParameter("name", name)
                .uniqueResult());
    }

    public Collection<Player> findAllPlayers() {
        Session session = sessionFactory.getCurrentSession();
        return Collections.unmodifiableCollection(session.createQuery("from Player", Player.class).list());
    }
}
