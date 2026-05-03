// Description: Java 25 JPA implementation of TZTimeDef history objects

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
 *  CFBamJpaTZTimeDefH provides history objects matching the CFBamTZTimeDef change history.
 */
@Entity
@Table(
    name = "tmzdef_h", schema = "CFBam31",
    indexes = {
        @Index(name = "TZTimeDefIdIdx_h", columnList = "auditClusterId, auditStamp, auditAction, requiredRevision, auditSessionId, Id", unique = true)
    }
)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("43096")
@Transactional(Transactional.TxType.SUPPORTS)
@PersistenceContext(unitName = "CFBamPU")
public class CFBamJpaTZTimeDefH extends CFBamJpaAtomH
    implements ICFBamTZTimeDefH
{
	@Column( name="Dummy", nullable=true, length=4 )
	protected String optionalDummy;

    public CFBamJpaTZTimeDefH() {
            super();
		optionalDummy = null;
    }

    @Override
    public int getClassCode() {
            return( ICFBamTZTimeDef.CLASS_CODE );
    }

	@Override
	public String getOptionalDummy() {
		return( optionalDummy );
	}

	@Override
	public void setOptionalDummy( String value ) {
		if( value != null && value.length() > 4 ) {
			throw new CFLibArgumentOverflowException( getClass(),
				"setOptionalDummy",
				1,
				"value.length()",
				value.length(),
				4 );
		}
		optionalDummy = value;
	}

    @Override
    public boolean equals( Object obj ) {
        if (obj == null) {
            return( false );
        }
        else if (obj instanceof ICFBamTZTimeDef) {
            ICFBamTZTimeDef rhs = (ICFBamTZTimeDef)obj;
        if (!super.equals(obj)) {
            return( false );
        }
			if( getOptionalDummy() != null ) {
				if( rhs.getOptionalDummy() != null ) {
					if( ! getOptionalDummy().equals( rhs.getOptionalDummy() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getOptionalDummy() != null ) {
					return( false );
				}
			}
            return( true );
        }
        else if (obj instanceof ICFBamTZTimeDefH) {
			if (!super.equals(obj)) {
				return( false );
			}
            ICFBamTZTimeDefH rhs = (ICFBamTZTimeDefH)obj;
        if (!super.equals(obj)) {
            return( false );
        }
			if( getOptionalDummy() != null ) {
				if( rhs.getOptionalDummy() != null ) {
					if( ! getOptionalDummy().equals( rhs.getOptionalDummy() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getOptionalDummy() != null ) {
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
		if( getOptionalDummy() != null ) {
			hashCode = hashCode + getOptionalDummy().hashCode();
		}
        return( hashCode & 0x7fffffff );
    }

    @Override
    public int compareTo( Object obj ) {
        int cmp;
        if (obj == null) {
            return( 1 );
        }
        else if (obj instanceof ICFBamTZTimeDef) {
        cmp = super.compareTo(obj);
        if (cmp != 0) {
            return( cmp );
        }
        ICFBamTZTimeDef rhs = (ICFBamTZTimeDef)obj;
			if( getOptionalDummy() != null ) {
				if( rhs.getOptionalDummy() != null ) {
					cmp = getOptionalDummy().compareTo( rhs.getOptionalDummy() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else {
				if( rhs.getOptionalDummy() != null ) {
					return( -1 );
				}
			}
            return( 0 );
        }
        else if (obj instanceof ICFBamValueHPKey) {
        return( super.compareTo(obj) );
        }
        else if (obj instanceof ICFBamTZTimeDefH) {
        cmp = super.compareTo(obj);
        if (cmp != 0) {
            return( cmp );
        }
        ICFBamTZTimeDefH rhs = (ICFBamTZTimeDefH)obj;
			if( getOptionalDummy() != null ) {
				if( rhs.getOptionalDummy() != null ) {
					cmp = getOptionalDummy().compareTo( rhs.getOptionalDummy() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else {
				if( rhs.getOptionalDummy() != null ) {
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
		if( src instanceof ICFBamTZTimeDef ) {
			setTZTimeDef( (ICFBamTZTimeDef)src );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				"compareTo",
				"src",
				src,
				"CFBamJpaTZTimeDef" );
		}
    }

	@Override
    public void setTZTimeDef( ICFBamTZTimeDef src ) {
        super.setAtom( src );
		setOptionalDummy( src.getOptionalDummy() );
    }

	@Override
    public void set( ICFBamValueH src ) {
		if( src instanceof ICFBamTZTimeDefH ) {
			setTZTimeDef( (ICFBamTZTimeDefH)src );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
					"set",
					"src",
					src,
					"ICFBamTZTimeDefH" );
		}
    }

	@Override
    public void setTZTimeDef( ICFBamTZTimeDefH src ) {
        super.setAtom( src );
		setOptionalDummy( src.getOptionalDummy() );
    }

    public String getXmlAttrFragment() {
        String ret = super.getXmlAttrFragment() 
			+ " OptionalDummy=" + ( ( getOptionalDummy() == null ) ? "null" : "\"" + StringEscapeUtils.escapeXml11( getOptionalDummy() ) + "\"" );
        return( ret );
    }

    public String toString() {
        String ret = "<CFBamJpaTZTimeDefH" + getXmlAttrFragment() + "/>";
        return( ret );
    }
}
