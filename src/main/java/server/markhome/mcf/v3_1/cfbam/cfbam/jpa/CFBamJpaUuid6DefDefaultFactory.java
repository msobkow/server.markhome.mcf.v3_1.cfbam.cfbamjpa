
// Description: Java 25 JPA Default Factory implementation for Uuid6Def.

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
 *	CFBamUuid6DefFactory JPA implementation for Uuid6Def
 */
public class CFBamJpaUuid6DefDefaultFactory
    implements ICFBamUuid6DefFactory
{
    public CFBamJpaUuid6DefDefaultFactory() {
    }

    @Override
    public ICFBamUuid6Def newRec() {
        ICFBamUuid6Def rec =
            new CFBamJpaUuid6Def();
        return( rec );
    }

	public CFBamJpaUuid6Def ensureRec(ICFBamUuid6Def rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFBamJpaUuid6Def) {
			return( (CFBamJpaUuid6Def)rec );
		}
		else {
			CFBamJpaUuid6Def mapped = new CFBamJpaUuid6Def();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFBamUuid6DefH newHRec() {
        ICFBamUuid6DefH hrec =
            new CFBamJpaUuid6DefH();
        return( hrec );
    }

	public CFBamJpaUuid6DefH ensureHRec(ICFBamUuid6DefH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFBamJpaUuid6DefH) {
			return( (CFBamJpaUuid6DefH)hrec );
		}
		else {
			CFBamJpaUuid6DefH mapped = new CFBamJpaUuid6DefH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}
