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

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;


/**
 * Properties object for query operations.  It is a read-only Map backed
 * up by a Hashtable.
 */
class J3dQueryProps extends AbstractMap {
    private Hashtable table;
    private Set entrySet = null;


    /**
     * Constructs a new J3dQueryProps object using the specified
     * array of keys and the specified values.  The arrays must be
     * the same size.
     */
    J3dQueryProps(ArrayList<String> keys, ArrayList<Object> values) {
	table = new Hashtable();
	for (int i = 0; i < keys.size(); i++) {
		table.put(keys.get(i), values.get(i));
	}
    }

    /**
     * Gets value corresponding to specified key
     */
    @Override
    public Object get(Object key) {
	return table.get(key);
    }

    /**
     * Returns true if the specified key is contained in this Map
     */
    @Override
    public boolean containsKey(Object key) {
	return table.containsKey(key);
    }

    /**
     * Returns true if the specified value is contained in this Map
     */
    @Override
    public boolean containsValue(Object value) {
	return table.containsValue(value);
    }

    /**
     * Returns a new Set object for the entries of this map
     */
    @Override
    public Set entrySet() {
	if (entrySet == null)
	    entrySet = new EntrySet();

	return entrySet;
    }


    /**
     * Entry set class
     */
    private class EntrySet extends AbstractSet {
	private EntrySet() {
	}

	@Override
	public int size() {
	    return table.size();
	}

	@Override
	public Iterator iterator() {
	    return new MapIterator();
	}
    }


    /**
     * Entry set class
     */
    private class MapIterator implements Iterator {
	private Iterator i;

	private MapIterator() {
	    i = table.entrySet().iterator();
	}

	@Override
	public boolean hasNext() {
	    return i.hasNext();
	}

	@Override
	public Object next() {
	    return i.next();
	}

	@Override
	public void remove() {
	    throw new UnsupportedOperationException();
	}
    }
}
