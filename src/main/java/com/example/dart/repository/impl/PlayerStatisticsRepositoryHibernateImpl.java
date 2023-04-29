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

    @Override
    public List<PlayerStatistics> getPageOfPlayerStatisticsOrderByGamesPlayedDesc(int page) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PlayerStatistics> query = cb.createQuery(PlayerStatistics.class);

        Root<PlayerStatistics> root = query.from(PlayerStatistics.class);

        query.orderBy(cb.desc(root.get("gamesPlayed")));

        List<PlayerStatistics> playerStatisticsOrderedByGamesPlayedDesc = session.createQuery(query)
                                                                                    .setFirstResult((page - 1) * PAGE_SIZE)
                                                                                    .setMaxResults(PAGE_SIZE)
                                                                                    .getResultList();

        logger.info("Returning {} player statistics objects", playerStatisticsOrderedByGamesPlayedDesc.size());

        return Collections.unmodifiableList(playerStatisticsOrderedByGamesPlayedDesc);
    }

    @Override
    public List<PlayerStatistics> getPageOfPlayerStatisticsOrderByShotsFiredDesc(int page) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PlayerStatistics> query = cb.createQuery(PlayerStatistics.class);

        Root<PlayerStatistics> root = query.from(PlayerStatistics.class);

        query.orderBy(cb.desc(root.get("shotsFired")));

        List<PlayerStatistics> playerStatisticsOrderedByShotsFiredDesc = session.createQuery(query)
                                                                                    .setFirstResult((page - 1) * PAGE_SIZE)
                                                                                    .setMaxResults(PAGE_SIZE)
                                                                                    .getResultList();

        logger.info("Returning {} player statistics objects ordered by shots fired", playerStatisticsOrderedByShotsFiredDesc.size());

        return Collections.unmodifiableList(playerStatisticsOrderedByShotsFiredDesc);
    }

    @Override
    public List<PlayerStatistics> getPageOfPlayerStatisticsOrderByGamesWonPercentageDesc(int page) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PlayerStatistics> query = cb.createQuery(PlayerStatistics.class);

        Root<PlayerStatistics> root = query.from(PlayerStatistics.class);

        query.orderBy(cb.desc(cb.quot(root.get("gamesWon"), root.get("gamesPlayed"))));

        List<PlayerStatistics> playerStatisticsOrderedByGamesWonPercentageDesc = session.createQuery(query)
                                                                                        .setFirstResult((page - 1) * PAGE_SIZE)
                                                                                        .setMaxResults(PAGE_SIZE)
                                                                                        .getResultList();

        logger.info("Returning {} player statistics objects ordered by games won percentage", playerStatisticsOrderedByGamesWonPercentageDesc.size());

        return Collections.unmodifiableList(playerStatisticsOrderedByGamesWonPercentageDesc);
    }

    @Override
    public List<PlayerStatistics> getPageOfPlayerStatisticsOrderByTriple20HitsPercentageDesc(int page) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PlayerStatistics> query = cb.createQuery(PlayerStatistics.class);

        Root<PlayerStatistics> root = query.from(PlayerStatistics.class);

        query.orderBy(cb.desc(cb.quot(root.get("triple20Hits"), root.get("shotsFired"))));

        List<PlayerStatistics> playerStatisticsOrderedByTriple20HitsPercentageDesc = session.createQuery(query)
                                                                                                .setFirstResult((page - 1) * PAGE_SIZE)
                                                                                                .setMaxResults(PAGE_SIZE)
                                                                                                .getResultList();

        logger.info("Returning {} player statistics objects ordered by triple 20 hits percentage", playerStatisticsOrderedByTriple20HitsPercentageDesc.size());

        return Collections.unmodifiableList(playerStatisticsOrderedByTriple20HitsPercentageDesc);
    }

    @Override
    public List<PlayerStatistics> getPageOfPlayerStatisticsOrderByDouble20HitsPercentageDesc(int page) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PlayerStatistics> query = cb.createQuery(PlayerStatistics.class);

        Root<PlayerStatistics> root = query.from(PlayerStatistics.class);

        query.orderBy(cb.desc(cb.quot(root.get("double20Hits"), root.get("shotsFired"))));

        List<PlayerStatistics> playerStatisticsOrderedByDouble20HitsPercentageDesc = session.createQuery(query)
                                                                                                .setFirstResult((page - 1) * PAGE_SIZE)
                                                                                                .setMaxResults(PAGE_SIZE)
                                                                                                .getResultList();

        logger.info("Returning {} player statistics objects ordered by double 20 hits percentage", playerStatisticsOrderedByDouble20HitsPercentageDesc.size());

        return Collections.unmodifiableList(playerStatisticsOrderedByDouble20HitsPercentageDesc);
    }

    @Override
    public List<PlayerStatistics> getPageOfPlayerStatisticsOrderBySingle20HitsPercentageDesc(int page) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PlayerStatistics> query = cb.createQuery(PlayerStatistics.class);

        Root<PlayerStatistics> root = query.from(PlayerStatistics.class);

        query.orderBy(cb.desc(cb.quot(root.get("single20Hits"), root.get("shotsFired"))));

        List<PlayerStatistics> playerStatisticsOrderedBySingle20HitsPercentageDesc = session.createQuery(query)
                                                                                                .setFirstResult((page - 1) * PAGE_SIZE)
                                                                                                .setMaxResults(PAGE_SIZE)
                                                                                                .getResultList();

        logger.info("Returning {} player statistics objects ordered by single 20 hits percentage", playerStatisticsOrderedBySingle20HitsPercentageDesc.size());

        return Collections.unmodifiableList(playerStatisticsOrderedBySingle20HitsPercentageDesc);
    }

    @Override
    public List<PlayerStatistics> getPageOfPlayerStatisticsOrderBySingle25HitsPercentageDesc(int page) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PlayerStatistics> query = cb.createQuery(PlayerStatistics.class);

        Root<PlayerStatistics> root = query.from(PlayerStatistics.class);

        query.orderBy(cb.desc(cb.quot(root.get("single25Hits"), root.get("shotsFired"))));

        List<PlayerStatistics> playerStatisticsOrderedBySingle25HitsPercentageDesc = session.createQuery(query)
                                                                                                .setFirstResult((page - 1) * PAGE_SIZE)
                                                                                                .setMaxResults(PAGE_SIZE)
                                                                                                .getResultList();

        logger.info("Returning {} player statistics objects ordered by single 25 hits percentage", playerStatisticsOrderedBySingle25HitsPercentageDesc.size());

        return Collections.unmodifiableList(playerStatisticsOrderedBySingle25HitsPercentageDesc);
    }

    @Override
    public List<PlayerStatistics> getPageOfPlayerStatisticsOrderByDouble25HitsPercentageDesc(int page) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PlayerStatistics> query = cb.createQuery(PlayerStatistics.class);

        Root<PlayerStatistics> root = query.from(PlayerStatistics.class);

        query.orderBy(cb.desc(cb.quot(root.get("double25Hits"), root.get("shotsFired"))));

        List<PlayerStatistics> playerStatisticsOrderedByDouble25HitsPercentageDesc = session.createQuery(query)
                                                                                                .setFirstResult((page - 1) * PAGE_SIZE)
                                                                                                .setMaxResults(PAGE_SIZE)
                                                                                                .getResultList();

        logger.info("Returning {} player statistics objects ordered by double 25 hits percentage", playerStatisticsOrderedByDouble25HitsPercentageDesc.size());

        return Collections.unmodifiableList(playerStatisticsOrderedByDouble25HitsPercentageDesc);
    }

    @Override
    public List<PlayerStatistics> getPageOfPlayerStatisticsOrderByDoubleHitsPercentageDesc(int page) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PlayerStatistics> query = cb.createQuery(PlayerStatistics.class);

        Root<PlayerStatistics> root = query.from(PlayerStatistics.class);

        query.orderBy(cb.desc(cb.quot(root.get("doubleHits"), root.get("shotsFired"))));

        List<PlayerStatistics> playerStatisticsOrderedByDoubleHitsPercentageDesc = session.createQuery(query)
                                                                                            .setFirstResult((page - 1) * PAGE_SIZE)
                                                                                            .setMaxResults(PAGE_SIZE)
                                                                                            .getResultList();

        logger.info("Returning {} player statistics objects ordered by double hits percentage", playerStatisticsOrderedByDoubleHitsPercentageDesc.size());

        return Collections.unmodifiableList(playerStatisticsOrderedByDoubleHitsPercentageDesc);
    }

    @Override
    public List<PlayerStatistics> getPageOfPlayerStatisticsOrderByTripleHitsPercentageDesc(int page) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PlayerStatistics> query = cb.createQuery(PlayerStatistics.class);

        Root<PlayerStatistics> root = query.from(PlayerStatistics.class);

        query.orderBy(cb.desc(cb.quot(root.get("tripleHits"), root.get("shotsFired"))));

        List<PlayerStatistics> playerStatisticsOrderedByTripleHitsPercentageDesc = session.createQuery(query)
                                                                                            .setFirstResult((page - 1) * PAGE_SIZE)
                                                                                            .setMaxResults(PAGE_SIZE)
                                                                                            .getResultList();

        logger.info("Returning {} player statistics objects ordered by triple hits percentage", playerStatisticsOrderedByTripleHitsPercentageDesc.size());

        return Collections.unmodifiableList(playerStatisticsOrderedByTripleHitsPercentageDesc);
    }
}
