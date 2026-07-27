
// Description: Java 25 Factory service implementation for Scope JPA objects

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
 *	Java 25 Factory service implementation for Scope JPA objects.
 */
public class CFBamJpaScopeFactoryService
    implements ICFBamScopeFactory
{
    public CFBamJpaScopeFactoryService() { }

    @Override
    public ICFBamScopeHPKey newHPKey() {
        ICFBamScopeHPKey hpkey = new CFBamJpaScopeHPKey();
        return( hpkey );
    }

	public CFBamJpaScopeHPKey ensureHPKey(ICFBamScopeHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFBamJpaScopeHPKey) {
			return( (CFBamJpaScopeHPKey)key );
		}
		else {
			CFBamJpaScopeHPKey mapped = new CFBamJpaScopeHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredId( key.getRequiredId() );
			return( mapped );
		}
	}

    @Override
    public ICFBamScopeByTenantIdxKey newByTenantIdxKey() {
		ICFBamScopeByTenantIdxKey key = new CFBamJpaScopeByTenantIdxKey();
	return( key );
    }

	public CFBamJpaScopeByTenantIdxKey ensureByTenantIdxKey(ICFBamScopeByTenantIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaScopeByTenantIdxKey) {
			return( (CFBamJpaScopeByTenantIdxKey)key );
		}
		else {
			CFBamJpaScopeByTenantIdxKey mapped = new CFBamJpaScopeByTenantIdxKey();
			mapped.setRequiredTenantId( key.getRequiredTenantId() );
			return( mapped );
		}
	}

    @Override
    public ICFBamScope newRec() {
        ICFBamScope rec = new CFBamJpaScope();
        return( rec );
    }

	public CFBamJpaScope ensureRec(ICFBamScope rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFBamJpaScope) {
			return( (CFBamJpaScope)rec );
		}
		else {
			switch(rec.getClassCode()) {
				case ICFBamScope.CLASS_CODE: {
					CFBamJpaScope mapped = new CFBamJpaScope();
					mapped.set(rec);
					return( mapped ); }
				case ICFBamSchemaDef.CLASS_CODE: {
					CFBamJpaSchemaDef mapped = new CFBamJpaSchemaDef();
					mapped.set((ICFBamSchemaDef)rec);
					return(mapped); }
				case ICFBamSchemaRef.CLASS_CODE: {
					CFBamJpaSchemaRef mapped = new CFBamJpaSchemaRef();
					mapped.set((ICFBamSchemaRef)rec);
					return(mapped); }
				case ICFBamServerMethod.CLASS_CODE: {
					CFBamJpaServerMethod mapped = new CFBamJpaServerMethod();
					mapped.set((ICFBamServerMethod)rec);
					return(mapped); }
				case ICFBamServerObjFunc.CLASS_CODE: {
					CFBamJpaServerObjFunc mapped = new CFBamJpaServerObjFunc();
					mapped.set((ICFBamServerObjFunc)rec);
					return(mapped); }
				case ICFBamServerProc.CLASS_CODE: {
					CFBamJpaServerProc mapped = new CFBamJpaServerProc();
					mapped.set((ICFBamServerProc)rec);
					return(mapped); }
				case ICFBamServerListFunc.CLASS_CODE: {
					CFBamJpaServerListFunc mapped = new CFBamJpaServerListFunc();
					mapped.set((ICFBamServerListFunc)rec);
					return(mapped); }
				case ICFBamTable.CLASS_CODE: {
					CFBamJpaTable mapped = new CFBamJpaTable();
					mapped.set((ICFBamTable)rec);
					return(mapped); }
				case ICFBamClearDep.CLASS_CODE: {
					CFBamJpaClearDep mapped = new CFBamJpaClearDep();
					mapped.set((ICFBamClearDep)rec);
					return(mapped); }
				case ICFBamClearSubDep1.CLASS_CODE: {
					CFBamJpaClearSubDep1 mapped = new CFBamJpaClearSubDep1();
					mapped.set((ICFBamClearSubDep1)rec);
					return(mapped); }
				case ICFBamClearSubDep2.CLASS_CODE: {
					CFBamJpaClearSubDep2 mapped = new CFBamJpaClearSubDep2();
					mapped.set((ICFBamClearSubDep2)rec);
					return(mapped); }
				case ICFBamClearSubDep3.CLASS_CODE: {
					CFBamJpaClearSubDep3 mapped = new CFBamJpaClearSubDep3();
					mapped.set((ICFBamClearSubDep3)rec);
					return(mapped); }
				case ICFBamClearTopDep.CLASS_CODE: {
					CFBamJpaClearTopDep mapped = new CFBamJpaClearTopDep();
					mapped.set((ICFBamClearTopDep)rec);
					return(mapped); }
				case ICFBamDelDep.CLASS_CODE: {
					CFBamJpaDelDep mapped = new CFBamJpaDelDep();
					mapped.set((ICFBamDelDep)rec);
					return(mapped); }
				case ICFBamDelSubDep1.CLASS_CODE: {
					CFBamJpaDelSubDep1 mapped = new CFBamJpaDelSubDep1();
					mapped.set((ICFBamDelSubDep1)rec);
					return(mapped); }
				case ICFBamDelSubDep2.CLASS_CODE: {
					CFBamJpaDelSubDep2 mapped = new CFBamJpaDelSubDep2();
					mapped.set((ICFBamDelSubDep2)rec);
					return(mapped); }
				case ICFBamDelSubDep3.CLASS_CODE: {
					CFBamJpaDelSubDep3 mapped = new CFBamJpaDelSubDep3();
					mapped.set((ICFBamDelSubDep3)rec);
					return(mapped); }
				case ICFBamDelTopDep.CLASS_CODE: {
					CFBamJpaDelTopDep mapped = new CFBamJpaDelTopDep();
					mapped.set((ICFBamDelTopDep)rec);
					return(mapped); }
				case ICFBamIndex.CLASS_CODE: {
					CFBamJpaIndex mapped = new CFBamJpaIndex();
					mapped.set((ICFBamIndex)rec);
					return(mapped); }
				case ICFBamPopDep.CLASS_CODE: {
					CFBamJpaPopDep mapped = new CFBamJpaPopDep();
					mapped.set((ICFBamPopDep)rec);
					return(mapped); }
				case ICFBamPopSubDep1.CLASS_CODE: {
					CFBamJpaPopSubDep1 mapped = new CFBamJpaPopSubDep1();
					mapped.set((ICFBamPopSubDep1)rec);
					return(mapped); }
				case ICFBamPopSubDep2.CLASS_CODE: {
					CFBamJpaPopSubDep2 mapped = new CFBamJpaPopSubDep2();
					mapped.set((ICFBamPopSubDep2)rec);
					return(mapped); }
				case ICFBamPopSubDep3.CLASS_CODE: {
					CFBamJpaPopSubDep3 mapped = new CFBamJpaPopSubDep3();
					mapped.set((ICFBamPopSubDep3)rec);
					return(mapped); }
				case ICFBamPopTopDep.CLASS_CODE: {
					CFBamJpaPopTopDep mapped = new CFBamJpaPopTopDep();
					mapped.set((ICFBamPopTopDep)rec);
					return(mapped); }
				case ICFBamRelation.CLASS_CODE: {
					CFBamJpaRelation mapped = new CFBamJpaRelation();
					mapped.set((ICFBamRelation)rec);
					return(mapped); }
				default:
					throw new CFLibUnsupportedClassException(getClass(), "ensureRec",
						"Unsupported class code " + rec.getClassCode() + " is not a derivative of CFBamScope",
						"Unsupported class code " + rec.getClassCode() + " is not a derivative of CFBamScope");
			}
		}
	}

    @Override
    public ICFBamScopeH newHRec() {
        ICFBamScopeH hrec = new CFBamJpaScopeH();
        return( hrec );
    }

	public CFBamJpaScopeH ensureHRec(ICFBamScopeH hrec) {
		if( hrec == null ) {
			return( null );
		}
		else if (hrec instanceof CFBamJpaScopeH) {
			return( (CFBamJpaScopeH)hrec );
		}
		else {
			switch(hrec.getClassCode()) {
				case ICFBamScope.CLASS_CODE: {
					CFBamJpaScopeH mapped = new CFBamJpaScopeH();
					mapped.set(hrec);
					return( mapped ); }
				case ICFBamSchemaDef.CLASS_CODE: {
					CFBamJpaSchemaDefH mapped = new CFBamJpaSchemaDefH();
					mapped.set((ICFBamSchemaDefH)hrec);
					return(mapped); }
				case ICFBamSchemaRef.CLASS_CODE: {
					CFBamJpaSchemaRefH mapped = new CFBamJpaSchemaRefH();
					mapped.set((ICFBamSchemaRefH)hrec);
					return(mapped); }
				case ICFBamServerMethod.CLASS_CODE: {
					CFBamJpaServerMethodH mapped = new CFBamJpaServerMethodH();
					mapped.set((ICFBamServerMethodH)hrec);
					return(mapped); }
				case ICFBamServerObjFunc.CLASS_CODE: {
					CFBamJpaServerObjFuncH mapped = new CFBamJpaServerObjFuncH();
					mapped.set((ICFBamServerObjFuncH)hrec);
					return(mapped); }
				case ICFBamServerProc.CLASS_CODE: {
					CFBamJpaServerProcH mapped = new CFBamJpaServerProcH();
					mapped.set((ICFBamServerProcH)hrec);
					return(mapped); }
				case ICFBamServerListFunc.CLASS_CODE: {
					CFBamJpaServerListFuncH mapped = new CFBamJpaServerListFuncH();
					mapped.set((ICFBamServerListFuncH)hrec);
					return(mapped); }
				case ICFBamTable.CLASS_CODE: {
					CFBamJpaTableH mapped = new CFBamJpaTableH();
					mapped.set((ICFBamTableH)hrec);
					return(mapped); }
				case ICFBamClearDep.CLASS_CODE: {
					CFBamJpaClearDepH mapped = new CFBamJpaClearDepH();
					mapped.set((ICFBamClearDepH)hrec);
					return(mapped); }
				case ICFBamClearSubDep1.CLASS_CODE: {
					CFBamJpaClearSubDep1H mapped = new CFBamJpaClearSubDep1H();
					mapped.set((ICFBamClearSubDep1H)hrec);
					return(mapped); }
				case ICFBamClearSubDep2.CLASS_CODE: {
					CFBamJpaClearSubDep2H mapped = new CFBamJpaClearSubDep2H();
					mapped.set((ICFBamClearSubDep2H)hrec);
					return(mapped); }
				case ICFBamClearSubDep3.CLASS_CODE: {
					CFBamJpaClearSubDep3H mapped = new CFBamJpaClearSubDep3H();
					mapped.set((ICFBamClearSubDep3H)hrec);
					return(mapped); }
				case ICFBamClearTopDep.CLASS_CODE: {
					CFBamJpaClearTopDepH mapped = new CFBamJpaClearTopDepH();
					mapped.set((ICFBamClearTopDepH)hrec);
					return(mapped); }
				case ICFBamDelDep.CLASS_CODE: {
					CFBamJpaDelDepH mapped = new CFBamJpaDelDepH();
					mapped.set((ICFBamDelDepH)hrec);
					return(mapped); }
				case ICFBamDelSubDep1.CLASS_CODE: {
					CFBamJpaDelSubDep1H mapped = new CFBamJpaDelSubDep1H();
					mapped.set((ICFBamDelSubDep1H)hrec);
					return(mapped); }
				case ICFBamDelSubDep2.CLASS_CODE: {
					CFBamJpaDelSubDep2H mapped = new CFBamJpaDelSubDep2H();
					mapped.set((ICFBamDelSubDep2H)hrec);
					return(mapped); }
				case ICFBamDelSubDep3.CLASS_CODE: {
					CFBamJpaDelSubDep3H mapped = new CFBamJpaDelSubDep3H();
					mapped.set((ICFBamDelSubDep3H)hrec);
					return(mapped); }
				case ICFBamDelTopDep.CLASS_CODE: {
					CFBamJpaDelTopDepH mapped = new CFBamJpaDelTopDepH();
					mapped.set((ICFBamDelTopDepH)hrec);
					return(mapped); }
				case ICFBamIndex.CLASS_CODE: {
					CFBamJpaIndexH mapped = new CFBamJpaIndexH();
					mapped.set((ICFBamIndexH)hrec);
					return(mapped); }
				case ICFBamPopDep.CLASS_CODE: {
					CFBamJpaPopDepH mapped = new CFBamJpaPopDepH();
					mapped.set((ICFBamPopDepH)hrec);
					return(mapped); }
				case ICFBamPopSubDep1.CLASS_CODE: {
					CFBamJpaPopSubDep1H mapped = new CFBamJpaPopSubDep1H();
					mapped.set((ICFBamPopSubDep1H)hrec);
					return(mapped); }
				case ICFBamPopSubDep2.CLASS_CODE: {
					CFBamJpaPopSubDep2H mapped = new CFBamJpaPopSubDep2H();
					mapped.set((ICFBamPopSubDep2H)hrec);
					return(mapped); }
				case ICFBamPopSubDep3.CLASS_CODE: {
					CFBamJpaPopSubDep3H mapped = new CFBamJpaPopSubDep3H();
					mapped.set((ICFBamPopSubDep3H)hrec);
					return(mapped); }
				case ICFBamPopTopDep.CLASS_CODE: {
					CFBamJpaPopTopDepH mapped = new CFBamJpaPopTopDepH();
					mapped.set((ICFBamPopTopDepH)hrec);
					return(mapped); }
				case ICFBamRelation.CLASS_CODE: {
					CFBamJpaRelationH mapped = new CFBamJpaRelationH();
					mapped.set((ICFBamRelationH)hrec);
					return(mapped); }
				default:
					throw new CFLibUnsupportedClassException(getClass(), "ensureHRec",
						"Unsupported class code " + hrec.getClassCode() + " is not a derivative of CFBamScope",
						"Unsupported class code " + hrec.getClassCode() + " is not a derivative of CFBamScope");
			}
		}
	}
}
