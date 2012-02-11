/*
 * Copyright (c) <2012> <xXLupoXx>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package me.xXLupoXx.WeatherZones.Listeners;

import me.xXLupoXx.WeatherZones.Util.Selection;
import me.xXLupoXx.WeatherZones.Util.WeatherStates.WeatherFlags;
import me.xXLupoXx.WeatherZones.WeatherZones;
import me.xXLupoXx.WeatherZones.Zones.Zones;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet70Bed;
import org.bukkit.Material;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

import java.util.logging.Logger;




public class WeatherZonesPlayerListener implements Listener
{

    Vector pt1;
    Vector pt2;
    Vector min, max;
    Zones zone;
    
    WeatherZones plugin;

    private final Logger Log = Logger.getLogger("Minecraft");

    public WeatherZonesPlayerListener(WeatherZones weatherZones)
    {
        plugin = weatherZones;
    }

    
    @EventHandler()
    public void onPlayerMove(final PlayerMoveEvent event)
    {
        EntityPlayer playerHandle = ((CraftPlayer)event.getPlayer()).getHandle();
        Player player = event.getPlayer();
        zone = plugin.zoneHandler.getZoneAt(player.getLocation());
        if(zone != null)
        {


           plugin.zoneHandler.PlayerInZone.put(player,true);
            //Log.info(zone.getFlag().getFlagName());
           if(zone.getFlag() == WeatherFlags.Rain)
           {
               playerHandle.netServerHandler.sendPacket(new Packet70Bed(WeatherFlags.Rain.getCode(),0));
           }
        } 
        else
        {
           if(plugin.zoneHandler.PlayerInZone.get(player))
           {
               playerHandle.netServerHandler.sendPacket(new Packet70Bed(WeatherFlags.Sunny.getCode(),0));
               plugin.zoneHandler.PlayerInZone.put(player,false);
           }
        }
        
        
           /* if(event.getPlayer().getLocation().toVector().isInAABB(min,max)){

                playerHandle.netServerHandler.sendPacket(new Packet70Bed(1,0));

            }  else {

                playerHandle.netServerHandler.sendPacket(new Packet70Bed(2,0));
            }*/
        
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        if(plugin.ps.PlayerMap.get(event.getPlayer()) == null)
        {
            plugin.ps.PlayerMap.put(event.getPlayer(),new Selection());
        }
        if(plugin.zoneHandler.PlayerInZone.get(event.getPlayer()) == null)
        {
            plugin.zoneHandler.PlayerInZone.put(event.getPlayer(),false);
        }
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        if(plugin.ps.PlayerMap.get(event.getPlayer()) != null)
        {
            plugin.ps.PlayerMap.remove(event.getPlayer());
        }
        if(plugin.zoneHandler.PlayerInZone.get(event.getPlayer()) != null)
        {
            plugin.zoneHandler.PlayerInZone.remove(event.getPlayer());
        }
    }



    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteract(final PlayerInteractEvent event)
    {
        Player player = event.getPlayer();

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
        {
            if(player.getItemInHand().getType() == Material.ARROW)
            {
               plugin.ps.PlayerMap.get(player).pt1 = player.getTargetBlock(null,100).getLocation().toVector();
               Log.info("----PT1 Set----");
            }
        }
        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK))
        {
            if(player.getItemInHand().getType() == Material.ARROW)
            {
                plugin.ps.PlayerMap.get(player).pt2 = player.getTargetBlock(null,100).getLocation().toVector();
                Log.info("----PT2 Set----");
            }
        }
    }

}
