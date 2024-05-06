package com.unclecole.colepercentday.cmd.subs;

import com.unclecole.colepercentday.ColePercentDay;
import com.unclecole.colepercentday.cmd.AbstractCommand;
import com.unclecole.colepercentday.utils.C;
import com.unclecole.colepercentday.utils.TL;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class VoteDayCmd extends AbstractCommand {

    public VoteDayCmd() {
        super("voteday", true);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        if(Bukkit.getWorld(ColePercentDay.getInstance().getWorld()).getTime() < 13000) {
            TL.CANT_VOTE_DAY.send(player);
            return false;
        }

        if(ColePercentDay.getInstance().getVoteDay().contains(player.getUniqueId())) {
            TL.ALREADY_VOTED.send(player);
            return false;
        }

        ColePercentDay.getInstance().getVoteDay().add(player.getUniqueId());
        TL.SUCCESSFULLY_VOTED.send(player);
        Bukkit.broadcastMessage(C.color(ColePercentDay.getInstance().getVoteMessage())
                .replace("%player%", player.getName())
                .replace("%votes%", ColePercentDay.getInstance().getVoteDay().size() + "")
                .replace("%percent%", ((int) Math.ceil(Bukkit.getOnlinePlayers().size()*0.25))+""));
        return false;
    }

    @Override
    public String getDescription() {
        return "Command to vote for day";
    }

    @Override
    public String getPermission() {
        return "voteday.vote";
    }
}
