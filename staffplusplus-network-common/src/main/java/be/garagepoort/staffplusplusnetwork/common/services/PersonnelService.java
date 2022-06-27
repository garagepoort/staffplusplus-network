package be.garagepoort.staffplusplusnetwork.common.services;

import be.garagepoort.mcioc.IocBean;
import be.garagepoort.staffplusplusnetwork.common.SppPlayer;
import be.garagepoort.staffplusplusnetwork.common.session.PlayerSettings;
import be.garagepoort.staffplusplusnetwork.common.session.PlayerSettingsSqlRepositoryImpl;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@IocBean
public class PersonnelService {

    private final PlayerSettingsSqlRepositoryImpl playerSettingsSqlRepository;

    public PersonnelService(PlayerSettingsSqlRepositoryImpl playerSettingsSqlRepository) {
        this.playerSettingsSqlRepository = playerSettingsSqlRepository;
    }

    public Map<String, List<SppPlayer>> getStaffOnServers(List<SppPlayer> allPlayers, String staffPermission) {
        List<UUID> allUuids = allPlayers.stream()
            .filter(p -> p.hasPermission(staffPermission))
            .map(SppPlayer::getId)
            .collect(Collectors.toList());

        List<PlayerSettings> settings = playerSettingsSqlRepository.findSettings(allUuids);
        List<UUID> uuidsToHide = settings.stream().filter(PlayerSettings::isVanished).map(PlayerSettings::getUuid).collect(Collectors.toList());

        return allPlayers.stream()
            .filter(p -> !uuidsToHide.contains(p.getId()))
            .collect(Collectors.groupingBy(SppPlayer::getServerName));
    }
}
