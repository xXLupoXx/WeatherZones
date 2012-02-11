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

package me.xXLupoXx.WeatherZones.Zones;

import me.xXLupoXx.WeatherZones.WeatherZones;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.logging.Logger;

public class ZoneHandler
{
    public Map <org.bukkit.World,List<Zones>> WorldZones = new HashMap<World, List<Zones>>();
    public Map <Player,Boolean> PlayerInZone = new HashMap<Player, Boolean>();
    
    WeatherZones plugin;

    //public final Logger Log = Logger.getLogger("Minecraft");

    public ZoneHandler(WeatherZones plugin)
    {
        this.plugin = plugin;

        for(World w:plugin.getServer().getWorlds())
        {
            WorldZones.put(w,new LinkedList<Zones>());
        }
    }

    public Zones getZoneAt(Location loc)
    {
        if(WorldZones.get(loc.getWorld()) != null)
        {
            List<Zones> zonesList = WorldZones.get(loc.getWorld());
            Iterator<Zones> zonesIterator = zonesList.iterator();
            Zones temp;


            while (zonesIterator.hasNext())
            {
                temp = zonesIterator.next();
                if(temp.isInZone(loc.toVector()))
                {
                    return temp;
                }
            }
        }
            return null;

    }
    
    public boolean ZoneExists(Location loc,String name)
    {
        if(WorldZones.get(loc.getWorld()) != null)
        {
            List<Zones> zonesList = WorldZones.get(loc.getWorld());
            Iterator<Zones> zonesIterator = zonesList.iterator();
            Zones temp;
            while (zonesIterator.hasNext())
            {
                temp = zonesIterator.next();
                if(temp.getName().equals(name))
                {
                    return true;
                }
            }
        }
        return false;
    }
}
