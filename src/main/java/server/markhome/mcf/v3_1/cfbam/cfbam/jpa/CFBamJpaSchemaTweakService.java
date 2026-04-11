// Description: Java 25 Spring JPA Service for SchemaTweak

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;
import server.markhome.mcf.v3_1.cfint.cfint.*;
import server.markhome.mcf.v3_1.cfbam.cfbam.*;

/**
 *	Service for the CFBamSchemaTweak entities defined in server.markhome.mcf.v3_1.cfbam.cfbam.jpa
 *	using the CFBamSchemaTweakRepository to access them.
 */
@Service("cfbam31JpaSchemaTweakService")
public class CFBamJpaSchemaTweakService {

	@Autowired
	@Qualifier("cfbam31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfbam31EntityManagerFactory;

	@Autowired
	private CFBamJpaSchemaTweakRepository cfbam31SchemaTweakRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFBamJpaSchemaTweak, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public CFBamJpaSchemaTweak create(CFBamJpaSchemaTweak data) {
		final String S_ProcName = "create";
		if (data == null) {
			return( null );
		}
		CFLibDbKeyHash256 originalRequiredId = data.getRequiredId();
		boolean generatedRequiredId = false;
		if(data.getRequiredTenantId() == null || data.getRequiredTenantId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredTenantId");
		}
		if(data.getRequiredScopeId() == null || data.getRequiredScopeId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredScopeId");
		}
		if(data.getRequiredName() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredName");
		}
		if(data.getRequiredTweakGelText() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredTweakGelText");
		}
		if(data.getRequiredSchemaDefId() == null || data.getRequiredSchemaDefId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredSchemaDefId");
		}
		try {
			if (data.getRequiredId() == null || data.getRequiredId().isNull()) {
				data.setRequiredId(new CFLibDbKeyHash256(0));
				generatedRequiredId = true;
			}
			LocalDateTime now = LocalDateTime.now();
			data.setCreatedAt(now);
			data.setUpdatedAt(now);
			if(data.getPKey() != null && cfbam31SchemaTweakRepository.existsById((CFLibDbKeyHash256)data.getPKey())) {
				return( (CFBamJpaSchemaTweak)(cfbam31SchemaTweakRepository.findById((CFLibDbKeyHash256)(data.getPKey())).get()));
			}
			return cfbam31SchemaTweakRepository.save(data);
		}
		catch(Exception ex) {
				if(generatedRequiredId) {
					data.setRequiredId(originalRequiredId);
				}
			throw new CFLibDbException(getClass(),
				S_ProcName,
				ex);
		}
	}

	/**
	 *	Update an existing entity.
	 *
	 *		@param	data	The entity to be updated.
	 *
	 *		@return The updated entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public CFBamJpaSchemaTweak update(CFBamJpaSchemaTweak data) {
		final String S_ProcName = "update";
		if (data == null) {
			return( null );
		}
		if (data.getPKey() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.getPKey()");
		}
		if(data.getRequiredTenantId() == null || data.getRequiredTenantId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredTenantId");
		}
		if(data.getRequiredScopeId() == null || data.getRequiredScopeId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredScopeId");
		}
		if(data.getRequiredName() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredName");
		}
		if(data.getRequiredTweakGelText() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredTweakGelText");
		}
		if(data.getRequiredSchemaDefId() == null || data.getRequiredSchemaDefId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredSchemaDefId");
		}
		// Ensure the entity exists and that the revision matches
		CFBamJpaSchemaTweak existing = cfbam31SchemaTweakRepository.findById((CFLibDbKeyHash256)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFBamTweak to existing object
		existing.setRequiredContainerScopeDef(data.getRequiredContainerScopeDef());
		existing.setOptionalLookupDefSchema(data.getOptionalLookupDefSchema());
		// Apply data columns of CFBamTweak to existing object
		existing.setRequiredTenantId(data.getRequiredTenantId());
		existing.setOptionalDefSchemaTenantId(data.getOptionalDefSchemaTenantId());
		existing.setRequiredName(data.getRequiredName());
		existing.setRequiredReplacesInherited(data.getRequiredReplacesInherited());
		existing.setRequiredTweakGelText(data.getRequiredTweakGelText());
		// Apply superior data relationships of CFBamSchemaTweak to existing object
		existing.setRequiredContainerSchemaDef(data.getRequiredContainerSchemaDef());
		// Apply data columns of CFBamSchemaTweak to existing object
		// Update the audit columns
		data.setUpdatedAt(LocalDateTime.now());
		// Save the changes we've made
		return cfbam31SchemaTweakRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredId
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public CFBamJpaSchemaTweak find(@Param("id") CFLibDbKeyHash256 requiredId) {
		return( cfbam31SchemaTweakRepository.get(requiredId));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public List<CFBamJpaSchemaTweak> findAll() {
		return( cfbam31SchemaTweakRepository.findAll() );
	}

	// CFBamTweak specified index finders

	/**
	 *	Find an entity using the columns of the ICFBamTweakByUNameIdxKey as arguments.
	 *
	 *		@param requiredScopeId
	 *		@param requiredName
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public CFBamJpaSchemaTweak findByUNameIdx(@Param("scopeId") CFLibDbKeyHash256 requiredScopeId,
		@Param("name") String requiredName) {
		return( cfbam31SchemaTweakRepository.findByUNameIdx(requiredScopeId,
			requiredName));
	}

	/**
	 *	ICFBamTweakByUNameIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFBamTweakByUNameIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public CFBamJpaSchemaTweak findByUNameIdx(ICFBamTweakByUNameIdxKey key) {
		return( cfbam31SchemaTweakRepository.findByUNameIdx(key.getRequiredScopeId(), key.getRequiredName()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFBamTweakByValTentIdxKey as arguments.
	 *
	 *		@param requiredTenantId
	 *
	 *		@return List&lt;CFBamJpaSchemaTweak&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public List<CFBamJpaSchemaTweak> findByValTentIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId) {
		return( cfbam31SchemaTweakRepository.findByValTentIdx(requiredTenantId));
	}

	/**
	 *	ICFBamTweakByValTentIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFBamTweakByValTentIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public List<CFBamJpaSchemaTweak> findByValTentIdx(ICFBamTweakByValTentIdxKey key) {
		return( cfbam31SchemaTweakRepository.findByValTentIdx(key.getRequiredTenantId()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFBamTweakByScopeIdxKey as arguments.
	 *
	 *		@param requiredScopeId
	 *
	 *		@return List&lt;CFBamJpaSchemaTweak&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public List<CFBamJpaSchemaTweak> findByScopeIdx(@Param("scopeId") CFLibDbKeyHash256 requiredScopeId) {
		return( cfbam31SchemaTweakRepository.findByScopeIdx(requiredScopeId));
	}

	/**
	 *	ICFBamTweakByScopeIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFBamTweakByScopeIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public List<CFBamJpaSchemaTweak> findByScopeIdx(ICFBamTweakByScopeIdxKey key) {
		return( cfbam31SchemaTweakRepository.findByScopeIdx(key.getRequiredScopeId()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFBamTweakByDefSchemaIdxKey as arguments.
	 *
	 *		@param optionalDefSchemaId
	 *
	 *		@return List&lt;CFBamJpaSchemaTweak&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public List<CFBamJpaSchemaTweak> findByDefSchemaIdx(@Param("defSchemaId") CFLibDbKeyHash256 optionalDefSchemaId) {
		return( cfbam31SchemaTweakRepository.findByDefSchemaIdx(optionalDefSchemaId));
	}

	/**
	 *	ICFBamTweakByDefSchemaIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFBamTweakByDefSchemaIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public List<CFBamJpaSchemaTweak> findByDefSchemaIdx(ICFBamTweakByDefSchemaIdxKey key) {
		return( cfbam31SchemaTweakRepository.findByDefSchemaIdx(key.getOptionalDefSchemaId()));
	}

	/**
	 *	Find an entity using the columns of the ICFBamTweakByUDefIdxKey as arguments.
	 *
	 *		@param requiredTenantId
	 *		@param requiredScopeId
	 *		@param optionalDefSchemaTenantId
	 *		@param optionalDefSchemaId
	 *		@param requiredName
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public CFBamJpaSchemaTweak findByUDefIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId,
		@Param("scopeId") CFLibDbKeyHash256 requiredScopeId,
		@Param("defSchemaTenantId") CFLibDbKeyHash256 optionalDefSchemaTenantId,
		@Param("defSchemaId") CFLibDbKeyHash256 optionalDefSchemaId,
		@Param("name") String requiredName) {
		return( cfbam31SchemaTweakRepository.findByUDefIdx(requiredTenantId,
			requiredScopeId,
			optionalDefSchemaTenantId,
			optionalDefSchemaId,
			requiredName));
	}

	/**
	 *	ICFBamTweakByUDefIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFBamTweakByUDefIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public CFBamJpaSchemaTweak findByUDefIdx(ICFBamTweakByUDefIdxKey key) {
		return( cfbam31SchemaTweakRepository.findByUDefIdx(key.getRequiredTenantId(), key.getRequiredScopeId(), key.getOptionalDefSchemaTenantId(), key.getOptionalDefSchemaId(), key.getRequiredName()));
	}

	// CFBamSchemaTweak specified index finders

	/**
	 *	Find zero or more entities into a List using the columns of the ICFBamSchemaTweakBySchemaIdxKey as arguments.
	 *
	 *		@param requiredSchemaDefId
	 *
	 *		@return List&lt;CFBamJpaSchemaTweak&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public List<CFBamJpaSchemaTweak> findBySchemaIdx(@Param("schemaDefId") CFLibDbKeyHash256 requiredSchemaDefId) {
		return( cfbam31SchemaTweakRepository.findBySchemaIdx(requiredSchemaDefId));
	}

	/**
	 *	ICFBamSchemaTweakBySchemaIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFBamSchemaTweakBySchemaIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public List<CFBamJpaSchemaTweak> findBySchemaIdx(ICFBamSchemaTweakBySchemaIdxKey key) {
		return( cfbam31SchemaTweakRepository.findBySchemaIdx(key.getRequiredSchemaDefId()));
	}

	// CFBamTweak specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public CFBamJpaSchemaTweak lockByIdIdx(@Param("id") CFLibDbKeyHash256 requiredId) {
		return( cfbam31SchemaTweakRepository.lockByIdIdx(requiredId));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredScopeId
	 *		@param requiredName
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public CFBamJpaSchemaTweak lockByUNameIdx(@Param("scopeId") CFLibDbKeyHash256 requiredScopeId,
		@Param("name") String requiredName) {
		return( cfbam31SchemaTweakRepository.lockByUNameIdx(requiredScopeId,
			requiredName));
	}

	/**
	 *	ICFBamTweakByUNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public CFBamJpaSchemaTweak lockByUNameIdx(ICFBamTweakByUNameIdxKey key) {
		return( cfbam31SchemaTweakRepository.lockByUNameIdx(key.getRequiredScopeId(), key.getRequiredName()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public List<CFBamJpaSchemaTweak> lockByValTentIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId) {
		return( cfbam31SchemaTweakRepository.lockByValTentIdx(requiredTenantId));
	}

	/**
	 *	ICFBamTweakByValTentIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public List<CFBamJpaSchemaTweak> lockByValTentIdx(ICFBamTweakByValTentIdxKey key) {
		return( cfbam31SchemaTweakRepository.lockByValTentIdx(key.getRequiredTenantId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredScopeId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public List<CFBamJpaSchemaTweak> lockByScopeIdx(@Param("scopeId") CFLibDbKeyHash256 requiredScopeId) {
		return( cfbam31SchemaTweakRepository.lockByScopeIdx(requiredScopeId));
	}

	/**
	 *	ICFBamTweakByScopeIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public List<CFBamJpaSchemaTweak> lockByScopeIdx(ICFBamTweakByScopeIdxKey key) {
		return( cfbam31SchemaTweakRepository.lockByScopeIdx(key.getRequiredScopeId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param optionalDefSchemaId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public List<CFBamJpaSchemaTweak> lockByDefSchemaIdx(@Param("defSchemaId") CFLibDbKeyHash256 optionalDefSchemaId) {
		return( cfbam31SchemaTweakRepository.lockByDefSchemaIdx(optionalDefSchemaId));
	}

	/**
	 *	ICFBamTweakByDefSchemaIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public List<CFBamJpaSchemaTweak> lockByDefSchemaIdx(ICFBamTweakByDefSchemaIdxKey key) {
		return( cfbam31SchemaTweakRepository.lockByDefSchemaIdx(key.getOptionalDefSchemaId()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 *		@param requiredScopeId
	 *		@param optionalDefSchemaTenantId
	 *		@param optionalDefSchemaId
	 *		@param requiredName
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public CFBamJpaSchemaTweak lockByUDefIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId,
		@Param("scopeId") CFLibDbKeyHash256 requiredScopeId,
		@Param("defSchemaTenantId") CFLibDbKeyHash256 optionalDefSchemaTenantId,
		@Param("defSchemaId") CFLibDbKeyHash256 optionalDefSchemaId,
		@Param("name") String requiredName) {
		return( cfbam31SchemaTweakRepository.lockByUDefIdx(requiredTenantId,
			requiredScopeId,
			optionalDefSchemaTenantId,
			optionalDefSchemaId,
			requiredName));
	}

	/**
	 *	ICFBamTweakByUDefIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public CFBamJpaSchemaTweak lockByUDefIdx(ICFBamTweakByUDefIdxKey key) {
		return( cfbam31SchemaTweakRepository.lockByUDefIdx(key.getRequiredTenantId(), key.getRequiredScopeId(), key.getOptionalDefSchemaTenantId(), key.getOptionalDefSchemaId(), key.getRequiredName()));
	}

	// CFBamSchemaTweak specified lock-by-index methods

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSchemaDefId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public List<CFBamJpaSchemaTweak> lockBySchemaIdx(@Param("schemaDefId") CFLibDbKeyHash256 requiredSchemaDefId) {
		return( cfbam31SchemaTweakRepository.lockBySchemaIdx(requiredSchemaDefId));
	}

	/**
	 *	ICFBamSchemaTweakBySchemaIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public List<CFBamJpaSchemaTweak> lockBySchemaIdx(ICFBamSchemaTweakBySchemaIdxKey key) {
		return( cfbam31SchemaTweakRepository.lockBySchemaIdx(key.getRequiredSchemaDefId()));
	}

	// CFBamTweak specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public void deleteByIdIdx(@Param("id") CFLibDbKeyHash256 requiredId) {
		cfbam31SchemaTweakRepository.deleteByIdIdx(requiredId);
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredScopeId
	 *		@param requiredName
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public void deleteByUNameIdx(@Param("scopeId") CFLibDbKeyHash256 requiredScopeId,
		@Param("name") String requiredName) {
		cfbam31SchemaTweakRepository.deleteByUNameIdx(requiredScopeId,
			requiredName);
	}

	/**
	 *	ICFBamTweakByUNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFBamTweakByUNameIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public void deleteByUNameIdx(ICFBamTweakByUNameIdxKey key) {
		cfbam31SchemaTweakRepository.deleteByUNameIdx(key.getRequiredScopeId(), key.getRequiredName());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public void deleteByValTentIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId) {
		cfbam31SchemaTweakRepository.deleteByValTentIdx(requiredTenantId);
	}

	/**
	 *	ICFBamTweakByValTentIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFBamTweakByValTentIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public void deleteByValTentIdx(ICFBamTweakByValTentIdxKey key) {
		cfbam31SchemaTweakRepository.deleteByValTentIdx(key.getRequiredTenantId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredScopeId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public void deleteByScopeIdx(@Param("scopeId") CFLibDbKeyHash256 requiredScopeId) {
		cfbam31SchemaTweakRepository.deleteByScopeIdx(requiredScopeId);
	}

	/**
	 *	ICFBamTweakByScopeIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFBamTweakByScopeIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public void deleteByScopeIdx(ICFBamTweakByScopeIdxKey key) {
		cfbam31SchemaTweakRepository.deleteByScopeIdx(key.getRequiredScopeId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param optionalDefSchemaId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public void deleteByDefSchemaIdx(@Param("defSchemaId") CFLibDbKeyHash256 optionalDefSchemaId) {
		cfbam31SchemaTweakRepository.deleteByDefSchemaIdx(optionalDefSchemaId);
	}

	/**
	 *	ICFBamTweakByDefSchemaIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFBamTweakByDefSchemaIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public void deleteByDefSchemaIdx(ICFBamTweakByDefSchemaIdxKey key) {
		cfbam31SchemaTweakRepository.deleteByDefSchemaIdx(key.getOptionalDefSchemaId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 *		@param requiredScopeId
	 *		@param optionalDefSchemaTenantId
	 *		@param optionalDefSchemaId
	 *		@param requiredName
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public void deleteByUDefIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId,
		@Param("scopeId") CFLibDbKeyHash256 requiredScopeId,
		@Param("defSchemaTenantId") CFLibDbKeyHash256 optionalDefSchemaTenantId,
		@Param("defSchemaId") CFLibDbKeyHash256 optionalDefSchemaId,
		@Param("name") String requiredName) {
		cfbam31SchemaTweakRepository.deleteByUDefIdx(requiredTenantId,
			requiredScopeId,
			optionalDefSchemaTenantId,
			optionalDefSchemaId,
			requiredName);
	}

	/**
	 *	ICFBamTweakByUDefIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFBamTweakByUDefIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public void deleteByUDefIdx(ICFBamTweakByUDefIdxKey key) {
		cfbam31SchemaTweakRepository.deleteByUDefIdx(key.getRequiredTenantId(), key.getRequiredScopeId(), key.getOptionalDefSchemaTenantId(), key.getOptionalDefSchemaId(), key.getRequiredName());
	}

	// CFBamSchemaTweak specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSchemaDefId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public void deleteBySchemaIdx(@Param("schemaDefId") CFLibDbKeyHash256 requiredSchemaDefId) {
		cfbam31SchemaTweakRepository.deleteBySchemaIdx(requiredSchemaDefId);
	}

	/**
	 *	ICFBamSchemaTweakBySchemaIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFBamSchemaTweakBySchemaIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public void deleteBySchemaIdx(ICFBamSchemaTweakBySchemaIdxKey key) {
		cfbam31SchemaTweakRepository.deleteBySchemaIdx(key.getRequiredSchemaDefId());
	}
}
