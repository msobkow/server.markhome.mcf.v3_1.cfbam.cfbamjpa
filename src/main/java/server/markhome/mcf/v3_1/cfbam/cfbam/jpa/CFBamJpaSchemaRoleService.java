// Description: Java 25 Spring JPA Service for SchemaRole

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
 *	Service for the CFBamSchemaRole entities defined in server.markhome.mcf.v3_1.cfbam.cfbam.jpa
 *	using the CFBamSchemaRoleRepository to access them.
 */
@Service("cfbam31JpaSchemaRoleService")
public class CFBamJpaSchemaRoleService {

	@Autowired
	@Qualifier("cfbam31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfbam31EntityManagerFactory;

	@Autowired
	private CFBamJpaSchemaRoleRepository cfbam31SchemaRoleRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFBamJpaSchemaRole, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public CFBamJpaSchemaRole create(CFBamJpaSchemaRole data) {
		final String S_ProcName = "create";
		if (data == null) {
			return( null );
		}
		CFLibDbKeyHash256 originalRequiredId = data.getRequiredId();
		boolean generatedRequiredId = false;
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
		if(data.getRequiredMembershipString() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredMembershipString");
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
			if(data.getPKey() != null && cfbam31SchemaRoleRepository.existsById((CFLibDbKeyHash256)data.getPKey())) {
				return( (CFBamJpaSchemaRole)(cfbam31SchemaRoleRepository.findById((CFLibDbKeyHash256)(data.getPKey())).get()));
			}
			return cfbam31SchemaRoleRepository.save(data);
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
	public CFBamJpaSchemaRole update(CFBamJpaSchemaRole data) {
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
		if(data.getRequiredMembershipString() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredMembershipString");
		}
		if(data.getRequiredSchemaDefId() == null || data.getRequiredSchemaDefId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredSchemaDefId");
		}
		// Ensure the entity exists and that the revision matches
		CFBamJpaSchemaRole existing = cfbam31SchemaRoleRepository.findById((CFLibDbKeyHash256)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFBamRoleDef to existing object
		existing.setRequiredContainerScopeDef(data.getRequiredContainerScopeDef());
		existing.setOptionalLookupDefSchema(data.getOptionalLookupDefSchema());
		// Apply data columns of CFBamRoleDef to existing object
		existing.setRequiredName(data.getRequiredName());
		existing.setRequiredMembershipString(data.getRequiredMembershipString());
		// Apply superior data relationships of CFBamSchemaRole to existing object
		existing.setRequiredContainerSchemaDef(data.getRequiredContainerSchemaDef());
		// Apply data columns of CFBamSchemaRole to existing object
		// Update the audit columns
		data.setUpdatedAt(LocalDateTime.now());
		// Save the changes we've made
		return cfbam31SchemaRoleRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredId
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public CFBamJpaSchemaRole find(@Param("id") CFLibDbKeyHash256 requiredId) {
		return( cfbam31SchemaRoleRepository.get(requiredId));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public List<CFBamJpaSchemaRole> findAll() {
		return( cfbam31SchemaRoleRepository.findAll() );
	}

	// CFBamRoleDef specified index finders

	/**
	 *	Find an entity using the columns of the ICFBamRoleDefByUNameIdxKey as arguments.
	 *
	 *		@param requiredScopeId
	 *		@param requiredName
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public CFBamJpaSchemaRole findByUNameIdx(@Param("scopeId") CFLibDbKeyHash256 requiredScopeId,
		@Param("name") String requiredName) {
		return( cfbam31SchemaRoleRepository.findByUNameIdx(requiredScopeId,
			requiredName));
	}

	/**
	 *	ICFBamRoleDefByUNameIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFBamRoleDefByUNameIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public CFBamJpaSchemaRole findByUNameIdx(ICFBamRoleDefByUNameIdxKey key) {
		return( cfbam31SchemaRoleRepository.findByUNameIdx(key.getRequiredScopeId(), key.getRequiredName()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFBamRoleDefByScopeIdxKey as arguments.
	 *
	 *		@param requiredScopeId
	 *
	 *		@return List&lt;CFBamJpaSchemaRole&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public List<CFBamJpaSchemaRole> findByScopeIdx(@Param("scopeId") CFLibDbKeyHash256 requiredScopeId) {
		return( cfbam31SchemaRoleRepository.findByScopeIdx(requiredScopeId));
	}

	/**
	 *	ICFBamRoleDefByScopeIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFBamRoleDefByScopeIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public List<CFBamJpaSchemaRole> findByScopeIdx(ICFBamRoleDefByScopeIdxKey key) {
		return( cfbam31SchemaRoleRepository.findByScopeIdx(key.getRequiredScopeId()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFBamRoleDefByDefSchemaIdxKey as arguments.
	 *
	 *		@param optionalDefSchemaId
	 *
	 *		@return List&lt;CFBamJpaSchemaRole&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public List<CFBamJpaSchemaRole> findByDefSchemaIdx(@Param("defSchemaId") CFLibDbKeyHash256 optionalDefSchemaId) {
		return( cfbam31SchemaRoleRepository.findByDefSchemaIdx(optionalDefSchemaId));
	}

	/**
	 *	ICFBamRoleDefByDefSchemaIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFBamRoleDefByDefSchemaIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public List<CFBamJpaSchemaRole> findByDefSchemaIdx(ICFBamRoleDefByDefSchemaIdxKey key) {
		return( cfbam31SchemaRoleRepository.findByDefSchemaIdx(key.getOptionalDefSchemaId()));
	}

	/**
	 *	Find an entity using the columns of the ICFBamRoleDefByUDefIdxKey as arguments.
	 *
	 *		@param requiredScopeId
	 *		@param optionalDefSchemaId
	 *		@param requiredName
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public CFBamJpaSchemaRole findByUDefIdx(@Param("scopeId") CFLibDbKeyHash256 requiredScopeId,
		@Param("defSchemaId") CFLibDbKeyHash256 optionalDefSchemaId,
		@Param("name") String requiredName) {
		return( cfbam31SchemaRoleRepository.findByUDefIdx(requiredScopeId,
			optionalDefSchemaId,
			requiredName));
	}

	/**
	 *	ICFBamRoleDefByUDefIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFBamRoleDefByUDefIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public CFBamJpaSchemaRole findByUDefIdx(ICFBamRoleDefByUDefIdxKey key) {
		return( cfbam31SchemaRoleRepository.findByUDefIdx(key.getRequiredScopeId(), key.getOptionalDefSchemaId(), key.getRequiredName()));
	}

	// CFBamSchemaRole specified index finders

	/**
	 *	Find zero or more entities into a List using the columns of the ICFBamSchemaRoleBySchemaIdxKey as arguments.
	 *
	 *		@param requiredSchemaDefId
	 *
	 *		@return List&lt;CFBamJpaSchemaRole&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public List<CFBamJpaSchemaRole> findBySchemaIdx(@Param("schemaDefId") CFLibDbKeyHash256 requiredSchemaDefId) {
		return( cfbam31SchemaRoleRepository.findBySchemaIdx(requiredSchemaDefId));
	}

	/**
	 *	ICFBamSchemaRoleBySchemaIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFBamSchemaRoleBySchemaIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public List<CFBamJpaSchemaRole> findBySchemaIdx(ICFBamSchemaRoleBySchemaIdxKey key) {
		return( cfbam31SchemaRoleRepository.findBySchemaIdx(key.getRequiredSchemaDefId()));
	}

	// CFBamRoleDef specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public CFBamJpaSchemaRole lockByIdIdx(@Param("id") CFLibDbKeyHash256 requiredId) {
		return( cfbam31SchemaRoleRepository.lockByIdIdx(requiredId));
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
	public CFBamJpaSchemaRole lockByUNameIdx(@Param("scopeId") CFLibDbKeyHash256 requiredScopeId,
		@Param("name") String requiredName) {
		return( cfbam31SchemaRoleRepository.lockByUNameIdx(requiredScopeId,
			requiredName));
	}

	/**
	 *	ICFBamRoleDefByUNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public CFBamJpaSchemaRole lockByUNameIdx(ICFBamRoleDefByUNameIdxKey key) {
		return( cfbam31SchemaRoleRepository.lockByUNameIdx(key.getRequiredScopeId(), key.getRequiredName()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredScopeId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public List<CFBamJpaSchemaRole> lockByScopeIdx(@Param("scopeId") CFLibDbKeyHash256 requiredScopeId) {
		return( cfbam31SchemaRoleRepository.lockByScopeIdx(requiredScopeId));
	}

	/**
	 *	ICFBamRoleDefByScopeIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public List<CFBamJpaSchemaRole> lockByScopeIdx(ICFBamRoleDefByScopeIdxKey key) {
		return( cfbam31SchemaRoleRepository.lockByScopeIdx(key.getRequiredScopeId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param optionalDefSchemaId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public List<CFBamJpaSchemaRole> lockByDefSchemaIdx(@Param("defSchemaId") CFLibDbKeyHash256 optionalDefSchemaId) {
		return( cfbam31SchemaRoleRepository.lockByDefSchemaIdx(optionalDefSchemaId));
	}

	/**
	 *	ICFBamRoleDefByDefSchemaIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public List<CFBamJpaSchemaRole> lockByDefSchemaIdx(ICFBamRoleDefByDefSchemaIdxKey key) {
		return( cfbam31SchemaRoleRepository.lockByDefSchemaIdx(key.getOptionalDefSchemaId()));
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
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public CFBamJpaSchemaRole lockByUDefIdx(@Param("scopeId") CFLibDbKeyHash256 requiredScopeId,
		@Param("defSchemaId") CFLibDbKeyHash256 optionalDefSchemaId,
		@Param("name") String requiredName) {
		return( cfbam31SchemaRoleRepository.lockByUDefIdx(requiredScopeId,
			optionalDefSchemaId,
			requiredName));
	}

	/**
	 *	ICFBamRoleDefByUDefIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public CFBamJpaSchemaRole lockByUDefIdx(ICFBamRoleDefByUDefIdxKey key) {
		return( cfbam31SchemaRoleRepository.lockByUDefIdx(key.getRequiredScopeId(), key.getOptionalDefSchemaId(), key.getRequiredName()));
	}

	// CFBamSchemaRole specified lock-by-index methods

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSchemaDefId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public List<CFBamJpaSchemaRole> lockBySchemaIdx(@Param("schemaDefId") CFLibDbKeyHash256 requiredSchemaDefId) {
		return( cfbam31SchemaRoleRepository.lockBySchemaIdx(requiredSchemaDefId));
	}

	/**
	 *	ICFBamSchemaRoleBySchemaIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public List<CFBamJpaSchemaRole> lockBySchemaIdx(ICFBamSchemaRoleBySchemaIdxKey key) {
		return( cfbam31SchemaRoleRepository.lockBySchemaIdx(key.getRequiredSchemaDefId()));
	}

	// CFBamRoleDef specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public void deleteByIdIdx(@Param("id") CFLibDbKeyHash256 requiredId) {
		cfbam31SchemaRoleRepository.deleteByIdIdx(requiredId);
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
		cfbam31SchemaRoleRepository.deleteByUNameIdx(requiredScopeId,
			requiredName);
	}

	/**
	 *	ICFBamRoleDefByUNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFBamRoleDefByUNameIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public void deleteByUNameIdx(ICFBamRoleDefByUNameIdxKey key) {
		cfbam31SchemaRoleRepository.deleteByUNameIdx(key.getRequiredScopeId(), key.getRequiredName());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredScopeId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public void deleteByScopeIdx(@Param("scopeId") CFLibDbKeyHash256 requiredScopeId) {
		cfbam31SchemaRoleRepository.deleteByScopeIdx(requiredScopeId);
	}

	/**
	 *	ICFBamRoleDefByScopeIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFBamRoleDefByScopeIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public void deleteByScopeIdx(ICFBamRoleDefByScopeIdxKey key) {
		cfbam31SchemaRoleRepository.deleteByScopeIdx(key.getRequiredScopeId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param optionalDefSchemaId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public void deleteByDefSchemaIdx(@Param("defSchemaId") CFLibDbKeyHash256 optionalDefSchemaId) {
		cfbam31SchemaRoleRepository.deleteByDefSchemaIdx(optionalDefSchemaId);
	}

	/**
	 *	ICFBamRoleDefByDefSchemaIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFBamRoleDefByDefSchemaIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public void deleteByDefSchemaIdx(ICFBamRoleDefByDefSchemaIdxKey key) {
		cfbam31SchemaRoleRepository.deleteByDefSchemaIdx(key.getOptionalDefSchemaId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredScopeId
	 *		@param optionalDefSchemaId
	 *		@param requiredName
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public void deleteByUDefIdx(@Param("scopeId") CFLibDbKeyHash256 requiredScopeId,
		@Param("defSchemaId") CFLibDbKeyHash256 optionalDefSchemaId,
		@Param("name") String requiredName) {
		cfbam31SchemaRoleRepository.deleteByUDefIdx(requiredScopeId,
			optionalDefSchemaId,
			requiredName);
	}

	/**
	 *	ICFBamRoleDefByUDefIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFBamRoleDefByUDefIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public void deleteByUDefIdx(ICFBamRoleDefByUDefIdxKey key) {
		cfbam31SchemaRoleRepository.deleteByUDefIdx(key.getRequiredScopeId(), key.getOptionalDefSchemaId(), key.getRequiredName());
	}

	// CFBamSchemaRole specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSchemaDefId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public void deleteBySchemaIdx(@Param("schemaDefId") CFLibDbKeyHash256 requiredSchemaDefId) {
		cfbam31SchemaRoleRepository.deleteBySchemaIdx(requiredSchemaDefId);
	}

	/**
	 *	ICFBamSchemaRoleBySchemaIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFBamSchemaRoleBySchemaIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfbam31TransactionManager")
	public void deleteBySchemaIdx(ICFBamSchemaRoleBySchemaIdxKey key) {
		cfbam31SchemaRoleRepository.deleteBySchemaIdx(key.getRequiredSchemaDefId());
	}

}
