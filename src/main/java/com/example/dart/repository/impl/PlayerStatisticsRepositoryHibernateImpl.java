package com.example.dart.repository.impl;

import com.example.dart.model.PlayerStatistics;
import com.example.dart.repository.PlayerStatisticsRepository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class PlayerStatisticsRepositoryHibernateImpl implements PlayerStatisticsRepository {
    private static final Logger logger = LoggerFactory.getLogger(PlayerStatisticsRepositoryHibernateImpl.class);
    private final SessionFactory sessionFactory;

    @Autowired
    public PlayerStatisticsRepositoryHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void updatePlayerStatistics(PlayerStatistics playerStatistics) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(playerStatistics);

        logger.info("Updated player statistics of player: {}", playerStatistics.getPlayer().getName());
    }

    public Optional<PlayerStatistics> getPlayerStatisticsByPlayerName(String playerName) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PlayerStatistics> query = cb.createQuery(PlayerStatistics.class);

        Root<PlayerStatistics> root = query.from(PlayerStatistics.class);

        query.select(root).where(cb.equal(root.get("player").get("name"), playerName));

        return Optional.ofNullable(session.createQuery(query).getSingleResultOrNull());
    }

    public List<PlayerStatistics> getPageOfPlayerStatisticsOrderByGamesPlayedDesc(int page) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PlayerStatistics> query = cb.createQuery(PlayerStatistics.class);

        Root<PlayerStatistics> root = query.from(PlayerStatistics.class);

        query.orderBy(cb.desc(root.get("gamesPlayed")));

        List<PlayerStatistics> playerStatisticsOrderedByGamesPlayedDesc = getPlayerStatisticsPageFromQuery(session, query, page);

        logger.info("Returning {} player statistics objects", playerStatisticsOrderedByGamesPlayedDesc.size());

        return Collections.unmodifiableList(playerStatisticsOrderedByGamesPlayedDesc);
    }

    public List<PlayerStatistics> getPageOfPlayerStatisticsOrderByShotsFiredDesc(int page) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PlayerStatistics> query = cb.createQuery(PlayerStatistics.class);

        Root<PlayerStatistics> root = query.from(PlayerStatistics.class);

        query.orderBy(cb.desc(root.get("shotsFired")));

        List<PlayerStatistics> playerStatisticsOrderedByShotsFiredDesc = getPlayerStatisticsPageFromQuery(session, query, page);

        logger.info("Returning {} player statistics objects ordered by shots fired", playerStatisticsOrderedByShotsFiredDesc.size());

        return Collections.unmodifiableList(playerStatisticsOrderedByShotsFiredDesc);
    }

    public List<PlayerStatistics> getPageOfPlayerStatisticsOrderByGamesWonPercentageDesc(int page) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PlayerStatistics> query = cb.createQuery(PlayerStatistics.class);

        Root<PlayerStatistics> root = query.from(PlayerStatistics.class);

        query.select(root).where(cb.gt(root.get("gamesPlayed"), 0));
        query.orderBy(cb.desc(cb.quot(root.get("gamesWon"), root.get("gamesPlayed"))));

        List<PlayerStatistics> playerStatisticsOrderedByGamesWonPercentageDesc = getPlayerStatisticsPageFromQuery(session, query, page);

        logger.info("Returning {} player statistics objects ordered by games won percentage", playerStatisticsOrderedByGamesWonPercentageDesc.size());

        return Collections.unmodifiableList(playerStatisticsOrderedByGamesWonPercentageDesc);
    }

    public List<PlayerStatistics> getPageOfPlayerStatisticsOrderByTriple20HitsPercentageDesc(int page) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PlayerStatistics> query = cb.createQuery(PlayerStatistics.class);

        Root<PlayerStatistics> root = query.from(PlayerStatistics.class);

        query.select(root).where(cb.gt(root.get("shotsFired"), 0));
        query.orderBy(cb.desc(cb.quot(root.get("triple20Hits"), root.get("shotsFired"))));

        List<PlayerStatistics> playerStatisticsOrderedByTriple20HitsPercentageDesc = getPlayerStatisticsPageFromQuery(session, query, page);

        logger.info("Returning {} player statistics objects ordered by triple 20 hits percentage", playerStatisticsOrderedByTriple20HitsPercentageDesc.size());

        return Collections.unmodifiableList(playerStatisticsOrderedByTriple20HitsPercentageDesc);
    }

    public List<PlayerStatistics> getPageOfPlayerStatisticsOrderByDouble20HitsPercentageDesc(int page) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PlayerStatistics> query = cb.createQuery(PlayerStatistics.class);

        Root<PlayerStatistics> root = query.from(PlayerStatistics.class);

        query.select(root).where(cb.gt(root.get("shotsFired"), 0));
        query.orderBy(cb.desc(cb.quot(root.get("double20Hits"), root.get("shotsFired"))));

        List<PlayerStatistics> playerStatisticsOrderedByDouble20HitsPercentageDesc = getPlayerStatisticsPageFromQuery(session, query, page);

        logger.info("Returning {} player statistics objects ordered by double 20 hits percentage", playerStatisticsOrderedByDouble20HitsPercentageDesc.size());

        return Collections.unmodifiableList(playerStatisticsOrderedByDouble20HitsPercentageDesc);
    }

    public List<PlayerStatistics> getPageOfPlayerStatisticsOrderBySingle20HitsPercentageDesc(int page) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PlayerStatistics> query = cb.createQuery(PlayerStatistics.class);

        Root<PlayerStatistics> root = query.from(PlayerStatistics.class);

        query.select(root).where(cb.gt(root.get("shotsFired"), 0));
        query.orderBy(cb.desc(cb.quot(root.get("single20Hits"), root.get("shotsFired"))));

        List<PlayerStatistics> playerStatisticsOrderedBySingle20HitsPercentageDesc = getPlayerStatisticsPageFromQuery(session, query, page);

        logger.info("Returning {} player statistics objects ordered by single 20 hits percentage", playerStatisticsOrderedBySingle20HitsPercentageDesc.size());

        return Collections.unmodifiableList(playerStatisticsOrderedBySingle20HitsPercentageDesc);
    }

    public List<PlayerStatistics> getPageOfPlayerStatisticsOrderBySingle25HitsPercentageDesc(int page) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PlayerStatistics> query = cb.createQuery(PlayerStatistics.class);

        Root<PlayerStatistics> root = query.from(PlayerStatistics.class);

        query.select(root).where(cb.gt(root.get("shotsFired"), 0));
        query.orderBy(cb.desc(cb.quot(root.get("single25Hits"), root.get("shotsFired"))));

        List<PlayerStatistics> playerStatisticsOrderedBySingle25HitsPercentageDesc = getPlayerStatisticsPageFromQuery(session, query, page);

        logger.info("Returning {} player statistics objects ordered by single 25 hits percentage", playerStatisticsOrderedBySingle25HitsPercentageDesc.size());

        return Collections.unmodifiableList(playerStatisticsOrderedBySingle25HitsPercentageDesc);
    }

    public List<PlayerStatistics> getPageOfPlayerStatisticsOrderByDouble25HitsPercentageDesc(int page) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PlayerStatistics> query = cb.createQuery(PlayerStatistics.class);

        Root<PlayerStatistics> root = query.from(PlayerStatistics.class);

        query.select(root).where(cb.gt(root.get("shotsFired"), 0));
        query.orderBy(cb.desc(cb.quot(root.get("double25Hits"), root.get("shotsFired"))));

        List<PlayerStatistics> playerStatisticsOrderedByDouble25HitsPercentageDesc = getPlayerStatisticsPageFromQuery(session, query, page);

        logger.info("Returning {} player statistics objects ordered by double 25 hits percentage", playerStatisticsOrderedByDouble25HitsPercentageDesc.size());

        return Collections.unmodifiableList(playerStatisticsOrderedByDouble25HitsPercentageDesc);
    }

    public List<PlayerStatistics> getPageOfPlayerStatisticsOrderByDoubleHitsPercentageDesc(int page) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PlayerStatistics> query = cb.createQuery(PlayerStatistics.class);

        Root<PlayerStatistics> root = query.from(PlayerStatistics.class);

        query.select(root).where(cb.gt(root.get("shotsFired"), 0));
        query.orderBy(cb.desc(cb.quot(root.get("doubleHits"), root.get("shotsFired"))));

        List<PlayerStatistics> playerStatisticsOrderedByDoubleHitsPercentageDesc = getPlayerStatisticsPageFromQuery(session, query, page);

        logger.info("Returning {} player statistics objects ordered by double hits percentage", playerStatisticsOrderedByDoubleHitsPercentageDesc.size());

        return Collections.unmodifiableList(playerStatisticsOrderedByDoubleHitsPercentageDesc);
    }

    public List<PlayerStatistics> getPageOfPlayerStatisticsOrderByTripleHitsPercentageDesc(int page) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PlayerStatistics> query = cb.createQuery(PlayerStatistics.class);

        Root<PlayerStatistics> root = query.from(PlayerStatistics.class);

        query.select(root).where(cb.gt(root.get("shotsFired"), 0));
        query.orderBy(cb.desc(cb.quot(root.get("tripleHits"), root.get("shotsFired"))));

        List<PlayerStatistics> playerStatisticsOrderedByTripleHitsPercentageDesc = getPlayerStatisticsPageFromQuery(session, query, page);

        logger.info("Returning {} player statistics objects ordered by triple hits percentage", playerStatisticsOrderedByTripleHitsPercentageDesc.size());

        return Collections.unmodifiableList(playerStatisticsOrderedByTripleHitsPercentageDesc);
    }

    private List<PlayerStatistics> getPlayerStatisticsPageFromQuery(Session session,
                                                                    CriteriaQuery<PlayerStatistics> query,
                                                                    int page) {

        return session.createQuery(query)
                .setFirstResult((page - 1) * PAGE_SIZE)
                .setMaxResults(PAGE_SIZE)
                .getResultList();
    }
}
