// Description: Java 25 JPA implementation of Uuid6Col history objects

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
 *  CFBamJpaUuid6ColH provides history objects matching the CFBamUuid6Col change history.
 */
@Entity
@Table(
    name = "uuid6col_h", schema = "CFBam31",
    indexes = {
        @Index(name = "Uuid6ColIdIdx_h", columnList = "auditClusterId, auditStamp, auditAction, requiredRevision, auditSessionId, Id", unique = true),
        @Index(name = "Uuid6ColTableIdx_h", columnList = "TableId", unique = false)
    }
)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("43143")
@Transactional(Transactional.TxType.SUPPORTS)
@PersistenceContext(unitName = "CFBamPU")
public class CFBamJpaUuid6ColH extends CFBamJpaUuid6DefH
    implements ICFBamUuid6ColH
{
	@AttributeOverrides({
		@AttributeOverride(name="bytes", column = @Column( name="TableId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 requiredTableId;

    public CFBamJpaUuid6ColH() {
            super();
		requiredTableId = CFLibDbKeyHash256.fromHex( ICFBamUuid6Col.TABLEID_INIT_VALUE.toString() );
    }

    @Override
    public int getClassCode() {
            return( ICFBamUuid6Col.CLASS_CODE );
    }

	@Override
	public CFLibDbKeyHash256 getRequiredTableId() {
		return( requiredTableId );
	}

	@Override
	public void setRequiredTableId( CFLibDbKeyHash256 value ) {
		if( value == null || value.isNull() ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredTableId",
				1,
				"value" );
		}
		requiredTableId = value;
	}

    @Override
    public boolean equals( Object obj ) {
        if (obj == null) {
            return( false );
        }
        else if (obj instanceof ICFBamUuid6Col) {
            ICFBamUuid6Col rhs = (ICFBamUuid6Col)obj;
        if (!super.equals(obj)) {
            return( false );
        }
			if( getRequiredTableId() != null ) {
				if( rhs.getRequiredTableId() != null ) {
					if( ! getRequiredTableId().equals( rhs.getRequiredTableId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredTableId() != null ) {
					return( false );
				}
			}
            return( true );
        }
        else if (obj instanceof ICFBamUuid6ColH) {
			if (!super.equals(obj)) {
				return( false );
			}
            ICFBamUuid6ColH rhs = (ICFBamUuid6ColH)obj;
        if (!super.equals(obj)) {
            return( false );
        }
			if( getRequiredTableId() != null ) {
				if( rhs.getRequiredTableId() != null ) {
					if( ! getRequiredTableId().equals( rhs.getRequiredTableId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredTableId() != null ) {
					return( false );
				}
			}
            return( true );
        }
        else if (obj instanceof ICFBamValueHPKey) {
			return( super.equals(obj) );
        }
        else if (obj instanceof ICFBamUuid6ColByTableIdxKey) {
            ICFBamUuid6ColByTableIdxKey rhs = (ICFBamUuid6ColByTableIdxKey)obj;
			if( getRequiredTableId() != null ) {
				if( rhs.getRequiredTableId() != null ) {
					if( ! getRequiredTableId().equals( rhs.getRequiredTableId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredTableId() != null ) {
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
		hashCode = hashCode + getRequiredTableId().hashCode();
        return( hashCode & 0x7fffffff );
    }

    @Override
    public int compareTo( Object obj ) {
        int cmp;
        if (obj == null) {
            return( 1 );
        }
        else if (obj instanceof ICFBamUuid6Col) {
        cmp = super.compareTo(obj);
        if (cmp != 0) {
            return( cmp );
        }
        ICFBamUuid6Col rhs = (ICFBamUuid6Col)obj;
			if (getRequiredTableId() != null) {
				if (rhs.getRequiredTableId() != null) {
					cmp = getRequiredTableId().compareTo( rhs.getRequiredTableId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredTableId() != null) {
				return( -1 );
			}
            return( 0 );
        }
        else if (obj instanceof ICFBamValueHPKey) {
        return( super.compareTo(obj) );
        }
        else if (obj instanceof ICFBamUuid6ColH) {
        cmp = super.compareTo(obj);
        if (cmp != 0) {
            return( cmp );
        }
        ICFBamUuid6ColH rhs = (ICFBamUuid6ColH)obj;
			if (getRequiredTableId() != null) {
				if (rhs.getRequiredTableId() != null) {
					cmp = getRequiredTableId().compareTo( rhs.getRequiredTableId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredTableId() != null) {
				return( -1 );
			}
            return( 0 );
        }
        else if (obj instanceof ICFBamUuid6ColByTableIdxKey ) {
            ICFBamUuid6ColByTableIdxKey rhs = (ICFBamUuid6ColByTableIdxKey)obj;
			if (getRequiredTableId() != null) {
				if (rhs.getRequiredTableId() != null) {
					cmp = getRequiredTableId().compareTo( rhs.getRequiredTableId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredTableId() != null) {
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
		if( src instanceof ICFBamUuid6Col ) {
			setUuid6Col( (ICFBamUuid6Col)src );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				"compareTo",
				"src",
				src,
				"CFBamJpaUuid6Col" );
		}
    }

	@Override
    public void setUuid6Col( ICFBamUuid6Col src ) {
        super.setUuid6Def( src );
		setRequiredTableId( src.getRequiredTableId() );
    }

	@Override
    public void set( ICFBamValueH src ) {
		if( src instanceof ICFBamUuid6ColH ) {
			setUuid6Col( (ICFBamUuid6ColH)src );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
					"set",
					"src",
					src,
					"ICFBamUuid6ColH" );
		}
    }

	@Override
    public void setUuid6Col( ICFBamUuid6ColH src ) {
        super.setUuid6Def( src );
		setRequiredTableId( src.getRequiredTableId() );
    }

    public String getXmlAttrFragment() {
        String ret = super.getXmlAttrFragment() 
			+ " RequiredTableId=" + "\"" + getRequiredTableId().toString() + "\"";
        return( ret );
    }

    public String toString() {
        String ret = "<CFBamJpaUuid6ColH" + getXmlAttrFragment() + "/>";
        return( ret );
    }
}
