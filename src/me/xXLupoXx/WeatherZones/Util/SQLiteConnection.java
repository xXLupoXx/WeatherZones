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

package me.xXLupoXx.WeatherZones.Util;

import me.xXLupoXx.WeatherZones.WeatherZones;
import me.xXLupoXx.WeatherZones.Zones.Zones;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLiteConnection
{
    WeatherZones plugin;
    Connection conn;
    Statement stat;
    public SQLiteConnection(WeatherZones plugin)
    {
        try
        {
            Class.forName( "org.sqlite.JDBC" );
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        this.plugin = plugin;

    }

    public void setupDB()
    {
        try
        {
            conn = DriverManager.getConnection("jdbc:sqlite:./plugins/WeatherZones/Zones.db");
            stat = conn.createStatement();

            stat.executeUpdate("create table if not exists Zones (world TEXT, name TEXT, owner TEXT, subzones TEXT, flag TEXT, min_x DOUBLE, min_y DOUBLE, min_z DOUBLE, max_x DOUBLE, max_y DOUBLE, max_z DOUBLE);");

            stat.close();
            conn.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void deleteZone(String World, String ZoneName)
    {
        try
        {
            conn = DriverManager.getConnection("jdbc:sqlite:./plugins/WeatherZones/Zones.db");
            stat = conn.createStatement();

            stat.executeUpdate("DELETE FROM Zones Where world = '"+World+"' AND name = '" + ZoneName + "'");

            stat.close();
            conn.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    
    public boolean ZoneExists(String World, String name)
    {
        boolean exists = false;

        try
        {
            conn = DriverManager.getConnection("jdbc:sqlite:./plugins/WeatherZones/Zones.db");
            stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("Select * From Zones Where world = '"+World+"' AND name = '"+name+"'");

            exists = rs.next();

            rs.close();
            stat.close();
            conn.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return exists;
    }
    
    public boolean addZone(String world, String name, String owner, String flag, Vector min, Vector max)
    {
        if(ZoneExists(world,name))
        {
            return false;
        }

        try
        {
            conn = DriverManager.getConnection("jdbc:sqlite:./plugins/WeatherZones/Zones.db");
            stat = conn.createStatement();

            stat.executeUpdate("Insert Into Zones (world, name, owner, flag, min_x, min_y, min_z, max_x, max_y, max_z) VALUES('"+world+"','"+name+"','"+owner+"','"+flag+"','"+min.getX()+"','"+min.getY()+"','"+min.getZ()+"','"+max.getX()+"','"+max.getY()+"','"+max.getZ()+"')");

            stat.close();
            conn.close();

            loadZones(); // Load new Zone to HashMaps

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return true;
    }
    
    public void loadZones()
    {
        Vector min, max;

        try
        {
            conn = DriverManager.getConnection("jdbc:sqlite:./plugins/WeatherZones/Zones.db");
            stat = conn.createStatement();
            ResultSet rs; 
            
            for (World w:plugin.getServer().getWorlds())
            {
                rs = stat.executeQuery("Select * From Zones Where world = '"+w.getName()+"'");
                
                while (rs.next())
                {
                    min = new Vector(rs.getDouble("min_x"),rs.getDouble("min_y"),rs.getDouble("min_z"));
                    max = new Vector(rs.getDouble("max_x"),rs.getDouble("max_y"),rs.getDouble("max_z"));
                    
                    plugin.zoneHandler.WorldZones.get(w).add(new Zones(min,max,rs.getString("name"),rs.getString("owner"),rs.getString("flag")));

                }
                
                rs.close();
            }

            stat.close();
            conn.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
}
