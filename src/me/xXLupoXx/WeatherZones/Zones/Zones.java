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

import me.xXLupoXx.WeatherZones.Util.WeatherStates.WeatherFlags;
import org.bukkit.util.Vector;

import java.util.logging.Logger;

public class Zones
{
    Vector min,max;
    String flag;
    String name,owner;

    //private final Logger Log = Logger.getLogger("Minecraft");

    public Zones(Vector min, Vector max,String name, String owner, String flag)
    {
        this.min = min;
        this.max = max;
        this.flag = flag;
        this.name = name;
        this.owner = owner;
    }
    
    public boolean isInZone(Vector playerPos)
    {
        return playerPos.isInAABB(min,max);
    }

    public WeatherFlags getFlag()
    {

        for (WeatherFlags f:WeatherFlags.values())
        {


            if(f.getFlagName().equals(flag))
            {
                return f;
            }
        }
        return null;

    }

    public String getName()
    {
        return name;
    }

    
    
}
