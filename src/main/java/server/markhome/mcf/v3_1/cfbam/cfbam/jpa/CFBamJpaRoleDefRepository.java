// Description: Java 25 Spring JPA Repository for RoleDef

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

import java.io.Serializable;
import java.math.*;
import java.time.*;
import java.util.*;
import jakarta.persistence.*;
import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cflib.xml.CFLibXmlUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;
import server.markhome.mcf.v3_1.cfint.cfint.*;
import server.markhome.mcf.v3_1.cfbam.cfbam.*;

/**
 *	JpaRepository for the CFBamJpaRoleDef entities defined in server.markhome.mcf.v3_1.cfbam.cfbam.jpa.
 *	The manufactured repositories try to provide a rich, do-it-all interface to the JPA data store, with both object and argument-based implementations of the interface defined.
 */
@Transactional(readOnly = true)
public interface CFBamJpaRoleDefRepository extends JpaRepository<CFBamJpaRoleDef, CFLibDbKeyHash256> {

	/**
	 *	Argument-based get database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredId
	 *
	 *		@return The retrieved entity, usually from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFBamJpaRoleDef r where r.requiredId = :id")
	CFBamJpaRoleDef get(@Param("id") CFLibDbKeyHash256 requiredId);

	// CFBamJpaRoleDef specified index readers

	/**
	 *	Read an entity using the columns of the CFBamRoleDefByUNameIdxKey as arguments.
	 *
	 *		@param requiredScopeId
	 *		@param requiredName
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFBamJpaRoleDef r where r.requiredContainerScopeDef.requiredId = :scopeId and r.requiredName = :name")
	CFBamJpaRoleDef findByUNameIdx(@Param("scopeId") CFLibDbKeyHash256 requiredScopeId,
		@Param("name") String requiredName);

	/**
	 *	CFBamRoleDefByUNameIdxKey entity reader convenience method for object-based access.
	 *
	 *		@param key The CFBamRoleDefByUNameIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	default CFBamJpaRoleDef findByUNameIdx(ICFBamRoleDefByUNameIdxKey key) {
		return( findByUNameIdx(key.getRequiredScopeId(), key.getRequiredName()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFBamRoleDefByScopeIdxKey as arguments.
	 *
	 *		@param requiredScopeId
	 *
	 *		@return List&lt;CFBamJpaRoleDef&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFBamJpaRoleDef r where r.requiredContainerScopeDef.requiredId = :scopeId")
	List<CFBamJpaRoleDef> findByScopeIdx(@Param("scopeId") CFLibDbKeyHash256 requiredScopeId);

	/**
	 *	CFBamRoleDefByScopeIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFBamRoleDefByScopeIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFBamJpaRoleDef> findByScopeIdx(ICFBamRoleDefByScopeIdxKey key) {
		return( findByScopeIdx(key.getRequiredScopeId()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFBamRoleDefByDefSchemaIdxKey as arguments.
	 *
	 *		@param optionalDefSchemaId
	 *
	 *		@return List&lt;CFBamJpaRoleDef&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFBamJpaRoleDef r where r.optionalLookupDefSchema.requiredId = :defSchemaId")
	List<CFBamJpaRoleDef> findByDefSchemaIdx(@Param("defSchemaId") CFLibDbKeyHash256 optionalDefSchemaId);

	/**
	 *	CFBamRoleDefByDefSchemaIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFBamRoleDefByDefSchemaIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFBamJpaRoleDef> findByDefSchemaIdx(ICFBamRoleDefByDefSchemaIdxKey key) {
		return( findByDefSchemaIdx(key.getOptionalDefSchemaId()));
	}

	/**
	 *	Read an entity using the columns of the CFBamRoleDefByUDefIdxKey as arguments.
	 *
	 *		@param requiredScopeId
	 *		@param optionalDefSchemaId
	 *		@param requiredName
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFBamJpaRoleDef r where r.requiredContainerScopeDef.requiredId = :scopeId and r.optionalLookupDefSchema.requiredId = :defSchemaId and r.requiredName = :name")
	CFBamJpaRoleDef findByUDefIdx(@Param("scopeId") CFLibDbKeyHash256 requiredScopeId,
		@Param("defSchemaId") CFLibDbKeyHash256 optionalDefSchemaId,
		@Param("name") String requiredName);

	/**
	 *	CFBamRoleDefByUDefIdxKey entity reader convenience method for object-based access.
	 *
	 *		@param key The CFBamRoleDefByUDefIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	default CFBamJpaRoleDef findByUDefIdx(ICFBamRoleDefByUDefIdxKey key) {
		return( findByUDefIdx(key.getRequiredScopeId(), key.getOptionalDefSchemaId(), key.getRequiredName()));
	}

	// CFBamJpaRoleDef specified delete-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFBamJpaRoleDef r where r.requiredId = :id")
	CFBamJpaRoleDef lockByIdIdx(@Param("id") CFLibDbKeyHash256 requiredId);

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredScopeId
	 *		@param requiredName
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFBamJpaRoleDef r where r.requiredContainerScopeDef.requiredId = :scopeId and r.requiredName = :name")
	CFBamJpaRoleDef lockByUNameIdx(@Param("scopeId") CFLibDbKeyHash256 requiredScopeId,
		@Param("name") String requiredName);

	/**
	 *	CFBamRoleDefByUNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFBamJpaRoleDef lockByUNameIdx(ICFBamRoleDefByUNameIdxKey key) {
		return( lockByUNameIdx(key.getRequiredScopeId(), key.getRequiredName()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredScopeId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFBamJpaRoleDef r where r.requiredContainerScopeDef.requiredId = :scopeId")
	List<CFBamJpaRoleDef> lockByScopeIdx(@Param("scopeId") CFLibDbKeyHash256 requiredScopeId);

	/**
	 *	CFBamRoleDefByScopeIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFBamJpaRoleDef> lockByScopeIdx(ICFBamRoleDefByScopeIdxKey key) {
		return( lockByScopeIdx(key.getRequiredScopeId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param optionalDefSchemaId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFBamJpaRoleDef r where r.optionalLookupDefSchema.requiredId = :defSchemaId")
	List<CFBamJpaRoleDef> lockByDefSchemaIdx(@Param("defSchemaId") CFLibDbKeyHash256 optionalDefSchemaId);

	/**
	 *	CFBamRoleDefByDefSchemaIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFBamJpaRoleDef> lockByDefSchemaIdx(ICFBamRoleDefByDefSchemaIdxKey key) {
		return( lockByDefSchemaIdx(key.getOptionalDefSchemaId()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredScopeId
	 *		@param optionalDefSchemaId
	 *		@param requiredName
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFBamJpaRoleDef r where r.requiredContainerScopeDef.requiredId = :scopeId and r.optionalLookupDefSchema.requiredId = :defSchemaId and r.requiredName = :name")
	CFBamJpaRoleDef lockByUDefIdx(@Param("scopeId") CFLibDbKeyHash256 requiredScopeId,
		@Param("defSchemaId") CFLibDbKeyHash256 optionalDefSchemaId,
		@Param("name") String requiredName);

	/**
	 *	CFBamRoleDefByUDefIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFBamJpaRoleDef lockByUDefIdx(ICFBamRoleDefByUDefIdxKey key) {
		return( lockByUDefIdx(key.getRequiredScopeId(), key.getOptionalDefSchemaId(), key.getRequiredName()));
	}

	// CFBamJpaRoleDef specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFBamJpaRoleDef r where r.requiredId = :id")
	void deleteByIdIdx(@Param("id") CFLibDbKeyHash256 requiredId);

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredScopeId
	 *		@param requiredName
	 */
	@Transactional
	@Modifying
	@Query("delete from CFBamJpaRoleDef r where r.requiredContainerScopeDef.requiredId = :scopeId and r.requiredName = :name")
	void deleteByUNameIdx(@Param("scopeId") CFLibDbKeyHash256 requiredScopeId,
		@Param("name") String requiredName);

