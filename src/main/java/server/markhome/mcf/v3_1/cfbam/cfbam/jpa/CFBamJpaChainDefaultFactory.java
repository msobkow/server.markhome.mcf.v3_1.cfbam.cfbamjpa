
// Description: Java 25 JPA Default Factory implementation for Chain.

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
 *	CFBamChainFactory JPA implementation for Chain
 */
public class CFBamJpaChainDefaultFactory
    implements ICFBamChainFactory
{
    public CFBamJpaChainDefaultFactory() {
    }

    @Override
    public ICFBamChainHPKey newHPKey() {
        ICFBamChainHPKey hpkey =
            new CFBamJpaChainHPKey();
        return( hpkey );
    }

	public CFBamJpaChainHPKey ensureHPKey(ICFBamChainHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFBamJpaChainHPKey) {
			return( (CFBamJpaChainHPKey)key );
		}
		else {
			CFBamJpaChainHPKey mapped = new CFBamJpaChainHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredId( key.getRequiredId() );
			return( mapped );
		}
	}

    @Override
    public ICFBamChainByChainTableIdxKey newByChainTableIdxKey() {
	ICFBamChainByChainTableIdxKey key =
            new CFBamJpaChainByChainTableIdxKey();
	return( key );
    }

	public CFBamJpaChainByChainTableIdxKey ensureByChainTableIdxKey(ICFBamChainByChainTableIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaChainByChainTableIdxKey) {
			return( (CFBamJpaChainByChainTableIdxKey)key );
		}
		else {
			CFBamJpaChainByChainTableIdxKey mapped = new CFBamJpaChainByChainTableIdxKey();
			mapped.setRequiredTableId( key.getRequiredTableId() );
			return( mapped );
		}
	}

    @Override
    public ICFBamChainByDefSchemaIdxKey newByDefSchemaIdxKey() {
	ICFBamChainByDefSchemaIdxKey key =
            new CFBamJpaChainByDefSchemaIdxKey();
	return( key );
    }

	public CFBamJpaChainByDefSchemaIdxKey ensureByDefSchemaIdxKey(ICFBamChainByDefSchemaIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaChainByDefSchemaIdxKey) {
			return( (CFBamJpaChainByDefSchemaIdxKey)key );
		}
		else {
			CFBamJpaChainByDefSchemaIdxKey mapped = new CFBamJpaChainByDefSchemaIdxKey();
			mapped.setOptionalDefSchemaId( key.getOptionalDefSchemaId() );
			return( mapped );
		}
	}

    @Override
    public ICFBamChainByUNameIdxKey newByUNameIdxKey() {
	ICFBamChainByUNameIdxKey key =
            new CFBamJpaChainByUNameIdxKey();
	return( key );
    }

	public CFBamJpaChainByUNameIdxKey ensureByUNameIdxKey(ICFBamChainByUNameIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaChainByUNameIdxKey) {
			return( (CFBamJpaChainByUNameIdxKey)key );
		}
		else {
			CFBamJpaChainByUNameIdxKey mapped = new CFBamJpaChainByUNameIdxKey();
			mapped.setRequiredTableId( key.getRequiredTableId() );
			mapped.setRequiredName( key.getRequiredName() );
			return( mapped );
		}
	}

    @Override
    public ICFBamChainByPrevRelIdxKey newByPrevRelIdxKey() {
	ICFBamChainByPrevRelIdxKey key =
            new CFBamJpaChainByPrevRelIdxKey();
	return( key );
    }

	public CFBamJpaChainByPrevRelIdxKey ensureByPrevRelIdxKey(ICFBamChainByPrevRelIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaChainByPrevRelIdxKey) {
			return( (CFBamJpaChainByPrevRelIdxKey)key );
		}
		else {
			CFBamJpaChainByPrevRelIdxKey mapped = new CFBamJpaChainByPrevRelIdxKey();
			mapped.setRequiredPrevRelationId( key.getRequiredPrevRelationId() );
			return( mapped );
		}
	}

    @Override
    public ICFBamChainByNextRelIdxKey newByNextRelIdxKey() {
	ICFBamChainByNextRelIdxKey key =
            new CFBamJpaChainByNextRelIdxKey();
	return( key );
    }

	public CFBamJpaChainByNextRelIdxKey ensureByNextRelIdxKey(ICFBamChainByNextRelIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaChainByNextRelIdxKey) {
			return( (CFBamJpaChainByNextRelIdxKey)key );
		}
		else {
			CFBamJpaChainByNextRelIdxKey mapped = new CFBamJpaChainByNextRelIdxKey();
			mapped.setRequiredNextRelationId( key.getRequiredNextRelationId() );
			return( mapped );
		}
	}

    @Override
    public ICFBamChain newRec() {
        ICFBamChain rec =
            new CFBamJpaChain();
        return( rec );
    }

	public CFBamJpaChain ensureRec(ICFBamChain rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFBamJpaChain) {
			return( (CFBamJpaChain)rec );
		}
		else {
			CFBamJpaChain mapped = new CFBamJpaChain();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFBamChainH newHRec() {
        ICFBamChainH hrec =
            new CFBamJpaChainH();
        return( hrec );
    }

	public CFBamJpaChainH ensureHRec(ICFBamChainH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFBamJpaChainH) {
			return( (CFBamJpaChainH)hrec );
		}
		else {
			CFBamJpaChainH mapped = new CFBamJpaChainH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}
