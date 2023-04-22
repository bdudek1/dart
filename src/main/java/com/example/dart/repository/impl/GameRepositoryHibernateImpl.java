package com.example.dart.repository.impl;

import com.example.dart.model.Game;
import com.example.dart.repository.GameRepository;

import org.hibernate.MultiIdentifierLoadAccess;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class GameRepositoryHibernateImpl implements GameRepository {
    private static final String GET_GAMES_IDS_BY_PLAYER_ID_QUERY = "SELECT GAME_ID FROM PLAYERS_TO_GAMES WHERE PLAYER_ID = :playerId";
    private static final Logger logger = LoggerFactory.getLogger(GameRepositoryHibernateImpl.class);
    private final SessionFactory sessionFactory;

    @Autowired
    public GameRepositoryHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveGame(Game game) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(game);
        logger.info("Game {} successfully saved", game);
    }

    public void updateGame(Game game) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(game);
        logger.info("Game {} successfully updated", game);
    }

    public void deleteGame(Game game) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(game);
        logger.info("Game {} successfully deleted", game);
    }

    public Optional<Game> findGameById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Game.class, id));
    }

    public Collection<Game> findGamesByPlayerId(Long playerId) {
        Session session = sessionFactory.getCurrentSession();

        Query<Long[]> query = session.createNativeQuery(GET_GAMES_IDS_BY_PLAYER_ID_QUERY, Long[].class);
        query.setParameter("playerId", playerId);

        List<Long[]> gameIds = query.getResultList();

        MultiIdentifierLoadAccess<Game> gamesLoadAccess = session.byMultipleIds(Game.class);

        return gamesLoadAccess.multiLoad(gameIds);
    }
}
