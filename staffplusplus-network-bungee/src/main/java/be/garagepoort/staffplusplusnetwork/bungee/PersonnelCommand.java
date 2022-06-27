package be.garagepoort.staffplusplusnetwork.bungee;

import be.garagepoort.mcioc.configuration.ConfigProperty;
import be.garagepoort.mcioc.tubingbungee.annotations.IocBungeeCommandHandler;
import be.garagepoort.staffplusplusnetwork.common.SppPlayer;
import be.garagepoort.staffplusplusnetwork.common.services.PersonnelService;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static be.garagepoort.staffplusplusnetwork.bungee.MessageUtil.colorizedComponent;

@IocBungeeCommandHandler
public class PersonnelCommand extends Command {

    @ConfigProperty("permissions.member")
    private String memberPermission;
    @ConfigProperty("messages.personnel.server-line")
    private String serverLineMessage;
    @ConfigProperty("messages.personnel.player-line")
    private String playerLineMessage;

    private final PersonnelService personnelService;

    public PersonnelCommand(@ConfigProperty("commands.personnel") String command, PersonnelService personnelService) {
        super(command);
        this.personnelService = personnelService;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        List<SppPlayer> allPlayers = ProxyServer.getInstance().getPlayers().stream()
            .map(BungeeSppPlayer::new)
            .collect(Collectors.toList());

        Map<String, List<SppPlayer>> staffOnServers = personnelService.getStaffOnServers(allPlayers, memberPermission);
        List<BaseComponent> baseComponents = new ArrayList<>();
        staffOnServers.forEach((serverName, players) -> {
            baseComponents.addAll(colorizedComponent(serverLineMessage.replace("%server%", serverName)));
            players.stream()
                .map(player -> colorizedComponent(playerLineMessage.replace("%name%", player.getUsername())))
                .forEach(baseComponents::addAll);
        });
        commandSender.sendMessage(baseComponents.toArray(new BaseComponent[]{}));
    }


}
