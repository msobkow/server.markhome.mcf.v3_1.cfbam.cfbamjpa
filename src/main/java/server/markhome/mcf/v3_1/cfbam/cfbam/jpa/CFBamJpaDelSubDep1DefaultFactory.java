
// Description: Java 25 JPA Default Factory implementation for DelSubDep1.

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
 *	CFBamDelSubDep1Factory JPA implementation for DelSubDep1
 */
public class CFBamJpaDelSubDep1DefaultFactory
    implements ICFBamDelSubDep1Factory
{
    public CFBamJpaDelSubDep1DefaultFactory() {
    }

    @Override
    public ICFBamDelSubDep1ByDelTopDepIdxKey newByDelTopDepIdxKey() {
	ICFBamDelSubDep1ByDelTopDepIdxKey key =
            new CFBamJpaDelSubDep1ByDelTopDepIdxKey();
	return( key );
    }

	public CFBamJpaDelSubDep1ByDelTopDepIdxKey ensureByDelTopDepIdxKey(ICFBamDelSubDep1ByDelTopDepIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaDelSubDep1ByDelTopDepIdxKey) {
			return( (CFBamJpaDelSubDep1ByDelTopDepIdxKey)key );
		}
		else {
			CFBamJpaDelSubDep1ByDelTopDepIdxKey mapped = new CFBamJpaDelSubDep1ByDelTopDepIdxKey();
			mapped.setRequiredDelTopDepId( key.getRequiredDelTopDepId() );
			return( mapped );
		}
	}

    @Override
    public ICFBamDelSubDep1ByUNameIdxKey newByUNameIdxKey() {
	ICFBamDelSubDep1ByUNameIdxKey key =
            new CFBamJpaDelSubDep1ByUNameIdxKey();
	return( key );
    }

	public CFBamJpaDelSubDep1ByUNameIdxKey ensureByUNameIdxKey(ICFBamDelSubDep1ByUNameIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaDelSubDep1ByUNameIdxKey) {
			return( (CFBamJpaDelSubDep1ByUNameIdxKey)key );
		}
		else {
			CFBamJpaDelSubDep1ByUNameIdxKey mapped = new CFBamJpaDelSubDep1ByUNameIdxKey();
			mapped.setRequiredDelTopDepId( key.getRequiredDelTopDepId() );
			mapped.setRequiredName( key.getRequiredName() );
			return( mapped );
		}
	}

    @Override
    public ICFBamDelSubDep1 newRec() {
        ICFBamDelSubDep1 rec =
            new CFBamJpaDelSubDep1();
        return( rec );
    }

	public CFBamJpaDelSubDep1 ensureRec(ICFBamDelSubDep1 rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFBamJpaDelSubDep1) {
			return( (CFBamJpaDelSubDep1)rec );
		}
		else {
			CFBamJpaDelSubDep1 mapped = new CFBamJpaDelSubDep1();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFBamDelSubDep1H newHRec() {
        ICFBamDelSubDep1H hrec =
            new CFBamJpaDelSubDep1H();
        return( hrec );
    }

	public CFBamJpaDelSubDep1H ensureHRec(ICFBamDelSubDep1H hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFBamJpaDelSubDep1H) {
			return( (CFBamJpaDelSubDep1H)hrec );
		}
		else {
			CFBamJpaDelSubDep1H mapped = new CFBamJpaDelSubDep1H();
			mapped.set(hrec);
			return( mapped );
		}
	}
}
