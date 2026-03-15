// Description: Java 25 JPA implementation of UInt32Type history objects

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

/**
 *  CFBamJpaUInt32TypeH provides history objects matching the CFBamUInt32Type change history.
 */
@Entity
@Table(
    name = "uint32typ_h", schema = "CFBam31",
    indexes = {
        @Index(name = "UInt32TypeIdIdx_h", columnList = "auditClusterId, auditStamp, auditAction, requiredRevision, auditSessionId, Id", unique = true),
        @Index(name = "UInt32TypeSchemaDefIdx_h", columnList = "SchemaDefId", unique = false)
    }
)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("43108")
@Transactional(Transactional.TxType.SUPPORTS)
@PersistenceContext(unitName = "CFBamPU")
public class CFBamJpaUInt32TypeH extends CFBamJpaUInt32DefH
    implements ICFBamUInt32TypeH
{
	@AttributeOverrides({
		@AttributeOverride(name="bytes", column = @Column( name="SchemaDefId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 requiredSchemaDefId;

    public CFBamJpaUInt32TypeH() {
            super();
		requiredSchemaDefId = CFLibDbKeyHash256.fromHex( ICFBamUInt32Type.SCHEMADEFID_INIT_VALUE.toString() );
    }

    @Override
    public int getClassCode() {
            return( ICFBamUInt32Type.CLASS_CODE );
    }

	@Override
	public CFLibDbKeyHash256 getRequiredSchemaDefId() {
		return( requiredSchemaDefId );
	}

	@Override
	public void setRequiredSchemaDefId( CFLibDbKeyHash256 value ) {
		if( value == null || value.isNull() ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredSchemaDefId",
				1,
				"value" );
		}
		requiredSchemaDefId = value;
	}

    @Override
    public boolean equals( Object obj ) {
        if (obj == null) {
            return( false );
        }
        else if (obj instanceof ICFBamUInt32Type) {
            ICFBamUInt32Type rhs = (ICFBamUInt32Type)obj;
        if (!super.equals(obj)) {
            return( false );
        }
			if( getRequiredSchemaDefId() != null ) {
				if( rhs.getRequiredSchemaDefId() != null ) {
					if( ! getRequiredSchemaDefId().equals( rhs.getRequiredSchemaDefId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSchemaDefId() != null ) {
					return( false );
				}
			}
            return( true );
        }
        else if (obj instanceof ICFBamUInt32TypeH) {
			if (!super.equals(obj)) {
				return( false );
			}
            ICFBamUInt32TypeH rhs = (ICFBamUInt32TypeH)obj;
        if (!super.equals(obj)) {
            return( false );
        }
			if( getRequiredSchemaDefId() != null ) {
				if( rhs.getRequiredSchemaDefId() != null ) {
					if( ! getRequiredSchemaDefId().equals( rhs.getRequiredSchemaDefId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSchemaDefId() != null ) {
					return( false );
				}
			}
            return( true );
        }
        else if (obj instanceof ICFBamValueHPKey) {
			return( super.equals(obj) );
        }
        else if (obj instanceof ICFBamUInt32TypeBySchemaIdxKey) {
            ICFBamUInt32TypeBySchemaIdxKey rhs = (ICFBamUInt32TypeBySchemaIdxKey)obj;
			if( getRequiredSchemaDefId() != null ) {
				if( rhs.getRequiredSchemaDefId() != null ) {
					if( ! getRequiredSchemaDefId().equals( rhs.getRequiredSchemaDefId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSchemaDefId() != null ) {
					return( false );
				}
			}
            return( true );
        }
        else {
			return( super.equals(obj) );
        }
    }
    
    @Override
    public int hashCode() {
        int hashCode = super.hashCode();
		hashCode = hashCode + getRequiredSchemaDefId().hashCode();
        return( hashCode & 0x7fffffff );
    }

    @Override
    public int compareTo( Object obj ) {
        int cmp;
        if (obj == null) {
            return( 1 );
        }
        else if (obj instanceof ICFBamUInt32Type) {
        cmp = super.compareTo(obj);
        if (cmp != 0) {
            return( cmp );
        }
        ICFBamUInt32Type rhs = (ICFBamUInt32Type)obj;
			if (getRequiredSchemaDefId() != null) {
				if (rhs.getRequiredSchemaDefId() != null) {
					cmp = getRequiredSchemaDefId().compareTo( rhs.getRequiredSchemaDefId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredSchemaDefId() != null) {
				return( -1 );
			}
            return( 0 );
        }
        else if (obj instanceof ICFBamValueHPKey) {
        return( super.compareTo(obj) );
        }
        else if (obj instanceof ICFBamUInt32TypeH) {
        cmp = super.compareTo(obj);
        if (cmp != 0) {
            return( cmp );
        }
        ICFBamUInt32TypeH rhs = (ICFBamUInt32TypeH)obj;
			if (getRequiredSchemaDefId() != null) {
				if (rhs.getRequiredSchemaDefId() != null) {
					cmp = getRequiredSchemaDefId().compareTo( rhs.getRequiredSchemaDefId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredSchemaDefId() != null) {
				return( -1 );
			}
            return( 0 );
        }
        else if (obj instanceof ICFBamUInt32TypeBySchemaIdxKey ) {
            ICFBamUInt32TypeBySchemaIdxKey rhs = (ICFBamUInt32TypeBySchemaIdxKey)obj;
			if (getRequiredSchemaDefId() != null) {
				if (rhs.getRequiredSchemaDefId() != null) {
					cmp = getRequiredSchemaDefId().compareTo( rhs.getRequiredSchemaDefId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredSchemaDefId() != null) {
				return( -1 );
			}
            return( 0 );
        }
        else {
        return( super.compareTo(obj) );
        }
    }
	@Override
    public void set( ICFBamValue src ) {
		if( src instanceof ICFBamUInt32Type ) {
			setUInt32Type( (ICFBamUInt32Type)src );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				"compareTo",
				"src",
				src,
				"CFBamJpaUInt32Type" );
		}
    }

	@Override
    public void setUInt32Type( ICFBamUInt32Type src ) {
        super.setUInt32Def( src );
		setRequiredSchemaDefId( src.getRequiredSchemaDefId() );
    }

	@Override
    public void set( ICFBamValueH src ) {
		if( src instanceof ICFBamUInt32TypeH ) {
			setUInt32Type( (ICFBamUInt32TypeH)src );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
					"set",
					"src",
					src,
					"ICFBamUInt32TypeH" );
		}
    }

	@Override
    public void setUInt32Type( ICFBamUInt32TypeH src ) {
        super.setUInt32Def( src );
		setRequiredSchemaDefId( src.getRequiredSchemaDefId() );
    }

    public String getXmlAttrFragment() {
        String ret = super.getXmlAttrFragment() 
			+ " RequiredSchemaDefId=" + "\"" + getRequiredSchemaDefId().toString() + "\"";
        return( ret );
    }

    public String toString() {
        String ret = "<CFBamJpaUInt32TypeH" + getXmlAttrFragment() + "/>";
        return( ret );
    }
}
