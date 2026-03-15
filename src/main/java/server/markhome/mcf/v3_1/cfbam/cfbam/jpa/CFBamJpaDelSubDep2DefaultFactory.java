
// Description: Java 25 JPA Default Factory implementation for DelSubDep2.

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
 *	CFBamDelSubDep2Factory JPA implementation for DelSubDep2
 */
public class CFBamJpaDelSubDep2DefaultFactory
    implements ICFBamDelSubDep2Factory
{
    public CFBamJpaDelSubDep2DefaultFactory() {
    }

    @Override
    public ICFBamDelSubDep2ByContDelDep1IdxKey newByContDelDep1IdxKey() {
	ICFBamDelSubDep2ByContDelDep1IdxKey key =
            new CFBamJpaDelSubDep2ByContDelDep1IdxKey();
	return( key );
    }

	public CFBamJpaDelSubDep2ByContDelDep1IdxKey ensureByContDelDep1IdxKey(ICFBamDelSubDep2ByContDelDep1IdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaDelSubDep2ByContDelDep1IdxKey) {
			return( (CFBamJpaDelSubDep2ByContDelDep1IdxKey)key );
		}
		else {
			CFBamJpaDelSubDep2ByContDelDep1IdxKey mapped = new CFBamJpaDelSubDep2ByContDelDep1IdxKey();
			mapped.setRequiredDelSubDep1Id( key.getRequiredDelSubDep1Id() );
			return( mapped );
		}
	}

    @Override
    public ICFBamDelSubDep2ByUNameIdxKey newByUNameIdxKey() {
	ICFBamDelSubDep2ByUNameIdxKey key =
            new CFBamJpaDelSubDep2ByUNameIdxKey();
	return( key );
    }

	public CFBamJpaDelSubDep2ByUNameIdxKey ensureByUNameIdxKey(ICFBamDelSubDep2ByUNameIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaDelSubDep2ByUNameIdxKey) {
			return( (CFBamJpaDelSubDep2ByUNameIdxKey)key );
		}
		else {
			CFBamJpaDelSubDep2ByUNameIdxKey mapped = new CFBamJpaDelSubDep2ByUNameIdxKey();
			mapped.setRequiredDelSubDep1Id( key.getRequiredDelSubDep1Id() );
			mapped.setRequiredName( key.getRequiredName() );
			return( mapped );
		}
	}

    @Override
    public ICFBamDelSubDep2 newRec() {
        ICFBamDelSubDep2 rec =
            new CFBamJpaDelSubDep2();
        return( rec );
    }

	public CFBamJpaDelSubDep2 ensureRec(ICFBamDelSubDep2 rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFBamJpaDelSubDep2) {
			return( (CFBamJpaDelSubDep2)rec );
		}
		else {
			CFBamJpaDelSubDep2 mapped = new CFBamJpaDelSubDep2();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFBamDelSubDep2H newHRec() {
        ICFBamDelSubDep2H hrec =
            new CFBamJpaDelSubDep2H();
        return( hrec );
    }

	public CFBamJpaDelSubDep2H ensureHRec(ICFBamDelSubDep2H hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFBamJpaDelSubDep2H) {
			return( (CFBamJpaDelSubDep2H)hrec );
		}
		else {
			CFBamJpaDelSubDep2H mapped = new CFBamJpaDelSubDep2H();
			mapped.set(hrec);
			return( mapped );
		}
	}
}
