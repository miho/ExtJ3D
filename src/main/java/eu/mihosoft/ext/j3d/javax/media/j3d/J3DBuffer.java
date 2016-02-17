/*
 * Copyright 2001-2008 Sun Microsystems, Inc.  All Rights Reserved.
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

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

/**
 * Java 3D wrapper class for java.nio.Buffer objects.
 * When used to wrap a non-null NIO buffer object, this class will
 * create a read-only view of the wrapped NIO buffer, and will call
 * <code>rewind</code> on the read-only view, so that elements 0
 * through <code>buffer.limit()-1</code> will be available internally.
 *
 * @see GeometryArray#setCoordRefBuffer(J3DBuffer)
 * @see GeometryArray#setColorRefBuffer(J3DBuffer)
 * @see GeometryArray#setNormalRefBuffer(J3DBuffer)
 * @see GeometryArray#setTexCoordRefBuffer(int,J3DBuffer)
 * @see GeometryArray#setVertexAttrRefBuffer(int,J3DBuffer)
 * @see GeometryArray#setInterleavedVertexBuffer(J3DBuffer)
 * @see CompressedGeometry#CompressedGeometry(CompressedGeometryHeader,J3DBuffer)
 *
 * @since Java 3D 1.3
 */

public class J3DBuffer {

enum Type {
	NULL,
	UNKNOWN,
	BYTE,
	CHAR,
	SHORT,
	INT,
	LONG,
	FLOAT,
	DOUBLE,
}

    private Buffer originalBuffer = null;
    private Buffer readonlyBuffer = null;
    Type bufferType = Type.NULL;

    /**
     * Constructs a J3DBuffer object and initializes it with
     * a null NIO buffer object.  The NIO buffer object
     * must be set to a non-null value before using this J3DBuffer
     * object in a Java 3D node component.
     *
     * @exception UnsupportedOperationException if the JVM does not
     * support native access to direct NIO buffers
     */
    public J3DBuffer() {
	this(null);
    }


    /**
     * Constructs a J3DBuffer object and initializes it with
     * the specified NIO buffer object.
     *
     * @param buffer the NIO buffer wrapped by this J3DBuffer
     *
     * @exception UnsupportedOperationException if the JVM does not
     * support native access to direct NIO buffers
     *
     * @exception IllegalArgumentException if the specified buffer is
     * not a direct buffer, or if the byte order of the specified
     * buffer does not match the native byte order of the underlying
     * platform.
     */
    public J3DBuffer(Buffer buffer) {
	setBuffer(buffer);
    }


    /**
     * Sets the NIO buffer object in this J3DBuffer to
     * the specified object.
     *
     * @param buffer the NIO buffer wrapped by this J3DBuffer
     *
     * @exception IllegalArgumentException if the specified buffer is
     * not a direct buffer, or if the byte order of the specified
     * buffer does not match the native byte order of the underlying
     * platform.
     */
    public void setBuffer(Buffer buffer) {
	Type bType = Type.NULL;
	boolean direct = false;
	ByteOrder order = ByteOrder.BIG_ENDIAN;

	if (buffer == null) {
	    bType = Type.NULL;
	}
	else if (buffer instanceof ByteBuffer) {
	    bType = Type.BYTE;
	    direct = ((ByteBuffer)buffer).isDirect();
	    order = ((ByteBuffer)buffer).order();
	}
	else if (buffer instanceof CharBuffer) {
	    bType = Type.CHAR;
	    direct = ((CharBuffer)buffer).isDirect();
	    order = ((CharBuffer)buffer).order();
	}
	else if (buffer instanceof ShortBuffer) {
	    bType = Type.SHORT;
	    direct = ((ShortBuffer)buffer).isDirect();
	    order = ((ShortBuffer)buffer).order();
	}
	else if (buffer instanceof IntBuffer) {
	    bType = Type.INT;
	    direct = ((IntBuffer)buffer).isDirect();
	    order = ((IntBuffer)buffer).order();
	}
	else if (buffer instanceof LongBuffer) {
	    bType = Type.LONG;
	    direct = ((LongBuffer)buffer).isDirect();
	    order = ((LongBuffer)buffer).order();
	}
	else if (buffer instanceof FloatBuffer) {
	    bType = Type.FLOAT;
	    direct = ((FloatBuffer)buffer).isDirect();
	    order = ((FloatBuffer)buffer).order();
	}
	else if (buffer instanceof DoubleBuffer) {
	    bType = Type.DOUBLE;
	    direct = ((DoubleBuffer)buffer).isDirect();
	    order = ((DoubleBuffer)buffer).order();
	}
	else {
	    bType = Type.UNKNOWN;
	}

	// Verify that the buffer is direct and has the correct byte order
	if (buffer != null) {
	    if (!direct) {
		throw new IllegalArgumentException(J3dI18N.getString("J3DBuffer1"));
	    }

	    if (order != ByteOrder.nativeOrder()) {
		throw new IllegalArgumentException(J3dI18N.getString("J3DBuffer2"));
	    }
	}

	bufferType = bType;
	originalBuffer = buffer;

	// Make a read-only view of the buffer if the type is one
	// of the internally supported types: byte, float, or double
	switch (bufferType) {
	case BYTE:
	    ByteBuffer byteBuffer =	((ByteBuffer)buffer).asReadOnlyBuffer();
	    byteBuffer.rewind();
	    readonlyBuffer = byteBuffer;
	    break;
	case FLOAT:
	    FloatBuffer floatBuffer = ((FloatBuffer)buffer).asReadOnlyBuffer();
	    floatBuffer.rewind();
	    readonlyBuffer = floatBuffer;
	    break;
	case DOUBLE:
	    DoubleBuffer doubleBuffer = ((DoubleBuffer)buffer).asReadOnlyBuffer();
	    doubleBuffer.rewind();
	    readonlyBuffer = doubleBuffer;
	    break;
	default:
		readonlyBuffer = null;
	}
    }


    /**
     * Retrieves the NIO buffer object from this J3DBuffer.
     *
     * @return the current NIO buffer wrapped by this J3DBuffer
     */
    public Buffer getBuffer() {
	return originalBuffer;
    }

/**
 * Gets the readonly view of the nio buffer we wrapped with J3DBuffer
 * @return
 */
Buffer getROBuffer() {
	return readonlyBuffer;
}
}
