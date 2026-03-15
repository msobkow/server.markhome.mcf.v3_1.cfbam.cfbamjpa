
// Description: Java 25 JPA Default Factory implementation for PopSubDep2.

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
 *	CFBamPopSubDep2Factory JPA implementation for PopSubDep2
 */
public class CFBamJpaPopSubDep2DefaultFactory
    implements ICFBamPopSubDep2Factory
{
    public CFBamJpaPopSubDep2DefaultFactory() {
    }

    @Override
    public ICFBamPopSubDep2ByPopSubDep1IdxKey newByPopSubDep1IdxKey() {
	ICFBamPopSubDep2ByPopSubDep1IdxKey key =
            new CFBamJpaPopSubDep2ByPopSubDep1IdxKey();
	return( key );
    }

	public CFBamJpaPopSubDep2ByPopSubDep1IdxKey ensureByPopSubDep1IdxKey(ICFBamPopSubDep2ByPopSubDep1IdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaPopSubDep2ByPopSubDep1IdxKey) {
			return( (CFBamJpaPopSubDep2ByPopSubDep1IdxKey)key );
		}
		else {
			CFBamJpaPopSubDep2ByPopSubDep1IdxKey mapped = new CFBamJpaPopSubDep2ByPopSubDep1IdxKey();
			mapped.setRequiredPopSubDep1Id( key.getRequiredPopSubDep1Id() );
			return( mapped );
		}
	}

    @Override
    public ICFBamPopSubDep2ByUNameIdxKey newByUNameIdxKey() {
	ICFBamPopSubDep2ByUNameIdxKey key =
            new CFBamJpaPopSubDep2ByUNameIdxKey();
	return( key );
    }

	public CFBamJpaPopSubDep2ByUNameIdxKey ensureByUNameIdxKey(ICFBamPopSubDep2ByUNameIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaPopSubDep2ByUNameIdxKey) {
			return( (CFBamJpaPopSubDep2ByUNameIdxKey)key );
		}
		else {
			CFBamJpaPopSubDep2ByUNameIdxKey mapped = new CFBamJpaPopSubDep2ByUNameIdxKey();
			mapped.setRequiredPopSubDep1Id( key.getRequiredPopSubDep1Id() );
			mapped.setRequiredName( key.getRequiredName() );
			return( mapped );
		}
	}

    @Override
    public ICFBamPopSubDep2 newRec() {
        ICFBamPopSubDep2 rec =
            new CFBamJpaPopSubDep2();
        return( rec );
    }

	public CFBamJpaPopSubDep2 ensureRec(ICFBamPopSubDep2 rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFBamJpaPopSubDep2) {
			return( (CFBamJpaPopSubDep2)rec );
		}
		else {
			CFBamJpaPopSubDep2 mapped = new CFBamJpaPopSubDep2();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFBamPopSubDep2H newHRec() {
        ICFBamPopSubDep2H hrec =
            new CFBamJpaPopSubDep2H();
        return( hrec );
    }

	public CFBamJpaPopSubDep2H ensureHRec(ICFBamPopSubDep2H hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFBamJpaPopSubDep2H) {
			return( (CFBamJpaPopSubDep2H)hrec );
		}
		else {
			CFBamJpaPopSubDep2H mapped = new CFBamJpaPopSubDep2H();
			mapped.set(hrec);
			return( mapped );
		}
	}
}
