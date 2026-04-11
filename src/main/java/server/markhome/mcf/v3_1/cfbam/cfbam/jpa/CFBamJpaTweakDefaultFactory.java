
// Description: Java 25 JPA Default Factory implementation for Tweak.

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
 *	CFBamTweakFactory JPA implementation for Tweak
 */
public class CFBamJpaTweakDefaultFactory
    implements ICFBamTweakFactory
{
    public CFBamJpaTweakDefaultFactory() {
    }

    @Override
    public ICFBamTweakHPKey newHPKey() {
        ICFBamTweakHPKey hpkey =
            new CFBamJpaTweakHPKey();
        return( hpkey );
    }

	public CFBamJpaTweakHPKey ensureHPKey(ICFBamTweakHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFBamJpaTweakHPKey) {
			return( (CFBamJpaTweakHPKey)key );
		}
		else {
			CFBamJpaTweakHPKey mapped = new CFBamJpaTweakHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredId( key.getRequiredId() );
			return( mapped );
		}
	}

    @Override
    public ICFBamTweakByUNameIdxKey newByUNameIdxKey() {
	ICFBamTweakByUNameIdxKey key =
            new CFBamJpaTweakByUNameIdxKey();
	return( key );
    }

	public CFBamJpaTweakByUNameIdxKey ensureByUNameIdxKey(ICFBamTweakByUNameIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaTweakByUNameIdxKey) {
			return( (CFBamJpaTweakByUNameIdxKey)key );
		}
		else {
			CFBamJpaTweakByUNameIdxKey mapped = new CFBamJpaTweakByUNameIdxKey();
			mapped.setRequiredScopeId( key.getRequiredScopeId() );
			mapped.setRequiredName( key.getRequiredName() );
			return( mapped );
		}
	}

    @Override
    public ICFBamTweakByValTentIdxKey newByValTentIdxKey() {
	ICFBamTweakByValTentIdxKey key =
            new CFBamJpaTweakByValTentIdxKey();
	return( key );
    }

	public CFBamJpaTweakByValTentIdxKey ensureByValTentIdxKey(ICFBamTweakByValTentIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaTweakByValTentIdxKey) {
			return( (CFBamJpaTweakByValTentIdxKey)key );
		}
		else {
			CFBamJpaTweakByValTentIdxKey mapped = new CFBamJpaTweakByValTentIdxKey();
			mapped.setRequiredTenantId( key.getRequiredTenantId() );
			return( mapped );
		}
	}

    @Override
    public ICFBamTweakByScopeIdxKey newByScopeIdxKey() {
	ICFBamTweakByScopeIdxKey key =
            new CFBamJpaTweakByScopeIdxKey();
	return( key );
    }

	public CFBamJpaTweakByScopeIdxKey ensureByScopeIdxKey(ICFBamTweakByScopeIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaTweakByScopeIdxKey) {
			return( (CFBamJpaTweakByScopeIdxKey)key );
		}
		else {
			CFBamJpaTweakByScopeIdxKey mapped = new CFBamJpaTweakByScopeIdxKey();
			mapped.setRequiredScopeId( key.getRequiredScopeId() );
			return( mapped );
		}
	}

    @Override
    public ICFBamTweakByDefSchemaIdxKey newByDefSchemaIdxKey() {
	ICFBamTweakByDefSchemaIdxKey key =
            new CFBamJpaTweakByDefSchemaIdxKey();
	return( key );
    }

	public CFBamJpaTweakByDefSchemaIdxKey ensureByDefSchemaIdxKey(ICFBamTweakByDefSchemaIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaTweakByDefSchemaIdxKey) {
			return( (CFBamJpaTweakByDefSchemaIdxKey)key );
		}
		else {
			CFBamJpaTweakByDefSchemaIdxKey mapped = new CFBamJpaTweakByDefSchemaIdxKey();
			mapped.setOptionalDefSchemaId( key.getOptionalDefSchemaId() );
			return( mapped );
		}
	}

    @Override
    public ICFBamTweakByUDefIdxKey newByUDefIdxKey() {
	ICFBamTweakByUDefIdxKey key =
            new CFBamJpaTweakByUDefIdxKey();
	return( key );
    }

	public CFBamJpaTweakByUDefIdxKey ensureByUDefIdxKey(ICFBamTweakByUDefIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaTweakByUDefIdxKey) {
			return( (CFBamJpaTweakByUDefIdxKey)key );
		}
		else {
			CFBamJpaTweakByUDefIdxKey mapped = new CFBamJpaTweakByUDefIdxKey();
			mapped.setRequiredTenantId( key.getRequiredTenantId() );
			mapped.setRequiredScopeId( key.getRequiredScopeId() );
			mapped.setOptionalDefSchemaTenantId( key.getOptionalDefSchemaTenantId() );
			mapped.setOptionalDefSchemaId( key.getOptionalDefSchemaId() );
			mapped.setRequiredName( key.getRequiredName() );
			return( mapped );
		}
	}

    @Override
    public ICFBamTweak newRec() {
        ICFBamTweak rec =
            new CFBamJpaTweak();
        return( rec );
    }

	public CFBamJpaTweak ensureRec(ICFBamTweak rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFBamJpaTweak) {
			return( (CFBamJpaTweak)rec );
		}
		else {
			CFBamJpaTweak mapped = new CFBamJpaTweak();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFBamTweakH newHRec() {
        ICFBamTweakH hrec =
            new CFBamJpaTweakH();
        return( hrec );
    }

	public CFBamJpaTweakH ensureHRec(ICFBamTweakH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFBamJpaTweakH) {
			return( (CFBamJpaTweakH)hrec );
		}
		else {
			CFBamJpaTweakH mapped = new CFBamJpaTweakH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}
