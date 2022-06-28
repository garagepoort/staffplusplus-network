package be.garagepoort.staffplusplusnetwork.network.common.services;

import be.garagepoort.mcioc.IocBean;
import be.garagepoort.mcioc.configuration.ConfigProperty;
import be.garagepoort.staffplusplusnetwork.network.common.SppPlayer;
import be.garagepoort.staffplusplusnetwork.network.common.session.PlayerSettings;
import be.garagepoort.staffplusplusnetwork.network.common.session.PlayerSettingsSqlRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@IocBean
public class PersonnelService {

    @ConfigProperty("permissions.member")
    private String memberPermission;
    @ConfigProperty("messages.personnel.header")
    private String headerMessage;
    @ConfigProperty("messages.personnel.footer")
    private String footerMessage;
    @ConfigProperty("messages.personnel.server-line")
    private String serverLineMessage;
    @ConfigProperty("messages.personnel.player-line")
    private String playerLineMessage;
    @ConfigProperty("messages.personnel.no-results")
    private String noResultsMessage;

    private final PlayerSettingsSqlRepositoryImpl playerSettingsSqlRepository;

    public PersonnelService(PlayerSettingsSqlRepositoryImpl playerSettingsSqlRepository) {
        this.playerSettingsSqlRepository = playerSettingsSqlRepository;
    }

    public String getPersonnelText(List<SppPlayer> allPlayers) {
        Map<String, List<SppPlayer>> staffOnServers = getStaffOnServers(allPlayers);
        if (staffOnServers.isEmpty()) {
            return noResultsMessage;
        }

        List<String> result = new ArrayList<>();
        if (isNotEmpty(headerMessage)) {
            result.add(headerMessage);
        }
        staffOnServers.forEach((serverName, players) -> {
            if (isNotEmpty(serverLineMessage)) {
                result.add(serverLineMessage.replace("%server%", serverName));
            }
            for (SppPlayer player : players)
                result.add(playerLineMessage
                    .replace("%server%", serverName)
                    .replace("%name%", player.getUsername()));
        });
        if (isNotEmpty(footerMessage)) {
            result.add(footerMessage);
        }
        return String.join("\n", result);
    }

    private boolean isNotEmpty(String message) {
        return message != null && !message.equals("");
    }

    public Map<String, List<SppPlayer>> getStaffOnServers(List<SppPlayer> allPlayers) {
        List<SppPlayer> staffPlayers = allPlayers.stream()
            .filter(p -> p.hasPermission(memberPermission))
            .collect(Collectors.toList());
        List<UUID> staffUuids = staffPlayers.stream()
            .map(SppPlayer::getId)
            .collect(Collectors.toList());

        List<PlayerSettings> settings = playerSettingsSqlRepository.findSettings(staffUuids);
        List<UUID> uuidsToHide = settings.stream().filter(PlayerSettings::isVanished).map(PlayerSettings::getUuid).collect(Collectors.toList());

        return staffPlayers.stream()
            .filter(player -> !uuidsToHide.contains(player.getId()))
            .collect(Collectors.groupingBy(SppPlayer::getServerName));
    }
}
