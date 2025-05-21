package guru.qa.niffler.data.repository.impl;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.entity.user.FriendshipStatus;
import guru.qa.niffler.data.entity.user.UserEntity;
import guru.qa.niffler.data.jpa.EntityManagers;
import guru.qa.niffler.data.repository.UserRepository;
import jakarta.persistence.EntityManager;

import java.util.Optional;
import java.util.UUID;

public class UdUserRepositoryHibernate implements UserRepository {

    private static final Config CFG = Config.getInstance();
    private final EntityManager em = EntityManagers.em(CFG.userdataJdbcUrl());

    @Override
    public UserEntity create(UserEntity userEntity) {
        em.joinTransaction();
        em.persist(userEntity);
        return userEntity;
    }

    @Override
    public Optional<UserEntity> findById(UUID uuid) {
        return Optional.ofNullable(em.find(UserEntity.class, uuid));
    }

    @Override
    public void addIncomeInvitation(UserEntity requester, UserEntity addressee) {
        em.joinTransaction();
        requester.addFriends(FriendshipStatus.PENDING, requester);
    }

    @Override
    public void addOutcomeInvitation(UserEntity requester, UserEntity addressee) {
        em.joinTransaction();
        requester.addFriends(FriendshipStatus.PENDING, addressee);
    }

    @Override
    public void addFriend(UserEntity requester, UserEntity addressee) {
        em.joinTransaction();
        requester.addFriends(FriendshipStatus.ACCEPTED, addressee);
        addressee.addFriends(FriendshipStatus.ACCEPTED, requester);
    }

    @Override
    public void deleteFriendshipForUser(UserEntity user) {

    }

    @Override
    public void deleteUser(UserEntity user) {

    }
}
