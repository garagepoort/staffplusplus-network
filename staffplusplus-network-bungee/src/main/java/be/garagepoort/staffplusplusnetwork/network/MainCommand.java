package be.garagepoort.staffplusplusnetwork.network;

import be.garagepoort.mcioc.configuration.ConfigProperty;
import be.garagepoort.mcioc.tubingbungee.annotations.IocBungeeCommandHandler;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

import java.util.List;

import static be.garagepoort.staffplusplusnetwork.network.MessageUtil.colorizedComponent;

@IocBungeeCommandHandler
public class MainCommand extends Command {
    public MainCommand(@ConfigProperty("permissions.command.reload") String reloadPermission) {
        super("staffplusplus-network", reloadPermission);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(strings.length == 0 || !strings[0].equalsIgnoreCase("reload")) {
            TextComponent textComponent = new TextComponent("Invalid use of command. Please add 'reload' to reload the plugin");
            textComponent.setColor(ChatColor.RED);
            commandSender.sendMessage(textComponent);
            return;
        }

        StaffPlusPlusBungeePlugin.getPlugin().reload();
        List<BaseComponent> baseComponents = colorizedComponent("&6[staff++ bungee] &creloaded plugin");
        commandSender.sendMessage(baseComponents.toArray(new BaseComponent[0]));

    }
}
