
// Description: Java 25 DbIO implementation for SchemaRole.

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
 *	CFBamJpaSchemaRoleTable database implementation for SchemaRole
 */
public class CFBamJpaSchemaRoleTable implements ICFBamSchemaRoleTable
{
	protected CFBamJpaSchema schema;


	public CFBamJpaSchemaRoleTable(ICFBamSchema schema) {
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

	protected boolean canCreateSchemaRole(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), Authorization.getSecClusterId(), Authorization.getSecTenantId(), "createschemarole");
		}
		return( permissionGranted );
	}

	protected boolean canReadSchemaRole(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), Authorization.getSecClusterId(), Authorization.getSecTenantId(), "readschemarole");
		}
		return( permissionGranted );
	}

	protected boolean canUpdateSchemaRole(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), ICFSecSchema.getSysClusterId(), ICFSecSchema.getSysTenantId(), "updateschemarole");
		}
		return( permissionGranted );
	}

	protected boolean canDeleteSchemaRole(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), ICFSecSchema.getSysClusterId(), ICFSecSchema.getSysTenantId(), "deleteschemarole");
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
	public ICFBamSchemaRole createSchemaRole( ICFSecAuthorization Authorization,
		ICFBamSchemaRole rec )
	{
		final String S_ProcName = "createSchemaRole";
		boolean permissionGranted = canCreateSchemaRole(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "createschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createSchemaRole", 1, "rec");
		}
		else if (rec instanceof CFBamJpaSchemaRole) {
			CFBamJpaSchemaRole jparec = (CFBamJpaSchemaRole)rec;
			jparec.setCreatedAt(LocalDateTime.now());
			jparec.setUpdatedAt(jparec.getCreatedAt());
			jparec.setCreatedByUserId(Authorization.getSecUserId());
			jparec.setUpdatedByUserId(Authorization.getSecUserId());
			CFBamJpaSchemaRole retval = schema.getJpaHooksSchema().getSchemaRoleService().create(jparec);
		return(retval);
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createSchemaRole", "rec", rec, "CFBamJpaSchemaRole");
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
	public ICFBamSchemaRole updateSchemaRole( ICFSecAuthorization Authorization,
		ICFBamSchemaRole rec )
	{
		final String S_ProcName = "updateSchemaRole";
		boolean permissionGranted = canUpdateSchemaRole(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "updateschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateSchemaRole", 1, "rec");
		}
		else if (rec instanceof CFBamJpaSchemaRole) {
			CFBamJpaSchemaRole jparec = (CFBamJpaSchemaRole)rec;
			jparec.setUpdatedAt(LocalDateTime.now());
			jparec.setUpdatedByUserId(Authorization.getSecUserId());
			CFBamJpaSchemaRole retval = schema.getJpaHooksSchema().getSchemaRoleService().update(jparec);
		return(retval);
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateSchemaRole", "rec", rec, "CFBamJpaSchemaRole");
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
	public void deleteSchemaRole( ICFSecAuthorization Authorization,
		ICFBamSchemaRole rec )
	{
		final String S_ProcName = "deleteSchemaRole";
		boolean permissionGranted = canDeleteSchemaRole(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		if (rec == null) {
			return;
		}
		if (rec instanceof CFBamJpaSchemaRole) {
			CFBamJpaSchemaRole jparec = (CFBamJpaSchemaRole)rec;
			schema.getJpaHooksSchema().getSchemaRoleService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteSchemaRole", "rec", rec, "CFBamJpaSchemaRole");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteSchemaRole");
	}

	/**
	 *	Delete the SchemaRole instances identified by the key SchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SchemaDefId	The SchemaRole key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSchemaRoleBySchemaIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argSchemaDefId )
	{
		final String S_ProcName = "deleteSchemaRoleBySchemaIdx";
		boolean permissionGranted = canDeleteSchemaRole(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getSchemaRoleService().deleteBySchemaIdx(argSchemaDefId);
	}


	/**
	 *	Delete the SchemaRole instances identified by the key SchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSchemaRoleBySchemaIdx( ICFSecAuthorization Authorization,
		ICFBamSchemaRoleBySchemaIdxKey argKey )
	{
		final String S_ProcName = "deleteSchemaRoleBySchemaIdx";
		boolean permissionGranted = canDeleteSchemaRole(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getSchemaRoleService().deleteBySchemaIdx(argKey.getRequiredSchemaDefId());
	}

	/**
	 *	Delete the SchemaRole instances identified by the key RoleScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	RoleScope	The SchemaRole key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSchemaRoleByRoleScopeIdx( ICFSecAuthorization Authorization,
		ICFBamPubSchema.RoleScopeEnum argRoleScope )
	{
		final String S_ProcName = "deleteSchemaRoleByRoleScopeIdx";
		boolean permissionGranted = canDeleteSchemaRole(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getSchemaRoleService().deleteByRoleScopeIdx(argRoleScope);
	}


	/**
	 *	Delete the SchemaRole instances identified by the key RoleScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSchemaRoleByRoleScopeIdx( ICFSecAuthorization Authorization,
		ICFBamSchemaRoleByRoleScopeIdxKey argKey )
	{
		final String S_ProcName = "deleteSchemaRoleByRoleScopeIdx";
		boolean permissionGranted = canDeleteSchemaRole(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getSchemaRoleService().deleteByRoleScopeIdx(argKey.getRequiredRoleScope());
	}

	/**
	 *	Delete the SchemaRole instances identified by the key SchRoleScpIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SchemaDefId	The SchemaRole key attribute of the instance generating the id.
	 *
	 *	@param	RoleScope	The SchemaRole key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSchemaRoleBySchRoleScpIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argSchemaDefId,
		ICFBamPubSchema.RoleScopeEnum argRoleScope )
	{
		final String S_ProcName = "deleteSchemaRoleBySchRoleScpIdx";
		boolean permissionGranted = canDeleteSchemaRole(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getSchemaRoleService().deleteBySchRoleScpIdx(argSchemaDefId,
		argRoleScope);
	}


	/**
	 *	Delete the SchemaRole instances identified by the key SchRoleScpIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSchemaRoleBySchRoleScpIdx( ICFSecAuthorization Authorization,
		ICFBamSchemaRoleBySchRoleScpIdxKey argKey )
	{
		final String S_ProcName = "deleteSchemaRoleBySchRoleScpIdx";
		boolean permissionGranted = canDeleteSchemaRole(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getSchemaRoleService().deleteBySchRoleScpIdx(argKey.getRequiredSchemaDefId(),
			argKey.getRequiredRoleScope());
	}

	/**
	 *	Delete the SchemaRole instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteSchemaRoleByIdIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argKey )
	{
		final String S_ProcName = "deleteSchemaRoleByIdIdx";
		boolean permissionGranted = canDeleteSchemaRole(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getSchemaRoleService().deleteByIdIdx(argKey);
	}

	/**
	 *	Delete the SchemaRole instances identified by the key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The SchemaRole key attribute of the instance generating the id.
	 *
	 *	@param	Name	The SchemaRole key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSchemaRoleByUNameIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		String argName )
	{
		final String S_ProcName = "deleteSchemaRoleByUNameIdx";
		boolean permissionGranted = canDeleteSchemaRole(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getSchemaRoleService().deleteByUNameIdx(argScopeId,
		argName);
	}


	/**
	 *	Delete the SchemaRole instances identified by the key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSchemaRoleByUNameIdx( ICFSecAuthorization Authorization,
		ICFBamRoleDefByUNameIdxKey argKey )
	{
		final String S_ProcName = "deleteSchemaRoleByUNameIdx";
		boolean permissionGranted = canDeleteSchemaRole(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getSchemaRoleService().deleteByUNameIdx(argKey.getRequiredScopeId(),
			argKey.getRequiredName());
	}

	/**
	 *	Delete the SchemaRole instances identified by the key ScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The SchemaRole key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSchemaRoleByScopeIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId )
	{
		final String S_ProcName = "deleteSchemaRoleByScopeIdx";
		boolean permissionGranted = canDeleteSchemaRole(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getSchemaRoleService().deleteByScopeIdx(argScopeId);
	}


	/**
	 *	Delete the SchemaRole instances identified by the key ScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSchemaRoleByScopeIdx( ICFSecAuthorization Authorization,
		ICFBamRoleDefByScopeIdxKey argKey )
	{
		final String S_ProcName = "deleteSchemaRoleByScopeIdx";
		boolean permissionGranted = canDeleteSchemaRole(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getSchemaRoleService().deleteByScopeIdx(argKey.getRequiredScopeId());
	}

	/**
	 *	Delete the SchemaRole instances identified by the key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DefSchemaId	The SchemaRole key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSchemaRoleByDefSchemaIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argDefSchemaId )
	{
		final String S_ProcName = "deleteSchemaRoleByDefSchemaIdx";
		boolean permissionGranted = canDeleteSchemaRole(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getSchemaRoleService().deleteByDefSchemaIdx(argDefSchemaId);
	}


	/**
	 *	Delete the SchemaRole instances identified by the key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSchemaRoleByDefSchemaIdx( ICFSecAuthorization Authorization,
		ICFBamRoleDefByDefSchemaIdxKey argKey )
	{
		final String S_ProcName = "deleteSchemaRoleByDefSchemaIdx";
		boolean permissionGranted = canDeleteSchemaRole(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getSchemaRoleService().deleteByDefSchemaIdx(argKey.getOptionalDefSchemaId());
	}

	/**
	 *	Delete the SchemaRole instances identified by the key UDefIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The SchemaRole key attribute of the instance generating the id.
	 *
	 *	@param	DefSchemaId	The SchemaRole key attribute of the instance generating the id.
	 *
	 *	@param	Name	The SchemaRole key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSchemaRoleByUDefIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		ICFLibKeyHash256 argDefSchemaId,
		String argName )
	{
		final String S_ProcName = "deleteSchemaRoleByUDefIdx";
		boolean permissionGranted = canDeleteSchemaRole(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getSchemaRoleService().deleteByUDefIdx(argScopeId,
		argDefSchemaId,
		argName);
	}


	/**
	 *	Delete the SchemaRole instances identified by the key UDefIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSchemaRoleByUDefIdx( ICFSecAuthorization Authorization,
		ICFBamRoleDefByUDefIdxKey argKey )
	{
		final String S_ProcName = "deleteSchemaRoleByUDefIdx";
		boolean permissionGranted = canDeleteSchemaRole(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getSchemaRoleService().deleteByUDefIdx(argKey.getRequiredScopeId(),
			argKey.getOptionalDefSchemaId(),
			argKey.getRequiredName());
	}


	/**
	 *	Read the derived SchemaRole record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SchemaRole instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamSchemaRole readDerived( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 PKey )
	{
		final String S_ProcName = "readDerived";
		boolean permissionGranted = canReadSchemaRole(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFBamSchemaRole retval = schema.getJpaHooksSchema().getSchemaRoleService().find(PKey);
		return(retval);
	}

	/**
	 *	Lock the derived SchemaRole record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SchemaRole instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamSchemaRole lockDerived( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 PKey )
	{
		final String S_ProcName = "lockDerived";
		boolean permissionGranted = canUpdateSchemaRole(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "updateschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFBamSchemaRole retval = schema.getJpaHooksSchema().getSchemaRoleService().lockByIdIdx(PKey);
		return(retval);
	}

	/**
	 *	Read all SchemaRole instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamSchemaRole[] readAllDerived( ICFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllDerived";
		boolean permissionGranted = canReadSchemaRole(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaSchemaRole> retlist = schema.getJpaHooksSchema().getSchemaRoleService().findAll();
		ICFBamSchemaRole[] retset = new ICFBamSchemaRole[retlist.size()];
		int idx = 0;
		for (CFBamJpaSchemaRole cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived SchemaRole record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Id	The SchemaRole key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamSchemaRole readDerivedByIdIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argId )
	{
		final String S_ProcName = "readDerivedByIdIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadSchemaRole(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		ICFBamSchemaRole retval = schema.getJpaHooksSchema().getSchemaRoleService().find(argId);
		if(retval != null && !ICFSecSchema.getSystemId().equals(Authorization.getSecUserId())) {
				ICFSecTenant tenant = retval.getRequiredContainerSchemaDef().getRequiredOwnerCTenant();
				ICFSecCluster cluster = tenant.getRequiredContainerCluster();
			CFLibDbKeyHash256 effClusterId = cluster.getRequiredId();
			CFLibDbKeyHash256 effTenantId = tenant.getRequiredId();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readschemarole")) {
				retval = null;
			}
		}
		return(retval);
	}

	/**
	 *	Read the derived SchemaRole record instance identified by the unique key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The SchemaRole key attribute of the instance generating the id.
	 *
	 *	@param	Name	The SchemaRole key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamSchemaRole readDerivedByUNameIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		String argName )
	{
		final String S_ProcName = "readDerivedByUNameIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadSchemaRole(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		ICFBamSchemaRole retval = schema.getJpaHooksSchema().getSchemaRoleService().findByUNameIdx(argScopeId,
		argName);
		if(retval != null && !ICFSecSchema.getSystemId().equals(Authorization.getSecUserId())) {
				ICFSecTenant tenant = retval.getRequiredContainerSchemaDef().getRequiredOwnerCTenant();
				ICFSecCluster cluster = tenant.getRequiredContainerCluster();
			CFLibDbKeyHash256 effClusterId = cluster.getRequiredId();
			CFLibDbKeyHash256 effTenantId = tenant.getRequiredId();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readschemarole")) {
				retval = null;
			}
		}
		return(retval);
	}

	/**
	 *	Read an array of the derived SchemaRole record instances identified by the duplicate key ScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The SchemaRole key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamSchemaRole[] readDerivedByScopeIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId )
	{
		final String S_ProcName = "readDerivedByScopeIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadSchemaRole(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		List<CFBamJpaSchemaRole> retlist = schema.getJpaHooksSchema().getSchemaRoleService().findByScopeIdx(argScopeId);
		ICFBamSchemaRole[] retset = new ICFBamSchemaRole[retlist.size()];
		int idx = 0;
		for (CFBamJpaSchemaRole cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived SchemaRole record instances identified by the duplicate key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DefSchemaId	The SchemaRole key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamSchemaRole[] readDerivedByDefSchemaIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argDefSchemaId )
	{
		final String S_ProcName = "readDerivedByDefSchemaIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadSchemaRole(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		List<CFBamJpaSchemaRole> retlist = schema.getJpaHooksSchema().getSchemaRoleService().findByDefSchemaIdx(argDefSchemaId);
		ICFBamSchemaRole[] retset = new ICFBamSchemaRole[retlist.size()];
		int idx = 0;
		for (CFBamJpaSchemaRole cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived SchemaRole record instance identified by the unique key UDefIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The SchemaRole key attribute of the instance generating the id.
	 *
	 *	@param	DefSchemaId	The SchemaRole key attribute of the instance generating the id.
	 *
	 *	@param	Name	The SchemaRole key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamSchemaRole readDerivedByUDefIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		ICFLibKeyHash256 argDefSchemaId,
		String argName )
	{
		final String S_ProcName = "readDerivedByUDefIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadSchemaRole(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		ICFBamSchemaRole retval = schema.getJpaHooksSchema().getSchemaRoleService().findByUDefIdx(argScopeId,
		argDefSchemaId,
		argName);
		if(retval != null && !ICFSecSchema.getSystemId().equals(Authorization.getSecUserId())) {
				ICFSecTenant tenant = retval.getRequiredContainerSchemaDef().getRequiredOwnerCTenant();
				ICFSecCluster cluster = tenant.getRequiredContainerCluster();
			CFLibDbKeyHash256 effClusterId = cluster.getRequiredId();
			CFLibDbKeyHash256 effTenantId = tenant.getRequiredId();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readschemarole")) {
				retval = null;
			}
		}
		return(retval);
	}

	/**
	 *	Read an array of the derived SchemaRole record instances identified by the duplicate key SchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SchemaDefId	The SchemaRole key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamSchemaRole[] readDerivedBySchemaIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argSchemaDefId )
	{
		final String S_ProcName = "readDerivedBySchemaIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadSchemaRole(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		List<CFBamJpaSchemaRole> retlist = schema.getJpaHooksSchema().getSchemaRoleService().findBySchemaIdx(argSchemaDefId);
		ICFBamSchemaRole[] retset = new ICFBamSchemaRole[retlist.size()];
		int idx = 0;
		for (CFBamJpaSchemaRole cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived SchemaRole record instances identified by the duplicate key RoleScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	RoleScope	The SchemaRole key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamSchemaRole[] readDerivedByRoleScopeIdx( ICFSecAuthorization Authorization,
		ICFBamPubSchema.RoleScopeEnum argRoleScope )
	{
		final String S_ProcName = "readDerivedByRoleScopeIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadSchemaRole(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		List<CFBamJpaSchemaRole> retlist = schema.getJpaHooksSchema().getSchemaRoleService().findByRoleScopeIdx(argRoleScope);
		ICFBamSchemaRole[] retset = new ICFBamSchemaRole[retlist.size()];
		int idx = 0;
		for (CFBamJpaSchemaRole cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived SchemaRole record instances identified by the duplicate key SchRoleScpIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SchemaDefId	The SchemaRole key attribute of the instance generating the id.
	 *
	 *	@param	RoleScope	The SchemaRole key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamSchemaRole[] readDerivedBySchRoleScpIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argSchemaDefId,
		ICFBamPubSchema.RoleScopeEnum argRoleScope )
	{
		final String S_ProcName = "readDerivedBySchRoleScpIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadSchemaRole(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		List<CFBamJpaSchemaRole> retlist = schema.getJpaHooksSchema().getSchemaRoleService().findBySchRoleScpIdx(argSchemaDefId,
		argRoleScope);
		ICFBamSchemaRole[] retset = new ICFBamSchemaRole[retlist.size()];
		int idx = 0;
		for (CFBamJpaSchemaRole cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the specific SchemaRole record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SchemaRole instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamSchemaRole readRec( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 PKey )
	{
		final String S_ProcName = "readRec";
		boolean permissionGranted = canReadSchemaRole(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Lock the specific SchemaRole record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SchemaRole instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamSchemaRole lockRec( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 PKey )
	{
		final String S_ProcName = "lockRec";
		boolean permissionGranted = canUpdateSchemaRole(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "updateschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific SchemaRole record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific SchemaRole instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFBamSchemaRole[] readAllRec( ICFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllRec";
		boolean permissionGranted = canReadSchemaRole(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read the specific SchemaRole record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Id	The SchemaRole key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamSchemaRole readRecByIdIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argId )
	{
		final String S_ProcName = "readRecByIdIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadSchemaRole(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read the specific SchemaRole record instance identified by the unique key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The SchemaRole key attribute of the instance generating the id.
	 *
	 *	@param	Name	The SchemaRole key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamSchemaRole readRecByUNameIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		String argName )
	{
		final String S_ProcName = "readRecByUNameIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadSchemaRole(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByUNameIdx");
	}

	/**
	 *	Read an array of the specific SchemaRole record instances identified by the duplicate key ScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The SchemaRole key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamSchemaRole[] readRecByScopeIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId )
	{
		final String S_ProcName = "readRecByScopeIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadSchemaRole(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByScopeIdx");
	}

	/**
	 *	Read an array of the specific SchemaRole record instances identified by the duplicate key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DefSchemaId	The SchemaRole key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamSchemaRole[] readRecByDefSchemaIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argDefSchemaId )
	{
		final String S_ProcName = "readRecByDefSchemaIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadSchemaRole(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByDefSchemaIdx");
	}

	/**
	 *	Read the specific SchemaRole record instance identified by the unique key UDefIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The SchemaRole key attribute of the instance generating the id.
	 *
	 *	@param	DefSchemaId	The SchemaRole key attribute of the instance generating the id.
	 *
	 *	@param	Name	The SchemaRole key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamSchemaRole readRecByUDefIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		ICFLibKeyHash256 argDefSchemaId,
		String argName )
	{
		final String S_ProcName = "readRecByUDefIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadSchemaRole(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByUDefIdx");
	}

	/**
	 *	Read an array of the specific SchemaRole record instances identified by the duplicate key SchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SchemaDefId	The SchemaRole key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamSchemaRole[] readRecBySchemaIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argSchemaDefId )
	{
		final String S_ProcName = "readRecBySchemaIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadSchemaRole(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecBySchemaIdx");
	}

	/**
	 *	Read an array of the specific SchemaRole record instances identified by the duplicate key RoleScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	RoleScope	The SchemaRole key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamSchemaRole[] readRecByRoleScopeIdx( ICFSecAuthorization Authorization,
		ICFBamPubSchema.RoleScopeEnum argRoleScope )
	{
		final String S_ProcName = "readRecByRoleScopeIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadSchemaRole(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByRoleScopeIdx");
	}

	/**
	 *	Read an array of the specific SchemaRole record instances identified by the duplicate key SchRoleScpIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SchemaDefId	The SchemaRole key attribute of the instance generating the id.
	 *
	 *	@param	RoleScope	The SchemaRole key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamSchemaRole[] readRecBySchRoleScpIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argSchemaDefId,
		ICFBamPubSchema.RoleScopeEnum argRoleScope )
	{
		final String S_ProcName = "readRecBySchRoleScpIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadSchemaRole(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readschemarole", ICFBamSchema.SCHEMA_NAME, ICFBamSchemaRoleTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecBySchRoleScpIdx");
	}
}
