package com.destrat.ThrowStones;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getLogger().info("HELLO EPTA");
        getServer().getPluginManager().registerEvents(this, this);

    }

    @Override
    public void onDisable() {
        getServer().getLogger().info("GOODBYE EPTA");
        getServer().getPluginManager().registerEvents(this, this);

    }

    @EventHandler
    public void handleJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage(ChatColor.RED + "Hello, world!");
    }

    @EventHandler
    public void ClickOnButton(PlayerInteractEvent event) {
        // Проверяем, что игрок нажал на каменную кнопку
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.STONE_BUTTON) {
            // Проверяем, что игрок зажал Shift
            if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR && event.getPlayer().isSneaking()) {
                // Получаем блок, на котором было нажато событие
                Block block = event.getClickedBlock();
                // Получаем данные блока
                BlockData blockData = block.getBlockData();
                // Проверяем, что кнопка размещена на полу
                if (blockData.getMaterial() == Material.STONE_BUTTON) {
                    // Удаляем каменную кнопку
                    block.breakNaturally();
                    // Выводим сообщение в чат
                    //event.getPlayer().sendMessage("Каменная кнопка удалена!");
                }
            }
        }
    }

    @EventHandler
    public void AirClick(PlayerInteractEvent event) {
        // Проверяем, что игрок нажал на каменную кнопку

        if (event.getAction() == Action.RIGHT_CLICK_AIR && event.getPlayer().getInventory().getItemInMainHand().getType() == Material.STONE_BUTTON && event.getPlayer().isSneaking()) {
            // Получаем направление взгляда игрока
            Player player = event.getPlayer();
            Location playerLocation = event.getPlayer().getLocation();
            // Создаем ArmorStand с кнопкой в качестве шлема
            ArmorStand armorStand = (ArmorStand) getServer().getWorlds().get(0).spawnEntity(playerLocation.add(0, 1, 0), EntityType.ARMOR_STAND);
            armorStand.setMarker(true);
            armorStand.setVisible(false);
            armorStand.setSmall(true);
            //armorStand.setHeadPose();
            //armorStand.setGravity(true);
            armorStand.setHelmet(new ItemStack(Material.STONE_BUTTON));
            // Добавляем звук
            armorStand.getWorld().playSound(armorStand.getLocation(), Sound.ITEM_BUNDLE_DROP_CONTENTS, 1, 1);
            event.getPlayer().getInventory().remove(Material.STONE_BUTTON);
            Location eyeLocation = player.getEyeLocation();
            Vector direction = eyeLocation.getDirection();
            // Создание новой стрелы
            Snowball arrow = player.getWorld().spawn(eyeLocation.add(0, 1, 0), Snowball.class);
            arrow.setCustomName("Камень");
            arrow.setCustomNameVisible(false);
            arrow.setVelocity(direction.multiply(2));
            armorStand.setVelocity(direction.multiply(2));
            arrow.setPassenger(armorStand);


        }
    }

    @EventHandler
    public void onArrowHit(ProjectileHitEvent event) {
        // Проверка, что произошло столкновение с блоком
        if (event.getEntity() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getEntity();
            // Проверка, что стрела касалась земли
            if (arrow.getLocation().getBlock().getType() == Material.AIR && arrow.getName().equals("Камень")) {
                // Удаление стрелы
                arrow.remove();
            }
        }
    }
}
