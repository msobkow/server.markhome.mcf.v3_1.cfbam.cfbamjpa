
// Description: Java 25 DbIO implementation for NmTokenType.

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
 *	CFBamJpaNmTokenTypeTable database implementation for NmTokenType
 */
public class CFBamJpaNmTokenTypeTable implements ICFBamNmTokenTypeTable
{
	protected CFBamJpaSchema schema;


	public CFBamJpaNmTokenTypeTable(ICFBamSchema schema) {
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

	protected boolean canCreateNmTokenType(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), Authorization.getSecClusterId(), Authorization.getSecTenantId(), "createnmtokentype");
		}
		return( permissionGranted );
	}

	protected boolean canReadNmTokenType(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), Authorization.getSecClusterId(), Authorization.getSecTenantId(), "readnmtokentype");
		}
		return( permissionGranted );
	}

	protected boolean canUpdateNmTokenType(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), ICFSecSchema.getSysClusterId(), ICFSecSchema.getSysTenantId(), "updatenmtokentype");
		}
		return( permissionGranted );
	}

	protected boolean canDeleteNmTokenType(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), ICFSecSchema.getSysClusterId(), ICFSecSchema.getSysTenantId(), "deletenmtokentype");
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
	public ICFBamNmTokenType createNmTokenType( ICFSecAuthorization Authorization,
		ICFBamNmTokenType rec )
	{
		final String S_ProcName = "createNmTokenType";
		boolean permissionGranted = canCreateNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "createnmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createNmTokenType", 1, "rec");
		}
		else if (rec instanceof CFBamJpaNmTokenType) {
			CFBamJpaNmTokenType jparec = (CFBamJpaNmTokenType)rec;
			CFBamJpaNmTokenType retval = schema.getJpaHooksSchema().getNmTokenTypeService().create(jparec);
		if(retval != null) {
			// Retrieve the TenantId from retval and check ICFSec.backingSchema().isMemberOfTenantGroup(auth,ClusterId,TenantId,'readnmtokentype'), clear retval to null if not a member
			CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
			CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readnmtokentype")) {
				retval = null;
			}
		}
		return( retval );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createNmTokenType", "rec", rec, "CFBamJpaNmTokenType");
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
	public ICFBamNmTokenType updateNmTokenType( ICFSecAuthorization Authorization,
		ICFBamNmTokenType rec )
	{
		final String S_ProcName = "updateNmTokenType";
		boolean permissionGranted = canUpdateNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "updatenmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateNmTokenType", 1, "rec");
		}
		else if (rec instanceof CFBamJpaNmTokenType) {
			CFBamJpaNmTokenType jparec = (CFBamJpaNmTokenType)rec;
			CFBamJpaNmTokenType retval = schema.getJpaHooksSchema().getNmTokenTypeService().update(jparec);
		if(retval != null) {
			// Retrieve the TenantId from retval and check ICFSec.backingSchema().isMemberOfTenantGroup(auth,ClusterId,TenantId,'readnmtokentype'), clear retval to null if not a member
			CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
			CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readnmtokentype")) {
				retval = null;
			}
		}
		return( retval );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateNmTokenType", "rec", rec, "CFBamJpaNmTokenType");
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
	public void deleteNmTokenType( ICFSecAuthorization Authorization,
		ICFBamNmTokenType rec )
	{
		final String S_ProcName = "deleteNmTokenType";
		boolean permissionGranted = canDeleteNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "deletenmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		if (rec == null) {
			return;
		}
		if (rec instanceof CFBamJpaNmTokenType) {
			CFBamJpaNmTokenType jparec = (CFBamJpaNmTokenType)rec;
			schema.getJpaHooksSchema().getNmTokenTypeService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteNmTokenType", "rec", rec, "CFBamJpaNmTokenType");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteNmTokenType");
	}

	/**
	 *	Delete the NmTokenType instances identified by the key SchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SchemaDefId	The NmTokenType key attribute of the instance generating the id.
	 */
	@Override
	public void deleteNmTokenTypeBySchemaIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSchemaDefId )
	{
		final String S_ProcName = "deleteNmTokenTypeBySchemaIdx";
		boolean permissionGranted = canDeleteNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "deletenmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getNmTokenTypeService().deleteBySchemaIdx(argSchemaDefId);
	}


	/**
	 *	Delete the NmTokenType instances identified by the key SchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteNmTokenTypeBySchemaIdx( ICFSecAuthorization Authorization,
		ICFBamNmTokenTypeBySchemaIdxKey argKey )
	{
		final String S_ProcName = "deleteNmTokenTypeBySchemaIdx";
		boolean permissionGranted = canDeleteNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "deletenmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getNmTokenTypeService().deleteBySchemaIdx(argKey.getRequiredSchemaDefId());
	}

	/**
	 *	Delete the NmTokenType instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteNmTokenTypeByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argKey )
	{
		final String S_ProcName = "deleteNmTokenTypeByIdIdx";
		boolean permissionGranted = canDeleteNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "deletenmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getNmTokenTypeService().deleteByIdIdx(argKey);
	}

	/**
	 *	Delete the NmTokenType instances identified by the key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The NmTokenType key attribute of the instance generating the id.
	 *
	 *	@param	Name	The NmTokenType key attribute of the instance generating the id.
	 */
	@Override
	public void deleteNmTokenTypeByUNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId,
		String argName )
	{
		final String S_ProcName = "deleteNmTokenTypeByUNameIdx";
		boolean permissionGranted = canDeleteNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "deletenmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getNmTokenTypeService().deleteByUNameIdx(argScopeId,
		argName);
	}


	/**
	 *	Delete the NmTokenType instances identified by the key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteNmTokenTypeByUNameIdx( ICFSecAuthorization Authorization,
		ICFBamValueByUNameIdxKey argKey )
	{
		final String S_ProcName = "deleteNmTokenTypeByUNameIdx";
		boolean permissionGranted = canDeleteNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "deletenmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getNmTokenTypeService().deleteByUNameIdx(argKey.getRequiredScopeId(),
			argKey.getRequiredName());
	}

	/**
	 *	Delete the NmTokenType instances identified by the key ScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The NmTokenType key attribute of the instance generating the id.
	 */
	@Override
	public void deleteNmTokenTypeByScopeIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId )
	{
		final String S_ProcName = "deleteNmTokenTypeByScopeIdx";
		boolean permissionGranted = canDeleteNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "deletenmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getNmTokenTypeService().deleteByScopeIdx(argScopeId);
	}


	/**
	 *	Delete the NmTokenType instances identified by the key ScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteNmTokenTypeByScopeIdx( ICFSecAuthorization Authorization,
		ICFBamValueByScopeIdxKey argKey )
	{
		final String S_ProcName = "deleteNmTokenTypeByScopeIdx";
		boolean permissionGranted = canDeleteNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "deletenmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getNmTokenTypeService().deleteByScopeIdx(argKey.getRequiredScopeId());
	}

	/**
	 *	Delete the NmTokenType instances identified by the key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DefSchemaId	The NmTokenType key attribute of the instance generating the id.
	 */
	@Override
	public void deleteNmTokenTypeByDefSchemaIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argDefSchemaId )
	{
		final String S_ProcName = "deleteNmTokenTypeByDefSchemaIdx";
		boolean permissionGranted = canDeleteNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "deletenmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getNmTokenTypeService().deleteByDefSchemaIdx(argDefSchemaId);
	}


	/**
	 *	Delete the NmTokenType instances identified by the key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteNmTokenTypeByDefSchemaIdx( ICFSecAuthorization Authorization,
		ICFBamValueByDefSchemaIdxKey argKey )
	{
		final String S_ProcName = "deleteNmTokenTypeByDefSchemaIdx";
		boolean permissionGranted = canDeleteNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "deletenmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getNmTokenTypeService().deleteByDefSchemaIdx(argKey.getOptionalDefSchemaId());
	}

	/**
	 *	Delete the NmTokenType instances identified by the key PrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PrevId	The NmTokenType key attribute of the instance generating the id.
	 */
	@Override
	public void deleteNmTokenTypeByPrevIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argPrevId )
	{
		final String S_ProcName = "deleteNmTokenTypeByPrevIdx";
		boolean permissionGranted = canDeleteNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "deletenmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getNmTokenTypeService().deleteByPrevIdx(argPrevId);
	}


	/**
	 *	Delete the NmTokenType instances identified by the key PrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteNmTokenTypeByPrevIdx( ICFSecAuthorization Authorization,
		ICFBamValueByPrevIdxKey argKey )
	{
		final String S_ProcName = "deleteNmTokenTypeByPrevIdx";
		boolean permissionGranted = canDeleteNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "deletenmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getNmTokenTypeService().deleteByPrevIdx(argKey.getOptionalPrevId());
	}

	/**
	 *	Delete the NmTokenType instances identified by the key NextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	NextId	The NmTokenType key attribute of the instance generating the id.
	 */
	@Override
	public void deleteNmTokenTypeByNextIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argNextId )
	{
		final String S_ProcName = "deleteNmTokenTypeByNextIdx";
		boolean permissionGranted = canDeleteNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "deletenmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getNmTokenTypeService().deleteByNextIdx(argNextId);
	}


	/**
	 *	Delete the NmTokenType instances identified by the key NextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteNmTokenTypeByNextIdx( ICFSecAuthorization Authorization,
		ICFBamValueByNextIdxKey argKey )
	{
		final String S_ProcName = "deleteNmTokenTypeByNextIdx";
		boolean permissionGranted = canDeleteNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "deletenmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getNmTokenTypeService().deleteByNextIdx(argKey.getOptionalNextId());
	}

	/**
	 *	Delete the NmTokenType instances identified by the key ContPrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The NmTokenType key attribute of the instance generating the id.
	 *
	 *	@param	PrevId	The NmTokenType key attribute of the instance generating the id.
	 */
	@Override
	public void deleteNmTokenTypeByContPrevIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId,
		CFLibDbKeyHash256 argPrevId )
	{
		final String S_ProcName = "deleteNmTokenTypeByContPrevIdx";
		boolean permissionGranted = canDeleteNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "deletenmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getNmTokenTypeService().deleteByContPrevIdx(argScopeId,
		argPrevId);
	}


	/**
	 *	Delete the NmTokenType instances identified by the key ContPrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteNmTokenTypeByContPrevIdx( ICFSecAuthorization Authorization,
		ICFBamValueByContPrevIdxKey argKey )
	{
		final String S_ProcName = "deleteNmTokenTypeByContPrevIdx";
		boolean permissionGranted = canDeleteNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "deletenmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getNmTokenTypeService().deleteByContPrevIdx(argKey.getRequiredScopeId(),
			argKey.getOptionalPrevId());
	}

	/**
	 *	Delete the NmTokenType instances identified by the key ContNextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The NmTokenType key attribute of the instance generating the id.
	 *
	 *	@param	NextId	The NmTokenType key attribute of the instance generating the id.
	 */
	@Override
	public void deleteNmTokenTypeByContNextIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId,
		CFLibDbKeyHash256 argNextId )
	{
		final String S_ProcName = "deleteNmTokenTypeByContNextIdx";
		boolean permissionGranted = canDeleteNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "deletenmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getNmTokenTypeService().deleteByContNextIdx(argScopeId,
		argNextId);
	}


	/**
	 *	Delete the NmTokenType instances identified by the key ContNextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteNmTokenTypeByContNextIdx( ICFSecAuthorization Authorization,
		ICFBamValueByContNextIdxKey argKey )
	{
		final String S_ProcName = "deleteNmTokenTypeByContNextIdx";
		boolean permissionGranted = canDeleteNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "deletenmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getNmTokenTypeService().deleteByContNextIdx(argKey.getRequiredScopeId(),
			argKey.getOptionalNextId());
	}


	/**
	 *	Read the derived NmTokenType record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the NmTokenType instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamNmTokenType readDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		final String S_ProcName = "readDerived";
		boolean permissionGranted = canReadNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "readnmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFBamNmTokenType retval = schema.getJpaHooksSchema().getNmTokenTypeService().find(PKey);
		if(retval != null) {
			// Retrieve the TenantId from retval and check ICFSec.backingSchema().isMemberOfTenantGroup(auth,ClusterId,TenantId,'readnmtokentype'), clear retval to null if not a member
			CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
			CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readnmtokentype")) {
				retval = null;
			}
		}
		return( retval );
	}

	/**
	 *	Lock the derived NmTokenType record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the NmTokenType instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamNmTokenType lockDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		final String S_ProcName = "lockDerived";
		boolean permissionGranted = canUpdateNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "updatenmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFBamNmTokenType retval = schema.getJpaHooksSchema().getNmTokenTypeService().lockByIdIdx(PKey);
		if(retval != null) {
			// Retrieve the TenantId from retval and check ICFSec.backingSchema().isMemberOfTenantGroup(auth,ClusterId,TenantId,'readnmtokentype'), clear retval to null if not a member
			CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
			CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readnmtokentype")) {
				retval = null;
			}
		}
		return( retval );
	}

	/**
	 *	Read all NmTokenType instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamNmTokenType[] readAllDerived( ICFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllDerived";
		boolean permissionGranted = canReadNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "readnmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaNmTokenType> retlist = schema.getJpaHooksSchema().getNmTokenTypeService().findAll();
		if(retlist != null) {
			ArrayList<CFBamJpaNmTokenType> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readnmtokentype")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamNmTokenType[] retset = new ICFBamNmTokenType[retlist.size()];
		int idx = 0;
		for (CFBamJpaNmTokenType cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived NmTokenType record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Id	The NmTokenType key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamNmTokenType readDerivedByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argId )
	{
		final String S_ProcName = "readDerivedByIdIdx";
		boolean permissionGranted = canReadNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "readnmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFBamNmTokenType retval = schema.getJpaHooksSchema().getNmTokenTypeService().find(argId);
		if(retval != null) {
			// Retrieve the TenantId from retval and check ICFSec.backingSchema().isMemberOfTenantGroup(auth,ClusterId,TenantId,'readnmtokentype'), clear retval to null if not a member
			CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
			CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readnmtokentype")) {
				retval = null;
			}
		}
		return( retval );
	}

	/**
	 *	Read the derived NmTokenType record instance identified by the unique key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The NmTokenType key attribute of the instance generating the id.
	 *
	 *	@param	Name	The NmTokenType key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamNmTokenType readDerivedByUNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId,
		String argName )
	{
		final String S_ProcName = "readDerivedByUNameIdx";
		boolean permissionGranted = canReadNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "readnmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFBamNmTokenType retval = schema.getJpaHooksSchema().getNmTokenTypeService().findByUNameIdx(argScopeId,
		argName);
		if(retval != null) {
			// Retrieve the TenantId from retval and check ICFSec.backingSchema().isMemberOfTenantGroup(auth,ClusterId,TenantId,'readnmtokentype'), clear retval to null if not a member
			CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
			CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readnmtokentype")) {
				retval = null;
			}
		}
		return( retval );
	}

	/**
	 *	Read an array of the derived NmTokenType record instances identified by the duplicate key ScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The NmTokenType key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamNmTokenType[] readDerivedByScopeIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId )
	{
		final String S_ProcName = "readDerivedByScopeIdx";
		boolean permissionGranted = canReadNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "readnmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaNmTokenType> retlist = schema.getJpaHooksSchema().getNmTokenTypeService().findByScopeIdx(argScopeId);
		if(retlist != null) {
			ArrayList<CFBamJpaNmTokenType> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readnmtokentype")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamNmTokenType[] retset = new ICFBamNmTokenType[retlist.size()];
		int idx = 0;
		for (CFBamJpaNmTokenType cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived NmTokenType record instances identified by the duplicate key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DefSchemaId	The NmTokenType key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamNmTokenType[] readDerivedByDefSchemaIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argDefSchemaId )
	{
		final String S_ProcName = "readDerivedByDefSchemaIdx";
		boolean permissionGranted = canReadNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "readnmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaNmTokenType> retlist = schema.getJpaHooksSchema().getNmTokenTypeService().findByDefSchemaIdx(argDefSchemaId);
		if(retlist != null) {
			ArrayList<CFBamJpaNmTokenType> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readnmtokentype")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamNmTokenType[] retset = new ICFBamNmTokenType[retlist.size()];
		int idx = 0;
		for (CFBamJpaNmTokenType cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived NmTokenType record instances identified by the duplicate key PrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PrevId	The NmTokenType key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamNmTokenType[] readDerivedByPrevIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argPrevId )
	{
		final String S_ProcName = "readDerivedByPrevIdx";
		boolean permissionGranted = canReadNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "readnmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaNmTokenType> retlist = schema.getJpaHooksSchema().getNmTokenTypeService().findByPrevIdx(argPrevId);
		if(retlist != null) {
			ArrayList<CFBamJpaNmTokenType> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readnmtokentype")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamNmTokenType[] retset = new ICFBamNmTokenType[retlist.size()];
		int idx = 0;
		for (CFBamJpaNmTokenType cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived NmTokenType record instances identified by the duplicate key NextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	NextId	The NmTokenType key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamNmTokenType[] readDerivedByNextIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argNextId )
	{
		final String S_ProcName = "readDerivedByNextIdx";
		boolean permissionGranted = canReadNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "readnmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaNmTokenType> retlist = schema.getJpaHooksSchema().getNmTokenTypeService().findByNextIdx(argNextId);
		if(retlist != null) {
			ArrayList<CFBamJpaNmTokenType> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readnmtokentype")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamNmTokenType[] retset = new ICFBamNmTokenType[retlist.size()];
		int idx = 0;
		for (CFBamJpaNmTokenType cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived NmTokenType record instances identified by the duplicate key ContPrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The NmTokenType key attribute of the instance generating the id.
	 *
	 *	@param	PrevId	The NmTokenType key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamNmTokenType[] readDerivedByContPrevIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId,
		CFLibDbKeyHash256 argPrevId )
	{
		final String S_ProcName = "readDerivedByContPrevIdx";
		boolean permissionGranted = canReadNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "readnmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaNmTokenType> retlist = schema.getJpaHooksSchema().getNmTokenTypeService().findByContPrevIdx(argScopeId,
		argPrevId);
		if(retlist != null) {
			ArrayList<CFBamJpaNmTokenType> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readnmtokentype")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamNmTokenType[] retset = new ICFBamNmTokenType[retlist.size()];
		int idx = 0;
		for (CFBamJpaNmTokenType cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived NmTokenType record instances identified by the duplicate key ContNextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The NmTokenType key attribute of the instance generating the id.
	 *
	 *	@param	NextId	The NmTokenType key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamNmTokenType[] readDerivedByContNextIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId,
		CFLibDbKeyHash256 argNextId )
	{
		final String S_ProcName = "readDerivedByContNextIdx";
		boolean permissionGranted = canReadNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "readnmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaNmTokenType> retlist = schema.getJpaHooksSchema().getNmTokenTypeService().findByContNextIdx(argScopeId,
		argNextId);
		if(retlist != null) {
			ArrayList<CFBamJpaNmTokenType> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readnmtokentype")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamNmTokenType[] retset = new ICFBamNmTokenType[retlist.size()];
		int idx = 0;
		for (CFBamJpaNmTokenType cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived NmTokenType record instances identified by the duplicate key SchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SchemaDefId	The NmTokenType key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamNmTokenType[] readDerivedBySchemaIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSchemaDefId )
	{
		final String S_ProcName = "readDerivedBySchemaIdx";
		boolean permissionGranted = canReadNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "readnmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaNmTokenType> retlist = schema.getJpaHooksSchema().getNmTokenTypeService().findBySchemaIdx(argSchemaDefId);
		if(retlist != null) {
			ArrayList<CFBamJpaNmTokenType> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readnmtokentype")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamNmTokenType[] retset = new ICFBamNmTokenType[retlist.size()];
		int idx = 0;
		for (CFBamJpaNmTokenType cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the specific NmTokenType record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the NmTokenType instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamNmTokenType readRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		final String S_ProcName = "readRec";
		boolean permissionGranted = canReadNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "readnmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Lock the specific NmTokenType record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the NmTokenType instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamNmTokenType lockRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		final String S_ProcName = "lockRec";
		boolean permissionGranted = canUpdateNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "updatenmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific NmTokenType record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific NmTokenType instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFBamNmTokenType[] readAllRec( ICFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllRec";
		boolean permissionGranted = canReadNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "readnmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read the specific NmTokenType record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Id	The NmTokenType key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamNmTokenType readRecByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argId )
	{
		final String S_ProcName = "readRecByIdIdx";
		boolean permissionGranted = canReadNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "readnmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read the specific NmTokenType record instance identified by the unique key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The NmTokenType key attribute of the instance generating the id.
	 *
	 *	@param	Name	The NmTokenType key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamNmTokenType readRecByUNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId,
		String argName )
	{
		final String S_ProcName = "readRecByUNameIdx";
		boolean permissionGranted = canReadNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "readnmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByUNameIdx");
	}

	/**
	 *	Read an array of the specific NmTokenType record instances identified by the duplicate key ScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The NmTokenType key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamNmTokenType[] readRecByScopeIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId )
	{
		final String S_ProcName = "readRecByScopeIdx";
		boolean permissionGranted = canReadNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "readnmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByScopeIdx");
	}

	/**
	 *	Read an array of the specific NmTokenType record instances identified by the duplicate key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DefSchemaId	The NmTokenType key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamNmTokenType[] readRecByDefSchemaIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argDefSchemaId )
	{
		final String S_ProcName = "readRecByDefSchemaIdx";
		boolean permissionGranted = canReadNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "readnmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByDefSchemaIdx");
	}

	/**
	 *	Read an array of the specific NmTokenType record instances identified by the duplicate key PrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PrevId	The NmTokenType key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamNmTokenType[] readRecByPrevIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argPrevId )
	{
		final String S_ProcName = "readRecByPrevIdx";
		boolean permissionGranted = canReadNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "readnmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByPrevIdx");
	}

	/**
	 *	Read an array of the specific NmTokenType record instances identified by the duplicate key NextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	NextId	The NmTokenType key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamNmTokenType[] readRecByNextIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argNextId )
	{
		final String S_ProcName = "readRecByNextIdx";
		boolean permissionGranted = canReadNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "readnmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByNextIdx");
	}

	/**
	 *	Read an array of the specific NmTokenType record instances identified by the duplicate key ContPrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The NmTokenType key attribute of the instance generating the id.
	 *
	 *	@param	PrevId	The NmTokenType key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamNmTokenType[] readRecByContPrevIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId,
		CFLibDbKeyHash256 argPrevId )
	{
		final String S_ProcName = "readRecByContPrevIdx";
		boolean permissionGranted = canReadNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "readnmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByContPrevIdx");
	}

	/**
	 *	Read an array of the specific NmTokenType record instances identified by the duplicate key ContNextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The NmTokenType key attribute of the instance generating the id.
	 *
	 *	@param	NextId	The NmTokenType key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamNmTokenType[] readRecByContNextIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId,
		CFLibDbKeyHash256 argNextId )
	{
		final String S_ProcName = "readRecByContNextIdx";
		boolean permissionGranted = canReadNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "readnmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByContNextIdx");
	}

	/**
	 *	Read an array of the specific NmTokenType record instances identified by the duplicate key SchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SchemaDefId	The NmTokenType key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamNmTokenType[] readRecBySchemaIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSchemaDefId )
	{
		final String S_ProcName = "readRecBySchemaIdx";
		boolean permissionGranted = canReadNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "readnmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecBySchemaIdx");
	}

	/**
	 *	Move the specified record up in the chain (i.e. to the previous position.)
	 *
	 *	@return	The refreshed record after it has been moved
	 */
	@Override
	public ICFBamNmTokenType moveRecUp( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argId,
		int revision )
	{
		final String S_ProcName = "moveRecUp";
		boolean permissionGranted = canUpdateNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "updatenmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "moveRecUp");
	}

	/**
	 *	Move the specified record down in the chain (i.e. to the next position.)
	 *
	 *	@return	The refreshed record after it has been moved
	 */
	@Override
	public ICFBamNmTokenType moveRecDown( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argId,
		int revision )
	{
		final String S_ProcName = "moveRecDown";
		boolean permissionGranted = canUpdateNmTokenType(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamNmTokenTypeTable.TABLE_NAME, "updatenmtokentype", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "moveRecDown");
	}
}
