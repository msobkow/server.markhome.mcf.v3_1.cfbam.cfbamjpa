
// Description: Java 25 DbIO implementation for PopTopDep.

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
import java.time.*;
import java.util.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;
import server.markhome.mcf.v3_1.cfint.cfint.*;
import server.markhome.mcf.v3_1.cfbam.cfbam.*;
import server.markhome.mcf.v3_1.cfsec.cfsecobj.*;
import server.markhome.mcf.v3_1.cfint.cfintobj.*;
import server.markhome.mcf.v3_1.cfbam.cfbamobj.*;
import server.markhome.mcf.v3_1.cfbam.cfbam.jpa.CFBamJpaHooksSchema;

/*
 *	CFBamJpaPopTopDepTable database implementation for PopTopDep
 */
public class CFBamJpaPopTopDepTable implements ICFBamPopTopDepTable
{
	protected CFBamJpaSchema schema;


	public CFBamJpaPopTopDepTable(ICFBamSchema schema) {
		if( schema == null ) {
			throw new CFLibNullArgumentException(getClass(), "constructor", 1, "schema" );
		}
		if (schema instanceof CFBamJpaSchema) {
			this.schema = (CFBamJpaSchema)schema;
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "constructor", "schema", schema, "CFBamJpaSchema");
		}
	}

	protected boolean canCreatePopTopDep(String S_ProcName, ICFSecAuthorization Authorization) {
		if (Authorization == null) {
			throw new CFLibNullArgumentException(getClass(), S_ProcName, 0, "Authorization");
		}
		boolean permissionGranted = false;
		CFLibDbKeyHash256 authUserId = Authorization.getSecUserId();
		if ((!permissionGranted) && (authUserId == null || authUserId.isNull())) {
			throw new CFLibNullArgumentException(getClass(), S_ProcName, 0, "Authorization.getSecUserId()");
		}
		// Check for "system" user
		CFLibDbKeyHash256 systemId = ICFSecSchema.getSystemId();
		if ((!permissionGranted) && (systemId != null && !systemId.isNull() && systemId.equals(authUserId))) {
			permissionGranted = true;
		}
		else if ((!permissionGranted) && (systemId == null || systemId.isNull())) {
			throw new CFLibNullArgumentException(getClass(), S_ProcName, 0, "ICFSecSchema.getSystemId()");
		}
		if(!permissionGranted) {
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), Authorization.getSecClusterId(), Authorization.getSecTenantId(), "createpoptopdep");
		}
		return( permissionGranted );
	}

	protected boolean canReadPopTopDep(String S_ProcName, ICFSecAuthorization Authorization) {
		if (Authorization == null) {
			throw new CFLibNullArgumentException(getClass(), S_ProcName, 0, "Authorization");
		}
		boolean permissionGranted = false;
		CFLibDbKeyHash256 authUserId = Authorization.getSecUserId();
		if ((!permissionGranted) && (authUserId == null || authUserId.isNull())) {
			throw new CFLibNullArgumentException(getClass(), S_ProcName, 0, "Authorization.getSecUserId()");
		}
		// Check for "system" user
		CFLibDbKeyHash256 systemId = ICFSecSchema.getSystemId();
		if ((!permissionGranted) && (systemId != null && !systemId.isNull() && systemId.equals(authUserId))) {
			permissionGranted = true;
		}
		else if ((!permissionGranted) && (systemId == null || systemId.isNull())) {
			throw new CFLibNullArgumentException(getClass(), S_ProcName, 0, "ICFSecSchema.getSystemId()");
		}
		if(!permissionGranted) {
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), Authorization.getSecClusterId(), Authorization.getSecTenantId(), "readpoptopdep");
		}
		return( permissionGranted );
	}

	protected boolean canUpdatePopTopDep(String S_ProcName, ICFSecAuthorization Authorization) {
		if (Authorization == null) {
			throw new CFLibNullArgumentException(getClass(), S_ProcName, 0, "Authorization");
		}
		boolean permissionGranted = false;
		CFLibDbKeyHash256 authUserId = Authorization.getSecUserId();
		if ((!permissionGranted) && (authUserId == null || authUserId.isNull())) {
			throw new CFLibNullArgumentException(getClass(), S_ProcName, 0, "Authorization.getSecUserId()");
		}
		// Check for "system" user
		CFLibDbKeyHash256 systemId = ICFSecSchema.getSystemId();
		if ((!permissionGranted) && (systemId != null && !systemId.isNull() && systemId.equals(authUserId))) {
			permissionGranted = true;
		}
		else if ((!permissionGranted) && (systemId == null || systemId.isNull())) {
			throw new CFLibNullArgumentException(getClass(), S_ProcName, 0, "ICFSecSchema.getSystemId()");
		}
		if(!permissionGranted) {
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), ICFSecSchema.getSysClusterId(), ICFSecSchema.getSysTenantId(), "updatepoptopdep");
		}
		return( permissionGranted );
	}

	protected boolean canDeletePopTopDep(String S_ProcName, ICFSecAuthorization Authorization) {
		if (Authorization == null) {
			throw new CFLibNullArgumentException(getClass(), S_ProcName, 0, "Authorization");
		}
		boolean permissionGranted = false;
		CFLibDbKeyHash256 authUserId = Authorization.getSecUserId();
		if ((!permissionGranted) && (authUserId == null || authUserId.isNull())) {
			throw new CFLibNullArgumentException(getClass(), S_ProcName, 0, "Authorization.getSecUserId()");
		}
		// Check for "system" user
		CFLibDbKeyHash256 systemId = ICFSecSchema.getSystemId();
		if ((!permissionGranted) && (systemId != null && !systemId.isNull() && systemId.equals(authUserId))) {
			permissionGranted = true;
		}
		else if ((!permissionGranted) && (systemId == null || systemId.isNull())) {
			throw new CFLibNullArgumentException(getClass(), S_ProcName, 0, "ICFSecSchema.getSystemId()");
		}
		if(!permissionGranted) {
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), ICFSecSchema.getSysClusterId(), ICFSecSchema.getSysTenantId(), "deletepoptopdep");
		}
		return( permissionGranted );
	}

	/**
	 *	Create the instance in the database, and update the specified record
	 *	with the assigned primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	rec	The instance interface to be created.
	 */
	@Override
	public ICFBamPopTopDep createPopTopDep( ICFSecAuthorization Authorization,
		ICFBamPopTopDep rec )
	{
		final String S_ProcName = "createPopTopDep";
		boolean permissionGranted = canCreatePopTopDep(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopTopDepTable.TABLE_NAME, "createpoptopdep", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createPopTopDep", 1, "rec");
		}
		else if (rec instanceof CFBamJpaPopTopDep) {
			CFBamJpaPopTopDep jparec = (CFBamJpaPopTopDep)rec;
			CFBamJpaPopTopDep retval = schema.getJpaHooksSchema().getPopTopDepService().create(jparec);
		if(retval != null) {
			// Retrieve the TenantId from retval and check ICFSec.backingSchema().isMemberOfTenantGroup(auth,ClusterId,TenantId,'readpoptopdep'), clear retval to null if not a member
			CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
			CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readpoptopdep")) {
				retval = null;
			}
		}
		return( retval );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createPopTopDep", "rec", rec, "CFBamJpaPopTopDep");
		}
	}

	/**
	 *	Update the instance in the database, and update the specified record
	 *	with any calculated changes imposed by the associated stored procedure.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	rec	The instance interface to be updated
	 */
	@Override
	public ICFBamPopTopDep updatePopTopDep( ICFSecAuthorization Authorization,
		ICFBamPopTopDep rec )
	{
		final String S_ProcName = "updatePopTopDep";
		boolean permissionGranted = canUpdatePopTopDep(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopTopDepTable.TABLE_NAME, "updatepoptopdep", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updatePopTopDep", 1, "rec");
		}
		else if (rec instanceof CFBamJpaPopTopDep) {
			CFBamJpaPopTopDep jparec = (CFBamJpaPopTopDep)rec;
			CFBamJpaPopTopDep retval = schema.getJpaHooksSchema().getPopTopDepService().update(jparec);
		if(retval != null) {
			// Retrieve the TenantId from retval and check ICFSec.backingSchema().isMemberOfTenantGroup(auth,ClusterId,TenantId,'readpoptopdep'), clear retval to null if not a member
			CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
			CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readpoptopdep")) {
				retval = null;
			}
		}
		return( retval );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updatePopTopDep", "rec", rec, "CFBamJpaPopTopDep");
		}
	}

	/**
	 *	Delete the instance from the database.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	rec	The instance interface to be deleted.
	 */
	@Override
	public void deletePopTopDep( ICFSecAuthorization Authorization,
		ICFBamPopTopDep rec )
	{
		final String S_ProcName = "deletePopTopDep";
		boolean permissionGranted = canDeletePopTopDep(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopTopDepTable.TABLE_NAME, "deletepoptopdep", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		if (rec == null) {
			return;
		}
		if (rec instanceof CFBamJpaPopTopDep) {
			CFBamJpaPopTopDep jparec = (CFBamJpaPopTopDep)rec;
			schema.getJpaHooksSchema().getPopTopDepService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deletePopTopDep", "rec", rec, "CFBamJpaPopTopDep");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deletePopTopDep");
	}

	/**
	 *	Delete the PopTopDep instances identified by the key ContRelIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ContRelationId	The PopTopDep key attribute of the instance generating the id.
	 */
	@Override
	public void deletePopTopDepByContRelIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argContRelationId )
	{
		final String S_ProcName = "deletePopTopDepByContRelIdx";
		boolean permissionGranted = canDeletePopTopDep(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopTopDepTable.TABLE_NAME, "deletepoptopdep", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getPopTopDepService().deleteByContRelIdx(argContRelationId);
	}


	/**
	 *	Delete the PopTopDep instances identified by the key ContRelIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deletePopTopDepByContRelIdx( ICFSecAuthorization Authorization,
		ICFBamPopTopDepByContRelIdxKey argKey )
	{
		final String S_ProcName = "deletePopTopDepByContRelIdx";
		boolean permissionGranted = canDeletePopTopDep(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopTopDepTable.TABLE_NAME, "deletepoptopdep", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getPopTopDepService().deleteByContRelIdx(argKey.getRequiredContRelationId());
	}

	/**
	 *	Delete the PopTopDep instances identified by the key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ContRelationId	The PopTopDep key attribute of the instance generating the id.
	 *
	 *	@param	Name	The PopTopDep key attribute of the instance generating the id.
	 */
	@Override
	public void deletePopTopDepByUNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argContRelationId,
		String argName )
	{
		final String S_ProcName = "deletePopTopDepByUNameIdx";
		boolean permissionGranted = canDeletePopTopDep(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopTopDepTable.TABLE_NAME, "deletepoptopdep", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getPopTopDepService().deleteByUNameIdx(argContRelationId,
		argName);
	}


	/**
	 *	Delete the PopTopDep instances identified by the key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deletePopTopDepByUNameIdx( ICFSecAuthorization Authorization,
		ICFBamPopTopDepByUNameIdxKey argKey )
	{
		final String S_ProcName = "deletePopTopDepByUNameIdx";
		boolean permissionGranted = canDeletePopTopDep(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopTopDepTable.TABLE_NAME, "deletepoptopdep", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getPopTopDepService().deleteByUNameIdx(argKey.getRequiredContRelationId(),
			argKey.getRequiredName());
	}

	/**
	 *	Delete the PopTopDep instances identified by the key RelationIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	RelationId	The PopTopDep key attribute of the instance generating the id.
	 */
	@Override
	public void deletePopTopDepByRelationIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argRelationId )
	{
		final String S_ProcName = "deletePopTopDepByRelationIdx";
		boolean permissionGranted = canDeletePopTopDep(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopTopDepTable.TABLE_NAME, "deletepoptopdep", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getPopTopDepService().deleteByRelationIdx(argRelationId);
	}


	/**
	 *	Delete the PopTopDep instances identified by the key RelationIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deletePopTopDepByRelationIdx( ICFSecAuthorization Authorization,
		ICFBamPopDepByRelationIdxKey argKey )
	{
		final String S_ProcName = "deletePopTopDepByRelationIdx";
		boolean permissionGranted = canDeletePopTopDep(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopTopDepTable.TABLE_NAME, "deletepoptopdep", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getPopTopDepService().deleteByRelationIdx(argKey.getRequiredRelationId());
	}

	/**
	 *	Delete the PopTopDep instances identified by the key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DefSchemaId	The PopTopDep key attribute of the instance generating the id.
	 */
	@Override
	public void deletePopTopDepByDefSchemaIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argDefSchemaId )
	{
		final String S_ProcName = "deletePopTopDepByDefSchemaIdx";
		boolean permissionGranted = canDeletePopTopDep(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopTopDepTable.TABLE_NAME, "deletepoptopdep", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getPopTopDepService().deleteByDefSchemaIdx(argDefSchemaId);
	}


	/**
	 *	Delete the PopTopDep instances identified by the key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deletePopTopDepByDefSchemaIdx( ICFSecAuthorization Authorization,
		ICFBamPopDepByDefSchemaIdxKey argKey )
	{
		final String S_ProcName = "deletePopTopDepByDefSchemaIdx";
		boolean permissionGranted = canDeletePopTopDep(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopTopDepTable.TABLE_NAME, "deletepoptopdep", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getPopTopDepService().deleteByDefSchemaIdx(argKey.getOptionalDefSchemaId());
	}

	/**
	 *	Delete the PopTopDep instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deletePopTopDepByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argKey )
	{
		final String S_ProcName = "deletePopTopDepByIdIdx";
		boolean permissionGranted = canDeletePopTopDep(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopTopDepTable.TABLE_NAME, "deletepoptopdep", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getPopTopDepService().deleteByIdIdx(argKey);
	}

	/**
	 *	Delete the PopTopDep instances identified by the key TenantIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TenantId	The PopTopDep key attribute of the instance generating the id.
	 */
	@Override
	public void deletePopTopDepByTenantIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTenantId )
	{
		final String S_ProcName = "deletePopTopDepByTenantIdx";
		boolean permissionGranted = canDeletePopTopDep(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopTopDepTable.TABLE_NAME, "deletepoptopdep", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getPopTopDepService().deleteByTenantIdx(argTenantId);
	}


	/**
	 *	Delete the PopTopDep instances identified by the key TenantIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deletePopTopDepByTenantIdx( ICFSecAuthorization Authorization,
		ICFBamScopeByTenantIdxKey argKey )
	{
		final String S_ProcName = "deletePopTopDepByTenantIdx";
		boolean permissionGranted = canDeletePopTopDep(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopTopDepTable.TABLE_NAME, "deletepoptopdep", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getPopTopDepService().deleteByTenantIdx(argKey.getRequiredTenantId());
	}


	/**
	 *	Read the derived PopTopDep record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the PopTopDep instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamPopTopDep readDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		final String S_ProcName = "readDerived";
		boolean permissionGranted = canReadPopTopDep(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopTopDepTable.TABLE_NAME, "readpoptopdep", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFBamPopTopDep retval = schema.getJpaHooksSchema().getPopTopDepService().find(PKey);
		if(retval != null) {
			// Retrieve the TenantId from retval and check ICFSec.backingSchema().isMemberOfTenantGroup(auth,ClusterId,TenantId,'readpoptopdep'), clear retval to null if not a member
			CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
			CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readpoptopdep")) {
				retval = null;
			}
		}
		return( retval );
	}

	/**
	 *	Lock the derived PopTopDep record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the PopTopDep instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamPopTopDep lockDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		final String S_ProcName = "lockDerived";
		boolean permissionGranted = canUpdatePopTopDep(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopTopDepTable.TABLE_NAME, "updatepoptopdep", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFBamPopTopDep retval = schema.getJpaHooksSchema().getPopTopDepService().lockByIdIdx(PKey);
		if(retval != null) {
			// Retrieve the TenantId from retval and check ICFSec.backingSchema().isMemberOfTenantGroup(auth,ClusterId,TenantId,'readpoptopdep'), clear retval to null if not a member
			CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
			CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readpoptopdep")) {
				retval = null;
			}
		}
		return( retval );
	}

	/**
	 *	Read all PopTopDep instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamPopTopDep[] readAllDerived( ICFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllDerived";
		boolean permissionGranted = canReadPopTopDep(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopTopDepTable.TABLE_NAME, "readpoptopdep", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaPopTopDep> retlist = schema.getJpaHooksSchema().getPopTopDepService().findAll();
		if(retlist != null) {
			ArrayList<CFBamJpaPopTopDep> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readpoptopdep")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamPopTopDep[] retset = new ICFBamPopTopDep[retlist.size()];
		int idx = 0;
		for (CFBamJpaPopTopDep cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived PopTopDep record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Id	The PopTopDep key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamPopTopDep readDerivedByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argId )
	{
		final String S_ProcName = "readDerivedByIdIdx";
		boolean permissionGranted = canReadPopTopDep(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopTopDepTable.TABLE_NAME, "readpoptopdep", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFBamPopTopDep retval = schema.getJpaHooksSchema().getPopTopDepService().find(argId);
		if(retval != null) {
			// Retrieve the TenantId from retval and check ICFSec.backingSchema().isMemberOfTenantGroup(auth,ClusterId,TenantId,'readpoptopdep'), clear retval to null if not a member
			CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
			CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readpoptopdep")) {
				retval = null;
			}
		}
		return( retval );
	}

	/**
	 *	Read an array of the derived PopTopDep record instances identified by the duplicate key TenantIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TenantId	The PopTopDep key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamPopTopDep[] readDerivedByTenantIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTenantId )
	{
		final String S_ProcName = "readDerivedByTenantIdx";
		boolean permissionGranted = canReadPopTopDep(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopTopDepTable.TABLE_NAME, "readpoptopdep", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaPopTopDep> retlist = schema.getJpaHooksSchema().getPopTopDepService().findByTenantIdx(argTenantId);
		if(retlist != null) {
			ArrayList<CFBamJpaPopTopDep> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readpoptopdep")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamPopTopDep[] retset = new ICFBamPopTopDep[retlist.size()];
		int idx = 0;
		for (CFBamJpaPopTopDep cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived PopTopDep record instances identified by the duplicate key RelationIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	RelationId	The PopTopDep key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamPopTopDep[] readDerivedByRelationIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argRelationId )
	{
		final String S_ProcName = "readDerivedByRelationIdx";
		boolean permissionGranted = canReadPopTopDep(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopTopDepTable.TABLE_NAME, "readpoptopdep", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaPopTopDep> retlist = schema.getJpaHooksSchema().getPopTopDepService().findByRelationIdx(argRelationId);
		if(retlist != null) {
			ArrayList<CFBamJpaPopTopDep> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readpoptopdep")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamPopTopDep[] retset = new ICFBamPopTopDep[retlist.size()];
		int idx = 0;
		for (CFBamJpaPopTopDep cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived PopTopDep record instances identified by the duplicate key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DefSchemaId	The PopTopDep key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamPopTopDep[] readDerivedByDefSchemaIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argDefSchemaId )
	{
		final String S_ProcName = "readDerivedByDefSchemaIdx";
		boolean permissionGranted = canReadPopTopDep(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopTopDepTable.TABLE_NAME, "readpoptopdep", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaPopTopDep> retlist = schema.getJpaHooksSchema().getPopTopDepService().findByDefSchemaIdx(argDefSchemaId);
		if(retlist != null) {
			ArrayList<CFBamJpaPopTopDep> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readpoptopdep")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamPopTopDep[] retset = new ICFBamPopTopDep[retlist.size()];
		int idx = 0;
		for (CFBamJpaPopTopDep cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived PopTopDep record instances identified by the duplicate key ContRelIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ContRelationId	The PopTopDep key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamPopTopDep[] readDerivedByContRelIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argContRelationId )
	{
		final String S_ProcName = "readDerivedByContRelIdx";
		boolean permissionGranted = canReadPopTopDep(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopTopDepTable.TABLE_NAME, "readpoptopdep", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaPopTopDep> retlist = schema.getJpaHooksSchema().getPopTopDepService().findByContRelIdx(argContRelationId);
		if(retlist != null) {
			ArrayList<CFBamJpaPopTopDep> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readpoptopdep")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamPopTopDep[] retset = new ICFBamPopTopDep[retlist.size()];
		int idx = 0;
		for (CFBamJpaPopTopDep cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived PopTopDep record instance identified by the unique key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ContRelationId	The PopTopDep key attribute of the instance generating the id.
	 *
	 *	@param	Name	The PopTopDep key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamPopTopDep readDerivedByUNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argContRelationId,
		String argName )
	{
		final String S_ProcName = "readDerivedByUNameIdx";
		boolean permissionGranted = canReadPopTopDep(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopTopDepTable.TABLE_NAME, "readpoptopdep", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFBamPopTopDep retval = schema.getJpaHooksSchema().getPopTopDepService().findByUNameIdx(argContRelationId,
		argName);
		if(retval != null) {
			// Retrieve the TenantId from retval and check ICFSec.backingSchema().isMemberOfTenantGroup(auth,ClusterId,TenantId,'readpoptopdep'), clear retval to null if not a member
			CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
			CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readpoptopdep")) {
				retval = null;
			}
		}
		return( retval );
	}

	/**
	 *	Read the specific PopTopDep record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the PopTopDep instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamPopTopDep readRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		final String S_ProcName = "readRec";
		boolean permissionGranted = canReadPopTopDep(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopTopDepTable.TABLE_NAME, "readpoptopdep", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Lock the specific PopTopDep record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the PopTopDep instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamPopTopDep lockRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		final String S_ProcName = "lockRec";
		boolean permissionGranted = canUpdatePopTopDep(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopTopDepTable.TABLE_NAME, "updatepoptopdep", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific PopTopDep record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific PopTopDep instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFBamPopTopDep[] readAllRec( ICFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllRec";
		boolean permissionGranted = canReadPopTopDep(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopTopDepTable.TABLE_NAME, "readpoptopdep", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read the specific PopTopDep record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Id	The PopTopDep key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamPopTopDep readRecByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argId )
	{
		final String S_ProcName = "readRecByIdIdx";
		boolean permissionGranted = canReadPopTopDep(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopTopDepTable.TABLE_NAME, "readpoptopdep", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read an array of the specific PopTopDep record instances identified by the duplicate key TenantIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TenantId	The PopTopDep key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamPopTopDep[] readRecByTenantIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTenantId )
	{
		final String S_ProcName = "readRecByTenantIdx";
		boolean permissionGranted = canReadPopTopDep(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopTopDepTable.TABLE_NAME, "readpoptopdep", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByTenantIdx");
	}

	/**
	 *	Read an array of the specific PopTopDep record instances identified by the duplicate key RelationIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	RelationId	The PopTopDep key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamPopTopDep[] readRecByRelationIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argRelationId )
	{
		final String S_ProcName = "readRecByRelationIdx";
		boolean permissionGranted = canReadPopTopDep(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopTopDepTable.TABLE_NAME, "readpoptopdep", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByRelationIdx");
	}

	/**
	 *	Read an array of the specific PopTopDep record instances identified by the duplicate key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DefSchemaId	The PopTopDep key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamPopTopDep[] readRecByDefSchemaIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argDefSchemaId )
	{
		final String S_ProcName = "readRecByDefSchemaIdx";
		boolean permissionGranted = canReadPopTopDep(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopTopDepTable.TABLE_NAME, "readpoptopdep", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByDefSchemaIdx");
	}

	/**
	 *	Read an array of the specific PopTopDep record instances identified by the duplicate key ContRelIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ContRelationId	The PopTopDep key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamPopTopDep[] readRecByContRelIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argContRelationId )
	{
		final String S_ProcName = "readRecByContRelIdx";
		boolean permissionGranted = canReadPopTopDep(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopTopDepTable.TABLE_NAME, "readpoptopdep", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByContRelIdx");
	}

	/**
	 *	Read the specific PopTopDep record instance identified by the unique key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ContRelationId	The PopTopDep key attribute of the instance generating the id.
	 *
	 *	@param	Name	The PopTopDep key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamPopTopDep readRecByUNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argContRelationId,
		String argName )
	{
		final String S_ProcName = "readRecByUNameIdx";
		boolean permissionGranted = canReadPopTopDep(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopTopDepTable.TABLE_NAME, "readpoptopdep", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByUNameIdx");
	}
}
