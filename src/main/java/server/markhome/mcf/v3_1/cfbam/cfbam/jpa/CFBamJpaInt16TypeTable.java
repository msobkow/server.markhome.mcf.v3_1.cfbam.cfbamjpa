
// Description: Java 25 DbIO implementation for Int16Type.

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
 *	CFBamJpaInt16TypeTable database implementation for Int16Type
 */
public class CFBamJpaInt16TypeTable implements ICFBamInt16TypeTable
{
	protected CFBamJpaSchema schema;


	public CFBamJpaInt16TypeTable(ICFBamSchema schema) {
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

	protected boolean canCreateInt16Type(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), Authorization.getSecClusterId(), Authorization.getSecTenantId(), "createint16type");
		}
		return( permissionGranted );
	}

	protected boolean canReadInt16Type(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), Authorization.getSecClusterId(), Authorization.getSecTenantId(), "readint16type");
		}
		return( permissionGranted );
	}

	protected boolean canUpdateInt16Type(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), ICFSecSchema.getSysClusterId(), ICFSecSchema.getSysTenantId(), "updateint16type");
		}
		return( permissionGranted );
	}

	protected boolean canDeleteInt16Type(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), ICFSecSchema.getSysClusterId(), ICFSecSchema.getSysTenantId(), "deleteint16type");
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
	public ICFBamInt16Type createInt16Type( ICFSecAuthorization Authorization,
		ICFBamInt16Type rec )
	{
		final String S_ProcName = "createInt16Type";
		boolean permissionGranted = canCreateInt16Type(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "createint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createInt16Type", 1, "rec");
		}
		else if (rec instanceof CFBamJpaInt16Type) {
			CFBamJpaInt16Type jparec = (CFBamJpaInt16Type)rec;
			jparec.setCreatedAt(LocalDateTime.now());
			jparec.setUpdatedAt(jparec.getCreatedAt());
			jparec.setCreatedByUserId(Authorization.getSecUserId());
			jparec.setUpdatedByUserId(Authorization.getSecUserId());
			CFBamJpaInt16Type retval = schema.getJpaHooksSchema().getInt16TypeService().create(jparec);
		return(retval);
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createInt16Type", "rec", rec, "CFBamJpaInt16Type");
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
	public ICFBamInt16Type updateInt16Type( ICFSecAuthorization Authorization,
		ICFBamInt16Type rec )
	{
		final String S_ProcName = "updateInt16Type";
		boolean permissionGranted = canUpdateInt16Type(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "updateint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateInt16Type", 1, "rec");
		}
		else if (rec instanceof CFBamJpaInt16Type) {
			CFBamJpaInt16Type jparec = (CFBamJpaInt16Type)rec;
			jparec.setUpdatedAt(LocalDateTime.now());
			jparec.setUpdatedByUserId(Authorization.getSecUserId());
			CFBamJpaInt16Type retval = schema.getJpaHooksSchema().getInt16TypeService().update(jparec);
		return(retval);
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateInt16Type", "rec", rec, "CFBamJpaInt16Type");
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
	public void deleteInt16Type( ICFSecAuthorization Authorization,
		ICFBamInt16Type rec )
	{
		final String S_ProcName = "deleteInt16Type";
		boolean permissionGranted = canDeleteInt16Type(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		if (rec == null) {
			return;
		}
		if (rec instanceof CFBamJpaInt16Type) {
			CFBamJpaInt16Type jparec = (CFBamJpaInt16Type)rec;
			schema.getJpaHooksSchema().getInt16TypeService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteInt16Type", "rec", rec, "CFBamJpaInt16Type");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteInt16Type");
	}

	/**
	 *	Delete the Int16Type instances identified by the key SchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SchemaDefId	The Int16Type key attribute of the instance generating the id.
	 */
	@Override
	public void deleteInt16TypeBySchemaIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argSchemaDefId )
	{
		final String S_ProcName = "deleteInt16TypeBySchemaIdx";
		boolean permissionGranted = canDeleteInt16Type(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getInt16TypeService().deleteBySchemaIdx(argSchemaDefId);
	}


	/**
	 *	Delete the Int16Type instances identified by the key SchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteInt16TypeBySchemaIdx( ICFSecAuthorization Authorization,
		ICFBamInt16TypeBySchemaIdxKey argKey )
	{
		final String S_ProcName = "deleteInt16TypeBySchemaIdx";
		boolean permissionGranted = canDeleteInt16Type(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getInt16TypeService().deleteBySchemaIdx(argKey.getRequiredSchemaDefId());
	}

	/**
	 *	Delete the Int16Type instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteInt16TypeByIdIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argKey )
	{
		final String S_ProcName = "deleteInt16TypeByIdIdx";
		boolean permissionGranted = canDeleteInt16Type(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getInt16TypeService().deleteByIdIdx(argKey);
	}

	/**
	 *	Delete the Int16Type instances identified by the key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The Int16Type key attribute of the instance generating the id.
	 *
	 *	@param	Name	The Int16Type key attribute of the instance generating the id.
	 */
	@Override
	public void deleteInt16TypeByUNameIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		String argName )
	{
		final String S_ProcName = "deleteInt16TypeByUNameIdx";
		boolean permissionGranted = canDeleteInt16Type(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getInt16TypeService().deleteByUNameIdx(argScopeId,
		argName);
	}


	/**
	 *	Delete the Int16Type instances identified by the key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteInt16TypeByUNameIdx( ICFSecAuthorization Authorization,
		ICFBamValueByUNameIdxKey argKey )
	{
		final String S_ProcName = "deleteInt16TypeByUNameIdx";
		boolean permissionGranted = canDeleteInt16Type(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getInt16TypeService().deleteByUNameIdx(argKey.getRequiredScopeId(),
			argKey.getRequiredName());
	}

	/**
	 *	Delete the Int16Type instances identified by the key ScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The Int16Type key attribute of the instance generating the id.
	 */
	@Override
	public void deleteInt16TypeByScopeIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId )
	{
		final String S_ProcName = "deleteInt16TypeByScopeIdx";
		boolean permissionGranted = canDeleteInt16Type(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getInt16TypeService().deleteByScopeIdx(argScopeId);
	}


	/**
	 *	Delete the Int16Type instances identified by the key ScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteInt16TypeByScopeIdx( ICFSecAuthorization Authorization,
		ICFBamValueByScopeIdxKey argKey )
	{
		final String S_ProcName = "deleteInt16TypeByScopeIdx";
		boolean permissionGranted = canDeleteInt16Type(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getInt16TypeService().deleteByScopeIdx(argKey.getRequiredScopeId());
	}

	/**
	 *	Delete the Int16Type instances identified by the key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DefSchemaId	The Int16Type key attribute of the instance generating the id.
	 */
	@Override
	public void deleteInt16TypeByDefSchemaIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argDefSchemaId )
	{
		final String S_ProcName = "deleteInt16TypeByDefSchemaIdx";
		boolean permissionGranted = canDeleteInt16Type(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getInt16TypeService().deleteByDefSchemaIdx(argDefSchemaId);
	}


	/**
	 *	Delete the Int16Type instances identified by the key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteInt16TypeByDefSchemaIdx( ICFSecAuthorization Authorization,
		ICFBamValueByDefSchemaIdxKey argKey )
	{
		final String S_ProcName = "deleteInt16TypeByDefSchemaIdx";
		boolean permissionGranted = canDeleteInt16Type(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getInt16TypeService().deleteByDefSchemaIdx(argKey.getOptionalDefSchemaId());
	}

	/**
	 *	Delete the Int16Type instances identified by the key PrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PrevId	The Int16Type key attribute of the instance generating the id.
	 */
	@Override
	public void deleteInt16TypeByPrevIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argPrevId )
	{
		final String S_ProcName = "deleteInt16TypeByPrevIdx";
		boolean permissionGranted = canDeleteInt16Type(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getInt16TypeService().deleteByPrevIdx(argPrevId);
	}


	/**
	 *	Delete the Int16Type instances identified by the key PrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteInt16TypeByPrevIdx( ICFSecAuthorization Authorization,
		ICFBamValueByPrevIdxKey argKey )
	{
		final String S_ProcName = "deleteInt16TypeByPrevIdx";
		boolean permissionGranted = canDeleteInt16Type(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getInt16TypeService().deleteByPrevIdx(argKey.getOptionalPrevId());
	}

	/**
	 *	Delete the Int16Type instances identified by the key NextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	NextId	The Int16Type key attribute of the instance generating the id.
	 */
	@Override
	public void deleteInt16TypeByNextIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argNextId )
	{
		final String S_ProcName = "deleteInt16TypeByNextIdx";
		boolean permissionGranted = canDeleteInt16Type(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getInt16TypeService().deleteByNextIdx(argNextId);
	}


	/**
	 *	Delete the Int16Type instances identified by the key NextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteInt16TypeByNextIdx( ICFSecAuthorization Authorization,
		ICFBamValueByNextIdxKey argKey )
	{
		final String S_ProcName = "deleteInt16TypeByNextIdx";
		boolean permissionGranted = canDeleteInt16Type(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getInt16TypeService().deleteByNextIdx(argKey.getOptionalNextId());
	}

	/**
	 *	Delete the Int16Type instances identified by the key ContPrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The Int16Type key attribute of the instance generating the id.
	 *
	 *	@param	PrevId	The Int16Type key attribute of the instance generating the id.
	 */
	@Override
	public void deleteInt16TypeByContPrevIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		ICFLibKeyHash256 argPrevId )
	{
		final String S_ProcName = "deleteInt16TypeByContPrevIdx";
		boolean permissionGranted = canDeleteInt16Type(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getInt16TypeService().deleteByContPrevIdx(argScopeId,
		argPrevId);
	}


	/**
	 *	Delete the Int16Type instances identified by the key ContPrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteInt16TypeByContPrevIdx( ICFSecAuthorization Authorization,
		ICFBamValueByContPrevIdxKey argKey )
	{
		final String S_ProcName = "deleteInt16TypeByContPrevIdx";
		boolean permissionGranted = canDeleteInt16Type(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getInt16TypeService().deleteByContPrevIdx(argKey.getRequiredScopeId(),
			argKey.getOptionalPrevId());
	}

	/**
	 *	Delete the Int16Type instances identified by the key ContNextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The Int16Type key attribute of the instance generating the id.
	 *
	 *	@param	NextId	The Int16Type key attribute of the instance generating the id.
	 */
	@Override
	public void deleteInt16TypeByContNextIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		ICFLibKeyHash256 argNextId )
	{
		final String S_ProcName = "deleteInt16TypeByContNextIdx";
		boolean permissionGranted = canDeleteInt16Type(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getInt16TypeService().deleteByContNextIdx(argScopeId,
		argNextId);
	}


	/**
	 *	Delete the Int16Type instances identified by the key ContNextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteInt16TypeByContNextIdx( ICFSecAuthorization Authorization,
		ICFBamValueByContNextIdxKey argKey )
	{
		final String S_ProcName = "deleteInt16TypeByContNextIdx";
		boolean permissionGranted = canDeleteInt16Type(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deleteint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getInt16TypeService().deleteByContNextIdx(argKey.getRequiredScopeId(),
			argKey.getOptionalNextId());
	}


	/**
	 *	Read the derived Int16Type record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the Int16Type instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamInt16Type readDerived( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 PKey )
	{
		final String S_ProcName = "readDerived";
		boolean permissionGranted = canReadInt16Type(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFBamInt16Type retval = schema.getJpaHooksSchema().getInt16TypeService().find(PKey);
		return(retval);
	}

	/**
	 *	Lock the derived Int16Type record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the Int16Type instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamInt16Type lockDerived( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 PKey )
	{
		final String S_ProcName = "lockDerived";
		boolean permissionGranted = canUpdateInt16Type(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "updateint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFBamInt16Type retval = schema.getJpaHooksSchema().getInt16TypeService().lockByIdIdx(PKey);
		return(retval);
	}

	/**
	 *	Read all Int16Type instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamInt16Type[] readAllDerived( ICFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllDerived";
		boolean permissionGranted = canReadInt16Type(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaInt16Type> retlist = schema.getJpaHooksSchema().getInt16TypeService().findAll();
		ICFBamInt16Type[] retset = new ICFBamInt16Type[retlist.size()];
		int idx = 0;
		for (CFBamJpaInt16Type cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived Int16Type record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Id	The Int16Type key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamInt16Type readDerivedByIdIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argId )
	{
		final String S_ProcName = "readDerivedByIdIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadInt16Type(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		ICFBamInt16Type retval = schema.getJpaHooksSchema().getInt16TypeService().find(argId);
		if(retval != null && !ICFSecSchema.getSystemId().equals(Authorization.getSecUserId())) {
				ICFSecTenant tenant = retval.getRequiredContainerSchemaDef().getRequiredOwnerCTenant();
				ICFSecCluster cluster = tenant.getRequiredContainerCluster();
			CFLibDbKeyHash256 effClusterId = cluster.getRequiredId();
			CFLibDbKeyHash256 effTenantId = tenant.getRequiredId();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readint16type")) {
				retval = null;
			}
		}
		return(retval);
	}

	/**
	 *	Read the derived Int16Type record instance identified by the unique key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The Int16Type key attribute of the instance generating the id.
	 *
	 *	@param	Name	The Int16Type key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamInt16Type readDerivedByUNameIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		String argName )
	{
		final String S_ProcName = "readDerivedByUNameIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadInt16Type(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		ICFBamInt16Type retval = schema.getJpaHooksSchema().getInt16TypeService().findByUNameIdx(argScopeId,
		argName);
		if(retval != null && !ICFSecSchema.getSystemId().equals(Authorization.getSecUserId())) {
				ICFSecTenant tenant = retval.getRequiredContainerSchemaDef().getRequiredOwnerCTenant();
				ICFSecCluster cluster = tenant.getRequiredContainerCluster();
			CFLibDbKeyHash256 effClusterId = cluster.getRequiredId();
			CFLibDbKeyHash256 effTenantId = tenant.getRequiredId();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readint16type")) {
				retval = null;
			}
		}
		return(retval);
	}

	/**
	 *	Read an array of the derived Int16Type record instances identified by the duplicate key ScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The Int16Type key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamInt16Type[] readDerivedByScopeIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId )
	{
		final String S_ProcName = "readDerivedByScopeIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadInt16Type(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		List<CFBamJpaInt16Type> retlist = schema.getJpaHooksSchema().getInt16TypeService().findByScopeIdx(argScopeId);
		ICFBamInt16Type[] retset = new ICFBamInt16Type[retlist.size()];
		int idx = 0;
		for (CFBamJpaInt16Type cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived Int16Type record instances identified by the duplicate key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DefSchemaId	The Int16Type key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamInt16Type[] readDerivedByDefSchemaIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argDefSchemaId )
	{
		final String S_ProcName = "readDerivedByDefSchemaIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadInt16Type(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		List<CFBamJpaInt16Type> retlist = schema.getJpaHooksSchema().getInt16TypeService().findByDefSchemaIdx(argDefSchemaId);
		ICFBamInt16Type[] retset = new ICFBamInt16Type[retlist.size()];
		int idx = 0;
		for (CFBamJpaInt16Type cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived Int16Type record instances identified by the duplicate key PrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PrevId	The Int16Type key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamInt16Type[] readDerivedByPrevIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argPrevId )
	{
		final String S_ProcName = "readDerivedByPrevIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadInt16Type(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		List<CFBamJpaInt16Type> retlist = schema.getJpaHooksSchema().getInt16TypeService().findByPrevIdx(argPrevId);
		ICFBamInt16Type[] retset = new ICFBamInt16Type[retlist.size()];
		int idx = 0;
		for (CFBamJpaInt16Type cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived Int16Type record instances identified by the duplicate key NextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	NextId	The Int16Type key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamInt16Type[] readDerivedByNextIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argNextId )
	{
		final String S_ProcName = "readDerivedByNextIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadInt16Type(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		List<CFBamJpaInt16Type> retlist = schema.getJpaHooksSchema().getInt16TypeService().findByNextIdx(argNextId);
		ICFBamInt16Type[] retset = new ICFBamInt16Type[retlist.size()];
		int idx = 0;
		for (CFBamJpaInt16Type cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived Int16Type record instances identified by the duplicate key ContPrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The Int16Type key attribute of the instance generating the id.
	 *
	 *	@param	PrevId	The Int16Type key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamInt16Type[] readDerivedByContPrevIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		ICFLibKeyHash256 argPrevId )
	{
		final String S_ProcName = "readDerivedByContPrevIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadInt16Type(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		List<CFBamJpaInt16Type> retlist = schema.getJpaHooksSchema().getInt16TypeService().findByContPrevIdx(argScopeId,
		argPrevId);
		ICFBamInt16Type[] retset = new ICFBamInt16Type[retlist.size()];
		int idx = 0;
		for (CFBamJpaInt16Type cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived Int16Type record instances identified by the duplicate key ContNextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The Int16Type key attribute of the instance generating the id.
	 *
	 *	@param	NextId	The Int16Type key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamInt16Type[] readDerivedByContNextIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		ICFLibKeyHash256 argNextId )
	{
		final String S_ProcName = "readDerivedByContNextIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadInt16Type(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		List<CFBamJpaInt16Type> retlist = schema.getJpaHooksSchema().getInt16TypeService().findByContNextIdx(argScopeId,
		argNextId);
		ICFBamInt16Type[] retset = new ICFBamInt16Type[retlist.size()];
		int idx = 0;
		for (CFBamJpaInt16Type cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived Int16Type record instances identified by the duplicate key SchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SchemaDefId	The Int16Type key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamInt16Type[] readDerivedBySchemaIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argSchemaDefId )
	{
		final String S_ProcName = "readDerivedBySchemaIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadInt16Type(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		List<CFBamJpaInt16Type> retlist = schema.getJpaHooksSchema().getInt16TypeService().findBySchemaIdx(argSchemaDefId);
		ICFBamInt16Type[] retset = new ICFBamInt16Type[retlist.size()];
		int idx = 0;
		for (CFBamJpaInt16Type cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the specific Int16Type record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the Int16Type instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamInt16Type readRec( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 PKey )
	{
		final String S_ProcName = "readRec";
		boolean permissionGranted = canReadInt16Type(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Lock the specific Int16Type record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the Int16Type instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamInt16Type lockRec( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 PKey )
	{
		final String S_ProcName = "lockRec";
		boolean permissionGranted = canUpdateInt16Type(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "updateint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific Int16Type record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific Int16Type instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFBamInt16Type[] readAllRec( ICFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllRec";
		boolean permissionGranted = canReadInt16Type(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read the specific Int16Type record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Id	The Int16Type key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamInt16Type readRecByIdIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argId )
	{
		final String S_ProcName = "readRecByIdIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadInt16Type(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read the specific Int16Type record instance identified by the unique key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The Int16Type key attribute of the instance generating the id.
	 *
	 *	@param	Name	The Int16Type key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamInt16Type readRecByUNameIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		String argName )
	{
		final String S_ProcName = "readRecByUNameIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadInt16Type(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByUNameIdx");
	}

	/**
	 *	Read an array of the specific Int16Type record instances identified by the duplicate key ScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The Int16Type key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamInt16Type[] readRecByScopeIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId )
	{
		final String S_ProcName = "readRecByScopeIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadInt16Type(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByScopeIdx");
	}

	/**
	 *	Read an array of the specific Int16Type record instances identified by the duplicate key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DefSchemaId	The Int16Type key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamInt16Type[] readRecByDefSchemaIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argDefSchemaId )
	{
		final String S_ProcName = "readRecByDefSchemaIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadInt16Type(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByDefSchemaIdx");
	}

	/**
	 *	Read an array of the specific Int16Type record instances identified by the duplicate key PrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PrevId	The Int16Type key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamInt16Type[] readRecByPrevIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argPrevId )
	{
		final String S_ProcName = "readRecByPrevIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadInt16Type(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByPrevIdx");
	}

	/**
	 *	Read an array of the specific Int16Type record instances identified by the duplicate key NextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	NextId	The Int16Type key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamInt16Type[] readRecByNextIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argNextId )
	{
		final String S_ProcName = "readRecByNextIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadInt16Type(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByNextIdx");
	}

	/**
	 *	Read an array of the specific Int16Type record instances identified by the duplicate key ContPrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The Int16Type key attribute of the instance generating the id.
	 *
	 *	@param	PrevId	The Int16Type key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamInt16Type[] readRecByContPrevIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		ICFLibKeyHash256 argPrevId )
	{
		final String S_ProcName = "readRecByContPrevIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadInt16Type(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByContPrevIdx");
	}

	/**
	 *	Read an array of the specific Int16Type record instances identified by the duplicate key ContNextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The Int16Type key attribute of the instance generating the id.
	 *
	 *	@param	NextId	The Int16Type key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamInt16Type[] readRecByContNextIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		ICFLibKeyHash256 argNextId )
	{
		final String S_ProcName = "readRecByContNextIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadInt16Type(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByContNextIdx");
	}

	/**
	 *	Read an array of the specific Int16Type record instances identified by the duplicate key SchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SchemaDefId	The Int16Type key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamInt16Type[] readRecBySchemaIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argSchemaDefId )
	{
		final String S_ProcName = "readRecBySchemaIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadInt16Type(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecBySchemaIdx");
	}

	/**
	 *	Move the specified record up in the chain (i.e. to the previous position.)
	 *
	 *	@return	The refreshed record after it has been moved
	 */
	@Override
	public ICFBamInt16Type moveRecUp( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argId,
		int revision )
	{
		final String S_ProcName = "moveRecUp";
		boolean permissionGranted = canUpdateInt16Type(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "updateint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "moveRecUp");
	}

	/**
	 *	Move the specified record down in the chain (i.e. to the next position.)
	 *
	 *	@return	The refreshed record after it has been moved
	 */
	@Override
	public ICFBamInt16Type moveRecDown( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argId,
		int revision )
	{
		final String S_ProcName = "moveRecDown";
		boolean permissionGranted = canUpdateInt16Type(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "updateint16type", ICFBamSchema.SCHEMA_NAME, ICFBamInt16TypeTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "moveRecDown");
	}
}
