
// Description: Java 25 DbIO implementation for TextDef.

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
 *	CFBamJpaTextDefTable database implementation for TextDef
 */
public class CFBamJpaTextDefTable implements ICFBamTextDefTable
{
	protected CFBamJpaSchema schema;


	public CFBamJpaTextDefTable(ICFBamSchema schema) {
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

	protected boolean canCreateTextDef(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), Authorization.getSecClusterId(), Authorization.getSecTenantId(), "createtextdef");
		}
		return( permissionGranted );
	}

	protected boolean canReadTextDef(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), Authorization.getSecClusterId(), Authorization.getSecTenantId(), "readtextdef");
		}
		return( permissionGranted );
	}

	protected boolean canUpdateTextDef(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), ICFSecSchema.getSysClusterId(), ICFSecSchema.getSysTenantId(), "updatetextdef");
		}
		return( permissionGranted );
	}

	protected boolean canDeleteTextDef(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), ICFSecSchema.getSysClusterId(), ICFSecSchema.getSysTenantId(), "deletetextdef");
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
	public ICFBamTextDef createTextDef( ICFSecAuthorization Authorization,
		ICFBamTextDef rec )
	{
		final String S_ProcName = "createTextDef";
		boolean permissionGranted = canCreateTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "createtextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createTextDef", 1, "rec");
		}
		else if (rec instanceof CFBamJpaTextDef) {
			CFBamJpaTextDef jparec = (CFBamJpaTextDef)rec;
			CFBamJpaTextDef retval = schema.getJpaHooksSchema().getTextDefService().create(jparec);
		if(retval != null) {
			// Retrieve the TenantId from retval and check ICFSec.backingSchema().isMemberOfTenantGroup(auth,ClusterId,TenantId,'readtextdef'), clear retval to null if not a member
			CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
			CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readtextdef")) {
				retval = null;
			}
		}
		return( retval );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createTextDef", "rec", rec, "CFBamJpaTextDef");
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
	public ICFBamTextDef updateTextDef( ICFSecAuthorization Authorization,
		ICFBamTextDef rec )
	{
		final String S_ProcName = "updateTextDef";
		boolean permissionGranted = canUpdateTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "updatetextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateTextDef", 1, "rec");
		}
		else if (rec instanceof CFBamJpaTextDef) {
			CFBamJpaTextDef jparec = (CFBamJpaTextDef)rec;
			CFBamJpaTextDef retval = schema.getJpaHooksSchema().getTextDefService().update(jparec);
		if(retval != null) {
			// Retrieve the TenantId from retval and check ICFSec.backingSchema().isMemberOfTenantGroup(auth,ClusterId,TenantId,'readtextdef'), clear retval to null if not a member
			CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
			CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readtextdef")) {
				retval = null;
			}
		}
		return( retval );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateTextDef", "rec", rec, "CFBamJpaTextDef");
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
	public void deleteTextDef( ICFSecAuthorization Authorization,
		ICFBamTextDef rec )
	{
		final String S_ProcName = "deleteTextDef";
		boolean permissionGranted = canDeleteTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "deletetextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		if (rec == null) {
			return;
		}
		if (rec instanceof CFBamJpaTextDef) {
			CFBamJpaTextDef jparec = (CFBamJpaTextDef)rec;
			schema.getJpaHooksSchema().getTextDefService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteTextDef", "rec", rec, "CFBamJpaTextDef");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteTextDef");
	}

	/**
	 *	Delete the TextDef instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteTextDefByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argKey )
	{
		final String S_ProcName = "deleteTextDefByIdIdx";
		boolean permissionGranted = canDeleteTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "deletetextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTextDefService().deleteByIdIdx(argKey);
	}

	/**
	 *	Delete the TextDef instances identified by the key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TextDef key attribute of the instance generating the id.
	 *
	 *	@param	Name	The TextDef key attribute of the instance generating the id.
	 */
	@Override
	public void deleteTextDefByUNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId,
		String argName )
	{
		final String S_ProcName = "deleteTextDefByUNameIdx";
		boolean permissionGranted = canDeleteTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "deletetextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTextDefService().deleteByUNameIdx(argScopeId,
		argName);
	}


	/**
	 *	Delete the TextDef instances identified by the key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteTextDefByUNameIdx( ICFSecAuthorization Authorization,
		ICFBamValueByUNameIdxKey argKey )
	{
		final String S_ProcName = "deleteTextDefByUNameIdx";
		boolean permissionGranted = canDeleteTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "deletetextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTextDefService().deleteByUNameIdx(argKey.getRequiredScopeId(),
			argKey.getRequiredName());
	}

	/**
	 *	Delete the TextDef instances identified by the key ScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TextDef key attribute of the instance generating the id.
	 */
	@Override
	public void deleteTextDefByScopeIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId )
	{
		final String S_ProcName = "deleteTextDefByScopeIdx";
		boolean permissionGranted = canDeleteTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "deletetextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTextDefService().deleteByScopeIdx(argScopeId);
	}


	/**
	 *	Delete the TextDef instances identified by the key ScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteTextDefByScopeIdx( ICFSecAuthorization Authorization,
		ICFBamValueByScopeIdxKey argKey )
	{
		final String S_ProcName = "deleteTextDefByScopeIdx";
		boolean permissionGranted = canDeleteTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "deletetextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTextDefService().deleteByScopeIdx(argKey.getRequiredScopeId());
	}

	/**
	 *	Delete the TextDef instances identified by the key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DefSchemaId	The TextDef key attribute of the instance generating the id.
	 */
	@Override
	public void deleteTextDefByDefSchemaIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argDefSchemaId )
	{
		final String S_ProcName = "deleteTextDefByDefSchemaIdx";
		boolean permissionGranted = canDeleteTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "deletetextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTextDefService().deleteByDefSchemaIdx(argDefSchemaId);
	}


	/**
	 *	Delete the TextDef instances identified by the key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteTextDefByDefSchemaIdx( ICFSecAuthorization Authorization,
		ICFBamValueByDefSchemaIdxKey argKey )
	{
		final String S_ProcName = "deleteTextDefByDefSchemaIdx";
		boolean permissionGranted = canDeleteTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "deletetextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTextDefService().deleteByDefSchemaIdx(argKey.getOptionalDefSchemaId());
	}

	/**
	 *	Delete the TextDef instances identified by the key PrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PrevId	The TextDef key attribute of the instance generating the id.
	 */
	@Override
	public void deleteTextDefByPrevIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argPrevId )
	{
		final String S_ProcName = "deleteTextDefByPrevIdx";
		boolean permissionGranted = canDeleteTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "deletetextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTextDefService().deleteByPrevIdx(argPrevId);
	}


	/**
	 *	Delete the TextDef instances identified by the key PrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteTextDefByPrevIdx( ICFSecAuthorization Authorization,
		ICFBamValueByPrevIdxKey argKey )
	{
		final String S_ProcName = "deleteTextDefByPrevIdx";
		boolean permissionGranted = canDeleteTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "deletetextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTextDefService().deleteByPrevIdx(argKey.getOptionalPrevId());
	}

	/**
	 *	Delete the TextDef instances identified by the key NextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	NextId	The TextDef key attribute of the instance generating the id.
	 */
	@Override
	public void deleteTextDefByNextIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argNextId )
	{
		final String S_ProcName = "deleteTextDefByNextIdx";
		boolean permissionGranted = canDeleteTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "deletetextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTextDefService().deleteByNextIdx(argNextId);
	}


	/**
	 *	Delete the TextDef instances identified by the key NextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteTextDefByNextIdx( ICFSecAuthorization Authorization,
		ICFBamValueByNextIdxKey argKey )
	{
		final String S_ProcName = "deleteTextDefByNextIdx";
		boolean permissionGranted = canDeleteTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "deletetextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTextDefService().deleteByNextIdx(argKey.getOptionalNextId());
	}

	/**
	 *	Delete the TextDef instances identified by the key ContPrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TextDef key attribute of the instance generating the id.
	 *
	 *	@param	PrevId	The TextDef key attribute of the instance generating the id.
	 */
	@Override
	public void deleteTextDefByContPrevIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId,
		CFLibDbKeyHash256 argPrevId )
	{
		final String S_ProcName = "deleteTextDefByContPrevIdx";
		boolean permissionGranted = canDeleteTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "deletetextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTextDefService().deleteByContPrevIdx(argScopeId,
		argPrevId);
	}


	/**
	 *	Delete the TextDef instances identified by the key ContPrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteTextDefByContPrevIdx( ICFSecAuthorization Authorization,
		ICFBamValueByContPrevIdxKey argKey )
	{
		final String S_ProcName = "deleteTextDefByContPrevIdx";
		boolean permissionGranted = canDeleteTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "deletetextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTextDefService().deleteByContPrevIdx(argKey.getRequiredScopeId(),
			argKey.getOptionalPrevId());
	}

	/**
	 *	Delete the TextDef instances identified by the key ContNextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TextDef key attribute of the instance generating the id.
	 *
	 *	@param	NextId	The TextDef key attribute of the instance generating the id.
	 */
	@Override
	public void deleteTextDefByContNextIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId,
		CFLibDbKeyHash256 argNextId )
	{
		final String S_ProcName = "deleteTextDefByContNextIdx";
		boolean permissionGranted = canDeleteTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "deletetextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTextDefService().deleteByContNextIdx(argScopeId,
		argNextId);
	}


	/**
	 *	Delete the TextDef instances identified by the key ContNextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteTextDefByContNextIdx( ICFSecAuthorization Authorization,
		ICFBamValueByContNextIdxKey argKey )
	{
		final String S_ProcName = "deleteTextDefByContNextIdx";
		boolean permissionGranted = canDeleteTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "deletetextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getTextDefService().deleteByContNextIdx(argKey.getRequiredScopeId(),
			argKey.getOptionalNextId());
	}


	/**
	 *	Read the derived TextDef record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the TextDef instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamTextDef readDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		final String S_ProcName = "readDerived";
		boolean permissionGranted = canReadTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "readtextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFBamTextDef retval = schema.getJpaHooksSchema().getTextDefService().find(PKey);
		if(retval != null) {
			// Retrieve the TenantId from retval and check ICFSec.backingSchema().isMemberOfTenantGroup(auth,ClusterId,TenantId,'readtextdef'), clear retval to null if not a member
			CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
			CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readtextdef")) {
				retval = null;
			}
		}
		return( retval );
	}

	/**
	 *	Lock the derived TextDef record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the TextDef instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamTextDef lockDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		final String S_ProcName = "lockDerived";
		boolean permissionGranted = canUpdateTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "updatetextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFBamTextDef retval = schema.getJpaHooksSchema().getTextDefService().lockByIdIdx(PKey);
		if(retval != null) {
			// Retrieve the TenantId from retval and check ICFSec.backingSchema().isMemberOfTenantGroup(auth,ClusterId,TenantId,'readtextdef'), clear retval to null if not a member
			CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
			CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readtextdef")) {
				retval = null;
			}
		}
		return( retval );
	}

	/**
	 *	Read all TextDef instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamTextDef[] readAllDerived( ICFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllDerived";
		boolean permissionGranted = canReadTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "readtextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaTextDef> retlist = schema.getJpaHooksSchema().getTextDefService().findAll();
		if(retlist != null) {
			ArrayList<CFBamJpaTextDef> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readtextdef")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamTextDef[] retset = new ICFBamTextDef[retlist.size()];
		int idx = 0;
		for (CFBamJpaTextDef cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived TextDef record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Id	The TextDef key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamTextDef readDerivedByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argId )
	{
		final String S_ProcName = "readDerivedByIdIdx";
		boolean permissionGranted = canReadTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "readtextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFBamTextDef retval = schema.getJpaHooksSchema().getTextDefService().find(argId);
		if(retval != null) {
			// Retrieve the TenantId from retval and check ICFSec.backingSchema().isMemberOfTenantGroup(auth,ClusterId,TenantId,'readtextdef'), clear retval to null if not a member
			CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
			CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readtextdef")) {
				retval = null;
			}
		}
		return( retval );
	}

	/**
	 *	Read the derived TextDef record instance identified by the unique key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TextDef key attribute of the instance generating the id.
	 *
	 *	@param	Name	The TextDef key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamTextDef readDerivedByUNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId,
		String argName )
	{
		final String S_ProcName = "readDerivedByUNameIdx";
		boolean permissionGranted = canReadTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "readtextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFBamTextDef retval = schema.getJpaHooksSchema().getTextDefService().findByUNameIdx(argScopeId,
		argName);
		if(retval != null) {
			// Retrieve the TenantId from retval and check ICFSec.backingSchema().isMemberOfTenantGroup(auth,ClusterId,TenantId,'readtextdef'), clear retval to null if not a member
			CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
			CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readtextdef")) {
				retval = null;
			}
		}
		return( retval );
	}

	/**
	 *	Read an array of the derived TextDef record instances identified by the duplicate key ScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TextDef key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamTextDef[] readDerivedByScopeIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId )
	{
		final String S_ProcName = "readDerivedByScopeIdx";
		boolean permissionGranted = canReadTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "readtextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaTextDef> retlist = schema.getJpaHooksSchema().getTextDefService().findByScopeIdx(argScopeId);
		if(retlist != null) {
			ArrayList<CFBamJpaTextDef> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readtextdef")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamTextDef[] retset = new ICFBamTextDef[retlist.size()];
		int idx = 0;
		for (CFBamJpaTextDef cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived TextDef record instances identified by the duplicate key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DefSchemaId	The TextDef key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamTextDef[] readDerivedByDefSchemaIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argDefSchemaId )
	{
		final String S_ProcName = "readDerivedByDefSchemaIdx";
		boolean permissionGranted = canReadTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "readtextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaTextDef> retlist = schema.getJpaHooksSchema().getTextDefService().findByDefSchemaIdx(argDefSchemaId);
		if(retlist != null) {
			ArrayList<CFBamJpaTextDef> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readtextdef")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamTextDef[] retset = new ICFBamTextDef[retlist.size()];
		int idx = 0;
		for (CFBamJpaTextDef cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived TextDef record instances identified by the duplicate key PrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PrevId	The TextDef key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamTextDef[] readDerivedByPrevIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argPrevId )
	{
		final String S_ProcName = "readDerivedByPrevIdx";
		boolean permissionGranted = canReadTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "readtextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaTextDef> retlist = schema.getJpaHooksSchema().getTextDefService().findByPrevIdx(argPrevId);
		if(retlist != null) {
			ArrayList<CFBamJpaTextDef> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readtextdef")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamTextDef[] retset = new ICFBamTextDef[retlist.size()];
		int idx = 0;
		for (CFBamJpaTextDef cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived TextDef record instances identified by the duplicate key NextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	NextId	The TextDef key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamTextDef[] readDerivedByNextIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argNextId )
	{
		final String S_ProcName = "readDerivedByNextIdx";
		boolean permissionGranted = canReadTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "readtextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaTextDef> retlist = schema.getJpaHooksSchema().getTextDefService().findByNextIdx(argNextId);
		if(retlist != null) {
			ArrayList<CFBamJpaTextDef> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readtextdef")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamTextDef[] retset = new ICFBamTextDef[retlist.size()];
		int idx = 0;
		for (CFBamJpaTextDef cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived TextDef record instances identified by the duplicate key ContPrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TextDef key attribute of the instance generating the id.
	 *
	 *	@param	PrevId	The TextDef key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamTextDef[] readDerivedByContPrevIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId,
		CFLibDbKeyHash256 argPrevId )
	{
		final String S_ProcName = "readDerivedByContPrevIdx";
		boolean permissionGranted = canReadTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "readtextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaTextDef> retlist = schema.getJpaHooksSchema().getTextDefService().findByContPrevIdx(argScopeId,
		argPrevId);
		if(retlist != null) {
			ArrayList<CFBamJpaTextDef> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readtextdef")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamTextDef[] retset = new ICFBamTextDef[retlist.size()];
		int idx = 0;
		for (CFBamJpaTextDef cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived TextDef record instances identified by the duplicate key ContNextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TextDef key attribute of the instance generating the id.
	 *
	 *	@param	NextId	The TextDef key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamTextDef[] readDerivedByContNextIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId,
		CFLibDbKeyHash256 argNextId )
	{
		final String S_ProcName = "readDerivedByContNextIdx";
		boolean permissionGranted = canReadTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "readtextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaTextDef> retlist = schema.getJpaHooksSchema().getTextDefService().findByContNextIdx(argScopeId,
		argNextId);
		if(retlist != null) {
			ArrayList<CFBamJpaTextDef> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readtextdef")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamTextDef[] retset = new ICFBamTextDef[retlist.size()];
		int idx = 0;
		for (CFBamJpaTextDef cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the specific TextDef record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the TextDef instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamTextDef readRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		final String S_ProcName = "readRec";
		boolean permissionGranted = canReadTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "readtextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Lock the specific TextDef record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the TextDef instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamTextDef lockRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		final String S_ProcName = "lockRec";
		boolean permissionGranted = canUpdateTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "updatetextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific TextDef record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific TextDef instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFBamTextDef[] readAllRec( ICFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllRec";
		boolean permissionGranted = canReadTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "readtextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read the specific TextDef record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Id	The TextDef key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamTextDef readRecByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argId )
	{
		final String S_ProcName = "readRecByIdIdx";
		boolean permissionGranted = canReadTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "readtextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read the specific TextDef record instance identified by the unique key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TextDef key attribute of the instance generating the id.
	 *
	 *	@param	Name	The TextDef key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamTextDef readRecByUNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId,
		String argName )
	{
		final String S_ProcName = "readRecByUNameIdx";
		boolean permissionGranted = canReadTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "readtextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByUNameIdx");
	}

	/**
	 *	Read an array of the specific TextDef record instances identified by the duplicate key ScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TextDef key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamTextDef[] readRecByScopeIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId )
	{
		final String S_ProcName = "readRecByScopeIdx";
		boolean permissionGranted = canReadTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "readtextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByScopeIdx");
	}

	/**
	 *	Read an array of the specific TextDef record instances identified by the duplicate key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DefSchemaId	The TextDef key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamTextDef[] readRecByDefSchemaIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argDefSchemaId )
	{
		final String S_ProcName = "readRecByDefSchemaIdx";
		boolean permissionGranted = canReadTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "readtextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByDefSchemaIdx");
	}

	/**
	 *	Read an array of the specific TextDef record instances identified by the duplicate key PrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PrevId	The TextDef key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamTextDef[] readRecByPrevIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argPrevId )
	{
		final String S_ProcName = "readRecByPrevIdx";
		boolean permissionGranted = canReadTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "readtextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByPrevIdx");
	}

	/**
	 *	Read an array of the specific TextDef record instances identified by the duplicate key NextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	NextId	The TextDef key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamTextDef[] readRecByNextIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argNextId )
	{
		final String S_ProcName = "readRecByNextIdx";
		boolean permissionGranted = canReadTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "readtextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByNextIdx");
	}

	/**
	 *	Read an array of the specific TextDef record instances identified by the duplicate key ContPrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TextDef key attribute of the instance generating the id.
	 *
	 *	@param	PrevId	The TextDef key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamTextDef[] readRecByContPrevIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId,
		CFLibDbKeyHash256 argPrevId )
	{
		final String S_ProcName = "readRecByContPrevIdx";
		boolean permissionGranted = canReadTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "readtextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByContPrevIdx");
	}

	/**
	 *	Read an array of the specific TextDef record instances identified by the duplicate key ContNextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The TextDef key attribute of the instance generating the id.
	 *
	 *	@param	NextId	The TextDef key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamTextDef[] readRecByContNextIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId,
		CFLibDbKeyHash256 argNextId )
	{
		final String S_ProcName = "readRecByContNextIdx";
		boolean permissionGranted = canReadTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "readtextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByContNextIdx");
	}

	/**
	 *	Move the specified record up in the chain (i.e. to the previous position.)
	 *
	 *	@return	The refreshed record after it has been moved
	 */
	@Override
	public ICFBamTextDef moveRecUp( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argId,
		int revision )
	{
		final String S_ProcName = "moveRecUp";
		boolean permissionGranted = canUpdateTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "updatetextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "moveRecUp");
	}

	/**
	 *	Move the specified record down in the chain (i.e. to the next position.)
	 *
	 *	@return	The refreshed record after it has been moved
	 */
	@Override
	public ICFBamTextDef moveRecDown( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argId,
		int revision )
	{
		final String S_ProcName = "moveRecDown";
		boolean permissionGranted = canUpdateTextDef(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamTextDefTable.TABLE_NAME, "updatetextdef", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "moveRecDown");
	}
}
