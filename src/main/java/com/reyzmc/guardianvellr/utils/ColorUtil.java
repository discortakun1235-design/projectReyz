package com.namamu.guardiansvaller.utils;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public class ColorUtil {

    /
    public static String colorize(String text) {
        if (text == null) {
            return "";
        }
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    
    public static List<String> colorize(List<String> textList) {
        if (textList == null) {
            return null;
        }
        return textList.stream()
                .map(ColorUtil::colorize)
                .collect(Collectors.toList());
    }
}
