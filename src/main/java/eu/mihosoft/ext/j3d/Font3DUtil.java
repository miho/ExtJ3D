/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.ext.j3d;

import eu.mihosoft.ext.j3d.javax.media.j3d.Font3D;
import eu.mihosoft.ext.j3d.javax.media.j3d.FontExtrusion;
import eu.mihosoft.ext.j3d.javax.media.j3d.GeometryArray;
import eu.mihosoft.ext.j3d.javax.media.j3d.TriangleArray;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class Font3DUtil {

    private Font3DUtil() {
        throw new AssertionError("Don't instantiate me!");
    }

    public static TriangleArray charToPolygon(char c, Font font, double depth) {
        return charToPolygon(c, font, depth, 0.0001);
    }

    public static TriangleArray charToPolygon(char c, Font font, double depth, double resolution) {

        Font3D f3d = new Font3D(font, resolution, new FontExtrusion());

        GeometryArray ga = f3d.getGlyphGeometry(c);

        if (ga instanceof TriangleArray) {
            return (TriangleArray) ga;
        } else {
            Logger.getLogger(Font3DUtil.class.getName()).
                    log(Level.SEVERE,
                            "Error: cannot create polygons, "
                            + "since geometry array is unsupported: " + ga);
        }

        return null;
    }
}
