package lc.auth2;

import com.connorlinfoot.titleapi.TitleAPI;
import lc.core2.utils.Util;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayersListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        if(!MYSQL.playerexits(p.getName()))
            TitleAPI.sendTitle(p, 0, 20 * 10, 0, Util.colorize("&a&lREGISTRATE"), "&fUsa /register <clave> <clave>");
        else
            TitleAPI.sendTitle(p, 0, 20 * 10, 0, Util.colorize("&a&lLOGUEATE"), "&fUsa /login <clave>");

    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        event.setTo(event.getFrom());
    }

}
