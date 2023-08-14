import net.minecraft.server.v1_20_R1.PacketPlayInFlying;
import net.minecraft.server.v1_20_R1.PacketPlayInUseItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getLogger().info("HELLO EPTA");
        getServer.getPluginManager().registerEvents(this, this);

    }

    @Override
    public void onDisable() {
        getServer.getLogger().info("GOODBYE EPTA");
        getServer.getPluginManager().registerEvents(this, this);

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getClickedBlock()!= null && event.getClickedBlock().getType() == Material.STONE_BUTTON) {
            CraftPlayer craftPlayer = (CraftPlayer) player;
            PacketPlayInUseItem packet = new PacketPlayInUseItem(PacketPlayInUseItem.EnumClickAction.SWAP, event.getClickedBlock().getLocation().getBlockX(), event.getClickedBlock().getLocation().getBlockY(), event.getClickedBlock().getLocation().getBlockZ(), event.getClickedBlock().getLocation().getBlockFace());
            craftPlayer.getHandle().playerConnection.sendPacket(packet);
            player.sendMessage(ChatColor.RED + "Hello, world!");
        }
    }

    @EventHandler
    public void handleJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage(ChatColor.RED + "Hello, world!");
        }
    }
}