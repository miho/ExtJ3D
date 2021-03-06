/*
 * Copyright 1999-2008 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 *
 */

package eu.mihosoft.ext.j3d.javax.media.j3d;


/**
 * The GeometryUpdater interface is used in updating geometry data
 * that is accessed by reference from a live or compiled GeometryArray
 * object.  Applications that wish to modify such data must define a
 * class that implements this interface.  An instance of that class is
 * then passed to the <code>updateData</code> method of the
 * GeometryArray object to be modified.
 *
 * @since Java 3D 1.2
 */

public interface GeometryUpdater {
    /**
     * Updates geometry data that is accessed by reference.
     * This method is called by the updateData method of a
     * GeometryArray object to effect
     * safe updates to vertex data that
     * is referenced by that object.  Applications that wish to modify
     * such data must implement this method and perform all updates
     * within it.
     * <br>
     * NOTE: Applications should <i>not</i> call this method directly.
     *
     * @param geometry the Geometry object being updated.
     * @see GeometryArray#updateData
     */
    public void updateData(Geometry geometry);
}
