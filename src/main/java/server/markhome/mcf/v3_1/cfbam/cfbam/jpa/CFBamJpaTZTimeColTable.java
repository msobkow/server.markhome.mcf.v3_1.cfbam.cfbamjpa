
// Description: Java 25 DbIO implementation for TZTimeCol.

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
 *	CFBamJpaTZTimeColTable database implementation for TZTimeCol
 */
public class CFBamJpaTZTimeColTable implements ICFBamTZTimeColTable
{
	protected CFBamJpaSchema schema;


	public CFBamJpaTZTimeColTable(ICFBamSchema schema) {
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

	protected boolean canCreateTZTimeCol(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), Authorization.getSecClusterId(), Authorization.getSecTenantId(), "createtztimecol");
		}
		return( permissionGranted );
	}

	protected boolean canReadTZTimeCol(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), Authorization.getSecClusterId(), Authorization.getSecTenantId(), "readtztimecol");
		}
		return( permissionGranted );
	}

	protected boolean canUpdateTZTimeCol(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), ICFSecSchema.getSysClusterId(), ICFSecSchema.getSysTenantId(), "updatetztimecol");
		}
		return( permissionGranted );
	}

	protected boolean canDeleteTZTimeCol(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), ICFSecSchema.getSysClusterId(), ICFSecSchema.getSysTenantId(), "deletetztimecol");
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
	public ICFBamTZTimeCol createTZTimeCol( ICFSecAuthorization Authorization,
		ICFBamTZTimeCol rec )
	{
		final String S_ProcName = "createTZTimeCol";
		boolean permissionGranted = canCreateTZTimeCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "createtztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createTZTimeCol", 1, "rec");
		}
		else if (rec instanceof CFBamJpaTZTimeCol) {
			CFBamJpaTZTimeCol jparec = (CFBamJpaTZTimeCol)rec;
			jparec.setCreatedAt(LocalDateTime.now());
			jparec.setUpdatedAt(jparec.getCreatedAt());
			jparec.setCreatedByUserId(Authorization.getSecUserId());
			jparec.setUpdatedByUserId(Authorization.getSecUserId());
			CFBamJpaTZTimeCol retval = schema.getJpaHooksSchema().getTZTimeColService().create(jparec);
		return(retval);
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createTZTimeCol", "rec", rec, "CFBamJpaTZTimeCol");
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
	public ICFBamTZTimeCol updateTZTimeCol( ICFSecAuthorization Authorization,
		ICFBamTZTimeCol rec )
	{
		final String S_ProcName = "updateTZTimeCol";
		boolean permissionGranted = canUpdateTZTimeCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "updatetztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateTZTimeCol", 1, "rec");
		}
		else if (rec instanceof CFBamJpaTZTimeCol) {
			CFBamJpaTZTimeCol jparec = (CFBamJpaTZTimeCol)rec;
			jparec.setUpdatedAt(LocalDateTime.now());
			jparec.setUpdatedByUserId(Authorization.getSecUserId());
			CFBamJpaTZTimeCol retval = schema.getJpaHooksSchema().getTZTimeColService().update(jparec);
		return(retval);
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateTZTimeCol", "rec", rec, "CFBamJpaTZTimeCol");
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
	public void deleteTZTimeCol( ICFSecAuthorization Authorization,
		ICFBamTZTimeCol rec )
	{
		final String S_ProcName = "deleteTZTimeCol";
		boolean permissionGranted = canDeleteTZTimeCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		if (rec == null) {
			return;
		}
		if (rec instanceof CFBamJpaTZTimeCol) {
			CFBamJpaTZTimeCol jparec = (CFBamJpaTZTimeCol)rec;
			schema.getJpaHooksSchema().getTZTimeColService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteTZTimeCol", "rec", rec, "CFBamJpaTZTimeCol");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteTZTimeCol");
	}

	/**
	 *	Delete the TZTimeCol instances identified by the key TableIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TableId	The TZTimeCol key attribute of the instance generating the id.
	 */
	@Override
	public void deleteTZTimeColByTableIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argTableId )
	{
		final String S_ProcName = "deleteTZTimeColByTableIdx";
		boolean permissionGranted = canDeleteTZTimeCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTZTimeColService().deleteByTableIdx(argTableId);
	}


	/**
	 *	Delete the TZTimeCol instances identified by the key TableIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteTZTimeColByTableIdx( ICFSecAuthorization Authorization,
		ICFBamTZTimeColByTableIdxKey argKey )
	{
		final String S_ProcName = "deleteTZTimeColByTableIdx";
		boolean permissionGranted = canDeleteTZTimeCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTZTimeColService().deleteByTableIdx(argKey.getRequiredTableId());
	}

	/**
	 *	Delete the TZTimeCol instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteTZTimeColByIdIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argKey )
	{
		final String S_ProcName = "deleteTZTimeColByIdIdx";
		boolean permissionGranted = canDeleteTZTimeCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTZTimeColService().deleteByIdIdx(argKey);
	}

	/**
	 *	Delete the TZTimeCol instances identified by the key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TZTimeCol key attribute of the instance generating the id.
	 *
	 *	@param	Name	The TZTimeCol key attribute of the instance generating the id.
	 */
	@Override
	public void deleteTZTimeColByUNameIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		String argName )
	{
		final String S_ProcName = "deleteTZTimeColByUNameIdx";
		boolean permissionGranted = canDeleteTZTimeCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTZTimeColService().deleteByUNameIdx(argScopeId,
		argName);
	}


	/**
	 *	Delete the TZTimeCol instances identified by the key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteTZTimeColByUNameIdx( ICFSecAuthorization Authorization,
		ICFBamValueByUNameIdxKey argKey )
	{
		final String S_ProcName = "deleteTZTimeColByUNameIdx";
		boolean permissionGranted = canDeleteTZTimeCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTZTimeColService().deleteByUNameIdx(argKey.getRequiredScopeId(),
			argKey.getRequiredName());
	}

	/**
	 *	Delete the TZTimeCol instances identified by the key ScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TZTimeCol key attribute of the instance generating the id.
	 */
	@Override
	public void deleteTZTimeColByScopeIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId )
	{
		final String S_ProcName = "deleteTZTimeColByScopeIdx";
		boolean permissionGranted = canDeleteTZTimeCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTZTimeColService().deleteByScopeIdx(argScopeId);
	}


	/**
	 *	Delete the TZTimeCol instances identified by the key ScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteTZTimeColByScopeIdx( ICFSecAuthorization Authorization,
		ICFBamValueByScopeIdxKey argKey )
	{
		final String S_ProcName = "deleteTZTimeColByScopeIdx";
		boolean permissionGranted = canDeleteTZTimeCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTZTimeColService().deleteByScopeIdx(argKey.getRequiredScopeId());
	}

	/**
	 *	Delete the TZTimeCol instances identified by the key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DefSchemaId	The TZTimeCol key attribute of the instance generating the id.
	 */
	@Override
	public void deleteTZTimeColByDefSchemaIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argDefSchemaId )
	{
		final String S_ProcName = "deleteTZTimeColByDefSchemaIdx";
		boolean permissionGranted = canDeleteTZTimeCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTZTimeColService().deleteByDefSchemaIdx(argDefSchemaId);
	}


	/**
	 *	Delete the TZTimeCol instances identified by the key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteTZTimeColByDefSchemaIdx( ICFSecAuthorization Authorization,
		ICFBamValueByDefSchemaIdxKey argKey )
	{
		final String S_ProcName = "deleteTZTimeColByDefSchemaIdx";
		boolean permissionGranted = canDeleteTZTimeCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTZTimeColService().deleteByDefSchemaIdx(argKey.getOptionalDefSchemaId());
	}

	/**
	 *	Delete the TZTimeCol instances identified by the key PrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PrevId	The TZTimeCol key attribute of the instance generating the id.
	 */
	@Override
	public void deleteTZTimeColByPrevIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argPrevId )
	{
		final String S_ProcName = "deleteTZTimeColByPrevIdx";
		boolean permissionGranted = canDeleteTZTimeCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTZTimeColService().deleteByPrevIdx(argPrevId);
	}


	/**
	 *	Delete the TZTimeCol instances identified by the key PrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteTZTimeColByPrevIdx( ICFSecAuthorization Authorization,
		ICFBamValueByPrevIdxKey argKey )
	{
		final String S_ProcName = "deleteTZTimeColByPrevIdx";
		boolean permissionGranted = canDeleteTZTimeCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTZTimeColService().deleteByPrevIdx(argKey.getOptionalPrevId());
	}

	/**
	 *	Delete the TZTimeCol instances identified by the key NextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	NextId	The TZTimeCol key attribute of the instance generating the id.
	 */
	@Override
	public void deleteTZTimeColByNextIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argNextId )
	{
		final String S_ProcName = "deleteTZTimeColByNextIdx";
		boolean permissionGranted = canDeleteTZTimeCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTZTimeColService().deleteByNextIdx(argNextId);
	}


	/**
	 *	Delete the TZTimeCol instances identified by the key NextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteTZTimeColByNextIdx( ICFSecAuthorization Authorization,
		ICFBamValueByNextIdxKey argKey )
	{
		final String S_ProcName = "deleteTZTimeColByNextIdx";
		boolean permissionGranted = canDeleteTZTimeCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTZTimeColService().deleteByNextIdx(argKey.getOptionalNextId());
	}

	/**
	 *	Delete the TZTimeCol instances identified by the key ContPrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TZTimeCol key attribute of the instance generating the id.
	 *
	 *	@param	PrevId	The TZTimeCol key attribute of the instance generating the id.
	 */
	@Override
	public void deleteTZTimeColByContPrevIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		ICFLibKeyHash256 argPrevId )
	{
		final String S_ProcName = "deleteTZTimeColByContPrevIdx";
		boolean permissionGranted = canDeleteTZTimeCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTZTimeColService().deleteByContPrevIdx(argScopeId,
		argPrevId);
	}


	/**
	 *	Delete the TZTimeCol instances identified by the key ContPrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteTZTimeColByContPrevIdx( ICFSecAuthorization Authorization,
		ICFBamValueByContPrevIdxKey argKey )
	{
		final String S_ProcName = "deleteTZTimeColByContPrevIdx";
		boolean permissionGranted = canDeleteTZTimeCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTZTimeColService().deleteByContPrevIdx(argKey.getRequiredScopeId(),
			argKey.getOptionalPrevId());
	}

	/**
	 *	Delete the TZTimeCol instances identified by the key ContNextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TZTimeCol key attribute of the instance generating the id.
	 *
	 *	@param	NextId	The TZTimeCol key attribute of the instance generating the id.
	 */
	@Override
	public void deleteTZTimeColByContNextIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		ICFLibKeyHash256 argNextId )
	{
		final String S_ProcName = "deleteTZTimeColByContNextIdx";
		boolean permissionGranted = canDeleteTZTimeCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTZTimeColService().deleteByContNextIdx(argScopeId,
		argNextId);
	}


	/**
	 *	Delete the TZTimeCol instances identified by the key ContNextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteTZTimeColByContNextIdx( ICFSecAuthorization Authorization,
		ICFBamValueByContNextIdxKey argKey )
	{
		final String S_ProcName = "deleteTZTimeColByContNextIdx";
		boolean permissionGranted = canDeleteTZTimeCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTZTimeColService().deleteByContNextIdx(argKey.getRequiredScopeId(),
			argKey.getOptionalNextId());
	}


	/**
	 *	Read the derived TZTimeCol record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the TZTimeCol instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamTZTimeCol readDerived( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 PKey )
	{
		final String S_ProcName = "readDerived";
		boolean permissionGranted = canReadTZTimeCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFBamTZTimeCol retval = schema.getJpaHooksSchema().getTZTimeColService().find(PKey);
		return(retval);
	}

	/**
	 *	Lock the derived TZTimeCol record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the TZTimeCol instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamTZTimeCol lockDerived( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 PKey )
	{
		final String S_ProcName = "lockDerived";
		boolean permissionGranted = canUpdateTZTimeCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "updatetztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFBamTZTimeCol retval = schema.getJpaHooksSchema().getTZTimeColService().lockByIdIdx(PKey);
		return(retval);
	}

	/**
	 *	Read all TZTimeCol instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamTZTimeCol[] readAllDerived( ICFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllDerived";
		boolean permissionGranted = canReadTZTimeCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaTZTimeCol> retlist = schema.getJpaHooksSchema().getTZTimeColService().findAll();
		ICFBamTZTimeCol[] retset = new ICFBamTZTimeCol[retlist.size()];
		int idx = 0;
		for (CFBamJpaTZTimeCol cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived TZTimeCol record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Id	The TZTimeCol key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamTZTimeCol readDerivedByIdIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argId )
	{
		final String S_ProcName = "readDerivedByIdIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTZTimeCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		ICFBamTZTimeCol retval = schema.getJpaHooksSchema().getTZTimeColService().find(argId);
		if(retval != null && !ICFSecSchema.getSystemId().equals(Authorization.getSecUserId())) {
				ICFSecTenant tenant = retval.getRequiredContainerTable().getRequiredOwnerTenant();
				ICFSecCluster cluster = tenant.getRequiredContainerCluster();
			CFLibDbKeyHash256 effClusterId = cluster.getRequiredId();
			CFLibDbKeyHash256 effTenantId = tenant.getRequiredId();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readtztimecol")) {
				retval = null;
			}
		}
		return(retval);
	}

	/**
	 *	Read the derived TZTimeCol record instance identified by the unique key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TZTimeCol key attribute of the instance generating the id.
	 *
	 *	@param	Name	The TZTimeCol key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamTZTimeCol readDerivedByUNameIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		String argName )
	{
		final String S_ProcName = "readDerivedByUNameIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTZTimeCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		ICFBamTZTimeCol retval = schema.getJpaHooksSchema().getTZTimeColService().findByUNameIdx(argScopeId,
		argName);
		if(retval != null && !ICFSecSchema.getSystemId().equals(Authorization.getSecUserId())) {
				ICFSecTenant tenant = retval.getRequiredContainerTable().getRequiredOwnerTenant();
				ICFSecCluster cluster = tenant.getRequiredContainerCluster();
			CFLibDbKeyHash256 effClusterId = cluster.getRequiredId();
			CFLibDbKeyHash256 effTenantId = tenant.getRequiredId();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readtztimecol")) {
				retval = null;
			}
		}
		return(retval);
	}

	/**
	 *	Read an array of the derived TZTimeCol record instances identified by the duplicate key ScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TZTimeCol key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamTZTimeCol[] readDerivedByScopeIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId )
	{
		final String S_ProcName = "readDerivedByScopeIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTZTimeCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		List<CFBamJpaTZTimeCol> retlist = schema.getJpaHooksSchema().getTZTimeColService().findByScopeIdx(argScopeId);
		ICFBamTZTimeCol[] retset = new ICFBamTZTimeCol[retlist.size()];
		int idx = 0;
		for (CFBamJpaTZTimeCol cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived TZTimeCol record instances identified by the duplicate key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DefSchemaId	The TZTimeCol key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamTZTimeCol[] readDerivedByDefSchemaIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argDefSchemaId )
	{
		final String S_ProcName = "readDerivedByDefSchemaIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTZTimeCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		List<CFBamJpaTZTimeCol> retlist = schema.getJpaHooksSchema().getTZTimeColService().findByDefSchemaIdx(argDefSchemaId);
		ICFBamTZTimeCol[] retset = new ICFBamTZTimeCol[retlist.size()];
		int idx = 0;
		for (CFBamJpaTZTimeCol cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived TZTimeCol record instances identified by the duplicate key PrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PrevId	The TZTimeCol key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamTZTimeCol[] readDerivedByPrevIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argPrevId )
	{
		final String S_ProcName = "readDerivedByPrevIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTZTimeCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		List<CFBamJpaTZTimeCol> retlist = schema.getJpaHooksSchema().getTZTimeColService().findByPrevIdx(argPrevId);
		ICFBamTZTimeCol[] retset = new ICFBamTZTimeCol[retlist.size()];
		int idx = 0;
		for (CFBamJpaTZTimeCol cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived TZTimeCol record instances identified by the duplicate key NextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	NextId	The TZTimeCol key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamTZTimeCol[] readDerivedByNextIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argNextId )
	{
		final String S_ProcName = "readDerivedByNextIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTZTimeCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		List<CFBamJpaTZTimeCol> retlist = schema.getJpaHooksSchema().getTZTimeColService().findByNextIdx(argNextId);
		ICFBamTZTimeCol[] retset = new ICFBamTZTimeCol[retlist.size()];
		int idx = 0;
		for (CFBamJpaTZTimeCol cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived TZTimeCol record instances identified by the duplicate key ContPrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TZTimeCol key attribute of the instance generating the id.
	 *
	 *	@param	PrevId	The TZTimeCol key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamTZTimeCol[] readDerivedByContPrevIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		ICFLibKeyHash256 argPrevId )
	{
		final String S_ProcName = "readDerivedByContPrevIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTZTimeCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		List<CFBamJpaTZTimeCol> retlist = schema.getJpaHooksSchema().getTZTimeColService().findByContPrevIdx(argScopeId,
		argPrevId);
		ICFBamTZTimeCol[] retset = new ICFBamTZTimeCol[retlist.size()];
		int idx = 0;
		for (CFBamJpaTZTimeCol cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived TZTimeCol record instances identified by the duplicate key ContNextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TZTimeCol key attribute of the instance generating the id.
	 *
	 *	@param	NextId	The TZTimeCol key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamTZTimeCol[] readDerivedByContNextIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		ICFLibKeyHash256 argNextId )
	{
		final String S_ProcName = "readDerivedByContNextIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTZTimeCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		List<CFBamJpaTZTimeCol> retlist = schema.getJpaHooksSchema().getTZTimeColService().findByContNextIdx(argScopeId,
		argNextId);
		ICFBamTZTimeCol[] retset = new ICFBamTZTimeCol[retlist.size()];
		int idx = 0;
		for (CFBamJpaTZTimeCol cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived TZTimeCol record instances identified by the duplicate key TableIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TableId	The TZTimeCol key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamTZTimeCol[] readDerivedByTableIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argTableId )
	{
		final String S_ProcName = "readDerivedByTableIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTZTimeCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		List<CFBamJpaTZTimeCol> retlist = schema.getJpaHooksSchema().getTZTimeColService().findByTableIdx(argTableId);
		ICFBamTZTimeCol[] retset = new ICFBamTZTimeCol[retlist.size()];
		int idx = 0;
		for (CFBamJpaTZTimeCol cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the specific TZTimeCol record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the TZTimeCol instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamTZTimeCol readRec( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 PKey )
	{
		final String S_ProcName = "readRec";
		boolean permissionGranted = canReadTZTimeCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Lock the specific TZTimeCol record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the TZTimeCol instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamTZTimeCol lockRec( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 PKey )
	{
		final String S_ProcName = "lockRec";
		boolean permissionGranted = canUpdateTZTimeCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "updatetztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific TZTimeCol record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific TZTimeCol instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFBamTZTimeCol[] readAllRec( ICFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllRec";
		boolean permissionGranted = canReadTZTimeCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read the specific TZTimeCol record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Id	The TZTimeCol key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamTZTimeCol readRecByIdIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argId )
	{
		final String S_ProcName = "readRecByIdIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTZTimeCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read the specific TZTimeCol record instance identified by the unique key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TZTimeCol key attribute of the instance generating the id.
	 *
	 *	@param	Name	The TZTimeCol key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamTZTimeCol readRecByUNameIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		String argName )
	{
		final String S_ProcName = "readRecByUNameIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTZTimeCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByUNameIdx");
	}

	/**
	 *	Read an array of the specific TZTimeCol record instances identified by the duplicate key ScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TZTimeCol key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamTZTimeCol[] readRecByScopeIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId )
	{
		final String S_ProcName = "readRecByScopeIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTZTimeCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByScopeIdx");
	}

	/**
	 *	Read an array of the specific TZTimeCol record instances identified by the duplicate key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DefSchemaId	The TZTimeCol key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamTZTimeCol[] readRecByDefSchemaIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argDefSchemaId )
	{
		final String S_ProcName = "readRecByDefSchemaIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTZTimeCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByDefSchemaIdx");
	}

	/**
	 *	Read an array of the specific TZTimeCol record instances identified by the duplicate key PrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PrevId	The TZTimeCol key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamTZTimeCol[] readRecByPrevIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argPrevId )
	{
		final String S_ProcName = "readRecByPrevIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTZTimeCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByPrevIdx");
	}

	/**
	 *	Read an array of the specific TZTimeCol record instances identified by the duplicate key NextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	NextId	The TZTimeCol key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamTZTimeCol[] readRecByNextIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argNextId )
	{
		final String S_ProcName = "readRecByNextIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTZTimeCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByNextIdx");
	}

	/**
	 *	Read an array of the specific TZTimeCol record instances identified by the duplicate key ContPrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TZTimeCol key attribute of the instance generating the id.
	 *
	 *	@param	PrevId	The TZTimeCol key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamTZTimeCol[] readRecByContPrevIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		ICFLibKeyHash256 argPrevId )
	{
		final String S_ProcName = "readRecByContPrevIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTZTimeCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByContPrevIdx");
	}

	/**
	 *	Read an array of the specific TZTimeCol record instances identified by the duplicate key ContNextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TZTimeCol key attribute of the instance generating the id.
	 *
	 *	@param	NextId	The TZTimeCol key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamTZTimeCol[] readRecByContNextIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		ICFLibKeyHash256 argNextId )
	{
		final String S_ProcName = "readRecByContNextIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTZTimeCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByContNextIdx");
	}

	/**
	 *	Read an array of the specific TZTimeCol record instances identified by the duplicate key TableIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TableId	The TZTimeCol key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamTZTimeCol[] readRecByTableIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argTableId )
	{
		final String S_ProcName = "readRecByTableIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTZTimeCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByTableIdx");
	}

	/**
	 *	Move the specified record up in the chain (i.e. to the previous position.)
	 *
	 *	@return	The refreshed record after it has been moved
	 */
	@Override
	public ICFBamTZTimeCol moveRecUp( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argId,
		int revision )
	{
		final String S_ProcName = "moveRecUp";
		boolean permissionGranted = canUpdateTZTimeCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "updatetztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "moveRecUp");
	}

	/**
	 *	Move the specified record down in the chain (i.e. to the next position.)
	 *
	 *	@return	The refreshed record after it has been moved
	 */
	@Override
	public ICFBamTZTimeCol moveRecDown( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argId,
		int revision )
	{
		final String S_ProcName = "moveRecDown";
		boolean permissionGranted = canUpdateTZTimeCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "updatetztimecol", ICFBamSchema.SCHEMA_NAME, ICFBamTZTimeColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "moveRecDown");
	}
}
