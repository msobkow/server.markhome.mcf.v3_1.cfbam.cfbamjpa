
// Description: Java 25 DbIO implementation for TextCol.

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
 *	CFBamJpaTextColTable database implementation for TextCol
 */
public class CFBamJpaTextColTable implements ICFBamTextColTable
{
	protected CFBamJpaSchema schema;


	public CFBamJpaTextColTable(ICFBamSchema schema) {
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

	protected boolean canCreateTextCol(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), Authorization.getSecClusterId(), Authorization.getSecTenantId(), "createtextcol");
		}
		return( permissionGranted );
	}

	protected boolean canReadTextCol(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), Authorization.getSecClusterId(), Authorization.getSecTenantId(), "readtextcol");
		}
		return( permissionGranted );
	}

	protected boolean canUpdateTextCol(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), ICFSecSchema.getSysClusterId(), ICFSecSchema.getSysTenantId(), "updatetextcol");
		}
		return( permissionGranted );
	}

	protected boolean canDeleteTextCol(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), ICFSecSchema.getSysClusterId(), ICFSecSchema.getSysTenantId(), "deletetextcol");
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
	public ICFBamTextCol createTextCol( ICFSecAuthorization Authorization,
		ICFBamTextCol rec )
	{
		final String S_ProcName = "createTextCol";
		boolean permissionGranted = canCreateTextCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "createtextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createTextCol", 1, "rec");
		}
		else if (rec instanceof CFBamJpaTextCol) {
			CFBamJpaTextCol jparec = (CFBamJpaTextCol)rec;
			jparec.setCreatedAt(LocalDateTime.now());
			jparec.setUpdatedAt(jparec.getCreatedAt());
			jparec.setCreatedByUserId(Authorization.getSecUserId());
			jparec.setUpdatedByUserId(Authorization.getSecUserId());
			CFBamJpaTextCol retval = schema.getJpaHooksSchema().getTextColService().create(jparec);
		return(retval);
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createTextCol", "rec", rec, "CFBamJpaTextCol");
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
	public ICFBamTextCol updateTextCol( ICFSecAuthorization Authorization,
		ICFBamTextCol rec )
	{
		final String S_ProcName = "updateTextCol";
		boolean permissionGranted = canUpdateTextCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "updatetextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateTextCol", 1, "rec");
		}
		else if (rec instanceof CFBamJpaTextCol) {
			CFBamJpaTextCol jparec = (CFBamJpaTextCol)rec;
			jparec.setUpdatedAt(LocalDateTime.now());
			jparec.setUpdatedByUserId(Authorization.getSecUserId());
			CFBamJpaTextCol retval = schema.getJpaHooksSchema().getTextColService().update(jparec);
		return(retval);
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateTextCol", "rec", rec, "CFBamJpaTextCol");
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
	public void deleteTextCol( ICFSecAuthorization Authorization,
		ICFBamTextCol rec )
	{
		final String S_ProcName = "deleteTextCol";
		boolean permissionGranted = canDeleteTextCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		if (rec == null) {
			return;
		}
		if (rec instanceof CFBamJpaTextCol) {
			CFBamJpaTextCol jparec = (CFBamJpaTextCol)rec;
			schema.getJpaHooksSchema().getTextColService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteTextCol", "rec", rec, "CFBamJpaTextCol");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteTextCol");
	}

	/**
	 *	Delete the TextCol instances identified by the key TableIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TableId	The TextCol key attribute of the instance generating the id.
	 */
	@Override
	public void deleteTextColByTableIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argTableId )
	{
		final String S_ProcName = "deleteTextColByTableIdx";
		boolean permissionGranted = canDeleteTextCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTextColService().deleteByTableIdx(argTableId);
	}


	/**
	 *	Delete the TextCol instances identified by the key TableIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteTextColByTableIdx( ICFSecAuthorization Authorization,
		ICFBamTextColByTableIdxKey argKey )
	{
		final String S_ProcName = "deleteTextColByTableIdx";
		boolean permissionGranted = canDeleteTextCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTextColService().deleteByTableIdx(argKey.getRequiredTableId());
	}

	/**
	 *	Delete the TextCol instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteTextColByIdIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argKey )
	{
		final String S_ProcName = "deleteTextColByIdIdx";
		boolean permissionGranted = canDeleteTextCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTextColService().deleteByIdIdx(argKey);
	}

	/**
	 *	Delete the TextCol instances identified by the key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TextCol key attribute of the instance generating the id.
	 *
	 *	@param	Name	The TextCol key attribute of the instance generating the id.
	 */
	@Override
	public void deleteTextColByUNameIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		String argName )
	{
		final String S_ProcName = "deleteTextColByUNameIdx";
		boolean permissionGranted = canDeleteTextCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTextColService().deleteByUNameIdx(argScopeId,
		argName);
	}


	/**
	 *	Delete the TextCol instances identified by the key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteTextColByUNameIdx( ICFSecAuthorization Authorization,
		ICFBamValueByUNameIdxKey argKey )
	{
		final String S_ProcName = "deleteTextColByUNameIdx";
		boolean permissionGranted = canDeleteTextCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTextColService().deleteByUNameIdx(argKey.getRequiredScopeId(),
			argKey.getRequiredName());
	}

	/**
	 *	Delete the TextCol instances identified by the key ScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TextCol key attribute of the instance generating the id.
	 */
	@Override
	public void deleteTextColByScopeIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId )
	{
		final String S_ProcName = "deleteTextColByScopeIdx";
		boolean permissionGranted = canDeleteTextCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTextColService().deleteByScopeIdx(argScopeId);
	}


	/**
	 *	Delete the TextCol instances identified by the key ScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteTextColByScopeIdx( ICFSecAuthorization Authorization,
		ICFBamValueByScopeIdxKey argKey )
	{
		final String S_ProcName = "deleteTextColByScopeIdx";
		boolean permissionGranted = canDeleteTextCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTextColService().deleteByScopeIdx(argKey.getRequiredScopeId());
	}

	/**
	 *	Delete the TextCol instances identified by the key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DefSchemaId	The TextCol key attribute of the instance generating the id.
	 */
	@Override
	public void deleteTextColByDefSchemaIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argDefSchemaId )
	{
		final String S_ProcName = "deleteTextColByDefSchemaIdx";
		boolean permissionGranted = canDeleteTextCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTextColService().deleteByDefSchemaIdx(argDefSchemaId);
	}


	/**
	 *	Delete the TextCol instances identified by the key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteTextColByDefSchemaIdx( ICFSecAuthorization Authorization,
		ICFBamValueByDefSchemaIdxKey argKey )
	{
		final String S_ProcName = "deleteTextColByDefSchemaIdx";
		boolean permissionGranted = canDeleteTextCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTextColService().deleteByDefSchemaIdx(argKey.getOptionalDefSchemaId());
	}

	/**
	 *	Delete the TextCol instances identified by the key PrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PrevId	The TextCol key attribute of the instance generating the id.
	 */
	@Override
	public void deleteTextColByPrevIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argPrevId )
	{
		final String S_ProcName = "deleteTextColByPrevIdx";
		boolean permissionGranted = canDeleteTextCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTextColService().deleteByPrevIdx(argPrevId);
	}


	/**
	 *	Delete the TextCol instances identified by the key PrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteTextColByPrevIdx( ICFSecAuthorization Authorization,
		ICFBamValueByPrevIdxKey argKey )
	{
		final String S_ProcName = "deleteTextColByPrevIdx";
		boolean permissionGranted = canDeleteTextCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTextColService().deleteByPrevIdx(argKey.getOptionalPrevId());
	}

	/**
	 *	Delete the TextCol instances identified by the key NextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	NextId	The TextCol key attribute of the instance generating the id.
	 */
	@Override
	public void deleteTextColByNextIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argNextId )
	{
		final String S_ProcName = "deleteTextColByNextIdx";
		boolean permissionGranted = canDeleteTextCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTextColService().deleteByNextIdx(argNextId);
	}


	/**
	 *	Delete the TextCol instances identified by the key NextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteTextColByNextIdx( ICFSecAuthorization Authorization,
		ICFBamValueByNextIdxKey argKey )
	{
		final String S_ProcName = "deleteTextColByNextIdx";
		boolean permissionGranted = canDeleteTextCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTextColService().deleteByNextIdx(argKey.getOptionalNextId());
	}

	/**
	 *	Delete the TextCol instances identified by the key ContPrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TextCol key attribute of the instance generating the id.
	 *
	 *	@param	PrevId	The TextCol key attribute of the instance generating the id.
	 */
	@Override
	public void deleteTextColByContPrevIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		ICFLibKeyHash256 argPrevId )
	{
		final String S_ProcName = "deleteTextColByContPrevIdx";
		boolean permissionGranted = canDeleteTextCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTextColService().deleteByContPrevIdx(argScopeId,
		argPrevId);
	}


	/**
	 *	Delete the TextCol instances identified by the key ContPrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteTextColByContPrevIdx( ICFSecAuthorization Authorization,
		ICFBamValueByContPrevIdxKey argKey )
	{
		final String S_ProcName = "deleteTextColByContPrevIdx";
		boolean permissionGranted = canDeleteTextCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTextColService().deleteByContPrevIdx(argKey.getRequiredScopeId(),
			argKey.getOptionalPrevId());
	}

	/**
	 *	Delete the TextCol instances identified by the key ContNextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TextCol key attribute of the instance generating the id.
	 *
	 *	@param	NextId	The TextCol key attribute of the instance generating the id.
	 */
	@Override
	public void deleteTextColByContNextIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		ICFLibKeyHash256 argNextId )
	{
		final String S_ProcName = "deleteTextColByContNextIdx";
		boolean permissionGranted = canDeleteTextCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTextColService().deleteByContNextIdx(argScopeId,
		argNextId);
	}


	/**
	 *	Delete the TextCol instances identified by the key ContNextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteTextColByContNextIdx( ICFSecAuthorization Authorization,
		ICFBamValueByContNextIdxKey argKey )
	{
		final String S_ProcName = "deleteTextColByContNextIdx";
		boolean permissionGranted = canDeleteTextCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "deletetextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTextColService().deleteByContNextIdx(argKey.getRequiredScopeId(),
			argKey.getOptionalNextId());
	}


	/**
	 *	Read the derived TextCol record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the TextCol instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamTextCol readDerived( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 PKey )
	{
		final String S_ProcName = "readDerived";
		boolean permissionGranted = canReadTextCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFBamTextCol retval = schema.getJpaHooksSchema().getTextColService().find(PKey);
		return(retval);
	}

	/**
	 *	Lock the derived TextCol record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the TextCol instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamTextCol lockDerived( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 PKey )
	{
		final String S_ProcName = "lockDerived";
		boolean permissionGranted = canUpdateTextCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "updatetextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFBamTextCol retval = schema.getJpaHooksSchema().getTextColService().lockByIdIdx(PKey);
		return(retval);
	}

	/**
	 *	Read all TextCol instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamTextCol[] readAllDerived( ICFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllDerived";
		boolean permissionGranted = canReadTextCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaTextCol> retlist = schema.getJpaHooksSchema().getTextColService().findAll();
		ICFBamTextCol[] retset = new ICFBamTextCol[retlist.size()];
		int idx = 0;
		for (CFBamJpaTextCol cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived TextCol record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Id	The TextCol key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamTextCol readDerivedByIdIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argId )
	{
		final String S_ProcName = "readDerivedByIdIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTextCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		ICFBamTextCol retval = schema.getJpaHooksSchema().getTextColService().find(argId);
		if(retval != null && !ICFSecSchema.getSystemId().equals(Authorization.getSecUserId())) {
				ICFSecTenant tenant = retval.getRequiredContainerTable().getRequiredOwnerTenant();
				ICFSecCluster cluster = tenant.getRequiredContainerCluster();
			CFLibDbKeyHash256 effClusterId = cluster.getRequiredId();
			CFLibDbKeyHash256 effTenantId = tenant.getRequiredId();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readtextcol")) {
				retval = null;
			}
		}
		return(retval);
	}

	/**
	 *	Read the derived TextCol record instance identified by the unique key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TextCol key attribute of the instance generating the id.
	 *
	 *	@param	Name	The TextCol key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamTextCol readDerivedByUNameIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		String argName )
	{
		final String S_ProcName = "readDerivedByUNameIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTextCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		ICFBamTextCol retval = schema.getJpaHooksSchema().getTextColService().findByUNameIdx(argScopeId,
		argName);
		if(retval != null && !ICFSecSchema.getSystemId().equals(Authorization.getSecUserId())) {
				ICFSecTenant tenant = retval.getRequiredContainerTable().getRequiredOwnerTenant();
				ICFSecCluster cluster = tenant.getRequiredContainerCluster();
			CFLibDbKeyHash256 effClusterId = cluster.getRequiredId();
			CFLibDbKeyHash256 effTenantId = tenant.getRequiredId();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readtextcol")) {
				retval = null;
			}
		}
		return(retval);
	}

	/**
	 *	Read an array of the derived TextCol record instances identified by the duplicate key ScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TextCol key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamTextCol[] readDerivedByScopeIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId )
	{
		final String S_ProcName = "readDerivedByScopeIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTextCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		List<CFBamJpaTextCol> retlist = schema.getJpaHooksSchema().getTextColService().findByScopeIdx(argScopeId);
		ICFBamTextCol[] retset = new ICFBamTextCol[retlist.size()];
		int idx = 0;
		for (CFBamJpaTextCol cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived TextCol record instances identified by the duplicate key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DefSchemaId	The TextCol key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamTextCol[] readDerivedByDefSchemaIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argDefSchemaId )
	{
		final String S_ProcName = "readDerivedByDefSchemaIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTextCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		List<CFBamJpaTextCol> retlist = schema.getJpaHooksSchema().getTextColService().findByDefSchemaIdx(argDefSchemaId);
		ICFBamTextCol[] retset = new ICFBamTextCol[retlist.size()];
		int idx = 0;
		for (CFBamJpaTextCol cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived TextCol record instances identified by the duplicate key PrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PrevId	The TextCol key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamTextCol[] readDerivedByPrevIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argPrevId )
	{
		final String S_ProcName = "readDerivedByPrevIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTextCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		List<CFBamJpaTextCol> retlist = schema.getJpaHooksSchema().getTextColService().findByPrevIdx(argPrevId);
		ICFBamTextCol[] retset = new ICFBamTextCol[retlist.size()];
		int idx = 0;
		for (CFBamJpaTextCol cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived TextCol record instances identified by the duplicate key NextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	NextId	The TextCol key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamTextCol[] readDerivedByNextIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argNextId )
	{
		final String S_ProcName = "readDerivedByNextIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTextCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		List<CFBamJpaTextCol> retlist = schema.getJpaHooksSchema().getTextColService().findByNextIdx(argNextId);
		ICFBamTextCol[] retset = new ICFBamTextCol[retlist.size()];
		int idx = 0;
		for (CFBamJpaTextCol cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived TextCol record instances identified by the duplicate key ContPrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TextCol key attribute of the instance generating the id.
	 *
	 *	@param	PrevId	The TextCol key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamTextCol[] readDerivedByContPrevIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		ICFLibKeyHash256 argPrevId )
	{
		final String S_ProcName = "readDerivedByContPrevIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTextCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		List<CFBamJpaTextCol> retlist = schema.getJpaHooksSchema().getTextColService().findByContPrevIdx(argScopeId,
		argPrevId);
		ICFBamTextCol[] retset = new ICFBamTextCol[retlist.size()];
		int idx = 0;
		for (CFBamJpaTextCol cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived TextCol record instances identified by the duplicate key ContNextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TextCol key attribute of the instance generating the id.
	 *
	 *	@param	NextId	The TextCol key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamTextCol[] readDerivedByContNextIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		ICFLibKeyHash256 argNextId )
	{
		final String S_ProcName = "readDerivedByContNextIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTextCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		List<CFBamJpaTextCol> retlist = schema.getJpaHooksSchema().getTextColService().findByContNextIdx(argScopeId,
		argNextId);
		ICFBamTextCol[] retset = new ICFBamTextCol[retlist.size()];
		int idx = 0;
		for (CFBamJpaTextCol cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived TextCol record instances identified by the duplicate key TableIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TableId	The TextCol key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamTextCol[] readDerivedByTableIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argTableId )
	{
		final String S_ProcName = "readDerivedByTableIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTextCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		List<CFBamJpaTextCol> retlist = schema.getJpaHooksSchema().getTextColService().findByTableIdx(argTableId);
		ICFBamTextCol[] retset = new ICFBamTextCol[retlist.size()];
		int idx = 0;
		for (CFBamJpaTextCol cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the specific TextCol record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the TextCol instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamTextCol readRec( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 PKey )
	{
		final String S_ProcName = "readRec";
		boolean permissionGranted = canReadTextCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Lock the specific TextCol record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the TextCol instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamTextCol lockRec( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 PKey )
	{
		final String S_ProcName = "lockRec";
		boolean permissionGranted = canUpdateTextCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "updatetextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific TextCol record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific TextCol instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFBamTextCol[] readAllRec( ICFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllRec";
		boolean permissionGranted = canReadTextCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read the specific TextCol record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Id	The TextCol key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamTextCol readRecByIdIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argId )
	{
		final String S_ProcName = "readRecByIdIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTextCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read the specific TextCol record instance identified by the unique key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TextCol key attribute of the instance generating the id.
	 *
	 *	@param	Name	The TextCol key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamTextCol readRecByUNameIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		String argName )
	{
		final String S_ProcName = "readRecByUNameIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTextCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByUNameIdx");
	}

	/**
	 *	Read an array of the specific TextCol record instances identified by the duplicate key ScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TextCol key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamTextCol[] readRecByScopeIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId )
	{
		final String S_ProcName = "readRecByScopeIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTextCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByScopeIdx");
	}

	/**
	 *	Read an array of the specific TextCol record instances identified by the duplicate key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DefSchemaId	The TextCol key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamTextCol[] readRecByDefSchemaIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argDefSchemaId )
	{
		final String S_ProcName = "readRecByDefSchemaIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTextCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByDefSchemaIdx");
	}

	/**
	 *	Read an array of the specific TextCol record instances identified by the duplicate key PrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PrevId	The TextCol key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamTextCol[] readRecByPrevIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argPrevId )
	{
		final String S_ProcName = "readRecByPrevIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTextCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByPrevIdx");
	}

	/**
	 *	Read an array of the specific TextCol record instances identified by the duplicate key NextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	NextId	The TextCol key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamTextCol[] readRecByNextIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argNextId )
	{
		final String S_ProcName = "readRecByNextIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTextCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByNextIdx");
	}

	/**
	 *	Read an array of the specific TextCol record instances identified by the duplicate key ContPrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TextCol key attribute of the instance generating the id.
	 *
	 *	@param	PrevId	The TextCol key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamTextCol[] readRecByContPrevIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		ICFLibKeyHash256 argPrevId )
	{
		final String S_ProcName = "readRecByContPrevIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTextCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByContPrevIdx");
	}

	/**
	 *	Read an array of the specific TextCol record instances identified by the duplicate key ContNextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TextCol key attribute of the instance generating the id.
	 *
	 *	@param	NextId	The TextCol key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamTextCol[] readRecByContNextIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argScopeId,
		ICFLibKeyHash256 argNextId )
	{
		final String S_ProcName = "readRecByContNextIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTextCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByContNextIdx");
	}

	/**
	 *	Read an array of the specific TextCol record instances identified by the duplicate key TableIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TableId	The TextCol key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamTextCol[] readRecByTableIdx( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argTableId )
	{
		final String S_ProcName = "readRecByTableIdx";
		boolean permissionGranted = false;
		if (!permissionGranted) {
			permissionGranted = canReadTextCol(S_ProcName, Authorization);
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "readtextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}
		throw new CFLibNotImplementedYetException(getClass(), "readRecByTableIdx");
	}

	/**
	 *	Move the specified record up in the chain (i.e. to the previous position.)
	 *
	 *	@return	The refreshed record after it has been moved
	 */
	@Override
	public ICFBamTextCol moveRecUp( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argId,
		int revision )
	{
		final String S_ProcName = "moveRecUp";
		boolean permissionGranted = canUpdateTextCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "updatetextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "moveRecUp");
	}

	/**
	 *	Move the specified record down in the chain (i.e. to the next position.)
	 *
	 *	@return	The refreshed record after it has been moved
	 */
	@Override
	public ICFBamTextCol moveRecDown( ICFSecAuthorization Authorization,
		ICFLibKeyHash256 argId,
		int revision )
	{
		final String S_ProcName = "moveRecDown";
		boolean permissionGranted = canUpdateTextCol(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, "updatetextcol", ICFBamSchema.SCHEMA_NAME, ICFBamTextColTable.TABLE_NAME, Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "moveRecDown");
	}
}