	/**
	 *	CFBamRoleDefByUNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFBamRoleDefByUNameIdxKey of the entity to be locked.
	 */
	default void deleteByUNameIdx(ICFBamRoleDefByUNameIdxKey key) {
		deleteByUNameIdx(key.getRequiredScopeId(), key.getRequiredName());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredScopeId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFBamJpaRoleDef r where r.requiredContainerScopeDef.requiredId = :scopeId")
	void deleteByScopeIdx(@Param("scopeId") CFLibDbKeyHash256 requiredScopeId);

	/**
	 *	CFBamRoleDefByScopeIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFBamRoleDefByScopeIdxKey of the entity to be locked.
	 */
	default void deleteByScopeIdx(ICFBamRoleDefByScopeIdxKey key) {
		deleteByScopeIdx(key.getRequiredScopeId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param optionalDefSchemaId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFBamJpaRoleDef r where r.optionalLookupDefSchema.requiredId = :defSchemaId")
	void deleteByDefSchemaIdx(@Param("defSchemaId") CFLibDbKeyHash256 optionalDefSchemaId);

	/**
	 *	CFBamRoleDefByDefSchemaIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFBamRoleDefByDefSchemaIdxKey of the entity to be locked.
	 */
	default void deleteByDefSchemaIdx(ICFBamRoleDefByDefSchemaIdxKey key) {
		deleteByDefSchemaIdx(key.getOptionalDefSchemaId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredScopeId
	 *		@param optionalDefSchemaId
	 *		@param requiredName
	 */
	@Transactional
	@Modifying
	@Query("delete from CFBamJpaRoleDef r where r.requiredContainerScopeDef.requiredId = :scopeId and r.optionalLookupDefSchema.requiredId = :defSchemaId and r.requiredName = :name")
	void deleteByUDefIdx(@Param("scopeId") CFLibDbKeyHash256 requiredScopeId,
		@Param("defSchemaId") CFLibDbKeyHash256 optionalDefSchemaId,
		@Param("name") String requiredName);

	/**
	 *	CFBamRoleDefByUDefIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFBamRoleDefByUDefIdxKey of the entity to be locked.
	 */
	default void deleteByUDefIdx(ICFBamRoleDefByUDefIdxKey key) {
		deleteByUDefIdx(key.getRequiredScopeId(), key.getOptionalDefSchemaId(), key.getRequiredName());
	}

}
