// Description: Java 25 JPA implementation of a RoleDef entity definition object.

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
import jakarta.transaction.Transactional;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;
import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cflib.xml.CFLibXmlUtil;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;
import server.markhome.mcf.v3_1.cfint.cfint.*;
import server.markhome.mcf.v3_1.cfbam.cfbam.*;
import server.markhome.mcf.v3_1.cfsec.cfsec.jpa.*;
import server.markhome.mcf.v3_1.cfint.cfint.jpa.*;

@Entity
@Table(
	name = "roledef", schema = "CFBam31",
	indexes = {
		@Index(name = "RoleDefIdIdx", columnList = "Id", unique = true),
		@Index(name = "RoleDefUNameIdx", columnList = "ScopeId, safe_name", unique = true),
		@Index(name = "RoleDefScopeIdx", columnList = "ScopeId", unique = false),
		@Index(name = "RoleDefDefSchemaDefIdx", columnList = "defschid", unique = false),
		@Index(name = "RoleDefUDefIdx", columnList = "ScopeId, defschid, safe_name", unique = true)
	}
)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "class_code", discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue("43150")
@Transactional(Transactional.TxType.SUPPORTS)
@PersistenceContext(unitName = "CFBamPU")
public class CFBamJpaRoleDef
	implements Comparable<Object>,
		ICFBamRoleDef,
		Serializable
{
	@Id
	@AttributeOverrides({
		@AttributeOverride(name="bytes", column = @Column( name="Id", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 requiredId;
	protected int requiredRevision;

	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn( name="ScopeId" )
	protected CFBamJpaScope requiredContainerScopeDef;
	@ManyToOne(fetch=FetchType.LAZY, optional=true)
	@JoinColumn( name="defschid" )
	protected CFBamJpaSchemaDef optionalLookupDefSchema;

	@AttributeOverrides({
		@AttributeOverride( name="bytes", column = @Column( name="CreatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 createdByUserId = CFLibDbKeyHash256.fromHex(ICFBamRoleDef.S_INIT_CREATED_BY);

	@Column(name="CreatedAt", nullable=false)
	protected LocalDateTime createdAt = LocalDateTime.now();

	@AttributeOverrides({
		@AttributeOverride( name="bytes", column= @Column( name="UpdatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 updatedByUserId = CFLibDbKeyHash256.fromHex(ICFBamRoleDef.S_INIT_UPDATED_BY);

	@Column(name="UpdatedAt", nullable=false)
	protected LocalDateTime updatedAt = LocalDateTime.now();
	@Column( name="safe_name", nullable=false, length=192 )
	protected String requiredName;
	@Column( name="member_str", nullable=false, length=2000000 )
	protected String requiredMembershipString;

	public CFBamJpaRoleDef() {
		requiredId = CFLibDbKeyHash256.fromHex( ICFBamRoleDef.ID_INIT_VALUE.toString() );
		requiredName = ICFBamRoleDef.NAME_INIT_VALUE;
		requiredMembershipString = ICFBamRoleDef.MEMBERSHIPSTRING_INIT_VALUE;
	}

	@Override
	public int getClassCode() {
		return( ICFBamRoleDef.CLASS_CODE );
	}

	@Override
	public ICFBamScope getRequiredContainerScopeDef() {
		return( requiredContainerScopeDef );
	}
	@Override
	public void setRequiredContainerScopeDef(ICFBamScope argObj) {
		if(argObj == null) {
			throw new CFLibNullArgumentException(getClass(), "setContainerScopeDef", 1, "argObj");
		}
		else if (argObj instanceof CFBamJpaScope) {
			requiredContainerScopeDef = (CFBamJpaScope)argObj;
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "setContainerScopeDef", "argObj", argObj, "CFBamJpaScope");
		}
	}

	@Override
	public void setRequiredContainerScopeDef(CFLibDbKeyHash256 argScopeId) {
		ICFBamSchema targetBackingSchema = ICFBamSchema.getBackingCFBam();
		if (targetBackingSchema == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredContainerScopeDef", 0, "ICFBamSchema.getBackingCFBam()");
		}
		ICFBamScopeTable targetTable = targetBackingSchema.getTableScope();
		if (targetTable == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredContainerScopeDef", 0, "ICFBamSchema.getBackingCFBam().getTableScope()");
		}
		ICFBamScope targetRec = targetTable.readDerived(null, argScopeId);
		setRequiredContainerScopeDef(targetRec);
	}

	@Override
	public ICFBamSchemaDef getOptionalLookupDefSchema() {
		return( optionalLookupDefSchema );
	}
	@Override
	public void setOptionalLookupDefSchema(ICFBamSchemaDef argObj) {
		if(argObj == null) {
			optionalLookupDefSchema = null;
		}
		else if (argObj instanceof CFBamJpaSchemaDef) {
			optionalLookupDefSchema = (CFBamJpaSchemaDef)argObj;
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "setLookupDefSchema", "argObj", argObj, "CFBamJpaSchemaDef");
		}
	}

	@Override
	public void setOptionalLookupDefSchema(CFLibDbKeyHash256 argDefSchemaId) {
		ICFBamSchema targetBackingSchema = ICFBamSchema.getBackingCFBam();
		if (targetBackingSchema == null) {
			throw new CFLibNullArgumentException(getClass(), "setOptionalLookupDefSchema", 0, "ICFBamSchema.getBackingCFBam()");
		}
		ICFBamSchemaDefTable targetTable = targetBackingSchema.getTableSchemaDef();
		if (targetTable == null) {
			throw new CFLibNullArgumentException(getClass(), "setOptionalLookupDefSchema", 0, "ICFBamSchema.getBackingCFBam().getTableSchemaDef()");
		}
		ICFBamSchemaDef targetRec = targetTable.readDerived(null, argDefSchemaId);
		setOptionalLookupDefSchema(targetRec);
	}

	@Override
	public CFLibDbKeyHash256 getCreatedByUserId() {
		return( createdByUserId );
	}

	@Override
	public void setCreatedByUserId( CFLibDbKeyHash256 value ) {
		if (value == null || value.isNull()) {
			throw new CFLibNullArgumentException(getClass(), "setCreatedByUserId", 1, "value");
		}
		createdByUserId = value;
	}

	@Override
	public LocalDateTime getCreatedAt() {
		return( createdAt );
	}

	@Override
	public void setCreatedAt( LocalDateTime value ) {
		if (value == null) {
			throw new CFLibNullArgumentException(getClass(), "setCreatedAt", 1, "value");
		}
		createdAt = value;
	}

	@Override
	public CFLibDbKeyHash256 getUpdatedByUserId() {
		return( updatedByUserId );
	}

	@Override
	public void setUpdatedByUserId( CFLibDbKeyHash256 value ) {
		if (value == null || value.isNull()) {
			throw new CFLibNullArgumentException(getClass(), "setUpdatedByUserId", 1, "value");
		}
		updatedByUserId = value;
	}

	@Override
	public LocalDateTime getUpdatedAt() {
		return( updatedAt );
	}

	@Override
	public void setUpdatedAt( LocalDateTime value ) {
		if (value == null) {
			throw new CFLibNullArgumentException(getClass(), "setUpdatedAt", 1, "value");
		}
		updatedAt = value;
	}

	@Override
	public CFLibDbKeyHash256 getPKey() {
		return getRequiredId();
	}

	@Override
	public void setPKey(CFLibDbKeyHash256 requiredId) {
		this.requiredId = requiredId;
	}
	
	@Override
	public CFLibDbKeyHash256 getRequiredId() {
		return( requiredId );
	}

	@Override
	public void setRequiredId( CFLibDbKeyHash256 value ) {
		if( value == null || value.isNull() ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredId",
				1,
				"value" );
		}
		requiredId = value;
	}

	
	@Override
	public int getRequiredRevision() {
		return( requiredRevision );
	}

	@Override
	public void setRequiredRevision( int value ) {
		requiredRevision = value;
	}

	@Override
	public CFLibDbKeyHash256 getRequiredScopeId() {
		ICFBamScope result = getRequiredContainerScopeDef();
		if (result != null) {
			return result.getRequiredId();
		}
		else {
			return( ICFBamScope.ID_INIT_VALUE );
		}
	}

	@Override
	public CFLibDbKeyHash256 getOptionalDefSchemaId() {
		ICFBamSchemaDef result = getOptionalLookupDefSchema();
		if (result != null) {
			return result.getRequiredId();
		}
		else {
			return null;
		}
	}

	@Override
	public String getRequiredName() {
		return( requiredName );
	}

	@Override
	public void setRequiredName( String value ) {
		if( value == null ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredName",
				1,
				"value" );
		}
		else if( value.length() > 192 ) {
			throw new CFLibArgumentOverflowException( getClass(),
				"setRequiredName",
				1,
				"value.length()",
				value.length(),
				192 );
		}
		requiredName = value;
	}

	@Override
	public String getRequiredMembershipString() {
		return( requiredMembershipString );
	}

	@Override
	public void setRequiredMembershipString( String value ) {
		if( value == null ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredMembershipString",
				1,
				"value" );
		}
		else if( value.length() > 2000000 ) {
			throw new CFLibArgumentOverflowException( getClass(),
				"setRequiredMembershipString",
				1,
				"value.length()",
				value.length(),
				2000000 );
		}
		requiredMembershipString = value;
	}

	@Override
	public boolean equals( Object obj ) {
		if (obj == null) {
			return( false );
		}
		else if (obj instanceof ICFBamRoleDef) {
			ICFBamRoleDef rhs = (ICFBamRoleDef)obj;
			if( ! getCreatedByUserId().equals( rhs.getCreatedByUserId() ) ) {
				return( false );
			}
			if( ! getCreatedAt().equals( rhs.getCreatedAt() ) ) {
				return( false );
			}
			if( ! getUpdatedByUserId().equals( rhs.getUpdatedByUserId() ) ) {
				return( false );
			}
			if( ! getUpdatedAt().equals( rhs.getUpdatedAt() ) ) {
				return( false );
			}
			if( getRequiredScopeId() != null ) {
				if( rhs.getRequiredScopeId() != null ) {
					if( ! getRequiredScopeId().equals( rhs.getRequiredScopeId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredScopeId() != null ) {
					return( false );
				}
			}
			if( getRequiredId() != null ) {
				if( rhs.getRequiredId() != null ) {
					if( ! getRequiredId().equals( rhs.getRequiredId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredId() != null ) {
					return( false );
				}
			}
			if( getOptionalDefSchemaId() != null ) {
				if( rhs.getOptionalDefSchemaId() != null ) {
					if( ! getOptionalDefSchemaId().equals( rhs.getOptionalDefSchemaId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getOptionalDefSchemaId() != null ) {
					return( false );
				}
			}
			if( getRequiredName() != null ) {
				if( rhs.getRequiredName() != null ) {
					if( ! getRequiredName().equals( rhs.getRequiredName() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredName() != null ) {
					return( false );
				}
			}
			if( getRequiredMembershipString() != null ) {
				if( rhs.getRequiredMembershipString() != null ) {
					if( ! getRequiredMembershipString().equals( rhs.getRequiredMembershipString() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredMembershipString() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFBamRoleDefH) {
			ICFBamRoleDefH rhs = (ICFBamRoleDefH)obj;
			if( ! getCreatedByUserId().equals( rhs.getCreatedByUserId() ) ) {
				return( false );
			}
			if( ! getCreatedAt().equals( rhs.getCreatedAt() ) ) {
				return( false );
			}
			if( ! getUpdatedByUserId().equals( rhs.getUpdatedByUserId() ) ) {
				return( false );
			}
			if( ! getUpdatedAt().equals( rhs.getUpdatedAt() ) ) {
				return( false );
			}
			if( getRequiredScopeId() != null ) {
				if( rhs.getRequiredScopeId() != null ) {
					if( ! getRequiredScopeId().equals( rhs.getRequiredScopeId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredScopeId() != null ) {
					return( false );
				}
			}
			if( getRequiredId() != null ) {
				if( rhs.getRequiredId() != null ) {
					if( ! getRequiredId().equals( rhs.getRequiredId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredId() != null ) {
					return( false );
				}
			}
			if( getOptionalDefSchemaId() != null ) {
				if( rhs.getOptionalDefSchemaId() != null ) {
					if( ! getOptionalDefSchemaId().equals( rhs.getOptionalDefSchemaId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getOptionalDefSchemaId() != null ) {
					return( false );
				}
			}
			if( getRequiredName() != null ) {
				if( rhs.getRequiredName() != null ) {
					if( ! getRequiredName().equals( rhs.getRequiredName() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredName() != null ) {
					return( false );
				}
			}
			if( getRequiredMembershipString() != null ) {
				if( rhs.getRequiredMembershipString() != null ) {
					if( ! getRequiredMembershipString().equals( rhs.getRequiredMembershipString() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredMembershipString() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFBamRoleDefHPKey) {
			ICFBamRoleDefHPKey rhs = (ICFBamRoleDefHPKey)obj;
			if( getRequiredId() != null ) {
				if( rhs.getRequiredId() != null ) {
					if( ! getRequiredId().equals( rhs.getRequiredId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredId() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFBamRoleDefByUNameIdxKey) {
			ICFBamRoleDefByUNameIdxKey rhs = (ICFBamRoleDefByUNameIdxKey)obj;
			if( getRequiredScopeId() != null ) {
				if( rhs.getRequiredScopeId() != null ) {
					if( ! getRequiredScopeId().equals( rhs.getRequiredScopeId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredScopeId() != null ) {
					return( false );
				}
			}
			if( getRequiredName() != null ) {
				if( rhs.getRequiredName() != null ) {
					if( ! getRequiredName().equals( rhs.getRequiredName() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredName() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFBamRoleDefByScopeIdxKey) {
			ICFBamRoleDefByScopeIdxKey rhs = (ICFBamRoleDefByScopeIdxKey)obj;
			if( getRequiredScopeId() != null ) {
				if( rhs.getRequiredScopeId() != null ) {
					if( ! getRequiredScopeId().equals( rhs.getRequiredScopeId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredScopeId() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFBamRoleDefByDefSchemaIdxKey) {
			ICFBamRoleDefByDefSchemaIdxKey rhs = (ICFBamRoleDefByDefSchemaIdxKey)obj;
			if( getOptionalDefSchemaId() != null ) {
				if( rhs.getOptionalDefSchemaId() != null ) {
					if( ! getOptionalDefSchemaId().equals( rhs.getOptionalDefSchemaId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getOptionalDefSchemaId() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFBamRoleDefByUDefIdxKey) {
			ICFBamRoleDefByUDefIdxKey rhs = (ICFBamRoleDefByUDefIdxKey)obj;
			if( getRequiredScopeId() != null ) {
				if( rhs.getRequiredScopeId() != null ) {
					if( ! getRequiredScopeId().equals( rhs.getRequiredScopeId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredScopeId() != null ) {
					return( false );
				}
			}
			if( getOptionalDefSchemaId() != null ) {
				if( rhs.getOptionalDefSchemaId() != null ) {
					if( ! getOptionalDefSchemaId().equals( rhs.getOptionalDefSchemaId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getOptionalDefSchemaId() != null ) {
					return( false );
				}
			}
			if( getRequiredName() != null ) {
				if( rhs.getRequiredName() != null ) {
					if( ! getRequiredName().equals( rhs.getRequiredName() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredName() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else {
			return( false );
		}
	}
	
	@Override
	public int hashCode() {
		int hashCode = getPKey().hashCode();
		hashCode = hashCode + getCreatedByUserId().hashCode();
		hashCode = hashCode + getCreatedAt().hashCode();
		hashCode = hashCode + getUpdatedByUserId().hashCode();
		hashCode = hashCode + getUpdatedAt().hashCode();
		hashCode = hashCode + getRequiredScopeId().hashCode();
		hashCode = hashCode + getRequiredId().hashCode();
		if( getOptionalDefSchemaId() != null ) {
			hashCode = hashCode + getOptionalDefSchemaId().hashCode();
		}
		if( getRequiredName() != null ) {
			hashCode = hashCode + getRequiredName().hashCode();
		}
		if( getRequiredMembershipString() != null ) {
			hashCode = hashCode + getRequiredMembershipString().hashCode();
		}
		return( hashCode & 0x7fffffff );
	}

	@Override
	public int compareTo( Object obj ) {
		int cmp;
		if (obj == null) {
			return( 1 );
		}
		else if (obj instanceof ICFBamRoleDef) {
			ICFBamRoleDef rhs = (ICFBamRoleDef)obj;
			if (getPKey() == null) {
				if (rhs.getPKey() != null) {
					return( -1 );
				}
			}
			else {
				if (rhs.getPKey() == null) {
					return( 1 );
				}
				else {
					cmp = getPKey().compareTo(rhs.getPKey());
					if (cmp != 0) {
						return( cmp );
					}
				}
			}
			cmp = getCreatedByUserId().compareTo( rhs.getCreatedByUserId() );
			if( cmp != 0 ) {
				return( cmp );
			}
			cmp = getCreatedAt().compareTo( rhs.getCreatedAt() );
			if( cmp != 0 ) {
				return( cmp );
			}
			cmp = getUpdatedByUserId().compareTo( rhs.getUpdatedByUserId() );
			if( cmp != 0 ) {
				return( cmp );
			}
			cmp = getUpdatedAt().compareTo( rhs.getUpdatedAt() );
			if( cmp != 0 ) {
				return( cmp );
			}
			if (getRequiredScopeId() != null) {
				if (rhs.getRequiredScopeId() != null) {
					cmp = getRequiredScopeId().compareTo( rhs.getRequiredScopeId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredScopeId() != null) {
				return( -1 );
			}
			if( getOptionalDefSchemaId() != null ) {
				if( rhs.getOptionalDefSchemaId() != null ) {
					cmp = getOptionalDefSchemaId().compareTo( rhs.getOptionalDefSchemaId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else {
				if( rhs.getOptionalDefSchemaId() != null ) {
					return( -1 );
				}
			}
			if (getRequiredName() != null) {
				if (rhs.getRequiredName() != null) {
					cmp = getRequiredName().compareTo( rhs.getRequiredName() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredName() != null) {
				return( -1 );
			}
			if (getRequiredMembershipString() != null) {
				if (rhs.getRequiredMembershipString() != null) {
					cmp = getRequiredMembershipString().compareTo( rhs.getRequiredMembershipString() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredMembershipString() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFBamRoleDefHPKey) {
			ICFBamRoleDefHPKey rhs = (ICFBamRoleDefHPKey)obj;
			if (getRequiredId() != null) {
				if (rhs.getRequiredId() != null) {
					cmp = getRequiredId().compareTo( rhs.getRequiredId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredId() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if( obj instanceof ICFBamRoleDefH ) {
			ICFBamRoleDefH rhs = (ICFBamRoleDefH)obj;
			if (getRequiredId() != null) {
				if (rhs.getRequiredId() != null) {
					cmp = getRequiredId().compareTo( rhs.getRequiredId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredId() != null) {
				return( -1 );
			}
			cmp = getCreatedByUserId().compareTo( rhs.getCreatedByUserId() );
			if( cmp != 0 ) {
				return( cmp );
			}
			cmp = getCreatedAt().compareTo( rhs.getCreatedAt() );
			if( cmp != 0 ) {
				return( cmp );
			}
			cmp = getUpdatedByUserId().compareTo( rhs.getUpdatedByUserId() );
			if( cmp != 0 ) {
				return( cmp );
			}
			cmp = getUpdatedAt().compareTo( rhs.getUpdatedAt() );
			if( cmp != 0 ) {
				return( cmp );
			}
			if (getRequiredScopeId() != null) {
				if (rhs.getRequiredScopeId() != null) {
					cmp = getRequiredScopeId().compareTo( rhs.getRequiredScopeId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredScopeId() != null) {
				return( -1 );
			}
			if( getOptionalDefSchemaId() != null ) {
				if( rhs.getOptionalDefSchemaId() != null ) {
					cmp = getOptionalDefSchemaId().compareTo( rhs.getOptionalDefSchemaId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else {
				if( rhs.getOptionalDefSchemaId() != null ) {
					return( -1 );
				}
			}
			if (getRequiredName() != null) {
				if (rhs.getRequiredName() != null) {
					cmp = getRequiredName().compareTo( rhs.getRequiredName() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredName() != null) {
				return( -1 );
			}
			if (getRequiredMembershipString() != null) {
				if (rhs.getRequiredMembershipString() != null) {
					cmp = getRequiredMembershipString().compareTo( rhs.getRequiredMembershipString() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredMembershipString() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFBamRoleDefByUNameIdxKey) {
			ICFBamRoleDefByUNameIdxKey rhs = (ICFBamRoleDefByUNameIdxKey)obj;
			if (getRequiredScopeId() != null) {
				if (rhs.getRequiredScopeId() != null) {
					cmp = getRequiredScopeId().compareTo( rhs.getRequiredScopeId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredScopeId() != null) {
				return( -1 );
			}
			if (getRequiredName() != null) {
				if (rhs.getRequiredName() != null) {
					cmp = getRequiredName().compareTo( rhs.getRequiredName() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredName() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFBamRoleDefByScopeIdxKey) {
			ICFBamRoleDefByScopeIdxKey rhs = (ICFBamRoleDefByScopeIdxKey)obj;
			if (getRequiredScopeId() != null) {
				if (rhs.getRequiredScopeId() != null) {
					cmp = getRequiredScopeId().compareTo( rhs.getRequiredScopeId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredScopeId() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFBamRoleDefByDefSchemaIdxKey) {
			ICFBamRoleDefByDefSchemaIdxKey rhs = (ICFBamRoleDefByDefSchemaIdxKey)obj;
			if( getOptionalDefSchemaId() != null ) {
				if( rhs.getOptionalDefSchemaId() != null ) {
					cmp = getOptionalDefSchemaId().compareTo( rhs.getOptionalDefSchemaId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else {
				if( rhs.getOptionalDefSchemaId() != null ) {
					return( -1 );
				}
			}
			return( 0 );
		}
		else if (obj instanceof ICFBamRoleDefByUDefIdxKey) {
			ICFBamRoleDefByUDefIdxKey rhs = (ICFBamRoleDefByUDefIdxKey)obj;
			if (getRequiredScopeId() != null) {
				if (rhs.getRequiredScopeId() != null) {
					cmp = getRequiredScopeId().compareTo( rhs.getRequiredScopeId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredScopeId() != null) {
				return( -1 );
			}
			if( getOptionalDefSchemaId() != null ) {
				if( rhs.getOptionalDefSchemaId() != null ) {
					cmp = getOptionalDefSchemaId().compareTo( rhs.getOptionalDefSchemaId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else {
				if( rhs.getOptionalDefSchemaId() != null ) {
					return( -1 );
				}
			}
			if (getRequiredName() != null) {
				if (rhs.getRequiredName() != null) {
					cmp = getRequiredName().compareTo( rhs.getRequiredName() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredName() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				"compareTo",
				"obj",
				obj,
				null );
		}
	}

	@Override
	public void set( ICFBamRoleDef src ) {
		setRoleDef( src );
	}

	@Override
	public void setRoleDef( ICFBamRoleDef src ) {
		setRequiredId(src.getRequiredId());
		setRequiredRevision( src.getRequiredRevision() );
		setCreatedByUserId( src.getCreatedByUserId() );
		setCreatedAt( src.getCreatedAt() );
		setUpdatedByUserId( src.getUpdatedByUserId() );
		setUpdatedAt( src.getUpdatedAt() );
		setRequiredContainerScopeDef(src.getRequiredContainerScopeDef());
		setOptionalLookupDefSchema(src.getOptionalLookupDefSchema());
		setRequiredName(src.getRequiredName());
		setRequiredMembershipString(src.getRequiredMembershipString());
	}

	@Override
	public void set( ICFBamRoleDefH src ) {
		setRoleDef( src );
	}

	@Override
	public void setRoleDef( ICFBamRoleDefH src ) {
		setRequiredId(src.getRequiredId());
		setRequiredContainerScopeDef(src.getRequiredScopeId());
		setOptionalLookupDefSchema(src.getOptionalDefSchemaId());
		setRequiredName(src.getRequiredName());
		setRequiredMembershipString(src.getRequiredMembershipString());
	}

	@Override
	public String getXmlAttrFragment() {
		String ret = ""
			+ " RequiredId=" + "\"" + getRequiredId().toString() + "\""
			+ " classCode=\"" + getClassCode() + "\""
			+ " RequiredRevision=\"" + Integer.toString( getRequiredRevision() ) + "\""
			+ " RequiredScopeId=" + "\"" + getRequiredScopeId().toString() + "\""
			+ " RequiredId=" + "\"" + getRequiredId().toString() + "\""
			+ " OptionalDefSchemaId=" + ( ( getOptionalDefSchemaId() == null ) ? "null" : "\"" + getOptionalDefSchemaId().toString() + "\"" )
			+ " RequiredName=" + "\"" + StringEscapeUtils.escapeXml11( getRequiredName() ) + "\""
			+ " RequiredMembershipString=" + "\"" + StringEscapeUtils.escapeXml11( getRequiredMembershipString() ) + "\"";
		return( ret );
	}

	@Override
	public String toString() {
		String ret = "<CFBamJpaRoleDef" + getXmlAttrFragment() + "/>";
		return( ret );
	}
}
