package guru.qa.niffler.data.repository.impl;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.dao.impl.jdbc.UdUserDAOJdbc;
import guru.qa.niffler.data.dao.interfaces.UserDao;
import guru.qa.niffler.data.entity.user.FriendshipEntity;
import guru.qa.niffler.data.entity.user.FriendshipStatus;
import guru.qa.niffler.data.entity.user.UserEntity;
import guru.qa.niffler.data.enums.CurrencyValues;
import guru.qa.niffler.data.repository.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static guru.qa.niffler.data.tpl.Connections.holder;

public class UdUserRepositoryJdbc implements UserRepository {

    private static final Config CFG = Config.getInstance();
    private final UserDao userdataUserDAO = new UdUserDAOJdbc();

    @Override
    public UserEntity create(UserEntity user) {
        return userdataUserDAO.create(user);
    }

    @Override
    public Optional<UserEntity> findById(UUID uuid) {
        Optional<UserEntity> userOpt = userdataUserDAO.findById(uuid);
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }
        UserEntity userEntity = userOpt.get();

        try (PreparedStatement friendshipRequestsPs = holder(CFG.userdataJdbcUrl()).connection()
                .prepareStatement("SELECT * FROM friendship f " +
                        "JOIN \"user\" u ON f.addressee_id = u.id " +
                        "WHERE requester_id = ?");
             PreparedStatement friendshipInvitationsPs = holder(CFG.userdataJdbcUrl()).connection()
                     .prepareStatement("SELECT * FROM friendship f " +
                             "JOIN \"user\" u ON f.requester_id = u.id " +
                             "WHERE addressee_id = ? AND status = ? ")
        ) {
            friendshipRequestsPs.setObject(1, uuid);
            friendshipRequestsPs.execute();
            List<FriendshipEntity> friendshipEntities = getFriendshipEntities(friendshipRequestsPs, userEntity);

            friendshipInvitationsPs.setObject(1, uuid);
            friendshipInvitationsPs.setString(2, FriendshipStatus.PENDING.name());
            friendshipInvitationsPs.execute();
            List<FriendshipEntity> friendshipInvEntities = getFriendshipInvEntities(friendshipInvitationsPs, userEntity);

            userEntity.setFriendshipRequests(friendshipEntities);
            userEntity.setFriendshipAddressees(friendshipInvEntities);
            return Optional.of(userEntity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void addIncomeInvitation(UserEntity requester, UserEntity addressee) {
        try (PreparedStatement ps = holder(CFG.userdataJdbcUrl()).connection().prepareStatement(
                "INSERT INTO friendship (requester_id, addressee_id, status, created_date) " +
                        "VALUES (?,?,?,?)"
        )) {
            ps.setObject(1, requester.getId());
            ps.setObject(2, addressee.getId());
            ps.setString(3, FriendshipStatus.PENDING.name());
            ps.setDate(4, new Date(System.currentTimeMillis()));

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void addOutcomeInvitation(UserEntity requester, UserEntity addressee) {

        try (PreparedStatement ps = holder(CFG.userdataJdbcUrl()).connection().prepareStatement(
                "INSERT INTO friendship (requester_id, addressee_id, status, created_date) " +
                        "VALUES (?,?,?,?)"
        )) {
            ps.setObject(1, requester.getId());
            ps.setObject(2, addressee.getId());
            ps.setString(3, FriendshipStatus.PENDING.name());
            ps.setDate(4, new Date(System.currentTimeMillis()));

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void addFriend(UserEntity requester, UserEntity addressee) {
        String friendshipSql = "INSERT INTO friendship (requester_id, addressee_id, status, created_date) VALUES (?,?,?,?)";
        try (PreparedStatement sentByRequester = holder(CFG.userdataJdbcUrl()).connection().prepareStatement(
                friendshipSql);
             PreparedStatement acceptedByAddressee = holder(CFG.userdataJdbcUrl()).connection().prepareStatement(
                     friendshipSql)
        ) {
            sentByRequester.setObject(1, requester.getId());
            sentByRequester.setObject(2, addressee.getId());
            sentByRequester.setString(3, FriendshipStatus.ACCEPTED.name());
            sentByRequester.setDate(4, new Date(System.currentTimeMillis()));
            sentByRequester.executeUpdate();

            acceptedByAddressee.setObject(1, addressee.getId());
            acceptedByAddressee.setObject(2, requester.getId());
            acceptedByAddressee.setString(3, FriendshipStatus.ACCEPTED.name());
            acceptedByAddressee.setDate(4, new Date(System.currentTimeMillis()));
            acceptedByAddressee.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserEntity update(UserEntity user) {
        try (PreparedStatement userPs = holder(CFG.userdataJdbcUrl()).connection().prepareStatement(
                "UPDATE \"user\" SET " +
                        "username = ?," +
                        "currency = ?," +
                        "firstname = ?," +
                        "surname = ?," +
                        "photo = ?," +
                        "photo_small = ?," +
                        "full_name = ? " +
                        "WHERE id = ?");
             PreparedStatement friendShipPs = holder(CFG.userdataJdbcUrl()).connection().prepareStatement(
                     "INSERT INTO friendship (requester_id, addressee_id, status) " +
                             "VALUES (?, ?, ?) " +
                             "ON CONFLICT (requester_id, addressee_id) " +
                             "DO UPDATE SET status = ?, created_date = NOW()"
             )) {
            userPs.setString(1, user.getUsername());
            userPs.setString(2, user.getCurrency().name());
            userPs.setString(3, user.getFirstName());
            userPs.setString(4, user.getSurname());
            userPs.setBytes(5, user.getPhoto());
            userPs.setBytes(6, user.getPhotoSmall());
            userPs.setString(7, user.getFullName());
            userPs.setObject(8, user.getId());
            userPs.executeUpdate();

            for (FriendshipEntity fe : user.getFriendshipRequests()) {
                friendShipPs.setObject(1, fe.getRequester().getId());
                friendShipPs.setObject(2, fe.getAddressee().getId());
                friendShipPs.setString(3, fe.getStatus().name());
                friendShipPs.setString(4, fe.getStatus().name());
                friendShipPs.clearParameters();
            }

            for (FriendshipEntity fe : user.getFriendshipAddressees()) {
                friendShipPs.setObject(1, fe.getRequester().getId());
                friendShipPs.setObject(2, fe.getAddressee().getId());
                friendShipPs.setString(3, fe.getStatus().name());
                friendShipPs.setString(4, fe.getStatus().name());
                friendShipPs.clearParameters();
            }
            friendShipPs.executeBatch();

            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteFriendshipForUser(UserEntity user) {
        try (PreparedStatement ps = holder(CFG.userdataJdbcUrl()).connection().prepareStatement(
                "DELETE FROM friendship \n" +
                        "WHERE requester_id = ? \n" +
                        "OR addressee_id = ?;"

        )) {
            ps.setObject(1, user.getId());
            ps.setObject(2, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUser(UserEntity user) {
        try (PreparedStatement ps = holder(CFG.userdataJdbcUrl()).connection().prepareStatement(
                "delete from \"user\" where id = ?"

        )) {
            ps.setObject(1, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeFriendship(UserEntity user) {
        try (PreparedStatement ps = holder(CFG.userdataJdbcUrl()).connection().prepareStatement(
                "WITH deleteted_friendship AS " +
                        "(DELETE FROM friendship WHERE requester_id = ? OR addressee_id = ?)" +
                        "DELETE FROM \"user\" WHERE id = ?"
        )) {
            ps.setObject(1, user.getId());
            ps.setObject(2, user.getId());
            ps.setObject(3, user.getId());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static FriendshipEntity fillFriendshipEntity(ResultSet rs, UserEntity requester, UserEntity addressee) throws SQLException {
        FriendshipEntity fe = new FriendshipEntity();
        fe.setStatus(FriendshipStatus.valueOf(rs.getString("status")));
        fe.setCreatedDate(rs.getDate("created_date"));
        fe.setRequester(requester);
        fe.setAddressee(addressee);
        return fe;
    }
    private static UserEntity fillUserEntity(ResultSet rs) throws SQLException {
        UserEntity ue = new UserEntity();
        ue.setId(rs.getObject("id", UUID.class));
        ue.setUsername(rs.getString("username"));
        ue.setCurrency(CurrencyValues.valueOf(rs.getString("currency")));
        ue.setFirstName(rs.getString("firstname"));
        ue.setSurname(rs.getString("surname"));
        ue.setPhoto(rs.getBytes("photo"));
        ue.setPhotoSmall(rs.getBytes("photo_small"));
        ue.setFullName(rs.getString("full_name"));
        return ue;
    }

    private static List<FriendshipEntity> getFriendshipInvEntities(PreparedStatement friendshipInvitationsPs, UserEntity ue) throws SQLException {
        List<FriendshipEntity> friendshipInvEntities = new ArrayList<>();
        try (ResultSet rs = friendshipInvitationsPs.getResultSet()) {
            while (rs.next()) {
                UserEntity requester = fillUserEntity(rs);
                FriendshipEntity fe = fillFriendshipEntity(rs, requester, ue);
                friendshipInvEntities.add(fe);
            }
        }
        return friendshipInvEntities;
    }

    private static List<FriendshipEntity> getFriendshipEntities(PreparedStatement friendshipRequestsPs, UserEntity ue) throws SQLException {
        List<FriendshipEntity> friendshipEntities = new ArrayList<>();
        try (ResultSet rs = friendshipRequestsPs.getResultSet()) {
            while (rs.next()) {
                UserEntity addressee = fillUserEntity(rs);
                FriendshipEntity fe = fillFriendshipEntity(rs, ue, addressee);
                friendshipEntities.add(fe);
            }
        }
        return friendshipEntities;
    }

//    @Override
//    public List<UserEntity> findByUsername(String username) {
//        List<UserEntity> users = new ArrayList<>();
//        try (PreparedStatement ps = connection.prepareStatement(
//                "SELECT * FROM user WHERE username=?")) {
//            ps.setString(1, username);
//            try (ResultSet rs = ps.executeQuery()) {
//                while (rs.next()) {
//                    UserEntity ue = new UserEntity();
//                    ue.setId(rs.getObject("id", UUID.class));
//                    ue.setUsername(rs.getString("username"));
//                    ue.setCurrency(CurrencyValues.valueOf(rs.getString("currency")));
//                    ue.setSurname(rs.getString("surname"));
//                    ue.setPhoto(rs.getBytes("photo"));
//                    ue.setPhotoSmall(rs.getBytes("photo_small"));
//                    ue.setFullName(rs.getString("full_name"));
//                    users.add(ue);
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return users;
//    }
//
//    @Override
//    public void deleteUser(UserEntity user) {
//        try (PreparedStatement ps = connection.prepareStatement(
//                "DELETE FROM user WHERE id=?")) {
//            ps.setObject(1, user.getId());
//            ps.execute();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
}