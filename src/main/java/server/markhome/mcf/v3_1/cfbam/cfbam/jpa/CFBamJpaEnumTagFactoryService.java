
// Description: Java 25 Factory service implementation for EnumTag JPA objects

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
import server.markhome.mcf.v3_1.cflib.keyhash.*;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;
import server.markhome.mcf.v3_1.cfint.cfint.*;
import server.markhome.mcf.v3_1.cfbam.cfbam.*;
import server.markhome.mcf.v3_1.cfsec.cfsec.jpa.*;
import server.markhome.mcf.v3_1.cfint.cfint.jpa.*;

/*
 *	Java 25 Factory service implementation for EnumTag JPA objects.
 */
public class CFBamJpaEnumTagFactoryService
    implements ICFBamEnumTagFactory
{
    public CFBamJpaEnumTagFactoryService() { }

    @Override
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
    public ICFBamEnumTagHPKey newHPKey() {
        ICFBamEnumTagHPKey hpkey = new CFBamJpaEnumTagHPKey();
        return( hpkey );
    }

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public CFBamJpaEnumTagHPKey ensureHPKey(ICFBamEnumTagHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFBamJpaEnumTagHPKey) {
			return( (CFBamJpaEnumTagHPKey)key );
		}
		else {
			CFBamJpaEnumTagHPKey mapped = new CFBamJpaEnumTagHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredId( key.getRequiredId() );
			return( mapped );
		}
	}

    @Override
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
    public ICFBamEnumTagByEnumIdxKey newByEnumIdxKey() {
		ICFBamEnumTagByEnumIdxKey key = new CFBamJpaEnumTagByEnumIdxKey();
	return( key );
    }

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public CFBamJpaEnumTagByEnumIdxKey ensureByEnumIdxKey(ICFBamEnumTagByEnumIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaEnumTagByEnumIdxKey) {
			return( (CFBamJpaEnumTagByEnumIdxKey)key );
		}
		else {
			CFBamJpaEnumTagByEnumIdxKey mapped = new CFBamJpaEnumTagByEnumIdxKey();
			mapped.setRequiredEnumId( key.getRequiredEnumId() );
			return( mapped );
		}
	}

    @Override
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
    public ICFBamEnumTagByDefSchemaIdxKey newByDefSchemaIdxKey() {
		ICFBamEnumTagByDefSchemaIdxKey key = new CFBamJpaEnumTagByDefSchemaIdxKey();
	return( key );
    }

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public CFBamJpaEnumTagByDefSchemaIdxKey ensureByDefSchemaIdxKey(ICFBamEnumTagByDefSchemaIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaEnumTagByDefSchemaIdxKey) {
			return( (CFBamJpaEnumTagByDefSchemaIdxKey)key );
		}
		else {
			CFBamJpaEnumTagByDefSchemaIdxKey mapped = new CFBamJpaEnumTagByDefSchemaIdxKey();
			mapped.setOptionalDefSchemaId( key.getOptionalDefSchemaId() );
			return( mapped );
		}
	}

    @Override
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
    public ICFBamEnumTagByEnumNameIdxKey newByEnumNameIdxKey() {
		ICFBamEnumTagByEnumNameIdxKey key = new CFBamJpaEnumTagByEnumNameIdxKey();
	return( key );
    }

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public CFBamJpaEnumTagByEnumNameIdxKey ensureByEnumNameIdxKey(ICFBamEnumTagByEnumNameIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaEnumTagByEnumNameIdxKey) {
			return( (CFBamJpaEnumTagByEnumNameIdxKey)key );
		}
		else {
			CFBamJpaEnumTagByEnumNameIdxKey mapped = new CFBamJpaEnumTagByEnumNameIdxKey();
			mapped.setRequiredEnumId( key.getRequiredEnumId() );
			mapped.setRequiredName( key.getRequiredName() );
			return( mapped );
		}
	}

    @Override
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
    public ICFBamEnumTagByPrevIdxKey newByPrevIdxKey() {
		ICFBamEnumTagByPrevIdxKey key = new CFBamJpaEnumTagByPrevIdxKey();
	return( key );
    }

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public CFBamJpaEnumTagByPrevIdxKey ensureByPrevIdxKey(ICFBamEnumTagByPrevIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaEnumTagByPrevIdxKey) {
			return( (CFBamJpaEnumTagByPrevIdxKey)key );
		}
		else {
			CFBamJpaEnumTagByPrevIdxKey mapped = new CFBamJpaEnumTagByPrevIdxKey();
			mapped.setOptionalPrevId( key.getOptionalPrevId() );
			return( mapped );
		}
	}

    @Override
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
    public ICFBamEnumTagByNextIdxKey newByNextIdxKey() {
		ICFBamEnumTagByNextIdxKey key = new CFBamJpaEnumTagByNextIdxKey();
	return( key );
    }

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public CFBamJpaEnumTagByNextIdxKey ensureByNextIdxKey(ICFBamEnumTagByNextIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFBamJpaEnumTagByNextIdxKey) {
			return( (CFBamJpaEnumTagByNextIdxKey)key );
		}
		else {
			CFBamJpaEnumTagByNextIdxKey mapped = new CFBamJpaEnumTagByNextIdxKey();
			mapped.setOptionalNextId( key.getOptionalNextId() );
			return( mapped );
		}
	}

    @Override
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
    public ICFBamEnumTag newRec() {
        ICFBamEnumTag rec = new CFBamJpaEnumTag();
        return( rec );
    }

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public CFBamJpaEnumTag ensureRec(ICFBamEnumTag rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFBamJpaEnumTag) {
			return( (CFBamJpaEnumTag)rec );
		}
		else {
			switch(rec.getClassCode()) {
				case ICFBamEnumTag.CLASS_CODE: {
					CFBamJpaEnumTag mapped = new CFBamJpaEnumTag();
					mapped.set(rec);
					return( mapped ); }
				default:
					throw new CFLibUnsupportedClassException(getClass(), "ensureRec",
						"Unsupported class code " + rec.getClassCode() + " is not a derivative of CFBamEnumTag",
						"Unsupported class code " + rec.getClassCode() + " is not a derivative of CFBamEnumTag");
			}
		}
	}

    @Override
    public ICFBamEnumTagH newHRec() {
        ICFBamEnumTagH hrec = new CFBamJpaEnumTagH();
        return( hrec );
    }

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public CFBamJpaEnumTagH ensureHRec(ICFBamEnumTagH hrec) {
		if( hrec == null ) {
			return( null );
		}
		else if (hrec instanceof CFBamJpaEnumTagH) {
			return( (CFBamJpaEnumTagH)hrec );
		}
		else {
			switch(hrec.getClassCode()) {
				case ICFBamEnumTag.CLASS_CODE: {
					CFBamJpaEnumTagH mapped = new CFBamJpaEnumTagH();
					mapped.set(hrec);
					return( mapped ); }
				default:
					throw new CFLibUnsupportedClassException(getClass(), "ensureHRec",
						"Unsupported class code " + hrec.getClassCode() + " is not a derivative of CFBamEnumTag",
						"Unsupported class code " + hrec.getClassCode() + " is not a derivative of CFBamEnumTag");
			}
		}
	}
}
