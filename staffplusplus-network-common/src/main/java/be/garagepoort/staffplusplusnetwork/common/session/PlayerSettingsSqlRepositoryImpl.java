package be.garagepoort.staffplusplusnetwork.common.session;

import be.garagepoort.mcioc.IocBean;
import be.garagepoort.mcsqlmigrations.helpers.QueryBuilderFactory;
import net.shortninja.staffplusplus.vanish.VanishType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@IocBean
public class PlayerSettingsSqlRepositoryImpl {
    private final QueryBuilderFactory query;

    public PlayerSettingsSqlRepositoryImpl(QueryBuilderFactory query) {
        this.query = query;
    }

    public Optional<PlayerSettings> findSettings(UUID uuid) {
        return query.create().findOne("SELECT * FROM sp_sessions WHERE player_uuid = ?", (ps) -> {
            ps.setString(1, uuid.toString());
        }, this::buildSessionEntity);
    }

    public List<PlayerSettings> findSettings(List<UUID> uuids) {
        if (uuids.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> questionMarks = uuids.stream().map(p -> "?").collect(Collectors.toList());
        String query = "SELECT * FROM sp_sessions WHERE player_uuid in (%s) ";

        return this.query.create().find(String.format(query, String.join(", ", questionMarks)),
            (ps) -> {
                int index = 1;
                for (UUID uuid : uuids) {
                    ps.setString(index, uuid.toString());
                    index++;
                }
            }, this::buildSessionEntity);
    }

    private PlayerSettings buildSessionEntity(ResultSet rs) throws SQLException {
        UUID playerUuid = UUID.fromString(rs.getString("player_uuid"));
        VanishType vanishType = VanishType.valueOf(rs.getString("vanish_type"));
        boolean inStaffMode = rs.getBoolean("in_staff_mode");
        return new PlayerSettings(
            playerUuid,
            vanishType,
            inStaffMode);
    }
}
