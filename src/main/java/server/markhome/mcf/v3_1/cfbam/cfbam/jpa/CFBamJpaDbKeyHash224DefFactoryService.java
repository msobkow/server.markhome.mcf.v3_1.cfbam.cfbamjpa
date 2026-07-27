
// Description: Java 25 Factory service implementation for DbKeyHash224Def JPA objects

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
 *	Java 25 Factory service implementation for DbKeyHash224Def JPA objects.
 */
public class CFBamJpaDbKeyHash224DefFactoryService
    implements ICFBamDbKeyHash224DefFactory
{
    public CFBamJpaDbKeyHash224DefFactoryService() { }

    @Override
    public ICFBamDbKeyHash224Def newRec() {
        ICFBamDbKeyHash224Def rec = new CFBamJpaDbKeyHash224Def();
        return( rec );
    }

	public CFBamJpaDbKeyHash224Def ensureRec(ICFBamDbKeyHash224Def rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFBamJpaDbKeyHash224Def) {
			return( (CFBamJpaDbKeyHash224Def)rec );
		}
		else {
			switch(rec.getClassCode()) {
				case ICFBamDbKeyHash224Def.CLASS_CODE: {
					CFBamJpaDbKeyHash224Def mapped = new CFBamJpaDbKeyHash224Def();
					mapped.set(rec);
					return( mapped ); }
				case ICFBamDbKeyHash224Col.CLASS_CODE: {
					CFBamJpaDbKeyHash224Col mapped = new CFBamJpaDbKeyHash224Col();
					mapped.set((ICFBamDbKeyHash224Col)rec);
					return(mapped); }
				case ICFBamDbKeyHash224Type.CLASS_CODE: {
					CFBamJpaDbKeyHash224Type mapped = new CFBamJpaDbKeyHash224Type();
					mapped.set((ICFBamDbKeyHash224Type)rec);
					return(mapped); }
				case ICFBamDbKeyHash224Gen.CLASS_CODE: {
					CFBamJpaDbKeyHash224Gen mapped = new CFBamJpaDbKeyHash224Gen();
					mapped.set((ICFBamDbKeyHash224Gen)rec);
					return(mapped); }
				default:
					throw new CFLibUnsupportedClassException(getClass(), "ensureRec",
						"Unsupported class code " + rec.getClassCode() + " is not a derivative of CFBamDbKeyHash224Def",
						"Unsupported class code " + rec.getClassCode() + " is not a derivative of CFBamDbKeyHash224Def");
			}
		}
	}

    @Override
    public ICFBamDbKeyHash224DefH newHRec() {
        ICFBamDbKeyHash224DefH hrec = new CFBamJpaDbKeyHash224DefH();
        return( hrec );
    }

	public CFBamJpaDbKeyHash224DefH ensureHRec(ICFBamDbKeyHash224DefH hrec) {
		if( hrec == null ) {
			return( null );
		}
		else if (hrec instanceof CFBamJpaDbKeyHash224DefH) {
			return( (CFBamJpaDbKeyHash224DefH)hrec );
		}
		else {
			switch(hrec.getClassCode()) {
				case ICFBamDbKeyHash224Def.CLASS_CODE: {
					CFBamJpaDbKeyHash224DefH mapped = new CFBamJpaDbKeyHash224DefH();
					mapped.set(hrec);
					return( mapped ); }
				case ICFBamDbKeyHash224Col.CLASS_CODE: {
					CFBamJpaDbKeyHash224ColH mapped = new CFBamJpaDbKeyHash224ColH();
					mapped.set((ICFBamDbKeyHash224ColH)hrec);
					return(mapped); }
				case ICFBamDbKeyHash224Type.CLASS_CODE: {
					CFBamJpaDbKeyHash224TypeH mapped = new CFBamJpaDbKeyHash224TypeH();
					mapped.set((ICFBamDbKeyHash224TypeH)hrec);
					return(mapped); }
				case ICFBamDbKeyHash224Gen.CLASS_CODE: {
					CFBamJpaDbKeyHash224GenH mapped = new CFBamJpaDbKeyHash224GenH();
					mapped.set((ICFBamDbKeyHash224GenH)hrec);
					return(mapped); }
				default:
					throw new CFLibUnsupportedClassException(getClass(), "ensureHRec",
						"Unsupported class code " + hrec.getClassCode() + " is not a derivative of CFBamDbKeyHash224Def",
						"Unsupported class code " + hrec.getClassCode() + " is not a derivative of CFBamDbKeyHash224Def");
			}
		}
	}
}
