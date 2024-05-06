package com.unclecole.colepercentday.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.logging.Level;

public enum TL {
	NO_PERMISSION("messages.no-permission", "&c&lERROR! &fYou don't have the permission to do that."),
	INVALID_ARGUMENT_NUMBER("messages.invalid-number", "&c&lERROR: &c'<argument>' has to be a number"),
	INVALID_ARGUMENT_PLAYER("messages.invalid-player", "&c&lERROR: &c'<argument>' has to be a player"),
	PLAYER_ONLY("messages.player-only", "&cThis command is for players only!"),
	ALREADY_VOTED("messages.already-voted", "&c&lERROR! &fYou already voted for day!"),
	CANT_VOTE_DAY("messages.cant-vote-day", "&c&lERROR! &fYou can't vote, its already day."),
	SUCCESSFULLY_VOTED("messages.successfully-voted", "&a&lSUCCESS! &fYou successfully voted for day!");


	private String path, def;
	private static ConfigFile config;

	TL(String path, String start) {
		this.path = path;
		this.def = start;
	}

	public String getDefault() {
		return this.def;
	}

	public String getPath() {
		return this.path;
	}

	public void setDefault(String message) {
		this.def = message;
	}

	public static void loadMessages(ConfigFile configFile) {
		config = configFile;
		FileConfiguration data = configFile.getConfig();
		for (TL message : values()) {
			if (!data.contains(message.getPath())) {
				data.set(message.getPath(), message.getDefault());
			}
		}
		configFile.save();
	}

	public void send(CommandSender sender) {
		if (sender instanceof org.bukkit.entity.Player) {
			sender.sendMessage(C.color(getDefault()));
		} else {
			sender.sendMessage(C.strip(getDefault()));
		}
	}

	public void send(CommandSender sender, PlaceHolder... placeHolders) {
		if (sender instanceof org.bukkit.entity.Player) {
			sender.sendMessage(C.color(getDefault(), placeHolders));
		} else {
			sender.sendMessage(C.strip(getDefault(), placeHolders));
		}
	}

	public static void message(CommandSender sender, String message) {
		sender.sendMessage(C.color(message));
	}

	public static void message(CommandSender sender, String message, PlaceHolder... placeHolders) {
		sender.sendMessage(C.color(message, placeHolders));
	}

	public static void message(CommandSender sender, List<String> message) {
		message.forEach(m -> sender.sendMessage(C.color(m)));
	}

	public static void message(CommandSender sender, List<String> message, PlaceHolder... placeHolders) {
		message.forEach(m -> sender.sendMessage(C.color(m, placeHolders)));
	}

	public static void log(Level lvl, String message) {
		Bukkit.getLogger().log(lvl, message);
	}
}
