
// Description: Java 25 JPA Default Factory implementation for RoleDef.

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
 *	CFBamRoleDefFactory JPA implementation for RoleDef
 */
public class CFBamJpaRoleDefDefaultFactory
    implements ICFBamRoleDefFactory
{
    public CFBamJpaRoleDefDefaultFactory() {
    }

    @Override
    public ICFBamRoleDefHPKey newHPKey() {
        ICFBamRoleDefHPKey hpkey =
            new CFBamJpaRoleDefHPKey();
        return( hpkey );
    }

	public CFBamJpaRoleDefHPKey ensureHPKey(ICFBamRoleDefHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFBamJpaRoleDefHPKey) {
			return( (CFBamJpaRoleDefHPKey)key );
		}
		else {
			CFBamJpaRoleDefHPKey mapped = new CFBamJpaRoleDefHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredId( key.getRequiredId() );
			return( mapped );
		}
	}

    @Override
    public ICFBamRoleDefByUNameIdxKey newByUNameIdxKey() {
	ICFBamRoleDefByUNameIdxKey key =
            new CFBamJpaRoleDefByUNameIdxKey();
	return( key );
    }

	public CFBamJpaRoleDefByUNameIdxKey ensureByUNameIdxKey(ICFBamRoleDefByUNameIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaRoleDefByUNameIdxKey) {
			return( (CFBamJpaRoleDefByUNameIdxKey)key );
		}
		else {
			CFBamJpaRoleDefByUNameIdxKey mapped = new CFBamJpaRoleDefByUNameIdxKey();
			mapped.setRequiredScopeId( key.getRequiredScopeId() );
			mapped.setRequiredName( key.getRequiredName() );
			return( mapped );
		}
	}

    @Override
    public ICFBamRoleDefByScopeIdxKey newByScopeIdxKey() {
	ICFBamRoleDefByScopeIdxKey key =
            new CFBamJpaRoleDefByScopeIdxKey();
	return( key );
    }

	public CFBamJpaRoleDefByScopeIdxKey ensureByScopeIdxKey(ICFBamRoleDefByScopeIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaRoleDefByScopeIdxKey) {
			return( (CFBamJpaRoleDefByScopeIdxKey)key );
		}
		else {
			CFBamJpaRoleDefByScopeIdxKey mapped = new CFBamJpaRoleDefByScopeIdxKey();
			mapped.setRequiredScopeId( key.getRequiredScopeId() );
			return( mapped );
		}
	}

    @Override
    public ICFBamRoleDefByDefSchemaIdxKey newByDefSchemaIdxKey() {
	ICFBamRoleDefByDefSchemaIdxKey key =
            new CFBamJpaRoleDefByDefSchemaIdxKey();
	return( key );
    }

	public CFBamJpaRoleDefByDefSchemaIdxKey ensureByDefSchemaIdxKey(ICFBamRoleDefByDefSchemaIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaRoleDefByDefSchemaIdxKey) {
			return( (CFBamJpaRoleDefByDefSchemaIdxKey)key );
		}
		else {
			CFBamJpaRoleDefByDefSchemaIdxKey mapped = new CFBamJpaRoleDefByDefSchemaIdxKey();
			mapped.setOptionalDefSchemaId( key.getOptionalDefSchemaId() );
			return( mapped );
		}
	}

    @Override
    public ICFBamRoleDefByUDefIdxKey newByUDefIdxKey() {
	ICFBamRoleDefByUDefIdxKey key =
            new CFBamJpaRoleDefByUDefIdxKey();
	return( key );
    }

	public CFBamJpaRoleDefByUDefIdxKey ensureByUDefIdxKey(ICFBamRoleDefByUDefIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaRoleDefByUDefIdxKey) {
			return( (CFBamJpaRoleDefByUDefIdxKey)key );
		}
		else {
			CFBamJpaRoleDefByUDefIdxKey mapped = new CFBamJpaRoleDefByUDefIdxKey();
			mapped.setRequiredScopeId( key.getRequiredScopeId() );
			mapped.setOptionalDefSchemaId( key.getOptionalDefSchemaId() );
			mapped.setRequiredName( key.getRequiredName() );
			return( mapped );
		}
	}

    @Override
    public ICFBamRoleDef newRec() {
        ICFBamRoleDef rec =
            new CFBamJpaRoleDef();
        return( rec );
    }

	public CFBamJpaRoleDef ensureRec(ICFBamRoleDef rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFBamJpaRoleDef) {
			return( (CFBamJpaRoleDef)rec );
		}
		else {
			CFBamJpaRoleDef mapped = new CFBamJpaRoleDef();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFBamRoleDefH newHRec() {
        ICFBamRoleDefH hrec =
            new CFBamJpaRoleDefH();
        return( hrec );
    }

	public CFBamJpaRoleDefH ensureHRec(ICFBamRoleDefH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFBamJpaRoleDefH) {
			return( (CFBamJpaRoleDefH)hrec );
		}
		else {
			CFBamJpaRoleDefH mapped = new CFBamJpaRoleDefH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}
