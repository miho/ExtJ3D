/*
 * Copyright (c) 2007 Sun Microsystems, Inc. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistribution of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in
 *   the documentation and/or other materials provided with the
 *   distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN MICROSYSTEMS, INC. ("SUN") AND ITS LICENSORS SHALL
 * NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF
 * USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR
 * ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL,
 * CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND
 * REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF OR
 * INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that this software is not designed, licensed or
 * intended for use in the design, construction, operation or
 * maintenance of any nuclear facility.
 *
 */

package eu.mihosoft.ext.j3d.com.sun.j3d.utils.scenegraph.io.state.javax.media.j3d;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import eu.mihosoft.ext.j3d.javax.media.j3d.ColorInterpolator;
import eu.mihosoft.ext.j3d.javax.media.j3d.Material;
import eu.mihosoft.ext.j3d.javax.media.j3d.SceneGraphObject;
import eu.mihosoft.ext.j3d.javax.vecmath.Color3f;

import eu.mihosoft.ext.j3d.com.sun.j3d.utils.scenegraph.io.retained.Controller;
import eu.mihosoft.ext.j3d.com.sun.j3d.utils.scenegraph.io.retained.SymbolTableData;

public class ColorInterpolatorState extends InterpolatorState {

    private int target;

    public ColorInterpolatorState(SymbolTableData symbol,Controller control) {
        super( symbol, control );

        if (node!=null)
            target = control.getSymbolTable().addReference( ((ColorInterpolator)node).getTarget() );
    }

    @Override
    public void writeObject( DataOutput out ) throws IOException {
        super.writeObject( out );

        out.writeInt( target );
        Color3f clr = new Color3f();
        ((ColorInterpolator)node).getStartColor( clr );
        control.writeColor3f( out, clr );
        ((ColorInterpolator)node).getEndColor( clr );
        control.writeColor3f( out, clr );
    }

    @Override
    public void readObject( DataInput in ) throws IOException {
        super.readObject( in );

        target = in.readInt();
        ((ColorInterpolator)node).setStartColor( control.readColor3f( in ) );
        ((ColorInterpolator)node).setEndColor( control.readColor3f( in ) );
    }

    /**
     * Called when this component reference count is incremented.
     * Allows this component to update the reference count of any components
     * that it references.
     */
    @Override
    public void addSubReference() {
        control.getSymbolTable().incNodeComponentRefCount( target );
    }

    @Override
    public void buildGraph() {
        ((ColorInterpolator)node).setTarget( (Material)control.getSymbolTable().getJ3dNode( target ));
        super.buildGraph(); // Must be last call in method
    }

    @Override
    public SceneGraphObject createNode( Class j3dClass ) {
        return createNode(j3dClass, new Class[] { eu.mihosoft.ext.j3d.javax.media.j3d.Alpha.class,
                                                    eu.mihosoft.ext.j3d.javax.media.j3d.Material.class },
                                      new Object[] { null,
                                                     null } );

    }

    @Override
    protected SceneGraphObject createNode() {
        return new ColorInterpolator( null, null );
    }


}