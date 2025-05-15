package guru.qa.niffler.data.repository.impl;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.entity.auth.AuthUserEntity;
import guru.qa.niffler.data.jpa.EntityManagers;
import guru.qa.niffler.data.repository.AuthUserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AuthUserRepositoryHibernate implements AuthUserRepository {

    private static final Config CFG = Config.getInstance();
    private final EntityManager em = EntityManagers.em(CFG.authJdbcUrl());

    @Override
    public AuthUserEntity create(AuthUserEntity authUserEntity) {
        em.joinTransaction();
        em.persist(authUserEntity);
        return authUserEntity;
    }

    @Override
    public Optional<AuthUserEntity> findById(UUID uuid) {
        return Optional.ofNullable(em.find(AuthUserEntity.class, uuid));
    }

    @Override
    public Optional<AuthUserEntity> findByName(String name) {
        try {
         return Optional.of(em.createQuery(
                 "select u from AuthUserEntity u where u.username = :name", AuthUserEntity.class)
                    .setParameter("name", name).getSingleResult());
        }catch (NoResultException e){
            return Optional.empty();
        }
    }

    @Override
    public List<AuthUserEntity> findAll() {
        return List.of();
    }

    @Override
    public void deleteAuthority(AuthUserEntity entity) {

    }

    @Override
    public void deleteUser(AuthUserEntity entity) {

    }
}
