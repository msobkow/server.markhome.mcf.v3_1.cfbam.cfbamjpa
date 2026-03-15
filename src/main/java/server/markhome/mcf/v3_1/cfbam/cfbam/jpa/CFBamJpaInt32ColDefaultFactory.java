
// Description: Java 25 JPA Default Factory implementation for Int32Col.

/*
 *	server.markhome.mcf.CFBam
 *
 *	Copyright (c) 2016-2026 Mark Stephen Sobkow
 *	
 *	Mark's Code Fractal CFBam 3.1 Business Application Model
 *	
 *	Copyright 2016-2026 Mark Stephen Sobkow
 *	
 *	This file is part of Mark's Code Fractal CFBam.
 *	
 *	Mark's Code Fractal CFBam is available under dual commercial license from
 *	Mark Stephen Sobkow, or under the terms of the GNU General Public License,
 *	Version 3 or later with classpath and static linking exceptions.
 *	
 *	As a special exception, Mark Sobkow gives you permission to link this library
 *	with independent modules to produce an executable, provided that none of them
 *	conflict with the intent of the GPLv3; that is, you are not allowed to invoke
 *	the methods of this library from non-GPLv3-compatibly licensed code. You may not
 *	implement an LPGLv3 "wedge" to try to bypass this restriction. That said, code which
 *	does not rely on this library is free to specify whatever license its authors decide
 *	to use. Mark Sobkow specifically rejects the infectious nature of the GPLv3, and
 *	considers the mere act of including GPLv3 modules in an executable to be perfectly
 *	reasonable given tools like modern Java's single-jar deployment options.
 *	
 *	Mark's Code Fractal CFBam is free software: you can redistribute it and/or
 *	modify it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *	
 *	Mark's Code Fractal CFBam is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *	
 *	You should have received a copy of the GNU General Public License
 *	along with Mark's Code Fractal CFBam.  If not, see <https://www.gnu.org/licenses/>.
 *	
 *	If you wish to modify and use this code without publishing your changes,
 *	or integrate it with proprietary code, please contact Mark Stephen Sobkow
 *	for a commercial license at mark.sobkow@gmail.com
 */

package server.markhome.mcf.v3_1.cfbam.cfbam.jpa;

import java.lang.reflect.*;
import java.net.*;
import java.rmi.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;
import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;
import server.markhome.mcf.v3_1.cfint.cfint.*;
import server.markhome.mcf.v3_1.cfbam.cfbam.*;
import server.markhome.mcf.v3_1.cfsec.cfsec.jpa.*;
import server.markhome.mcf.v3_1.cfint.cfint.jpa.*;

/*
 *	CFBamInt32ColFactory JPA implementation for Int32Col
 */
public class CFBamJpaInt32ColDefaultFactory
    implements ICFBamInt32ColFactory
{
    public CFBamJpaInt32ColDefaultFactory() {
    }

    @Override
    public ICFBamInt32ColByTableIdxKey newByTableIdxKey() {
	ICFBamInt32ColByTableIdxKey key =
            new CFBamJpaInt32ColByTableIdxKey();
	return( key );
    }

	public CFBamJpaInt32ColByTableIdxKey ensureByTableIdxKey(ICFBamInt32ColByTableIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaInt32ColByTableIdxKey) {
			return( (CFBamJpaInt32ColByTableIdxKey)key );
		}
		else {
			CFBamJpaInt32ColByTableIdxKey mapped = new CFBamJpaInt32ColByTableIdxKey();
			mapped.setRequiredTableId( key.getRequiredTableId() );
			return( mapped );
		}
	}

    @Override
    public ICFBamInt32Col newRec() {
        ICFBamInt32Col rec =
            new CFBamJpaInt32Col();
        return( rec );
    }

	public CFBamJpaInt32Col ensureRec(ICFBamInt32Col rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFBamJpaInt32Col) {
			return( (CFBamJpaInt32Col)rec );
		}
		else {
			CFBamJpaInt32Col mapped = new CFBamJpaInt32Col();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFBamInt32ColH newHRec() {
        ICFBamInt32ColH hrec =
            new CFBamJpaInt32ColH();
        return( hrec );
    }

	public CFBamJpaInt32ColH ensureHRec(ICFBamInt32ColH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFBamJpaInt32ColH) {
			return( (CFBamJpaInt32ColH)hrec );
		}
		else {
			CFBamJpaInt32ColH mapped = new CFBamJpaInt32ColH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}
