package be.garagepoort.staffplusplusnetwork.network;

import be.garagepoort.mcioc.configuration.ConfigProperty;
import be.garagepoort.mcioc.tubingbungee.annotations.IocBungeeCommandHandler;
import be.garagepoort.staffplusplusnetwork.network.common.SppPlayer;
import be.garagepoort.staffplusplusnetwork.network.common.services.PersonnelService;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static be.garagepoort.staffplusplusnetwork.network.MessageUtil.colorizedComponent;

@IocBungeeCommandHandler
public class PersonnelCommand extends Command {

    private final PersonnelService personnelService;

    public PersonnelCommand(@ConfigProperty("commands.personnel") String command,
                            @ConfigProperty("permissions.command.personnel") String permission,
                            PersonnelService personnelService) {
        super(command, permission);
        this.personnelService = personnelService;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        List<SppPlayer> allPlayers = ProxyServer.getInstance().getPlayers().stream()
            .map(BungeeSppPlayer::new)
            .collect(Collectors.toList());

        String personnelText = personnelService.getPersonnelText(allPlayers);
        List<BaseComponent> baseComponents = new ArrayList<>(colorizedComponent(personnelText));
        commandSender.sendMessage(baseComponents.toArray(new BaseComponent[]{}));
    }


}
