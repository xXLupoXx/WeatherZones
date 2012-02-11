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

package me.xXLupoXx.WeatherZones;


import me.xXLupoXx.WeatherZones.Commands.SaveWeatherZone;
import me.xXLupoXx.WeatherZones.Listeners.WeatherZonesPlayerListener;
import me.xXLupoXx.WeatherZones.Util.PlayerSelection;
import me.xXLupoXx.WeatherZones.Util.SQLiteConnection;
import me.xXLupoXx.WeatherZones.Zones.ZoneHandler;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class WeatherZones extends JavaPlugin
{
    public PlayerSelection ps;
    public ZoneHandler zoneHandler;
    public SQLiteConnection sqLiteConnection;
    WeatherZonesPlayerListener wpl;

    PluginDescriptionFile pluginDescription;
    public final Logger Log = Logger.getLogger("Minecraft");
    
    
    public void onDisable()
    {
        Log.info("["+pluginDescription.getName()+" version "+pluginDescription.getVersion()+"]: Disabled!");
    }

    public void onEnable()
    {
        pluginDescription = getDescription();
        sqLiteConnection = new SQLiteConnection(this);
        ps  = new PlayerSelection();
        zoneHandler = new ZoneHandler(this);
        wpl = new WeatherZonesPlayerListener(this);

        getCommand("saveweatherzone").setExecutor(new SaveWeatherZone(this));


        Log.info("["+pluginDescription.getName()+" version "+pluginDescription.getVersion()+"]: Enabled!");


        getServer().getPluginManager().registerEvents(wpl, this);

        sqLiteConnection.setupDB();


    }

}
