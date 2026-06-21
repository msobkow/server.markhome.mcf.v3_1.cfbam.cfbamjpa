
// Description: Java 25 Factory service implementation for Int32Def JPA objects

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;
import server.markhome.mcf.v3_1.cfint.cfint.*;
import server.markhome.mcf.v3_1.cfbam.cfbam.*;
import server.markhome.mcf.v3_1.cfsec.cfsec.jpa.*;
import server.markhome.mcf.v3_1.cfint.cfint.jpa.*;

/*
 *	Java 25 Factory service implementation for Int32Def JPA objects.
 */
public class CFBamJpaInt32DefFactoryService
    implements ICFBamInt32DefFactory
{
    public CFBamJpaInt32DefFactoryService() { }

    @Override
    public ICFBamInt32Def newRec() {
        ICFBamInt32Def rec = new CFBamJpaInt32Def();
        return( rec );
    }

	public CFBamJpaInt32Def ensureRec(ICFBamInt32Def rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFBamJpaInt32Def) {
			return( (CFBamJpaInt32Def)rec );
		}
		else {
			switch(rec.getClassCode()) {
				case ICFBamInt32Def.CLASS_CODE: {
					CFBamJpaInt32Def mapped = new CFBamJpaInt32Def();
					mapped.set(rec);
					return( mapped ); }
				case ICFBamInt32Type.CLASS_CODE: {
					CFBamJpaInt32Type mapped = new CFBamJpaInt32Type();
					mapped.set((ICFBamInt32Type)rec);
					return(mapped); }
				case ICFBamId32Gen.CLASS_CODE: {
					CFBamJpaId32Gen mapped = new CFBamJpaId32Gen();
					mapped.set((ICFBamId32Gen)rec);
					return(mapped); }
				case ICFBamInt32Col.CLASS_CODE: {
					CFBamJpaInt32Col mapped = new CFBamJpaInt32Col();
					mapped.set((ICFBamInt32Col)rec);
					return(mapped); }
				default:
					throw new CFLibUnsupportedClassException(getClass(), "ensureRec",
						"Unsupported class code " + rec.getClassCode() + " is not a derivative of CFBamInt32Def",
						"Unsupported class code " + rec.getClassCode() + " is not a derivative of CFBamInt32Def");
			}
		}
	}

    @Override
    public ICFBamInt32DefH newHRec() {
        ICFBamInt32DefH hrec = new CFBamJpaInt32DefH();
        return( hrec );
    }

	public CFBamJpaInt32DefH ensureHRec(ICFBamInt32DefH hrec) {
		if( hrec == null ) {
			return( null );
		}
		else if (hrec instanceof CFBamJpaInt32DefH) {
			return( (CFBamJpaInt32DefH)hrec );
		}
		else {
			switch(hrec.getClassCode()) {
				case ICFBamInt32Def.CLASS_CODE: {
					CFBamJpaInt32DefH mapped = new CFBamJpaInt32DefH();
					mapped.set(hrec);
					return( mapped ); }
				case ICFBamInt32Type.CLASS_CODE: {
					CFBamJpaInt32TypeH mapped = new CFBamJpaInt32TypeH();
					mapped.set((ICFBamInt32TypeH)hrec);
					return(mapped); }
				case ICFBamId32Gen.CLASS_CODE: {
					CFBamJpaId32GenH mapped = new CFBamJpaId32GenH();
					mapped.set((ICFBamId32GenH)hrec);
					return(mapped); }
				case ICFBamInt32Col.CLASS_CODE: {
					CFBamJpaInt32ColH mapped = new CFBamJpaInt32ColH();
					mapped.set((ICFBamInt32ColH)hrec);
					return(mapped); }
				default:
					throw new CFLibUnsupportedClassException(getClass(), "ensureHRec",
						"Unsupported class code " + hrec.getClassCode() + " is not a derivative of CFBamInt32Def",
						"Unsupported class code " + hrec.getClassCode() + " is not a derivative of CFBamInt32Def");
			}
		}
	}
}
