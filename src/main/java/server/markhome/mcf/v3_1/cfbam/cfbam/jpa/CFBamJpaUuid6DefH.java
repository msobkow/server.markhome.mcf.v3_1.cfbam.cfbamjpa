// Description: Java 25 JPA implementation of Uuid6Def history objects

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
 *  CFBamJpaUuid6DefH provides history objects matching the CFBamUuid6Def change history.
 */
@Entity
@Table(
    name = "uuid6def_h", schema = "CFBam31",
    indexes = {
        @Index(name = "Uuid6DefIdIdx_h", columnList = "auditClusterId, auditStamp, auditAction, requiredRevision, auditSessionId, Id", unique = true)
    }
)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("43116")
@Transactional(Transactional.TxType.SUPPORTS)
@PersistenceContext(unitName = "CFBamPU")
public class CFBamJpaUuid6DefH extends CFBamJpaAtomH
    implements ICFBamUuid6DefH
{
	@Column( name="InitVal", nullable=true, length=62 )
	protected String optionalInitValue;

    public CFBamJpaUuid6DefH() {
            super();
		optionalInitValue = null;
    }

    @Override
    public int getClassCode() {
            return( ICFBamUuid6Def.CLASS_CODE );
    }

	@Override
	public String getOptionalInitValue() {
		return( optionalInitValue );
	}

	@Override
	public void setOptionalInitValue( String value ) {
		if( value != null && value.length() > 62 ) {
			throw new CFLibArgumentOverflowException( getClass(),
				"setOptionalInitValue",
				1,
				"value.length()",
				value.length(),
				62 );
		}
		optionalInitValue = value;
	}

    @Override
    public boolean equals( Object obj ) {
        if (obj == null) {
            return( false );
        }
        else if (obj instanceof ICFBamUuid6Def) {
            ICFBamUuid6Def rhs = (ICFBamUuid6Def)obj;
        if (!super.equals(obj)) {
            return( false );
        }
			if( getOptionalInitValue() != null ) {
				if( rhs.getOptionalInitValue() != null ) {
					if( ! getOptionalInitValue().equals( rhs.getOptionalInitValue() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getOptionalInitValue() != null ) {
					return( false );
				}
			}
            return( true );
        }
        else if (obj instanceof ICFBamUuid6DefH) {
			if (!super.equals(obj)) {
				return( false );
			}
            ICFBamUuid6DefH rhs = (ICFBamUuid6DefH)obj;
        if (!super.equals(obj)) {
            return( false );
        }
			if( getOptionalInitValue() != null ) {
				if( rhs.getOptionalInitValue() != null ) {
					if( ! getOptionalInitValue().equals( rhs.getOptionalInitValue() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getOptionalInitValue() != null ) {
					return( false );
				}
			}
            return( true );
        }
        else if (obj instanceof ICFBamValueHPKey) {
			return( super.equals(obj) );
        }
        else {
			return( super.equals(obj) );
        }
    }
    
    @Override
    public int hashCode() {
        int hashCode = super.hashCode();
		if( getOptionalInitValue() != null ) {
			hashCode = hashCode + getOptionalInitValue().hashCode();
		}
        return( hashCode & 0x7fffffff );
    }

    @Override
    public int compareTo( Object obj ) {
        int cmp;
        if (obj == null) {
            return( 1 );
        }
        else if (obj instanceof ICFBamUuid6Def) {
        cmp = super.compareTo(obj);
        if (cmp != 0) {
            return( cmp );
        }
        ICFBamUuid6Def rhs = (ICFBamUuid6Def)obj;
			if( getOptionalInitValue() != null ) {
				if( rhs.getOptionalInitValue() != null ) {
					cmp = getOptionalInitValue().compareTo( rhs.getOptionalInitValue() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else {
				if( rhs.getOptionalInitValue() != null ) {
					return( -1 );
				}
			}
            return( 0 );
        }
        else if (obj instanceof ICFBamValueHPKey) {
        return( super.compareTo(obj) );
        }
        else if (obj instanceof ICFBamUuid6DefH) {
        cmp = super.compareTo(obj);
        if (cmp != 0) {
            return( cmp );
        }
        ICFBamUuid6DefH rhs = (ICFBamUuid6DefH)obj;
			if( getOptionalInitValue() != null ) {
				if( rhs.getOptionalInitValue() != null ) {
					cmp = getOptionalInitValue().compareTo( rhs.getOptionalInitValue() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else {
				if( rhs.getOptionalInitValue() != null ) {
					return( -1 );
				}
			}
            return( 0 );
        }
        else {
        return( super.compareTo(obj) );
        }
    }
	@Override
    public void set( ICFBamValue src ) {
		if( src instanceof ICFBamUuid6Def ) {
			setUuid6Def( (ICFBamUuid6Def)src );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				"compareTo",
				"src",
				src,
				"CFBamJpaUuid6Def" );
		}
    }

	@Override
    public void setUuid6Def( ICFBamUuid6Def src ) {
        super.setAtom( src );
		setOptionalInitValue( src.getOptionalInitValue() );
    }

	@Override
    public void set( ICFBamValueH src ) {
		if( src instanceof ICFBamUuid6DefH ) {
			setUuid6Def( (ICFBamUuid6DefH)src );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
					"set",
					"src",
					src,
					"ICFBamUuid6DefH" );
		}
    }

	@Override
    public void setUuid6Def( ICFBamUuid6DefH src ) {
        super.setAtom( src );
		setOptionalInitValue( src.getOptionalInitValue() );
    }

    public String getXmlAttrFragment() {
        String ret = super.getXmlAttrFragment() 
			+ " OptionalInitValue=" + ( ( getOptionalInitValue() == null ) ? "null" : "\"" + StringEscapeUtils.escapeXml11( getOptionalInitValue() ) + "\"" );
        return( ret );
    }

    public String toString() {
        String ret = "<CFBamJpaUuid6DefH" + getXmlAttrFragment() + "/>";
        return( ret );
    }
}
