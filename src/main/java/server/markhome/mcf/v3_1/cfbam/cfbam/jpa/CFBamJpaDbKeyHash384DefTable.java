
// Description: Java 25 DbIO implementation for DbKeyHash384Def.

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
import server.markhome.mcf.v3_1.cflib.keyhash.*;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;
import server.markhome.mcf.v3_1.cfint.cfint.*;
import server.markhome.mcf.v3_1.cfbam.cfbam.*;
import server.markhome.mcf.v3_1.cfsec.cfsecobj.*;
import server.markhome.mcf.v3_1.cfint.cfintobj.*;
import server.markhome.mcf.v3_1.cfbam.cfbamobj.*;
import server.markhome.mcf.v3_1.cfbam.cfbam.jpa.CFBamJpaHooksSchema;

/*
 *	CFBamJpaDbKeyHash384DefTable database implementation for DbKeyHash384Def
 */
public class CFBamJpaDbKeyHash384DefTable implements ICFBamDbKeyHash384DefTable
{
	protected CFBamJpaSchema schema;


	public CFBamJpaDbKeyHash384DefTable(ICFBamSchema schema) {
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

	protected boolean canCreateDbKeyHash384Def(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), Authorization.getSecClusterId(), Authorization.getSecTenantId(), "createdbkeyhash384def");
		}
		return( permissionGranted );
	}

	protected boolean canReadDbKeyHash384Def(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), Authorization.getSecClusterId(), Authorization.getSecTenantId(), "readdbkeyhash384def");
		}
		return( permissionGranted );
	}

	protected boolean canUpdateDbKeyHash384Def(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), ICFSecSchema.getSysClusterId(), ICFSecSchema.getSysTenantId(), "updatedbkeyhash384def");
		}
		return( permissionGranted );
	}

	protected boolean canDeleteDbKeyHash384Def(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), ICFSecSchema.getSysClusterId(), ICFSecSchema.getSysTenantId(), "deletedbkeyhash384def");
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
	public ICFBamDbKeyHash384Def createDbKeyHash384Def( ICFSecAuthorization Authorization,
		ICFBamDbKeyHash384Def rec )
	{
		final String S_ProcName = "createDbKeyHash384Def";
		boolean permissionGranted = canCreateDbKeyHash384Def(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "createdbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createDbKeyHash384Def", 1, "rec");
		}
		else if (rec instanceof CFBamJpaDbKeyHash384Def) {
			CFBamJpaDbKeyHash384Def jparec = (CFBamJpaDbKeyHash384Def)rec;
			jparec.setCreatedAt(LocalDateTime.now());
			jparec.setUpdatedAt(jparec.getCreatedAt());
			jparec.setCreatedByUserId(Authorization.getSecUserId());
			jparec.setUpdatedByUserId(Authorization.getSecUserId());
			CFBamJpaDbKeyHash384Def retval = schema.getJpaHooksSchema().getDbKeyHash384DefService().create(jparec);
		return(retval);
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createDbKeyHash384Def", "rec", rec, "CFBamJpaDbKeyHash384Def");
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
	public ICFBamDbKeyHash384Def updateDbKeyHash384Def( ICFSecAuthorization Authorization,
		ICFBamDbKeyHash384Def rec )
	{
		final String S_ProcName = "updateDbKeyHash384Def";
		boolean permissionGranted = canUpdateDbKeyHash384Def(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "updatedbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateDbKeyHash384Def", 1, "rec");
		}
		else if (rec instanceof CFBamJpaDbKeyHash384Def) {
			CFBamJpaDbKeyHash384Def jparec = (CFBamJpaDbKeyHash384Def)rec;
			jparec.setUpdatedAt(LocalDateTime.now());
			jparec.setUpdatedByUserId(Authorization.getSecUserId());
			CFBamJpaDbKeyHash384Def retval = schema.getJpaHooksSchema().getDbKeyHash384DefService().update(jparec);
		return(retval);
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateDbKeyHash384Def", "rec", rec, "CFBamJpaDbKeyHash384Def");
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
	public void deleteDbKeyHash384Def( ICFSecAuthorization Authorization,
		ICFBamDbKeyHash384Def rec )
	{
		final String S_ProcName = "deleteDbKeyHash384Def";
		boolean permissionGranted = canDeleteDbKeyHash384Def(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletedbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		if (rec == null) {
			return;
		}
		if (rec instanceof CFBamJpaDbKeyHash384Def) {
			CFBamJpaDbKeyHash384Def jparec = (CFBamJpaDbKeyHash384Def)rec;
			schema.getJpaHooksSchema().getDbKeyHash384DefService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteDbKeyHash384Def", "rec", rec, "CFBamJpaDbKeyHash384Def");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteDbKeyHash384Def");
	}

	/**
	 *	Delete the DbKeyHash384Def instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteDbKeyHash384DefByIdIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argKey )
	{
		final String S_ProcName = "deleteDbKeyHash384DefByIdIdx";
		boolean permissionGranted = canDeleteDbKeyHash384Def(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletedbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getDbKeyHash384DefService().deleteByIdIdx(argKey);
	}

	/**
	 *	Delete the DbKeyHash384Def instances identified by the key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The DbKeyHash384Def key attribute of the instance generating the id.
	 *
	 *	@param	Name	The DbKeyHash384Def key attribute of the instance generating the id.
	 */
	@Override
	public void deleteDbKeyHash384DefByUNameIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		String argName )
	{
		final String S_ProcName = "deleteDbKeyHash384DefByUNameIdx";
		boolean permissionGranted = canDeleteDbKeyHash384Def(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletedbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getDbKeyHash384DefService().deleteByUNameIdx(argScopeId,
		argName);
	}


	/**
	 *	Delete the DbKeyHash384Def instances identified by the key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteDbKeyHash384DefByUNameIdx( ICFSecAuthorization Authorization,
		ICFBamValueByUNameIdxKey argKey )
	{
		final String S_ProcName = "deleteDbKeyHash384DefByUNameIdx";
		boolean permissionGranted = canDeleteDbKeyHash384Def(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletedbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getDbKeyHash384DefService().deleteByUNameIdx(argKey.getRequiredScopeId(),
			argKey.getRequiredName());
	}

	/**
	 *	Delete the DbKeyHash384Def instances identified by the key ScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The DbKeyHash384Def key attribute of the instance generating the id.
	 */
	@Override
	public void deleteDbKeyHash384DefByScopeIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId )
	{
		final String S_ProcName = "deleteDbKeyHash384DefByScopeIdx";
		boolean permissionGranted = canDeleteDbKeyHash384Def(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletedbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getDbKeyHash384DefService().deleteByScopeIdx(argScopeId);
	}


	/**
	 *	Delete the DbKeyHash384Def instances identified by the key ScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteDbKeyHash384DefByScopeIdx( ICFSecAuthorization Authorization,
		ICFBamValueByScopeIdxKey argKey )
	{
		final String S_ProcName = "deleteDbKeyHash384DefByScopeIdx";
		boolean permissionGranted = canDeleteDbKeyHash384Def(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletedbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getDbKeyHash384DefService().deleteByScopeIdx(argKey.getRequiredScopeId());
	}

	/**
	 *	Delete the DbKeyHash384Def instances identified by the key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DefSchemaId	The DbKeyHash384Def key attribute of the instance generating the id.
	 */
	@Override
	public void deleteDbKeyHash384DefByDefSchemaIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argDefSchemaId )
	{
		final String S_ProcName = "deleteDbKeyHash384DefByDefSchemaIdx";
		boolean permissionGranted = canDeleteDbKeyHash384Def(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletedbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getDbKeyHash384DefService().deleteByDefSchemaIdx(argDefSchemaId);
	}


	/**
	 *	Delete the DbKeyHash384Def instances identified by the key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteDbKeyHash384DefByDefSchemaIdx( ICFSecAuthorization Authorization,
		ICFBamValueByDefSchemaIdxKey argKey )
	{
		final String S_ProcName = "deleteDbKeyHash384DefByDefSchemaIdx";
		boolean permissionGranted = canDeleteDbKeyHash384Def(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletedbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getDbKeyHash384DefService().deleteByDefSchemaIdx(argKey.getOptionalDefSchemaId());
	}

	/**
	 *	Delete the DbKeyHash384Def instances identified by the key PrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PrevId	The DbKeyHash384Def key attribute of the instance generating the id.
	 */
	@Override
	public void deleteDbKeyHash384DefByPrevIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argPrevId )
	{
		final String S_ProcName = "deleteDbKeyHash384DefByPrevIdx";
		boolean permissionGranted = canDeleteDbKeyHash384Def(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletedbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getDbKeyHash384DefService().deleteByPrevIdx(argPrevId);
	}


	/**
	 *	Delete the DbKeyHash384Def instances identified by the key PrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteDbKeyHash384DefByPrevIdx( ICFSecAuthorization Authorization,
		ICFBamValueByPrevIdxKey argKey )
	{
		final String S_ProcName = "deleteDbKeyHash384DefByPrevIdx";
		boolean permissionGranted = canDeleteDbKeyHash384Def(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletedbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getDbKeyHash384DefService().deleteByPrevIdx(argKey.getOptionalPrevId());
	}

	/**
	 *	Delete the DbKeyHash384Def instances identified by the key NextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	NextId	The DbKeyHash384Def key attribute of the instance generating the id.
	 */
	@Override
	public void deleteDbKeyHash384DefByNextIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argNextId )
	{
		final String S_ProcName = "deleteDbKeyHash384DefByNextIdx";
		boolean permissionGranted = canDeleteDbKeyHash384Def(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletedbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getDbKeyHash384DefService().deleteByNextIdx(argNextId);
	}


	/**
	 *	Delete the DbKeyHash384Def instances identified by the key NextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteDbKeyHash384DefByNextIdx( ICFSecAuthorization Authorization,
		ICFBamValueByNextIdxKey argKey )
	{
		final String S_ProcName = "deleteDbKeyHash384DefByNextIdx";
		boolean permissionGranted = canDeleteDbKeyHash384Def(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletedbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getDbKeyHash384DefService().deleteByNextIdx(argKey.getOptionalNextId());
	}

	/**
	 *	Delete the DbKeyHash384Def instances identified by the key ContPrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The DbKeyHash384Def key attribute of the instance generating the id.
	 *
	 *	@param	PrevId	The DbKeyHash384Def key attribute of the instance generating the id.
	 */
	@Override
	public void deleteDbKeyHash384DefByContPrevIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		ICFLibKeyHash256 argPrevId )
	{
		final String S_ProcName = "deleteDbKeyHash384DefByContPrevIdx";
		boolean permissionGranted = canDeleteDbKeyHash384Def(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletedbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getDbKeyHash384DefService().deleteByContPrevIdx(argScopeId,
		argPrevId);
	}


	/**
	 *	Delete the DbKeyHash384Def instances identified by the key ContPrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteDbKeyHash384DefByContPrevIdx( ICFSecAuthorization Authorization,
		ICFBamValueByContPrevIdxKey argKey )
	{
		final String S_ProcName = "deleteDbKeyHash384DefByContPrevIdx";
		boolean permissionGranted = canDeleteDbKeyHash384Def(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletedbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getDbKeyHash384DefService().deleteByContPrevIdx(argKey.getRequiredScopeId(),
			argKey.getOptionalPrevId());
	}

	/**
	 *	Delete the DbKeyHash384Def instances identified by the key ContNextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The DbKeyHash384Def key attribute of the instance generating the id.
	 *
	 *	@param	NextId	The DbKeyHash384Def key attribute of the instance generating the id.
	 */
	@Override
	public void deleteDbKeyHash384DefByContNextIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		ICFLibKeyHash256 argNextId )
	{
		final String S_ProcName = "deleteDbKeyHash384DefByContNextIdx";
		boolean permissionGranted = canDeleteDbKeyHash384Def(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletedbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getDbKeyHash384DefService().deleteByContNextIdx(argScopeId,
		argNextId);
	}


	/**
	 *	Delete the DbKeyHash384Def instances identified by the key ContNextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteDbKeyHash384DefByContNextIdx( ICFSecAuthorization Authorization,
		ICFBamValueByContNextIdxKey argKey )
	{
		final String S_ProcName = "deleteDbKeyHash384DefByContNextIdx";
		boolean permissionGranted = canDeleteDbKeyHash384Def(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletedbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getDbKeyHash384DefService().deleteByContNextIdx(argKey.getRequiredScopeId(),
			argKey.getOptionalNextId());
	}


	/**
	 *	Read the derived DbKeyHash384Def record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the DbKeyHash384Def instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamDbKeyHash384Def readDerived( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 PKey )
	{
		final String S_ProcName = "readDerived";
		boolean permissionGranted = canReadDbKeyHash384Def(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readdbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFBamDbKeyHash384Def retval = schema.getJpaHooksSchema().getDbKeyHash384DefService().find(PKey);
		return(retval);
	}

	/**
	 *	Lock the derived DbKeyHash384Def record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the DbKeyHash384Def instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamDbKeyHash384Def lockDerived( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 PKey )
	{
		final String S_ProcName = "lockDerived";
		boolean permissionGranted = canUpdateDbKeyHash384Def(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "updatedbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFBamDbKeyHash384Def retval = schema.getJpaHooksSchema().getDbKeyHash384DefService().lockByIdIdx(PKey);
		return(retval);
	}

	/**
	 *	Read all DbKeyHash384Def instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamDbKeyHash384Def[] readAllDerived( ICFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllDerived";
		boolean permissionGranted = canReadDbKeyHash384Def(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readdbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaDbKeyHash384Def> retlist = schema.getJpaHooksSchema().getDbKeyHash384DefService().findAll();
		ICFBamDbKeyHash384Def[] retset = new ICFBamDbKeyHash384Def[retlist.size()];
		int idx = 0;
		for (CFBamJpaDbKeyHash384Def cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived DbKeyHash384Def record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Id	The DbKeyHash384Def key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamDbKeyHash384Def readDerivedByIdIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argId )
	{
		final String S_ProcName = "readDerivedByIdIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadDbKeyHash384Def(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readdbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		ICFBamDbKeyHash384Def retval = schema.getJpaHooksSchema().getDbKeyHash384DefService().find(argId);
		if(retval != null && !ICFSecSchema.getSystemId().equals(Authorization.getSecUserId())) {
				ICFSecTenant tenant = retval.getRequiredContainerScope().getRequiredOwnerTenant();
				ICFSecCluster cluster = tenant.getRequiredContainerCluster();
			CFLibDbKeyHash256 effClusterId = cluster.getRequiredId();
			CFLibDbKeyHash256 effTenantId = tenant.getRequiredId();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readdbkeyhash384def")) {
				retval = null;
			}
		}
		return(retval);
	}

	/**
	 *	Read the derived DbKeyHash384Def record instance identified by the unique key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The DbKeyHash384Def key attribute of the instance generating the id.
	 *
	 *	@param	Name	The DbKeyHash384Def key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamDbKeyHash384Def readDerivedByUNameIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		String argName )
	{
		final String S_ProcName = "readDerivedByUNameIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadDbKeyHash384Def(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readdbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		ICFBamDbKeyHash384Def retval = schema.getJpaHooksSchema().getDbKeyHash384DefService().findByUNameIdx(argScopeId,
		argName);
		if(retval != null && !ICFSecSchema.getSystemId().equals(Authorization.getSecUserId())) {
				ICFSecTenant tenant = retval.getRequiredContainerScope().getRequiredOwnerTenant();
				ICFSecCluster cluster = tenant.getRequiredContainerCluster();
			CFLibDbKeyHash256 effClusterId = cluster.getRequiredId();
			CFLibDbKeyHash256 effTenantId = tenant.getRequiredId();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readdbkeyhash384def")) {
				retval = null;
			}
		}
		return(retval);
	}

	/**
	 *	Read an array of the derived DbKeyHash384Def record instances identified by the duplicate key ScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The DbKeyHash384Def key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamDbKeyHash384Def[] readDerivedByScopeIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId )
	{
		final String S_ProcName = "readDerivedByScopeIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadDbKeyHash384Def(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readdbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		List<CFBamJpaDbKeyHash384Def> retlist = schema.getJpaHooksSchema().getDbKeyHash384DefService().findByScopeIdx(argScopeId);
		ICFBamDbKeyHash384Def[] retset = new ICFBamDbKeyHash384Def[retlist.size()];
		int idx = 0;
		for (CFBamJpaDbKeyHash384Def cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived DbKeyHash384Def record instances identified by the duplicate key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DefSchemaId	The DbKeyHash384Def key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamDbKeyHash384Def[] readDerivedByDefSchemaIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argDefSchemaId )
	{
		final String S_ProcName = "readDerivedByDefSchemaIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadDbKeyHash384Def(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readdbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		List<CFBamJpaDbKeyHash384Def> retlist = schema.getJpaHooksSchema().getDbKeyHash384DefService().findByDefSchemaIdx(argDefSchemaId);
		ICFBamDbKeyHash384Def[] retset = new ICFBamDbKeyHash384Def[retlist.size()];
		int idx = 0;
		for (CFBamJpaDbKeyHash384Def cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived DbKeyHash384Def record instances identified by the duplicate key PrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PrevId	The DbKeyHash384Def key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamDbKeyHash384Def[] readDerivedByPrevIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argPrevId )
	{
		final String S_ProcName = "readDerivedByPrevIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadDbKeyHash384Def(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readdbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		List<CFBamJpaDbKeyHash384Def> retlist = schema.getJpaHooksSchema().getDbKeyHash384DefService().findByPrevIdx(argPrevId);
		ICFBamDbKeyHash384Def[] retset = new ICFBamDbKeyHash384Def[retlist.size()];
		int idx = 0;
		for (CFBamJpaDbKeyHash384Def cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived DbKeyHash384Def record instances identified by the duplicate key NextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	NextId	The DbKeyHash384Def key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamDbKeyHash384Def[] readDerivedByNextIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argNextId )
	{
		final String S_ProcName = "readDerivedByNextIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadDbKeyHash384Def(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readdbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		List<CFBamJpaDbKeyHash384Def> retlist = schema.getJpaHooksSchema().getDbKeyHash384DefService().findByNextIdx(argNextId);
		ICFBamDbKeyHash384Def[] retset = new ICFBamDbKeyHash384Def[retlist.size()];
		int idx = 0;
		for (CFBamJpaDbKeyHash384Def cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived DbKeyHash384Def record instances identified by the duplicate key ContPrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The DbKeyHash384Def key attribute of the instance generating the id.
	 *
	 *	@param	PrevId	The DbKeyHash384Def key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamDbKeyHash384Def[] readDerivedByContPrevIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		ICFLibKeyHash256 argPrevId )
	{
		final String S_ProcName = "readDerivedByContPrevIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadDbKeyHash384Def(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readdbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		List<CFBamJpaDbKeyHash384Def> retlist = schema.getJpaHooksSchema().getDbKeyHash384DefService().findByContPrevIdx(argScopeId,
		argPrevId);
		ICFBamDbKeyHash384Def[] retset = new ICFBamDbKeyHash384Def[retlist.size()];
		int idx = 0;
		for (CFBamJpaDbKeyHash384Def cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived DbKeyHash384Def record instances identified by the duplicate key ContNextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The DbKeyHash384Def key attribute of the instance generating the id.
	 *
	 *	@param	NextId	The DbKeyHash384Def key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamDbKeyHash384Def[] readDerivedByContNextIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		ICFLibKeyHash256 argNextId )
	{
		final String S_ProcName = "readDerivedByContNextIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadDbKeyHash384Def(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readdbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		List<CFBamJpaDbKeyHash384Def> retlist = schema.getJpaHooksSchema().getDbKeyHash384DefService().findByContNextIdx(argScopeId,
		argNextId);
		ICFBamDbKeyHash384Def[] retset = new ICFBamDbKeyHash384Def[retlist.size()];
		int idx = 0;
		for (CFBamJpaDbKeyHash384Def cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the specific DbKeyHash384Def record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the DbKeyHash384Def instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamDbKeyHash384Def readRec( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 PKey )
	{
		final String S_ProcName = "readRec";
		boolean permissionGranted = canReadDbKeyHash384Def(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readdbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Lock the specific DbKeyHash384Def record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the DbKeyHash384Def instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamDbKeyHash384Def lockRec( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 PKey )
	{
		final String S_ProcName = "lockRec";
		boolean permissionGranted = canUpdateDbKeyHash384Def(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "updatedbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific DbKeyHash384Def record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific DbKeyHash384Def instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFBamDbKeyHash384Def[] readAllRec( ICFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllRec";
		boolean permissionGranted = canReadDbKeyHash384Def(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readdbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read the specific DbKeyHash384Def record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Id	The DbKeyHash384Def key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamDbKeyHash384Def readRecByIdIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argId )
	{
		final String S_ProcName = "readRecByIdIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadDbKeyHash384Def(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readdbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read the specific DbKeyHash384Def record instance identified by the unique key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The DbKeyHash384Def key attribute of the instance generating the id.
	 *
	 *	@param	Name	The DbKeyHash384Def key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamDbKeyHash384Def readRecByUNameIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		String argName )
	{
		final String S_ProcName = "readRecByUNameIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadDbKeyHash384Def(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readdbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByUNameIdx");
	}

	/**
	 *	Read an array of the specific DbKeyHash384Def record instances identified by the duplicate key ScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The DbKeyHash384Def key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamDbKeyHash384Def[] readRecByScopeIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId )
	{
		final String S_ProcName = "readRecByScopeIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadDbKeyHash384Def(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readdbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByScopeIdx");
	}

	/**
	 *	Read an array of the specific DbKeyHash384Def record instances identified by the duplicate key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DefSchemaId	The DbKeyHash384Def key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamDbKeyHash384Def[] readRecByDefSchemaIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argDefSchemaId )
	{
		final String S_ProcName = "readRecByDefSchemaIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadDbKeyHash384Def(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readdbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByDefSchemaIdx");
	}

	/**
	 *	Read an array of the specific DbKeyHash384Def record instances identified by the duplicate key PrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PrevId	The DbKeyHash384Def key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamDbKeyHash384Def[] readRecByPrevIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argPrevId )
	{
		final String S_ProcName = "readRecByPrevIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadDbKeyHash384Def(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readdbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByPrevIdx");
	}

	/**
	 *	Read an array of the specific DbKeyHash384Def record instances identified by the duplicate key NextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	NextId	The DbKeyHash384Def key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamDbKeyHash384Def[] readRecByNextIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argNextId )
	{
		final String S_ProcName = "readRecByNextIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadDbKeyHash384Def(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readdbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByNextIdx");
	}

	/**
	 *	Read an array of the specific DbKeyHash384Def record instances identified by the duplicate key ContPrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The DbKeyHash384Def key attribute of the instance generating the id.
	 *
	 *	@param	PrevId	The DbKeyHash384Def key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamDbKeyHash384Def[] readRecByContPrevIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		ICFLibKeyHash256 argPrevId )
	{
		final String S_ProcName = "readRecByContPrevIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadDbKeyHash384Def(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readdbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByContPrevIdx");
	}

	/**
	 *	Read an array of the specific DbKeyHash384Def record instances identified by the duplicate key ContNextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The DbKeyHash384Def key attribute of the instance generating the id.
	 *
	 *	@param	NextId	The DbKeyHash384Def key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamDbKeyHash384Def[] readRecByContNextIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		ICFLibKeyHash256 argNextId )
	{
		final String S_ProcName = "readRecByContNextIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadDbKeyHash384Def(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readdbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByContNextIdx");
	}

	/**
	 *	Move the specified record up in the chain (i.e. to the previous position.)
	 *
	 *	@return	The refreshed record after it has been moved
	 */
	@Override
	public ICFBamDbKeyHash384Def moveRecUp( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argId,
		int revision )
	{
		final String S_ProcName = "moveRecUp";
		boolean permissionGranted = canUpdateDbKeyHash384Def(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "updatedbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "moveRecUp");
	}

	/**
	 *	Move the specified record down in the chain (i.e. to the next position.)
	 *
	 *	@return	The refreshed record after it has been moved
	 */
	@Override
	public ICFBamDbKeyHash384Def moveRecDown( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argId,
		int revision )
	{
		final String S_ProcName = "moveRecDown";
		boolean permissionGranted = canUpdateDbKeyHash384Def(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "updatedbkeyhash384def", ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384DefTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "moveRecDown");
	}
}
