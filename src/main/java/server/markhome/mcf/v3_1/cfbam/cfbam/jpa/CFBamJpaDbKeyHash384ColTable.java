
// Description: Java 25 DbIO implementation for DbKeyHash384Col.

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
 *	CFBamJpaDbKeyHash384ColTable database implementation for DbKeyHash384Col
 */
public class CFBamJpaDbKeyHash384ColTable implements ICFBamDbKeyHash384ColTable
{
	protected CFBamJpaSchema schema;


	public CFBamJpaDbKeyHash384ColTable(ICFBamSchema schema) {
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

	protected boolean canCreateDbKeyHash384Col(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), Authorization.getSecClusterId(), Authorization.getSecTenantId(), "createdbkeyhash384col");
		}
		return( permissionGranted );
	}

	protected boolean canReadDbKeyHash384Col(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), Authorization.getSecClusterId(), Authorization.getSecTenantId(), "readdbkeyhash384col");
		}
		return( permissionGranted );
	}

	protected boolean canUpdateDbKeyHash384Col(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), ICFSecSchema.getSysClusterId(), ICFSecSchema.getSysTenantId(), "updatedbkeyhash384col");
		}
		return( permissionGranted );
	}

	protected boolean canDeleteDbKeyHash384Col(String S_ProcName, ICFSecAuthorization Authorization) {
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
			permissionGranted = ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), ICFSecSchema.getSysClusterId(), ICFSecSchema.getSysTenantId(), "deletedbkeyhash384col");
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
	public ICFBamDbKeyHash384Col createDbKeyHash384Col( ICFSecAuthorization Authorization,
		ICFBamDbKeyHash384Col rec )
	{
		final String S_ProcName = "createDbKeyHash384Col";
		boolean permissionGranted = canCreateDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "createdbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createDbKeyHash384Col", 1, "rec");
		}
		else if (rec instanceof CFBamJpaDbKeyHash384Col) {
			CFBamJpaDbKeyHash384Col jparec = (CFBamJpaDbKeyHash384Col)rec;
			CFBamJpaDbKeyHash384Col retval = schema.getJpaHooksSchema().getDbKeyHash384ColService().create(jparec);
		if(retval != null) {
			// Retrieve the TenantId from retval and check ICFSec.backingSchema().isMemberOfTenantGroup(auth,ClusterId,TenantId,'readdbkeyhash384col'), clear retval to null if not a member
			CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
			CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readdbkeyhash384col")) {
				retval = null;
			}
		}
		return( retval );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createDbKeyHash384Col", "rec", rec, "CFBamJpaDbKeyHash384Col");
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
	public ICFBamDbKeyHash384Col updateDbKeyHash384Col( ICFSecAuthorization Authorization,
		ICFBamDbKeyHash384Col rec )
	{
		final String S_ProcName = "updateDbKeyHash384Col";
		boolean permissionGranted = canUpdateDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "updatedbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateDbKeyHash384Col", 1, "rec");
		}
		else if (rec instanceof CFBamJpaDbKeyHash384Col) {
			CFBamJpaDbKeyHash384Col jparec = (CFBamJpaDbKeyHash384Col)rec;
			CFBamJpaDbKeyHash384Col retval = schema.getJpaHooksSchema().getDbKeyHash384ColService().update(jparec);
		if(retval != null) {
			// Retrieve the TenantId from retval and check ICFSec.backingSchema().isMemberOfTenantGroup(auth,ClusterId,TenantId,'readdbkeyhash384col'), clear retval to null if not a member
			CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
			CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readdbkeyhash384col")) {
				retval = null;
			}
		}
		return( retval );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateDbKeyHash384Col", "rec", rec, "CFBamJpaDbKeyHash384Col");
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
	public void deleteDbKeyHash384Col( ICFSecAuthorization Authorization,
		ICFBamDbKeyHash384Col rec )
	{
		final String S_ProcName = "deleteDbKeyHash384Col";
		boolean permissionGranted = canDeleteDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "deletedbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		if (rec == null) {
			return;
		}
		if (rec instanceof CFBamJpaDbKeyHash384Col) {
			CFBamJpaDbKeyHash384Col jparec = (CFBamJpaDbKeyHash384Col)rec;
			schema.getJpaHooksSchema().getDbKeyHash384ColService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteDbKeyHash384Col", "rec", rec, "CFBamJpaDbKeyHash384Col");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteDbKeyHash384Col");
	}

	/**
	 *	Delete the DbKeyHash384Col instances identified by the key TableIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TableId	The DbKeyHash384Col key attribute of the instance generating the id.
	 */
	@Override
	public void deleteDbKeyHash384ColByTableIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTableId )
	{
		final String S_ProcName = "deleteDbKeyHash384ColByTableIdx";
		boolean permissionGranted = canDeleteDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "deletedbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getDbKeyHash384ColService().deleteByTableIdx(argTableId);
	}


	/**
	 *	Delete the DbKeyHash384Col instances identified by the key TableIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteDbKeyHash384ColByTableIdx( ICFSecAuthorization Authorization,
		ICFBamDbKeyHash384ColByTableIdxKey argKey )
	{
		final String S_ProcName = "deleteDbKeyHash384ColByTableIdx";
		boolean permissionGranted = canDeleteDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "deletedbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getDbKeyHash384ColService().deleteByTableIdx(argKey.getRequiredTableId());
	}

	/**
	 *	Delete the DbKeyHash384Col instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteDbKeyHash384ColByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argKey )
	{
		final String S_ProcName = "deleteDbKeyHash384ColByIdIdx";
		boolean permissionGranted = canDeleteDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "deletedbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getDbKeyHash384ColService().deleteByIdIdx(argKey);
	}

	/**
	 *	Delete the DbKeyHash384Col instances identified by the key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The DbKeyHash384Col key attribute of the instance generating the id.
	 *
	 *	@param	Name	The DbKeyHash384Col key attribute of the instance generating the id.
	 */
	@Override
	public void deleteDbKeyHash384ColByUNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId,
		String argName )
	{
		final String S_ProcName = "deleteDbKeyHash384ColByUNameIdx";
		boolean permissionGranted = canDeleteDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "deletedbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getDbKeyHash384ColService().deleteByUNameIdx(argScopeId,
		argName);
	}


	/**
	 *	Delete the DbKeyHash384Col instances identified by the key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteDbKeyHash384ColByUNameIdx( ICFSecAuthorization Authorization,
		ICFBamValueByUNameIdxKey argKey )
	{
		final String S_ProcName = "deleteDbKeyHash384ColByUNameIdx";
		boolean permissionGranted = canDeleteDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "deletedbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getDbKeyHash384ColService().deleteByUNameIdx(argKey.getRequiredScopeId(),
			argKey.getRequiredName());
	}

	/**
	 *	Delete the DbKeyHash384Col instances identified by the key ScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The DbKeyHash384Col key attribute of the instance generating the id.
	 */
	@Override
	public void deleteDbKeyHash384ColByScopeIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId )
	{
		final String S_ProcName = "deleteDbKeyHash384ColByScopeIdx";
		boolean permissionGranted = canDeleteDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "deletedbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getDbKeyHash384ColService().deleteByScopeIdx(argScopeId);
	}


	/**
	 *	Delete the DbKeyHash384Col instances identified by the key ScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteDbKeyHash384ColByScopeIdx( ICFSecAuthorization Authorization,
		ICFBamValueByScopeIdxKey argKey )
	{
		final String S_ProcName = "deleteDbKeyHash384ColByScopeIdx";
		boolean permissionGranted = canDeleteDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "deletedbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getDbKeyHash384ColService().deleteByScopeIdx(argKey.getRequiredScopeId());
	}

	/**
	 *	Delete the DbKeyHash384Col instances identified by the key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DefSchemaId	The DbKeyHash384Col key attribute of the instance generating the id.
	 */
	@Override
	public void deleteDbKeyHash384ColByDefSchemaIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argDefSchemaId )
	{
		final String S_ProcName = "deleteDbKeyHash384ColByDefSchemaIdx";
		boolean permissionGranted = canDeleteDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "deletedbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getDbKeyHash384ColService().deleteByDefSchemaIdx(argDefSchemaId);
	}


	/**
	 *	Delete the DbKeyHash384Col instances identified by the key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteDbKeyHash384ColByDefSchemaIdx( ICFSecAuthorization Authorization,
		ICFBamValueByDefSchemaIdxKey argKey )
	{
		final String S_ProcName = "deleteDbKeyHash384ColByDefSchemaIdx";
		boolean permissionGranted = canDeleteDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "deletedbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getDbKeyHash384ColService().deleteByDefSchemaIdx(argKey.getOptionalDefSchemaId());
	}

	/**
	 *	Delete the DbKeyHash384Col instances identified by the key PrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PrevId	The DbKeyHash384Col key attribute of the instance generating the id.
	 */
	@Override
	public void deleteDbKeyHash384ColByPrevIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argPrevId )
	{
		final String S_ProcName = "deleteDbKeyHash384ColByPrevIdx";
		boolean permissionGranted = canDeleteDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "deletedbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getDbKeyHash384ColService().deleteByPrevIdx(argPrevId);
	}


	/**
	 *	Delete the DbKeyHash384Col instances identified by the key PrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteDbKeyHash384ColByPrevIdx( ICFSecAuthorization Authorization,
		ICFBamValueByPrevIdxKey argKey )
	{
		final String S_ProcName = "deleteDbKeyHash384ColByPrevIdx";
		boolean permissionGranted = canDeleteDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "deletedbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getDbKeyHash384ColService().deleteByPrevIdx(argKey.getOptionalPrevId());
	}

	/**
	 *	Delete the DbKeyHash384Col instances identified by the key NextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	NextId	The DbKeyHash384Col key attribute of the instance generating the id.
	 */
	@Override
	public void deleteDbKeyHash384ColByNextIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argNextId )
	{
		final String S_ProcName = "deleteDbKeyHash384ColByNextIdx";
		boolean permissionGranted = canDeleteDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "deletedbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getDbKeyHash384ColService().deleteByNextIdx(argNextId);
	}


	/**
	 *	Delete the DbKeyHash384Col instances identified by the key NextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteDbKeyHash384ColByNextIdx( ICFSecAuthorization Authorization,
		ICFBamValueByNextIdxKey argKey )
	{
		final String S_ProcName = "deleteDbKeyHash384ColByNextIdx";
		boolean permissionGranted = canDeleteDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "deletedbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getDbKeyHash384ColService().deleteByNextIdx(argKey.getOptionalNextId());
	}

	/**
	 *	Delete the DbKeyHash384Col instances identified by the key ContPrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The DbKeyHash384Col key attribute of the instance generating the id.
	 *
	 *	@param	PrevId	The DbKeyHash384Col key attribute of the instance generating the id.
	 */
	@Override
	public void deleteDbKeyHash384ColByContPrevIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId,
		CFLibDbKeyHash256 argPrevId )
	{
		final String S_ProcName = "deleteDbKeyHash384ColByContPrevIdx";
		boolean permissionGranted = canDeleteDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "deletedbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getDbKeyHash384ColService().deleteByContPrevIdx(argScopeId,
		argPrevId);
	}


	/**
	 *	Delete the DbKeyHash384Col instances identified by the key ContPrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteDbKeyHash384ColByContPrevIdx( ICFSecAuthorization Authorization,
		ICFBamValueByContPrevIdxKey argKey )
	{
		final String S_ProcName = "deleteDbKeyHash384ColByContPrevIdx";
		boolean permissionGranted = canDeleteDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "deletedbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getDbKeyHash384ColService().deleteByContPrevIdx(argKey.getRequiredScopeId(),
			argKey.getOptionalPrevId());
	}

	/**
	 *	Delete the DbKeyHash384Col instances identified by the key ContNextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The DbKeyHash384Col key attribute of the instance generating the id.
	 *
	 *	@param	NextId	The DbKeyHash384Col key attribute of the instance generating the id.
	 */
	@Override
	public void deleteDbKeyHash384ColByContNextIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId,
		CFLibDbKeyHash256 argNextId )
	{
		final String S_ProcName = "deleteDbKeyHash384ColByContNextIdx";
		boolean permissionGranted = canDeleteDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "deletedbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getDbKeyHash384ColService().deleteByContNextIdx(argScopeId,
		argNextId);
	}


	/**
	 *	Delete the DbKeyHash384Col instances identified by the key ContNextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteDbKeyHash384ColByContNextIdx( ICFSecAuthorization Authorization,
		ICFBamValueByContNextIdxKey argKey )
	{
		final String S_ProcName = "deleteDbKeyHash384ColByContNextIdx";
		boolean permissionGranted = canDeleteDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "deletedbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		schema.getJpaHooksSchema().getDbKeyHash384ColService().deleteByContNextIdx(argKey.getRequiredScopeId(),
			argKey.getOptionalNextId());
	}


	/**
	 *	Read the derived DbKeyHash384Col record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the DbKeyHash384Col instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamDbKeyHash384Col readDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		final String S_ProcName = "readDerived";
		boolean permissionGranted = canReadDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "readdbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFBamDbKeyHash384Col retval = schema.getJpaHooksSchema().getDbKeyHash384ColService().find(PKey);
		if(retval != null) {
			// Retrieve the TenantId from retval and check ICFSec.backingSchema().isMemberOfTenantGroup(auth,ClusterId,TenantId,'readdbkeyhash384col'), clear retval to null if not a member
			CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
			CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readdbkeyhash384col")) {
				retval = null;
			}
		}
		return( retval );
	}

	/**
	 *	Lock the derived DbKeyHash384Col record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the DbKeyHash384Col instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamDbKeyHash384Col lockDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		final String S_ProcName = "lockDerived";
		boolean permissionGranted = canUpdateDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "updatedbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFBamDbKeyHash384Col retval = schema.getJpaHooksSchema().getDbKeyHash384ColService().lockByIdIdx(PKey);
		if(retval != null) {
			// Retrieve the TenantId from retval and check ICFSec.backingSchema().isMemberOfTenantGroup(auth,ClusterId,TenantId,'readdbkeyhash384col'), clear retval to null if not a member
			CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
			CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readdbkeyhash384col")) {
				retval = null;
			}
		}
		return( retval );
	}

	/**
	 *	Read all DbKeyHash384Col instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamDbKeyHash384Col[] readAllDerived( ICFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllDerived";
		boolean permissionGranted = canReadDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "readdbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaDbKeyHash384Col> retlist = schema.getJpaHooksSchema().getDbKeyHash384ColService().findAll();
		if(retlist != null) {
			ArrayList<CFBamJpaDbKeyHash384Col> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readdbkeyhash384col")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamDbKeyHash384Col[] retset = new ICFBamDbKeyHash384Col[retlist.size()];
		int idx = 0;
		for (CFBamJpaDbKeyHash384Col cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived DbKeyHash384Col record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Id	The DbKeyHash384Col key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamDbKeyHash384Col readDerivedByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argId )
	{
		final String S_ProcName = "readDerivedByIdIdx";
		boolean permissionGranted = canReadDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "readdbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFBamDbKeyHash384Col retval = schema.getJpaHooksSchema().getDbKeyHash384ColService().find(argId);
		if(retval != null) {
			// Retrieve the TenantId from retval and check ICFSec.backingSchema().isMemberOfTenantGroup(auth,ClusterId,TenantId,'readdbkeyhash384col'), clear retval to null if not a member
			CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
			CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readdbkeyhash384col")) {
				retval = null;
			}
		}
		return( retval );
	}

	/**
	 *	Read the derived DbKeyHash384Col record instance identified by the unique key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The DbKeyHash384Col key attribute of the instance generating the id.
	 *
	 *	@param	Name	The DbKeyHash384Col key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFBamDbKeyHash384Col readDerivedByUNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId,
		String argName )
	{
		final String S_ProcName = "readDerivedByUNameIdx";
		boolean permissionGranted = canReadDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "readdbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFBamDbKeyHash384Col retval = schema.getJpaHooksSchema().getDbKeyHash384ColService().findByUNameIdx(argScopeId,
		argName);
		if(retval != null) {
			// Retrieve the TenantId from retval and check ICFSec.backingSchema().isMemberOfTenantGroup(auth,ClusterId,TenantId,'readdbkeyhash384col'), clear retval to null if not a member
			CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
			CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
			if (!ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readdbkeyhash384col")) {
				retval = null;
			}
		}
		return( retval );
	}

	/**
	 *	Read an array of the derived DbKeyHash384Col record instances identified by the duplicate key ScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The DbKeyHash384Col key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamDbKeyHash384Col[] readDerivedByScopeIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId )
	{
		final String S_ProcName = "readDerivedByScopeIdx";
		boolean permissionGranted = canReadDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "readdbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaDbKeyHash384Col> retlist = schema.getJpaHooksSchema().getDbKeyHash384ColService().findByScopeIdx(argScopeId);
		if(retlist != null) {
			ArrayList<CFBamJpaDbKeyHash384Col> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readdbkeyhash384col")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamDbKeyHash384Col[] retset = new ICFBamDbKeyHash384Col[retlist.size()];
		int idx = 0;
		for (CFBamJpaDbKeyHash384Col cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived DbKeyHash384Col record instances identified by the duplicate key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DefSchemaId	The DbKeyHash384Col key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamDbKeyHash384Col[] readDerivedByDefSchemaIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argDefSchemaId )
	{
		final String S_ProcName = "readDerivedByDefSchemaIdx";
		boolean permissionGranted = canReadDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "readdbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaDbKeyHash384Col> retlist = schema.getJpaHooksSchema().getDbKeyHash384ColService().findByDefSchemaIdx(argDefSchemaId);
		if(retlist != null) {
			ArrayList<CFBamJpaDbKeyHash384Col> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readdbkeyhash384col")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamDbKeyHash384Col[] retset = new ICFBamDbKeyHash384Col[retlist.size()];
		int idx = 0;
		for (CFBamJpaDbKeyHash384Col cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived DbKeyHash384Col record instances identified by the duplicate key PrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PrevId	The DbKeyHash384Col key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamDbKeyHash384Col[] readDerivedByPrevIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argPrevId )
	{
		final String S_ProcName = "readDerivedByPrevIdx";
		boolean permissionGranted = canReadDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "readdbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaDbKeyHash384Col> retlist = schema.getJpaHooksSchema().getDbKeyHash384ColService().findByPrevIdx(argPrevId);
		if(retlist != null) {
			ArrayList<CFBamJpaDbKeyHash384Col> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readdbkeyhash384col")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamDbKeyHash384Col[] retset = new ICFBamDbKeyHash384Col[retlist.size()];
		int idx = 0;
		for (CFBamJpaDbKeyHash384Col cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived DbKeyHash384Col record instances identified by the duplicate key NextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	NextId	The DbKeyHash384Col key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamDbKeyHash384Col[] readDerivedByNextIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argNextId )
	{
		final String S_ProcName = "readDerivedByNextIdx";
		boolean permissionGranted = canReadDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "readdbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaDbKeyHash384Col> retlist = schema.getJpaHooksSchema().getDbKeyHash384ColService().findByNextIdx(argNextId);
		if(retlist != null) {
			ArrayList<CFBamJpaDbKeyHash384Col> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readdbkeyhash384col")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamDbKeyHash384Col[] retset = new ICFBamDbKeyHash384Col[retlist.size()];
		int idx = 0;
		for (CFBamJpaDbKeyHash384Col cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived DbKeyHash384Col record instances identified by the duplicate key ContPrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The DbKeyHash384Col key attribute of the instance generating the id.
	 *
	 *	@param	PrevId	The DbKeyHash384Col key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamDbKeyHash384Col[] readDerivedByContPrevIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId,
		CFLibDbKeyHash256 argPrevId )
	{
		final String S_ProcName = "readDerivedByContPrevIdx";
		boolean permissionGranted = canReadDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "readdbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaDbKeyHash384Col> retlist = schema.getJpaHooksSchema().getDbKeyHash384ColService().findByContPrevIdx(argScopeId,
		argPrevId);
		if(retlist != null) {
			ArrayList<CFBamJpaDbKeyHash384Col> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readdbkeyhash384col")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamDbKeyHash384Col[] retset = new ICFBamDbKeyHash384Col[retlist.size()];
		int idx = 0;
		for (CFBamJpaDbKeyHash384Col cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived DbKeyHash384Col record instances identified by the duplicate key ContNextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The DbKeyHash384Col key attribute of the instance generating the id.
	 *
	 *	@param	NextId	The DbKeyHash384Col key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamDbKeyHash384Col[] readDerivedByContNextIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId,
		CFLibDbKeyHash256 argNextId )
	{
		final String S_ProcName = "readDerivedByContNextIdx";
		boolean permissionGranted = canReadDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "readdbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaDbKeyHash384Col> retlist = schema.getJpaHooksSchema().getDbKeyHash384ColService().findByContNextIdx(argScopeId,
		argNextId);
		if(retlist != null) {
			ArrayList<CFBamJpaDbKeyHash384Col> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readdbkeyhash384col")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamDbKeyHash384Col[] retset = new ICFBamDbKeyHash384Col[retlist.size()];
		int idx = 0;
		for (CFBamJpaDbKeyHash384Col cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived DbKeyHash384Col record instances identified by the duplicate key TableIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TableId	The DbKeyHash384Col key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFBamDbKeyHash384Col[] readDerivedByTableIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTableId )
	{
		final String S_ProcName = "readDerivedByTableIdx";
		boolean permissionGranted = canReadDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "readdbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		List<CFBamJpaDbKeyHash384Col> retlist = schema.getJpaHooksSchema().getDbKeyHash384ColService().findByTableIdx(argTableId);
		if(retlist != null) {
			ArrayList<CFBamJpaDbKeyHash384Col> finallist = new ArrayList<>();
			for (var retval: retlist) {
				if(retval != null) {
					CFLibDbKeyHash256 effClusterId = CFLibDbKeyHash256.nullGet();
					CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
					if (ICFSecSchema.getSecurityService().isMemberOfTenantGroup(Authorization.getSecUserId(), effClusterId, effTenantId, "readdbkeyhash384col")) {
						finallist.add(retval);
					}
				}
			}
			retlist = finallist;
		}
		ICFBamDbKeyHash384Col[] retset = new ICFBamDbKeyHash384Col[retlist.size()];
		int idx = 0;
		for (CFBamJpaDbKeyHash384Col cur: retlist) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the specific DbKeyHash384Col record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the DbKeyHash384Col instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamDbKeyHash384Col readRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		final String S_ProcName = "readRec";
		boolean permissionGranted = canReadDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "readdbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Lock the specific DbKeyHash384Col record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the DbKeyHash384Col instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamDbKeyHash384Col lockRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		final String S_ProcName = "lockRec";
		boolean permissionGranted = canUpdateDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "updatedbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific DbKeyHash384Col record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific DbKeyHash384Col instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFBamDbKeyHash384Col[] readAllRec( ICFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllRec";
		boolean permissionGranted = canReadDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "readdbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read the specific DbKeyHash384Col record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Id	The DbKeyHash384Col key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamDbKeyHash384Col readRecByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argId )
	{
		final String S_ProcName = "readRecByIdIdx";
		boolean permissionGranted = canReadDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "readdbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read the specific DbKeyHash384Col record instance identified by the unique key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The DbKeyHash384Col key attribute of the instance generating the id.
	 *
	 *	@param	Name	The DbKeyHash384Col key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamDbKeyHash384Col readRecByUNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId,
		String argName )
	{
		final String S_ProcName = "readRecByUNameIdx";
		boolean permissionGranted = canReadDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "readdbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByUNameIdx");
	}

	/**
	 *	Read an array of the specific DbKeyHash384Col record instances identified by the duplicate key ScopeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The DbKeyHash384Col key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamDbKeyHash384Col[] readRecByScopeIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId )
	{
		final String S_ProcName = "readRecByScopeIdx";
		boolean permissionGranted = canReadDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "readdbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByScopeIdx");
	}

	/**
	 *	Read an array of the specific DbKeyHash384Col record instances identified by the duplicate key DefSchemaIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DefSchemaId	The DbKeyHash384Col key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamDbKeyHash384Col[] readRecByDefSchemaIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argDefSchemaId )
	{
		final String S_ProcName = "readRecByDefSchemaIdx";
		boolean permissionGranted = canReadDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "readdbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByDefSchemaIdx");
	}

	/**
	 *	Read an array of the specific DbKeyHash384Col record instances identified by the duplicate key PrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PrevId	The DbKeyHash384Col key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamDbKeyHash384Col[] readRecByPrevIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argPrevId )
	{
		final String S_ProcName = "readRecByPrevIdx";
		boolean permissionGranted = canReadDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "readdbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByPrevIdx");
	}

	/**
	 *	Read an array of the specific DbKeyHash384Col record instances identified by the duplicate key NextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	NextId	The DbKeyHash384Col key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamDbKeyHash384Col[] readRecByNextIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argNextId )
	{
		final String S_ProcName = "readRecByNextIdx";
		boolean permissionGranted = canReadDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "readdbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByNextIdx");
	}

	/**
	 *	Read an array of the specific DbKeyHash384Col record instances identified by the duplicate key ContPrevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The DbKeyHash384Col key attribute of the instance generating the id.
	 *
	 *	@param	PrevId	The DbKeyHash384Col key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamDbKeyHash384Col[] readRecByContPrevIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId,
		CFLibDbKeyHash256 argPrevId )
	{
		final String S_ProcName = "readRecByContPrevIdx";
		boolean permissionGranted = canReadDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "readdbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByContPrevIdx");
	}

	/**
	 *	Read an array of the specific DbKeyHash384Col record instances identified by the duplicate key ContNextIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ScopeId	The DbKeyHash384Col key attribute of the instance generating the id.
	 *
	 *	@param	NextId	The DbKeyHash384Col key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamDbKeyHash384Col[] readRecByContNextIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argScopeId,
		CFLibDbKeyHash256 argNextId )
	{
		final String S_ProcName = "readRecByContNextIdx";
		boolean permissionGranted = canReadDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "readdbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByContNextIdx");
	}

	/**
	 *	Read an array of the specific DbKeyHash384Col record instances identified by the duplicate key TableIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TableId	The DbKeyHash384Col key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFBamDbKeyHash384Col[] readRecByTableIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTableId )
	{
		final String S_ProcName = "readRecByTableIdx";
		boolean permissionGranted = canReadDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "readdbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "readRecByTableIdx");
	}

	/**
	 *	Move the specified record up in the chain (i.e. to the previous position.)
	 *
	 *	@return	The refreshed record after it has been moved
	 */
	@Override
	public ICFBamDbKeyHash384Col moveRecUp( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argId,
		int revision )
	{
		final String S_ProcName = "moveRecUp";
		boolean permissionGranted = canUpdateDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "updatedbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "moveRecUp");
	}

	/**
	 *	Move the specified record down in the chain (i.e. to the next position.)
	 *
	 *	@return	The refreshed record after it has been moved
	 */
	@Override
	public ICFBamDbKeyHash384Col moveRecDown( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argId,
		int revision )
	{
		final String S_ProcName = "moveRecDown";
		boolean permissionGranted = canUpdateDbKeyHash384Col(S_ProcName, Authorization);
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFBamSchema.SCHEMA_NAME, ICFBamDbKeyHash384ColTable.TABLE_NAME, "updatedbkeyhash384col", Authorization.getAuthUuid6().toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		throw new CFLibNotImplementedYetException(getClass(), "moveRecDown");
	}
}
