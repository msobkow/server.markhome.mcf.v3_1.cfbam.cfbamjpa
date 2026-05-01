
// Description: Java 25 DbIO implementation for PopSubDep2.

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
 *	CFBamJpaPopSubDep2Table database implementation for PopSubDep2
 */
public class CFBamJpaPopSubDep2Table implements ICFBamPopSubDep2Table
{
	protected CFBamJpaSchema schema;


	public CFBamJpaPopSubDep2Table(ICFBamSchema schema) {
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

	protected boolean canCreatePopSubDep2(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), Authorization.getSecClusterId(), Authorization.getSecTenantId(), "createpopsubdep2");
		}
		return( permissionGranted );
	}

	protected boolean canReadPopSubDep2(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), Authorization.getSecClusterId(), Authorization.getSecTenantId(), "readpopsubdep2");
		}
		return( permissionGranted );
	}

	protected boolean canUpdatePopSubDep2(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), ICFSecSchema.getSysClusterId(), ICFSecSchema.getSysTenantId(), "updatepopsubdep2");
		}
		return( permissionGranted );
	}

	protected boolean canDeletePopSubDep2(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), ICFSecSchema.getSysClusterId(), ICFSecSchema.getSysTenantId(), "deletepopsubdep2");
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
	public ICFBamPopSubDep2 createPopSubDep2( ICFSecAuthorization Authorization,
		ICFBamPopSubDep2 rec )
	{
		final String S_ProcName = "createPopSubDep2";
		boolean permissionGranted = canCreatePopSubDep2(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopSubDep2Table.TABLE_NAME, "createpopsubdep2", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createPopSubDep2", 1, "rec");
		}
		else if (rec instanceof CFBamJpaPopSubDep2) {
			CFBamJpaPopSubDep2 jparec = (CFBamJpaPopSubDep2)rec;
			CFBamJpaPopSubDep2 retval = schema.getJpaHooksSchema().getPopSubDep2Service().create(jparec);
		if(retval != null) {
			// Retrieve the TenantId from retval and check ICFSec.backingSchema().isMemberOfTenantGroup(auth,ClusterId,TenantId,'readpopsubdep2'), clear retval to null if not a member
			CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
			CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readpopsubdep2")) {
				retval = null;
			}
		}
		return( retval );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createPopSubDep2", "rec", rec, "CFBamJpaPopSubDep2");
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
	public ICFBamPopSubDep2 updatePopSubDep2( ICFSecAuthorization Authorization,
		ICFBamPopSubDep2 rec )
	{
		final String S_ProcName = "updatePopSubDep2";
		boolean permissionGranted = canUpdatePopSubDep2(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopSubDep2Table.TABLE_NAME, "updatepopsubdep2", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updatePopSubDep2", 1, "rec");
		}
		else if (rec instanceof CFBamJpaPopSubDep2) {
			CFBamJpaPopSubDep2 jparec = (CFBamJpaPopSubDep2)rec;
			CFBamJpaPopSubDep2 retval = schema.getJpaHooksSchema().getPopSubDep2Service().update(jparec);
		if(retval != null) {
			// Retrieve the TenantId from retval and check ICFSec.backingSchema().isMemberOfTenantGroup(auth,ClusterId,TenantId,'readpopsubdep2'), clear retval to null if not a member
			CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
			CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readpopsubdep2")) {
				retval = null;
			}
		}
		return( retval );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updatePopSubDep2", "rec", rec, "CFBamJpaPopSubDep2");
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
	public void deletePopSubDep2( ICFSecAuthorization Authorization,
		ICFBamPopSubDep2 rec )
	{
		final String S_ProcName = "deletePopSubDep2";
		boolean permissionGranted = canDeletePopSubDep2(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopSubDep2Table.TABLE_NAME, "deletepopsubdep2", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		if (rec == null) {
			return;
		}
		if (rec instanceof CFBamJpaPopSubDep2) {
			CFBamJpaPopSubDep2 jparec = (CFBamJpaPopSubDep2)rec;
			schema.getJpaHooksSchema().getPopSubDep2Service().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deletePopSubDep2", "rec", rec, "CFBamJpaPopSubDep2");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deletePopSubDep2");
	}

	/**
	 *	Delete the PopSubDep2 instances identified by the key PopSubDep1Idx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PopSubDep1Id	The PopSubDep2 key attribute of the instance generating the id.
	 */
	@Override
	public void deletePopSubDep2ByPopSubDep1Idx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argPopSubDep1Id )
	{
		final String S_ProcName = "deletePopSubDep2ByPopSubDep1Idx";
		boolean permissionGranted = canDeletePopSubDep2(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopSubDep2Table.TABLE_NAME, "deletepopsubdep2", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getPopSubDep2Service().deleteByPopSubDep1Idx(argPopSubDep1Id);
	}


	/**
	 *	Delete the PopSubDep2 instances identified by the key PopSubDep1Idx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deletePopSubDep2ByPopSubDep1Idx( ICFSecAuthorization Authorization,
		ICFBamPopSubDep2ByPopSubDep1IdxKey argKey )
	{
		final String S_ProcName = "deletePopSubDep2ByPopSubDep1Idx";
		boolean permissionGranted = canDeletePopSubDep2(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopSubDep2Table.TABLE_NAME, "deletepopsubdep2", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getPopSubDep2Service().deleteByPopSubDep1Idx(argKey.getRequiredPopSubDep1Id());
	}

	/**
	 *	Delete the PopSubDep2 instances identified by the key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PopSubDep1Id	The PopSubDep2 key attribute of the instance generating the id.
	 *
	 *	@param	Name	The PopSubDep2 key attribute of the instance generating the id.
	 */
	@Override
	public void deletePopSubDep2ByUNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argPopSubDep1Id,
		String argName )
	{
		final String S_ProcName = "deletePopSubDep2ByUNameIdx";
		boolean permissionGranted = canDeletePopSubDep2(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopSubDep2Table.TABLE_NAME, "deletepopsubdep2", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getPopSubDep2Service().deleteByUNameIdx(argPopSubDep1Id,
		argName);
	}


	/**
	 *	Delete the PopSubDep2 instances identified by the key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deletePopSubDep2ByUNameIdx( ICFSecAuthorization Authorization,
		ICFBamPopSubDep2ByUNameIdxKey argKey )
	{
		final String S_ProcName = "deletePopSubDep2ByUNameIdx";
		boolean permissionGranted = canDeletePopSubDep2(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopSubDep2Table.TABLE_NAME, "deletepopsubdep2", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getPopSubDep2Service().deleteByUNameIdx(argKey.getRequiredPopSubDep1Id(),
			argKey.getRequiredName());
	}

	/**
	 *	Delete the PopSubDep2 instances identified by the key RelationIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	RelationId	The PopSubDep2 key attribute of the instance generating the id.
	 */
	@Override
	public void deletePopSubDep2ByRelationIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argRelationId )
	{
		final String S_ProcName = "deletePopSubDep2ByRelationIdx";
		boolean permissionGranted = canDeletePopSubDep2(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopSubDep2Table.TABLE_NAME, "deletepopsubdep2", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getPopSubDep2Service().deleteByRelationIdx(argRelationId);
	}


	/**
	 *	Delete the PopSubDep2 instances identified by the key RelationIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deletePopSubDep2ByRelationIdx( ICFSecAuthorization Authorization,
		ICFBamPopDepByRelationIdxKey argKey )
	{
		final String S_ProcName = "deletePopSubDep2ByRelationIdx";
		boolean permissionGranted = canDeletePopSubDep2(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopSubDep2Table.TABLE_NAME, "deletepopsubdep2", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getPopSubDep2Service().deleteByRelationIdx(argKey.getRequiredRelationId());
	}

	/**
	 *	Delete the PopSubDep2 instances identified by the key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DefSchemaId	The PopSubDep2 key attribute of the instance generating the id.
	 */
	@Override
	public void deletePopSubDep2ByDefSchemaIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argDefSchemaId )
	{
		final String S_ProcName = "deletePopSubDep2ByDefSchemaIdx";
		boolean permissionGranted = canDeletePopSubDep2(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopSubDep2Table.TABLE_NAME, "deletepopsubdep2", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getPopSubDep2Service().deleteByDefSchemaIdx(argDefSchemaId);
	}


	/**
	 *	Delete the PopSubDep2 instances identified by the key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deletePopSubDep2ByDefSchemaIdx( ICFSecAuthorization Authorization,
		ICFBamPopDepByDefSchemaIdxKey argKey )
	{
		final String S_ProcName = "deletePopSubDep2ByDefSchemaIdx";
		boolean permissionGranted = canDeletePopSubDep2(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopSubDep2Table.TABLE_NAME, "deletepopsubdep2", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getPopSubDep2Service().deleteByDefSchemaIdx(argKey.getOptionalDefSchemaId());
	}

	/**
	 *	Delete the PopSubDep2 instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deletePopSubDep2ByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argKey )
	{
		final String S_ProcName = "deletePopSubDep2ByIdIdx";
		boolean permissionGranted = canDeletePopSubDep2(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopSubDep2Table.TABLE_NAME, "deletepopsubdep2", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getPopSubDep2Service().deleteByIdIdx(argKey);
	}

	/**
	 *	Delete the PopSubDep2 instances identified by the key TenantIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TenantId	The PopSubDep2 key attribute of the instance generating the id.
	 */
	@Override
	public void deletePopSubDep2ByTenantIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTenantId )
	{
		final String S_ProcName = "deletePopSubDep2ByTenantIdx";
		boolean permissionGranted = canDeletePopSubDep2(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopSubDep2Table.TABLE_NAME, "deletepopsubdep2", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getPopSubDep2Service().deleteByTenantIdx(argTenantId);
	}


	/**
	 *	Delete the PopSubDep2 instances identified by the key TenantIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deletePopSubDep2ByTenantIdx( ICFSecAuthorization Authorization,
		ICFBamScopeByTenantIdxKey argKey )
	{
		final String S_ProcName = "deletePopSubDep2ByTenantIdx";
		boolean permissionGranted = canDeletePopSubDep2(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopSubDep2Table.TABLE_NAME, "deletepopsubdep2", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getPopSubDep2Service().deleteByTenantIdx(argKey.getRequiredTenantId());
	}


	/**
	 *	Read the derived PopSubDep2 record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the PopSubDep2 instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamPopSubDep2 readDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		final String S_ProcName = "readDerived";
		boolean permissionGranted = canReadPopSubDep2(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopSubDep2Table.TABLE_NAME, "readpopsubdep2", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFBamPopSubDep2 retval = schema.getJpaHooksSchema().getPopSubDep2Service().find(PKey);
		if(retval != null) {
			// Retrieve the TenantId from retval and check ICFSec.backingSchema().isMemberOfTenantGroup(auth,ClusterId,TenantId,'readpopsubdep2'), clear retval to null if not a member
			CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
			CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readpopsubdep2")) {
				retval = null;
			}
		}
		return( retval );
	}

	/**
	 *	Lock the derived PopSubDep2 record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the PopSubDep2 instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamPopSubDep2 lockDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		final String S_ProcName = "lockDerived";
		boolean permissionGranted = canUpdatePopSubDep2(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopSubDep2Table.TABLE_NAME, "updatepopsubdep2", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFBamPopSubDep2 retval = schema.getJpaHooksSchema().getPopSubDep2Service().lockByIdIdx(PKey);
		if(retval != null) {
			// Retrieve the TenantId from retval and check ICFSec.backingSchema().isMemberOfTenantGroup(auth,ClusterId,TenantId,'readpopsubdep2'), clear retval to null if not a member
			CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
			CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readpopsubdep2")) {
				retval = null;
			}
		}
		return( retval );
	}

	/**
	 *	Read all PopSubDep2 instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamPopSubDep2[] readAllDerived( ICFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllDerived";
		boolean permissionGranted = canReadPopSubDep2(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopSubDep2Table.TABLE_NAME, "readpopsubdep2", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaPopSubDep2> retlist = schema.getJpaHooksSchema().getPopSubDep2Service().findAll();
		if(retlist != null) {
			ArrayList<CFBamJpaPopSubDep2> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readpopsubdep2")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamPopSubDep2[] retset = new ICFBamPopSubDep2[retlist.size()];
		int idx = 0;
		for (CFBamJpaPopSubDep2 cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived PopSubDep2 record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Id	The PopSubDep2 key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamPopSubDep2 readDerivedByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argId )
	{
		final String S_ProcName = "readDerivedByIdIdx";
		boolean permissionGranted = canReadPopSubDep2(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopSubDep2Table.TABLE_NAME, "readpopsubdep2", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFBamPopSubDep2 retval = schema.getJpaHooksSchema().getPopSubDep2Service().find(argId);
		if(retval != null) {
			// Retrieve the TenantId from retval and check ICFSec.backingSchema().isMemberOfTenantGroup(auth,ClusterId,TenantId,'readpopsubdep2'), clear retval to null if not a member
			CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
			CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readpopsubdep2")) {
				retval = null;
			}
		}
		return( retval );
	}

	/**
	 *	Read an array of the derived PopSubDep2 record instances identified by the duplicate key TenantIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TenantId	The PopSubDep2 key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamPopSubDep2[] readDerivedByTenantIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTenantId )
	{
		final String S_ProcName = "readDerivedByTenantIdx";
		boolean permissionGranted = canReadPopSubDep2(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopSubDep2Table.TABLE_NAME, "readpopsubdep2", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaPopSubDep2> retlist = schema.getJpaHooksSchema().getPopSubDep2Service().findByTenantIdx(argTenantId);
		if(retlist != null) {
			ArrayList<CFBamJpaPopSubDep2> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readpopsubdep2")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamPopSubDep2[] retset = new ICFBamPopSubDep2[retlist.size()];
		int idx = 0;
		for (CFBamJpaPopSubDep2 cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived PopSubDep2 record instances identified by the duplicate key RelationIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	RelationId	The PopSubDep2 key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamPopSubDep2[] readDerivedByRelationIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argRelationId )
	{
		final String S_ProcName = "readDerivedByRelationIdx";
		boolean permissionGranted = canReadPopSubDep2(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopSubDep2Table.TABLE_NAME, "readpopsubdep2", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaPopSubDep2> retlist = schema.getJpaHooksSchema().getPopSubDep2Service().findByRelationIdx(argRelationId);
		if(retlist != null) {
			ArrayList<CFBamJpaPopSubDep2> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readpopsubdep2")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamPopSubDep2[] retset = new ICFBamPopSubDep2[retlist.size()];
		int idx = 0;
		for (CFBamJpaPopSubDep2 cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived PopSubDep2 record instances identified by the duplicate key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DefSchemaId	The PopSubDep2 key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamPopSubDep2[] readDerivedByDefSchemaIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argDefSchemaId )
	{
		final String S_ProcName = "readDerivedByDefSchemaIdx";
		boolean permissionGranted = canReadPopSubDep2(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopSubDep2Table.TABLE_NAME, "readpopsubdep2", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaPopSubDep2> retlist = schema.getJpaHooksSchema().getPopSubDep2Service().findByDefSchemaIdx(argDefSchemaId);
		if(retlist != null) {
			ArrayList<CFBamJpaPopSubDep2> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readpopsubdep2")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamPopSubDep2[] retset = new ICFBamPopSubDep2[retlist.size()];
		int idx = 0;
		for (CFBamJpaPopSubDep2 cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived PopSubDep2 record instances identified by the duplicate key PopSubDep1Idx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PopSubDep1Id	The PopSubDep2 key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamPopSubDep2[] readDerivedByPopSubDep1Idx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argPopSubDep1Id )
	{
		final String S_ProcName = "readDerivedByPopSubDep1Idx";
		boolean permissionGranted = canReadPopSubDep2(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopSubDep2Table.TABLE_NAME, "readpopsubdep2", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaPopSubDep2> retlist = schema.getJpaHooksSchema().getPopSubDep2Service().findByPopSubDep1Idx(argPopSubDep1Id);
		if(retlist != null) {
			ArrayList<CFBamJpaPopSubDep2> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readpopsubdep2")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamPopSubDep2[] retset = new ICFBamPopSubDep2[retlist.size()];
		int idx = 0;
		for (CFBamJpaPopSubDep2 cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived PopSubDep2 record instance identified by the unique key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PopSubDep1Id	The PopSubDep2 key attribute of the instance generating the id.
	 *
	 *	@param	Name	The PopSubDep2 key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamPopSubDep2 readDerivedByUNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argPopSubDep1Id,
		String argName )
	{
		final String S_ProcName = "readDerivedByUNameIdx";
		boolean permissionGranted = canReadPopSubDep2(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopSubDep2Table.TABLE_NAME, "readpopsubdep2", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFBamPopSubDep2 retval = schema.getJpaHooksSchema().getPopSubDep2Service().findByUNameIdx(argPopSubDep1Id,
		argName);
		if(retval != null) {
			// Retrieve the TenantId from retval and check ICFSec.backingSchema().isMemberOfTenantGroup(auth,ClusterId,TenantId,'readpopsubdep2'), clear retval to null if not a member
			CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
			CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readpopsubdep2")) {
				retval = null;
			}
		}
		return( retval );
	}

	/**
	 *	Read the specific PopSubDep2 record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the PopSubDep2 instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamPopSubDep2 readRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		final String S_ProcName = "readRec";
		boolean permissionGranted = canReadPopSubDep2(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopSubDep2Table.TABLE_NAME, "readpopsubdep2", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Lock the specific PopSubDep2 record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the PopSubDep2 instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamPopSubDep2 lockRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		final String S_ProcName = "lockRec";
		boolean permissionGranted = canUpdatePopSubDep2(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopSubDep2Table.TABLE_NAME, "updatepopsubdep2", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific PopSubDep2 record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific PopSubDep2 instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFBamPopSubDep2[] readAllRec( ICFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllRec";
		boolean permissionGranted = canReadPopSubDep2(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopSubDep2Table.TABLE_NAME, "readpopsubdep2", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read the specific PopSubDep2 record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Id	The PopSubDep2 key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamPopSubDep2 readRecByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argId )
	{
		final String S_ProcName = "readRecByIdIdx";
		boolean permissionGranted = canReadPopSubDep2(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopSubDep2Table.TABLE_NAME, "readpopsubdep2", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read an array of the specific PopSubDep2 record instances identified by the duplicate key TenantIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TenantId	The PopSubDep2 key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamPopSubDep2[] readRecByTenantIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTenantId )
	{
		final String S_ProcName = "readRecByTenantIdx";
		boolean permissionGranted = canReadPopSubDep2(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopSubDep2Table.TABLE_NAME, "readpopsubdep2", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByTenantIdx");
	}

	/**
	 *	Read an array of the specific PopSubDep2 record instances identified by the duplicate key RelationIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	RelationId	The PopSubDep2 key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamPopSubDep2[] readRecByRelationIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argRelationId )
	{
		final String S_ProcName = "readRecByRelationIdx";
		boolean permissionGranted = canReadPopSubDep2(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopSubDep2Table.TABLE_NAME, "readpopsubdep2", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByRelationIdx");
	}

	/**
	 *	Read an array of the specific PopSubDep2 record instances identified by the duplicate key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DefSchemaId	The PopSubDep2 key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamPopSubDep2[] readRecByDefSchemaIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argDefSchemaId )
	{
		final String S_ProcName = "readRecByDefSchemaIdx";
		boolean permissionGranted = canReadPopSubDep2(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopSubDep2Table.TABLE_NAME, "readpopsubdep2", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByDefSchemaIdx");
	}

	/**
	 *	Read an array of the specific PopSubDep2 record instances identified by the duplicate key PopSubDep1Idx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PopSubDep1Id	The PopSubDep2 key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamPopSubDep2[] readRecByPopSubDep1Idx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argPopSubDep1Id )
	{
		final String S_ProcName = "readRecByPopSubDep1Idx";
		boolean permissionGranted = canReadPopSubDep2(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopSubDep2Table.TABLE_NAME, "readpopsubdep2", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByPopSubDep1Idx");
	}

	/**
	 *	Read the specific PopSubDep2 record instance identified by the unique key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PopSubDep1Id	The PopSubDep2 key attribute of the instance generating the id.
	 *
	 *	@param	Name	The PopSubDep2 key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamPopSubDep2 readRecByUNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argPopSubDep1Id,
		String argName )
	{
		final String S_ProcName = "readRecByUNameIdx";
		boolean permissionGranted = canReadPopSubDep2(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamPopSubDep2Table.TABLE_NAME, "readpopsubdep2", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByUNameIdx");
	}
}
