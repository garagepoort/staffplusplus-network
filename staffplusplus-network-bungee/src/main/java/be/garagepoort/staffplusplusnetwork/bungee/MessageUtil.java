package be.garagepoort.staffplusplusnetwork.bungee;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.List;

import static net.md_5.bungee.api.ChatColor.translateAlternateColorCodes;

public class MessageUtil {

    public static List<BaseComponent> colorizedComponent(String text) {
        return List.of(TextComponent.fromLegacyText(colorize(text)));
    }

    public static String colorize(String text) {
        return translateAlternateColorCodes('&', text);
    }
}
